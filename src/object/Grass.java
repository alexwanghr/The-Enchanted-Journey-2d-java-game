package object;

import util.ObjectTag;
import util.Point3f;

public class Grass extends GameObject {
    public Grass(Point3f centre, ObjectTag tag) {
        hasTextured=true;
        this.width=32;
        this.height=32;
        this.centre =centre;
        this.tag = tag;
        SetTexture();
    }

    public void SetTexture()
    {
        switch (tag)
        {
            case grass_L:
                textureLocation = pathutil.getGrassPath("grass_L");
                break;
            case grass_R:
                textureLocation = pathutil.getGrassPath("grass_R");
                break;
            case grass_T:
                textureLocation = pathutil.getGrassPath("grass_T");
                break;
            case grass:
                textureLocation = pathutil.getGrassPath("grass");
                break;
        }
    }
}
