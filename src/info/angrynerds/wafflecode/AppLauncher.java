package info.angrynerds.wafflecode;

import info.angrynerds.wafflecode.mvc.WaffleController;

public class AppLauncher {
	public static final String VERSION = "Alpha Version 0.2.1";
	
	public static void main(String[] args) {
		new WaffleController().runApplication();
	}
}
