package com.zjedu.action;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zjedu.Params;
import com.zjedu.dao.MsgDao;
import com.zjedu.po.Message;

public class MsgAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private MsgDao dao = new MsgDao();
	public void release(){
		String username = requestParam(Params.USERNAME);
		String city = requestParam(Params.MSG_CITY);
		String time = requestParam(Params.MSG_TIME);
		String content = requestParam(Params.MSG_CONTENT);
		if(username == null || username.equals("") || content == null || content.equals("")){
			sendMsg(false, "发表失败,请求参数不能为空");
			return;
		}
		System.out.println("msg receive content -->" + content);
		try{
			Message msg = new Message();
			msg.setCity(city);
			msg.setMsg_content(content);
			msg.setSendtime(time);
			msg.setUsername(username);
			if(dao.saveMsg(msg)){
				sendMsg(true, "发表成功");
			}else{
				sendMsg(false, "发表失败");
			}
		}catch(Exception e){
			sendMsg(false, e.getMessage());
		}
		
	}
	
	
	public void getmsgs(){
		try{
			List<Message> messages = dao.findAllMsg();
			Gson gson = new Gson();
			Type type = new TypeToken<List<Message>>() {
			}.getType();
			if(messages.size() > 0){
				sendMsg(true, gson.toJson(messages, type));
				System.out.println("查询msgs------>size=" + messages.size());
			}
			else 
				sendMsg(false, "没有取相关消息");
		}catch(Exception e){
			sendMsg(false, e.getMessage());
			System.out.println("exception---" + e);
		}
	}
}
