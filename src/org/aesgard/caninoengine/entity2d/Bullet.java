package org.aesgard.caninoengine.entity2d;

/*********************************************************
 * Bullet class derives from BaseVectorShape
 **********************************************************/
import java.awt.Rectangle;

import org.aesgard.caninoengine.entity2d.base.VectorEntity;


public class Bullet extends VectorEntity {

    //bounding rectangle
    public Rectangle getBounds() {
        Rectangle r;
        r = new Rectangle((int)getX(), (int) getY(), 1, 1);
        return r;
    }

    public Bullet() {
        //create the bullet shape
        setShape(new Rectangle(0, 0, 1, 1));
        setAlive(false);
    }
}
