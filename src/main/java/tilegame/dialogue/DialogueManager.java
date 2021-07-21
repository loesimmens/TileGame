/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.dialogue;

import tilegame.game_elements.Rendering;
import tilegame.game_elements.Ticking;
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
public class DialogueManager implements Listener, Ticking, Rendering
{
    private static final DialogueManager dialogueManager = new DialogueManager();
    private final HashMap<Long, Dialogue> dialogueMap;
    private final DialogueBox dialogueBox;
    private Dialogue currentDialogue;
    private int lineCounter;

    private static final String CREATURE = "creature";
    private static final Logger LOGGER = TileGameLogger.getLogger();

    private DialogueManager(){
        dialogueMap = new HashMap<>();
        dialogueBox = DialogueBox.getInstance();
        loadDialogues("res/dialogues/dialogues.txt");
        currentDialogue = null;
    }

    public void loadDialogues(String path)
    {
        String stringFromFile = Utils.loadFileAsString(path);
        String[] lines = stringFromFile.split("\\r?\\n");

        while(lineCounter < lines.length - 4 && lines[lineCounter].equals(CREATURE))
        {
            lineCounter += 1;
            var creatureID = Long.parseLong(lines[lineCounter]);
            var dialogue = createDialogue(lines, creatureID);
            dialogueMap.put(creatureID, dialogue);
            dialogue.setCurrentNode(1);
        }
    }

    private Dialogue createDialogue(String[] lines, long creatureID) {
        var nNodes = Integer.parseInt(lines[lineCounter + 1]);
        var dialogue = new Dialogue(creatureID, nNodes);
        lineCounter += 2;
        createDialogueNodes(lines, dialogue);
        return dialogue;
    }

    private void createDialogueNodes(String[] lines, Dialogue dialogue) {
        while(lineCounter < lines.length - 2 && !lines[lineCounter].equals(CREATURE)) //create dialogueNodes with dialogueOptions
        {
            if(lines[lineCounter].equals("node"))
            {
                var id = Integer.parseInt(lines[lineCounter + 1]);
                String text = lines[lineCounter + 2];

                var node = new DialogueNode(id, text);
                dialogue.addNode(node);

                lineCounter += 3;
                String optionText;
                int optionDestination;
                var optionCounter = 1;

                while(lineCounter < lines.length - 1 && !lines[lineCounter].equals("node")&& !lines[lineCounter].equals(CREATURE))
                {
                    optionText = lines[lineCounter];
                    optionDestination = Integer.parseInt(lines[lineCounter + 1]);
                    dialogue.addOption(optionCounter, optionText, node, optionDestination);
                    optionCounter++;
                    lineCounter += 2;
                }
            }
        }
    }

    @Override
    public void tick() {
        if(dialogueBox.isActive())
            dialogueBox.tick();
    }
            
    @Override
    public void render(Graphics g) {
        if(dialogueBox.isActive())
            dialogueBox.render(g);
    }
    
    @Override
    public String toString()
    {
        return "dialogue manager";
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
