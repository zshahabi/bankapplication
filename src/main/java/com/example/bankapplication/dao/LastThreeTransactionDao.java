package com.example.bankapplication.dao;

import com.example.bankapplication.HibernateUtils;
import com.example.bankapplication.entity.LastThreeTransaction;
import com.example.bankapplication.entity.LastThreeUpdate;
import com.example.bankapplication.entity.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class LastThreeTransactionDao {
    public void delete(LastThreeTransaction u) {
        try (
                Session se = HibernateUtils.getSession()) {

            Transaction tx = se.beginTransaction();

            se.delete(u);
            tx.commit();
        } catch (HibernateException ex) {
            System.out.println("Class LastThreeTransactionDao : Method delete : " + ex.getMessage());
        }
    }


}
