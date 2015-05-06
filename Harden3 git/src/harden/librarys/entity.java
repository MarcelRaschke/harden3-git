package harden.librarys;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class entity extends Rectangle{
	
	public int xspeed;
	public int yspeed;
	public int speed;
	public boolean solid;
	public String name;
	public String imgsrc;
	public Image img;
	
	public entity(int a,int b,int gspeed,boolean existant,String tag, String path) {
		x=a;
		y=b;
		speed=gspeed;
		solid=existant;
		name=tag;
		img = new ImageIcon(path).getImage();
		imgsrc=path;
		if(tag.equalsIgnoreCase(pather.mainpath+"stone.png")) {
			width=13;
			height=40;
		} else {
			width=100;
			height=100;
		}
	}
	
	//Movement
	public void move(int dir) {
		
		switch(dir) {
			case 0:
				xspeed=-speed;
				break;
			case 1:
				xspeed=speed;
				break;
			case 2:
				yspeed=-speed;
				break;
			case 3:
				yspeed=speed;
				break;
		}
		
	}
	public void update() {
		x += xspeed;
		y += yspeed;
	}
	public void stopx() {
		xspeed=0;
	}
	public void stopy() {
		yspeed=0;
	}
	
	public void changeImg(String path) {
		img = new ImageIcon(path).getImage();
	}
	
}
