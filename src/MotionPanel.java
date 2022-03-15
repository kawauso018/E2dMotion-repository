import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.LogManager;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import org.jnativehook.mouse.NativeMouseMotionListener;

public class MotionPanel extends JPanel implements ActionListener, NativeMouseInputListener {

	final int PANEL_WIDTH = 782;
	final int PANEL_HEIGHT = 781;

	Image eye_left;
	Image eye_right;
	Image eyelids;
	Image eyelids_back;
	Image dot_left;
	Image dot_right;
	Image face_front;
	Image face_back;
	Image mouth;
	Image ear_right;
	Image ear_left;
	Image nose_back;
	Image nose;
	Image tan;
	Image backgroundImage;
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
	int y_mouth = -115;
	int mouth_count = 0;
	int y_eyelids = 0;
	int yVelocity_eyelids = 0;
	int eyelids_count = 0;
	int ear_angle = 0;
	int Velocity_ear = 0;
	
	Player m_CPlay = new Player();
    Recorder m_CRec = new Recorder();
    Thread t = new Thread();

	MotionPanel() {
		//グローバルフック開始
		if (!GlobalScreen.isNativeHookRegistered()) {
			try {
				GlobalScreen.registerNativeHook();
			} catch (NativeHookException e) {
				e.printStackTrace();
				System.exit(-1);
			}
		}
		// キー・リスナを登録
		GlobalScreen.addNativeMouseListener(this);
		GlobalScreen.addNativeMouseMotionListener(this);
		// ログを非表示
		LogManager.getLogManager().reset();
		
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		
		eye_left = new ImageIcon(Main.class.getResource("img/eye_left.png")).getImage();
		eye_right = new ImageIcon(Main.class.getResource("img/eye_right.png")).getImage();
		dot_left = new ImageIcon(Main.class.getResource("img/dot_left.png")).getImage();
		dot_right = new ImageIcon(Main.class.getResource("img/dot_right.png")).getImage();
		eyelids = new ImageIcon(Main.class.getResource("img/eyelids1.png")).getImage();
		eyelids_back = new ImageIcon(Main.class.getResource("img/eyelids_back.png")).getImage();
		ear_left = new ImageIcon(Main.class.getResource("img/ear_left.png")).getImage();
		ear_right = new ImageIcon(Main.class.getResource("img/ear_right.png")).getImage();
		nose_back = new ImageIcon(Main.class.getResource("img/nose_back.png")).getImage();
		nose = new ImageIcon(Main.class.getResource("img/nose.png")).getImage();
		tan = new ImageIcon(Main.class.getResource("img/tan.png")).getImage();
		mouth = new ImageIcon(Main.class.getResource("img/mouth.png")).getImage();
		face_front = new ImageIcon(Main.class.getResource("img/face_front1.png")).getImage();
		face_back = new ImageIcon(Main.class.getResource("img/face_back1.png")).getImage();
		backgroundImage = new ImageIcon(Main.class.getResource("img/背景.png")).getImage();
		
		timer = new Timer(10, this);// 10ミリ秒ごとにアクションを行う
		timer.start();
		
        // スレッド開始
        m_CPlay.start();
        m_CRec.start();
        t.start();
	}

	public void paint(Graphics g) {

		super.paint(g);
		Graphics2D g2D = (Graphics2D) g;
		AffineTransform at = g2D.getTransform();
		g2D.drawImage(backgroundImage, 0, 0, null);
		g2D.drawImage(face_back, 0, 0, null);
		g2D.drawImage(tan, 0, y_mouth, null);
		g2D.drawImage(mouth, 0, y_mouth, null);
		g2D.drawImage(face_front, 0, 0, null);
		g2D.drawImage(nose_back, 0, 0, null);
		g2D.drawImage(nose, 0, 0, null);
		at.setToRotation(-Math.toRadians(ear_angle), 226, 258);
		g2D.setTransform(at);
		g2D.drawImage(ear_left, 0, 0, null);
		at.setToRotation(Math.toRadians(ear_angle), 553, 258);
		g2D.setTransform(at);
		g2D.drawImage(ear_right, 0, 0, null);
		at.setToRotation(Math.toRadians(0), 0, 0);
		g2D.setTransform(at);
		g2D.drawImage(eye_left, 0, 0, null);
		g2D.drawImage(eye_right, 0, 0, null);
		g2D.drawImage(dot_left, x_eye, y_eye, null);
		g2D.drawImage(dot_right, x_eye, y_eye, null);
		g2D.drawImage(eyelids, 0, y_eyelids, null);
		g2D.drawImage(eyelids_back, 0, 0, null);
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
			//係数の決定
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
			//係数の決定
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
		
		if(yVelocity_eyelids < 0) {
			if(y_eyelids + yVelocity_eyelids < 0) {
				y_eyelids = 0;
			}else {
				y_eyelids += yVelocity_eyelids;
			}
			eyelids_count = 0;
		}else if(eyelids_count >= 50) {
			if(y_eyelids + yVelocity_eyelids > 32) {
				y_eyelids = 32;
			}else {
				y_eyelids += yVelocity_eyelids;
			}
		}else {
			eyelids_count++;
		}
		if(y_eye-y_eyelids <= -32) {
			y_eye = y_eyelids-32;
		}
		
		//音声の取得
		if(mouth_count == 15) {
			byte[] voice = m_CRec.getVoice();
			byte[] voice_denoise = Arrays.copyOf(voice, voice.length);
			voice_denoise = ArrayElement.Denoising(voice_denoise);
			int denoise_count = ArrayElement.Count(voice_denoise);
			if(denoise_count>=50) {
				if(denoise_count<=129) {
					y_mouth = -85;
				}else if(130 <=denoise_count && denoise_count<=150) {
					y_mouth = -80;
				}else if(151 <=denoise_count && denoise_count<=172) {
					y_mouth = -75;
				}else if(173 <=denoise_count && denoise_count<=193) {
					y_mouth = -70;
				}else if(194 <=denoise_count && denoise_count<=216) {
					y_mouth = -65;
				}else if(217 <=denoise_count && denoise_count<=239) {
					y_mouth = -60;
				}else if(240 <=denoise_count && denoise_count<=260) {
					y_mouth = -55;
				}else if(261 <=denoise_count && denoise_count<=281) {
					y_mouth = -50;
				}

			}else {
				y_mouth = - 115;
			}
			mouth_count = 0;
		}
		mouth_count++;

		//耳の回転
		Random random = new Random();
		int num_random = random.nextInt(232);
		if(num_random==231) {
			if(ear_angle >= 8) {
				Velocity_ear = 0;
			}else{
				Velocity_ear = 1;
			}
		}else {
			if(ear_angle<=0) {
				Velocity_ear = 0;
			}else if(ear_angle >= 8){
				Velocity_ear = -1;
			}
		}
		ear_angle += Velocity_ear;
		
		repaint();
	}

	@Override
	public void nativeMouseDragged(NativeMouseEvent arg0) {
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
		point_count++;
	}

	@Override
	public void nativeMouseMoved(NativeMouseEvent arg0) {
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
		point_count++;
	}

	@Override
	public void nativeMouseClicked(NativeMouseEvent arg0) {}

	@Override
	public void nativeMousePressed(NativeMouseEvent arg0) {
		if(arg0.getModifiers()==512 || arg0.getModifiers()==513) {
			yVelocity_eyelids = 2;
		}
	}

	@Override
	public void nativeMouseReleased(NativeMouseEvent arg0) {
		if(arg0.getModifiers()==0 || arg0.getModifiers()==1) {
			yVelocity_eyelids = -4;
		}
	}
}