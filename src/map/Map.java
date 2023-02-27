package map;

import util.GameUtil;
import util.ObjectTag;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Map {
    public int [][] map=null;
    private GameUtil pathutil = GameUtil.getInstance();

    public ObjectTag getTag(int id)
    {
        switch (id) {
            case (1):
                return ObjectTag.grass_L;
            case (2):
                return ObjectTag.grass_R;
            case (3):
                return ObjectTag.grass_T;
            case (4):
                return ObjectTag.grass;
            case (5):
                return ObjectTag.frog;
            case (6):
                return ObjectTag.bat;
            case (7):
                return ObjectTag.ghost;
            case (8):
                return ObjectTag.skeleton;
            case (9):
                return ObjectTag.item;
        }
        return ObjectTag.object;
    }

    public  int[][] ReadMap(int level) throws Exception
    {
        ArrayList<String> stringList = new ArrayList<String>();

        FileInputStream fis = new FileInputStream(pathutil.getMap(level));
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);

        String str = br.readLine();
        while(str!=null)
        {
            stringList.add(str);
            str = br.readLine();
        }

        br.close();

        int row= stringList.size();
        int cloum=stringList.get(0).split(",").length;

        map = new int [row][cloum];

        for (int i = 0; i < row; i++) {
            String s= stringList.get(i);
            String [] values=s.split(",");
            for (int j = 0; j < cloum; j++) {
                map[i][j]=Integer.parseInt(values[j]);
            }
        }
        return map;
    }

}
