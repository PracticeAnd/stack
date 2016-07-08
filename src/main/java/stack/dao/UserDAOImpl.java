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
       /* String sqlInsert = "INSERT INTO public.\"Users\" (login, password) VALUES (?, ?)";
        String sqlUpdate = "UPDATE public.\"Users\" SET firstName = ?, secondName = ?, password = ?, email = ? WHERE user_id = ?";
*/
        String sqlInsert = "INSERT INTO User (login, password, email) VALUES (?, ?, ?)";
        String sqlUpdate = "UPDATE User SET firstName = ?, lastName = ?, password = ?, email = ? WHERE user_id = ?";


        if (user.getId() == null)
            jdbcTemplate.update(sqlInsert, getPreparedStatementForInsert(user));
        else {
            jdbcTemplate.update(sqlUpdate, getPreparedStatementForUpdate(user));
        }
    }

    //Метод для вывода полного списка юзеров
    @Override
    public List<User> listOfUser() {
        //String sql = "SELECT * FROM public.\"Users\"";
        String sql = "SELECT * FROM User";
        List<User> userList = jdbcTemplate.query(sql, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                User user = new User();

                user.setId(resultSet.getInt("user_id"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));
                user.setFirstName(resultSet.getString("firstname"));
                user.setLastName(resultSet.getString("lastname"));

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

    //Метод для вытаскивания из базы юзера по id
    @Override
    public User getUser(Integer userId) {
        //String sql = "SELECT * FROM public.Users WHERE user_id = " + userId;
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
                    user.setFirstName(resultSet.getString("firstname"));
                    user.setLastName(resultSet.getString("lastname"));

                    return user;
                }
                return null;
            }
        });
    }

    @Override
    public User getUserByLogin(String login) {
//        String sql = "SELECT * FROM public.Users WHERE login = '" + login + "'";
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
                    user.setFirstName(resultSet.getString("firstname"));
                    user.setLastName(resultSet.getString("lastname"));

                    return user;
                }
                return null;
            }
        });
    }

    //Вспомогательный Метод для создания пользователя
    private PreparedStatementSetter getPreparedStatementForInsert(final User user) {
        return new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, user.getLogin());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.setString(3, user.getEmail());
            }
        };
    }

    //Вспомогательный метод для обновления информации о пользователе
    private PreparedStatementSetter getPreparedStatementForUpdate(final User user) {
        return new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, user.getFirstName());
                preparedStatement.setString(2, user.getLastName());
                preparedStatement.setString(3, user.getPassword());
                preparedStatement.setString(4, user.getEmail());
                preparedStatement.setInt(5, user.getId());
            }
        };
    }
}
