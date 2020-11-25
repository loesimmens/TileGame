/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.dialogue;

import java.util.HashMap;

/**
 *
 * @author Loes Immens
 */
public class DialogueNode 
{
    private int nodeID;
    private String text;
    private HashMap<Integer, DialogueOption> options;
    
    public DialogueNode(int nodeID, String text)
    {
        this.nodeID = nodeID;
        this.text = text;
        options = new HashMap<>();
    }
    
    @Override
    public String toString()
    {
        return "node: " + nodeID;
    }
    
    //GETTERS SETTERS

    public HashMap<Integer, DialogueOption> getOptions() 
    {
        return options;
    }

    public int getNodeID() 
    {
        return nodeID;
    }

    public void setNodeID(int nodeID) 
    {
        this.nodeID = nodeID;
    }

    public String getText() 
    {
        return text;
    }
}
