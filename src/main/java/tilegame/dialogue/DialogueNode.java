/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.dialogue;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Loes Immens
 */
public class DialogueNode implements java.io.Serializable
{
    private int nodeID;
    private String text;
    private Map<Integer, DialogueOption> options;
    
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

    public Map<Integer, DialogueOption> getOptions()
    {
        return options;
    }

    public int getNodeID() 
    {
        return nodeID;
    }

    public String getText() 
    {
        return text;
    }
}
