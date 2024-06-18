import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class OpeningScreen extends JFrame {

	private JPanel contentPane = new JPanel();;
	
	JButton[] btns = {
		new JButton("START"),
		new JButton("QUIT")
	};

	JLabel[] lbls = {
		new JLabel("Obstacle Avoidance Car Game"),
		new JLabel("MicroProject By"),
		new JLabel("Soham Metha"),
		new JLabel("",new ImageIcon(getClass().getResource("/imgs/OpeningScreenBG.jfif")),JLabel.CENTER),
	};

	int[][] lblBounds = {
		{	35, 	45, 	583, 	64 	},
		{	674, 	675, 	160, 	50	},
		{	844, 	713, 	130, 	40	},
		{	0, 		0, 		1010, 	835	}

	};

	int[] lblFontSize = {  30,  14,  12,  0  };
	
	public OpeningScreen() {
		setTitle("Opening Screen");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1022, 800);
		setLocationRelativeTo(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		btns[0].setForeground(new Color(255, 255, 255));
		btns[1].setForeground(new Color(255, 0, 0));
		btns[0].setBounds(85, 520, 143, 40);
		btns[1].setBounds(85, 583, 119, 40);

		for(int i = 0;i<btns.length;i++) {
			btns[i].setOpaque(false);
			btns[i].setContentAreaFilled(false);
			btns[i].setBorderPainted(false);
			btns[i].setFont(new Font("Impact", Font.PLAIN, 30));
			contentPane.add(btns[i]);
		}
		
		for (int i = 0; i<lbls.length;i++) {
			lbls[i].setFont(new Font("Ink Free", Font.BOLD, lblFontSize[i]));
			lbls[i].setForeground(new Color(255, 255, 255));
			lbls[i].setBounds(lblBounds[i][0],lblBounds[i][1],lblBounds[i][2],lblBounds[i][3]);
			contentPane.add(lbls[i]);
		}
		
		btns[0].addActionListener(new ActionListener()  {
			public void actionPerformed(ActionEvent e)  {
				new GameFrame1();
			}
		});
		
		btns[1].addActionListener(new ActionListener()  {
			public void actionPerformed(ActionEvent e)  {
				System.exit(0);
			}
		});
	}
	public static void main(String[] args)  {
		(new OpeningScreen()).setVisible(true);
	}

}