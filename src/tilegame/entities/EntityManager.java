/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.entities;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import tilegame.dialogue.DialogueManager;
import tilegame.worlds.World;

/**
 * based on CodeNMore's tutorial, see: https://github.com/CodeNMore/New-Beginner-Java-Game-Programming-Src
 * expanded on by Loes Immens
 */
public class EntityManager 
{
    private World world;
    private ArrayList<Entity> entities; 
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
    private DialogueManager dialogueManager;
    
    public EntityManager(World world)
    {
        this.world = world;
        
        dialogueManager = new DialogueManager(world);
        dialogueManager.loadDialogues("res/dialogues/dialogues.txt");
        
        entities = new ArrayList<Entity>();
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
    }
    
    @Override
    public String toString()
    {
        return "entity manager";
    }
    
    //GETTERS SETTERS

    public World getWorld() 
    {
        return world;
    }

    public void setHandler(World world) 
    {
        this.world = world;
    }

    public ArrayList<Entity> getEntities() 
    {
        return entities;
    }

    public void setEntities(ArrayList<Entity> entities) 
    {
        this.entities = entities;
    }

    public DialogueManager getDialogueManager() {
        return dialogueManager;
    }
    
    
    
}
