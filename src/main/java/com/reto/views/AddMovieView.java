package com.reto.views;

import com.reto.Db;
import com.reto.Session;
import com.reto.dao.MovieCopyDAO;
import com.reto.dao.MovieDAO;
import com.reto.enums.MovieCondition;
import com.reto.enums.Platform;
import com.reto.model.Movie;
import com.reto.model.MovieCopy;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddMovieView extends JFrame {
    private MovieDAO mDao = new MovieDAO(Db.getConn());
    private MovieCopyDAO mcDao = new MovieCopyDAO(Db.getConn());

    private JPanel root;
    private JComboBox<Movie> movieTitleChoice;
    private JComboBox<String> movieConditionChoice;
    private JComboBox<String> moviePlatformChoice;
    private JButton saveMovie;
    private JButton cancelButton;

    public AddMovieView() {
        List<Movie> movies = new ArrayList<>(mDao.findAll());
        List<String> movieConditions = getEnumValues(MovieCondition.class);
        List<String> moviePlatforms = getEnumValues(Platform.class);
        setContentPane(root);
        setSize(900, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        movies.forEach(movieTitleChoice::addItem);
        movieConditions.forEach(condition -> movieConditionChoice.addItem(condition));
        moviePlatforms.forEach(platform -> moviePlatformChoice.addItem(platform));
        cancelButton.addActionListener(_ -> navBack());
        saveMovie.addActionListener( _ -> saveMovieCopy());
    }

    private void saveMovieCopy() {
        Movie movie = (Movie) movieTitleChoice.getSelectedItem();
        MovieCondition movieCondition =  MovieCondition.valueOf(Objects.requireNonNull(movieConditionChoice.getSelectedItem()).toString());
        Platform moviePlatform = Platform.valueOf(Objects.requireNonNull(moviePlatformChoice.getSelectedItem()).toString());
        if(movie != null) {
            MovieCopy mc = new MovieCopy(
              null,
              movie.getId(),
              Session.userSelected.getId(),
              movieCondition,
              moviePlatform
            );
            if(mcDao.save(mc)) {
                MainView mv = new MainView();
                dispose();
                mv.setVisible(true);
            } else
                JOptionPane.showMessageDialog(root, "Posible duplicate of movies, change params!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private <T extends Enum<T>> List<String> getEnumValues(Class<T> enumClass) {
        List<String> res = new ArrayList<>();
        for(T c : enumClass.getEnumConstants()) {
            res.add(c.toString());
        }
        return res;
    }

    private void navBack() {
        MainView mv = new MainView();
        dispose();
        mv.setVisible(true);
    }
}
