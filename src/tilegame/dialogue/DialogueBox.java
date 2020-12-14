/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.dialogue;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import tilegame.gfx.Assets;
import tilegame.gfx.Text;
import tilegame.utils.Utils;
import tilegame.worlds.World;

/**
 *
 * @author Loes Immens
 */
public class DialogueBox implements java.io.Serializable
{
    private final int x, y, width, height; 
    private int maxTokensPerLine;
    private boolean active = false;
    private World world;
    
    public DialogueBox(World world)
    {
        maxTokensPerLine = 55;
        this.world = world;
        
        x = (int)(world.getGameState().getGame().getWidth() * 0.05);
        y = (int)(world.getGameState().getGame().getHeight() * 0.75);
        width = (int)(world.getGameState().getGame().getWidth() * 0.9);
        height = (int)(world.getGameState().getGame().getHeight() * 0.2);
        
    }
    
    public void tick(Dialogue dialogue)
    {
        if(!active)
            return;
        if(dialogue.getCurrentNode() == null)
        {
            active = false;
            return;
        }
        
        if(world.getGameState().getGame().getKeyManager().hasJustBeenPressed(KeyEvent.VK_1)) //1
            setCurrentNode(dialogue, 1);
        else if(world.getGameState().getGame().getKeyManager().hasJustBeenPressed(KeyEvent.VK_2)) //2
            setCurrentNode(dialogue, 2);
        
        if(dialogue.getCurrentNode().getOptions().isEmpty() &&
                world.getGameState().getGame().getKeyManager().hasJustBeenPressed(KeyEvent.VK_E))
        {
            active = false;
            dialogue.setCurrentNode(1);
        }
    }
    
    public void render(Graphics g, Dialogue dialogue)
    {
        if(!active)
            return;
        if(dialogue.getCurrentNode() == null)
        {
            active = false;
            return;
        }
        g.drawImage(Assets.getAssets().imageMap.get("dialogueBox"), x, y, width, height, null);
        displayCurrentNode(g, dialogue.getCurrentNode());
    }
    
    public void displayCurrentNode(Graphics g, DialogueNode node)
    {
        int optionCounter = 1;
        int textX = x + 20;
        int textY = y + 20;
        
        String textToDisplay = Utils.cutTextToFitLine(node.getText(), maxTokensPerLine);
        
        for(String line: textToDisplay.split("\n"))
        {
            Text.drawString(g, line, textX, textY, false, Color.black, Assets.getAssets().font14);
            textY += 15;
        }
        
        for(DialogueOption o : node.getOptions().values())
        {
            textToDisplay = Utils.cutTextToFitLine(o.getText(), maxTokensPerLine);
            
            boolean firstLine = true;
            for(String line: textToDisplay.split("\n"))
            {
                if(firstLine)
                    Text.drawString(g, optionCounter + ". " + line, textX, textY, false, Color.blue, Assets.getAssets().font14);
                else
                    Text.drawString(g, "   " + line, textX, textY, false, Color.blue, Assets.getAssets().font14);
                firstLine = false;
                textY += 15;
            }
            
            optionCounter++;
        }
    }

    private void setCurrentNode(Dialogue dialogue, int option) 
    {
        if(!dialogue.getCurrentNode().getOptions().isEmpty())
            dialogue.setCurrentNode(dialogue.getCurrentNode().getOptions().get(option).getDestinationNodeID());
        else
        {
            System.out.println("No option " + option + " available!");
            dialogue.setCurrentNode(1);
        }
    }
    
    //GETTERS SETTERS
    
    public boolean isActive() 
    {
        return active;
    }
    
    public void setActive(boolean active) 
    {
        this.active = active;
    }
}
