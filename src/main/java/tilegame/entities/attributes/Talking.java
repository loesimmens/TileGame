package tilegame.entities.attributes;

import tilegame.dialogue.DialogueManager;

public interface Talking {

    //todo: Dialogue doorgeven aan DialogueManager met Observer pattern
    default void talk(long id){
        DialogueManager.getInstance().update(id);
    }
}
