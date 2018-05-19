package app.models;

import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;

/**
 * Created by dragosbontea on 19/05/2018.
 */

@Repository
@Transactional
public class TrainDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void create(Train user) {
        entityManager.persist(user);
        return;
    }

    public void update(Train user) {
        entityManager.merge(user);
        return;
    }

    public Train getByName(String name) {
        return (Train) entityManager.createQuery(
                "from Train where name = :name")
                .setParameter("name", name)
                .getSingleResult();
    }

}
