/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.saves;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *todo: JDBC? MongoDB?
 * @author Loes Immens, based on Almas Baimagambetov's Java Serialization (Save/Load data) video:
 * https://www.youtube.com/watch?v=-xW0pBZqpjU
 */
public class ResourceManager 
{
    public static void save(Serializable data, String fileName) throws Exception
    {
        try(ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName))))
        {
            oos.writeObject(data);
        }
    }
    
    public static Object load(String fileName) throws Exception
    {
        try(ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Paths.get(fileName))))
        {
            return ois.readObject();
        }
    }
}
