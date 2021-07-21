/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.dialogue;

/**
 * @author Loes Immens
 */
public class DialogueOption implements java.io.Serializable
{
    private String text;
    private int destinationNodeID;
    
    public DialogueOption(String text, int destinationNodeID)
    {
        this.text = text;
        this.destinationNodeID = destinationNodeID;
    }

    public String getText() 
    {
        return text;
    }

    public int getDestinationNodeID() 
    {
        return destinationNodeID;
    }
    
    public String toString()
    {
        return "option: " + text;
    }
}
