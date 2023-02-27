package object;

import util.GameObject;
import util.ObjectTag;
import util.Point3f;
import java.util.Random;

public class Item extends GameObject {

    int score;

    public Item(Point3f centre, ObjectTag tag)
    {
        hasTextured=true;
        this.width=32;
        this.height=32;
        this.centre =centre;
        this.tag = tag;
        this.score = 50;
        SetTexture();
    }

    void SetTexture()
    {
        int min = 1;
        int max = 14;
        int n = (int)Math.random()*((max - min) + 1) + min;
        this.textureLocation = pathutil.getItemPath(Integer.toString(n));
    }

    public int getScore(){return score;}

}
