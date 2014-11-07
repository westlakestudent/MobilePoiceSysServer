package com.zjedu.action;

import com.zjedu.Params;
import com.zjedu.dao.UserDao;

public class RegisterAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void register(){
		String username = requestParam(Params.USERNAME);
		String password = requestParam(Params.PASSWORD);
		if(username.equals("") || username == null || password.equals("") || password == null){
			sendMsg(false, "请求参数不能为空");
			return;
		}
		try{
			UserDao dao = new UserDao();
			if(dao.register(username, password)){
				sendMsg(true, "注册成功");
			}else{
				sendMsg(false, "注册失败");
			}
		}catch(Exception e){
			sendMsg(false, e.getMessage());
		}
	}
	
}
