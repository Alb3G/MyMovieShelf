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

public class MainView extends JFrame {
    private JPanel rootPanel;
    private JButton logOutButton;
    private JButton addMovieButton;
    private JTable movieTable;
    private DefaultTableModel tableModel;
    private List<MovieDTO> moviesDTO;

    public MainView() {
        UserDAO dao = new UserDAO(Db.getConn());
        String[] header = {"Title", "Condition", "Platform"};
        tableModel = new DefaultTableModel(header, 0);
        moviesDTO = dao.findAllUserCopies(Session.userSelected);

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
        addMovieButton.addActionListener( _ -> {
            AddMovieView amv = new AddMovieView();
            dispose();
            amv.setVisible(true);
        });
    }

    private void logOut(ActionEvent actionEvent) {
        Session.setParamsToNull();
        dispose();
        LoginView lw = new LoginView();
        lw.setLocationRelativeTo(null);
        lw.setVisible(true);
    }

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

    private void tableHeaderStyle() {
        JTableHeader headerStyle = movieTable.getTableHeader();
        headerStyle.setFont(new Font("Arial", Font.BOLD, 18));
    }

    private MovieDTO getMovieDtoForDetail() {
        MovieDTO movieDTO = null;
        int selectedRow = movieTable.getSelectedRow();
        if(selectedRow >= 0 && selectedRow < moviesDTO.size()) {
            movieDTO = moviesDTO.get(selectedRow);
        }
        return movieDTO;
    }
}
