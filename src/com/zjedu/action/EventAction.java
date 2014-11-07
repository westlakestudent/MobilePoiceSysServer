package com.zjedu.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zjedu.Params;
import com.zjedu.dao.EventDao;
import com.zjedu.po.Event;
import com.zjedu.po.EventReceive;

public class EventAction extends BaseAction{

	private EventDao dao = new EventDao();
	private static final int BUFFER_SIZE = 1024 ;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void getallevents(){
		String status = requestParam(Params.STATUS);
		String username = requestParam(Params.USERNAME);
		if(status == null || status.equals("") || username == null || username.equals("")){
			sendMsg(false, "参数不能为空");
			return;
		}
		try{
			List<Event> events = dao.findEvents(status,username);
			Gson gson = new Gson();
			Type type = new TypeToken<List<Event>>() {
			}.getType();
			if(events.size() > 0){
				sendMsg(true, gson.toJson(events, type));
				System.out.println("events------>" + events.get(0).getContent() + ";" + events.get(0).getLocation() + "count:" + events.size());
			}
			else 
				sendMsg(false, "您已获得最新事件信息");
		}catch(Exception e){
			sendMsg(false, e.getMessage());
			System.out.println("exception---" + e);
		}
		
	}
	
	public void updateReceive(){
		String username = requestParam(Params.USERNAME);
		String eventids = requestParam(Params.EVENTIDS);
		
		if(eventids == null || eventids.equals("") || username == null || username.equals("")){
			sendMsg(false, "参数不能为空");
			return;
		}
		String []ids = eventids.split(",");
		try{
			for(String id : ids){
				System.out.println("id :" + id );
				EventReceive receive = new EventReceive();
				receive.setUsername(username);
				receive.setEventid(Integer.valueOf(id));
				
				if(dao.saveReceive(receive)){
					sendMsg(true, "update ok");
				}else{
					sendMsg(false, "update fail");
				}
			}
		}catch(Exception e){
			sendMsg(false, e.getMessage());
		}
	}

	public void srelease() throws ServletException, IOException{
		if(event != null){
			System.out.println("content:" + event.getContent() + "status:" + event.getStatus() + "location:" + event.getLocation() + "time:" + event.getTime());
			if(dao.insertEvent(event)){
				RequestDispatcher dispatcher = httpRequest().getRequestDispatcher("/success.jsp");
				dispatcher.forward(httpRequest(), httpResponse());
			}else{
				RequestDispatcher dispatcher = httpRequest().getRequestDispatcher("/error.jsp");
				dispatcher.forward(httpRequest(), httpResponse());
			}
		}
		
	}
	
	public void release(){
		String username = requestParam(Params.USERNAME);
		if(username == null || username.equals("")){
			sendMsg(false, "上传参数不符合要求");
			return;
		}
		String status = requestParam(Params.STATUS);
		String title = requestParam(Params.TITLE);
		String content = requestParam(Params.CONTENT);
		String time = requestParam(Params.TIME);
		String location  = requestParam(Params.ADDRESS);
		String path = null;
		FileOutputStream fos = null;
	    FileInputStream fis = null;
		try {
			System.out.println("文件存放目录: "+getSavePath());
            System.out.println("文件名称: "+fileFileName);
            System.out.println("文件大小: "+getFile().length());
            System.out.println("文件类型: "+fileContentType);
			File imageFile = new File(ServletActionContext.getServletContext().getRealPath("/upload"));
	        if(!imageFile.exists())
	       	   imageFile.mkdirs();
	        path = getSavePath() + "/" + getFileFileName();
	        fos = new FileOutputStream(path);
            fis = new FileInputStream(getFile());
            byte[] buffer = new byte[BUFFER_SIZE];
            int len = 0;
            while ((len = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
            String photourl = httpRequest().getScheme()+"://" +httpRequest().getServerName()+":" + httpRequest().getServerPort() + httpRequest().getContextPath() + "/upload" +  "/" + getFileFileName();
            Event event = new Event();
            event.setContent(content);
            event.setLocation(location);
            event.setPath(photourl);
            event.setTime(time);
            event.setTitle(title);
            event.setStatus(status);
            if(dao.insertEvent(event)){
            	sendMsg(true, "发布成功");
            }else{
            	sendMsg(false, "发布失败");
            }
            
            System.out.println(httpRequest().getScheme()+"://" +httpRequest().getServerName()+":" + httpRequest().getServerPort() + httpRequest().getContextPath());
		}  catch (Exception e) {
			e.printStackTrace();
			sendMsg(false, "发布失败");
		}  finally{
			close(fos, fis);
		}
		
	}
	
	private Event event = null;
	private File file = null;
    private String fileContentType = null;
    private String fileFileName = null;
    private String savePath = null;

    

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}


	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String getSavePath() throws Exception{
		return   ServletActionContext.getServletContext().getRealPath(savePath); 
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}
	
	
	private void close(FileOutputStream fos, FileInputStream fis) {
        if (fis != null) {
            try {
                fis.close();
                fis=null;
            } catch (IOException e) {
                System.out.println("FileInputStream关闭失败");
                e.printStackTrace();
            }
        }
        if (fos != null) {
            try {
                fos.close();
                fis=null;
            } catch (IOException e) {
                System.out.println("FileOutputStream关闭失败");
                e.printStackTrace();
            }
        }
    }
	
}
