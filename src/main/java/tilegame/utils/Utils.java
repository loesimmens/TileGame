/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * based on CodeNMore's tutorial, see: https://github.com/CodeNMore/New-Beginner-Java-Game-Programming-Src
 * expanded on by Loes Immens
 */
public class Utils 
{
    public static String loadFileAsString(String path)
    {
        StringBuilder builder = new StringBuilder();
        
        try(BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while((line = br.readLine())!= null)
                builder.append(line + "\n");
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        
        return builder.toString();
    }
    
    public static String cutTextToFitLine(String text, int maxTokensPerLine) 
    {
        if(text.length() < maxTokensPerLine)
            return text;
        String cutText = "";
        int cutPoint = 0;
        int startOfLine = 0;
        int currentLine = 1;
        for(int i = 0; i < currentLine; i++)
        {
            if((cutPoint + maxTokensPerLine) < text.length())
                cutPoint += maxTokensPerLine - 1;
            else
                cutPoint = text.length() - 1;
            //System.out.println("cut point:" + text.charAt(cutPoint) + ".");
            while(cutPoint != text.length() - 1 && text.charAt(cutPoint) != ' ')
            {
                //System.out.println(text.charAt(cutPoint));
                cutPoint--;
            }
            cutText += text.substring(startOfLine, cutPoint + 1);
            if((text.length() - cutPoint - 1) > 0)
            {
                cutText += "\n";
                currentLine++;
                startOfLine = cutPoint + 1;
            }
            
            //System.out.println(cutText);
        }
        return cutText;
    }
}
