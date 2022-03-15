import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class LaunchPage implements ActionListener{
	
	JFrame frame = new JFrame();
	JButton myButton1 = new JButton("Normal");
	JButton myButton2 = new JButton("Apex");
	
	LaunchPage(){
		
		myButton1.setBounds(100,100,200,40);
		myButton1.setFocusable(false);
		myButton1.addActionListener(this);
		myButton2.setBounds(100,200,200,40);
		myButton2.setFocusable(false);
		myButton2.addActionListener(this);
		
		frame.setTitle("Main menu");
		frame.add(myButton1);
		frame.add(myButton2);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(420,420);
		frame.setLayout(null);
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==myButton1) {
			//frame.dispose();//ボタンが有るフレームが終了した
			myButton1.setEnabled(false);//ボタンを無効
			myButton2.setEnabled(false);
			new MyFrame(1);
		}
		if(e.getSource()==myButton2) {
			myButton1.setEnabled(false);
			myButton2.setEnabled(false);//ボタンを無効
		  	new MyFrame(2);
		}
	}
}
