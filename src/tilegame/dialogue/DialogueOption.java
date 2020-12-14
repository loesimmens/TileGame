/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.dialogue;

/**
 *
 * @author Loes Immens
 */
public class DialogueOption implements java.io.Serializable
{
    private String text;
    private DialogueNode sourceNode;
    private int destinationNodeID;
    private int optionNr;
    
    public DialogueOption(String text, DialogueNode sourceNode, int destinationNodeID)
    {
        this.text = text;
        this.sourceNode = sourceNode;
        this.destinationNodeID = destinationNodeID;
        optionNr = sourceNode.getOptions().size() + 1;
    }

    public String getText() 
    {
        return text;
    }

    public DialogueNode getSourceNodeID() 
    {
        return sourceNode;
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
