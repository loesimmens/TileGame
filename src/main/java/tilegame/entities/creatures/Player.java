/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.entities.creatures;

import tilegame.dialogue.DialogueBox;
import tilegame.entities.Direction;
import tilegame.entities.Entity;
import tilegame.entities.EntityManager;
import tilegame.entities.State;
import tilegame.entities.exceptions.PlayerException;
import tilegame.gfx.GameCamera;
import tilegame.inventory.InventoryPanel;
import tilegame.utils.Listener;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * based on CodeNMore's tutorial, see: https://github.com/CodeNMore/New-Beginner-Java-Game-Programming-Src
 * expanded on by Loes Immens
 */
public class Player extends Creature implements java.io.Serializable, Listener
{
    private static Player player;
    private long lastInteractionTimer;
    private static final long INTERACTION_COOLDOWN = 800;
    private long interactionTimer;

    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;
    private boolean interact;
    private boolean attack;
        
    private Player(float x, float y)
    {
        super(x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT, 0, "player");
        facing = Direction.UP;
        interactionTimer = INTERACTION_COOLDOWN;
    }

    public static Player getInstance(float x, float y){
        if(player == null){
            player = new Player(x, y);
        }
        return player;
    }

    public static Player getInstance() throws PlayerException {
        if(player != null){
            return player;
        }
        else{
            throw new PlayerException("player has not been created yet");
        }
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
        GameCamera.getInstance().centerOnEntity(this);

        checkInteractions();
    }
    
    private void checkInteractions()
    {
        if(state == State.ATTACKING || state == State.INTERACTING) {
            if(enoughTimeBetweenInteractions() && !InventoryPanel.isActive()){
                Rectangle interactionBounds = setInteractionBounds();
                interactionTimer = 0;
                for (Entity e : EntityManager.getAllEntitiesExceptPlayer()) {
                    if (!e.equals(this)) {
                        if (intersectsWithOtherEntity(interactionBounds, e)) {
                            if (state.equals(State.ATTACKING)) {
                                e.hurt(1);
                                state = State.IDLE;
                            } else if (state.equals(State.INTERACTING)) {
                                if (e.getState() != State.INTERACTING) {
                                    e.interact();
                                    DialogueBox.setActive(active);
                                }
                                if (!DialogueBox.isActive()) {
                                    e.setState(State.IDLE);
                                    state = State.IDLE;
                                }
                            }
                            return;
                        }
                    }
                }
            }
        }
    }

    private boolean enoughTimeBetweenInteractions() {
        interactionTimer += System.currentTimeMillis() - lastInteractionTimer;
        lastInteractionTimer = System.currentTimeMillis();
        if (interactionTimer >= INTERACTION_COOLDOWN) {
            return true;
        }
        return false;
    }

    private Rectangle setInteractionBounds()
    {
        Rectangle collisionBounds = getCollisionBounds(0,0);
        Rectangle interactionBounds = new Rectangle();
        int arSize = 20;
        interactionBounds.width = arSize;
        interactionBounds.height = arSize;

        switch(facing)
        {
            case UP:
            {
                interactionBounds.x = collisionBounds.x + collisionBounds.width / 2 - arSize / 2;
                interactionBounds.y = collisionBounds.y - arSize;
            }   break;
            case DOWN:
            {
                interactionBounds.x = collisionBounds.x + collisionBounds.width / 2 - arSize / 2;
                interactionBounds.y = collisionBounds.y + collisionBounds.height;
            }   break;
            case LEFT:
            {
                interactionBounds.x = collisionBounds.x - arSize;
                interactionBounds.y = collisionBounds.y + collisionBounds.height / 2 - arSize / 2;
            }   break;
            case RIGHT:
            {
                interactionBounds.x = collisionBounds.x + collisionBounds.width;
                interactionBounds.y = collisionBounds.y + collisionBounds.height / 2 - arSize / 2;
            }   break;
            default:
            {
                interactionBounds.x = collisionBounds.x;
                interactionBounds.y = collisionBounds.y;
            }
            break;
        }
        return interactionBounds;
    }

    private boolean intersectsWithOtherEntity(Rectangle interactionBounds, Entity e) {
        return e.getCollisionBounds(0, 0).intersects(interactionBounds);
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
        
        if(InventoryPanel.isActive())
            return;
        if(state != State.INTERACTING){
            if(up)
            {
                state = State.WALKING;
                facing = Direction.UP;
                yMove = -speed;
            }
            else if(down)
            {
                state = State.WALKING;
                facing = Direction.DOWN;
                yMove = speed;
            }
            else if(left)
            {
                state = State.WALKING;
                facing = Direction.LEFT;
                xMove = -speed;
            }
            else if(right)
            {
                state = State.WALKING;
                facing = Direction.RIGHT;
                xMove = speed;
            }
            else if(attack)
            {
                state = State.ATTACKING;
            }
            else if(interact)
            {
                state = State.INTERACTING;
            }
            else {
                state = State.IDLE;
            }
        }
    }

    @Override
    public void interact(){}


    @Override
    public void update(Object o) {
        if(o instanceof Integer){
            Integer keyCode = (Integer)o;
            switch(keyCode){
                case KeyEvent.VK_W: up = true; break;
                case KeyEvent.VK_S: down = true; break;
                case KeyEvent.VK_A: left = true; break;
                case KeyEvent.VK_D: right = true; break;
                case KeyEvent.VK_E: interact = true; break;
                case KeyEvent.VK_X: attack = true; break;
                case -1: {
                    up = false;
                    down = false;
                    left = false;
                    right = false;
                    interact = false;
                    attack = false;
                    break;
                }
                default: break;
            }
        }
        else
            throw new IllegalArgumentException("player input is no integer!");
    }
}
