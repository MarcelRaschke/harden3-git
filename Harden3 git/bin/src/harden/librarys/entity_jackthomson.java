package harden.librarys;

import harden.mainframe.game;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;

import javax.swing.ImageIcon;

public class entity_jackthomson extends Rectangle {
	
	public int xspeed;
	public int yspeed;
	public int speed;
	public boolean solid;
	public String name;
	public String imgsrc;
	public Image img;
	public Image imgg;
	public int desty=100;
	public int tim=0;
	public int thing = 0;
	Random r = new Random();
	public int hp=100;
	public boolean won=false;
	
	public entity_jackthomson(int a,int b,int gspeed,boolean existant,String tag, String path, String path2) {
		x=a;
		y=b;
		speed=gspeed;
		solid=existant;
		name=tag;
		img = new ImageIcon(path).getImage();
		imgsrc=path;
		width=100;
		height=100;
		imgg = new ImageIcon(path2).getImage();
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
	public void update(game g) {
		if(!won) {
		if(tim==0&&thing==1) {
			thing=2;
			tim=r.nextInt(50)+1;
			System.out.println("move");
			g.shoot(x,y,true);
		}
		if(tim==0&&thing==2) {
			g.shoot(x,y,true);
			thing=0;
			tim=0;
			if(g.player.y>400) {
				desty=250;
			} else {
				desty=g.player.y;
			}
			System.out.println("shoot");
		}
		
		if(y==desty) {
			if(tim==0) {
			thing=1;
			tim=r.nextInt(20)+1;
			}
		}
		if(r.nextInt(1000)==0){
			g.shoot(x,y,true);
			y=desty;
		}
		if(desty>y) {
			y+=r.nextInt(2)+1;
		} else if(!(y==desty)){
			y-=r.nextInt(2)+1;
		}
		if(!(thing==0)) {
			tim--;
		}
		System.out.println(tim+"");
		} else {
			img=imgg;
			y++;
		}
	}
	public void stopx() {
		xspeed=0;
	}
	public void stopy() {
		yspeed=0;
	}
	
}
