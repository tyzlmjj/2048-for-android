package com.mjj.model;

public class Fangkuai_Y {
	private int id;
	
	private long x;
	
	private long y;
	
	private int shuzi;
	
	public Fangkuai_Y(int id,int x,int y,int shuzi){
		this.id=id;
		this.x=x;
		this.y=y;
		this.setShuzi(shuzi);
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getX() {
		return x;
	}

	public void setX(long x) {
		this.x = x;
	}

	public long getY() {
		return y;
	}

	public void setY(long y) {
		this.y = y;
	}
	public int getShuzi() {
		return shuzi;
	}
	public void setShuzi(int shuzi) {
		this.shuzi = shuzi;
	}
	
	
}
