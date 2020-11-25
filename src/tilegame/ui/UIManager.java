/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import tilegame.Game;
import tilegame.states.StateManager;

/**
 * based on CodeNMore's tutorial, see: https://github.com/CodeNMore/New-Beginner-Java-Game-Programming-Src
 * expanded on by Loes Immens
 */
public class UIManager 
{
    private HashMap<String, UIObject> menuObjects;
    private HashMap<String, UIObject> gameObjects;
    private HashMap<String, UIObject> optionsObjects;
    
    public UIManager()
    {
        menuObjects = new HashMap<>();
        gameObjects = new HashMap<>();
        optionsObjects = new HashMap<>();
    }
    
    public void tick()
    {
        if(StateManager.getStateManager().getState() == Game.getGame().menuState)
            for(UIObject o: menuObjects.values())
                o.tick();
        else if(StateManager.getStateManager().getState() == Game.getGame().gameState)
            for(UIObject o: gameObjects.values())
                o.tick();
        else if(StateManager.getStateManager().getState() == Game.getGame().optionsState)
            for(UIObject o: optionsObjects.values())
                o.tick();
    }
    
    public void render(Graphics g)
    {
        if(StateManager.getStateManager().getState() == Game.getGame().menuState)
            for(UIObject o : menuObjects.values())
                o.render(g);
        if(StateManager.getStateManager().getState() == Game.getGame().gameState)
            for(UIObject o: gameObjects.values())
                o.render(g);
    }
    
    public void onMouseMove(MouseEvent e)
    {
        for(UIObject o : menuObjects.values())
            o.onMouseMove(e);
    }
    
    public void onMouseRelease(MouseEvent e)
    {
        for(UIObject o : menuObjects.values())
            o.OnMouseRelease(e);
    }
    
    public void addObjectToMenu(String name, UIObject o)
    {
        menuObjects.put(name, o);
    }
    
    public void addObjectToOptions(String name, UIObject o)
    {
        optionsObjects.put(name, o);
    }
    
    public void removeObjectFromMenu(String name)
    {
        menuObjects.remove(name);
    }

    public HashMap<String, UIObject> getMenuObjects() 
    {
        return menuObjects;
    }

    public HashMap<String, UIObject> getGameObjects() 
    {
        return gameObjects;
    }
}
