/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.states;

import tilegame.Game;
import tilegame.gfx.Assets;
import tilegame.gfx.Text;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Loes Immens
 */
public class OptionsState implements State {
    private final int leftX = Game.getDisplayWidth() / 3;
    private final int rightX = 2 * Game.getDisplayWidth() / 3;
    private LinkedHashMap<String, String> keySettings;
    
    public OptionsState()
    {
        setKeySettings();
    }

    @Override
    public void tick() 
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void render(Graphics g) 
    {
        var y = 64;
        for(Map.Entry<String, String> me : keySettings.entrySet())
        {
            Text.drawString(g, me.getKey(), leftX, y, true, Color.black, Assets.getAssets().font14);
            Text.drawString(g, me.getValue(), rightX, y, true, Color.blue, Assets.getAssets().font14);
            y += 64;
        }
    }

    private void setKeySettings() 
    {
        keySettings = new LinkedHashMap<>();
        
        keySettings.put("Move", "WASD");
        keySettings.put("Interact", "E");
        keySettings.put("Attack", "X");
        keySettings.put("Inventory", "I");
        keySettings.put("Return to menu", "ESC");
    }
}
