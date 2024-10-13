package com.reto.dao;

import com.reto.dto.MovieDTO;
import com.reto.enums.MovieCondition;
import com.reto.enums.Platform;
import com.reto.model.MovieCopy;
import com.reto.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieCopyDAO implements DAO<MovieCopy> {
    private static Connection connection = null;
    private static final String INSERT_MOVIE_COPY = "INSERT INTO MovieCopy (movie_id, user_id, movie_condition, platform) values (?,?,?,?);";
    private static final String DELETE_MOVIE_COPY = "DELETE FROM MovieCopy where copy_id = ?;";
    private static final String UPDATE_MOVIE_COPY = "UPDATE MovieCopy set movie_condition = ?, platform = ? where copy_id = ?;";

    public MovieCopyDAO(Connection conn) { connection = conn; }

    @Override
    public List<MovieCopy> findAll() {
        List<MovieCopy> copies = new ArrayList<>();
        try(PreparedStatement ps = connection.prepareStatement("SELECT * FROM MovieCopy;")) {
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                copies.add(new MovieCopy(
                        rs.getInt("copy_id"),
                        rs.getInt("movie_id"),
                        rs.getInt("user_id"),
                        MovieCondition.valueOf(rs.getString("movie_condition")),
                        Platform.valueOf(rs.getString("platform"))
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return copies;
    }

    @Override
    public MovieCopy findById(Integer id) {
        MovieCopy copy = null;
        try(PreparedStatement ps = connection.prepareStatement("SELECT * FROM MovieCopy where copy_id = ?;")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                copy = new MovieCopy(
                        rs.getInt("copy_id"),
                        rs.getInt("movie_id"),
                        rs.getInt("user_id"),
                        MovieCondition.valueOf(rs.getString("movie_condition")),
                        Platform.valueOf(rs.getString("platform"))
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return copy;
    }

    @Override
    public boolean save(MovieCopy movieCopy) {
        boolean res = false;
        try(var ps = connection.prepareStatement(INSERT_MOVIE_COPY, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, movieCopy.getMovieId());
            ps.setInt(2, movieCopy.getUserId());
            ps.setString(3, movieCopy.getMovieCondition().toString());
            ps.setString(4, movieCopy.getPlatform().toString());
            if(ps.executeUpdate() == 1) {
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                movieCopy.setCopyId(rs.getInt(1));
                res = true;
            }
        } catch (SQLException e) {
            System.out.println("Duplicate entry or something else went wrong");
        }
        return res;
    }

    @Override
    public void update(MovieCopy movieCopy) {
        try(var ps = connection.prepareStatement(UPDATE_MOVIE_COPY)) {
            ps.setString(1, movieCopy.getMovieCondition().toString());
            ps.setString(2, movieCopy.getPlatform().toString());
            ps.setInt(3, movieCopy.getCopyId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(MovieCopy movieCopy) {
        boolean res = false;
        try(var ps = connection.prepareStatement(DELETE_MOVIE_COPY)) {
            ps.setInt(1, movieCopy.getCopyId());
            if(ps.executeUpdate() > 0)
                res = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    @Override
    public void deleteById(Integer id) {
        try(var ps = connection.prepareStatement(DELETE_MOVIE_COPY)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Integer findSpecificCopy(User user, MovieDTO dto) {
        int movieCopyId = 0;
        try(PreparedStatement ps = connection.prepareStatement("select copy_id from MovieCopy inner join Movie on id = movie_id where movie_id = ? and user_id = ? and movie_condition = ? and platform = ?;")) {
            ps.setInt(1, dto.getMovie().getId());
            ps.setInt(2, user.getId());
            ps.setString(3, dto.getMovieCondition().toString());
            ps.setString(4, dto.getPlatform().toString());
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                movieCopyId = rs.getInt("copy_id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return movieCopyId;
    }
}
