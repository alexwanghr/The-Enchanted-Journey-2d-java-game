import object.*;
import util.GameUtil;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
public class Viewer extends JPanel{
	private long CurrentAnimationTime= 0;
	private static Model model;
	private GameUtil gameUtil = GameUtil.getInstance();
	 
	public Viewer(Model world) {
		model = world;
		// TODO Auto-generated constructor stub
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

	public void showTips(Enemy enemy)
	{
		String line = enemy.getLine();
		System.out.println(line);
	}
	
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		CurrentAnimationTime++; // runs animation time step

		drawBackground(g);
		drawPlayer(model.getPlayer(1),g);
		drawPlayer(model.getPlayer(2),g);

		model.getEnemies().forEach((temp) -> {drawEnemies(temp,g);});
		model.getBullets().forEach((temp) -> {drawBullet(temp,g);});
        model.getItems().forEach((temp) -> {drawItems(temp,g);});
		model.getGrass().forEach((temp) -> {drawGrass(temp,g);});

		drawUI(g);
		drawGate(g);
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
		String texture = grass.getTexture();
		File TextureToLoad = new File(texture);  //should work okay on OSX and Linux but check if you have issues depending your eclipse install or if your running this without an IDE
		try {
			Image myImage = ImageIO.read(TextureToLoad);
			int x = (int)grass.getCentre().getX();
			int y = (int)grass.getCentre().getY();
			int w = grass.getWidth();
			int h = grass.getHeight();
			g.drawImage(myImage, x,y, x+w, y+h, 0,
					0, w, h, null);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		File TextureToLoad = new File(gameUtil.getBg(model.getLevel()));
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
		int life = model.getPlayer(1).getLife();
		//should work okay on OSX and Linux but check if you have issues depending your eclipse install or if your running this without an IDE
		try {
			for(int i=0; i<3; i++)
			{
				Image myImage = ImageIO.read((i>life-1)?heartF:heartT);
				int x = i*25;
				int y = 0;
				g.drawImage(myImage, x, y, 25, 25, null);
			}

			life = model.getPlayer(2).getLife();
			for(int i=0; i<3; i++)
			{
				Image myImage = ImageIO.read((i>life-1)?heartF:heartT);
				int x = gameUtil.getWindowWidth()+(i-3)*25;
				int y = 0;
				g.drawImage(myImage, x, y, 25, 25, null);
			}

			g.drawString("Score1 =  "+ model.getScore()[0],0,30);
			g.drawString("Score2 =  "+ model.getScore()[1],gameUtil.getWindowWidth()-40,30);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void drawGate(Graphics g)
	{
		Gate gate = model.getGate();
		if(gate==null)
		{
			return;
		}
		File file = new File(gate.getTexture());
		//should work okay on OSX and Linux but check if you have issues depending your eclipse install or if your running this without an IDE
		try {
			Image myImage = ImageIO.read(file);
			int x = (int) gate.getCentre().getX();
			int y = (int) gate.getCentre().getY();
			if(x<gameUtil.getWindowWidth()) {
				int w = gate.getWidth();
				int h = gate.getHeight();
				g.drawImage(myImage, x, y, x + w, y + h, null);

				int animationNumber = (int) (CurrentAnimationTime % 40) / 10;
				g.drawImage(myImage, x, y, x + w, y + h, animationNumber * w,
						0, (animationNumber + 1) * w, h, null);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
