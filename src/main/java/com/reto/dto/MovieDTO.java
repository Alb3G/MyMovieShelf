package com.reto.dto;

import com.reto.enums.MovieCondition;
import com.reto.enums.Platform;
import com.reto.model.Movie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase a utilizar para mostrar los datos específicos de una película
 * y de una copia en la tabla del usuario.
 * @author Alberto Guzman Moreno
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieDTO {
    private Movie movie;
    private MovieCondition movieCondition;
    private Platform platform;
}
