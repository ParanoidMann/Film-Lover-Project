package me.mashyrin.filmLovers.model.DAO;

import me.mashyrin.filmLovers.Main;
import me.mashyrin.filmLovers.model.entities.Film;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class FilmDAO extends DAO<Film, Integer> {
    
    @Override
    public ArrayList<Film> selectAll() throws SQLException {
        ArrayList<Film> filmArrayList = new ArrayList<>();
        try( Statement statement = connection.createStatement() ) {
            ResultSet resultSet = statement.executeQuery(
                    new StringBuilder( "SELECT * FROM get_film" ).toString() );
            
            while( resultSet.next() ) {
                Film result = new Film();
                result.setName( resultSet.getString( "name" ) );
                result.setBudget( resultSet.getInt( "budget" ) );
                result.setComment( resultSet.getString( "comment" ) );
                result.setCountryId( resultSet.getInt( "country_id" ) );
                result.setCriticScore( resultSet.getDouble( "critic_score" ) );
                result.setFilmId( resultSet.getInt( "film_id" ) );
                result.setGenreId( resultSet.getInt( "genre_id" ) );
                result.setReleaseDate( resultSet.getDate( "release_date" ) );
                result.setScore( resultSet.getDouble( "score" ) );
                filmArrayList.add( result );
            }
        }
        return filmArrayList;
    }
    
    /**
     * Call adding review to JSON function
     *
     * @param filmId
     * @param review - your review
     * @throws SQLException - SQL error into database
     */
    public void addReview( Integer filmId, String review ) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement( "SELECT add_film_review(?,?,?)" );
            preparedStatement.setInt( 1, filmId );
            preparedStatement.setString( 2, Main.getCurrentUser().getLogin() );
            preparedStatement.setString( 3, review );
            preparedStatement.execute();
        } finally {
            if( preparedStatement != null ) {
                preparedStatement.close();
            }
        }
    }
    
    /**
     * Call deleting review from JSON function
     *
     * @param filmId
     * @throws SQLException - SQL error into database
     */
    public void deleteReview( Integer filmId ) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement( "SELECT delete_film_review(?,?)" );
            preparedStatement.setInt( 1, filmId );
            preparedStatement.setString( 2, Main.getCurrentUser().getLogin() );
            preparedStatement.execute();
        } finally {
            if( preparedStatement != null ) {
                preparedStatement.close();
            }
        }
    }
    
    /**
     * Call selecting reviews from JSON function
     *
     * @param filmId
     * @return HashMap of login like String key and review like String value
     * @throws SQLException - SQL error into database
     */
    public HashMap<String, String> selectFilmReview( Integer filmId ) throws SQLException {
        HashMap<String, String> result = new HashMap<>();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM get_film_review( ? );" );
            preparedStatement.setInt( 1, filmId );
            ResultSet resultSet = preparedStatement.executeQuery();
            while( resultSet.next() ) {
                result.put( resultSet.getString( "key" ), resultSet.getString( "value" ) );
            }
        } finally {
            if( preparedStatement != null ) {
                preparedStatement.close();
            }
        }
        return result;
    }
    
    @Override
    public Film selectById( Integer id ) throws SQLException {
        Film result = new Film();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement( "SELECT * FROM get_film WHERE film_id = ?;" );
            preparedStatement.setInt( 1, id );
            ResultSet resultSet = preparedStatement.executeQuery();
            while( resultSet.next() ) {
                result.setName( resultSet.getString( "name" ) );
                result.setBudget( resultSet.getInt( "budget" ) );
                result.setComment( resultSet.getString( "comment" ) );
                result.setCountryId( resultSet.getInt( "country_id" ) );
                result.setCriticScore( resultSet.getDouble( "critic_score" ) );
                result.setFilmId( resultSet.getInt( "film_id" ) );
                result.setGenreId( resultSet.getInt( "genre_id" ) );
                result.setReleaseDate( resultSet.getDate( "release_date" ) );
                result.setScore( resultSet.getDouble( "score" ) );
            }
        } finally {
            if( preparedStatement != null ) {
                preparedStatement.close();
            }
        }
        return result;
    }
    
    @Override
    public void deleteById( Integer id ) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement( "SELECT delete_film(?);" );
            preparedStatement.setInt( 1, id );
            preparedStatement.executeQuery();
        } finally {
            if( preparedStatement != null ) {
                preparedStatement.close();
            }
        }
    }
    
    @Override
    public Film insert( Film entity ) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement( "SELECT add_film(?,?)" );
            preparedStatement.setString( 1, entity.getName() );
            preparedStatement.setInt( 2, entity.getGenreId() );
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            entity.setFilmId( resultSet.getInt( 1 ) );
        } finally {
            if( preparedStatement != null ) {
                preparedStatement.close();
            }
        }
        return entity;
    }
    
    @Override
    public void update( Film entity ) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement( "SELECT edit_film(?,?,?,?,?,?,?)" );
            preparedStatement.setDate( 1, entity.getReleaseDate() == null ? null : entity.getReleaseDate() );
            preparedStatement.setInt( 2, entity.getBudget() );
            preparedStatement.setString( 3, entity.getComment() );
            preparedStatement.setInt( 4, entity.getGenreId() );
            preparedStatement.setInt( 5, entity.getCountryId() );
            preparedStatement.setString( 6, entity.getName() );
            preparedStatement.setInt( 7, entity.getFilmId() );
            preparedStatement.execute();
        } finally {
            if( preparedStatement != null ) {
                preparedStatement.close();
            }
        }
    }
}
