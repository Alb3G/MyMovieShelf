package com.reto.views;

import com.reto.Db;
import com.reto.Session;
import com.reto.dao.UserDAO;
import com.reto.model.User;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class LoginView extends JFrame {
    private JPanel rootPanel;
    private JLabel appTitle;
    private JPanel form;
    private JTextField userInput;
    private JPasswordField passInput;
    private JPanel inputsPanel;
    private JButton loginBtn;
    private JButton registerBtn;
    private JCheckBox rememberUser;
    private JPanel buttonsPanel;

    public LoginView() {
        UserDAO dao = new UserDAO(Db.getConn());

        userInput.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        passInput.setBorder(BorderFactory.createLineBorder(Color.BLACK));


        setContentPane(rootPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);

        loginBtn.addActionListener( _ -> loginProcess(dao));
        registerBtn.addActionListener( _ -> {
            RegisterFormView registerForm = new RegisterFormView();
            dispose();
            registerForm.setVisible(true);
        });
    }

    private void loginProcess(UserDAO dao) {
        String password = new String(passInput.getPassword());
        Optional<User> logged = dao.loginProcess(userInput.getText(), password);

        if(logged.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Wrong User Name or Password try again!");
        } else {
            Point location = this.getLocation();
            Session.userSelected = logged.get();
            dispose();
            MainView mainWindow = new MainView();
            mainWindow.setVisible(true);
        }

        if(rememberUser.isSelected()) {
            passInput.setText("");
        } else {
            userInput.setText("");
            passInput.setText("");
        }
    }
}
