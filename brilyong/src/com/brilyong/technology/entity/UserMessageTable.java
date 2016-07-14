package com.brilyong.technology.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class UserMessageTable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int total;

	private ArrayList<Message> rows;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public ArrayList<Message> getRows() {
		return rows;
	}

	public void setRows(ArrayList<Message> rows) {
		this.rows = rows;
	}

}
