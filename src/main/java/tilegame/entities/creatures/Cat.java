/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.entities.creatures;

import tilegame.Game;
import tilegame.entities.StateMachine;
import tilegame.entities.attributes.Talking;
import tilegame.entities.staticEntities.StaticEntity;
import tilegame.gfx.Assets;

import java.awt.*;

/**
 *
 * @author Loes Immens
 */
public class Cat extends StaticEntity implements Talking
{
    
    public Cat(float x, float y, int width, int height, int id, String name)
    {
        super(x, y, width, height, 16, (int) (height / 1.5f), width - 34, (int) (height - height / 1.5f), id, name, false, null);
        
    }
    
    
    @Override
    public void render(Graphics g)
    {
        if(id == 3)
            g.drawImage(Assets.getAssets().imageMap.get("cat1"), (int) (x - Game.getGameState().getGame().getGameCamera().getxOffset()),
                (int) (y - Game.getGameState().getGame().getGameCamera().getyOffset()), width, height, null);
        else if(id == 4)
            g.drawImage(Assets.getAssets().imageMap.get("cat2"), (int) (x - Game.getGameState().getGame().getGameCamera().getxOffset()),
                (int) (y - Game.getGameState().getGame().getGameCamera().getyOffset()), width, height, null);
    }

    @Override
    public void die() 
    {
        
    }

    @Override
    public void interact() 
    {
        state = StateMachine.INTERACTING;
        talk(id);
    }

    @Override
    public void tick() 
    {
        
    }
}
