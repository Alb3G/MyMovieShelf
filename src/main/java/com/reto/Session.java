package com.reto;

import com.reto.dto.MovieDTO;
import com.reto.model.User;

public class Session {
    public static MovieDTO movieSelected = null;
    public static User userSelected = null;

    public static void setParamsToNull() {
        movieSelected = null;
        userSelected = null;
    }
}
