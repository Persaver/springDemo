package com.demo.scripts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ScriptLoaderDemoClass {
	protected static final Logger LOG = LoggerFactory.getLogger(ScriptLoaderDemoClass.class);

	@Value("#{systemProperties['os.name'] matches 'Windows 7' ? winScript : unixScript}")
	private ICommandLineScript script;

	public ICommandLineScript getScript() {
		return this.script;
	}

	public void setScript(ICommandLineScript script) {
		this.script = script;
	}

	public ScriptLoaderDemoClass() {
	}

	public String loadScript() {
		String out = "Loading script of type: " + this.script.getClass();
		LOG.info(out);
		out += this.script.viewDirectoryContents(" myDir");
		LOG.info(this.script.viewDirectoryContents("myDir"));

		return out;
	}

}
