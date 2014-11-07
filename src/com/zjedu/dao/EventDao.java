package com.zjedu.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.zjedu.po.Event;
import com.zjedu.po.EventReceive;

public class EventDao {

	public static SessionFactory mSessionFactory;
	static{
		try{
			Configuration mConfig = new Configuration();
			mConfig.addClass(Event.class);
			mSessionFactory = mConfig.configure().buildSessionFactory();
		}catch(RuntimeException e){
			e.printStackTrace();
			throw e;
		}
	}
	
	public boolean insertEvent(Event event){
		boolean done = false;
		Session mSession = mSessionFactory.getCurrentSession();
		Transaction tx = mSession.beginTransaction();
		Object obj = mSession.save(event);
		if(obj != null)
			done = true;
		tx.commit();
		return done;
	}
	
	@SuppressWarnings("unchecked")
	public List<Event>findEvents(String status,String username){
		Session mSession = mSessionFactory.getCurrentSession();
		Transaction tx = mSession.beginTransaction();
		boolean same = false;
		List<Event> events = null;
		List<EventReceive> receives = null;
		List<Event> evs = new ArrayList<Event>();
		String sql = "select * from event where status = ?";
		Query query = mSession.createSQLQuery(sql).addEntity(Event.class);
		query.setString(0, status);
		events = query.list();
		
		sql = "select * from eventreceive where username = ?";
		query = mSession.createSQLQuery(sql).addEntity(EventReceive.class);
		query.setString(0, username);
		receives = query.list();
		if(receives.size() >0 && events.size() >0){
			for(Event event : events){
				same = false;
				for(EventReceive receive : receives){
					if(event.getId() == receive.getEventid()){
						same = true;
						break;
					}
				}
				if(!same)
					evs.add(event);
			}
		}
		if(receives.size() <=0 && events.size() >0){
			evs = events;
		}
		tx.commit();
		return evs;
	}
	
	
	@SuppressWarnings("unchecked")
	public boolean saveReceive(EventReceive receive){
		boolean done = false;
		boolean same = false;
		Session mSession = mSessionFactory.getCurrentSession();
		Transaction tx = mSession.beginTransaction();
		List<EventReceive> receives = null;
		String sql = "select * from eventreceive where username = ?"; 
		Query query = mSession.createSQLQuery(sql).addEntity(EventReceive.class);
		query.setString(0, receive.getUsername());
		receives = query.list();
		for(EventReceive rev : receives){
			if(rev.getEventid() == receive.getEventid())
				same = true;
		}
		if(!same){
			Object obj = mSession.save(receive);
			if(obj != null)
				done = true;
		}
		tx.commit();
		return done;
	}
	
}
