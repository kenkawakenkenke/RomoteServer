package com.garimacho.romo.samples;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

import com.garimacho.remote.model.RomoCharacterEmotion;
import com.garimacho.remote.model.RomoCharacterExpression;
import com.garimacho.remote.model.RomoModel;
import com.garimacho.remote.server.RomoServerManager;

/**
 * Set expressions/emotions by selecting from GUI
 * @author kenny
 *
 */
public class GUI_ExpressionsEmotions {


	public static void main(String[] args) throws InterruptedException {
		RomoServerManager manager=new RomoServerManager();
		final RomoModel romo=manager.getAnyModel();
		
		JFrame characterFrame=new JFrame();
		{
			characterFrame.setLayout(new BoxLayout(characterFrame.getContentPane(), BoxLayout.Y_AXIS));
			for(final RomoCharacterEmotion emotion:RomoCharacterEmotion.values()){
				JButton btn=new JButton(emotion.name());
				btn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						romo.setEmotion(emotion);
					}
				});;
				characterFrame.add(btn);
			}
			characterFrame.pack();
			characterFrame.setVisible(true);
		}

		JFrame expressionFrame=new JFrame();
		{
			expressionFrame.setLayout(new GridLayout(10, 10));
			for(final RomoCharacterExpression expression:RomoCharacterExpression.values()){
				JButton btn=new JButton(expression.name());
				btn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						romo.setExpression(expression);
					}
				});
				expressionFrame.add(btn);
			}
			expressionFrame.pack();
			expressionFrame.setVisible(true);
		}
		
	}
	
}
