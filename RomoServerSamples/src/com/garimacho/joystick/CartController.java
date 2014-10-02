package com.garimacho.joystick;

import net.java.games.input.Component;

import com.garimacho.remote.model.RomoCharacterEmotion;
import com.garimacho.remote.model.RomoCharacterExpression;
import com.garimacho.remote.model.RomoModel;
import common.util.Sleeper;

/**
 * Demo of controlling Romo using gamepad
 * @author kenny
 *
 */
public class CartController extends GamepadAdapter{
	RomoModel romo;
	
	//movement parameters
	private double acceleration=1;
	private double damper=0.9;
	private double maxPower=0.8;
	
	public void setMovementParameters(double acceleration, double damper, double maxPower){
		this.acceleration=acceleration;
		this.damper=damper;
		this.maxPower=maxPower;
	}

	public double acceleration(){return acceleration;}
	/**
	 * maxAcceleration- acceleration when "accelerate" button is pressed. numbers between 0.1~1 is recommended
	 * @return
	 */
	public void setAcceleration(double acceleration){
		this.acceleration=acceleration;
	}
	public double damper(){return damper;}
	/**
	 * damper- deceleration when no buttons are pressed
	 * @return
	 */
	public void setDamper(double damper){
		this.damper=damper;
	}
	public double maxPower(){return maxPower;}
	/**
	 * maxPower- bound on maximum speed of Romo
	 * @return
	 */
	public void setMaxPower(double maxPower){
		this.maxPower=maxPower;
	}
	
	
	double turnLevel=0;
	double acc=0;
	
	/**
	 * Input infos
	 */
//	private long tStartedGoingStraight=0;
//	public long durationStraight(){
//		if(tStartedGoingStraight==0)return 0;
//		return System.currentTimeMillis()-tStartedGoingStraight;
//	}
	private long tLastUserInput=System.currentTimeMillis();
	public long durationNoUserInput(){
		return System.currentTimeMillis()-tLastUserInput;
	}

	public final GamepadInput gamepad;
	public CartController(final RomoModel romo, GamepadInput gamepad){
		this.gamepad=gamepad;
		
//		final Mutable<Boolean> controllable=new Mutable<Boolean>(true);
		if(gamepad!=null)gamepad.addListener(this);
		
		this.romo=romo;
		romo.tiltToAngle(90);
		
		Thread thread=new Thread(){
			public void run(){
				Sleeper sleeper=new Sleeper(60);
				double power=0;
				
				double prevLeftPower=0, prevRightPower=0;
				
				while(sleeper.sleep()){
					power+= acc*0.05;
					if(acc==0)
						power*=damper;
					
					if(power>maxPower)power=maxPower;
					if(power<-maxPower)power=-maxPower;
					
					double leftPower=power;
					double rightPower=power;
					
					if(turnLevel<0){
						leftPower= map(turnLevel,0,-1.8, leftPower,-leftPower);
						rightPower=power*1.5f;
					}else if(turnLevel>0){
						rightPower= map(turnLevel,0,1.8, rightPower,-rightPower);
//						rightPower*=(1-turnLevel);
						leftPower=power*1.5f;
					}
					if(Math.abs(prevLeftPower-leftPower)>0.05 || Math.abs(prevRightPower-rightPower)>0.05){
						romo.setRawPower(leftPower, rightPower);
					}
				}
			}
		};
		thread.start();
	}
	
	@Override
	public void leftJoystickChanged(float x, float y) {
		turnLevel=x;
		tLastUserInput=System.currentTimeMillis();
	}
	

	@Override
	public void buttonHeld(Component component) {
		if(component.getName().equals("5")){ //right top trigger -> accelerate
			romo.setEmotion(RomoCharacterEmotion.RMCharacterEmotionExcited);
			romo.tiltToAngle(70);
			acc=acceleration;
		}else if(component.getName().equals("4")){ //left top trigger
			romo.setEmotion(RomoCharacterEmotion.RMCharacterEmotionExcited);
			romo.tiltToAngle(110);
			acc=-acceleration;
		}
		else if(component.getName().equals("0")){ //spin
//			animateSpin();
		}else if(component.getName().equals("1")){ //zoom
//			animateZoom();
		}else if(component.getName().equals("3")){ //zoom
//				new Thread(){
//					public void run(){
//						romo.playSound("zoom_accelerate.wav");
//					}
//				}.start();
		}
		tLastUserInput=System.currentTimeMillis();
	}

	//70,135
	double angle=90;
	boolean front=false;
	boolean flash=false;
	@Override
	public void buttonPressed(Component component) {
		if(component.getName().equals("7")){ //right bottom trigger
			front=!front;
			romo.startCapture(front);
		}else if(component.getName().equals("6")){ //left bottom trigger
			flash=!flash;
			romo.setFlash(flash);
		}else if(component.getName().equals("5")){ //right top trigger -> accelerate
			romo.setEmotion(RomoCharacterEmotion.RMCharacterEmotionExcited);
			romo.tiltToAngle(70);
			acc=acceleration;
		}else if(component.getName().equals("4")){ //left top trigger
			romo.setEmotion(RomoCharacterEmotion.RMCharacterEmotionExcited);
			romo.tiltToAngle(110);
			acc=-acceleration;
		}
//			else if(component.getName().equals("0")){ //spin
//			animateSpin();
//			}else if(component.getName().equals("1")){ //zoom
//			animateZoom();
//			}else if(component.getName().equals("3")){ //zoom
//				new Thread(){
//					public void run(){
//						romo.playSound("zoom_accelerate.wav");
//					}
//				}.start();
//			}
		else if(component.getName().equals("0")){
			romo.setExpression(RomoCharacterExpression.RMCharacterExpressionAngry);
		}
		else if(component.getName().equals("1")){
			romo.setExpression(RomoCharacterExpression.RMCharacterExpressionHappy);
		}
		else if(component.getName().equals("2")){
			romo.setExpression(RomoCharacterExpression.RMCharacterExpressionSmack);
		}
		else if(component.getName().equals("3")){
			romo.setExpression(RomoCharacterExpression.RMCharacterExpressionWee);
		}
		tLastUserInput=System.currentTimeMillis();
	}
	

	@Override
	public void buttonReleased(Component component) {
		if(component.getName().equals("5")){ //right top trigger -> accelerate
			romo.setEmotion(RomoCharacterEmotion.RMCharacterEmotionSleepy);
			romo.tiltToAngle(90);
			acc=0;
		}else if(component.getName().equals("4")){ //left top trigger
			romo.setEmotion(RomoCharacterEmotion.RMCharacterEmotionSleepy);
			romo.tiltToAngle(90);
			acc=0;
//			angle-=1;
//			if(angle>135)angle=135;
//			if(angle<70)angle=70;
////			System.out.println(angle);
//			romo.tiltToAngle(angle);
		}
		tLastUserInput=System.currentTimeMillis();
	}

	public static double map(double v,double origMin,double origMax,double min,double max){
		if(min>max){
			double t=max;
			max=min;
			min=t;
			t=origMin;
			origMin=origMax;
			origMax=t;
		}
		if(v==origMin){
			return min;
		}
		if(v==origMax){
			return max;
		}
		
		v= (v-origMin)/(origMax-origMin)*(max-min)+min;
		
		return Math.min(Math.max(v,min),max);
	}
	
	
}
