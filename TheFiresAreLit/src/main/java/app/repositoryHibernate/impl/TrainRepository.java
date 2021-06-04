package app.repositoryHibernate.impl;

import app.model.Train;
import app.repositoryHibernate.RandomItemRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TrainRepository implements RandomItemRepository<Train> {

    private final SessionFactory sessionFactory;

    @Autowired
    public TrainRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Train> findAll() {
        Session session = sessionFactory.openSession();
        Query<Train> query = session.createQuery("from Train", Train.class);
        List<Train> items = query.getResultList();
        session.close();
        return items;
    }

    @Override
    public void save(Train item) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        session.saveOrUpdate(item);
        transaction.commit();
        session.close();
    }

    @Override
    public void deleteAll() {
        Session session = sessionFactory.openSession();
        Query<Train> query = session.createQuery("from Train", Train.class);
        List<Train> items = query.getResultList();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        for (Train item : items) {
            session.delete(item);
        }
        transaction.commit();
        session.close();
    }

    @Override
    public long count() {
        Session session = sessionFactory.openSession();
        Query<Train> query = session.createQuery("from Train", Train.class);
        long size = query.getResultList().size();
        session.close();
        return size;
    }

    @Override
    public Optional<Train> findById(long id) {
        Session session = sessionFactory.openSession();
        Train item = session.get(Train.class, id);
        session.close();
        return Optional.of(item);
    }

    @Override
    public Optional<Train> getRandomItem() {
        Session session = sessionFactory.openSession();
        Query<Train> query = session.createQuery("from Train order by rand()", Train.class).setMaxResults(1);
        Train item = query.getSingleResult();
        session.close();
        return Optional.of(item);
    }
}
