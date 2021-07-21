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
public class Dialogue implements java.io.Serializable
{
    private final long creatureID;
    private final int nNodes;
    
    private HashMap<Integer, DialogueNode> nodes;
    private DialogueNode currentNode;
    
    
    public Dialogue(long creatureID, int nNodes)
    {
        this.creatureID = creatureID;
        this.nNodes = nNodes;
        nodes = new HashMap<>();
    }
    
    public void addNode(DialogueNode node)
    {
        if(node == null)
            return;
        if(nodes.get(node.getNodeID()) == null)
            nodes.put(node.getNodeID(), node);
        else
            System.out.println("node with this id already exists!");
    }
    
    public void addOption(int counter, String text, DialogueNode node, int destinationNodeID)
    {
        node.getOptions().put(counter, new DialogueOption(text, destinationNodeID));
    }
    
    @Override
    public String toString()
    {
        return "dialogue for creature: " + creatureID;
    }
    
    public DialogueNode getNode(int nodeID)
    {
        return nodes.get(nodeID);
    }

    public DialogueNode getCurrentNode() 
    {
        return currentNode;
    }

    public void setCurrentNode(int id)
    {
        currentNode = getNode(id);
    }
}
