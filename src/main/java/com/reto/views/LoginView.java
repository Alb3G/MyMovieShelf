package com.reto.views;

import com.reto.Db;
import com.reto.Session;
import com.reto.dao.UserDAO;
import com.reto.model.User;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

/**
 * Vista de inicio de sesión.
 * @author Alberto Guzman Moreno
 */
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

    /**
     * Constructor de la vista de inicio de sesión.
     */
    public LoginView() {
        UserDAO dao = new UserDAO(Db.getConn());

        userInput.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        passInput.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        setContentPane(rootPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);

        loginBtn.addActionListener( _ -> loginProcess(dao));
        registerBtn.addActionListener( _ -> registerUser());
    }

    /**
     * Navegación a la vista de registro de usuario.
     */
    private void registerUser() {
        RegisterFormView registerForm = new RegisterFormView();
        dispose();
        registerForm.setVisible(true);
    }

    /**
     * Procesa el inicio de sesión del usuario, en funcion del Optional
     * que devuelve el dao mostramos mensaje de error si el usuario no
     * existe o navegamos a la MainView si el user existe.
     *
     * @param dao El objeto UserDAO para realizar la autenticación.
     */
    private void loginProcess(UserDAO dao) {
        String password = new String(passInput.getPassword());
        Optional<User> logged = dao.loginProcess(userInput.getText(), password);

        if(logged.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nombre de usuario o contraseña incorrectos / vacios, ¡inténtelo de nuevo!");
        } else {
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
