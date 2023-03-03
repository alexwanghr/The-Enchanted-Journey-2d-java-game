import object.Player;
import util.GameUtil;
import util.Point3f;

import java.io.*;
import java.util.ArrayList;

public class Save {

    Player playerOne;
    Player playerTwo;
    int level=1;
    private GameUtil pathutil = GameUtil.getInstance();
    private Model model;

    public Save() throws Exception {
        playerOne = new Player(pathutil.getPlayerPath(1),new Point3f(pathutil.getWindowWidth()/2-50,190,0));
        playerTwo = new Player(pathutil.getPlayerPath(2),new Point3f(pathutil.getWindowWidth()/2-50,210,0));
        ReadSave();
    }

    public void SaveGame(Model model)
    {
        playerOne = model.getPlayer(1);
        playerTwo = model.getPlayer(2);
        level = model.getLevel();
        if(level>3) level=3;
        String playerData = String.format("id:%d,life:%d,score:%d,level:%d",
                1,playerOne.getLife(),playerOne.getPlayerScore(),level);
        String str = playerData+"\n";
        playerData = String.format("id:%d,life:%d,score:%d,level:%d",
                2,playerTwo.getLife(),playerTwo.getPlayerScore(),level);
        str+=playerData;
        writeFile(str);
    }

    public void writeFile(String data)
    {
        try {
            File file = new File(pathutil.getSave());
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fileWriter);
            bw.write(data);
            bw.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void ReadSave() throws IOException {
        ArrayList<String> stringList = new ArrayList<String>();

        FileInputStream fis = new FileInputStream(pathutil.getSave());
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);

        String str = br.readLine();
        while(str!=null)
        {
            stringList.add(str);
            str = br.readLine();
        }

        br.close();

        int playerId=1;
        int playerLife=3;
        int playerScore=0;

        for (String s: stringList)
        {
            var dataList= s.split(",");
            for (String data: dataList)
            {
                var playerData = s.split(":");
                if(playerData[0]=="id") playerId=Integer.parseInt(playerData[1]);
                if(playerData[0]=="life") playerLife=Integer.parseInt(playerData[1]);
                if(playerData[0]=="score") playerScore=Integer.parseInt(playerData[1]);
                if(playerData[0]=="level") level=Integer.parseInt(playerData[1]);
                setPlayer(playerId,playerLife,playerScore);
            }
        }


    }

    void setPlayer(int id,int life, int playerScore)
    {
        if(id==1) {
            playerOne = new Player(pathutil.getPlayerPath(1),
                    new Point3f(pathutil.getWindowWidth() / 2-50, 190, 0)
                    , life, playerScore);
        }
        else
        {
            playerTwo = new Player(pathutil.getPlayerPath(2),
                    new Point3f(pathutil.getWindowWidth() / 2-50, 210, 0)
                    , life, playerScore);
        }
    }

    public int getLevel()
    {
        return this.level;
    }

    public Player getPlayerOne(){return playerOne;}
    public Player getPlayerTwo(){return playerTwo;}
}
