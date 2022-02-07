/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.ui;

import tilegame.gfx.Assets;
import tilegame.gfx.Text;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *
 * @author Loes Immens
 */
public class UITextButton extends UIObject
{
    private BufferedImage[] images;
    private ClickListener clicker;
    private String text;

    public UITextButton(float x, float y, int width, int height, BufferedImage[] images, String text, ClickListener clicker) 
    {
        super(x, y, width, height);
        
        this.images = images;
        this.clicker = clicker;
        this.text = text;
    }

    @Override
    public void render(Graphics g) 
    {
        int textX = (int)x + width / 2;
        int textY = (int)y + height / 2;
        
        if(hovering)
        {
            g.drawImage(images[1], (int) x, (int) y, width, height, null); //emphasized button image
            Text.drawString(g, text, textX, textY, true, Color.cyan, Assets.getAssets().FONT_28);
        }
        else
        {
            g.drawImage(images[0], (int) x, (int) y, width, height, null); //regular button image
            Text.drawString(g, text, textX, textY, true, Color.black, Assets.getAssets().FONT_28);
        }
    }

    @Override
    public void onClick() 
    {
        clicker.onClick();
    }
    
}
