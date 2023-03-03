package object;

import util.ObjectTag;
import util.Point3f;

public class Player extends GameObject {

    private int playerScore =0;
    private int id;

    public Player(String textureLocation, Point3f centre, int id) {
        hasTextured=true;
        this.id = id;
        this.textureLocation=textureLocation;
        this.width=48;
        this.height=64;
        this.centre =centre;
        this.life=3;
        this.tag = ObjectTag.player;
    }

    public Player(String textureLocation, Point3f centre,int id, int life, int playerScore) {
        hasTextured=true;
        this.id = id;
        this.textureLocation=textureLocation;
        this.width=48;
        this.height=64;
        this.centre =centre;
        this.life=life;
        this.playerScore = playerScore;
        this.tag = ObjectTag.player;
    }

    public void changeScore(int count)
    {
        playerScore +=count;
    }

    public void changeLife(int count)
    {
        life+=count;
    }

    public int getPlayerScore()
    {
        return playerScore;
    }

    @Override
    public int getHeight() {
        return super.getHeight();
    }

    @Override
    public int getWidth() {
        return super.getWidth();
    }

    @Override
    public Point3f getCentre() {
        return super.getCentre();
    }

    public int getLife()
    {
        return this.life;
    }

    @Override
    public String toString() {
        return String.format("Player id:%d, life:%d, score:%d", id,life,playerScore);
    }
}
