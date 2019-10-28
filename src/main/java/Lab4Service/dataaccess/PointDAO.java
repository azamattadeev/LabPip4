package Lab4Service.dataaccess;

import Lab4Service.entities.Point;
import Lab4Service.entities.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class PointDAO {
    @PersistenceContext(unitName = "data")
    private EntityManager em;

    public List<Point> findAllByOwner(User user){
        try {
            user = em.merge(user);
            List<Point> points = (List<Point>) em.createQuery("select u.points from User u where u.id = :id")
                    .setParameter("id", user.getId())
                    .getResultList();
            return points;
        }catch (NoResultException ex){
            return new ArrayList<>();
        }
    }

    public void createPoint(Point point){
        em.persist(point);
    }
}
