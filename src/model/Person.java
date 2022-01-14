/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import Main.MainClass;
import java.awt.Frame;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sijan
 */
public final class Person {

    public static String USERNAME = "DemoUser";
    public static String Email = "0000000000";
    public static String path=System.getProperty("user.home");;
    public static File file=new File(path + "/AskChat.txt");
    public static boolean isFirstOpen = true;

    public Person() {
         getServerFile();
        isFirstOpen = false;
    }

    public static void setPerson(String user, String num) {
        USERNAME = user;
        Email = num;
        storeUser();
    }

    static void storeUser() {
        try {
            file.delete();
            try (BufferedWriter bf = new BufferedWriter(new FileWriter(file))) {
                bf.append(USERNAME);
                bf.newLine();
                bf.append(Email);
            }
        } catch (IOException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void initUser() {
        try (BufferedReader bf = new BufferedReader(new FileReader(file))) {
            USERNAME = bf.readLine();
            Email = bf.readLine();
            bf.close();
        } catch (IOException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean isUserLogged() {
        
        return new File(path + "/AskChat.txt").exists();
    }

    public static void getServerFile() {
        if (!new File(path + "/serverfile.json").exists()) {
            try (BufferedInputStream inputStream = new BufferedInputStream(new URL("https://firebasestorage.googleapis.com/v0/b/jmav-2bc1e.appspot.com/o/jmav-2bc1e-firebase-adminsdk-i8nm4-06575f36cb.json?alt=media&token=a0c66fae-6457-4e85-949c-37f514a2c814").openStream()); FileOutputStream fileOS = new FileOutputStream(path + "/serverfile.json")) {
                byte data[] = new byte[1024];
                int byteContent;
                while ((byteContent = inputStream.read(data, 0, 1024)) != -1) {
                    fileOS.write(data, 0, byteContent);
                }
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }

    public static void logout(Frame f) {
        file.delete();
        String[] args = null;
        MainClass.main(args);
    }

}
