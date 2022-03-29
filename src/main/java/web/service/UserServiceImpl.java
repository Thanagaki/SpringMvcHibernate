package web.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.UserDao;
import web.model.User;

import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private final UserDao userDao;

    public UserServiceImpl (UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional
    @Override
    public List<User> index() {
        return userDao.index();
    }

    @Transactional
    @Override
    public User getUserById(long userId){
        return  userDao.getUserById(userId);
    }

    @Transactional
    @Override
    public void create(User user) {
        userDao.create(user);
    }
    @Transactional
    @Override
    public  void update(long userId, User user){
        userDao.update(userId,user);

    }
    @Transactional
    @Override
   public  void delete(long userId){
        userDao.delete(userId);

    }

}
