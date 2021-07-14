/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame;

import tilegame.dialogue.DialogueBox;
import tilegame.display.Display;
import tilegame.entities.creatures.Player;
import tilegame.entities.exceptions.PlayerException;
import tilegame.gfx.Assets;
import tilegame.gfx.GameCamera;
import tilegame.input.KeyManager;
import tilegame.input.MouseManager;
import tilegame.inventory.InventoryPanel;
import tilegame.logger.TileGameLogger;
import tilegame.saves.ResourceManager;
import tilegame.saves.SaveData;
import tilegame.states.*;
import tilegame.tiles.TileManager;
import tilegame.ui.ClickListener;
import tilegame.ui.UIManager;
import tilegame.ui.UITextButton;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * based on CodeNMore's tutorial, see: https://github.com/CodeNMore/New-Beginner-Java-Game-Programming-Src
 * expanded on by Loes Immens
 */
public class Game implements Runnable
{
    private static final Game game = new Game();
    private static final String PLAYER_NOT_CREATED_YET = "Player not created yet!";
    private static final String BTN_EMPTY = "btn_empty";

    private static Display display;
    private static int width;
    private static int height;
    
    private boolean running = false;
    private Thread thread;
    
    //States
    private static State gameState;
    private static State menuState;
    private static State optionsState;
    
    //Input
    private static KeyManager keyManager;
    private static MouseManager mouseManager;

    private static UIManager uiManager;

    private static GameCamera gameCamera;

    private static final Logger LOGGER = TileGameLogger.getLogger();
            
    private Game(){}
    
    public static void init(int width, int height)
    {
        Game.width = width;
        Game.height = height;
        Assets.getAssets().init();
        initStates();
        keyManager = KeyManager.getInstance();
        Player player;
        try {
            player = Player.getInstance();
            KeyManager.subscribe(player, List.of(KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_E, KeyEvent.VK_X, -1));
        } catch (PlayerException e) {
            LOGGER.log(Level.SEVERE, PLAYER_NOT_CREATED_YET, e);
        }
        KeyManager.subscribe(DialogueBox.getInstance(), List.of(KeyEvent.VK_1, KeyEvent.VK_2, KeyEvent.VK_E, -1));
        KeyManager.subscribe(InventoryPanel.getInstance(), List.of(KeyEvent.VK_I, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_E, -1));
        mouseManager = new MouseManager();
        
        initDisplay();

        TileManager.getTileManager().init();
        
        gameCamera = GameCamera.getInstance();

        uiManager = new UIManager();
        mouseManager.setUIManager(uiManager);
  
        uiManager.addObjectToMenu("startButton", new UITextButton(width / 2 - 80, 256, 160, 32, Assets.getAssets().imageArrayMap.get(BTN_EMPTY), "START", () -> {
            mouseManager.setUIManager(null);
            StateManager.getStateManager().setState(gameState);
        }
        ));
        uiManager.addObjectToMenu("loadButton", new UITextButton(width / 2 - 80, 304, 160, 32, Assets.getAssets().imageArrayMap.get(BTN_EMPTY), "LOAD", () -> {
            try
            {
                SaveData data = (SaveData) ResourceManager.load("1.save");
                try {
                    Player.getInstance().setHealth(data.getHealth());
                    Player.getInstance().setX(data.getX());
                    Player.getInstance().setY(data.getY());
                } catch(PlayerException e) {
                    LOGGER.log(Level.SEVERE, PLAYER_NOT_CREATED_YET, e);
                }
                //todo: getGameState().getInventory().setInventoryItems(data.getInventoryItems());
                mouseManager.setUIManager(null);
                StateManager.getStateManager().setState(gameState);
            }
            catch(Exception e)
            {
                System.out.println("Couldn't load save data: " + e.getMessage());
            }
        }
        ));
        uiManager.addObjectToMenu("saveButton", new UITextButton(width / 2 - 80, 352, 160, 32, Assets.getAssets().imageArrayMap.get(BTN_EMPTY), "SAVE", new ClickListener()
        {
            @Override
            public void onClick() 
            {
                SaveData data = new SaveData();
                data.setName("player");
                try {
                    data.setHealth(Player.getInstance().getHealth());
                    data.setX(Player.getInstance().getX());
                    data.setY(Player.getInstance().getY());
                } catch (PlayerException e) {
                    LOGGER.log(Level.SEVERE, PLAYER_NOT_CREATED_YET, e);
                }
                //data.setInventoryItems(getGameState().getInventory().getInventoryItems());
                
                try
                {
                    ResourceManager.save(data, "1.save");
                }
                catch(Exception e)
                {
                    System.out.println("Couldn't save: " + e.getMessage());
                }
            }
        }
        ));
        uiManager.addObjectToMenu("optionsButton", new UITextButton(width / 2 - 80, 400, 160, 32, Assets.getAssets().imageArrayMap.get(BTN_EMPTY), "OPTIONS", new ClickListener()
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
    
    private static void initDisplay()
    {
        display = Display.getInstance();
        display.getFrame().addKeyListener(keyManager);
        display.getFrame().addMouseListener(mouseManager);
        display.getFrame().addMouseMotionListener(mouseManager);
        display.getCanvas().addMouseListener(mouseManager);
        display.getCanvas().addMouseMotionListener(mouseManager);
    }
    
    private static void initStates()
    {
        gameState = new GameState();
        menuState = new MenuState();
        optionsState = new OptionsState();
        StateManager.getStateManager().setState(menuState);
    }

    private void tick()
    {
        if(StateManager.getStateManager().getState() != null)
        {
            keyManager.tick();
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

    private void render()
    {
        BufferStrategy bufferStrategy = display.getCanvas().getBufferStrategy(); //way for computer to draw things to a screen using buffers (buffer is hidden "screen" within computer) or how many buffers it's going to use
        if(bufferStrategy == null)
        {
            display.getCanvas().createBufferStrategy(3); //create bufferstrategy using 3 buffers
            return;
        }
        Graphics graphics = bufferStrategy.getDrawGraphics(); //graphics allows you to draw images to canvas like a paintbrush
        //Clear Screen
        graphics.clearRect(0, 0, width, height);
        //Draw Here!
        
        if(StateManager.getStateManager().getState() != null)
        {
            StateManager.getStateManager().getState().render(graphics);
            uiManager.render(graphics);
        }

        //End Drawing!
        bufferStrategy.show();
        graphics.dispose();
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
    
    public static Game getInstance()
    {
        return game;
    }
    
    public GameCamera getGameCamera()
    {
        return gameCamera;
    }
    
    public static int getWidth()
    {
        return width;
    }
    
    public static int getHeight()
    {
        return height;
    }

    public static GameState getGameState()
    {
        return (GameState)gameState;
    }

    public static State getMenuState() {
        return menuState;
    }

    public static State getOptionsState() {
        return optionsState;
    }
}
