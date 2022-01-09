package UI;

import DatabaseHandle.FirebaseDBManager;
import java.awt.Toolkit;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
/**
 *
 * @author Sijan
 */
public class Login extends javax.swing.JFrame {
    public Login() {
        initComponents();
        FirebaseDBManager.getInstance();
        ok.setEnabled(false);
        String path = System.getProperty("user.home");
        if (!new File(path + "/serverfile.json").exists())
        try ( BufferedInputStream inputStream = new BufferedInputStream(new URL("https://firebasestorage.googleapis.com/v0/b/jmav-2bc1e.appspot.com/o/jmav-2bc1e-firebase-adminsdk-i8nm4-06575f36cb.json?alt=media&token=a0c66fae-6457-4e85-949c-37f514a2c814").openStream());  FileOutputStream fileOS = new FileOutputStream(path + "/serverfile.json")) {
            byte data[] = new byte[1024];
            int byteContent;
            while ((byteContent = inputStream.read(data, 0, 1024)) != -1) {
                fileOS.write(data, 0, byteContent);
            }
        } catch (IOException e) {
            System.out.println(e);

        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        username = new RSMaterialComponent.RSTextFieldMaterialIcon();
        ok = new rojeru_san.rsbutton.RSButtonRoundEffect();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        backg = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login");
        setAlwaysOnTop(true);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Images/asklogo.png")));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(51, 153, 255)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        username.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 51)));
        username.setForeground(new java.awt.Color(255, 0, 0));
        username.setColorIcon(new java.awt.Color(255, 102, 0));
        username.setPlaceholder("Username");
        username.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                usernameCaretUpdate(evt);
            }
        });
        jPanel1.add(username, new org.netbeans.lib.awtextra.AbsoluteConstraints(69, 183, -1, -1));

        ok.setBackground(new java.awt.Color(255, 102, 51));
        ok.setText("Start Chat");
        ok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okActionPerformed(evt);
            }
        });
        jPanel1.add(ok, new org.netbeans.lib.awtextra.AbsoluteConstraints(92, 243, -1, -1));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 102, 0));
        jLabel1.setText("Welcome To AskChat");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(109, 17, -1, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/asklogo.png"))); // NOI18N
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(93, 45, 174, 132));

        backg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/giphy (1).gif"))); // NOI18N
        jPanel1.add(backg, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, -10, 370, 440));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void okActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okActionPerformed
       Dash.main(username.getText());
       dispose();
    }//GEN-LAST:event_okActionPerformed

    private void usernameCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_usernameCaretUpdate
        if(username.getText().equals("")){
            ok.setEnabled(false);
            
        }else{
            ok.setEnabled(true);
        }
    }//GEN-LAST:event_usernameCaretUpdate

    /**
     * @param arg
     */
    public static void main(String[] arg) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(() -> {
            new Login().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel backg;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private rojeru_san.rsbutton.RSButtonRoundEffect ok;
    private RSMaterialComponent.RSTextFieldMaterialIcon username;
    // End of variables declaration//GEN-END:variables
}
