package org.aesgard.caninoengine.sprite;

import java.util.List;

import org.aesgard.caninoengine.CaninoGameEngine2D;
import org.aesgard.caninoengine.entity2d.AnimatedSprite;
import org.aesgard.caninoengine.entity2d.Sprite;

public final class SpriteManagement {
	private static CaninoGameEngine2D engine;
	
    /*****************************************************
     * once every second during the frame update, this method
     * is called to remove all dead sprites from the linked list
     *****************************************************/
    public static void purgeSprites(List<? extends Sprite> sprites) {
        for (int n=0; n < sprites.size(); n++) {
            AnimatedSprite spr = (AnimatedSprite) sprites.get(n);
            if (!spr.alive()) {
                sprites.remove(n);
            }
        }
    }
    
    /*****************************************************
     * perform collision testing of all active sprites
     *****************************************************/
    public static void testCollisions(List<? extends Sprite> _sprites) {
        //iterate through the sprite list, test each sprite against
        //every other sprite in the list
        for (int first=0; first < _sprites.size(); first++) {

            //get the first sprite to test for collision
            AnimatedSprite spr1 = (AnimatedSprite) _sprites.get(first);
            if (spr1.alive()) {

                //look through all sprites again for collisions
                for (int second = 0; second < _sprites.size(); second++) {

                    //make sure this isn't the same sprite
                    if (first != second) {

                        //get the second sprite to test for collision
                        AnimatedSprite spr2 = (AnimatedSprite)
                                              _sprites.get(second);
                        if (spr2.alive()) {
                            if (spr2.collidesWith(spr1)) {
                                engine.spriteCollision(spr1, spr2);
                                break;
                            }
                            else
                               spr1.setCollided(false);

                        }
                    }
                }
            }
        }
    }

    
    /*****************************************************
     * update the sprite list from the game loop thread
     *****************************************************/
    public static void updateSprites(List<? extends Sprite> sprites) {
        for (int n=0; n < sprites.size(); n++) {
            AnimatedSprite spr = (AnimatedSprite) sprites.get(n);
            if (spr.alive()) {
                spr.updatePosition();
                spr.updateRotation();
                spr.updateAnimation();
                engine.spriteUpdate(spr);
                spr.updateLifetime();
                if (!spr.alive()) {
                    engine.spriteDying(spr);
                }
            }
        }
    }

    /*****************************************************
     * draw all active sprites in the sprite list
     * sprites lower in the list are drawn on top
     *****************************************************/
    public static void drawSprites(List<? extends Sprite> _sprites) {
        //draw sprites in reverse order (reverse priority)
        for (int n=0; n<_sprites.size(); n++) {
            AnimatedSprite spr = (AnimatedSprite) _sprites.get(n);
            if (spr.alive()) {
                spr.updateFrame();
                spr.transform();
                spr.draw();
                engine.spriteDraw(spr);
            }
        }
    }

    public static void setEngine(CaninoGameEngine2D e) {
    	engine = e;
    }

}
