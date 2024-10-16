package com.reto.views;

import com.reto.Db;
import com.reto.Session;
import com.reto.dao.MovieCopyDAO;
import com.reto.dto.MovieDTO;
import com.reto.model.MovieCopy;

import javax.swing.*;

/**
 * Vista de detalle para mostrar información de una copia de película.
 * @author Alberto Guzman Moreno
 */
public class DetailView extends JFrame {
    private MovieCopyDAO mcDao = new MovieCopyDAO(Db.getConn());

    private JPanel rootPanel;
    private JLabel movieIdDetail;
    private JButton backButton;
    private JTextArea movieDescriptionTxt;
    private JTextField movieTitleTxt;
    private JTextField movieDirectorTxt;
    private JTextField movieGenreTxt;
    private JTextField movieYearTxt;
    private JTextField moviePlatformTxt;
    private JTextField movieConditionTxt;
    private JButton delete;

    /**
     * Constructor de DetailView.
     * Inicializa la vista y configura los componentes.
     */
    public DetailView() {
        setContentPane(rootPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);

        setFieldsWithInfo(Session.movieSelected);
        backButton.addActionListener( _ -> backToMainView());
        delete.addActionListener( _ -> deleteMovieProcess());
    }

    /**
     * Navega de vuelta a la vista principal.
     */
    private void backToMainView() {
        MainView backToMain = new MainView();
        Session.movieSelected = null;
        dispose();
        backToMain.setVisible(true);
    }

    /**
     * Procesa la eliminación de una copia de película.
     */
    private void deleteMovieProcess() {
        MovieDTO dto = Session.movieSelected;
        Integer copyID = mcDao.findSpecificCopy(Session.userSelected, dto);
        MovieCopy movieCopy = new MovieCopy(
                copyID,
                dto.getMovie().getId(),
                Session.userSelected.getId(),
                dto.getMovieCondition(),
                dto.getPlatform()
        );
        if(mcDao.delete(movieCopy)) {
            MainView mv = new MainView();
            dispose();
            mv.setVisible(true);
        } else
            JOptionPane.showMessageDialog(rootPanel, "Error deleting the copy!", "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Establece los textFields con la información de la película seleccionada,
     * estan disabled por lo que no se puede cambiar la información.
     *
     * @param movieDTO Objeto MovieDTO con la información de la película.
     */
    private void setFieldsWithInfo(MovieDTO movieDTO) {
        movieIdDetail.setText(movieDTO.getMovie().getId().toString());
        movieTitleTxt.setText(movieDTO.getMovie().getTitle());
        movieDescriptionTxt.setText(movieDTO.getMovie().getDescription());
        movieDirectorTxt.setText(movieDTO.getMovie().getDirector());
        movieConditionTxt.setText(movieDTO.getMovieCondition().toString());
        movieGenreTxt.setText(movieDTO.getMovie().getGenre());
        moviePlatformTxt.setText(movieDTO.getPlatform().toString());
        movieYearTxt.setText(movieDTO.getMovie().getReleaseYear().toString());
    }
}
