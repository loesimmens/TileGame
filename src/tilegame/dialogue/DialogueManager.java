/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.dialogue;

import java.awt.Graphics;
import java.util.HashMap;
import tilegame.entities.Entity;
import tilegame.entities.StateMachine;
import tilegame.utils.Utils;
import tilegame.worlds.World;

/**
 *
 * @author Loes Immens
 */
public class DialogueManager 
{
    private HashMap<Integer, Dialogue> dialogueMap;
    private World world;
    private DialogueBox dialogueBox;
    
    public DialogueManager(World world)
    {
        this.world = world;
        dialogueMap = new HashMap<>();
        dialogueBox = new DialogueBox(world);
    }
    
    public void loadDialogues(String path)
    {
        String file = Utils.loadFileAsString(path);
        String[] tokens = file.split("\\r?\\n");
        
        int counter = 0;
        while(counter < tokens.length - 4 && tokens[counter].equals("creature"))
        {
            counter += 1;
            int creatureID = Utils.parseInt(tokens[counter]);
            int nNodes = Utils.parseInt(tokens[counter + 1]);
            Dialogue dialogue = new Dialogue(creatureID, nNodes);
            //System.out.println(counter + ". New dialogue created for creature " + creatureID + " with " + nNodes + " nodes.");
            counter += 2;
            while(counter < tokens.length - 2 && !tokens[counter].equals("creature")) //create dialogueNodes with dialogueOptions
            {
                if(tokens[counter].equals("node"))
                {
                    //System.out.println("Reading for new node.");
                    int id = Utils.parseInt(tokens[counter + 1]);
                    //System.out.println("Node id: " + id);
                    String text = tokens[counter + 2];

                    if(id == 0  || text == null)
                        return;

                    DialogueNode node = new DialogueNode(id, text);
                    dialogue.addNode(node);
                    //System.out.println(counter + ". New node " + id + " added with text: " + text);

                    counter += 3;
                    String optionText;
                    int optionDestination;
                    int optionCounter = 1;

                    while(counter < tokens.length - 1 && !tokens[counter].equals("node")&& !tokens[counter].equals("creature"))
                    {
                        optionText = tokens[counter];
                        optionDestination = Utils.parseInt(tokens[counter + 1]);
                        dialogue.addOption(optionCounter, optionText, node, optionDestination);
                        //System.out.println(counter + ". Option " + optionCounter + " added with text: " + optionText + " referring to node " + optionDestination);
                        optionCounter++;
                        counter += 2;
                    }
                }
            }
            if(dialogue != null)
            {
                //System.out.println("creatureID: " + creatureID + ", dialogue " + dialogue.getNode(1).getNodeID());
                dialogueMap.put(creatureID, dialogue);
                dialogue.setCurrentNode(1);
            }
        }
    }
    
    public void tick()
    {
        if(!dialogueBox.isActive())
            return;
        for(Entity e : world.getEntityManager().getEntities())
        {
            if(e.getId() != 0 && e.getState() == StateMachine.INTERACTING)
            {
                dialogueBox.tick(dialogueMap.get(e.getId()));
            }
        }
    }
            
    
    public void render(Graphics g)
    {
        if(!dialogueBox.isActive())
            return;
        for(Entity e : world.getEntityManager().getEntities())
        {
            if(e.getId() != 0 && e.getState() == StateMachine.INTERACTING)
            {
                dialogueBox.render(g, dialogueMap.get(e.getId()));
            }
        }
    }
    
    @Override
    public String toString()
    {
        return "dialogue manager";
    }
    
    //GETTERS SETTERS

    public HashMap<Integer, Dialogue> getDialogueMap() 
    {
        return dialogueMap;
    }

    public DialogueBox getDialogueBox() 
    {
        return dialogueBox;
    }
    
    
}
