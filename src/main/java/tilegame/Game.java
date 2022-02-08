/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame;

import org.springframework.stereotype.Controller;
import tilegame.dialogue.DialogueBox;
import tilegame.display.Display;
import tilegame.entities.creatures.Player;
import tilegame.entities.exceptions.PlayerException;
import tilegame.game_elements.Ticking;
import tilegame.gfx.Assets;
import tilegame.input.KeyManager;
import tilegame.input.MouseManager;
import tilegame.inventory.InventoryPanel;
import tilegame.logger.TileGameLogger;
import tilegame.states.*;
import tilegame.tiles.TileManager;
import tilegame.ui.MenuService;
import tilegame.ui.UIManager;

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
@Controller
public class Game implements Ticking
{
    private static final Game game = new Game();

    private static Display display;
    private static int displayWidth;
    private static int displayHeight;
    
    private boolean running = false;

    private static State gameState;
    private static State menuState;
    private static State optionsState;

    private static KeyManager keyManager;
    private static MouseManager mouseManager;

    private static UIManager uiManager;

    private BufferStrategy bufferStrategy;
    private Graphics graphics;

    private static final Logger LOGGER = TileGameLogger.getLogger();
    private static final String PLAYER_NOT_CREATED_YET = "Player not created yet!";
            
    private Game(){}

    public static Game getInstance()
    {
        return game;
    }
    
    public static void init(int width, int height)
    {
        Game.displayWidth = width;
        Game.displayHeight = height;
        Assets.getAssets().init();
        initStates();
        keyManager = KeyManager.getInstance();
        Player player;
        try {
            player = Player.getInstance();
            List<Integer> keysSubscribedTo = List.of(KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_E, KeyEvent.VK_X, -1);
            KeyManager.subscribe(player, keysSubscribedTo);
            LOGGER.info("Player has subscribed to keys " + keysSubscribedTo);
        } catch (PlayerException e) {
            LOGGER.log(Level.SEVERE, PLAYER_NOT_CREATED_YET, e);
        }
        List<Integer> keysSubscribedTo = List.of(KeyEvent.VK_1, KeyEvent.VK_2, KeyEvent.VK_E, -1);
        KeyManager.subscribe(DialogueBox.getInstance(), keysSubscribedTo);
        LOGGER.info("DialogueBox has subscribed to keys " + keysSubscribedTo);

        keysSubscribedTo = List.of(KeyEvent.VK_I, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_E, -1);
        KeyManager.subscribe(InventoryPanel.getInstance(), keysSubscribedTo);
        LOGGER.info("InventoryPanel has subscribed to keys " + keysSubscribedTo);

        mouseManager = new MouseManager();
        
        initDisplay();

        TileManager.getTileManager().init();

        uiManager = new UIManager();
        mouseManager.setUIManager(uiManager);
        var menuService = new MenuService(uiManager, mouseManager, gameState, optionsState);

        menuService.addStartButtonToMenu(width);
        menuService.addLoadButtonToMenu(width);
        menuService.addSaveButtonToMenu(width);
        menuService.addOptionsButtonToMenu(width);
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

    @Override
    public void tick()
    {
        if(StateManager.getStateManager().getState() != null)
        {
            keyManager.tick();
            StateManager.getStateManager().getState().tick();
        }
        if(StateManager.getStateManager().getState() != menuState && keyManager.hasJustBeenPressed(KeyEvent.VK_ESCAPE)) {
                mouseManager.setUIManager(uiManager);
                StateManager.getStateManager().setState(menuState);
        }
    }

    private void render() {
        initGraphics();
        clearScreen();
        renderState();
        stopDrawing();
    }

    private void initGraphics() {
        bufferStrategy = display.getCanvas().getBufferStrategy();
        if(bufferStrategy == null)
        {
            display.getCanvas().createBufferStrategy(3);
            bufferStrategy = display.getCanvas().getBufferStrategy();
        }
        graphics = bufferStrategy.getDrawGraphics();
    }

    private void clearScreen() {
        graphics.clearRect(0, 0, displayWidth, displayHeight);
    }

    private void renderState() {
        if(StateManager.getStateManager().getState() != null)
        {
            StateManager.getStateManager().getState().render(graphics);
            uiManager.render(graphics);
        }
    }

    private void stopDrawing() {
        bufferStrategy.show();
        graphics.dispose();
    }

    public synchronized void start()
    {
        if(!running) {
            running = true;
            LOGGER.info("Game has started");
            var fps = 60;
            double timePerTick = (double) 1000000000 / fps;
            double delta = 0;
            long lastTime = System.nanoTime();
            long timer = 0;
            var ticks = 0;

            gameLoop(timePerTick, delta, lastTime, timer, ticks);

            stop(); //todo: never reached?
        }
    }

    public synchronized void stop() {
        if(running) {
            running = false;
            LOGGER.info("Game has stopped");
        }
    }

    private void gameLoop(double timePerTick, double delta, long lastTime, long timer, int ticks) {
        long now;
        while(running)
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

            if(timer >= 1000000000) {
                ticks = 0;
                timer = 0;
            }
        }
    }

    public static int getDisplayWidth()
    {
        return displayWidth;
    }
    
    public static int getDisplayHeight()
    {
        return displayHeight;
    }

    public static GameState getGameState()
    {
        return (GameState)gameState;
    }

    public static State getMenuState() {
        return menuState;
    }
}
