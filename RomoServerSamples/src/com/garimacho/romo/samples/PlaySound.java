package com.garimacho.romo.samples;

import com.garimacho.remote.model.RomoModel;
import com.garimacho.remote.server.RomoServerManager;

/**
 * Demo playing saved sound on Romo
 * @author kenny
 *
 */
public class PlaySound {

	public static void main(String[] args) throws InterruptedException {
		//create and start server
		RomoServerManager manager=new RomoServerManager();
		//wait until any device connects
		final RomoModel romo=manager.getAnyModel();
		
		//play sound
		romo.playSound("throw.wav");
		
		//kill server
		manager.kill();
	}
	
}
