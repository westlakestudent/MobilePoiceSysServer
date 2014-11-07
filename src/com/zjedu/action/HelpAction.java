package com.zjedu.action;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zjedu.Params;
import com.zjedu.dao.HelpDao;
import com.zjedu.po.Help;

public class HelpAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HelpDao dao = new HelpDao();
	public void forhelp(){
		String username = requestParam(Params.USERNAME);
		String addr = requestParam(Params.HELP_ADDR);
		String time = requestParam(Params.HELP_TIME);
		String content = requestParam(Params.HELP_CONTENT);
		
		if(username == null || username.equals("") || content == null || content.equals("")){
			sendMsg(false, "请求失败,请求参数不能为空");
			return;
		}
		
		System.out.println("help receive on server ; content is --->" + content);
		try{
			Help help = new Help();
			help.setAddr(addr);
			help.setContent(content);
			help.setTime(time);
			help.setUsername(username);
			if(dao.saveHelp(help)){
				sendMsg(true, "请求成功");
			}else{
				sendMsg(false, "请求失败");
			}
		}catch(Exception e){
			sendMsg(false, e.getMessage());
		}
		
	}
	
	public void queryhelp(){
		String username = requestParam(Params.USERNAME);
		if(username == null || username.equals("")){
			sendMsg(false, "请求参数不能为空");
			return;
		}
		try{
			List<Help> helps = dao.findHelps(username);
			Gson gson = new Gson();
			Type type = new TypeToken<List<Help>>() {
			}.getType();
			if(helps.size() > 0){
				sendMsg(true, gson.toJson(helps, type));
				System.out.println("查询help------>size=" + helps.size() );
			}
			else 
				sendMsg(false, "没有请求援救");
		}catch(Exception e){
			sendMsg(false, e.getMessage());
			System.out.println("exception---" + e);
		}
	}
	
	public void saveHelpReceive(){
		String username = requestParam(Params.USERNAME);
		String helpids = requestParam(Params.HELPIDS);
		if(username == null || username.equalsIgnoreCase("") || helpids == null || helpids.equals("")){
			sendMsg(false, "请求参数不能为空");
			return;
		}
		
		String[] ids = helpids.split(",");
		try{
			String result = dao.saveHelprecvs(username, ids);
			if(result == null || result.equals("")){
				sendMsg(true, "已收到help反馈");
			}else{
				sendMsg(false, "未收到help反馈" + result);
			}
		}catch(Exception e){
			sendMsg(false, "未收到help反馈");
		}
		
	}

}
