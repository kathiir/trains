package app.hiber.repository.impl;

import app.hiber.repository.RailRepository;
import app.model.Rail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RailRepositoryImpl implements RailRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public RailRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Rail> findAll() {
        Session session = sessionFactory.openSession();
        Query<Rail> query = session.createQuery("from Rail", Rail.class);
        List<Rail> items = query.getResultList();
        session.close();
        return items;
    }

    @Override
    public void save(Rail item) {
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
        Query<Rail> query = session.createQuery("from Rail", Rail.class);
        List<Rail> items = query.getResultList();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        for (Rail item : items) {
            session.delete(item);
        }
        transaction.commit();
        session.close();
    }

    @Override
    public long count() {
        Session session = sessionFactory.openSession();
        Query<Rail> query = session.createQuery("from Rail", Rail.class);
        long size = query.getResultList().size();
        session.close();
        return size;
    }

    @Override
    public Optional<Rail> findById(Integer id) {
        Session session = sessionFactory.openSession();
        Rail item = session.get(Rail.class, id);
        session.close();
        return Optional.of(item);
    }

    @Override
    public Optional<Rail> getRandomItem() {
        Session session = sessionFactory.openSession();
        Query<Rail> query = session.createQuery("from Rail order by rand()", Rail.class).setMaxResults(1);
        Rail item = query.getSingleResult();
        session.close();
        return Optional.of(item);
    }
}
