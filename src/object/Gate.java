package object;

import util.ObjectTag;
import util.Point3f;

public class Gate extends GameObject {

    public Gate(Point3f centre) {
        hasTextured=true;
        this.width=240;
        this.height=240;
        this.centre =centre;
        this.tag = ObjectTag.gate;
        this.textureLocation = pathutil.getPath("gate");
    }
}
