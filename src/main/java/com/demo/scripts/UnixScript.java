package com.demo.scripts;

import org.springframework.stereotype.Component;

@Component("unixScript")
public class UnixScript implements ICommandLineScript {

	@Override
	public String viewDirectoryContents(String dir) {
		return "ls " + dir;
	}
}
