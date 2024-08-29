package org.aesgard.caninoengine.entity2d.base;

/*********************************************************
 * Base game image class for bitmapped game entities
 **********************************************************/
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;

import org.aesgard.caninoengine.CaninoGameEngine2D;
import org.aesgard.caninoengine.util.FileUtil;

public class ImageEntity extends BaseGameEntity {
    //variables
    protected Image image;
    protected Component component;
    protected AffineTransform at;
    protected Graphics2D g2d;

    //default constructor
    public ImageEntity(Component c) {
        component = c;
        setImage(null);
        setAlive(true);
    }
    
    //default constructor
    public ImageEntity(CaninoGameEngine2D e) {
        component = e.getComponent();
        setImage(null);
        setAlive(true);
    }

    public Image getImage() { return image; }

    public void setImage(Image image) {
        this.image = image;
        double x = component.getSize().width/2  - width()/2;
        double y = component.getSize().height/2 - height()/2;
        at = AffineTransform.getTranslateInstance(x, y);
    }

    public int width() {
        if (image != null)
            return image.getWidth(component);
        else
            return 0;
    }
    public int height() {
        if (image != null)
            return image.getHeight(component);
        else
            return 0;
    }

    public double getCenterX() {
        return getX() + width() / 2;
    }
    public double getCenterY() {
        return getY() + height() / 2;
    }

    public void setGraphics(Graphics2D g) {g2d = g;}
    public Graphics2D getGraphics() { return g2d; }

    public Component getComponent() {return component;}
	public void setComponent(Component c) {this.component = c;}

	public AffineTransform getAt() {
		return at;
	}

	public void setAt(AffineTransform at) {
		this.at = at;
	}

	public void load(String filename) {
        Toolkit tk = Toolkit.getDefaultToolkit();
        image = tk.getImage(FileUtil.getURL(filename));
        while(getImage().getWidth(component) <= 0);
        double x = component.getSize().width/2  - width()/2;
        double y = component.getSize().height/2 - height()/2;
        at = AffineTransform.getTranslateInstance(x, y);
    }

    public void transform() {
        at.setToIdentity();
        at.translate((int)getX() + width()/2, (int)getY() + height()/2);
        at.rotate(Math.toRadians(getFaceAngle()));
        at.translate(-width()/2, -height()/2);
    }

    public void draw() {
        g2d.drawImage(getImage(), at, component);
    }

    //bounding rectangle
    public Rectangle getBounds() {
        Rectangle r;
        r = new Rectangle((int)getX(), (int)getY(), width(), height());
        return r;
    }

}
