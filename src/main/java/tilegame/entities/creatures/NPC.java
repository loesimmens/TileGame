/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.entities.creatures;

import tilegame.entities.StateMachine;
import tilegame.entities.attributes.Talking;

/**
 * @author Loes Immens
 */
public class NPC extends Creature implements java.io.Serializable, Talking
{
    public NPC(float x, float y, int width, int height, int id, String name)
    {
        super(x, y, width, height, id, name);
    }

    @Override
    public void die() 
    {
        
    }

    //todo: implement talk
    @Override
    public void interact()
    {
        state = StateMachine.INTERACTING;
        talk(id);
    }
}