/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.states;

import java.awt.Graphics;
import tilegame.Game;

/**
 * based on CodeNMore's tutorial, see: https://github.com/CodeNMore/New-Beginner-Java-Game-Programming-Src
 * expanded on by Loes Immens
 */
public abstract class State 
{
    protected Game game;
    
    public State(Game game)
    {
        this.game = game;
    }
    
    public abstract void tick();
    
    public abstract void render(Graphics g);
}
