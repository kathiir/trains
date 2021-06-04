package app.repositoryHibernate.impl;

import app.model.SchedulePart;
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
public class SchedulePartRepository implements BaseRepository<SchedulePart> {

    private final SessionFactory sessionFactory;

    @Autowired
    public SchedulePartRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<SchedulePart> findAll() {
        Session session = sessionFactory.openSession();
        Query<SchedulePart> query = session.createQuery("from SchedulePart", SchedulePart.class);
        List<SchedulePart> items = query.getResultList();
        session.close();
        return items;
    }

    @Override
    public void save(SchedulePart item) {
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
        Query<SchedulePart> query = session.createQuery("from SchedulePart", SchedulePart.class);
        List<SchedulePart> items = query.getResultList();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        for (SchedulePart item : items) {
            session.delete(item);
        }
        transaction.commit();
        session.close();
    }

    @Override
    public long count() {
        Session session = sessionFactory.openSession();
        Query<SchedulePart> query = session.createQuery("from SchedulePart", SchedulePart.class);
        long size = query.getResultList().size();
        session.close();
        return size;
    }

    @Override
    public Optional<SchedulePart> findById(long id) {
        Session session = sessionFactory.openSession();
        SchedulePart item = session.get(SchedulePart.class, id);
        session.close();
        return Optional.of(item);
    }
}
