package util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Dictionary;
import java.util.Enumeration;

/**
 * @author huirong wang
 * 2023.3 assignment for COMP30540 Game Development
 */
public class GameUtil {
	
	private static final GameUtil instance = new GameUtil();
	private int windowHeight=480;
	private int windowWidth=640;

	public int getWindowHeight()
	{
		return windowHeight;
	}

	public int getWindowWidth()
	{
		return windowWidth;
	}

	public int[] getGridSize()
	{
		return new int[]{32,32};
	}

	public String getMap(int level)
	{
		return "res/file/map"+ level +".txt";
	}

	public String getLine()
	{
		return "res/file/line.txt";
	}

	public String getBossLine()
	{
		return "res/file/bossline.txt";
	}

	public String getPath(String name)
	{
		return "res/"+ name +".png";
	}

	public String getBgPath(String name)
	{
		return "res/bg_"+ name +".png";
	}

	public String getItemPath(String name)
	{
		return "res/item/"+ name +".png";
	}

	public String getPlayerPath(int player)
	{
		return "res/witch"+player+".png";
	}

	public String getGrassPath(String type)
	{
		return "res/grass/"+type +".png";
	}
	public String getHeartPath(boolean bool)
	{
		return bool?"res/heart/0.png":"res/heart/1.png";
	}

	public String getSave()
	{
		return "res/file/save.txt";
	}

	public String getHistory()
	{
		return "res/file/history.txt";
	}

	public String getMusic(String name)
	{
		return "res/music/"+name+".wav";
	}
	public Image getGrassImage(String type) throws IOException {
		File TextureToLoad = new File(getGrassPath(type));  //should work okay on OSX and Linux but check if you have issues depending your eclipse install or if your running this without an IDE
		try {
			return ImageIO.read(TextureToLoad);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static GameUtil getInstance() {
		// TODO Auto-generated method stub
		return instance;
	}

}
