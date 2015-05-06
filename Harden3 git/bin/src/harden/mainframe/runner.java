package harden.mainframe;

import harden.librarys.pather;

import javax.swing.JFrame;

public class runner {
	
	public static JFrame frame = new JFrame();
	public static game Game;
	
	public static void main(String[] args) {
		
		new runner();
		
	}
	
	public runner() {
		
		System.out.println("Info: Setting up mainpath.");
		pather.createMainPath("harden3");
		System.out.println("Info: Set up mainpath.");
		System.out.println("Info: Setting up frame.");
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setTitle("Harden 3 v1.0");
		System.out.println("Info: Attempting to load game.");
		Game = new game();
		frame.add(Game);
		System.out.println("Info: Makeing frame visable.");
		frame.setVisible(true);
		System.out.println("Info: Adding listener.");
		System.out.println("Info: Set up frame.");
		
	}
	
	public void save(){
		
	}
	
}
