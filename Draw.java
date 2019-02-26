import javax.swing.JComponent;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class Draw extends JComponent{

	private BufferedImage backgroundImage;

	public Player player;

	public Random randomizer;

	//Monster monster1;
	//Monster monster2;
	//Monster monster3;
	//Monster monster4;
	public int enemyCount;
    Monster[] monsters = new Monster[10];
    public ArrayList<Monster> monsterlist = new ArrayList<>();

	public Draw(){
		randomizer = new Random();
		player = new Player(200, 200, this);

		spawnEnemy();

		//monster1 = new Monster(200, 200);
		//monster2 = new Monster(300, 200);
		//monster3 = new Monster(400, 200);
		//monster4 = new Monster(500, 200);

		try{
			backgroundImage = ImageIO.read(getClass().getResource("background.jpg"));
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	

    public void startGame(){
        Thread gameThread = new Thread(new Runnable(){
            public void run(){
                while(true){
                    try{
                        for(int c = 0; c < monsterlist.size(); c++){
                            if(monsterlist.size()!= 0){
                                monsterlist.get(c).moveTo(player.x, player.y);
                                repaint();
                            }
                            if(monsterlist.get(c).life <= 0){
                                monsterlist.remove(c);
                }
                        }
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                            e.printStackTrace();
                    }
                }
            }
        });
        gameThread.start();
    }

    public void spawnEnemy(){
        if(enemyCount < 10){
			monsters[enemyCount] = new Monster(randomizer.nextInt(500), randomizer.nextInt(500), this);
			enemyCount++;
        }
    }

    public void checkCollision(){
     
        for(int i=0; i < monsterlist.size(); i++){
            if(player.Attacking == true){
                if(player.playerBounds().intersects(monsterlist.get(i).monsterBounds())){
                    monsterlist.get(i).life -= 10;

                }
                        
            }
        }
    }
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.YELLOW);
		g.drawImage(backgroundImage, 0, 0, this);

		g.drawImage(player.image, player.x, player.y, this);

		//g.drawImage(monster1.image, monster1.xPos, monster1.yPos, this);
		//g.drawImage(monster2.image, monster2.xPos, monster2.yPos, this);
		//g.drawImage(monster3.image, monster3.xPos, monster3.yPos, this);
		//g.drawImage(monster4.image, monster4.xPos, monster4.yPos, this);
		for(int c = 0; c < monsterlist.size(); c++){
            if(monsterlist.size() != 0){
                g.drawImage(monsterlist.get(c).image, monsterlist.get(c).xPos, monsterlist.get(c).yPos, this);
                g.fillRect(monsterlist.get(c).xPos+7, monsterlist.get(c).yPos, monsterlist.get(c).life, 2);
            }
        }
    }
}
	
