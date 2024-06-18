import javax.swing.*;
import java.awt.*;

public class GameFrame1 extends JFrame {
	
	GamePanel1 gamePanel;
	
	GameFrame1() {
		gamePanel = new GamePanel1();
		gamePanel.setLayout(null);
		setPreferredSize(new Dimension( 1022, 800));
		getContentPane().add(gamePanel);
		setTitle("Car");
		setResizable(false);
		pack();
		setVisible(true);
		setLocationRelativeTo(null);
	}
}