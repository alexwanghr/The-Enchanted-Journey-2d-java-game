package object;
import util.ObjectTag;
import util.Point3f;


public class Enemy extends GameObject {
    private boolean hasTip;
    private String line;
    private String tipTextureLocation;
    private int score;

    public Enemy(Point3f centre, ObjectTag tag) {
        hasTextured=true;
        this.width=32;
        this.height=32;
        this.centre =centre;
        this.life=1;
        this.tag = tag;
        this.score = 50;
        if(Math.random()>0.2)
        {
            this.hasTip = true;
        }
        SetTexture();
    }

    public void SetTexture()
    {
        if(hasTip)
        {
            tipTextureLocation = pathutil.getPath("tip");
        }
        switch (tag)
        {
            case frog:
                textureLocation = pathutil.getPath("frog");
                break;
            case bat:
                textureLocation = pathutil.getPath("bat");
                break;
            case ghost:
                textureLocation = pathutil.getPath("ghost");
                break;
            case skeleton:
                textureLocation = pathutil.getPath("skeleton");
                break;
        }
    }

    public void SetLine(String line)
    {
        this.line = line;
    }

    public String getLine()
    {
        return line;
    }

    public boolean isHasTip()
    {
        return hasTip;
    }

    public String getTipTextureLocation()
    {
        return tipTextureLocation;
    }

    public int getScore(){return score;}
}
