package com.reto.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa una película.
 * Contiene información sobre el título, género, año de lanzamiento, descripción y director.
 * Utiliza Lombok para generar los métodos getter, setter, constructor con todos los argumentos,
 * constructor sin argumentos y el método toString.
 *
 * @author Alberto Guzman Moreno
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
    private Integer id;
    private String title;
    private String genre;
    private Integer releaseYear;
    private String description;
    private String director;

    @Override
    public String toString() {
        return title;
    }
}
