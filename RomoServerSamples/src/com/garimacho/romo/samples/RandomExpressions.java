package com.garimacho.romo.samples;

import com.garimacho.remote.model.RomoCharacterExpression;
import com.garimacho.remote.model.RomoModel;
import com.garimacho.remote.server.RomoServerManager;

/**
 * Sets random expressions every 5 seconds
 * @author kenny
 *
 */
public class RandomExpressions {

	public static void main(String[] args) throws InterruptedException {
		RomoServerManager manager=new RomoServerManager();
		RomoModel romo=manager.getAnyModel();
		
		romo.showRomo(); //make sure Romo is shown
		while(true){
			//set random expression
			RomoCharacterExpression expression=RomoCharacterExpression.values()[(int)(Math.random()*RomoCharacterExpression.values().length)];
			System.out.println(expression);
			
			romo.setExpression(expression);
			Thread.sleep(5000);
		}
	}
	
}
