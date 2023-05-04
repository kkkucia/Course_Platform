package org.example.actions;

import org.example.utils.DbElement;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class DBActions {

    private Session session;
    private Transaction tx;

    public DBActions(Session session) {
        this.session = session;
        this.tx = session.beginTransaction();
    }

    public void addNewElement(DbElement dbElement){
        session.save(dbElement);
        tx.commit();
    }

    public void updateElement(DbElement oldElement, DbElement newElement){
//        TODO
    }
}
