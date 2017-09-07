package com.crystal_optech.app.tools;

public class BaseTools {

	public static void wait(int time) {
		if (time>0) {
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
