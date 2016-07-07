package stack.dao;

import stack.model.User;

import java.util.List;

public interface UserDAO {
    public void addOrUpdateUser(User user);

    public List<User> listOfUser();

    public void removeUser(Integer id);

    public User getUser(Integer id);

    public User getUserByLogin(String login);
}
