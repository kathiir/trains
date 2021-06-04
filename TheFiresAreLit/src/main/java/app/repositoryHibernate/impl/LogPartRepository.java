package app.repositoryHibernate.impl;

import app.model.LogPart;
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
public class LogPartRepository implements BaseRepository<LogPart> {

    private final SessionFactory sessionFactory;

    @Autowired
    public LogPartRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<LogPart> findAll() {
        Session session = sessionFactory.openSession();
        Query<LogPart> query = session.createQuery("from LogPart", LogPart.class);
        List<LogPart> items = query.getResultList();
        session.close();
        return items;
    }

    @Override
    public void save(LogPart item) {
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
        Query<LogPart> query = session.createQuery("from LogPart", LogPart.class);
        List<LogPart> items = query.getResultList();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        for (LogPart item : items) {
            session.delete(item);
        }
        transaction.commit();
        session.close();
    }

    @Override
    public long count() {
        Session session = sessionFactory.openSession();
        Query<LogPart> query = session.createQuery("from LogPart", LogPart.class);
        long size = query.getResultList().size();
        session.close();
        return size;
    }

    @Override
    public Optional<LogPart> findById(long id) {
        Session session = sessionFactory.openSession();
        LogPart item = session.get(LogPart.class, id);
        session.close();
        return Optional.of(item);
    }
}
