import java.awt.*;
import java.awt.event.*; 
import javax.swing.*;
import java.util.Random;

public class GamePanel1 extends JPanel implements ActionListener {
	
	static final int SCREEN_WIDTH = 1022;
	static final int SCREEN_HEIGHT = 219;
	static final int EXTRA_SPACE = 581;
	static final int UNIT_SIZE = 73;
	static final int VERTICAL_ADJUSTMENT = EXTRA_SPACE/2;
	
	static int DELAY = 70;
	static int DIFFICULTY=25;
	int obstaclesAvoided;
	
	boolean running = false;
	Timer timer;
	Random random = new Random();
	
	int lane =0;
	int ObsLn[] = new int[2];

	int x[] = new int[2];
	int y[] = new int[2];
	int obstacleX[] = new int[2];
	int obstacleY[] = new int[2];
	
	JLabel[] lbls = {
		new JLabel(""),
		new JLabel("Controls : "),
		new JLabel("<UP> <DOWN> : Move Car"),
		new JLabel("<W> <S> : Move Car"),
		new JLabel("<ESC> : Exit")
	};

	int[][] lblBounds = {
		{ 10, 	VERTICAL_ADJUSTMENT+SCREEN_HEIGHT+10, 	SCREEN_WIDTH-10, 	60 },
		{ 10, 	VERTICAL_ADJUSTMENT-130, 				SCREEN_WIDTH-10, 	60 },
		{185, 	VERTICAL_ADJUSTMENT-120, 				SCREEN_WIDTH-30, 	40 },
		{185, 	VERTICAL_ADJUSTMENT-80, 				SCREEN_WIDTH-30, 	40 },
		{185, 	VERTICAL_ADJUSTMENT-40, 				SCREEN_WIDTH-30, 	40 }
	};

	JLabel 	BGImg = new JLabel("",new ImageIcon(getClass().getResource("/imgs/GameBG.jpg")),JLabel.CENTER),
			RoadImg = new JLabel("",new ImageIcon(getClass().getResource("/imgs/road_1.gif")),JLabel.CENTER),
			CarImg = new JLabel("",new ImageIcon(getClass().getResource("/imgs/car_9.png")),JLabel.CENTER),
			Obs1Img = new JLabel("",new ImageIcon(getClass().getResource("/imgs/obs_1.png")),JLabel.CENTER),
			Obs2Img = new JLabel("",new ImageIcon(getClass().getResource("/imgs/obs_1.png")),JLabel.CENTER),
			GameOverImg = new JLabel("",new ImageIcon(getClass().getResource("/imgs/GameOver_3.gif")),JLabel.CENTER);

	public JLabel[] imgs = { BGImg, RoadImg, CarImg, Obs1Img, Obs2Img, GameOverImg };

	int[][] imgBounds = {
		{	0,				0,						SCREEN_WIDTH,	(SCREEN_HEIGHT+EXTRA_SPACE)	}, //BG
		{	0, 				VERTICAL_ADJUSTMENT, 	SCREEN_WIDTH,	SCREEN_HEIGHT 				}, //Road
		{	x[0], 			y[0],					UNIT_SIZE*2, 	UNIT_SIZE					}, //Car
		{	obstacleX[0], 	obstacleY[0],			UNIT_SIZE, 		UNIT_SIZE					}, //Obs1
		{	obstacleX[1], 	obstacleY[1],			UNIT_SIZE, 		UNIT_SIZE					}, //Obs2
		{  	0,				VERTICAL_ADJUSTMENT, 	SCREEN_WIDTH, 	SCREEN_HEIGHT 				}  //GameOver
	};
	
	public JPanel[] panels = {
		new JPanel(),
		new JPanel(),
		new JPanel()
	};
	
	GamePanel1() {
		
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,(SCREEN_HEIGHT+EXTRA_SPACE)));
		this.setLayout(null);
		this.add(BGImg);

		BGImg.add(RoadImg);
		BGImg.add(GameOverImg);

		//set bounds of all images
		for (int i = 0;i<imgs.length;i++) {
			imgs[i].setBounds(imgBounds[i][0],imgBounds[i][1],imgBounds[i][2],imgBounds[i][3]);
		}

		panels[0].setSize(UNIT_SIZE*2, UNIT_SIZE);
		panels[0].setLocation(0,0);

		for(int i = 0;i<panels.length;i++) {
			if (i!=0) {
				panels[i].setSize(UNIT_SIZE, UNIT_SIZE);
				panels[i].setLocation(obstacleX[i-1], obstacleY[i-1]);
			}
			panels[i].setOpaque(false);
			panels[i].add(imgs[i+2]);
			RoadImg.add(panels[i]);
		}
		
		for (int i = 0; i<lbls.length;i++) {
			lbls[i].setFont(new Font("Bahnschrift", Font.PLAIN, 35));
			lbls[i].setForeground(new Color(255, 255, 255));
			lbls[i].setBounds(lblBounds[i][0],lblBounds[i][1],lblBounds[i][2],lblBounds[i][3]);
			BGImg.add(lbls[i]);
		}

		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}
	
	public void actionPerformed(ActionEvent e) {
		if(running) {
			move();
			checkCollision();
		}
		repaint();
	}
	
	public void startGame() {
		newObstacle();
		running = true;
		timer = new Timer(DELAY,this);
		timer.start();
	}
	
	public void newObstacle() {
		for(int i = 0;i<obstacleX.length;i++) {
			obstacleX[i] = SCREEN_WIDTH;
			ObsLn[i] = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE));
			obstacleY[i] = ObsLn[i]*UNIT_SIZE;
		}
	}
	
	public void move() {
		obstacleX[0]= obstacleX[0]-UNIT_SIZE;
		obstacleX[1]= obstacleX[1]-UNIT_SIZE;
		panels[1].setLocation(obstacleX[0],obstacleY[0]);
		panels[2].setLocation(obstacleX[1],obstacleY[1]);
	}
	
	public void checkCollision() {
		for(int i = 0;i<obstacleX.length;i++) {
			if((x[1] == obstacleX[i]) && (ObsLn[i]==lane)) {
				running = false;
			}
		}
		
		if(!running) {
			timer.stop();
		}
		
		if(obstacleX[0] < 0) {
			newObstacle();
			obstaclesAvoided+=2;
			if(obstaclesAvoided>=DIFFICULTY && DELAY>40) {
				DELAY=DELAY-5;
				DIFFICULTY=DIFFICULTY+20;
				timer.setDelay(DELAY);
			}
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	public void draw(Graphics g) {
		if(running) {			
			CarImg.setBounds(x[0], y[0],UNIT_SIZE*2, UNIT_SIZE);
			panels[0].add(CarImg);
			
			lbls[0].setText("Score : "+obstaclesAvoided);
		}
		else {
			gameOver(g);
		}
	}

	public void gameOver(Graphics g) {
		lbls[0].setText("Score : "+obstaclesAvoided);
		BGImg.remove(RoadImg);
	}
	
	public class MyKeyAdapter extends KeyAdapter {
		public void keyPressed(KeyEvent e)  {
			int x= panels[0].getX();
			int y= panels[0].getY();
			
			switch(e.getKeyCode()) {
				case KeyEvent.VK_W:
				case KeyEvent.VK_UP:
					if(y > 0) {
						panels[0].setLocation(x,y-UNIT_SIZE);
						lane--;
					}
					break;
				case KeyEvent.VK_S:
				case KeyEvent.VK_DOWN:
					if(y < (SCREEN_HEIGHT-UNIT_SIZE)) {
						panels[0].setLocation(x,y+UNIT_SIZE);
						lane++;
					}
					break;
				case KeyEvent.VK_ESCAPE:
					System.exit(0);
			}
		}
	}
}
//Execution order : Opening Screen -> GameFrame -> GamePanel -> StartGame() -> newObstacle() -> move()  
//                  -> checkCollosion() -> paintComponent() -> draw() -> gameOver()