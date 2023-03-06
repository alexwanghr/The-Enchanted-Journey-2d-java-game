import util.GameUtil;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.*;
import java.util.ArrayList;

/**
 * @author huirong wang
 * 2023.3 assignment for COMP30540 Game Development
 */
class HistoryScore
{
    public int score;
    public String time;

    public HistoryScore(int score, String time)
    {
        this.score=score;
        this.time = time;
    }

    public int getScore()
    {
        return this.score;
    }

    @Override
    public String toString() {
        return String.format("score,"+score+";time,"+time);
    }

    public String displayString(int rank){
        return String.format("Rank%d     Score %d Achieve Time %s",rank, score,time);
    }
}
public class History {
    private GameUtil pathutil = GameUtil.getInstance();
    ArrayList<HistoryScore> historyList = new ArrayList<>();

    public History() throws IOException {
       ReadHistory();
    }

    public void SaveHistory(Model model)
    {
        Date now = new Date();
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        if(model.getPlayer(1).getLife()>0) {
            historyList.add(new HistoryScore(model.getPlayer(1).getPlayerScore(), df.format(now)));
        }
        if(model.getPlayer(2).getLife()>0) {
            historyList.add(new HistoryScore(model.getPlayer(2).getPlayerScore(), df.format(now)));
        }

        Sort();
        writeFile();
    }

    public void writeFile()
    {
        try {
            File file = new File(pathutil.getHistory());
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fileWriter);
            String str="";
            int count = Math.min(10,historyList.size());
            for(int i=0; i<count; i++)
            {
                str += historyList.get(i).toString()+"\n";
            }
            bw.write(str);
            bw.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void ReadHistory() throws IOException {
        ArrayList<String> stringList = new ArrayList<String>();
        FileInputStream fis = new FileInputStream(pathutil.getHistory());
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);

        String str = br.readLine();
        while(str!=null)
        {
            stringList.add(str);
            str = br.readLine();
        }

        br.close();

        int playerScore=0;
        String time = "";

        for (String s: stringList)
        {
            var dataList= s.split(";");
            for (String data: dataList)
            {
                var playerData = data.split(",");
                if(playerData[0].equals("score")) {
                    playerScore = Integer.parseInt(playerData[1]);
                }
                if(playerData[0].equals("time")) {
                    time = playerData[1];
                }
            }
            historyList.add(new HistoryScore(playerScore,time));
        }
        Sort();
    }

    private void Sort()
    {
        if(historyList.size()==0)
        {
            return;
        }

        HistoryScore temp;
        for(int i=0; i<historyList.size(); i++)
        {
            for(int j=0; j<historyList.size()-1-i; j++)
            {
                if(historyList.get(j+1).getScore()>historyList.get(j).getScore())
                {
                    temp = historyList.get(j+1);
                    historyList.set(j+1, historyList.get(j));
                    historyList.set(j, temp);
                }
            }
        }
    }

    public ArrayList<HistoryScore> getHistoryList() {
        return historyList;
    }

    public String getString()
    {
        String s="";
        for(int i=0; i<historyList.size(); i++)
        {
            s += historyList.get(i).displayString(i+1) +"\n";
        }
        return s;
    }
}
