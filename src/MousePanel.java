import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.LogManager;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseMotionListener;

public class MousePanel extends JPanel implements ActionListener, NativeMouseMotionListener {

	final int PANEL_WIDTH = 782;
	final int PANEL_HEIGHT = 781;
	
	Image eye_left;
	Image eye_right;
	Image dot_left;
	Image dot_right;
	
	Timer timer;
	
	int xVelocity = 0;
	int yVelocity = 0;
	int x = 0;
	int y = 0;
	int x_eye = 0;
	int y_eye = 0;
	int point_count = 0;
	double x_co = 0.5;
	double y_co = 0.5;
	
	public MousePanel() {
		if (!GlobalScreen.isNativeHookRegistered()) {
			try {
				// ÉtÉbÉNÇìoò^
				GlobalScreen.registerNativeHook();
			} catch (NativeHookException e) {
				e.printStackTrace();
				System.exit(-1);
			}
		}
		GlobalScreen.addNativeMouseMotionListener(this);
		LogManager.getLogManager().reset();
		
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		this.setBackground(Color.white);
		
		eye_left = new ImageIcon("eye_left.png").getImage();
		eye_right = new ImageIcon("eye_right.png").getImage();
		dot_left = new ImageIcon("dot_left.png").getImage();
		dot_right = new ImageIcon("dot_right.png").getImage();
		
		timer = new Timer(10, this);// 10É~ÉäïbÇ≤Ç∆Ç…ÉAÉNÉVÉáÉìÇçsÇ§
		timer.start();
	}
	
	public void paint(Graphics g) {

		super.paint(g);
		Graphics2D g2D = (Graphics2D) g;
		g2D.drawImage(eye_left, 0, 0, null);
		g2D.drawImage(eye_right, 0, 0, null);
		g2D.drawImage(dot_left, x_eye, y_eye, null);
		g2D.drawImage(dot_right, x_eye, y_eye, null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(xVelocity==0 && yVelocity==0){
			x_eye = (int) (x_eye * 0.9999);
			y_eye = (int) (y_eye * 0.9999);
		}
		
		int num = 25;
		if(x_eye+ xVelocity<-num) {
			x_eye = -num + 1;
			xVelocity=0;
		}else if(x_eye+ xVelocity>num){
			x_eye = num - 1;
			xVelocity=0;
		}else{
			x_eye = x_eye + xVelocity;
			//åWêîÇÃåàíË
			if(x_eye>=-25 && x_eye<-23 || x_eye<=25 && x_eye>23) {
				x_co = 1;
			}else if(x_eye>=-22 && x_eye<-10 || x_eye<=22 && x_eye>10) {
				x_co = 1.5;
			}else {
				x_co = 1.8;
			}
		}
		
		if(y_eye+ yVelocity<-num) {
			y_eye = -num + 1;
			yVelocity=0;
		}else if(y_eye+ yVelocity>num){
			y_eye = num - 1; 
			yVelocity=0;
		}else {
			y_eye = y_eye + yVelocity;
			//åWêîÇÃåàíË
			if(y_eye>=-25 && y_eye<-23 || y_eye<=25 && y_eye>23) {
				y_co = 1;
			}else if(y_eye>=-22 && y_eye<-10 || y_eye<=22 && y_eye>10) {
				y_co = 1.2;
			}else {
				y_co = 1.5;
			}
		}
		xVelocity = 0;
		yVelocity = 0;
		
		repaint();
	}

	@Override
	public void nativeMouseDragged(NativeMouseEvent arg0) {	}

	@Override
	public void nativeMouseMoved(NativeMouseEvent arg0) {
		if(x == 0 && y == 0) {
			x = arg0.getX();
			y = arg0.getY();
		}else {
			if(point_count == 10) {
				xVelocity = (int)((x - arg0.getX())*-x_co);
				if(xVelocity>40) {
					xVelocity = 4;
				}else if(xVelocity<-40) {
					xVelocity = -4;
				}else {
					xVelocity = xVelocity / 10;
				}
				yVelocity = (int)((y - arg0.getY())*-y_co);
				if(yVelocity>40) {
					yVelocity = 4;
				}else if(yVelocity<-40) {
					yVelocity = -4;
				}else {
					yVelocity = yVelocity / 10;
				}
				x = arg0.getX();
				y = arg0.getY();
				point_count = 0;
			}
		}
		point_count++;
		
	}
	
	
}
