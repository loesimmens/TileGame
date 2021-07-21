/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.entities;

import tilegame.dialogue.DialogueManager;
import tilegame.entities.creatures.Player;
import tilegame.game_elements.Rendering;
import tilegame.game_elements.Ticking;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * based on CodeNMore's tutorial, see: https://github.com/CodeNMore/New-Beginner-Java-Game-Programming-Src
 * expanded on by Loes Immens
 */
public class EntityManager implements java.io.Serializable, Ticking, Rendering
{
    private static final EntityManager entityManager = new EntityManager();
    private final DialogueManager dialogueManager = DialogueManager.getInstance();
    private static List<Entity> entities = new ArrayList<>();
    private static List<Entity> allEntitiesExceptPlayer = new ArrayList<>();
    private final transient Comparator<Entity> entityComparator = (a, b) -> Float.compare(a.getyLocation() + a.height, b.getyLocation() + b.height);
    
    private EntityManager() {}

    public static EntityManager getInstance(){
        return entityManager;
    }

    @Override
    public void tick()
    {
        Iterator<Entity> it = entities.iterator();
        while(it.hasNext())
        {
            Entity e = it.next();
            e.tick();
            if(!e.isActive())
                it.remove();
        }
        entities.sort(entityComparator);
        dialogueManager.tick();
    }

    @Override
    public void render(Graphics g)
    {
        for(Entity e : entities)
        {
            e.render(g);
        }
        dialogueManager.render(g);
    }
    
    public void addEntity(Entity e)
    {
        entities.add(e);
        if(!(e instanceof Player)) {
            allEntitiesExceptPlayer.add(e);
        }
    }
    
    @Override
    public String toString()
    {
        return "entity manager";
    }

    public static List<Entity> getEntities()
    {
        return entities;
    }

    public static List<Entity> getAllEntitiesExceptPlayer() {
        return allEntitiesExceptPlayer;
    }
}
