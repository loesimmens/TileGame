/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.gfx;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * based on CodeNMore's tutorial, see: https://github.com/CodeNMore/New-Beginner-Java-Game-Programming-Src
 * expanded on by Loes Immens
 */
public class ImageLoader 
{
    public static BufferedImage loadImage(String path)
    {
        try 
        {
            URL url = new File(path).toURI().toURL();
            return ImageIO.read(url);
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }
}
