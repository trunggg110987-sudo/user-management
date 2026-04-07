package DAO;

import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserDAO implements IUserDAO{

    private static final String INSERT_USERS_SQL = "INSERT INTO users (name, email, country) VALUES (?, ?, ?);";
    private static final String SELECT_USER_BY_ID = "select id,name,email,country from users where id =?";
    private static final String SELECT_ALL_USERS = "select * from users";
    private static final String DELETE_USERS_SQL = "delete from users where id = ?;";
    private static final String UPDATE_USERS_SQL = "update users set name = ?,email= ?, country =? where id = ?;";
    public static final String FIND_BY_COUNTRY = "SELECT * FROM users WHERE country = ?";

    public UserDAO() {
    }

    @Override
    public void insertUser(User user) throws SQLException {
        try(Connection conn = dbConnection.getConnection()){
            PreparedStatement ps = conn.prepareStatement(INSERT_USERS_SQL);
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getCountry());
            ps.executeUpdate();
            System.out.println("User inserted successfully");
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public User selectUser(int id) {
        User user = new User();
        try(Connection conn = dbConnection.getConnection()){
            PreparedStatement ps = conn.prepareStatement(SELECT_USER_BY_ID);
            ps.setInt(1, id);
            System.out.println(ps);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");
                return new User(id, name, email, country);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public List<User> selectAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        try(Connection conn = dbConnection.getConnection()){
            PreparedStatement ps = conn.prepareStatement(SELECT_ALL_USERS);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");
                users.add(new User(id, name, email, country));
            }
        }catch (SQLException e) {
            throw new SQLException(e);
        }
        return users;
    }

    @Override
    public boolean deleteUser(int id) throws SQLException {
        boolean isDeleted;
        try(Connection conn = dbConnection.getConnection()){
            PreparedStatement ps = conn.prepareStatement(DELETE_USERS_SQL);
            ps.setInt(1, id);
            isDeleted = ps.executeUpdate() > 0;
        }catch (SQLException e) {
            throw new SQLException(e);
        }
        return isDeleted;
    }

    @Override
    public boolean updateUser(User user) throws SQLException {
        boolean isUpdated;
        try(Connection conn = dbConnection.getConnection()){
            PreparedStatement ps = conn.prepareStatement(UPDATE_USERS_SQL);
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getCountry());
            ps.setInt(4, user.getId());
            isUpdated = ps.executeUpdate() > 0;
        }catch (SQLException e) {
            throw new SQLException(e);
        }
        return isUpdated;
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }

    public List<User> findByCountry(String country) throws SQLException {
        List<User> users = new ArrayList<>();
        try(Connection conn = dbConnection.getConnection()){
            PreparedStatement ps = conn.prepareStatement(FIND_BY_COUNTRY);
            ps.setString(1, country);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                users.add(new User(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("country")));
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return users;
    }

    public List<User> sortByName(List<User> users) {
        users.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));
        return users;
    }
}
