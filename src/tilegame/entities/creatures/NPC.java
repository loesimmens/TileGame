/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.entities.creatures;

import tilegame.entities.StateMachine;
import tilegame.worlds.World;

/**
 * @author Loes Immens
 */
public class NPC extends Creature implements java.io.Serializable
{
    public NPC(World world, float x, float y, int width, int height, int id, String name)
    {
        super(world, x, y, width, height, id, name);
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
}