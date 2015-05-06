package harden.librarys;

public class state {
	
	public static int state;
	public final static int MENY=0;
	public final static int PLAY=1;
	public final static int DONE=2;
	public final static int PAUSE=3;
	public static int partstate;
	public final static int NONE=-1;
	public final static int PLAY_WORM=0;
	public final static int PLAY_PUPET=1;
	public final static int PLAY_BUTTERFLY=2;
	public final static int DONE_WORM=3;
	public final static int DONE_PUPET=4;
	public final static int DONE_BUTTERFLY=5;
	
	public state(int s,int ps) {
		state=s;
		partstate=ps;
	}
	
}
