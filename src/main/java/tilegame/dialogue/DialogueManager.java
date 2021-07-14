/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.dialogue;

import tilegame.logger.TileGameLogger;
import tilegame.utils.Listener;
import tilegame.utils.Utils;

import java.awt.*;
import java.util.HashMap;
import java.util.logging.Logger;

/**
 * @author Loes Immens
 * todo: singleton, static methods, in interface Talking
 */
public class DialogueManager implements java.io.Serializable, Listener
{
    private static final DialogueManager dialogueManager = new DialogueManager();
    private HashMap<Long, Dialogue> dialogueMap;
    private DialogueBox dialogueBox;
    private Dialogue currentDialogue;
    private Logger logger = TileGameLogger.getLogger();

    private DialogueManager(){
        dialogueMap = new HashMap<>();
        dialogueBox = DialogueBox.getInstance();
        loadDialogues("res/dialogues/dialogues.txt");
        currentDialogue = null;
        logger.info("DialogueManager created");
    }

    //todo: Files.readAllLines(Path p) of Files.lines(Path p)
    public void loadDialogues(String path)
    {
        String file = Utils.loadFileAsString(path);
        String[] tokens = file.split("\\r?\\n");
        
        int counter = 0;
        while(counter < tokens.length - 4 && tokens[counter].equals("creature"))
        {
            counter += 1;
            long creatureID = Long.parseLong(tokens[counter]);
            int nNodes = Utils.parseInt(tokens[counter + 1]);
            Dialogue dialogue = new Dialogue(creatureID, nNodes);
            counter += 2;
            while(counter < tokens.length - 2 && !tokens[counter].equals("creature")) //create dialogueNodes with dialogueOptions
            {
                if(tokens[counter].equals("node"))
                {
                    int id = Utils.parseInt(tokens[counter + 1]);
                    String text = tokens[counter + 2];

                    if(id == 0  || text == null)
                        return;

                    DialogueNode node = new DialogueNode(id, text);
                    dialogue.addNode(node);

                    counter += 3;
                    String optionText;
                    int optionDestination;
                    int optionCounter = 1;

                    while(counter < tokens.length - 1 && !tokens[counter].equals("node")&& !tokens[counter].equals("creature"))
                    {
                        optionText = tokens[counter];
                        optionDestination = Utils.parseInt(tokens[counter + 1]);
                        dialogue.addOption(optionCounter, optionText, node, optionDestination);
                        optionCounter++;
                        counter += 2;
                    }
                }
            }
            dialogueMap.put(creatureID, dialogue);
            dialogue.setCurrentNode(1);
        }
    }
    
    public void tick()
    {
        if(dialogueBox.isActive())
            dialogueBox.tick();
    }
            
    
    public void render(Graphics g)
    {
        if(dialogueBox.isActive())
            dialogueBox.render(g);
    }
    
    @Override
    public String toString()
    {
        return "dialogue manager";
    }

    public DialogueBox getDialogueBox() 
    {
        return dialogueBox;
    }

    @Override
    public void update(Object id) {
        if(id instanceof Long){
            currentDialogue = dialogueMap.get(id);
            dialogueBox.setDialogue(currentDialogue);
        }
        else
            throw new IllegalArgumentException("id is not a Long!");
    }

    public static DialogueManager getInstance(){
        return dialogueManager;
    }
}
