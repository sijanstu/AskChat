/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MainClass;
import UI.Dash;
import UI.Login;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;

public class MainLoader {
    static Login login;
    static Dash dash;
    public static void main(String[] args) {
       // login.main();
        /* Use an appropriate Look and Feel */
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            //UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException | ClassNotFoundException ex) {
            System.out.println(ex);
        }
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        //Schedule a job for the event-dispatching thread:
        //adding TrayIcon.
        SwingUtilities.invokeLater(MainLoader::createAndShowGUI);
    }
     
    private static void createAndShowGUI() {
        //Check the SystemTray support
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }
        final PopupMenu popup = new PopupMenu();
        final TrayIcon trayIcon =
                new TrayIcon(createImage("/Images/trayicon.png", "tray icon"));
        final SystemTray tray = SystemTray.getSystemTray();
         
        // Create a popup menu components
        MenuItem aboutItem = new MenuItem("About");
        CheckboxMenuItem cb1 = new CheckboxMenuItem("Set auto size");
        CheckboxMenuItem cb2 = new CheckboxMenuItem("Set tooltip");
        Menu displayMenu = new Menu("Display");
        MenuItem errorItem = new MenuItem("Error");
        MenuItem warningItem = new MenuItem("Warning");
        MenuItem infoItem = new MenuItem("Info");
        MenuItem noneItem = new MenuItem("None");
        MenuItem exitItem = new MenuItem("Exit");
         
        //Add components to popup menu
        popup.add(aboutItem);
        popup.addSeparator();
        popup.add(cb1);
        popup.add(cb2);
        popup.addSeparator();
        popup.add(displayMenu);
        displayMenu.add(errorItem);
        displayMenu.add(warningItem);
        displayMenu.add(infoItem);
        displayMenu.add(noneItem);
        popup.add(exitItem);
         
        trayIcon.setPopupMenu(popup);
         
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
            return;
        }
         
        trayIcon.addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(null,
                    "This dialog box is run from System Tray");
        });
         
        aboutItem.addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(null,
                    "This dialog box is run from the About menu item");
        });
         
        cb1.addItemListener((ItemEvent e) -> {
            int cb1Id = e.getStateChange();
            if (cb1Id == ItemEvent.SELECTED){
                trayIcon.setImageAutoSize(true);
            } else {
                trayIcon.setImageAutoSize(false);
            }
        });
         
        cb2.addItemListener((ItemEvent e) -> {
            int cb2Id = e.getStateChange();
            if (cb2Id == ItemEvent.SELECTED){
                trayIcon.setToolTip("Sun TrayIcon");
            } else {
                trayIcon.setToolTip(null);
            }
        });
         
        ActionListener listener = (ActionEvent e) -> {
            MenuItem item = (MenuItem)e.getSource();
            //TrayIcon.MessageType type = null;
            System.out.println(item.getLabel());
            if (null != item.getLabel()) switch (item.getLabel()) {
                case "Error":
                    //type = TrayIcon.MessageType.ERROR;
                    trayIcon.displayMessage("Sun TrayIcon Demo",
                            "This is an error message", TrayIcon.MessageType.ERROR);
                    break;
                case "Warning":
                    //type = TrayIcon.MessageType.WARNING;
                    trayIcon.displayMessage("Sun TrayIcon Demo",
                            "This is a warning message", TrayIcon.MessageType.WARNING);
                    break;
                case "Info":
                    //type = TrayIcon.MessageType.INFO;
                    trayIcon.displayMessage("Sun TrayIcon Demo",
                            "This is an info message", TrayIcon.MessageType.INFO);
                    break;
                case "None":
                    //type = TrayIcon.MessageType.NONE;
                    trayIcon.displayMessage("Sun TrayIcon Demo",
                            "This is an ordinary message", TrayIcon.MessageType.NONE);
                    break;
                default:
                    break;
            }
        };
         
        errorItem.addActionListener(listener);
        warningItem.addActionListener(listener);
        infoItem.addActionListener(listener);
        noneItem.addActionListener(listener);
         
        exitItem.addActionListener((ActionEvent e) -> {
            tray.remove(trayIcon);
            System.exit(0);
        });
    }
     
    //Obtain the image URL
    protected static Image createImage(String path, String description) {
        URL imageURL = MainLoader.class.getResource(path);
         
        if (imageURL == null) {
            System.err.println("Resource not found: " + path);
            return null;
        } else {
            return (new ImageIcon(imageURL, description)).getImage();
        }
    }
}