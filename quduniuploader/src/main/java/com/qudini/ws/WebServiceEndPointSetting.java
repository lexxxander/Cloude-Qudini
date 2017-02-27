package com.qudini.ws;

public class WebServiceEndPointSetting {
	protected String url;
	protected String sortedFile;
	protected String wsMethod;

	public String getWsMethod() {
		return wsMethod;
	}

	public void setWsMethod(String wsMethod) {
		this.wsMethod = wsMethod;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSortedFile() {
		return sortedFile;
	}

	public void setSortedFile(String sortedFile) {
		this.sortedFile = sortedFile;
	}

}
