package object;

import util.ObjectTag;
import util.Point3f;

public class Item extends GameObject {

    int score;

    public Item(Point3f centre)
    {
        hasTextured=true;
        this.width=32;
        this.height=32;
        this.centre =centre;
        this.tag = ObjectTag.item;
        this.score = 50;
        SetTexture();
    }

    void SetTexture()
    {
        int min = 1;
        int max = 13;
        int n = (int)(Math.random()*(max - min + 1)) + min;
        this.textureLocation = pathutil.getItemPath(Integer.toString(n));
    }

    public int getScore(){return score;}

}
