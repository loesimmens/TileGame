/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.states;

import java.awt.Color;
import java.awt.Graphics;
import tilegame.Game;
import tilegame.entities.creatures.Creature;
import tilegame.gfx.Assets;
import tilegame.gfx.Text;
import tilegame.ui.ClickListener;
import tilegame.ui.UIImageButton;
import tilegame.ui.UIManager;
import tilegame.ui.UIObject;

/**
 * based on CodeNMore's tutorial, see: https://github.com/CodeNMore/New-Beginner-Java-Game-Programming-Src
 * expanded on by Loes Immens
 */
public class MenuState extends State
{
    private UIManager uiManager;
    
    public MenuState(Game game)
    {
        super(game);
    }

    @Override
    public void tick() 
    {
        
    }

    @Override
    public void render(Graphics g) 
    {
        g.drawImage(Assets.getAssets().creatureAnimMap.get(1).get("down")[0], 
                128, 96, Creature.DEFAULT_CREATURE_WIDTH * 2, Creature.DEFAULT_CREATURE_HEIGHT * 2, null);
        g.drawImage(Assets.getAssets().creatureAnimMap.get(0).get("down")[0], 
                256, 64, Creature.DEFAULT_CREATURE_WIDTH * 2, Creature.DEFAULT_CREATURE_HEIGHT * 2, null);
        g.drawImage(Assets.getAssets().creatureAnimMap.get(2).get("down")[0], 
                384, 96, Creature.DEFAULT_CREATURE_WIDTH * 2, Creature.DEFAULT_CREATURE_HEIGHT * 2, null);
        
        Text.drawString(g, "Liekes verjaardag!", 152, 256, false, Color.red, Assets.getAssets().font28);
    }
}
