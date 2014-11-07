package com.zjedu.action;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected HttpServletRequest httpRequest() {
		return ServletActionContext.getRequest();
	}
	
	protected HttpServletResponse httpResponse(){
		return ServletActionContext.getResponse();
	}
	
	protected String requestParam(String key){
		return ServletActionContext.getRequest().getParameter(key);
	}
	
	
	protected void sendMsg(boolean flag,String content){
		HttpServletResponse resp=ServletActionContext.getResponse();
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		resp.addHeader("Cache-Control","no-cache");
		JSONObject result=new JSONObject();
		result.accumulate("type", flag?"success":"fail");
		result.accumulate("value", content);
		try {
			resp.getWriter().write(result.toString());
		} catch (IOException e) {
			System.out.println("error---" + e);
		}
	}

	
	protected void sendMsg(boolean flag,JSONObject content){
		HttpServletResponse resp=ServletActionContext.getResponse();
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		resp.addHeader("Cache-Control","no-cache");
		JSONObject result=new JSONObject();
		result.accumulate("type", flag?"success":"fail");
		result.accumulate("value", content);
		try {
			resp.getWriter().write(result.toString());
		} catch (IOException e) {
			System.out.println("error---" + e);
		}
	}
}
