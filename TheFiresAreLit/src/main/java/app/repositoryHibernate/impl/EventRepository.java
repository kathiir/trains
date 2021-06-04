package app.repositoryHibernate.impl;

import app.model.Event;
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
public class EventRepository implements BaseRepository<Event> {

    private final SessionFactory sessionFactory;

    @Autowired
    public EventRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Event> findAll() {
        Session session = sessionFactory.openSession();
        Query<Event> query = session.createQuery("from Event", Event.class);
        List<Event> items = query.getResultList();
        session.close();
        return items;
    }

    @Override
    public void save(Event item) {
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
        Query<Event> query = session.createQuery("from Event", Event.class);
        List<Event> items = query.getResultList();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        for (Event item : items) {
            session.delete(item);
        }
        transaction.commit();
        session.close();
    }

    @Override
    public long count() {
        Session session = sessionFactory.openSession();
        Query<Event> query = session.createQuery("from Event", Event.class);
        long size = query.getResultList().size();
        session.close();
        return size;
    }

    @Override
    public Optional<Event> findById(long id) {
        Session session = sessionFactory.openSession();
        Event item = session.get(Event.class, id);
        session.close();
        return Optional.of(item);
    }

}
