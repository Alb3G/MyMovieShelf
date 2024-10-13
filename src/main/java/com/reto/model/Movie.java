package com.reto.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
