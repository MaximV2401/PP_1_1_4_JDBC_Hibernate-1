package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.List;






public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }
    private String sql;
    SessionFactory sessionFactory = Util.getSessionFactory();

    @Override
    public void createUsersTable() {
        sql = "CREATE TABLE IF NOT EXISTS users(" +
                "id bigint AUTO_INCREMENT PRIMARY KEY not null ," +
                "name varchar(30)," +
                "lastname varchar(30)," +
                "age tinyint UNSIGNED)";
        Transaction tx;
        try (Session session = sessionFactory.openSession()){
            tx = session.beginTransaction();
            session.createNativeMutationQuery(sql).executeUpdate();
            tx.commit();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void dropUsersTable() {
        sql = "DROP TABLE IF EXISTS users";
        Transaction tx;
        try (Session session = sessionFactory.openSession()){
            tx = session.beginTransaction();
            session.createNativeMutationQuery(sql).executeUpdate();
            tx.commit();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction tx = null;
        User user = new User(name, lastName, age);
        try (Session session = sessionFactory.openSession()){
            tx = session.beginTransaction();
            session.persist(user);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }

        }

    }

    @Override
    public void removeUserById(long id) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()){
            User user = session.get(User.class, id);
            tx = session.beginTransaction();
            if (user !=null) {
                session.remove(user);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        try(Session session = sessionFactory.openSession()) {
           return  session.createQuery("from User", User.class).list();

        } catch (HibernateException e) {
            e.printStackTrace();

        }
        return null;
    }

    @Override
    public void cleanUsersTable() {
        sql = "DELETE FROM users";
        Transaction tx;
        try (Session session = sessionFactory.openSession()){
            tx = session.beginTransaction();
            session.createNativeMutationQuery(sql).executeUpdate();
            tx.commit();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}