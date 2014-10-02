package com.garimacho.romo.samples;

import java.awt.Color;

import com.garimacho.remote.model.RomoModel;
import com.garimacho.remote.server.RomoServerManager;

/**
 * Demo showing how to show full screen colour on device
 * @author kenny
 *
 */
public class ShowColor {
	public static void main(String[] args) throws InterruptedException {
		RomoServerManager manager=new RomoServerManager();
		RomoModel romo=manager.getAnyModel();
		
		while(true){
			
			Color color=new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255));
			romo.showColor(color);
			
			Thread.sleep(200);
		}
		
	}
	
}
