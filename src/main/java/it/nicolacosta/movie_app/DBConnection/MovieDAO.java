package it.nicolacosta.movie_app.DBConnection;

import it.nicolacosta.movie_app.Movie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MovieDAO {

    DatabaseConnectionManager connectionManager;

    private MovieDAO() throws SQLException {
        DatabaseConnectionManager connectionManager = new DatabaseConnectionManager();
    }

    private void addMovie(Movie movie) throws SQLException{
        Connection connection = connectionManager.getConnection();
        String query = "INSERT INTO movie_table (title, director, year, genre, status, rating) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, movie.getTitle());
        statement.setString(2, movie.getDirector());
        statement.setInt(3, movie.getYear());
        statement.setString(4, movie.getGenre());
        statement.setString(5, movie.getStatus());
        statement.setInt(6, movie.getRating());
        statement.executeUpdate();
    }


}
