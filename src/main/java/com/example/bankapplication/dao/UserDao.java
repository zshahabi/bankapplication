package com.example.bankapplication.dao;

import com.example.bankapplication.HibernateUtils;
import com.example.bankapplication.entity.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserDao {
    public void updateUser(User u) {
        try (
                Session se = HibernateUtils.getSession()) {

            Transaction tx = se.beginTransaction();

            se.update(u);
            tx.commit();

        } catch (HibernateException ex) {
            System.out.println("Class UserDao : Method updateUser : " + ex.getMessage());
        }
    }
    public void saveUser(User u) {
        try (                Session se = HibernateUtils.getSession()){


            Transaction tx = se.beginTransaction();

            se.save(u);
            tx.commit();
        } catch (HibernateException ex) {
            System.out.println("Class UserDao : Method saveUser : " + ex.getMessage());
        }
    } public User findUserByNationalCode(long ncode) {
        try (
                Session se = HibernateUtils.getSession()) {

            Query<User> query =se.createNamedQuery("User.findByNationalCode");
            query.setParameter("ncode",ncode);

            if(query.list().size()==0){
                return null;
            }
           return query.getSingleResult();

        } catch (HibernateException ex) {
            System.out.println("Class UserDao : Method fidnUserByNationalCode : " + ex.getMessage());
        }
        return null;
    }
    public List<User> findUserByName(String name) {
        try (
                Session se = HibernateUtils.getSession()) {

            Query<User> query =se.createNamedQuery("User.findByName");
            query.setParameter("fname",name);

            if(query.list().size()==0){
                return null;
            }
           return query.list();

        } catch (HibernateException ex) {
            System.out.println("Class UserDao : Method findUserByName : " + ex.getMessage());
        }
        return null;
    } public List<User> findUserByLastName(String lname) {
        try (
                Session se = HibernateUtils.getSession()) {

            Query<User> query =se.createNamedQuery("User.findByLastName");
            query.setParameter("lname",lname);

            if(query.list().size()==0){
                return null;
            }
           return query.list();

        } catch (HibernateException ex) {
            System.out.println("Class UserDao : Method findByLastName : " + ex.getMessage());
        }
        return null;
    }
}
