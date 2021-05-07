/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.items;

import java.awt.image.BufferedImage;

/**
 *
 * @author Loes Immens
 */
public class Note extends Item
{
    private String text;
    private boolean active;
    
    public Note(BufferedImage texture, String name, int id, String text) 
    {
        super(texture, name, id);
        
        this.text = text;
        active = false;
    }

    public String getText() 
    {
        return text;
    }
    
    public void setActive(boolean active)
    {
        this.active = active;
    }

    public boolean isActive() 
    {
        return active;
    }
    
    
    
}
