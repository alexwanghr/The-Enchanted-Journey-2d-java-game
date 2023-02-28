package object;

import util.ObjectTag;
import util.Point3f;

public class Gate extends GameObject {

    public Gate(Point3f centre, ObjectTag tag) {
        hasTextured=true;
        this.width=256;
        this.height=256;
        this.centre =centre;
        this.tag = tag;
        this.textureLocation = pathutil.getPath("gate");
    }
}
