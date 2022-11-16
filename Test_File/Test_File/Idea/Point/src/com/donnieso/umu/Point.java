package com.donnieso.umu;

public class Point {
    private int x;
    private int y;

    public Point (int x, int y){
        this.x = x;
        this.y = y;
    }

    public Point (Point p){
        x = p.x;
        y = p.y;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public void setX (int x){
        this.x = x;
    }

    public void setY (int y){
        this.y = y;
    }

    @Override
    public String toString (){
        return " x Cordinate is == " + x + " y Cordinate is == " + y;
    }

}
