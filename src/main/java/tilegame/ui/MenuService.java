package tilegame.ui;

import tilegame.entities.creatures.Player;
import tilegame.entities.exceptions.PlayerException;
import tilegame.gfx.Assets;
import tilegame.input.MouseManager;
import tilegame.inventory.Inventory;
import tilegame.logger.TileGameLogger;
import tilegame.saves.SaveData;
import tilegame.saves.SaveException;
import tilegame.saves.SaveService;
import tilegame.states.State;
import tilegame.states.StateManager;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MenuService {

    private final UIManager uiManager;
    private final MouseManager mouseManager;
    private final State gameState;
    private final State optionsState;

    private static final Logger LOGGER = TileGameLogger.getLogger();
    private static final String BTN_EMPTY = "btn_empty";
    private static final String PLAYER_NOT_CREATED_YET = "Player not created yet!";

    public MenuService(UIManager uiManager, MouseManager mouseManager, State gameState, State optionsState) {
        this.uiManager = uiManager;
        this.mouseManager = mouseManager;
        this.gameState = gameState;
        this.optionsState = optionsState;
    }

    public void addStartButtonToMenu(int width) {
        uiManager.addObjectToMenu("startButton", new UITextButton(width / 2 - 80, 256, 160, 32, Assets.getAssets().imageArrayMap.get(BTN_EMPTY), "START", () -> {
            mouseManager.setUIManager(null);
            StateManager.getStateManager().setState(gameState);
        }
        ));
    }

    public void addLoadButtonToMenu(int width) {
        uiManager.addObjectToMenu("loadButton", new UITextButton(width / 2 - 80, 304, 160, 32, Assets.getAssets().imageArrayMap.get(BTN_EMPTY), "LOAD", () -> {
            try {
                var saveData = SaveService.loadSaveData("1.save");
                setPlayerSavedData(saveData);
                Inventory.setInventoryItems(saveData.getInventoryItems());
                mouseManager.setUIManager(null);
                StateManager.getStateManager().setState(gameState);
            } catch(SaveException e) {
                LOGGER.log(Level.SEVERE, "Could not load save data", e);
            }
        }
        ));
    }

    private static void setPlayerSavedData(SaveData saveData) {
        try {
            Player.getInstance().setHealth(saveData.getHealth());
            Player.getInstance().setxLocation(saveData.getX());
            Player.getInstance().setyLocation(saveData.getY());
        } catch(PlayerException e) {
            LOGGER.log(Level.SEVERE, PLAYER_NOT_CREATED_YET, e);
        }
    }

    public void addSaveButtonToMenu(int width) {
        uiManager.addObjectToMenu("saveButton", new UITextButton(width / 2 - 80, 352, 160, 32, Assets.getAssets().imageArrayMap.get(BTN_EMPTY), "SAVE", () -> {
            var saveData = new SaveData();
            saveData.setName("player");
            try {
                saveData.setHealth(Player.getInstance().getHealth());
                saveData.setX(Player.getInstance().getxLocation());
                saveData.setY(Player.getInstance().getyLocation());
            } catch (PlayerException e) {
                LOGGER.log(Level.SEVERE, PLAYER_NOT_CREATED_YET, e);
            }

            try
            {
                SaveService.save(saveData, "1.save");
            }
            catch(SaveException e)
            {
                LOGGER.log(Level.SEVERE, "Could not save", e);
            }
        }
        ));
    }

    public void addOptionsButtonToMenu(int width) {
        uiManager.addObjectToMenu("optionsButton", new UITextButton(width / 2 - 80, 400, 160, 32, Assets.getAssets().imageArrayMap.get(BTN_EMPTY), "OPTIONS", () -> {
            mouseManager.setUIManager(null);
            StateManager.getStateManager().setState(optionsState);
        }
        ));
    }
}
