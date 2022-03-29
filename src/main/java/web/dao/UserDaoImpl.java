package web.dao;

import org.springframework.stereotype.Repository;
import web.model.User;

import javax.persistence.*;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    private final EntityManagerFactory entityManagerFactory;

    public UserDaoImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @PersistenceContext
    private EntityManager entityManager;



    @Override
    @SuppressWarnings("unchecked")
    public List<User> index() {
        return
       entityManager.createQuery("FROM User").getResultList();
    }

    @Override
    public User getUserById(long userId){
       return  entityManager.find(User.class,userId);
    }

    @Override
    public void create(User user) {
        entityManager.persist(user);
    }

    public void update(long userId, User user){
        entityManager.detach(user);
        User updateUser = getUserById(userId);
        updateUser.setAge(user.getAge());
        updateUser.setName(user.getName());
        updateUser.setSurname(user.getSurname());
        updateUser.setEmail(user.getEmail());
        entityManager.persist(updateUser);

    }

    @Override
    public void delete(long userId) {
        entityManager.remove(getUserById(userId));
    }

}

