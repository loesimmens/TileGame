/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * based on CodeNMore's tutorial, see: https://github.com/CodeNMore/New-Beginner-Java-Game-Programming-Src
 * expanded on by Loes Immens
 */
public class KeyManager implements KeyListener
{
    private boolean[] keys, justPressed, cantPress;
    public boolean up, down, left, right,
        interact, attack,
            one, two;
    
    public KeyManager()
    {
        keys = new boolean[256];
        justPressed = new boolean[keys.length];
        cantPress = new boolean[keys.length];
    }    
    
    public void tick()
    {
        for(int i = 0; i < keys.length; i++)
        {
            if(cantPress[i] && !keys[i]) //key has been pressed once but has been released
            {
                cantPress[i] = false;
            }
            else if(justPressed[i])
            {
                cantPress[i] = true;
                justPressed[i] = false;
            }
            if(!cantPress[i] && keys[i])
            {
                justPressed[i] = true;
            }
        }
        
        up = keys[KeyEvent.VK_W];
        down = keys[KeyEvent.VK_S];
        left = keys[KeyEvent.VK_A];
        right = keys[KeyEvent.VK_D];
        
        interact = keys[KeyEvent.VK_E];
        attack = keys[KeyEvent.VK_X];
        
        one = keys[KeyEvent.VK_1];
        two = keys[KeyEvent.VK_2];
    }
    
    public int numberPressed()
    {
        for(int i = 0; i < keys.length; i++)
        {
            if(keys[i])
                return i;
        }
        return -1;
    }
    
    public boolean hasJustBeenPressed(int keyCode)
    {
        if(keyCode < 0 || keyCode >= keys.length)
            return false;
        return justPressed[keyCode];
    }

    @Override
    public void keyPressed(KeyEvent e) 
    {
        if(e.getKeyCode() < 0 || e.getKeyCode() > keys.length)
            return;
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) 
    {
        if(e.getKeyCode() < 0 || e.getKeyCode() > keys.length)
            return;
        keys[e.getKeyCode()] = false;
    }
    
    @Override
    public void keyTyped(KeyEvent e) 
    {
        
    }

    public boolean[] getKeys() {
        return keys;
    }
}
