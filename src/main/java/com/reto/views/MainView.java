package com.reto.views;


import com.reto.Db;
import com.reto.Session;
import com.reto.dao.UserDAO;
import com.reto.dto.MovieDTO;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * Vista principal de la aplicación.
 * @author Alberto Guzman Moreno
 */
public class MainView extends JFrame {
    private UserDAO userDAO = new UserDAO(Db.getConn());

    private JPanel rootPanel;
    private JButton logOutButton;
    private JButton addMovieButton;
    private JTable movieTable;
    private JButton exitButton;
    private JButton settingsButton;
    private DefaultTableModel tableModel;
    private List<MovieDTO> moviesDTO;

    /**
     * Constructor de MainView, donde creamos la cabecera de la tabla,
     * le damos estilo y actualizamos el modelo de esta.
     */
    public MainView() {
        String[] header = {"Title", "Condition", "Platform"};
        tableModel = new DefaultTableModel(header, 0);
        moviesDTO = userDAO.findAllUserCopies(Session.userSelected);

        moviesDTO.forEach(movie -> {
            Object[] row = { movie.getMovie().getTitle(), movie.getMovieCondition(), movie.getPlatform() };
            tableModel.addRow(row);
        });

        tableHeaderStyle();
        movieTable.setModel(tableModel);

        setContentPane(rootPanel);
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        logOutButton.addActionListener(this::logOut);
        movieTable.getSelectionModel().addListSelectionListener(this::showDetailOnClick);
        addMovieButton.addActionListener( _ -> goToAddMovieView());
        exitButton.addActionListener(_ -> System.exit(0));
        settingsButton.addActionListener(_ -> showProfileInfo());
    }

    /**
     * Navega a la vista de agregar película.
     */
    private void goToAddMovieView() {
        AddMovieView amv = new AddMovieView();
        dispose();
        amv.setVisible(true);
    }

    /**
     * Cierra la sesión del usuario, seteando los parámetros de Session a null
     * y navegando al Login.
     *
     * @param actionEvent Evento de acción.
     */
    private void logOut(ActionEvent actionEvent) {
        Session.setParamsToNull();
        dispose();
        LoginView lw = new LoginView();
        lw.setLocationRelativeTo(null);
        lw.setVisible(true);
    }

    /**
     * Navega al detalle de la película seleccionada.
     * @param e Evento de selección de lista.
     */
    private void showDetailOnClick(ListSelectionEvent e) {
        if(!e.getValueIsAdjusting()) {
            Session.movieSelected = getMovieDtoForDetail();
            if(Session.movieSelected != null) {
                dispose();
                DetailView detailView = new DetailView();
                detailView.setVisible(true);
            }
        }
    }

    /**
     * Establece el estilo del encabezado de la tabla.
     */
    private void tableHeaderStyle() {
        JTableHeader headerStyle = movieTable.getTableHeader();
        headerStyle.setFont(new Font("Arial", Font.BOLD, 18));
    }

    /**
     * Obtiene el DTO de la película seleccionada para mostrar detalles.
     * @return El DTO de la película seleccionada.
     */
    private MovieDTO getMovieDtoForDetail() {
        MovieDTO movieDTO = null;
        int selectedRow = movieTable.getSelectedRow();
        if(selectedRow >= 0 && selectedRow < moviesDTO.size()) {
            movieDTO = moviesDTO.get(selectedRow);
        }
        return movieDTO;
    }

    /**
     * Muestra la información del perfil del usuario.
     */
    private void showProfileInfo() {
        int copiesAmount = userDAO.findAllUserCopies(Session.userSelected).size();
        String message = String.format(
                "Account info, Id: %d / Name: %s / Num of movies: %d",
                Session.userSelected.getId(),
                Session.userSelected.getUserName(),
                copiesAmount
        );
        JOptionPane.showMessageDialog(rootPanel, message, Session.userSelected.getUserName(), JOptionPane.INFORMATION_MESSAGE);
    }
}
