package com.AtomEdition.KittyClicker.game;

/**
 * Created with IntelliJ IDEA.
 * User: FruityDevil
 * Date: 16.09.14
 * Time: 14:27
 * To change this template use File | Settings | File Templates.
 */
public class Kitty {

    public Kitty(){

    }

    public Kitty(Integer x, Integer y) {
        this.x = x;
        this.y = y;
        this.flag = false;
        this.speedX = 0;
        this.speedY = 0;
    }

    private Integer x,y;
    private Integer speedX,speedY;
    private boolean flag;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getSpeedX() {
        return speedX;
    }

    public void setSpeedX(Integer speedX) {
        this.speedX = speedX;
    }

    public Integer getSpeedY() {
        return speedY;
    }

    public void setSpeedY(Integer speedY) {
        this.speedY = speedY;
    }

    /**
     * Sets new position of object depending on it's speed.
     */
    public void move(){
        this.x += this.speedX;
        this.y += this.speedY;
    }
}
