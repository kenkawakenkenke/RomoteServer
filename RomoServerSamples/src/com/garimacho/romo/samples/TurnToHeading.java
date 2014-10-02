package com.garimacho.romo.samples;

import com.garimacho.remote.model.RomoModel;
import com.garimacho.remote.server.RomoServerManager;

/**
 * {@link RomoModel#turnToHeadingBlocking(double)} lets you turn the Romo to the specified (IMU) heading
 * @author kenny
 *
 */
public class TurnToHeading {


	public static void main(String[] args) throws InterruptedException {
		RomoServerManager manager=new RomoServerManager();
		RomoModel romo=manager.getAnyModel();

		romo.turnToHeadingBlocking(0);
		romo.setRawPower(1, 1);
		Thread.sleep(800);
		
		romo.turnToHeadingBlocking(Math.PI/2);
		romo.setRawPower(1, 1);
		Thread.sleep(800);

		
		romo.turnToHeadingBlocking(Math.PI);
		romo.setRawPower(1, 1);
		Thread.sleep(800);

		
		romo.turnToHeadingBlocking(Math.PI*3/4);
		romo.setRawPower(1, 1);
		Thread.sleep(800);
		
		romo.turnToHeadingBlocking(0);
		romo.setRawPower(1, 1);
		Thread.sleep(800);
		
	}
	
}
