/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.entities.creatures;

import java.awt.Color;
import java.awt.Graphics;
import tilegame.entities.StateMachine;
import tilegame.entities.staticEntities.StaticEntity;
import tilegame.gfx.Assets;
import tilegame.worlds.World;

/**
 *
 * @author Loes Immens
 */
public class Cat extends StaticEntity
{
    
    public Cat(World world, float x, float y, int width, int height, int id, String name)
    {
        super(world, x, y, width, height, 16, (int) (height / 1.5f), width - 34, (int) (height - height / 1.5f), id, name, false, null);
        
    }
    
    
    @Override
    public void render(Graphics g)
    {
        if(id == 3)
            g.drawImage(Assets.getAssets().imageMap.get("cat1"), (int) (x - world.getGameState().getGame().getGameCamera().getxOffset()), 
                (int) (y - world.getGameState().getGame().getGameCamera().getyOffset()), width, height, null);
        else if(id == 4)
            g.drawImage(Assets.getAssets().imageMap.get("cat2"), (int) (x - world.getGameState().getGame().getGameCamera().getxOffset()), 
                (int) (y - world.getGameState().getGame().getGameCamera().getyOffset()), width, height, null);
    }

    @Override
    public void die() 
    {
        
    }

    @Override
    public void interact() 
    {
        state = StateMachine.INTERACTING;
    }

    @Override
    public void tick() 
    {
        
    }
}
