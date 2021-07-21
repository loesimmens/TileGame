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
import tilegame.entities.EntityState;
import tilegame.entities.exceptions.PlayerException;
import tilegame.gfx.GameCamera;
import tilegame.inventory.InventoryPanel;
import tilegame.logger.TileGameLogger;
import tilegame.utils.Listener;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.logging.Logger;

/**
 * based on CodeNMore's tutorial, see: https://github.com/CodeNMore/New-Beginner-Java-Game-Programming-Src
 * expanded on by Loes Immens
 */
public class Player extends Creature implements Listener
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

    private static final Logger LOGGER = TileGameLogger.getLogger();
        
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
        tickAnimations();
        getInput();
        move();
        GameCamera.centerOnEntity(this);

        if(timeToAttackOrInteract()){
            attackOrInteract();
        }
    }

    private void getInput() {
        xMove = 0;
        yMove = 0;

        if(!InventoryPanel.isActive() && entityState != EntityState.INTERACTING) {
            if(up) {
                entityState = EntityState.WALKING;
                facing = Direction.UP;
                yMove = -speed;
                LOGGER.info("Player is moving " + facing + -yMove);
            }
            else if(down) {
                entityState = EntityState.WALKING;
                facing = Direction.DOWN;
                yMove = speed;
            }
            else if(left) {
                entityState = EntityState.WALKING;
                facing = Direction.LEFT;
                xMove = -speed;
            }
            else if(right) {
                entityState = EntityState.WALKING;
                facing = Direction.RIGHT;
                xMove = speed;
            }
            else if(attack) {
                entityState = EntityState.ATTACKING;
            }
            else if(interact) {
                entityState = EntityState.INTERACTING;
            }
            else {
                beIdle();
            }
        }
    }

    @Override
    public void interact(){
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(Object o) {
        if(o instanceof Integer){
            Integer keyCode = (Integer)o;
            switch(keyCode){
                case KeyEvent.VK_W: up = true;
                    LOGGER.info("Player hears that key UP has been pressed");
                    break;
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
                    LOGGER.info("Player hears that a previously pressed key has been released");
                    break;
                }
                default: break;
            }
        }
        else
            throw new IllegalArgumentException("player input is no integer!");
    }

    private void attackOrInteract() {
        Rectangle interactionBounds = setInteractionBounds();
        interactionTimer = 0;
        for(Entity e : EntityManager.getAllEntitiesExceptPlayer()) {
            if(intersectsWithOtherEntity(interactionBounds, e)) {
                if(isAttacking()) {
                    attack(e);
                } else if(isInteracting()) {
                    if(!isInteracting(e)) {
                        interactWith(e);
                    }
                    if(!DialogueBox.isActive()) {
                        setIdle(e);
                        beIdle();
                    }
                }
            }
        }
    }

    private boolean timeToAttackOrInteract(){
        return (isAttacking() || isInteracting())
                && enoughTimeBetweenInteractions() && !InventoryPanel.isActive();
    }

    private boolean isAttacking() {
        return entityState == EntityState.ATTACKING;
    }

    private boolean isInteracting() {
        return entityState == EntityState.INTERACTING;
    }

    private boolean isInteracting(Entity e) {
        return e.getState() == EntityState.INTERACTING;
    }

    private void setIdle(Entity e) {
        e.setState(EntityState.IDLE);
    }

    private boolean enoughTimeBetweenInteractions() {
        interactionTimer += System.currentTimeMillis() - lastInteractionTimer;
        lastInteractionTimer = System.currentTimeMillis();
        return (interactionTimer >= INTERACTION_COOLDOWN);
    }

    private Rectangle setInteractionBounds()
    {
        Rectangle collisionBounds = getCollisionBounds(0,0);
        var interactionBounds = new Rectangle();
        var arSize = 20;
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

    private void interactWith(Entity e) {
        e.interact();
        if(e.getDialogue() != null) {
            DialogueBox.setActive(active);
        }
    }

    @Override
    public void die() 
    {
        LOGGER.info("Player has died!");
    }
}
