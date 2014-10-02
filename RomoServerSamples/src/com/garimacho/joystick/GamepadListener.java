package com.garimacho.joystick;

import net.java.games.input.Component;

public interface GamepadListener{
	public void buttonPressed(Component component);
	public void buttonReleased(Component component);
	public void buttonHeld(Component component);
	
	public void leftJoystickChanged(float x,float y);
	public void rightJoystickChanged(float x,float y);
}