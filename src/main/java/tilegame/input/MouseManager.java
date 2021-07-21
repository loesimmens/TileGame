/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.input;

import tilegame.ui.UIManager;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * based on CodeNMore's tutorial, see: https://github.com/CodeNMore/New-Beginner-Java-Game-Programming-Src
 * expanded on by Loes Immens
 */
public class MouseManager implements MouseListener, MouseMotionListener
{
    private boolean leftPressed, rightPressed;
    private int mouseX, mouseY;
    private UIManager uiManager;
    
    public void setUIManager(UIManager uiManager)
    {
        this.uiManager = uiManager;
    }

    @Override
    public void mousePressed(MouseEvent e) 
    {
        if(e.getButton() == MouseEvent.BUTTON1)
            leftPressed = true;
        else if(e.getButton() == MouseEvent.BUTTON3)
            rightPressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) 
    {
        if(e.getButton() == MouseEvent.BUTTON1)
            leftPressed = false;
        else if(e.getButton() == MouseEvent.BUTTON3)
            rightPressed = false;
        
        if(uiManager != null)
            uiManager.onMouseRelease(e);
    }
    
    @Override
    public void mouseMoved(MouseEvent e) 
    {
        mouseX = e.getX();
        mouseY = e.getY();
        
        if(uiManager != null)
            uiManager.onMouseMove(e);
    }
    
    @Override
    public void mouseClicked(MouseEvent e) 
    {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) 
    {
        
    }

    @Override
    public void mouseExited(MouseEvent e) 
    {
        
    }

    @Override
    public void mouseDragged(MouseEvent e) 
    {
        
    }
}
