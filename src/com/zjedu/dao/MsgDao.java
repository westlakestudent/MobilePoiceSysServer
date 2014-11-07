package com.zjedu.dao;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.zjedu.po.Message;


public class MsgDao {

	public static SessionFactory mSessionFactory;
	static{
		try{
			Configuration mConfig = new Configuration();
			mConfig.addClass(Message.class);
			mSessionFactory = mConfig.configure().buildSessionFactory();
		}catch(RuntimeException e){
			e.printStackTrace();
			throw e;
		}
	}
	
	
	public boolean saveMsg(Message msg){
		boolean done = false;
		Session mSession = mSessionFactory.getCurrentSession();
		Transaction tx = mSession.beginTransaction();
		Object obj = mSession.save(msg);
		if(obj != null)
			done = true;
		tx.commit();
		return done;
	}
	
	@SuppressWarnings("unchecked")
	public List<Message> findAllMsg(){
		Session mSession = mSessionFactory.getCurrentSession();
		Transaction tx = mSession.beginTransaction();
		List<Message> messages = null;
		String sql = "select * from msg";
		Query query = mSession.createSQLQuery(sql).addEntity(Message.class);
		messages = query.list();
		tx.commit();
		return messages;
		
	}
	
	
	
//	public static void main(String[]args){
//		MsgDao dao = new MsgDao();
//		List<Message> messages = dao.findAllMsg();
//		if(messages.size() > 0){
//			for(Message msg : messages){
//				System.out.println("msg ----" + msg.getMsg_content());
//			}
//		}
//		
//	}
}
