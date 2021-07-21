/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.saves;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *todo: database ipv file?
 * @author Loes Immens, based on Almas Baimagambetov's Java Serialization (Save/Load data) video:
 * https://www.youtube.com/watch?v=-xW0pBZqpjU
 */
public class SaveService {

    private SaveService(){}

    public static void save(Serializable data, String fileName) throws SaveException {
        try(var oos = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)))) {
            oos.writeObject(data);
        } catch(IOException e) {
            throw new SaveException("couldn't save to file: " + fileName, e);
        }
    }

    public static SaveData loadSaveData(String fileName) throws SaveException {
        try(var ois = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)))) {
            return (SaveData) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new SaveException("couldn't load from from file: " + fileName, e);
        }
    }
}
