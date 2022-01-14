package TrayHide;

import Main.MainClass;
import UI.Dash;
import com.google.firebase.database.DatabaseReference;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;
import model.Person;

public class Tray {
    static TrayIcon trayIcon;
    static SystemTray tray;
    //public static boolean isopen=false;
    static Dash dash=new Dash();
    public static boolean cannotify=true;
    public static void main() {
        dash.main();
        //isopen=true;
        
        
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException | ClassNotFoundException ex) {
            System.out.println(ex);
        }
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        //Schedule a job for the event-dispatching thread:
        //adding TrayIcon.
        SwingUtilities.invokeLater(Tray::createAndShowGUI);
    }
    private static void createAndShowGUI() {
      //  trayIcon.setImageAutoSize(true);
        //Check the SystemTray support
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }
        final PopupMenu popup = new PopupMenu();
        trayIcon
                = new TrayIcon(createImage("/Images/trayicon.png", "tray icon"));
        tray = SystemTray.getSystemTray();

        // Create a popup menu components
        MenuItem aboutItem = new MenuItem("Open");
        MenuItem LogItem = new MenuItem("Logout");
        CheckboxMenuItem cb1 = new CheckboxMenuItem("Notification");
        cb1.setState(true);
        //CheckboxMenuItem cb2 = new CheckboxMenuItem("Set tooltip");
        Menu displayMenu = new Menu("Test");
        MenuItem errorItem = new MenuItem("Error");
        MenuItem warningItem = new MenuItem("Warning");
        MenuItem infoItem = new MenuItem("Info");
        MenuItem noneItem = new MenuItem("None");
        MenuItem exitItem = new MenuItem("Exit");

        //Add components to popup menu
        popup.add(aboutItem);
        popup.add(LogItem);
        popup.addSeparator();
        popup.add(cb1);
        //popup.add(cb2);
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
            dash.setVisible(true);
            

        });

        aboutItem.addActionListener((ActionEvent e) -> {
          dash.setVisible(true);
            
        });
        
        
        LogItem.addActionListener((ActionEvent e) -> {
            
            //isopen=false;
            dash.dispose();
            Dash.goOffline();
            Person.file.delete();
            tray.remove(trayIcon);
            String[] args = null;
            MainClass.main(args);
        });
        cb1.addItemListener((ItemEvent e) -> {
            int cb1Id = e.getStateChange();
            cannotify = cb1Id == ItemEvent.SELECTED;
            System.out.print(ItemEvent.SELECTED);
        });
        
        ActionListener listener = (ActionEvent e) -> {
            MenuItem item = (MenuItem) e.getSource();
            //TrayIcon.MessageType type = null;
            System.out.println(item.getLabel());
            if (null != item.getLabel()) {
                switch (item.getLabel()) {
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
            }
        };

        errorItem.addActionListener(listener);
        warningItem.addActionListener(listener);
        infoItem.addActionListener(listener);
        noneItem.addActionListener(listener);

        exitItem.addActionListener((ActionEvent e) -> {
          //  tray.remove(trayIcon);
            Dash.goOffline();
            System.exit(0);
        });
    }

    public static void displayInfo(String author, String messege) {
        trayIcon.displayMessage(author,
                messege, TrayIcon.MessageType.INFO);
    }

    public static void close() {
        tray.remove(trayIcon);
    }

    protected static Image createImage(String path, String description) {
        URL imageURL = Tray.class.getResource(path);

        if (imageURL == null) {
            System.err.println("Resource not found: " + path);
            return null;
        } else {
            return (new ImageIcon(imageURL, description)).getImage();
        }
    }
}
