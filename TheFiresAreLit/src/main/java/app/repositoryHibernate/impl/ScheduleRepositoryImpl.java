package app.repositoryHibernate.impl;

import app.model.Schedule;
import app.repositoryHibernate.BaseRepository;
import app.repositoryHibernate.ScheduleRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ScheduleRepositoryImpl implements ScheduleRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public ScheduleRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Schedule> findAll() {
        Session session = sessionFactory.openSession();
        Query<Schedule> query = session.createQuery("from Schedule", Schedule.class);
        List<Schedule> items = query.getResultList();
        session.close();
        return items;
    }

    @Override
    public void save(Schedule item) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        session.save(item);
        transaction.commit();
        session.close();
    }

    @Override
    public void deleteAll() {
        Session session = sessionFactory.openSession();
        Query<Schedule> query = session.createQuery("from Schedule", Schedule.class);
        List<Schedule> items = query.getResultList();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        for (Schedule item : items) {
            session.delete(item);
        }
        transaction.commit();
        session.close();
    }

    @Override
    public long count() {
        Session session = sessionFactory.openSession();
        Query<Schedule> query = session.createQuery("from Schedule", Schedule.class);
        long size = query.getResultList().size();
        session.close();
        return size;
    }

    @Override
    public Optional<Schedule> findById(long id) {
        Session session = sessionFactory.openSession();
        Schedule item = session.get(Schedule.class, id);
        session.close();
        return Optional.of(item);
    }
}
