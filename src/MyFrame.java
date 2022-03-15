import javax.swing.JFrame;

public class MyFrame extends JFrame{
	
	final int PANEL_WIDTH = 782;
	final int PANEL_HEIGHT = 781;
	
	MotionPanel panel1;
	ApexPanel panel2;
	
	MyFrame(int num){
		
		this.setTitle("Main window");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		switch(num) {
			case(1): panel1 = new MotionPanel();
			this.add(panel1);
			break;
			case(2): panel2 = new ApexPanel();
			this.add(panel2);
			break;
		}
		
		this.pack();
		this.setResizable(false);
		this.setVisible(true);
	}
}
