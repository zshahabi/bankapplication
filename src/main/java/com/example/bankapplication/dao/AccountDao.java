package com.example.bankapplication.dao;

import com.example.bankapplication.HibernateUtils;
import com.example.bankapplication.entity.Account;
import com.example.bankapplication.entity.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class AccountDao {
    public void saveAccount(Account u) {
        try (
                Session se = HibernateUtils.getSession()) {

            Transaction tx = se.beginTransaction();

            se.save(u);
            tx.commit();
        } catch (HibernateException ex) {
            System.out.println("Class AccountDao : Method saveAccount : " + ex.getMessage());
        }
    }   public void updateAccount(Account u) {
        try (
                Session se = HibernateUtils.getSession()) {

            Transaction tx = se.beginTransaction();

            se.update(u);
            tx.commit();
        } catch (HibernateException ex) {
            System.out.println("Class AccountDao : Method saveAccount : " + ex.getMessage());
        }
    }
    public Account findAccountByCardNumber(long anum) {
        try (                Session se = HibernateUtils.getSession()){

            Query<Account> query =se.createNamedQuery("Account.findAccountByCardNumber");
            query.setParameter("anum",anum);

            if(query.list().size()==0){
              return null;
          }
            return query.getSingleResult();

        } catch (HibernateException ex) {
            System.out.println("Class AccountDao : Method findAccountByCardNumber : " + ex.getMessage());
        }
        return null;
    }

}
