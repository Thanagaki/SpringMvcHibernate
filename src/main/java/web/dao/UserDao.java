package web.dao;

import web.model.User;

import java.util.List;

public interface UserDao {

    List<User> index();
    User getUserById(long userId);
    void create(User user);
    void update(long userId, User user);
    void delete(long userId);


}
