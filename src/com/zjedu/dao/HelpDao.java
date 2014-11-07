package com.zjedu.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.zjedu.po.Help;
import com.zjedu.po.HelpReceive;


public class HelpDao {

	public static SessionFactory mSessionFactory;
	static{
		try{
			Configuration mConfig = new Configuration();
			mConfig.addClass(Help.class);
			mSessionFactory = mConfig.configure().buildSessionFactory();
		}catch(RuntimeException e){
			e.printStackTrace();
			throw e;
		}
	}
	
	public boolean saveHelp(Help help){
		boolean done = false;
		Session mSession = mSessionFactory.getCurrentSession();
		Transaction tx = mSession.beginTransaction();
		Object obj = mSession.save(help);
		if(obj != null)
			done = true;
		tx.commit();
		return done;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Help> findHelps(String username){
		Session mSession = mSessionFactory.getCurrentSession();
		Transaction tx = mSession.beginTransaction();
		List<Help> helps = null;
		boolean same = false;
		List<HelpReceive> receives = null;
		List<Help> tmpHelps = new ArrayList<Help>();
		List<Help> realHelps = new ArrayList<Help>();
		String sql = "select * from help ";
		Query query = mSession.createSQLQuery(sql).addEntity(Help.class);
		helps = query.list();
		
		
		sql = "select * from helpreceive where username = ?";
		query = mSession.createSQLQuery(sql).addEntity(HelpReceive.class);
		query.setString(0, username);
		receives = query.list();
		
		if(helps.size() > 0 && receives.size() > 0){
			for(Help help : helps){
				same = false;
				for(HelpReceive receive : receives){
					if(help.getId() == receive.getHelpid()){
						same = true;
						break;
					}
				}
				if(!same)
					tmpHelps.add(help);
			}
		}else if(helps.size() > 0 && receives.size() <= 0){
			tmpHelps = helps;
		}
		
		
		if(tmpHelps.size() > 0){
			for(Help h : tmpHelps){
				if(!h.getUsername().equalsIgnoreCase(username))
					realHelps.add(h);
			}
		}
		tx.commit();
		return realHelps;
	}
	
	
	public String saveHelprecvs(String username,String[]helpids){
		StringBuffer buf = new StringBuffer();
		for(String helpid :helpids){
			HelpReceive hrecv = new HelpReceive();
			hrecv.setHelpid(Integer.valueOf(helpid));
			hrecv.setUsername(username);
			if(!saveHelprecv(hrecv)){
				buf.append(helpid + ",");
			}
		}
		return buf.toString();
	}
	
	public boolean saveHelprecv(HelpReceive hrecv){
		boolean done = false;
		Session mSession = mSessionFactory.getCurrentSession();
		Transaction tx = mSession.beginTransaction();
		Object obj = mSession.save(hrecv);
		if(obj != null){
			done = true;
			System.out.println("haved saved a helprecv");
		}
		tx.commit();
		return done;		
	}
	
//	public static void main(String[]args){
//		HelpDao dao = new HelpDao();
//		Help help = new Help();
//		help.setAddr("杭州市508号");
//		help.setContent("今天要下雨啊");
//		if(dao.saveHelp(help))
//			System.out.println("ok-->");
//		System.out.println(dao.findHelps("fuck").size());
//		
//	}
}
