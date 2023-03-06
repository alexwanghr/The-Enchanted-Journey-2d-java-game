package map;
import util.GameUtil;
import util.ObjectTag;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * @author huirong wang
 * 2023.3 assignment for COMP30540 Game Development
 */
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
            case (9):
                return ObjectTag.item;
            case (10):
                return ObjectTag.gate;
            case (11):
                return ObjectTag.boss;
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
        int cloum=stringList.get(0).split(",").length+100;

        map = new int [row][cloum];

        for (int i = 0; i < row; i++) {
            String s= stringList.get(i);
            String [] values=s.split(",");
            for (int j = 0; j < cloum; j++) {
                if(j<values.length) {
                    try {
                        map[i][j] = Integer.parseInt(values[j]);
                    } catch (NumberFormatException ex) {
                        ex.printStackTrace();
                    }
                }
                else
                {
                    map[i][j] = 0;
                }
            }
        }
        return map;
    }

}
