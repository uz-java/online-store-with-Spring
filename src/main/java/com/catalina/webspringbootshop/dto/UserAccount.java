package com.catalina.webspringbootshop.dto;

import org.apache.catalina.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class UserAccount {

    @Autowired
    private SessionFactory sessionFactory;

    public User findAccount(String userName) {
        Session session = this.sessionFactory.getCurrentSession();
        return session.find(User.class, userName);
    }

}
