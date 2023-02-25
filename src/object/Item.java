package object;

import util.GameObject;
import util.ObjectTag;
import util.Point3f;

public class Item extends GameObject {

    public Item(Point3f centre, ObjectTag tag)
    {
        hasTextured=true;
        this.width=32;
        this.height=32;
        this.centre =centre;
        this.tag = tag;
        this.textureLocation = pathutil.getPath("cherry");
    }

}
