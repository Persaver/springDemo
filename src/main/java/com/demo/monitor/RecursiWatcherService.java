package com.demo.monitor;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sun.nio.file.SensitivityWatchEventModifier;

@Service
public class RecursiWatcherService {

	private static final Logger LOG = LoggerFactory.getLogger(RecursiWatcherService.class);

	@Value("${root.folder}")
	private File rootFolder;

	private WatchService watcher;

	private ExecutorService executor;

	@PostConstruct
	public void init() throws IOException {
		this.watcher = FileSystems.getDefault().newWatchService();
		this.executor = Executors.newSingleThreadExecutor();
		this.startRecursiveWatcher();
	}

	@PreDestroy
	public void cleanup() {
		try {
			this.watcher.close();
		} catch (IOException e) {
			LOG.error("Error closing watcher service", e);
		}
		this.executor.shutdown();
	}

	private void startRecursiveWatcher() throws IOException {
		LOG.info("Starting Recursive Watcher");

		final Map<WatchKey, Path> keys = new HashMap<>();

		Consumer<Path> register = p -> {
			// test si le fichier existe et si c'est un dossier
			if (!p.toFile().exists() || !p.toFile().isDirectory()) {
				throw new RuntimeException("folder " + p + " does not exist or is not a directory");
			}
			try {
				// Ne suit pas les liens symboliques et visite tout les niveaux de l'arboressnce
				Files.walkFileTree(p, new SimpleFileVisitor<Path>() {
					@Override
					public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
						LOG.info("registering " + dir + " in watcher service");
						// Pose une watchKey sur la création, la suppréssion et la modification
						WatchKey watchKey = dir.register(RecursiWatcherService.this.watcher, new WatchEvent.Kind[]{ENTRY_CREATE,ENTRY_DELETE,ENTRY_MODIFY}, SensitivityWatchEventModifier.HIGH);
						keys.put(watchKey, dir);
						return FileVisitResult.CONTINUE;
					}
				});
			} catch (IOException e) {
				throw new RuntimeException("Error registering path " + p);
			}
		};

		register.accept(this.rootFolder.toPath());

		this.executor.submit(() -> {
			// boucle infini pour l'écoute
			while (true) {
				final WatchKey key;
				try {
					key = this.watcher.take(); // wait for a key to be available
				} catch (InterruptedException ex) {
					return;
				}

				final Path dir = keys.get(key);
				if (dir == null) {
					System.err.println("WatchKey " + key + " not recognized!");
					continue;
				}

				// filtre le evenement de type 
				key.pollEvents().stream()
				.filter(e -> (e.kind() != OVERFLOW))
				.map(e -> ((WatchEvent<Path>) e))
				.forEach(e -> {
					Path p = e.context();
					final Path absPath = dir.resolve(p);
					if (absPath.toFile().isDirectory()) {
						if(e.kind() == ENTRY_CREATE){
							register.accept(absPath);
						}

					} else {
						final File f = absPath.toFile();
						String action = null, type = null;
						// TODO
						//tester le type event pour afficher la bonne info
						if(e.kind() == ENTRY_CREATE) { action = "Detected new file";	}
						if(e.kind() == ENTRY_DELETE) { action = "Detected delete file";	}
						if(e.kind() == ENTRY_MODIFY) { action = "Detected modify file";	}

						// teste l'extension du fichier

						type = this.identifyFileTypeUsingFilesProbeContentType(f.getAbsolutePath());




						LOG.info("{} {} {} ",action , type, f.getAbsolutePath());
						//LOG.info("p " + e.kind());
					}
				});

				boolean valid = key.reset(); // IMPORTANT: The key must be reset after processed
				if (!valid) {
					break;
				}
			}
		});
	}

	/**
	 * Identify file type of file with provided path and name
	 * using JDK 7's Files.probeContentType(Path).
	 *
	 * @param fileName Name of file whose type is desired.
	 * @return String representing identified type of file with provided name.
	 */
	public String identifyFileTypeUsingFilesProbeContentType(final String fileName)
	{
		String fileType = "Undetermined";
		final File file = new File(fileName);
		try
		{
			fileType = Files.probeContentType(file.toPath());
		}
		catch (IOException ioException)
		{
			LOG.info("ERROR: Unable to determine file type for {} due to exception {}" , file, ioException);
		}
		return fileType;
	}
}