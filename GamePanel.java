import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JPanel;


public class GamePanel extends JPanel implements KeyListener{
	//fields
	private int x, i;
	private Image ball, hoop,ring;
	private int velX, velY;
	private int ballX, ballY;
	private int lives, scores, misses;
	private boolean MOVING, ENDOFGAME, RESTART; //flags 

	
	
	
	public GamePanel(Image ball, Image hoop,Image ring){
		this.ball = ball;
		this.hoop = hoop;
		this.ring = ring;
		ballX = 0; ballY = 400;
		velX = 5; velY = 10;
		MOVING = true; 
		scores = 0; misses = 0;
		lives = 6
		;
		setBackground(Color.WHITE);
		ENDOFGAME = false; RESTART = false;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		//draws the strings at the end of the game
		g.drawImage(ring,0,0,1100,600,null);//draws the background image
		g.drawImage(hoop, 480, 50 , 155, 155, null);  //draws the hoop image

		g.drawImage(ball, ballX, ballY, 55, 55, null); // draws the basketball
		/*for(int i = 0; i < lives; i++){
			g.drawImage(ball, 45*i, 55, 45, 45, null);
		}*/
		g.setFont(new Font("serif", Font.BOLD, 35)); //counter for scores
		g.setColor(Color.BLACK);
		g.drawString("LIVES:" + lives, 0, 67);
		g.setFont(new Font("serif", Font.BOLD, 45)); //counter for scores
		g.drawString("SCORES:" + scores, 0, 37);
		if(ENDOFGAME){
			g.setFont(new Font("Times New Roman", Font.BOLD, 32));
			g.drawString("SCORES: " + scores, 500, 400);
			g.setFont(new Font("serif", Font.LAYOUT_NO_START_CONTEXT, 35));
			g.drawString("PRESS R TO " , 480, 440);
			g.setFont(new Font("serif", Font.ROMAN_BASELINE, 40));
			g.drawString("PLAY AGAIN " , 462, 480);
		}

	}
	
	public boolean checkBall(){
		if(ballX >= 500 && ballX <= 595  && ballY < 80)
			return true;
		return false;
	}
	
	public void playSound(int x){
		//buzzer sound
		if(x == 1){
			try {
				Clip clip = AudioSystem.getClip();
				clip.open(AudioSystem.getAudioInputStream(new File("endOfGame.wav").getAbsoluteFile()));
				clip.start();
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		//swish sound
		else{
			try {
				Clip clip = AudioSystem.getClip();
				clip.open(AudioSystem.getAudioInputStream(new File("drop.wav").getAbsoluteFile()));
				clip.start();
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	public void vel()
	{int i=0;
		for(int j=1;j<100;j++)
		{
			ballY=ballY+i++;
			repaint();
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	//game engine. whole game runs in this method
	public void run(){	
		//loop for basketball moving side to side
		if(!ENDOFGAME)
		{
			try {
				Clip clip = AudioSystem.getClip();
				clip.open(AudioSystem.getAudioInputStream(new File("3.wav").getAbsoluteFile()));
				clip.start();
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		//for(i = 0; i <= (450/velX) * 2; i = i + 1){
		for(i = 0; true ; i = i + 1){
			//basketball has been shot
			if(!MOVING){ 
				for(int x = 0; x <= 415/velY; x++){
					//basketball is missed
					if(x == 3505/velY){
						vel();
						ballY=415;
						lives--;
						misses++;
						MOVING = !MOVING;
						break;
					}
					ballY = ballY - velY;
					repaint();
					try{
						Thread.sleep(20);
					} catch(InterruptedException e){
						e.printStackTrace();
					}
					//check for a made basket
					if(checkBall()){
						MOVING = !MOVING;
						playSound(-1);
						scores++;
						vel();
						try{
							Thread.sleep(50);
						} catch(InterruptedException e){
							e.printStackTrace();
						}
						velX = velX + 1;
						velY = velY + 1;
						i = 0;
						ballX = 0;
						ballY = 400;
						break;
					}
				}
			}
			if(i == (450/velX * 2)) // prevents ball for moving all the way past the rim
				i = 0;
			if(i < 450/velX) {
				ballX = ballX + (int) (velX * 2.4);
				repaint();
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			else{
				ballX = ballX - (int)(velX*2.4);
				repaint();
				try{
					Thread.sleep(20);
				} catch(InterruptedException e){
					e.printStackTrace();
				}
			}	
			
			if(lives == 0 && !ENDOFGAME){ //"holds" the loop temporarily so waiting for the player to restart the game
				playSound(1);
				ballX = -10000; //to make it seem like the ball disappears
				
				ENDOFGAME = true; //prevents the buzzer for sounding infinitely until the space bar is pressed
			}	
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		//for a restart
		if(ENDOFGAME){
			ballX = 0;
			ballY = 400;
			lives = 6;
			i = 0;
			velX = 5; velY = 10;
			scores = 0; misses = 0;
			ENDOFGAME = false;
			repaint();
			try{
				Thread.sleep(50);
			} catch(InterruptedException ex){
				ex.printStackTrace();
			}
			return;
			
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE){
			MOVING = !MOVING;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
}