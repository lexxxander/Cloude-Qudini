package com.qudini.ws.response;


import javax.xml.bind.annotation.XmlRootElement;


public class WebServiceResponse {

	private String status;
	private String errMsg;
	
	
	public WebServiceResponse() {
		super();
		this.status = "ok";
		this.errMsg = "";
	}
	

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}


}
