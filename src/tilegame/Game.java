/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import tilegame.display.Display;
import tilegame.gfx.Assets;
import tilegame.gfx.GameCamera;
import tilegame.input.KeyManager;
import tilegame.input.MouseManager;
import tilegame.states.GameState;
import tilegame.states.MenuState;
import tilegame.states.OptionsState;
import tilegame.states.State;
import tilegame.states.StateManager;
import tilegame.tiles.TileManager;
import tilegame.ui.ClickListener;
import tilegame.ui.UIImageButton;
import tilegame.ui.UIManager;
import tilegame.ui.UITextButton;

/**
 * based on CodeNMore's tutorial, see: https://github.com/CodeNMore/New-Beginner-Java-Game-Programming-Src
 * expanded on by Loes Immens
 */
public class Game implements Runnable
{
    private static Game game = new Game();
    
    private Display display;
    private int width, height;
    public String title;
    
    private boolean running = false;
    private Thread thread;
    
    private BufferStrategy bs;
    private Graphics g;
    
    //States
    public State gameState;
    public State menuState;
    public State optionsState;
    
    //Input
    private KeyManager keyManager;
    private MouseManager mouseManager;
    
    //UI
    private UIManager uiManager;
    
    //Camera
    private GameCamera gameCamera;
            
    private Game(){}
    
    
    public void init(String title, int width, int height)
    {
        this.width = width;
        this.height = height;
        this.title = title;
        keyManager = new KeyManager();
        mouseManager = new MouseManager();
        
        initDisplay();
        
        Assets.getAssets().init();
        TileManager.getTileManager().init();
        
        gameCamera = new GameCamera(this, 0, 0);        
        
        initStates();
        
        uiManager = new UIManager();
        mouseManager.setUIManager(uiManager);
                
//        uiManager.addObjectToMenu("startButton", new UIImageButton(width / 2 - 64, 320, 128, 64, Assets.getAssets().imageArrayMap.get("btn_start"), new ClickListener() 
//        {
//            @Override
//            public void onClick() 
//            {
//                mouseManager.setUIManager(null);
//                StateManager.getStateManager().setState(gameState);
//            }
//        }));
        uiManager.addObjectToMenu("startButton", new UITextButton(width / 2 - 64, 304, 128, 64, Assets.getAssets().imageArrayMap.get("btn_empty"), "START", new ClickListener() 
        {
            @Override
            public void onClick() 
            {
                mouseManager.setUIManager(null);
                StateManager.getStateManager().setState(gameState);
            }
        }
        ));
        uiManager.addObjectToMenu("optionsButton", new UITextButton(width / 2 - 80, 384, 160, 64, Assets.getAssets().imageArrayMap.get("btn_empty"), "OPTIONS", new ClickListener() 
        {
            @Override
            public void onClick() 
            {
                mouseManager.setUIManager(null);
                StateManager.getStateManager().setState(optionsState);
            }
        }
        ));
    }
    
    private void initDisplay()
    {
        display = new Display(title, width, height);
        display.getFrame().addKeyListener(keyManager);
        display.getFrame().addMouseListener(mouseManager);
        display.getFrame().addMouseMotionListener(mouseManager);
        display.getCanvas().addMouseListener(mouseManager);
        display.getCanvas().addMouseMotionListener(mouseManager);
    }
    
    private void initStates() 
    {
        gameState = new GameState(this);
        menuState = new MenuState(this);
        optionsState = new OptionsState(this);
        StateManager.getStateManager().setState(menuState);
    }
    
    //update
    private void tick()
    {
        keyManager.tick();
        
        if(StateManager.getStateManager().getState() != null)
        {
            StateManager.getStateManager().getState().tick();
            uiManager.tick();
        }
        if(StateManager.getStateManager().getState() != menuState)
        {
            if(keyManager.hasJustBeenPressed(KeyEvent.VK_ESCAPE))
            {
                mouseManager.setUIManager(uiManager);
                StateManager.getStateManager().setState(menuState);
            }
        }
        
    }
    
    //draws things to the screen
    private void render()
    {
        bs = display.getCanvas().getBufferStrategy(); //way for computer to draw things to a screen using buffers (buffer is hidden "screen" within computer) or how many buffers it's going to use
        if(bs == null)
        {
            display.getCanvas().createBufferStrategy(3); //create bufferstrategy using 3 buffers
            return;
        }
        g = bs.getDrawGraphics(); //graphics allows you to draw images to canvas like a paintbrush
        //Clear Screen
        g.clearRect(0, 0, width, height);
        //Draw Here!
        
        if(StateManager.getStateManager().getState() != null)
        {
            StateManager.getStateManager().getState().render(g);
            uiManager.render(g);
        }

        //End Drawing!
        bs.show();
        g.dispose();
    }

    @Override
    public void run() 
    {
        int fps = 60;
        double timePerTick = 1000000000 / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime(); //current time of computer in nanoseconds, like a clock
        long timer = 0;
        int ticks = 0;
        
        while(running) //game loop
        {
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            timer += now - lastTime;
            lastTime = now;
            
            if(delta >= 1)
            {
                tick();
                render();
                ticks++;
                delta--;
            }
            
            //check if it runs exactly the right amount of fps
            if(timer >= 1000000000)
            {
                //System.out.println("Ticks and Frames: " + ticks);
                ticks = 0;
                timer = 0;
            }
        }
        
        stop();
    }
    
    //starts seperate thread
    public synchronized void start()
    {
        if(running)
            return;
        running = true;
        thread = new Thread(this);
        thread.start(); // will run run method
    }
    
    public synchronized void stop()
    {
        if(!running)
            return;
        running = false;
        try 
        {
            thread.join();
        }
        catch (InterruptedException e) 
        {
            e.printStackTrace();
        }
    }
    
    public static Game getGame()
    {
        return game;
    }

    public KeyManager getKeyManager()
    {
        return keyManager;
    }
    
    public MouseManager getMouseManager()
    {
        return mouseManager;
    }
    
    public GameCamera getGameCamera()
    {
        return gameCamera;
    }
    
    public int getWidth()
    {
        return width;
    }
    
    public int getHeight()
    {
        return height;
    }

    public GameState getGameState() 
    {
        return (GameState)gameState;
    }
    
    
}
