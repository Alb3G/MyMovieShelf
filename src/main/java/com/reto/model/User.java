package com.reto.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa un usuario del sistema.
 *
 * @author Alberto Guzman Moreno
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;
    private String userName;
    private String password;

    public User(String name, String password) {
        this.userName = name;
        this.password = password;
    }
}
