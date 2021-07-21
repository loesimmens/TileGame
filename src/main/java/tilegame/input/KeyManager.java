/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.input;

import tilegame.game_elements.Ticking;
import tilegame.logger.TileGameLogger;
import tilegame.utils.Listener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

/**
 * based on CodeNMore's tutorial, see: https://github.com/CodeNMore/New-Beginner-Java-Game-Programming-Src
 * expanded on by Loes Immens
 */
public class KeyManager implements KeyListener, Ticking
{
    private static final KeyManager keyManager = new KeyManager();
    private final boolean[] keys;
    private final boolean[] justPressed;
    private final boolean[] cantPress;
    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;
    private boolean interact;
    private boolean attack;
    private boolean one;
    private boolean two;

    private static final HashMap<Integer, List<Listener>> LISTENERS = new HashMap<>();
    private static final Logger LOGGER = TileGameLogger.getLogger();
    
    private KeyManager()
    {
        keys = new boolean[256];
        justPressed = new boolean[keys.length];
        cantPress = new boolean[keys.length];
    }

    public static KeyManager getInstance(){
        return keyManager;
    }

    @Override
    public void tick() {
        for(var i = 0; i < keys.length; i++) {
            if(pressedThenReleased(i)) {
                cantPress[i] = false;
                notify(-1);
                LOGGER.info("Notify all listeners that key " + i + " has just been released");
            }
            else if(justPressed[i]) {
                cantPress[i] = true;
                justPressed[i] = false;
                LOGGER.info("Can't press key " + i + " again, because it has just been pressed");
            }
            if(!cantPress[i] && keys[i]) {
                justPressed[i] = true;
                notify(i);
                LOGGER.info("Notify all listeners that key " + i + " has just been pressed");
            }
        }

        up = keys[KeyEvent.VK_W];
        if(up) {
            LOGGER.info("UP has been pressed");
        }
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
        if(!(e.getKeyCode() < 0 || e.getKeyCode() > keys.length)) {
            keys[e.getKeyCode()] = true;
            LOGGER.info("Key pressed: " + e.getKeyCode());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) 
    {
        if(!(e.getKeyCode() < 0 || e.getKeyCode() > keys.length)) {
            keys[e.getKeyCode()] = false;
            LOGGER.info("Key released: " + e.getKeyCode());
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException();
    }

    public static void subscribe(Listener listener, List<Integer> keys) {
        for(Integer key : keys) {
            LISTENERS.computeIfAbsent(key, k -> new ArrayList<>());
            LISTENERS.get(key).add(listener);
            LOGGER.info("Listener " + listener + " has subscribed to key " + key);
        }
    }

    public void notify(Integer keyCode) {
        if(LISTENERS.containsKey(keyCode)){
            for(Listener listener: LISTENERS.get(keyCode)){
                listener.update(keyCode);
            }
        }
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isInteract() {
        return interact;
    }

    public void setInteract(boolean interact) {
        this.interact = interact;
    }

    public boolean isAttack() {
        return attack;
    }

    public void setAttack(boolean attack) {
        this.attack = attack;
    }

    public boolean isOne() {
        return one;
    }

    public void setOne(boolean one) {
        this.one = one;
    }

    public boolean isTwo() {
        return two;
    }

    public void setTwo(boolean two) {
        this.two = two;
    }
}
