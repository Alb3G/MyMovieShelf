package com.reto.model;

import com.reto.enums.MovieCondition;
import com.reto.enums.Platform;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa una copia de una película.
 *
 * @author Alberto Guzman Moreno
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieCopy {
    private Integer copyId;
    private Integer movieId;
    private Integer userId;
    private MovieCondition movieCondition;
    private Platform platform;
}
