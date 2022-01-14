package UI;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import javax.swing.text.SimpleAttributeSet;
import DatabaseHandle.FirebaseDBManager;
import TrayHide.Tray;
import model.Message;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import model.Person;

public final class Dash extends javax.swing.JFrame {
    private SimpleAttributeSet localUserAttributeSet;
    private SimpleAttributeSet otherUsersAttributeSet;
    private SimpleAttributeSet authorAttributeSet;

    private static DatabaseReference dbRefOnlineUsersCounter;
    private DatabaseReference dbRefMessages;
    String username;
    public Dash() {
        initComponents();
        adduser();
        username=Person.USERNAME;
        initAttributeSets();
        send.setEnabled(false);
        setTitle("AskChat");
        usertitle.setText(Person.USERNAME+ "'s AskChat");
        setupDBReferences();
        goOnline();
        addWindowListener(new WindowAdapter() {
            
            @Override
            public void windowClosing(WindowEvent e) {
                Tray.displayInfo("AskChat","Minimized to tray"); 
                //Tray.isopen=false;
            }
        });
        DefaultCaret caret = (DefaultCaret)pane.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        
    }
    void adduser(){
        DatabaseReference adduserobj =FirebaseDBManager.getDBRef("/AskChat/Users");;
        adduserobj.child(Person.USERNAME).setValueAsync(Person.Email);
    }
    void goOnline() {
        
        dbRefOnlineUsersCounter.child(username).setValueAsync(true);
    }

    public static void goOffline() {
        dbRefOnlineUsersCounter.child(Person.USERNAME).removeValueAsync();
    }
    public void sendMessage(Message message) {
        DatabaseReference newMessageRef = dbRefMessages.push();
        newMessageRef.setValueAsync(message);
    }
static String stringOfDash(String a) {
        String string = "";
        for(int i = 0; i < a.length(); i++)
            string = string + "-";
        return string;
    }
    void appendMessageLocally(Message message) {
        boolean messageSentByLocalUser = message.getAuthor().equals(username);

        SimpleAttributeSet as = messageSentByLocalUser ? localUserAttributeSet : otherUsersAttributeSet;
        StyledDocument doc = pane.getStyledDocument();

        try {
            
            doc.setParagraphAttributes(doc.getLength(), message.getText().length(), as, true);
            String str=stringOfDash(message.getText());
            if(message.getAuthor().equals(username))
                //message.setAuthor(message.getAuthor()+"    ");
                message.setText(message.getText()+"    ");
            doc.insertString(doc.getLength(),"\n    "+str+"    \n",null);
            doc.insertString(doc.getLength(), "    "+message.getText() + "\n    "+str+"    \n", null);

            String authorAlias = messageSentByLocalUser ? "Me" : message.getAuthor();

            doc.insertString(doc.getLength(), "    BY:" + authorAlias + emptyLine(), authorAttributeSet);
            emptyLine();

        } catch (BadLocationException ex) {
            Logger.getLogger(Dash.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String emptyLine() {
        return "    \n\n";
    }
int fl=1;
    private void setupDBReferences() {
        FirebaseDBManager.getInstance();

        dbRefOnlineUsersCounter = FirebaseDBManager.getDBRef("/AskChat/onlineUsers");
        dbRefOnlineUsersCounter.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot data) {
                live.setText("Online:" + data.getChildrenCount());
            }

            @Override
            public void onCancelled(DatabaseError de) {
            }
        });

        dbRefMessages = FirebaseDBManager.getDBRef("/AskChat/messages");
        //System.out.println(dbRefMessages.toString());
        
        dbRefMessages.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot data, String prevChildKey) {
                
                HashMap<String, String> messageObj = (HashMap<String, String>) data.getValue();
                
                String text = messageObj.get("text");
                String author = messageObj.get("author");
                appendMessageLocally(new Message(text, author));
                if(fl==0)
                    if(!author.equals(Person.USERNAME))
                        Tray.displayInfo(author, text);
            }

            @Override
            public void onChildChanged(DataSnapshot ds, String string) {
                
            }

            @Override
            public void onChildRemoved(DataSnapshot ds) {
                
            }

            @Override
            public void onChildMoved(DataSnapshot ds, String string) {
            }

            @Override
            public void onCancelled(DatabaseError de) {
                
            }
        });
    }
    private void initAttributeSets() {
        localUserAttributeSet = new SimpleAttributeSet();
        StyleConstants.setAlignment(localUserAttributeSet, StyleConstants.ALIGN_RIGHT);
        StyleConstants.setForeground(localUserAttributeSet, Color.BLACK);

        otherUsersAttributeSet = new SimpleAttributeSet();
        StyleConstants.setAlignment(otherUsersAttributeSet, StyleConstants.ALIGN_LEFT);
        StyleConstants.setForeground(otherUsersAttributeSet, Color.BLUE);
        authorAttributeSet = new SimpleAttributeSet();
        StyleConstants.setBold(authorAttributeSet, true);
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        pane = new javax.swing.JTextPane();
        mess = new rojeru_san.rsfield.RSTextFullBD();
        send = new rojeru_san.rsbutton.RSButtonRoundEffect();
        usertitle = new javax.swing.JLabel();
        live = new javax.swing.JLabel();
        usertitle1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Images/asklogo.png")));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(51, 153, 255)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pane.setEditable(false);
        pane.setBorder(null);
        pane.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jScrollPane1.setViewportView(pane);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 34, 473, 490));

        mess.setPlaceholder("Your Message");
        mess.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                messCaretUpdate(evt);
            }
        });
        mess.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                messFocusGained(evt);
            }
        });
        mess.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                messActionPerformed(evt);
            }
        });
        jPanel1.add(mess, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 530, 369, 51));

        send.setText("Send");
        send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendActionPerformed(evt);
            }
        });
        jPanel1.add(send, new org.netbeans.lib.awtextra.AbsoluteConstraints(376, 535, 98, -1));

        usertitle.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        usertitle.setForeground(new java.awt.Color(51, 153, 255));
        usertitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        usertitle.setText("User's AskChat");
        jPanel1.add(usertitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 0, 270, 30));

        live.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        live.setForeground(new java.awt.Color(0, 153, 0));
        live.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        live.setText("Online Users");
        jPanel1.add(live, new org.netbeans.lib.awtextra.AbsoluteConstraints(299, 1, 160, 30));

        usertitle1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        usertitle1.setForeground(new java.awt.Color(255, 51, 0));
        usertitle1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        usertitle1.setText("AskChat");
        jPanel1.add(usertitle1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 1, 80, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void messCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_messCaretUpdate
        if (!mess.getText().equals("")) {
            send.setEnabled(true);
        } else {
            send.setEnabled(false);
        }
    }//GEN-LAST:event_messCaretUpdate

    private void sendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendActionPerformed
        sendMessage(new Message(mess.getText(), username));
        mess.setText("");
        

    }//GEN-LAST:event_sendActionPerformed

    private void messActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_messActionPerformed
        if (mess.getText().equals("")) {
        } else {
            send.doClick();
        }
    }//GEN-LAST:event_messActionPerformed

    private void messFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_messFocusGained
fl=0;        // TODO add your handling code here:
    }//GEN-LAST:event_messFocusGained
    public void logout(){
        this.goOffline();
        Person.logout(this);
        this.dispose();
    }    Tray tr;
    public void main() {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dash.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Dash().setVisible(true);
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel live;
    private rojeru_san.rsfield.RSTextFullBD mess;
    private javax.swing.JTextPane pane;
    private rojeru_san.rsbutton.RSButtonRoundEffect send;
    private javax.swing.JLabel usertitle;
    private javax.swing.JLabel usertitle1;
    // End of variables declaration//GEN-END:variables
}
