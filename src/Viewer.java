import com.journaldev.design.observer.EventType;
import com.journaldev.design.observer.Observer;
import com.journaldev.design.observer.Subject;
import object.*;
import util.GameUtil;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/*
 * Created by Abraham Campbell on 15/01/2020.
 *   Copyright (c) 2020  Abraham Campbell

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
   
   (MIT LICENSE ) e.g do what you want with this :-) 
 
 * Credits: Kelly Charles (2020)
 */

/*
// Tutorial //
Observer Design Pattern in Java
https://www.digitalocean.com/community/tutorials/observer-design-pattern-in-java
*/
public class Viewer extends JPanel implements Observer {
	private long CurrentAnimationTime= 0;
	private static Model model;
	private GameUtil gameUtil = GameUtil.getInstance();

	private Image grass_L;
	private Image grass_T;
	private Image grass_R;
	private Image grass_Img;

	public Viewer(Model world) throws IOException {
		model = world;
		subject = world;
		grass_L = gameUtil.getGrassImage("grass_L");
		grass_T = gameUtil.getGrassImage("grass_T");
		grass_R = gameUtil.getGrassImage("grass_R");
		grass_Img = gameUtil.getGrassImage("grass");
	}

	public Viewer(LayoutManager layout) {
		super(layout);
		// TODO Auto-generated constructor stub
	}

	public Viewer(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public Viewer(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public void updateview() {
		this.repaint();
		// TODO Auto-generated method stub
	}
	
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		if(showGameEnd)
		{
			drawGameEnd(g);
			return;
		}
		CurrentAnimationTime++; // runs animation time step

		drawBackground(g);
		model.getPlayerList().forEach((temp) -> {drawPlayer(temp,g);});
		model.getEnemies().forEach((temp) -> {drawEnemies(temp,g);});
		model.getBullets().forEach((temp) -> {drawBullet(temp,g);});
        model.getItems().forEach((temp) -> {drawItems(temp,g);});
		model.getGrass().forEach((temp) -> {drawGrass(temp,g);});

		drawUI(g);

		Gate gate = model.getGate();
		if(gate!=null && (int)gate.getCentre().getX()<gameUtil.getWindowWidth())
		{
			drawGate(gate,g);
		}
		Boss boss = model.getBoss();
		if(boss!=null && (int)boss.getCentre().getX()<gameUtil.getWindowWidth())
		{
			drawBoss(boss,g);
		}

		if(showTips)
		{
			if(model.getHitEnemy()!=null) {
				drawTips(model.getHitEnemy(),g);
			}
		}
		if(showBossTips)
		{
			if(model.getBoss()!=null) {
				drawBossTips(g);
			}
		}
	}


	private void drawEnemies(Enemy enemy,Graphics g) {
		String texture = enemy.getTexture();
		File TextureToLoad = new File(texture);  //should work okay on OSX and Linux but check if you have issues depending your eclipse install or if your running this without an IDE 
		try {
			Image myImage = ImageIO.read(TextureToLoad);
			int x = (int)enemy.getCentre().getX();
			int y = (int)enemy.getCentre().getY();
			int w = enemy.getWidth();
			int h = enemy.getHeight();
			int animationNumber = (int)(CurrentAnimationTime%30)/10;
			g.drawImage(myImage, x,y, x+w, y+h, animationNumber*w,
					0, (animationNumber+1)*w, h, null);

			if(enemy.isHasTip())
			{
				File TextureTipToLoad = new File(enemy.getTipTextureLocation());
				myImage = ImageIO.read(TextureTipToLoad);
				g.drawImage(myImage, x+w,y-h, x+2*w, y, 0,
						0, w, h, null);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    private void drawItems(Item item, Graphics g) {
		String texture = item.getTexture();
        File TextureToLoad = new File(texture);  //should work okay on OSX and Linux but check if you have issues depending your eclipse install or if your running this without an IDE
        try {
            Image myImage = ImageIO.read(TextureToLoad);
			int x = (int)item.getCentre().getX();
			int y = (int)item.getCentre().getY();
			int w = item.getWidth();
			int h = item.getHeight();
            g.drawImage(myImage, x,y, w, h,null);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

	private void drawGrass(Grass grass,Graphics g) {
//		String texture = grass.getTexture();
//		File TextureToLoad = new File(texture);  //should work okay on OSX and Linux but check if you have issues depending your eclipse install or if your running this without an IDE
		//			Image myImage = ImageIO.read(TextureToLoad);
		int x = (int)grass.getCentre().getX();
		int y = (int)grass.getCentre().getY();
		int w = grass.getWidth();
		int h = grass.getHeight();
		switch(grass.getTag())
		{
			case grass_L -> g.drawImage(grass_L, x,y, w, h,null);
			case grass_R -> g.drawImage(grass_R, x,y, w, h,null);
			case grass_T -> g.drawImage(grass_T, x,y, w, h,null);
			case grass -> g.drawImage(grass_Img, x,y, w, h,null);
		}

	}

	private void drawBullet(Bullet bullet, Graphics g)
	{
		String texture = bullet.getTexture();
		File TextureToLoad = new File(texture);  //should work okay on OSX and Linux but check if you have issues depending your eclipse install or if your running this without an IDE
		try {
			Image myImage = ImageIO.read(TextureToLoad);
			int x = (int)bullet.getCentre().getX();
			int y = (int)bullet.getCentre().getY();
			int w = bullet.getWidth();
			int h = bullet.getHeight();
			 g.drawImage(myImage, x,y, x+w, y+h, 0 , 0, w, h, null);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	private void drawPlayer(Player p, Graphics g) {
		String texture = p.getTexture();
		File TextureToLoad = new File(texture);  //should work okay on OSX and Linux but check if you have issues depending your eclipse install or if your running this without an IDE 
		try {
			Image myImage = ImageIO.read(TextureToLoad);
			int x = (int) p.getCentre().getX();
			int y = (int) p.getCentre().getY();
			int w = p.getWidth();
			int h = p.getHeight();
			int animationNumber = (int)(CurrentAnimationTime%30)/10;
			g.drawImage(myImage, x,y, x+w, y+h, animationNumber*w,
					0, (animationNumber+1)*w, h, null);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void drawBackground(Graphics g)
	{
		File TextureToLoad = new File(gameUtil.getBgPath(Integer. toString(model.getLevel())));
		//should work okay on OSX and Linux but check if you have issues depending your eclipse install or if your running this without an IDE
		try {
			Image myImage = ImageIO.read(TextureToLoad);
			g.drawImage(myImage, 0,0,gameUtil.getWindowWidth() , gameUtil.getWindowHeight(), null);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void drawUI(Graphics g)
	{
		File heartT = new File(gameUtil.getHeartPath(true));
		File heartF = new File(gameUtil.getHeartPath(false));
		int life = model.getLife()[0];
		//should work okay on OSX and Linux but check if you have issues depending your eclipse install or if your running this without an IDE
		try {
			for(int i=0; i<3; i++)
			{
				Image myImage = ImageIO.read((i>life-1)?heartF:heartT);
				int x = i*25;
				int y = 0;
				g.drawImage(myImage, x, y, 25, 25, null);
			}

			life = model.getLife()[1];
			for(int i=0; i<3; i++)
			{
				Image myImage = ImageIO.read((i>life-1)?heartF:heartT);
				int x = gameUtil.getWindowWidth()+(i-4)*25;
				int y = 0;
				g.drawImage(myImage, x, y, 25, 25, null);
			}

			g.drawString("Player 1 Score =  "+ model.getScore()[0],6,40);
			g.drawString("Player 2 Score =  "+ model.getScore()[1],gameUtil.getWindowWidth()-130,40);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void drawGate(Gate gate,Graphics g)
	{
		File file = new File(gate.getTexture());
		//should work okay on OSX and Linux but check if you have issues depending your eclipse install or if your running this without an IDE
		try {
			Image myImage = ImageIO.read(file);
			int x = (int) gate.getCentre().getX();
			int y = (int) gate.getCentre().getY();
			int w = gate.getWidth();
			int h = gate.getHeight();
			int animationNumber = (int) (CurrentAnimationTime % 20) / 10;
			g.drawImage(myImage, x, y, x + w, y + h, animationNumber * w,
					0, (animationNumber + 1) * w, h, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void drawBoss(Boss boss,Graphics g)
	{
		File file = new File(boss.getTexture());
		//should work okay on OSX and Linux but check if you have issues depending your eclipse install or if your running this without an IDE
		try {
			Image myImage = ImageIO.read(file);
			int x = (int) boss.getCentre().getX();
			int y = (int) boss.getCentre().getY();
			int w = boss.getWidth();
			int h = boss.getHeight();
			g.drawImage(myImage, x, y, x + w, y + h, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void drawTips(Enemy enemy,Graphics g)
    {
        File file = new File(gameUtil.getPath("dialog"));
        String line = enemy.getLine();
        try {
			Image myImage = ImageIO.read(file);
			g.drawImage(myImage, 0, 0, gameUtil.getWindowWidth(), gameUtil.getWindowHeight(), null);
			g.drawString(line,60,350);
            g.drawString("press Space to continue",230,430);
		} catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

	private void drawBossTips(Graphics g)
	{
		File dialog = new File(gameUtil.getPath("boss_dialog"));
		File princess = new File(gameUtil.getPath("princess"));
		Boss boss = model.getBoss();
		try {
			Image myImage = ImageIO.read(dialog);
			g.drawImage(myImage, 0, 0, gameUtil.getWindowWidth(), gameUtil.getWindowHeight(), null);

			Image princessImg = ImageIO.read(princess);
			int animationNumber = (int)(CurrentAnimationTime%30)/10;
			g.drawImage(princessImg, 280,120, 328, 184, animationNumber*48,
					0, (animationNumber+1)*48, 64, null);
			g.drawString(boss.getLine(),60,350);
			g.drawString("Press Space to continue",230,430);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void drawGameEnd(Graphics g)
	{
		File TextureToLoad = new File(gameUtil.getBgPath("win"));
		//should work okay on OSX and Linux but check if you have issues depending your eclipse install or if your running this without an IDE
		try {
			Image myImage = ImageIO.read(TextureToLoad);
			g.drawImage(myImage, 0,0,gameUtil.getWindowWidth() , gameUtil.getWindowHeight(), null);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		g.drawString("Player 1 Score =  "+ model.getScore()[0],240,320);
		g.drawString("Player 2 Score =  "+ model.getScore()[1],240,340);
		g.drawString("Press Alt to continue",240,430);
	}


	private Subject subject;
	@Override
	public void update() {
		EventType msg = (EventType) subject.getUpdate(this);
		//System.out.println("VIEWER RECIEVE MSG:" + msg.toString());
		switch(msg)
		{
			case GAME_WIN -> GameWin();
			case SHOW_TIP -> showTips=true;
			case CLOSE_TIP -> showTips=false;
			case HIT_BOSS -> showBossTips=true;
			case GO_MENU -> Reset();
		}
	}

	void GameWin()
	{
		showGameEnd=true;
		showBossTips=false;
	}

	void Reset()
	{
		showGameEnd=false;
		showBossTips=false;
	}

	private boolean showTips;
	private boolean showGameEnd;
	private boolean showBossTips;
	@Override
	public void setSubject(Subject sub) {
		subject = model;
	}
}


/*
 * 
 * 
 *              VIEWER HMD into the world                                                             
                                                                                
                                      .                                         
                                         .                                      
                                             .  ..                              
                               .........~++++.. .  .                            
                 .   . ....,++??+++?+??+++?++?7ZZ7..   .                        
         .   . . .+?+???++++???D7I????Z8Z8N8MD7I?=+O$..                         
      .. ........ZOZZ$7ZZNZZDNODDOMMMMND8$$77I??I?+?+=O .     .                 
      .. ...7$OZZ?788DDNDDDDD8ZZ7$$$7I7III7??I?????+++=+~.                      
       ...8OZII?III7II77777I$I7II???7I??+?I?I?+?+IDNN8??++=...                  
     ....OOIIIII????II?I??II?I????I?????=?+Z88O77ZZO8888OO?++,......            
      ..OZI7III??II??I??I?7ODM8NN8O8OZO8DDDDDDDDD8DDDDDDDDNNNOZ= ......   ..    
     ..OZI?II7I?????+????+IIO8O8DDDDD8DNMMNNNNNDDNNDDDNDDNNNNNNDD$,.........    
      ,ZII77II?III??????DO8DDD8DNNNNNDDMDDDDDNNDDDNNNDNNNNDNNNNDDNDD+.......   .
      7Z??II7??II??I??IOMDDNMNNNNNDDDDDMDDDDNDDNNNNNDNNNNDNNDMNNNNNDDD,......   
 .  ..IZ??IIIII777?I?8NNNNNNNNNDDDDDDDDNDDDDDNNMMMDNDMMNNDNNDMNNNNNNDDDD.....   
      .$???I7IIIIIIINNNNNNNNNNNDDNDDDDDD8DDDDNM888888888DNNNNNNDNNNNNNDDO.....  
       $+??IIII?II?NNNNNMMMMMDN8DNNNDDDDZDDNN?D88I==INNDDDNNDNMNNMNNNNND8:..... 
   ....$+??III??I+NNNNNMMM88D88D88888DDDZDDMND88==+=NNNNMDDNNNNNNMMNNNNND8......
.......8=+????III8NNNNMMMDD8I=~+ONN8D8NDODNMN8DNDNNNNNNNM8DNNNNNNMNNNNDDD8..... 
. ......O=??IIIIIMNNNMMMDDD?+=?ONNNN888NMDDM88MNNNNNNNNNMDDNNNMNNNMMNDNND8......
........,+++???IINNNNNMMDDMDNMNDNMNNM8ONMDDM88NNNNNN+==ND8NNNDMNMNNNNNDDD8......
......,,,:++??I?ONNNNNMDDDMNNNNNNNNMM88NMDDNN88MNDN==~MD8DNNNNNMNMNNNDND8O......
....,,,,:::+??IIONNNNNNNDDMNNNNNO+?MN88DN8DDD888DNMMM888DNDNNNNMMMNNDDDD8,.... .
...,,,,::::~+?+?NNNNNNNMD8DNNN++++MNO8D88NNMODD8O88888DDDDDDNNMMMNNNDDD8........
..,,,,:::~~~=+??MNNNNNNNND88MNMMMD888NNNNNNNMODDDDDDDDND8DDDNNNNNNDDD8,.........
..,,,,:::~~~=++?NMNNNNNNND8888888O8DNNNNNNMMMNDDDDDDNMMNDDDOO+~~::,,,.......... 
..,,,:::~~~~==+?NNNDDNDNDDNDDDDDDDDNNND88OOZZ$8DDMNDZNZDZ7I?++~::,,,............
..,,,::::~~~~==7DDNNDDD8DDDDDDDD8DD888OOOZZ$$$7777OOZZZ$7I?++=~~:,,,.........   
..,,,,::::~~~~=+8NNNNNDDDMMMNNNNNDOOOOZZZ$$$77777777777II?++==~::,,,......  . ..
...,,,,::::~~~~=I8DNNN8DDNZOM$ZDOOZZZZ$$$7777IIIIIIIII???++==~~::,,........  .  
....,,,,:::::~~~~+=++?I$$ZZOZZZZZ$$$$$777IIII?????????+++==~~:::,,,...... ..    
.....,,,,:::::~~~~~==+?II777$$$$77777IIII????+++++++=====~~~:::,,,........      
......,,,,,:::::~~~~==++??IIIIIIIII?????++++=======~~~~~~:::,,,,,,.......       
.......,,,,,,,::::~~~~==+++???????+++++=====~~~~~~::::::::,,,,,..........       
.........,,,,,,,,::::~~~======+======~~~~~~:::::::::,,,,,,,,............        
  .........,.,,,,,,,,::::~~~~~~~~~~:::::::::,,,,,,,,,,,...............          
   ..........,..,,,,,,,,,,::::::::::,,,,,,,,,.,....................             
     .................,,,,,,,,,,,,,,,,.......................                   
       .................................................                        
           ....................................                                 
               ....................   .                                         
                                                                                
                                                                                
                                                                 GlassGiant.com
                                                                 */
