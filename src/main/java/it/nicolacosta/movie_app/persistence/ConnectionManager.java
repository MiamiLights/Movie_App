package it.nicolacosta.movie_app.persistence;

import java.sql.Connection;

public interface ConnectionManager {
    Connection getConnection();
}
