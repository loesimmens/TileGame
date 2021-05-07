/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame;

/**
 * based on CodeNMore's tutorial, see: https://github.com/CodeNMore/New-Beginner-Java-Game-Programming-Src
 * expanded on by Loes Immens
 */
public class Launcher 
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        Game.getInstance().init(640, 480);
        Game.getInstance().start();
    }
    
}
