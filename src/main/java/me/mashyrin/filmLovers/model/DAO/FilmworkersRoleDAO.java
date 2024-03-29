package me.mashyrin.filmLovers.model.DAO;

import me.mashyrin.filmLovers.controller.ConnectionManager;
import me.mashyrin.filmLovers.model.entities.FilmworkersRole;

import java.sql.*;
import java.util.ArrayList;

/**
 * Filmworker's roles DAO
 */
public class FilmworkersRoleDAO {
    
    private Connection connection = ConnectionManager.getConnection();
    
    /**
     * Call selecting all actors function
     *
     * @return returns ArrayList of actors
     * @throws SQLException - SQL error into database
     */
    public ArrayList<FilmworkersRole> selectAll() throws SQLException {
        ArrayList<FilmworkersRole> filmworkersRoleArrayList = new ArrayList<>();
        try( Statement statement = connection.createStatement() ) {
            ResultSet resultSet = statement.executeQuery(
                    new StringBuilder( "SELECT * FROM get_filmworkers_role" ).toString() );
            
            while( resultSet.next() ) {
                FilmworkersRole result = new FilmworkersRole();
                result.setFilmId( resultSet.getInt( "film_id" ) );
                result.setFilmworkerId( resultSet.getInt( "filmworker_id" ) );
                result.setRoleId( resultSet.getInt( "role_id" ) );
                filmworkersRoleArrayList.add( result );
            }
        }
        return filmworkersRoleArrayList;
        
    }
    
    /**
     * Call deleting function
     *
     * @param roleId
     * @param filmworkerId
     * @param filmId
     * @throws SQLException - SQL error into database
     */
    public void deleteById( Integer roleId, Integer filmworkerId, Integer filmId ) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement( "SELECT delete_filmworkers_role(?,?,?)" );
            preparedStatement.setInt( 1, roleId );
            preparedStatement.setInt( 2, filmworkerId );
            preparedStatement.setInt( 3, filmId );
            preparedStatement.executeQuery();
        } finally {
            if( preparedStatement != null ) {
                preparedStatement.close();
            }
        }
    }
    
    /**
     * Call inserting function
     *
     * @param roleId
     * @param filmworkerId
     * @param filmId
     * @throws SQLException - SQL error into database
     */
    public void insert( Integer roleId, Integer filmworkerId, Integer filmId ) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement( "SELECT add_filmworkers_role(?,?,?)" );
            preparedStatement.setInt( 1, roleId );
            preparedStatement.setInt( 2, filmworkerId );
            preparedStatement.setInt( 3, filmId );
            preparedStatement.executeQuery();
        } finally {
            if( preparedStatement != null ) {
                preparedStatement.close();
            }
        }
    }
}
