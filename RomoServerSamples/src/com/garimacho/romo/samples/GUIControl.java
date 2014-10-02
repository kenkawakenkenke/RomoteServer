package com.garimacho.romo.samples;

import com.garimacho.remote.model.RomoModel;
import com.garimacho.remote.server.RomoServerManager;

public class GUIControl {


	public static void main(String[] args) {
		RomoServerManager manager=new RomoServerManager();
		RomoModel romo=manager.getAnyModel();
		
		romo.openDefaultGUI();
		
	}
	
}
