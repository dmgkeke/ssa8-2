package com.sds.metac.launcher;

import com.sds.metac.core.Core;

public class CoreLauncher {
	public static void main(String[] args) {
		Core core = Core.INSTANCE;
		
		core.doProcess();
	}
}
