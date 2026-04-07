package service;

import DAO.UserDAO;
import model.User;

import java.sql.SQLException;
import java.util.List;

public class UserService implements IUserService {
    private final UserDAO userDAO = new UserDAO();

    @Override
    public void insertUser(User user) throws SQLException {
        userDAO.insertUser(user);
    }

    @Override
    public User selectUser(int id) {
        return userDAO.selectUser(id);
    }

    @Override
    public List<User> selectAllUsers() throws SQLException {
        return userDAO.selectAllUsers();
    }

    @Override
    public boolean deleteUser(int id) throws SQLException {
        return userDAO.deleteUser(id);
    }

    @Override
    public boolean updateUser(User user) throws SQLException {
        return userDAO.updateUser(user);
    }

    @Override
    public List<User> findByCountry(String country) throws SQLException {
        return userDAO.findByCountry(country);
    }

    @Override
    public List<User> sortByName() throws SQLException {
        List<User> users = userDAO.selectAllUsers();
        return userDAO.sortByName(users);
    }
}
