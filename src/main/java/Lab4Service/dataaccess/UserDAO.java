package Lab4Service.dataaccess;

import Lab4Service.entities.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Stateless
public class UserDAO {
    @PersistenceContext(unitName = "data")
    private EntityManager em;

    public User findByLogin(String login){
        try {
            return (User) em.createQuery("select u from User u where u.login = :login").setParameter("login", login).getSingleResult();
        }catch (NoResultException ex){
            return null;
        }
    }

    public void updateUserToken(User user, String token){
        user = em.merge(user);
        user.setToken(token);
    }

    public User findByToken(String token) {
        try {
            return (User) em.createQuery("select u from User u where u.token = :token").setParameter("token", token).getSingleResult();
        }catch (NoResultException ex){
            return null;
        }
    }

    public void createUser(User user){
        em.persist(user);
    }

}