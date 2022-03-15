import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class StaticPanel extends JPanel{
	final int PANEL_WIDTH = 782;
	final int PANEL_HEIGHT = 781;
	
	Image face_back;
	Image backgroundImage;
	
	public StaticPanel() {
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		this.setBackground(Color.white);

		face_back = new ImageIcon("face_back1.png").getImage();
		backgroundImage = new ImageIcon("îwåi.png").getImage();

	}
	
	public void paint(Graphics g) {

		super.paint(g);
		Graphics2D g2D = (Graphics2D) g;
		g2D.drawImage(backgroundImage, 0, 0, null);
		g2D.drawImage(face_back, 0, 0, null);

	}

}
