/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * based on CodeNMore's tutorial, see: https://github.com/CodeNMore/New-Beginner-Java-Game-Programming-Src
 * expanded on by Loes Immens
 */
@SpringBootApplication
public class Launcher 
{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(Launcher.class).headless(false).run(args);
        context.getBean(Game.class);
        Game.init(640, 480);
        Game.getInstance().start();
    }
}
