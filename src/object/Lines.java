package object;
import util.GameUtil;
import util.ObjectTag;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class Lines {
    public String[] line=null;
    public ArrayList<String> stringList = new ArrayList<String>();
    private GameUtil pathutil = GameUtil.getInstance();

    public ArrayList<String> ReadLine() throws Exception
    {
        FileInputStream fis = new FileInputStream(pathutil.getLine());
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);

        String str = br.readLine();
        while(str!=null)
        {
            stringList.add(str);
            str = br.readLine();
        }

        br.close();

        return stringList;
    }

    public String getRandomLine()
    {
        if(stringList.size()>0)
        {
            int index = (int) (Math.random() * stringList.size());
            return stringList.get(index);
        }
        else
        {
            return "\"Don't be afraid of the dragon. She may look scary, but she has a kind heart.\"";
        }
    }

}
