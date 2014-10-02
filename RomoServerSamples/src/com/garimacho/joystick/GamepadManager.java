package com.garimacho.joystick;

import java.util.LinkedList;

import net.java.games.input.Controller;
import net.java.games.input.Controller.Type;
import net.java.games.input.ControllerEnvironment;

public class GamepadManager {
	
	private LinkedList<GamepadInput> unusedControllers=new LinkedList<>();
	private LinkedList<GamepadInput> usedControllers=new LinkedList<>();
	
	public GamepadManager(){

		ControllerEnvironment ce = ControllerEnvironment.getDefaultEnvironment(); 
		{
			for(Controller c:ce.getControllers()){
				if(c.getType()==Type.GAMEPAD){
//					System.out.println(c);
					unusedControllers.add(new GamepadInput(c));
				}
			}
		}
		System.out.println("found "+unusedControllers.size()+" controllers");
	}
	
//	public synchronized List<GamepadInput> gamepads(){return unusedControllers;}
	
	public synchronized int numUnused(){return unusedControllers.size();}
	
	public synchronized GamepadInput getUnused(){
		if(unusedControllers.size()==0)return null;
//		System.out.println(unusedControllers+" "+usedControllers);
		GamepadInput gamepad=unusedControllers.poll();
		gamepad.start();
		usedControllers.add(gamepad);
		return gamepad;
	}
	
	public static GamepadInput getSingleController(){
		GamepadManager gamepadManager=new GamepadManager();
		return gamepadManager.getUnused();
	}
}
