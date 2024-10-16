package com.reto.dao;

import com.reto.dto.MovieDTO;
import com.reto.enums.MovieCondition;
import com.reto.enums.Platform;
import com.reto.model.Movie;
import com.reto.model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Clase que implementa las operaciones CRUD para la entidad User.
 * Proporciona métodos para encontrar, guardar, actualizar y eliminar usuarios
 * en la base de datos. También incluye métodos para encontrar todas las copias de
 * películas de un usuario y para el proceso de inicio de sesión.
 * @author Alberto Guzman Moreno
 */
public class UserDAO implements DAO<User> {
    private static Connection connection = null;
    private static final String INSERT_USER = "INSERT INTO User (user_name, password) values (?, ?);";
    private static final String DELETE_USER = "DELETE FROM User where id = ?;";
    private static final String UPDATE_USER = "UPDATE User set user_name = ?, password = ? where id = ?;";

    public UserDAO(Connection conn) { connection = conn; }

    /**
     * Encuentra todos los usuarios en la base de datos.
     *
     * @return una lista de todos los usuarios.
     */
    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try(PreparedStatement ps = connection.prepareStatement("SELECT * FROM User;")) {
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                users.add(new User(
                        rs.getInt("id"),
                        rs.getString("user_name"),
                        rs.getString("password")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    /**
     * Encuentra un usuario por su ID.
     *
     * @param id el ID del usuario a encontrar.
     * @return el usuario encontrado, o null si no se encuentra.
     */
    @Override
    public User findById(Integer id) {
        User user = null;
        try(PreparedStatement ps = connection.prepareStatement("SELECT * FROM User where id = ?;")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                user = new User(
                        rs.getInt("id"),
                        rs.getString("user_name"),
                        rs.getString("password")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    /**
     * Guarda un usuario en la base de datos.
     *
     * @param user el usuario a guardar.
     * @return true si el usuario fue guardado exitosamente, false en caso contrario.
     */
    @Override
    public boolean save(User user) {
        boolean res = false;
        try(var ps = connection.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getPassword());
            if(ps.executeUpdate() == 1) {
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                user.setId(rs.getInt(1));
                res = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    /**
     * Actualiza un usuario en la base de datos.
     *
     * @param user el usuario a actualizar.
     */
    @Override
    public void update(User user) {
        try(var ps = connection.prepareStatement(UPDATE_USER)) {
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getPassword());
            ps.setInt(3, user.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Elimina un usuario de la base de datos.
     *
     * @param user el usuario a eliminar.
     * @return true si el usuario fue eliminado exitosamente, false en caso contrario.
     */
    @Override
    public boolean delete(User user) {
        boolean res = false;
        try(var ps = connection.prepareStatement(DELETE_USER)) {
            ps.setInt(1, user.getId());
            if(ps.executeUpdate() > 0)
                res = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    /**
     * Elimina un usuario de la base de datos por su ID.
     *
     * @param id el ID del usuario a eliminar.
     */
    @Override
    public void deleteById(Integer id) {
        try(var ps = connection.prepareStatement(DELETE_USER)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Encuentra todas las copias de películas de un usuario.
     *
     * @param user el usuario cuyas copias de películas se van a encontrar.
     * @return una lista de todas las copias de películas del usuario.
     */
    public List<MovieDTO> findAllUserCopies(User user) {
        List<MovieDTO> userMovies = new ArrayList<>();
        try(PreparedStatement ps = connection.prepareStatement("select * from Movie inner join MovieCopy on id = movie_id where user_id = ?;")) {
            ps.setInt(1, user.getId());
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                userMovies.add(new MovieDTO(
                        new Movie(
                                rs.getInt("id"),
                                rs.getString("title"),
                                rs.getString("genre"),
                                rs.getInt("release_year"),
                                rs.getString("description"),
                                rs.getString("director")
                        ),
                        MovieCondition.valueOf(rs.getString("movie_condition")),
                        Platform.valueOf(rs.getString("platform"))
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userMovies;
    }

    /**
     * Procesa el inicio de sesión de un usuario.
     *
     * @param userName el nombre de usuario.
     * @param password la contraseña del usuario.
     * @return un Optional que contiene el usuario si las credenciales son correctas, o un Optional vacío si no lo son.
     */
    public Optional<User> loginProcess(String userName, String password) {
        User res = null;
        try(var ps = connection.prepareStatement("Select * from User where user_name = ?;")) {
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();
            if(rs.next() && BCrypt.checkpw(password, rs.getString("password"))) {
                res = new User(
                        rs.getInt("id"),
                        rs.getString("user_name"),
                        rs.getString("password")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(res);
    }
}
