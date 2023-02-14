package com.be.ticketing.response;

public class DownloadResponse {
	private byte[] base64Data;
	private String fileName;
	private String filePathName;
	
	public DownloadResponse(byte[] base64Data, String fileName, String filePathName) {
		super();
		this.base64Data = base64Data;
		this.fileName = fileName;
		this.filePathName = filePathName;
	}
	
	public DownloadResponse() {
	}

	public byte[] getBase64Data() {
		return base64Data;
	}

	public void setBase64Data(byte[] base64Data) {
		this.base64Data = base64Data;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePathName() {
		return filePathName;
	}

	public void setFilePathName(String filePathName) {
		this.filePathName = filePathName;
	}
	
	
}
