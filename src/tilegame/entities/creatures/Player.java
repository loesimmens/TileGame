/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.entities.creatures;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import tilegame.entities.Direction;
import tilegame.entities.Entity;
import tilegame.entities.StateMachine;
import tilegame.worlds.World;

/**
 * based on CodeNMore's tutorial, see: https://github.com/CodeNMore/New-Beginner-Java-Game-Programming-Src
 * expanded on by Loes Immens
 */
public class Player extends Creature
{
    //World
    private World world;
    
    //Attack timer
    private long lastInteractionTimer, interactionCooldown = 800, interactionTimer = interactionCooldown;
    
        
    public Player(World world, float x, float y)
    {
        super(world, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT, 0, "player");
        
        this.world = world;
        
        facing = Direction.UP;
    }

    @Override
    public void tick() 
    {
        //Animations
        animDown.tick();
        animUp.tick();
        animLeft.tick();
        animRight.tick();
        
        //Movement
        getInput();
        move();
        world.getGameState().getGame().getGameCamera().centerOnEntity(this);
        //Attack
        checkInteractions();
    }
    
    private void checkInteractions()
    {
        if(!(state == StateMachine.ATTACKING || state == StateMachine.INTERACTING))
            return;
        interactionTimer += System.currentTimeMillis() - lastInteractionTimer;
        lastInteractionTimer = System.currentTimeMillis();
        if(interactionTimer < interactionCooldown)
            return;

        if(world.getGameState().getInventoryPanel().isActive())
            return;
        
        Rectangle interactionBounds = setInteractionBounds();

        interactionTimer = 0;

        for(Entity e : world.getEntityManager().getEntities())
        {
            if(e.equals(this))
                continue;
            if(e.getCollisionBounds(0,0).intersects(interactionBounds))
            {
                if(state.equals(StateMachine.ATTACKING))
                {
                    e.hurt(1);
                    state = StateMachine.IDLE;
                }
                else if(state.equals(StateMachine.INTERACTING))
                {
                    if(e.getState() != StateMachine.INTERACTING)
                    {
                        e.interact();
                        world.getEntityManager().getDialogueManager().getDialogueBox().setActive(active);
                    }
                    if(!world.getEntityManager().getDialogueManager().getDialogueBox().isActive())
                    {
                        e.setState(StateMachine.IDLE);
                        state = StateMachine.IDLE;
                    }
                }
                return;
            }
        }
    }
    
    @Override
    public void die() 
    {
        System.out.println("You lose");
    }
    
    private void getInput()
    {
        xMove = 0;
        yMove = 0;
        
        if(world.getGameState().getInventoryPanel().isActive())
            return;
        if(state != StateMachine.INTERACTING)
        if(world.getGameState().getGame().getKeyManager().up)
        {
            state = StateMachine.WALKING;
            facing = Direction.UP;
            yMove = -speed;
        }
        if(world.getGameState().getGame().getKeyManager().down)
        {
            state = StateMachine.WALKING;
            facing = Direction.DOWN;
            yMove = speed;
        }
        if(world.getGameState().getGame().getKeyManager().left)
        {
            state = StateMachine.WALKING;
            facing = Direction.LEFT;
            xMove = -speed;
        }
        if(world.getGameState().getGame().getKeyManager().right)
        {
            state = StateMachine.WALKING;
            facing = Direction.RIGHT;
            xMove = speed;
        }
        if(world.getGameState().getGame().getKeyManager().attack)
        {
            state = StateMachine.ATTACKING;
        }
        if(world.getGameState().getGame().getKeyManager().hasJustBeenPressed(KeyEvent.VK_E))
        {
            state = StateMachine.INTERACTING;
        }
    }

    @Override
    public void interact(){}

    private Rectangle setInteractionBounds() 
    {
        Rectangle cb = getCollisionBounds(0,0);
        Rectangle ib = new Rectangle(); //interaction bounds
        int arSize = 20;
        ib.width = arSize;
        ib.height = arSize;
        
        switch(facing)
        {
            case UP:
            {
                ib.x = cb.x + cb.width / 2 - arSize / 2;
                ib.y = cb.y - arSize;
            }   break;
            case DOWN:
            {
                ib.x = cb.x + cb.width / 2 - arSize / 2;
                ib.y = cb.y + cb.height;
            }   break;
            case LEFT:
            {
                ib.x = cb.x - arSize;
                ib.y = cb.y + cb.height / 2 - arSize / 2;
            }   break;
            case RIGHT:
            {
                ib.x = cb.x + cb.width;
                ib.y = cb.y + cb.height / 2 - arSize / 2;
            }   break;
            default:
            {
                ib.x = cb.x;
                ib.y = cb.y;
            }
                break;
        }
        return ib;
    }
}
