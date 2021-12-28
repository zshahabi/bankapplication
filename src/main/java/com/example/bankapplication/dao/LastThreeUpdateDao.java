package com.example.bankapplication.dao;

import com.example.bankapplication.HibernateUtils;
import com.example.bankapplication.entity.LastThreeUpdate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class LastThreeUpdateDao {
    public void delete(LastThreeUpdate u) {
        try (
                Session se = HibernateUtils.getSession()) {

            Transaction tx = se.beginTransaction();

            se.delete(u);
            tx.commit();
        } catch (HibernateException ex) {
            System.out.println("Class LastThreeUpdateDao : Method delete : " + ex.getMessage());
        }
    }
}
