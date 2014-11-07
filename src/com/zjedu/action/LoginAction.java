package com.zjedu.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.zjedu.Params;
import com.zjedu.dao.UserDao;


public class LoginAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void login(){
		String username = requestParam(Params.USERNAME);
		String password = requestParam(Params.PASSWORD);
		if(username.equals("") || username == null || password.equals("") || password == null){
			sendMsg(false, "请求参数不能为空");
			return;
		}
		UserDao dao = new UserDao();
	    boolean success = dao.login(username, password);
	    if(success){
	    	sendMsg(true, "登入成功");
	    }else{
	    	sendMsg(false, "登入失败，请检查帐号密码");
	    }
	}

	@Override
	public String execute() throws Exception {
		HttpServletRequest request=ServletActionContext.getRequest();
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		if(username.equalsIgnoreCase("admin") && password.equalsIgnoreCase("admin")){
			System.out.println("login----->ok");
			success = "true";
		}
		else
			success = "false";
		return SUCCESS;
	}

	private String success;

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}
	
	

}
