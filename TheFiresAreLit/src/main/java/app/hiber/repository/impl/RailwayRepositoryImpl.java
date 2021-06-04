package app.hiber.repository.impl;

import app.hiber.repository.RailwayRepository;
import app.model.Railway;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RailwayRepositoryImpl implements RailwayRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public RailwayRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Railway> findAll() {
        Session session = sessionFactory.openSession();
        Query<Railway> query = session.createQuery("from Railway", Railway.class);
        List<Railway> items = query.getResultList();
        session.close();
        return items;
    }

    @Override
    public void save(Railway item) {
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
        Query<Railway> query = session.createQuery("from Railway", Railway.class);
        List<Railway> items = query.getResultList();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        for (Railway item : items) {
            session.delete(item);
        }
        transaction.commit();
        session.close();
    }

    @Override
    public long count() {
        Session session = sessionFactory.openSession();
        Query<Railway> query = session.createQuery("from Railway", Railway.class);
        long size = query.getResultList().size();
        session.close();
        return size;
    }

    @Override
    public Optional<Railway> findById(Integer id) {
        Session session = sessionFactory.openSession();
        Railway item = session.get(Railway.class, id);
        session.close();
        return Optional.of(item);
    }
}
