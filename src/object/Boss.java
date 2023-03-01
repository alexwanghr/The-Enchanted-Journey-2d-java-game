package object;

import util.ObjectTag;
import util.Point3f;

public class Boss extends GameObject {
    int punishscore;

    public Boss(Point3f centre) {
        hasTextured=true;
        this.width=240;
        this.height=240;
        this.centre =centre;
        this.life=10;
        this.tag = ObjectTag.boss;
        this.punishscore = 50;
        this.textureLocation = pathutil.getPath("boss");
    }
}
