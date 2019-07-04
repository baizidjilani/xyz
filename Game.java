import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Image;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;


public class Game extends JFrame{
	
	private Image hoop, basketballIcon,ring;
	private GamePanel game;
	public Game(){
		super("Basketball Game");
		//hoop = new ImageIcon("basketring.png").getImage();
        //hoop = new ImageIcon("new.gif.gif").getImage();
		basketballIcon = new ImageIcon("basketball copy.png").getImage();
		ring = new ImageIcon("1.jpg").getImage();
		game = new GamePanel(basketballIcon, hoop,ring);
		initialize();
	}
	
	private void initialize(){
		
		setSize(1116,600);
		setLocationRelativeTo(null);
		setBackground(Color.blue);
		setResizable(true);
		addKeyListener(game);
		add(game);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setIconImage(basketballIcon);
		
		game.run();//game offically starts with this method
		
		
	}
}
