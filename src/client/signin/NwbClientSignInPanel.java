package client.signin;
import java.awt.*;
import java.net.URL;
import javax.swing.*;

public class NwbClientSignInPanel extends JPanel{
	private Image img;
	public NwbClientSignInPanel(){
		super();
		URL url = getClass().getResource("resource/cavans.jpg");
		img = new ImageIcon(url).getImage();
	}
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(img,0,0,this);
	}
}
