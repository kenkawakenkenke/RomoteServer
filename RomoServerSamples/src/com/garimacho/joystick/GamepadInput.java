package com.garimacho.joystick;

import java.util.ArrayList;
import java.util.List;

import net.java.games.input.Component;
import net.java.games.input.Controller;

import common.util.Sleeper;

public class GamepadInput extends Thread{
	public final int id=(int)(Math.random()*10000);
	@Override
	public String toString() {
		return "Controller["+id+"]";
	}
	
	private Controller controller;
	private double samplingRate=30;
	public GamepadInput(Controller controller){
		this(controller, 15);
	}
	public GamepadInput(Controller controller, double samplingRate){
		this.samplingRate=samplingRate;
		this.controller=controller;
//		ControllerEnvironment ce = ControllerEnvironment.getDefaultEnvironment(); 
//		{
//			for(Controller c:ce.getControllers()){
//				if(c.getType()==Type.GAMEPAD){
//					controller=c;
//				}
//			}
//		}
//		if(controller==null){
//			System.err.println("no controller found!");
//		}else{
//			start();
//		}
	}
	
	public static class JoystickState{
		public float x, y;
		public boolean isOn=false;
		
		public float prevX, prevY;
		public boolean prevWasOn=false;
		
		public void update(Component componentX, Component componentY){
			prevX=x;
			prevY=y;
			prevWasOn=isOn;
			
			x=componentX.getPollData();
			y=componentY.getPollData();
			if(Math.abs(x)<0.01f)x=0;
			if(Math.abs(y)<0.01f)y=0;
			if(x!=0 || y!=0){
				isOn=true;
			}else{
				isOn=false;
				x=y=0;
			}
		}
		public boolean isOn(){return isOn;}
		public boolean turnedOff(){return prevWasOn && !isOn;}
	}
	public void run(){
		Sleeper sleeper=new Sleeper(samplingRate);

		JoystickState leftJoystick=new JoystickState();
		JoystickState rightJoystick=new JoystickState();
		
		final long THR_HOLD=100;
		
		long tPressed[]=new long[controller.getComponents().length];
		while(sleeper.sleep()){ 
			long t=System.currentTimeMillis();
			
			controller.poll();
			
			Component[] components=controller.getComponents();
			
			//left joystick
			leftJoystick.update(components[0], components[1]);
			if(leftJoystick.isOn() || leftJoystick.turnedOff())
				dispatchLeftJoystickChanged(leftJoystick.x, leftJoystick.y);

			//right joystick
			rightJoystick.update(components[2], components[3]);
			if(rightJoystick.isOn() || rightJoystick.turnedOff())
				dispatchRightJoystickChanged(rightJoystick.x, rightJoystick.y);
			
			for(int i=4;i<components.length;i++){
				Component component=components[i];
				
//				if(component.isAnalog()){
//					float v=component.getPollData();
//					if(Math.abs(v)>0.01){
//						list.add(component.getName()+"("+String.format("%.02f",component.getPollData())+")");
//					}
//				}
				if(!component.isAnalog()){
					if(component.getPollData()==1){
						if(tPressed[i]==0){
							dispatchButtonPressed(component);
							dispatchButtonHeld(component);
							tPressed[i]=t;
						}else if(t-tPressed[i]>=THR_HOLD){
							dispatchButtonHeld(component);
						}
					}else{
						if(tPressed[i]>0){
							dispatchButtonReleased(component);
							tPressed[i]=0;
						}
					}
				}
			}

//			System.out.println(list);
		}
	}
	
	List<GamepadListener> listeners=new ArrayList<GamepadListener>();
	public void addListener(GamepadListener listener){
		synchronized(listeners){
			listeners.add(listener);
		}
	}
	public void removeListener(GamepadListener listener){
		synchronized(listeners){
			listeners.remove(listener);
		}
	}
	public void dispatchButtonPressed(Component component){
		synchronized(listeners){
			for(GamepadListener listener:listeners){
				try{
					listener.buttonPressed(component);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
	public void dispatchButtonHeld(Component component){
		synchronized(listeners){
			for(GamepadListener listener:listeners){
				try{
					listener.buttonHeld(component);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
	public void dispatchButtonReleased(Component component){
		synchronized(listeners){
			for(GamepadListener listener:listeners){
				try{
					listener.buttonReleased(component);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
	public void dispatchLeftJoystickChanged(float x, float y){
		synchronized(listeners){
			for(GamepadListener listener:listeners){
				try{
					listener.leftJoystickChanged(x, y);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
	public void dispatchRightJoystickChanged(float x, float y){
		synchronized(listeners){
			for(GamepadListener listener:listeners){
				try{
					listener.rightJoystickChanged(x, y);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) {
		GamepadManager gamepadManager=new GamepadManager();
		
		
		while(gamepadManager.numUnused()>0){
			final GamepadInput gamepad=gamepadManager.getUnused();
			gamepad.addListener(new GamepadListener() {
				
				@Override
				public void buttonReleased(Component component) {
				}
				@Override
				public void buttonHeld(Component component) {
					System.out.println(gamepad+" held: "+component.getName());
				}
				
				@Override
				public void buttonPressed(Component component) {
					System.out.println(gamepad+" pressed: "+component.getName());
				}
	
				@Override
				public void leftJoystickChanged(float x, float y) {
					System.out.println(gamepad+" left: "+x+","+y);
				}
	
				@Override
				public void rightJoystickChanged(float x, float y) {
					System.out.println(gamepad+" right: "+x+","+y);
				}
			});
		}
		
	}
}
