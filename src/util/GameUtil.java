package util;

import java.util.Dictionary;
import java.util.Enumeration;

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
		return "res/map"+ level +".txt";
	}

	public String getBg(int level)
	{
		return "res/bg"+ level +".png";
	}

	public String getLine()
	{
		return "res/line.txt";
	}

	public String getBossLine()
	{
		return "res/bossline.txt";
	}

	public String getPath(String name)
	{
		return "res/"+ name +".png";
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
		return "res/save.txt";
	}

	public String getMusic(String name)
	{
		return "res/music/"+name+".wav";
	}
	public static GameUtil getInstance() {
		// TODO Auto-generated method stub
		return instance;
	}

}
