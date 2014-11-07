package com.zjedu.dao;


import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.zjedu.po.User;


public class UserDao {

	public static SessionFactory mSessionFactory;
	static{
		try{
			Configuration mConfig = new Configuration();
			mConfig.addClass(User.class);
			mSessionFactory = mConfig.configure().buildSessionFactory();
		}catch(RuntimeException e){
			e.printStackTrace();
			throw e;
		}
	}
	
	
	public boolean login(String username,String password){
		boolean done = false;
		Session mSession = mSessionFactory.getCurrentSession();
		Transaction tx = mSession.beginTransaction();
		Query mQuery = mSession.createSQLQuery("select * from user where username = ? and password = ?").addEntity(User.class);
		mQuery.setString(0, username);
		mQuery.setString(1, password);
		int r = mQuery.list().size();
		if(r != 0)
			done = true;
		tx.commit();
		return done;
	}
	
	
	public boolean register(String username,String password){
		boolean done = true;
		User user = new User();
		user.setPassword(password);
		user.setUsername(username);
		Session mSession = mSessionFactory.getCurrentSession();
		Transaction tx = mSession.beginTransaction();
		Object obj = mSession.save(user);
		if(obj == null)
			done = false;
		tx.commit();
		return done;
	}
	
	
}
