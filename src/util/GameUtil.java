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

	public int getScore(String name)
	{
		switch(name)
		{
			case("item"):
				return 100;
			case("enemy"):
				return 50;
		}
		return 0;
	}

	public String getMap()
	{
		return "res/map.txt";
	}

	public String getLine()
	{
		return "res/line.txt";
	}

	public String getPath(String name)
	{
		return "res/"+ name +".png";
	}

	public String getPlayerPath(int player)
	{
		return "res/witch"+player+".png";
	}

	public String getMapGridPath(int type)
	{
		return "res/dirt/"+ "dirt"+type +".png";
	}

	public String getGrassPath(String type)
	{
		return "res/grass/"+type +".png";
	}

	public static GameUtil getInstance() {
		// TODO Auto-generated method stub
		return instance;
	}

}
