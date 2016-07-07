package stack.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import stack.model.User;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    private JdbcTemplate jdbcTemplate;

    public UserDAOImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //Добалвение нового пользователя в БД
    @Override
    public void addOrUpdateUser(User user) {
        //String sql = "INSERT INTO stack.public.\"Users\" (login, password) VALUES (?, ?, ?)";
        /*String sql = "INSERT INTO User (login, password, email, firstName, lastName, user_id) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, getPreparedStatement(user));
*/
        Integer id = user.getId();
        if (id == null) {
            String sql = "INSERT INTO User (login, password, email, firstName, lastName) VALUES (?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql,
                    user.getLogin(),
                    user.getPassword(),
                    user.getEmail(),
                    user.getFirstName(),
                    user.getLastName());
        } else {
            jdbcTemplate.update("UPDATE USER SET login=?, password=?, email=?, firstName=?, lastName=? WHERE user_id=?",
                    user.getLogin(),
                    user.getPassword(),
                    user.getEmail(),
                    user.getFirstName(),
                    user.getLastName(),
                    id);
        }
    }

    //Метод для вывода полного списка юзеров
    @Override
    public List<User> listOfUser() {
        //String sql = "SELECT * FROM stack.public.\"Users\"";
        String sql = "SELECT * FROM User";
        List<User> userList = jdbcTemplate.query(sql, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                User user = new User();

                user.setId(resultSet.getInt("user_id"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));
                user.setFirstName(resultSet.getString("firstName"));
                user.setLastName(resultSet.getString("lastName"));
                return user;
            }
        });

        return userList;
    }

    //Удаление пользователя
    //Дописать
    @Override
    public void removeUser(Integer id) {

    }

    //Метод для изменения или создания пользователя
    private PreparedStatementSetter getPreparedStatement(final User user) {
        return new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                int i = 0;

                preparedStatement.setString(++i, user.getLogin());
                preparedStatement.setString(++i, user.getPassword());
                preparedStatement.setString(++i, user.getEmail());
                preparedStatement.setString(++i, user.getFirstName());
                preparedStatement.setString(++i, user.getLastName());
                preparedStatement.setInt(++i, user.getId());
                //
            }
        };
    }

    //Метод для вытаскивания из базы юзера по id
    @Override
    public User getUser(Integer userId) {
        //String sql = "SELECT * FROM stack.Users WHERE user_id = " + userId;
        String sql = "SELECT * FROM User WHERE user_id = " + userId;

        return jdbcTemplate.query(sql, new ResultSetExtractor<User>() {
            @Override
            public User extractData(ResultSet resultSet) throws SQLException,
                    DataAccessException {
                if (resultSet.next()) {
                    User user = new User();

                    user.setId(resultSet.getInt("user_id"));
                    user.setLogin(resultSet.getString("login"));
                    user.setPassword(resultSet.getString("password"));
                    user.setEmail(resultSet.getString("email"));
                    user.setFirstName(resultSet.getString("firstName"));
                    user.setLastName(resultSet.getString("lastName"));

                    return user;
                }
                return null;
            }
        });
    }

    //Метод для вытаскивания из базы юзера по login
    @Override
    public User getUserByLogin(String login) {
        //String sql = "SELECT * FROM stack.Users WHERE login = " + login;
        String sql = "SELECT * FROM User WHERE login = '" + login + "'";

        return jdbcTemplate.query(sql, new ResultSetExtractor<User>() {
            @Override
            public User extractData(ResultSet resultSet) throws SQLException,
                    DataAccessException {
                if (resultSet.next()) {
                    User user = new User();

                    user.setId(resultSet.getInt("user_id"));
                    user.setLogin(resultSet.getString("login"));
                    user.setPassword(resultSet.getString("password"));
                    user.setEmail(resultSet.getString("email"));
                    user.setFirstName(resultSet.getString("firstName"));
                    user.setLastName(resultSet.getString("lastName"));

                    return user;
                }
                return null;
            }
        });
    }
}
