package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.List;






public class UserDaoHibernateImpl implements UserDao {

    private  final String CREATE = "CREATE TABLE IF NOT EXISTS users(" +
            "id bigint AUTO_INCREMENT PRIMARY KEY not null ," +
            "name varchar(30)," +
            "lastname varchar(30)," +
            "age tinyint UNSIGNED)";
    private final String DROP = "DROP TABLE IF EXISTS users";

    private final String CLEAN = "TRUNCATE FROM users";


    public UserDaoHibernateImpl() {

    }
    private String sql;
    SessionFactory sessionFactory = Util.getSessionFactory();

    @Override
    public void createUsersTable() {


        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.createNativeMutationQuery(CREATE).executeUpdate();
           session.getTransaction().commit();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void dropUsersTable() {


        try (Session session = sessionFactory.openSession()){
           session.beginTransaction();
            session.createNativeMutationQuery(DROP).executeUpdate();
            session.getTransaction().commit();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        User user = new User(name, lastName, age);
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.persist(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }

        }

    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            User user = session.get(User.class, id);
            transaction = session.beginTransaction();
            if (user !=null) {
                session.remove(user);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
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


        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.createNativeMutationQuery(CLEAN).executeUpdate();
            session.getTransaction().commit();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}