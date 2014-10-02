package com.garimacho.romo.samples;

import com.garimacho.remote.model.RomoModel;
import com.garimacho.remote.server.RomoServerManager;

/**
 * Demo showing how to start and stop
 * @author kenny
 *
 */
public class StartAndStop {


	public static void main(String[] args) throws InterruptedException {
		//create and start server
		RomoServerManager manager=new RomoServerManager();
		//wait until any device connects
		final RomoModel romo=manager.getAnyModel();
		
		//move slowly forwards...
		romo.setRawPower(0.6, 0.6);
		Thread.sleep(500); //for 500ms
		
		//and stop
		romo.setRawPower(0, 0);
		
		manager.kill();
	}
	
}
