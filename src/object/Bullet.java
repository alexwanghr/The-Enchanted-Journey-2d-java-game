package object;

import util.GameObject;
import util.Point3f;

public class Bullet extends GameObject {

    private int belongId;
    public Bullet(Point3f centre, int belongId)
    {
        hasTextured=true;
        this.width=32;
        this.height=32;
        this.centre =centre;
        this.belongId = belongId;
        SetTexture();
    }

    public int getBelongId()
    {
        return belongId;
    }

    void SetTexture() {
        switch (belongId) {
            case 1:
                textureLocation = pathutil.getPath("bullet1");
                break;
            case 2:
                textureLocation = pathutil.getPath("bullet1");
                break;
        }
    }
}
