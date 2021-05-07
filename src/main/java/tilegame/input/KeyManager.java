/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.input;

import tilegame.utils.Listener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * based on CodeNMore's tutorial, see: https://github.com/CodeNMore/New-Beginner-Java-Game-Programming-Src
 * expanded on by Loes Immens
 */
public class KeyManager implements KeyListener
{
    private static final KeyManager keyManager = new KeyManager();
    private boolean[] keys, justPressed, cantPress;
    public boolean up, down, left, right,
        interact, attack,
            one, two;
    private static HashMap<Integer, List<Listener>> listeners = new HashMap<>();
    
    private KeyManager()
    {
        keys = new boolean[256];
        justPressed = new boolean[keys.length];
        cantPress = new boolean[keys.length];
    }

    public static final KeyManager getInstance(){
        return keyManager;
    }
    
    public void tick()
    {
        for(int i = 0; i < keys.length; i++)
        {
            if(pressedThenReleased(i))
            {
                cantPress[i] = false;
                notify(-1);
            }
            else if(justPressed[i])
            {
                cantPress[i] = true;
                justPressed[i] = false;
            }
            if(!cantPress[i] && keys[i])
            {
                justPressed[i] = true;
                notify(i);
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

    private boolean pressedThenReleased(int i) {
        return cantPress[i] && !keys[i];
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
    public void keyTyped(KeyEvent e){}

    public static void subscribe(Listener listener, List<Integer> keys){

        for(Integer key : keys){
            if(!listeners.containsKey(key)) {
                listeners.put(key, new ArrayList<>());
            }
            listeners.get(key).add(listener);
        }
    }

    //todo: eerst player, dan dialogueBox
    public void notify(Integer keyCode){
        if(listeners.containsKey(keyCode)){
            for(Listener listener: listeners.get(keyCode)){
                listener.update(keyCode);
            }
        }
    }
}
