package org.aesgard.caninoengine.entity2d;

/*********************************************************
 * Asteroid class derives from BaseVectorShape
 **********************************************************/
import java.awt.Polygon;
import java.awt.Rectangle;

import org.aesgard.caninoengine.entity2d.base.VectorEntity;


public class Asteroid extends VectorEntity {
    //define the asteroid polygon shape
    private int[] astx = {-20,-13, 0,20,22, 20, 12,  2,-10,-22,-16};
    private int[] asty = { 20, 23,17,20,16,-20,-22,-14,-17,-20, -5};

    //rotation speed
    protected double rotVel;
    public double getRotationVelocity() { return rotVel; }
    public void setRotationVelocity(double v) { rotVel = v; }

    //bounding rectangle
    public Rectangle getBounds() {
        Rectangle r;
        r = new Rectangle((int)getX() - 20, (int) getY() - 20, 40, 40);
        return r;
    }

    //default constructor
    public Asteroid() {
        setShape(new Polygon(astx, asty, astx.length));
        setAlive(true);
        setRotationVelocity(0.0);
    }

}
