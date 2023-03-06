package object;

import util.ObjectTag;
import util.Point3f;

public class Player extends GameObject {

    private int playerScore =0;
    private int id;
    private float attackValue;

    public Player(String textureLocation, Point3f centre, int id) {
        hasTextured=true;
        this.id = id;
        this.textureLocation=textureLocation;
        this.width=48;
        this.height=64;
        this.centre =centre;
        this.life=3;
        this.tag = ObjectTag.player;
        setAttackValue();
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
        setAttackValue();
    }

    public void changeScore(int count)
    {
        playerScore +=count;
        if(playerScore<0) playerScore=0;
        setAttackValue();
    }

    void setAttackValue()
    {
        attackValue = 1.0f + (playerScore/350)*0.1f;
        System.out.println("Player attack value : " + attackValue);
    }

    public float getAttackValue()
    {
        return this.attackValue;
    }

    public void changeLife(int count)
    {
        life+=count;
        if(life==0)
        {
            playerScore-=100;
        }
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
