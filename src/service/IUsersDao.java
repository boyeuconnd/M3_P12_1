package service;

import model.User;

import java.sql.SQLException;
import java.util.List;

public interface IUsersDao {
    void insertUser(User user) throws SQLException;

    User selectUser(int id);

    List<User> selectAllUsers();

    List<User> sortByName();

    boolean deleteUser(int id) throws SQLException;

    boolean updateUser(User user) throws SQLException;

    User getUserById(int id);

    void insertUserStore(User user) throws SQLException;

    void addUserTransaction(User user, int[] permision);

}
