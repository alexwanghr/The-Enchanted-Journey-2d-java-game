import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.journaldev.design.observer.EventType;
import com.journaldev.design.observer.Observer;
import com.journaldev.design.observer.Subject;
import util.GameUtil;
import util.UnitTests;

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
 */ 

/*
// Tutorial //
Observer Design Pattern in Java
https://www.digitalocean.com/community/tutorials/observer-design-pattern-in-java
*/


public class MainWindow implements Observer {
	private Subject subject;

	@Override
	public void update() {
		EventType msg = (EventType) subject.getUpdate(this);
		switch(msg)
		{
			case GO_TO_MENU -> SetMenuPage();
			case GAME_OVER -> SetGameOverPage();
		}
	}

	@Override
	public void setSubject(Subject sub) {
		subject = model;
	}
	 private static JFrame frame = new JFrame("The Enchanted Journey");

	 private static Save save;
	 private static Model model;

	 static{
		try {
			save = new Save();
			model = new Model(save);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	 private static GameUtil gameUtil= new GameUtil();
	 private static Viewer viewer;

	static {
		try {
			viewer = new Viewer(model);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private KeyListener Controller =new Controller();
	 private int width;
	 private int height;
	 private static int TargetFPS = 100;
	 private static boolean startGame= false; 
	 private JLabel BackgroundImageForStartMenu;
	  
	public MainWindow() throws Exception {
		subject = model;
		model.setObservers(this);
		model.setObservers(viewer);
		width = gameUtil.getWindowWidth();
		height = gameUtil.getWindowHeight();
		  frame.setSize(width, height);  // you can customise this later and adapt it to change on size.
	      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //If exit // you can modify with your way of quitting , just is a template.
	      frame.setLayout(null);
	      frame.add(viewer);
	      viewer.setBounds(0, 0, width, height);
		  viewer.setBackground(new Color(255,255,255)); //white background  replaced by Space background but if you remove the background method this will draw a white screen
		  viewer.setVisible(false);   // this will become visible after you press the key.
		SetMenuPage();
	}

	public static void main(String[] args) throws Exception {
		MainWindow hello = new MainWindow();  //sets up environment 
		while(true)   //not nice but remember we do just want to keep looping till the end.  // this could be replaced by a thread but again we want to keep things simple 
		{ 
			//swing has timer class to help us time this but I'm writing my own, you can of course use the timer, but I want to set FPS and display it 
			int TimeBetweenFrames =  800 / TargetFPS;
			long FrameCheck = System.currentTimeMillis() + (long) TimeBetweenFrames;
			
			//wait till next time step
			Thread.sleep(TimeBetweenFrames);
			//while (FrameCheck > System.currentTimeMillis()){}

			if(startGame)
			{
				gameloop();
			}
			//UNIT test to see if framerate matches 
		    UnitTests.CheckFrameRate(System.currentTimeMillis(),FrameCheck, TargetFPS);
		}
	}

	//Basic Model-View-Controller pattern 
	private static void gameloop() throws Exception {
		// GAMELOOP
		// controller input  will happen on its own thread
		// So no need to call it explicitly
		// model update
		model.gamelogic();
		viewer.updateview();
	}

	void SetMenuPage()
	{
		startGame = false;
		viewer.setVisible(false);
		//loading background image
		File BackroundToLoad = new File(gameUtil.getPath("menu"));  //should work okay on OSX and Linux but check if you have issues depending your eclipse install or if your running this without an IDE
		try {
			BufferedImage myPicture = ImageIO.read(BackroundToLoad);
			BackgroundImageForStartMenu = new JLabel(new ImageIcon(myPicture));
			BackgroundImageForStartMenu.setSize(width,height);
			frame.add(BackgroundImageForStartMenu);
		}  catch (IOException e) {
			e.printStackTrace();
		}
		SetLevelButton();
		frame.setVisible(true);
	}

	void SetGameOverPage()
	{
		startGame = false;
		viewer.setVisible(false);

		File BackroundToLoad = new File(gameUtil.getPath("gameover"));  //should work okay on OSX and Linux but check if you have issues depending your eclipse install or if your running this without an IDE
		try {
			BufferedImage myPicture = ImageIO.read(BackroundToLoad);
			BackgroundImageForStartMenu = new JLabel(new ImageIcon(myPicture));
			BackgroundImageForStartMenu.setSize(width,height);
			frame.add(BackgroundImageForStartMenu);
		}  catch (IOException e) {
			e.printStackTrace();
		}

		JButton loadBtn = new JButton(save.getLevel()>1 ? "Continue" : "New Game");
		loadBtn.setBounds(260, 240, 120, 40);
		loadBtn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				loadBtn.setVisible(false);
				try {
					LevelBtnOnClick();
				} catch (Exception ex) {
					throw new RuntimeException(ex);
				}
			}});

		frame.add(loadBtn);
		frame.setVisible(true);
	}

	void SetLevelButton() {
		JButton loadBtn = new JButton(save.getLevel()>1 ? "Continue" : "New Game");
		loadBtn.setBounds(260, 240, 120, 40);
		JButton level1Btn = new JButton("LEVEL 1");
		level1Btn.setBounds(110, 300, 120, 40);
		JButton level2Btn = new JButton("LEVEL 2");
		level2Btn.setBounds(260, 300, 120, 40);
		JButton level3Btn = new JButton("LEVEL 3");
		level3Btn.setBounds(410, 300, 120, 40);
		loadBtn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				loadBtn.setVisible(false);
				level1Btn.setVisible(false);
				level2Btn.setVisible(false);
				level3Btn.setVisible(false);
				try {
					LevelBtnOnClick();
				} catch (Exception ex) {
					throw new RuntimeException(ex);
				}
			}});

		level1Btn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				if(save.getLevel()!=1)return;
				loadBtn.setVisible(false);
				level1Btn.setVisible(false);
				level2Btn.setVisible(false);
				level3Btn.setVisible(false);
				try {
					LevelBtnOnClick();
				} catch (Exception ex) {
					throw new RuntimeException(ex);
				}
			}});

		level2Btn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				if(save.getLevel()!=2)return;
				loadBtn.setVisible(false);
				level1Btn.setVisible(false);
				level2Btn.setVisible(false);
				level3Btn.setVisible(false);
				try {
					LevelBtnOnClick();
				} catch (Exception ex) {
					throw new RuntimeException(ex);
				}
			}});

		level3Btn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				if(save.getLevel()!=3)return;
				loadBtn.setVisible(false);
				level1Btn.setVisible(false);
				level2Btn.setVisible(false);
				level3Btn.setVisible(false);
				try {
					LevelBtnOnClick();
				} catch (Exception ex) {
					throw new RuntimeException(ex);
				}
			}});

		frame.add(loadBtn);
		frame.add(level1Btn);
		frame.add(level2Btn);
		frame.add(level3Btn);
	}

	void LevelBtnOnClick() {
		BackgroundImageForStartMenu.setVisible(false);
		viewer.setVisible(true);
		viewer.addKeyListener(Controller);    //adding the controller to the Canvas
		viewer.requestFocusInWindow();
		startGame=true;
	}
}

/*
 * 
 * 

Hand shake agreement 
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,=+++
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,,,,,,:::::,=+++????
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,,,,,,,,,,,,,,:++++????+??
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,:,:,,:,:,,,,,,,,,,,,,,,,,,,,++++++?+++++????
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,=++?+++++++++++??????
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,:,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,~+++?+++?++?++++++++++?????
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,:::,,,,,,,,,,,,,,,,,,,,,,,,,,,~+++++++++++++++????+++++++???????
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,:,,,,,,,,,,,,,,,,,,,,,,:===+=++++++++++++++++++++?+++????????????????
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,,,,,,,,,,,,,,,,,,~=~~~======++++++++++++++++++++++++++????????????????
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,::::,,,,,,=~.,,,,,,,+===~~~~~~====++++++++++++++++++++++++++++???????????????
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,:,,,,,~~.~??++~.,~~~~~======~=======++++++++++++++++++++++++++????????????????II
:::::::::::::::::::::::::::::::::::::::::::::::::::::::,:,,,,:=+++??=====~~~~~~====================+++++++++++++++++++++?????????????????III
:::::::::::::::::::::::::::::::::::::::::::::::::::,:,,,++~~~=+=~~~~~~==~~~::::~~==+++++++==++++++++++++++++++++++++++?????????????????IIIII
::::::::::::::::::::::::::::::::::::::::::::::::,:,,,:++++==+??+=======~~~~=~::~~===++=+??++++++++++++++++++++++++?????????????????I?IIIIIII
::::::::::::::::::::::::::::::::::::::::::::::::,,:+????+==??+++++?++====~~~~~:~~~++??+=+++++++++?++++++++++??+???????????????I?IIIIIIII7I77
::::::::::::::::::::::::::::::::::::::::::::,,,,+???????++?+?+++???7?++======~~+=====??+???++++++??+?+++???????????????????IIIIIIIIIIIIIII77
:::::::::::::::::::::::::::::::::::::::,,,,,,=??????IIII7???+?+II$Z77??+++?+=+++++=~==?++?+?++?????????????III?II?IIIIIIIIIIIIIIIIIIIIIIIIII
::::::::::::::::::::::::::::::,,,,,,~=======++++???III7$???+++++Z77ZDZI?????I?777I+~~+=7+?II??????????????IIIIIIIIIIIIIIIIIIIIII??=:,,,,,,,,
::::::::,:,:,,,,,,,:::~==+=++++++++++++=+=+++++++???I7$7I?+~~~I$I??++??I78DDDO$7?++==~I+7I7IIIIIIIIIIIIIIIIII777I?=:,,,,,,,,,,,,,,,,,,,,,,,,
++=++=++++++++++++++?+????+??????????+===+++++????I7$$ZZ$I+=~$7I???++++++===~~==7??++==7II?~,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
+++++++++++++?+++?++????????????IIIII?I+??I???????I7$ZOOZ7+=~7II?+++?II?I?+++=+=~~~7?++:,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
+?+++++????????????????I?I??I??IIIIIIII???II7II??I77$ZO8ZZ?~~7I?+==++?O7II??+??+=====.,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
?????????????III?II?????I?????IIIII???????II777IIII7$ZOO7?+~+7I?+=~~+???7NNN7II?+=+=++,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
????????????IIIIIIIIII?IIIIIIIIIIII????II?III7I7777$ZZOO7++=$77I???==+++????7ZDN87I??=~,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
IIII?II??IIIIIIIIIIIIIIIIIIIIIIIIIII???+??II7777II7$$OZZI?+$$$$77IIII?????????++=+.,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII?+++?IIIII7777$$$$$$7$$$$7IIII7I$IIIIII???I+=,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII???????IIIIII77I7777$7$$$II????I??I7Z87IIII?=,,,,,,,,,,,:,,::,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
777777777777777777777I7I777777777~,,,,,,,+77IIIIIIIIIII7II7$$$Z$?I????III???II?,,,,,,,,,,::,::::::::,,:,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
777777777777$77777777777+::::::::::::::,,,,,,,=7IIIII78ZI?II78$7++D7?7O777II??:,,,:,,,::::::::::::::,:,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
$$$$$$$$$$$$$77=:,:::::::::::::::::::::::::::,,7II$,,8ZZI++$8ZZ?+=ZI==IIII,+7:,,,,:::::::::::::::::,:::,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
$$$I~::::::::::::::::::::::::::::::::::::::::::II+,,,OOO7?$DOZII$I$I7=77?,,,,,,:::::::::::::::::::::,,,:,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
::::::::::::::::::::::::::::::::::::::::::::::::::::::+ZZ?,$ZZ$77ZZ$?,,,,,::::::::::::::::::::::::::,::::,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::I$:::::::::::::::::::::::::::::::::::::::::::,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,,,:,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,,,,,,,,,,,,,,,,,,,,,,,,,,,
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,,,,,,,,,,,,,,,,,,,,,,,,,,,
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,,,,,,,,,,,,,,,,,,,,,,,,,
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,,,,,,,,,,,,,,,,,,,,,,
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,,,,,,,,,,,,,,,,,,,,,,
                                                                                                                             GlassGiant.com
 * 
 * 
 */
