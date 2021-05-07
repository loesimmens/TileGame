/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.dialogue;

import tilegame.Game;
import tilegame.entities.StateMachine;
import tilegame.entities.creatures.Player;
import tilegame.gfx.Assets;
import tilegame.gfx.Text;
import tilegame.utils.Listener;
import tilegame.utils.Utils;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 *
 * @author Loes Immens
 */
public class DialogueBox implements java.io.Serializable, Listener
{
    private static final DialogueBox dialogueBox = new DialogueBox();
    private final int x, y, width, height; 
    private int maxTokensPerLine;
    private static boolean active = false;
    private int keyJustPressed = -1;
    private Dialogue dialogue;
    
    private DialogueBox()
    {
        maxTokensPerLine = 55;
        
        x = (int)(Game.getWidth() * 0.05);
        y = (int)(Game.getHeight() * 0.75);
        width = (int)(Game.getWidth() * 0.9);
        height = (int)(Game.getHeight() * 0.2);
    }
    
    public void tick()
    {
        if(!active)
            return;
        if(dialogue.getCurrentNode() == null)
        {
            active = false;
            return;
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
    
    public void render(Graphics g)
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
    
    public static DialogueBox getInstance(){
        return dialogueBox;
    }
    
    public static boolean isActive()
    {
        return active;
    }
    
    public static void setActive(boolean active)
    {
        DialogueBox.active = active;
    }

    @Override
    public void update(Object o) {
        if(Player.getInstance().getState().equals(StateMachine.INTERACTING)){
            if(o instanceof Integer){
                int keyCode = (Integer) o;
                if(keyCode > 0){
                    if(keyCode == KeyEvent.VK_1) //1
                        setCurrentNode(dialogue, 1);
                    else if(keyCode == KeyEvent.VK_2) //2
                        setCurrentNode(dialogue, 2);
                    if(dialogue.getCurrentNode().getOptions().isEmpty() && keyCode == KeyEvent.VK_E)
                    {
                        active = false;
                        dialogue.setCurrentNode(1);
                    }
                }
            }
        }
    }

    public void setDialogue(Dialogue dialogue) {
        this.dialogue = dialogue;
    }
}
