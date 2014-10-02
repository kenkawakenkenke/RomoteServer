package common.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ImageDialog extends JFrame{
	public static class ImagePanel extends JPanel{
		private BufferedImage img;
		public void setImage(BufferedImage img){
			this.img=img;
			if(img!=null){
				this.setPreferredSize(new Dimension(img.getWidth(),img.getHeight()));
				this.setSize(new Dimension(img.getWidth(),img.getHeight()));
			}
		}
		public void paint(Graphics g){
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, getWidth(),getHeight());
			if(img!=null)
			g.drawImage(img, 0, 0, this.getWidth(),this.getHeight(),this);
		}
	}
	
	ArrayList<ImagePanel> pnlImages=new ArrayList<ImagePanel>();
	
	JPanel pnlMain=new JPanel();
	public JPanel pnlMain(){return pnlMain;}
	public ImageDialog(boolean exitOnClose){
		pnlMain.setLayout(new BoxLayout(pnlMain, BoxLayout.Y_AXIS));
		
		this.add(pnlMain);
		this.pack();
		this.setVisible(true);
		
		if(exitOnClose)
			this.addWindowListener(new WindowAdapter(){
				public void windowClosing(WindowEvent e) {
					System.exit(-1);
				}
			});
	}
	public ImageDialog(){
		this(false);
	}
	public ImageDialog(BufferedImage img){
		this(false);
		
		setImage(img);
	}
	
	public void setImage(int idx,BufferedImage img){
		while(idx>=pnlImages.size())
			addImage(null);
		pnlImages.get(idx).setImage(img);
		this.pack();
		this.repaint();
	}
	public void setImage(BufferedImage img){
		setImage(0,img);
	}
	
	public void addImage(BufferedImage img){
		final int imageIdx=pnlImages.size();
		
		final ImagePanel newImage=new ImagePanel();
		newImage.setImage(img);
		pnlImages.add(newImage);
		pnlMain.add(newImage);
		
		this.pack();
		this.repaint();
	}
	
	public void setQuitWhenClosed(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static interface ClickListener{
		public void pointClicked(int imageIdx,int x,int y);
	}
	private List<ClickListener> listeners=new ArrayList<ClickListener>();
	public void addListener(ClickListener listener){
		synchronized(listeners){
			listeners.add(listener);
		}
	}
	
}
