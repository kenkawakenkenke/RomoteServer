package com.garimacho.romo.samples;

import com.garimacho.joystick.CartController;
import com.garimacho.joystick.GamepadInput;
import com.garimacho.joystick.GamepadManager;
import com.garimacho.remote.model.RomoModel;
import com.garimacho.remote.server.RomoServerManager;
import com.garimacho.remote.server.RomoServerManager.RomoServerListener;

/**
 * Demo showing how to use gamepad with Romo. Requires a gamepad compatible with JInput
 * @author kenny
 *
 */
public class GamepadDemo {

	public static void main(String[] args) {
		//create Romo device manager
		RomoServerManager manager=new RomoServerManager();

		//create gamepad manager
		final GamepadManager gamepadManager=new GamepadManager();
		
		//listener for Romo server
		manager.addListener(new RomoServerListener() {
			
			//what to do when a new device is added
			@Override
			public void deviceAdded(RomoModel romo) {
				//get a new gamepad
				GamepadInput gamepad=gamepadManager.getUnused();
				if(gamepad!=null){
					//create controller for romo, using gamepad
					CartController controller=new CartController(romo, gamepad);
					controller.setMovementParameters(0.6/*acceleration*/, 0.9/*damping*/, 0.8/*max power*/);
					
				}
			}
		});
		
	}
}
