package app.repositoryHibernate.impl;

import app.model.Log;
import app.repositoryHibernate.BaseRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class LogRepository implements BaseRepository<Log> {

    private final SessionFactory sessionFactory;

    @Autowired
    public LogRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Log> findAll() {
        Session session = sessionFactory.openSession();
        Query<Log> query = session.createQuery("from Log", Log.class);
        List<Log> items = query.getResultList();
        session.close();
        return items;
    }

    @Override
    public void save(Log item) {
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
        Query<Log> query = session.createQuery("from Log", Log.class);
        List<Log> items = query.getResultList();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        for (Log item : items) {
            session.delete(item);
        }
        transaction.commit();
        session.close();
    }

    @Override
    public long count() {
        Session session = sessionFactory.openSession();
        Query<Log> query = session.createQuery("from Log", Log.class);
        long size = query.getResultList().size();
        session.close();
        return size;
    }

    @Override
    public Optional<Log> findById(long id) {
        Session session = sessionFactory.openSession();
        Log item = session.get(Log.class, id);
        session.close();
        return Optional.of(item);
    }
}
