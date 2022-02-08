/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.entities.creatures;

import tilegame.entities.EntityState;
import tilegame.entities.attributes.Talking;
import tilegame.entities.static_entities.StaticEntity;
import tilegame.game_elements.Rendering;
import tilegame.game_elements.Ticking;
import tilegame.gfx.Assets;
import tilegame.gfx.GameCamera;

import java.awt.*;

/**
 *
 * @author Loes Immens
 */
public class Cat extends StaticEntity implements Talking, Ticking, Rendering
{
    public Cat(float x, float y, int width, int height, int id, String name)
    {
        super(x, y, width, height, 16, (int) (height / 1.5f), width - 34, (int) (height - height / 1.5f), id, name, false, null);
        
    }

    @Override
    public void render(Graphics g)
    {
        if(id == 3)
            g.drawImage(Assets.getAssets().IMAGE_MAP.get("cat1"), (int) (xLocation - GameCamera.getxOffset()),
                (int) (yLocation - GameCamera.getyOffset()), width, height, null);
        else if(id == 4)
            g.drawImage(Assets.getAssets().IMAGE_MAP.get("cat2"), (int) (xLocation - GameCamera.getxOffset()),
                (int) (yLocation - GameCamera.getyOffset()), width, height, null);
    }

    @Override
    public void die() 
    {
        
    }

    @Override
    public void interact() 
    {
        entityState = EntityState.INTERACTING;
        talk(id);
    }

    @Override
    public void tick() 
    {
        
    }
}
