package com.garimacho.joystick;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.HashSet;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.java.games.input.Component;

@SuppressWarnings("serial")
public class GamePadView extends JFrame{
	
	final GamepadInput gamepad;
	
	JoystickPanel leftPanel, rightPanel;
	JLabel txtControl;
	public GamePadView(GamepadInput gamepad){
		this.gamepad=gamepad;

		this.setTitle(gamepad.id+"");
		
		this.getContentPane().setLayout(new BorderLayout());
		
		JPanel pnlJoysticks=new JPanel();
		{
			pnlJoysticks.setLayout(new BoxLayout(pnlJoysticks,BoxLayout.X_AXIS));
	
			leftPanel=new JoystickPanel();
			rightPanel=new JoystickPanel();
			
			pnlJoysticks.add(leftPanel);
			pnlJoysticks.add(rightPanel);
		}
		this.add(pnlJoysticks,BorderLayout.CENTER);
		
		JPanel pnlButtons=new JPanel();
		{
			txtControl=new JLabel("Initializing...");
			pnlButtons.add(txtControl);
		}
		this.add(pnlButtons,BorderLayout.SOUTH);
		
		this.pack();
		this.setVisible(true);
		
		gamepad.addListener(new GamepadAdapter() {
			
			@Override
			public void buttonHeld(Component component) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void rightJoystickChanged(float x, float y) {
				rightPanel.update(x, y);
			}
			
			@Override
			public void leftJoystickChanged(float x, float y) {
//				System.out.println(x+" "+y);
				leftPanel.update(x, y);
			}
			
			@Override
			public void buttonReleased(Component component) {
				downButtons.remove(component.getName());

				txtControl.setText(downButtons.toString());
			}
			HashSet<String> downButtons=new HashSet<String>();
			
			@Override
			public void buttonPressed(Component component) {
				downButtons.add(component.getName());
				
				txtControl.setText(downButtons.toString());
			}
		});
	}
	
	public static class JoystickPanel extends JPanel{
		double x,y;
		
		public JoystickPanel() {
			this.setPreferredSize(new Dimension(300,300));
		}
		public void update(double x,double y){
			this.x=x;
			this.y=y;
			
			repaint();
		}
		public void paint(java.awt.Graphics g) {
			int size=getWidth()*7/8;
			
			g.setColor(new Color(230,230,255));
			g.fillOval(getWidth()/2-size/2, getHeight()/2-size/2, size,size);
			
			int cx=(int)(getWidth()/2 + x*size/2);
			int cy=(int)(getHeight()/2 + y*size/2);
//			int cx=(int)MathUtil.map(x, -1, 1,(getWidth()/2)-size/2,(getWidth()/2)+size/2);
//			int cy=(int)MathUtil.map(y, -1, 1,(getHeight()/2)-size/2,(getHeight()/2)+size/2);
			
			
			g.setColor(Color.RED);
			g.fillRect(cx-2, cy-2, 4, 4);
		}
	}
	
	public static void main(String[] args) {
		GamepadManager gamepads=new GamepadManager();
		while(gamepads.numUnused()>0){
			new GamePadView(gamepads.getUnused());
		}
		
	}
}
