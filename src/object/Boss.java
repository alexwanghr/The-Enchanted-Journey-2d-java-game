package object;

import util.ObjectTag;
import util.Point3f;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Boss extends GameObject {
    private int punishscore;
    private ArrayList<String> stringList = new ArrayList<String>();
    private int currIndex = 0;

    public Boss(Point3f centre) throws Exception {
        hasTextured=true;
        this.width=280;
        this.height=240;
        this.centre =centre;
        this.life=3;
        this.tag = ObjectTag.boss;
        this.punishscore = 100;
        this.textureLocation = pathutil.getPath("boss");
        this.currIndex=-1;
        ReadBossLine();
    }

    public int getPunishscore(){return this.punishscore;}

    public void ReadBossLine() throws Exception
    {
        FileInputStream fis = new FileInputStream(pathutil.getBossLine());
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);

        String str = br.readLine();
        while(str!=null)
        {
            stringList.add(str);
            str = br.readLine();
        }
        br.close();
    }

    public boolean CheckHasNextLine()
    {
        return currIndex<stringList.size()-1;
    }
    public String getLine()
    {
        currIndex++;
        return this.stringList.get(currIndex);
    }

    public ArrayList<String> getStringList() {
        return stringList;
    }

    public int getLife()
    {
        return this.life;
    }
}
