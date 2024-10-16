package com.reto;

import com.reto.dto.MovieDTO;
import com.reto.model.User;

/**
 * Session almacena de manera pública x numero de objetos para que puedan
 * ser utilizados en cualquier punto de la aplicación de una manera sencilla
 * hay que tener en cuenta que deberán volver a setearse a null cuando se
 * salga de la app.
 * @author Alberto Guzman Moreno
 */
public class Session {
    public static MovieDTO movieSelected = null;
    public static User userSelected = null;

    public static void setParamsToNull() {
        movieSelected = null;
        userSelected = null;
    }
}
