package com.brilyong.technology.entity;

public class Recorder {
	public float time;
	private String filePath;
	private boolean isClient;

	public Recorder(float time, String filePath) {
		super();
		this.time = time;
		this.filePath = filePath;
	}

	public float getTime() {
		return time;
	}

	public void setTime(float time) {
		this.time = time;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
