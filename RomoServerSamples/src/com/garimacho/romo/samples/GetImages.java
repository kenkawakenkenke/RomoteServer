package com.garimacho.romo.samples;

import java.awt.image.BufferedImage;

import com.garimacho.remote.model.RomoModel;
import com.garimacho.remote.server.RomoServerManager;

import common.gui.ImageDialog;

/**
 * Demo showing how to get images from Romo camera
 * @author kenny
 *
 */
public class GetImages {

	public static void main(String[] args) throws InterruptedException {
		RomoServerManager manager=new RomoServerManager();
		final RomoModel romo=manager.getAnyModel();
		
		//start capturing on front camera
		romo.startCapture(true);
		
		ImageDialog dialog=new ImageDialog();
		while(true){
			//get image from device
			BufferedImage img=romo.getImage();
			
			dialog.setImage(img);
			
			Thread.sleep(500);
		}
		
	}
	
}
