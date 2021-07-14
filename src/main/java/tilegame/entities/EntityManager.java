/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.entities;

import tilegame.dialogue.DialogueManager;
import tilegame.entities.creatures.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

/**
 * based on CodeNMore's tutorial, see: https://github.com/CodeNMore/New-Beginner-Java-Game-Programming-Src
 * expanded on by Loes Immens
 */
public class EntityManager implements java.io.Serializable
{
    private static final EntityManager entityManager = new EntityManager();
    private DialogueManager dialogueManager;
    private static ArrayList<Entity> entities;
    private static ArrayList<Entity> allEntitiesExceptPlayer;
    private Comparator<Entity> renderSorter = new Comparator<Entity>()
    {
        @Override
        public int compare(Entity a, Entity b) 
        {
            if(a.getY() + a.height < b.getY() + b.height)
                return -1;
            else
                return 1;
        }
    };
    
    private EntityManager()
    {
        dialogueManager = DialogueManager.getInstance();
        
        entities = new ArrayList<>();
        allEntitiesExceptPlayer = new ArrayList<>();
    }

    public static final EntityManager getInstance(){
        return entityManager;
    }
    
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
        entities.sort(renderSorter);
        dialogueManager.tick();
    }
    
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

    public static ArrayList<Entity> getEntities()
    {
        return entities;
    }

    public static ArrayList<Entity> getAllEntitiesExceptPlayer() {
        return allEntitiesExceptPlayer;
    }

    public DialogueManager getDialogueManager() {
        return dialogueManager;
    }
}
