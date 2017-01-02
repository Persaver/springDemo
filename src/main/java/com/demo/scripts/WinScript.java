package com.demo.scripts;

import org.springframework.stereotype.Component;

@Component("winScript")
public class WinScript implements ICommandLineScript{

	@Override
	public String viewDirectoryContents(String dir) {
		return "dir " + dir;
	}

}
