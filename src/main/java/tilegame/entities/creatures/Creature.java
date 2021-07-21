/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.entities.creatures;

import tilegame.entities.Direction;
import tilegame.entities.Entity;
import tilegame.entities.EntityManager;
import tilegame.entities.EntityState;
import tilegame.game_elements.Rendering;
import tilegame.game_elements.Ticking;
import tilegame.gfx.Animation;
import tilegame.gfx.Assets;
import tilegame.gfx.GameCamera;
import tilegame.logger.TileGameLogger;
import tilegame.tiles.Tile;
import tilegame.worlds.World;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

/**
 * based on CodeNMore's tutorial, see: https://github.com/CodeNMore/New-Beginner-Java-Game-Programming-Src
 * expanded on by Loes Immens
 */
public abstract class Creature extends Entity implements Ticking, Rendering
{
    protected float speed;
    protected float xMove;
    protected float yMove;
    
    protected Direction facing;

    protected transient Animation animDown;
    protected transient Animation animUp;
    protected transient Animation animLeft;
    protected transient Animation animRight;

    public static final float DEFAULT_SPEED = 3.0f;
    public static final int DEFAULT_CREATURE_WIDTH = 64;
    public static final int DEFAULT_CREATURE_HEIGHT = 64;
    private static final Logger LOGGER = TileGameLogger.getLogger();
    
    protected Creature(float x, float y, int width, int height, int id, String name) {
        super(x, y, width, height, id, name);
        
        speed = DEFAULT_SPEED;
        xMove = 0;
        yMove = 0;
        
        bounds.x = 25;
        bounds.y = 26;
        bounds.width = 12;
        bounds.height = 37;
        
        facing = Direction.DOWN;

        animDown = new Animation(400, Assets.getAssets().creatureAnimMap.get(id).get("down"));
        animUp = new Animation(400, Assets.getAssets().creatureAnimMap.get(id).get("up"));
        animLeft = new Animation(400, Assets.getAssets().creatureAnimMap.get(id).get("left"));
        animRight = new Animation(400, Assets.getAssets().creatureAnimMap.get(id).get("right"));
    }
    
    @Override
    public void tick()  {
        tickAnimations();
        move();
    }

    protected void tickAnimations() {
        animDown.tick();
        animUp.tick();
        animLeft.tick();
        animRight.tick();
    }

    @Override
    public void render(Graphics g)
    {
        g.drawImage(getCurrentAnimationFrame(), (int) (xLocation - GameCamera.getxOffset()),
                (int) (yLocation - GameCamera.getyOffset()), width, height, null);
    }
    
    public void move()
    {
        if(ableToMoveTo(xMove, 0f)) {
            moveHorizontally();
        }
        if(ableToMoveTo(0f, yMove)) {
            moveVertically();
        }
    }

    public boolean ableToMoveTo(float xOffset, float yOffset)
    {
        for(Entity e : EntityManager.getAllEntitiesExceptPlayer()) {
            if(!e.equals(this) && e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset))) {
                return false;
            }
        }
        return true;
    }
    
    public void moveHorizontally()
    {
        if(movingRight())
        {
            int outerXOfBoundsAfterMoving = (int) (xLocation + xMove + bounds.x + bounds.width);
            int nTilesHorizontalLocationAfterMoving = outerXOfBoundsAfterMoving / Tile.TILEWIDTH;
            
            if(tilePassable(nTilesHorizontalLocationAfterMoving, (int) (yLocation + bounds.y) / Tile.TILEHEIGHT) &&
                    tilePassable(nTilesHorizontalLocationAfterMoving, (int) (yLocation + bounds.y + bounds.height) / Tile.TILEHEIGHT)) {
                xLocation += xMove;
            }
            else {
                xLocation = nTilesHorizontalLocationAfterMoving * Tile.TILEWIDTH - bounds.x - bounds.width - 1;
            }
        }
        else if(movingLeft())
        {
            int outerXOfBoundsAfterMoving = (int) (xLocation + xMove + bounds.x);
            int nTileHorizontalLocationAfterMoving = outerXOfBoundsAfterMoving / Tile.TILEWIDTH;
            
            if(tilePassable(nTileHorizontalLocationAfterMoving, (int) (yLocation + bounds.y) / Tile.TILEHEIGHT) &&
                    tilePassable(nTileHorizontalLocationAfterMoving, (int) (yLocation + bounds.y + bounds.height) / Tile.TILEHEIGHT))
            {
                xLocation += xMove;
            }
            else
            {
                xLocation = nTileHorizontalLocationAfterMoving * Tile.TILEWIDTH + Tile.TILEWIDTH - bounds.x;
            }
        }
        
    }

    private boolean movingRight() {
        return xMove > 0;
    }

    private boolean movingLeft() {
        return xMove < 0;
    }

    public void moveVertically()
    {
        if(movingUp())
        {
            LOGGER.info(this + " tries to move up");
            int outerYOfBoundsAfterMoving = (int) (yLocation + yMove + bounds.y);
            int nTilesVerticalLocationAfterMoving = outerYOfBoundsAfterMoving / Tile.TILEHEIGHT;
            
            if(tilePassable((int) (xLocation + bounds.x) / Tile.TILEWIDTH, nTilesVerticalLocationAfterMoving) &&
                    tilePassable((int) (xLocation + bounds.x + bounds.width) / Tile.TILEWIDTH, nTilesVerticalLocationAfterMoving)) {
                yLocation += yMove;
                LOGGER.info(this + " moves up");
            }
            else {
                yLocation = outerYOfBoundsAfterMoving + Tile.TILEHEIGHT - bounds.y;
            }
        }
        else if(movingDown())
        {
            int outerYOfBoundsAfterMoving = (int) (yLocation + yMove + bounds.y + bounds.height);
            int nTilesVerticalLocationAfterMoving = outerYOfBoundsAfterMoving / Tile.TILEHEIGHT;
            
            if(!tilePassable((int) (xLocation + bounds.x) / Tile.TILEWIDTH, nTilesVerticalLocationAfterMoving) &&
                    !tilePassable((int) (xLocation + bounds.x + bounds.width) / Tile.TILEWIDTH, nTilesVerticalLocationAfterMoving))
            {
                yLocation += yMove;
            }
            else
            {
                yLocation = nTilesVerticalLocationAfterMoving * Tile.TILEHEIGHT - bounds.y - bounds.height - 1;
            }
        }
    }

    private boolean movingUp() {
        return yMove < 0;
    }

    private boolean movingDown() {
        return yMove > 0;
    }

    private BufferedImage getCurrentAnimationFrame()
    {
        switch(facing)
        {
            case LEFT:
                return animLeft.getCurrentFrame(); 
            case RIGHT:
                return animRight.getCurrentFrame();
            case UP:
                return animUp.getCurrentFrame(); 
            default:
                return animDown.getCurrentFrame();
        }
    }
    
    protected boolean tilePassable(int x, int y)
    {
        return !World.getTile(x, y).isSolid();
    }

    protected void attack(Entity e) {
        e.hurt(1);
        beIdle();
    }

    protected void beIdle() {
        entityState = EntityState.IDLE;
    }
}
