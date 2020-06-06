package service;

import model.User;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUsersDao {
    private String jdbcURL = "jdbc:mysql://localhost:3306/p12_1_demo";
    private String jdbcUsername = "root";
    private String jdbcPassword = "1234";

    private static final String INSERT_USERS_SQL = "INSERT INTO users" + "  (name, email, country) VALUES " +
            " (?, ?, ?);";

    private static final String SELECT_USER_BY_ID = "select id,name,email,country from users where id =?";
    private static final String SELECT_ALL_USERS = "select * from users;";
    private static final String DELETE_USERS_SQL = "delete from users where id = ?;";
    private static final String UPDATE_USERS_SQL = "update users set name = ?,email= ?, country =? where id = ?;";
    private static final String SORT_BY_NAME = "select * from users order by name";
    private static final String SORT_BY_COUNTRY = "select * from users order by country";

    public UserDAO() {}

    protected Connection getConnection(){
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver"); //????????????
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public void insertUser(User user) throws SQLException {
        System.out.println(INSERT_USERS_SQL);
        // try-with-resource statement will auto close the connection.
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getCountry());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Kết nối thất bại");
        }

    }

    @Override
    public User selectUser(int id) {
        User user = null;
        // Step 1: Establishing a Connection
        try (Connection connection = getConnection();
             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");
                user = new User(id, name, email, country);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<User> selectAllUsers() {
        // using try-with-resources to avoid closing resources (boiler plate code)
        List<User> users = new ArrayList<>();
        // Step 1: Establishing a Connection
        try (Connection connection = getConnection();

             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS)) {
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");
                users.add(new User(id, name, email, country));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public List<User> sortByName() {
        List<User> sortList = new ArrayList<>();
        try{
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(SORT_BY_NAME);
            while (rs.next()){
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String email = rs.getString(3);
                String country = rs.getString(4);
                sortList.add(new User(id,name,email,country));
            }
        }catch (SQLException e){

        }

        return sortList;
    }

    @Override
    public boolean deleteUser(int id) throws SQLException {
        boolean rowDeleted;
        try (
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_USERS_SQL);) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    @Override
    public boolean updateUser(User user) throws SQLException {
        boolean rowUpdate;
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_USERS_SQL);
            statement.setString(1,user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getCountry());
            statement.setInt(4, user.getId());

            rowUpdate = statement.executeUpdate()>0;
        return rowUpdate;

    }

    @Override
    public User getUserById(int id) {
        User user = null;

        String query = "{CALL get_user_by_id(?)}";

        // Step 1: Establishing a Connection

        try (Connection connection = getConnection();

             // Step 2:Create a statement using connection object

             CallableStatement callableStatement = connection.prepareCall(query);) {

            callableStatement.setInt(1, id);

            // Step 3: Execute the query or update query

            ResultSet rs = callableStatement.executeQuery();

            // Step 4: Process the ResultSet object.

            while (rs.next()) {

                String name = rs.getString("name");

                String email = rs.getString("email");

                String country = rs.getString("country");

                user = new User(id, name, email, country);

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return user;
    }

    @Override
    public void insertUserStore(User user) throws SQLException {
        String query = "{CALL insert_user(?,?,?)}";

        // try-with-resource statement will auto close the connection.

        try (Connection connection = getConnection();

             CallableStatement callableStatement = connection.prepareCall(query);) {

            callableStatement.setString(1, user.getName());

            callableStatement.setString(2, user.getEmail());

            callableStatement.setString(3, user.getCountry());

            System.out.println(callableStatement);

            callableStatement.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();

        }
    }

    @Override
    public void addUserTransaction(User user, int[] permision) {
        Connection connection = null;
        PreparedStatement pstmt =null;
        PreparedStatement pstmtAssignment = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement(INSERT_USERS_SQL,Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1,user.getName());
            pstmt.setString(1,user.getEmail());
            pstmt.setString(1,user.getCountry());
            int rowEffect = pstmt.executeUpdate();

            rs = pstmt.getGeneratedKeys();

            int userId = 0;
            if(rs.next()){
                userId=rs.getInt(1);
            }
            if(rowEffect ==1 ){
                String sqlPivot ="INSERT INTO user_permision(user_id,permision_id)"+
                        "VALUES(?,?);";
                pstmtAssignment = connection.prepareStatement(sqlPivot);
                for (int permisionId:permision) {
                    pstmtAssignment.setInt(1,userId);
                    pstmtAssignment.setInt(2,permisionId);
                    pstmtAssignment.executeUpdate();
                }
                connection.commit();
            }else {
                connection.rollback();
            }

        } catch (SQLException e) {
            try {

                if (connection != null)

                    connection.rollback();

            } catch (SQLException sql) {

                System.out.println(sql.getMessage());

            }

            System.out.println(e.getMessage());
        }finally {
            try {

                if (rs != null) rs.close();

                if (pstmt != null) pstmt.close();

                if (pstmtAssignment != null) pstmtAssignment.close();

                if (connection != null) connection.close();

            } catch (SQLException e) {

                System.out.println(e.getMessage());

            }
        }
    }


    public List<User> sortByCountry() {
        List<User> sortList = new ArrayList<>();
        try{
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(SORT_BY_COUNTRY);
            while (rs.next()){
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String email = rs.getString(3);
                String country = rs.getString(4);
                sortList.add(new User(id,name,email,country));
            }
        }catch (SQLException e){

        }

        return sortList;
    }
}
