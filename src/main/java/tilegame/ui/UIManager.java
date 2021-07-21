/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.ui;

import tilegame.Game;
import tilegame.game_elements.Rendering;
import tilegame.states.StateManager;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * based on CodeNMore's tutorial, see: https://github.com/CodeNMore/New-Beginner-Java-Game-Programming-Src
 * expanded on by Loes Immens
 */
public class UIManager implements Rendering
{
    private Map<String, UIObject> menuObjects;
    private Map<String, UIObject> gameObjects;
    private Map<String, UIObject> optionsObjects;
    
    public UIManager()
    {
        menuObjects = new HashMap<>();
        gameObjects = new HashMap<>();
        optionsObjects = new HashMap<>();
    }

    @Override
    public void render(Graphics g)
    {
        if(StateManager.getStateManager().getState() == Game.getMenuState())
            for(UIObject o : menuObjects.values())
                o.render(g);
        if(StateManager.getStateManager().getState() == Game.getGameState())
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
            o.onMouseRelease(e);
    }
    
    public void addObjectToMenu(String name, UIObject o)
    {
        menuObjects.put(name, o);
    }
}
