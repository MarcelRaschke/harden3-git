package harden.librarys;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class entity_lazer extends Rectangle{
	public int xspeed;
	public int yspeed;
	public int speed;
	public boolean solid;
	public String name;
	public String imgsrc;
	public Image img;
	
	public entity_lazer(int a,int b,int gspeed,boolean existant,String tag, String path) {
		x=a;
		y=b;
		speed=gspeed;
		solid=existant;
		name=tag;
		img = new ImageIcon(path).getImage();
		imgsrc=path;
			width=25;
			height=10;
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
	public void update(entity p) {
		if(p.y>y) {
			y+=2;
		} else {
			y-=2;
		}
		x-=5;
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
