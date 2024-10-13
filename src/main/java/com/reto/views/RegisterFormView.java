package com.reto.views;

import com.reto.Db;
import com.reto.dao.UserDAO;
import com.reto.model.User;
import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;

public class RegisterFormView extends JFrame {
    private JPanel root;
    private JButton registerButton;
    private JTextField userNameInput;
    private JPasswordField passInput;
    private JPasswordField confirmPassInput;
    private JButton cancelButton;
    private UserDAO dao = new UserDAO(Db.getConn());

    public RegisterFormView() {
        setContentPane(root);
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        registerButton.addActionListener( _ -> registerUser());
        cancelButton.addActionListener( _ -> backToLogin());
    }

    private void registerUser() {
        String pass = new String(passInput.getPassword());
        String pass2 = new String(confirmPassInput.getPassword());
        if(!pass.isEmpty() && !pass2.isEmpty() && (pass.equals(pass2))) {
            User u = new User(userNameInput.getText(), BCrypt.hashpw(pass, BCrypt.gensalt()));
            if(dao.save(u)) {
                LoginView lw = new LoginView();
                dispose();
                lw.setVisible(true);
            } else
                JOptionPane.showMessageDialog(root, "Password Error check both of them!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(root, "Password Error check both of them!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void backToLogin() {
        LoginView lw = new LoginView();
        dispose();
        lw.setVisible(true);
    }
}
