package harden.mainframe;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import harden.librarys.*;

import java.io.*;

import sun.audio.*;

public class game extends JPanel implements ActionListener, KeyListener {

	private final int BUFFER_SIZE = 128000;
	private File soundFile;
	public static String mainpath = pather.mainpath;
	public static Timer tim;
	public entity player = new entity(100, 200, 15, true, "player", mainpath
			+ "charactars/1.png");
	Image caterforest = new ImageIcon(mainpath + "Forest.png").getImage();
	Image creditforest = new ImageIcon(mainpath + "corest.png").getImage();
	Image Credits = new ImageIcon(mainpath + "Credits.png").getImage();
	entity[] frenemies = new entity[10];
	Timer fallupdater = new Timer(650, this);
	public int points = 0;
	public int interval = 650;
	public int pace = 9;
	public int goal = 10;
	Timer flash = new Timer(2000, this);
	Timer buggcatcher = new Timer(1, this);
	int checktimeinterval = 0;
	public String flashmessage = "";
	public boolean scare = false;
	public Image scareimg = new ImageIcon(mainpath + "photo.gif").getImage();
	public int ammo = 3;
	public entity[] shot = new entity[10];
	int onbullet = 0;
	boolean dead = false;
	boolean deadtwo = false;
	Timer death = new Timer(5000, this);
	public Image dead1 = new ImageIcon(mainpath + "Death1.png").getImage();
	public Image dead2 = new ImageIcon(mainpath + "Death2.png").getImage();
	public AudioStream audioStream = null;
	public AudioStream backgroundmusic = null;
	public boolean pause = false;
	public String state = "menu";
	Image appleimage;
	public Timer musicupdater = new Timer(1000 * 60 + 1000 * 40, this);
	int creditprogress = 0;
	boolean instantdeath = false;
	boolean deathbeep = true;
	boolean backgroundmusic1 = true;
	int difficulty = 2;
	boolean heartofthedeveloper = false;
	int hightscore = 0;
	int honey = 0;
	int daycycle = 50;
	int unlocked = 1;
	int avatar = 1;
	entity_queen queen;
	boolean spawn = true;
	entity_lazer[] lazer = new entity_lazer[10];
	boolean boss = true;
	Image imt = new ImageIcon(mainpath + "stone.png").getImage();
	String name = "Verstek";
	boolean play = false;
	entity_jackthomson jackthomson;
	int ux = 0;

	public void unlock(boolean t) {
		if (t) {

		}
	}

	public void loadSave() {
		File f = new File(mainpath + "save.sav");
		if (f.exists()) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(f));
				try {
					if (br.readLine().equals("true")) {
						instantdeath = true;
					} else {
						instantdeath = false;
					}
					if (br.readLine().equals("true")) {
						deathbeep = true;
					} else {
						deathbeep = false;
					}
					if (br.readLine().equals("true")) {
						backgroundmusic1 = true;
					} else {
						backgroundmusic1 = false;
					}
					if (br.readLine().equals("true")) {
						heartofthedeveloper = true;
					} else {
						heartofthedeveloper = false;
					}
					difficulty = (int) Double.parseDouble(br.readLine());
					hightscore = (int) Double.parseDouble(br.readLine());
					unlocked = (int) Double.parseDouble(br.readLine());
					if (br.readLine().equals("true")) {
						boss = true;
					} else {
						boss = false;
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void saveSave() {
		File f = new File(mainpath + "save.sav");
		if (f.exists()) {
			f.delete();
		}
		try {
			f.createNewFile();
			FileWriter fw = new FileWriter(f);
			fw.write(instantdeath + "\n" + deathbeep + "\n" + backgroundmusic1
					+ "\n" + heartofthedeveloper + "\n" + difficulty + "\n"
					+ hightscore + "\n" + unlocked + "\n" + boss);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public game() {
		Random r = new Random();
		int rn = r.nextInt(4) + 1;
		appleimage = new ImageIcon(mainpath + "characters/" + avatar + ".png")
				.getImage();
		tim = new Timer(25, this);
		this.setFocusable(true);
		this.requestFocusInWindow();
		this.addKeyListener(this);
		tim.start();
		queen = new entity_queen(50000, 50000, 0, true, "frienemie", mainpath
				+ "charactars/1.png", mainpath + "charactars/1.png");
		jackthomson = new entity_jackthomson(50000, 50000, 0, true, "frienemie", mainpath
				+ "charactars/1.png", mainpath + "charactars/1.png");
		for (int i = 0; i < 10; i++) {
			frenemies[i] = new entity(50000, 50000, 0, true, "frienemie",
					mainpath + "charactars/1.png");
			shot[i] = new entity(50000, 50000, 0, true, "frienemie", mainpath
					+ "Shot.png");
			fallupdater.start();
			lazer[i] = new entity_lazer(50000, 50000, 0, true, "frienemie",
					mainpath + "Shot.png");
		}
		musicupdater.start();
		backgroundmusic();
		loadSave();
	}

	public void startdeath() throws FileNotFoundException {
		if (points > hightscore) {
			hightscore = points;
			saveSave();
		}
		for (int i = 0; i < 10; i++) {
			shot[i] = new entity(50000, 50000, 0, true, "frienemie", mainpath
					+ "Shot.png");
		}
		AudioPlayer.player.stop(backgroundmusic);
		if (!instantdeath) {
			dead = true;
			death.setInitialDelay(5000);
			death.start();
			// open the sound file as a Java input stream
			if (deathbeep) {
				try {
					String gongFile = mainpath + "death.wav";
					InputStream in = new FileInputStream(gongFile);

					// create an audiostream from the inputstream
					audioStream = new AudioStream(in);
					// play the audio clip with the audioplayer class
					AudioPlayer.player.start(audioStream);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			if (points > hightscore) {
				hightscore = points;
				saveSave();
				JOptionPane.showMessageDialog(null,
						"Game over!\n Sorry man gameover your score was "
								+ points + "\n New hightscore!");
			} else {
				JOptionPane.showMessageDialog(null,
						"Game over!\n Sorry man gameover your score was "
								+ points + "\n Better luck next time! :)");
			}
			backgroundmusic();
			deadtwo = false;
			dead = false;
			reset();
		}
	}

	public void reset() {
		spawn = true;
		caterforest = new ImageIcon(mainpath + "Forest.png").getImage();
		player.x = 100;
		player.y = 10;
		player.stopx();
		player.stopy();
		for (int i = 0; i < 10; i++) {
			frenemies[i] = new entity(50000, 50000, 0, true, "frienemie",
					mainpath + "Caterpillar_right.png");
			fallupdater.start();
		}
		points = 0;
		interval = 650;
		pace = 10;
		goal = 10;
		updateinterval();
		backgroundmusic();
		daycycle = 50;
		ammo = 3;
		honey = 1;
		queen = new entity_queen(50000, 50000, 0, true, "frienemie", mainpath
				+ "Caterpillar_right.png", mainpath + "Caterpillar_right.png");
		jackthomson = new entity_jackthomson(50000, 50000, 0, true,
				"frienemie", mainpath + "Caterpillar_right.png", mainpath
						+ "Caterpillar_right.png");
	}

	public void updateinterval() {
		tim.setDelay(25);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == tim) {
			repaint();
			update();
		} else if (e.getSource() == fallupdater) {
			if (state.equalsIgnoreCase("play")) {
				Random r = new Random();
				int rn = r.nextInt(3) + 1;
				for (int i = 0; i < rn; i++) {
					respawn();
				}
			}
		} else if (e.getSource() == flash) {
			flashmessage = "";
		} else if (e.getSource() == buggcatcher) {
			checktimeinterval++;
			if (!(checktimeinterval == interval)) {
				fallupdater.stop();
				fallupdater.start();
				System.out.println("No respawn bugg");
			}
		} else if (e.getSource() == death) {
			if (state.equalsIgnoreCase("play")) {
				if (!deadtwo) {
					deadtwo = true;
					death.stop();
					death.setInitialDelay(2000);
					death.start();
				} else {
					death.stop();
					AudioPlayer.player.stop(audioStream);
					JOptionPane.showMessageDialog(null,
							"Game over!\n Sorry man gameover your score was "
									+ points + "\n Better luck next time! :)");
					deadtwo = false;
					dead = false;
					reset();
				}
			}
		} else if (e.getSource() == musicupdater) {
			backgroundmusic();
		}

	}

	public void backgroundmusic() {
		AudioPlayer.player.stop(backgroundmusic);
		if (backgroundmusic1) {
			if (heartofthedeveloper) {
				try {
					String gongFile = mainpath
							+ "heartofadeveloperslastgame.wav";
					InputStream in = new FileInputStream(gongFile);

					// create an audiostream from the inputstream
					backgroundmusic = new AudioStream(in);
					// play the audio clip with the audioplayer class
					AudioPlayer.player.start(backgroundmusic);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				if (state.equalsIgnoreCase("play")) {
					if (spawn) {
						try {
							String gongFile = mainpath + "Funny Sound.wav";
							InputStream in = new FileInputStream(gongFile);

							// create an audiostream from the inputstream
							backgroundmusic = new AudioStream(in);
							// play the audio clip with the audioplayer class
							AudioPlayer.player.start(backgroundmusic);
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else {
						try {
							String gongFile = mainpath + "Adventure Meme.wav";
							InputStream in = new FileInputStream(gongFile);

							// create an audiostream from the inputstream
							backgroundmusic = new AudioStream(in);
							// play the audio clip with the audioplayer class
							AudioPlayer.player.start(backgroundmusic);
						} catch (IOException e) {
							e.printStackTrace();
						}

					}
				} else if (state.equalsIgnoreCase("menu")) {
					try {
						String gongFile = mainpath + "Montuak Point.wav";
						InputStream in = new FileInputStream(gongFile);

						// create an audiostream from the inputstream
						backgroundmusic = new AudioStream(in);
						// play the audio clip with the audioplayer class
						AudioPlayer.player.start(backgroundmusic);
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else if (state.equalsIgnoreCase("credits")) {
					try {
						String gongFile = mainpath + "Adventure Meme.wav";
						InputStream in = new FileInputStream(gongFile);

						// create an audiostream from the inputstream
						backgroundmusic = new AudioStream(in);
						// play the audio clip with the audioplayer class
						AudioPlayer.player.start(backgroundmusic);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public void respawn() {
		if (spawn) {
			Random r = new Random();
			String src;
			if (r.nextInt(difficulty * 2) == 0) {
				src = mainpath + "apple.png";
			} else {
				src = mainpath + "stone.png";
			}
			for (int i = 0; i < 10; i++) {
				if (frenemies[i].x < 0 || frenemies[i].x > 1000) {
					boolean possible = false;
					while (!possible) {
						frenemies[i] = new entity(1000 + r.nextInt(400),
								r.nextInt(350), (pace / 2) * difficulty, true,
								src, src);
						for (int j = 0; j < frenemies.length; j++) {
							if (!(frenemies[i].intersects(frenemies[j]))
									&& !(j == i)) {
								possible = true;
							}
						}
					}
					frenemies[i].move(0);
					fallupdater.start();
					i = 10;
					if (interval < 10 || pace > 60) {
						interval += r.nextInt(35);
						pace = 15;
						System.out.println("Game to Tight");
					} else {
						interval = 700;
					}
					if (interval > 700) {
						interval -= r.nextInt(interval - 200) + 100;
						System.out.println("Game to loose");
					}
					fallupdater.setDelay(interval);
				}
			}
			fallupdater.stop();
			fallupdater.start();
		}
	}

	public void paint(Graphics g) {
		if (state.equalsIgnoreCase("play")) {
			if (!dead) {
				if (!scare) {
					Font title = new Font("Serif", 2, 57);
					g.drawImage(caterforest, 0, 0, this);
					g.drawImage(player.img, player.x, player.y, 100, 100, this);
					for (int i = 0; i < 10; i++) {
						g.drawImage(frenemies[i].img, frenemies[i].x,
								frenemies[i].y, this);
						g.drawImage(lazer[i].img, lazer[i].x, lazer[i].y, this);
						g.drawImage(shot[i].img, shot[i].x, shot[i].y, this);
					}
					g.drawString("Points:" + points + "/" + goal + " Ammo:"
							+ ammo + " Honey:" + honey, 10, 50);
					g.drawImage(queen.img, queen.x, queen.y, this);
					g.drawImage(jackthomson.img, jackthomson.x, jackthomson.y,
							this);
					g.setColor(Color.RED);
					// g.fillRect(queen.x, queen.y, (int) 200-(queen.hp*2),
					// 250);
					g.setColor(Color.WHITE);
					title = new Font("Serif", 2, 30);
					g.setFont(title);
					g.setColor(Color.BLUE);
					g.drawString(flashmessage, 100, 250);
				} else {
					g.drawImage(scareimg, 0, 0, 500, 500, this);
					tim.stop();
					scaresound();
				}
			} else {
				if (deadtwo) {
					g.drawImage(dead2, 0, 0, this);
				} else {
					g.drawImage(dead1, 0, 0, this);
				}
			}
		} else if (state.equalsIgnoreCase("menu")) {

			Font title = new Font("Serif", 2, 34);
			Font dFont = new Font("Serif", 1, 17);
			g.drawImage(caterforest, 0, 0, this);
			g.drawImage(player.img, 0, 370, 128, 128, this);
			g.setColor(Color.WHITE);
			g.setFont(title);
			g.drawString("Harden 3: The last Adventure", 88, 140);
			g.setFont(dFont);
			g.setColor(Color.MAGENTA);
			g.drawString("Space=Shoot", 0, 445);
			g.drawString("Up and down or W and S=Move", 0, 465);
			g.setColor(Color.white);
			g.drawString("a) Play", 88, 197);
			g.drawString("b) How to play", 88, 217);
			g.drawString("c) Avatar", 88, 237);
			g.drawString("d) Settings", 88, 277);
			g.drawString("e) Credits", 88, 257);
			g.drawString("f) Quit", 88, 297);
			g.drawString("Hightscore:" + hightscore, 88, 317);
		} else if (state.equalsIgnoreCase("credits")) {
			Font title = new Font("Serif", 2, 34);
			Font dFont = new Font("Serif", 1, 14);
			Font Font = new Font("Serif", 1, 11);
			g.setFont(Font);
			g.drawImage(Credits, 0, 0, this);
			g.drawImage(creditforest, 0, creditprogress, this);
			if(creditprogress==8000) {
			creditprogress++;
			JOptionPane.showMessageDialog(null,"'Heart Rate Monitor Flatline Sound' Mike Koenig (soundbible.com) Licensed under Creative Commons: By Attribution 3.0 http://creativecommons.org/licenses/by/3.0/");
			JOptionPane.showMessageDialog(null,"'Despair and Triumph' Kevin MacLeod (incompetech.com) Licensed under Creative Commons: By Attribution 3.0 http://creativecommons.org/licenses/by/3.0/");
			JOptionPane.showMessageDialog(null,"'Montauk Point' Kevin MacLeod (incompetech.com) Licensed under Creative Commons: By Attribution 3.0 http://creativecommons.org/licenses/by/3.0/");
			JOptionPane.showMessageDialog(null,"'Adventure Meme' Kevin MacLeod (incompetech.com) Licensed under Creative Commons: By Attribution 3.0 http://creativecommons.org/licenses/by/3.0/");
			JOptionPane.showMessageDialog(null,"'Funny song' Bensound.com");
			}
			g.setFont(dFont);
			g.drawString("Wilhelm Eklund", 100, creditprogress - 360);
			g.drawString("Pr,Sales,Ceo,Product Responsible", 100,
					creditprogress - 340);
			g.drawString("Paul Kumlin", 100, creditprogress - 300);
			g.drawString("Pr,Sales", 100, creditprogress - 280);
			g.drawString("Karl Glassel", 100, creditprogress - 240);
			g.drawString("Pr Art", 100, creditprogress - 220);
			g.drawString("Edvardo Lidvall", 100, creditprogress - 140);
			g.drawString("Hugo Karrel", 100, creditprogress - 120);
			g.drawString("Matilda Rundgren", 100, creditprogress - 80);
			g.drawString("Play Testors:", 100, creditprogress - 40);
			g.drawString("Wilhelm Eklund:Director,Designer,Sprite Artist, Programmer", 100, creditprogress);
			g.drawString("Karl Glassel:Sprite Artist", 100, creditprogress + 20);
			g.drawString("Masterfireheart:Sprite Artist/Music Composition", 100, creditprogress + 40);
			g.setFont(title);
			g.drawString("Game Team & Testers:", 100, creditprogress + 80);
			g.drawString("Mingames Staff:", 100, creditprogress - 190);
			creditprogress+=1;
			if (creditprogress == 1100) {
				state = "menu";
				creditprogress = 0;
			}
		} else if (state.equalsIgnoreCase("settings")) {
			Font title = new Font("Serif", 2, 57);
			Font dFont = new Font("Serif", 1, 17);
			g.drawImage(caterforest, 0, 0, this);
			g.setFont(title);
			g.drawString("Settings", 100, 50);
			g.setFont(dFont);
			g.drawString("Instantdeath: " + instantdeath + " Toggle A", 100,
					150);
			g.drawString("Deathbeep: " + deathbeep + " Toggle B", 100, 170);
			g.drawString("BackgroundMusic: " + backgroundmusic1 + " Toggle C",
					100, 190);
			g.drawString("Heart: " + heartofthedeveloper + " Toggle D", 100,
					210);
			g.drawString("Boss: " + boss + " Toggle E", 100, 230);
			g.drawString("Difficulty: " + difficulty + " Toggle 1,2,3", 100,
					250);
			g.drawString("Press escape to enter main menu", 0, 470);
		} else if (state.equalsIgnoreCase("avatar")) {
			Font title = new Font("Serif", 2, 57);
			Font dFont = new Font("Serif", 1, 17);
			g.drawImage(caterforest, 0, 0, this);
			g.setFont(title);
			g.drawString("Avatar " + avatar, 100, 50);
			g.setFont(dFont);
			g.drawImage(player.img, 150, 150, 100, 100, this);
			g.drawString(name, 200, 300);
			g.drawString("Left Right Button to select avatar.", 0, 450);
			g.drawString("Press escape to enter main menu", 0, 470);
		} else if (state.equalsIgnoreCase("htp")) {
			Font title = new Font("Serif", 2, 57);
			Font dFont = new Font("Impact", 1, 15);
			g.drawImage(caterforest, 0, 0, this);
			g.setFont(title);
			g.drawString("How to play", 100, 50);
			g.setFont(dFont);
			g.drawString(
					"Gameplay avoid the wasps and collect the flower done. Once you",
					20, 100);
			g.drawString("have collected some flowers you meet Queen Wasp!",
					20, 120);
			g.drawString(
					"Boss the boss is Queen Wasp she will shoot lazers and bees!",
					20, 140);
			g.drawString("He has 100 lives so you will have to be careful!",
					20, 160);
			g.drawString(
					"Shooting is the only weapon your character will have", 20,
					180);
			g.drawString("she shoots by you pressing the space bar.", 20, 200);
			g.drawString(
					"Your character will recive honey when reaching goals. When he gets",
					20, 220);
			g.drawString("hit he will survive if he has more than zero honey.",
					20, 240);
			g.drawString("Press escape to enter main menu", 20, 320);
			g.drawString("The controls for the game are:", 20, 260);
			g.drawString(
					"go up arrow up/w; go down arrow down/s; shoot space; pause escape",
					20, 280);
		}
	}

	public void update() {
		if (state.equalsIgnoreCase("play")) {
			if (!dead) {
				jackthomson.update(this);
				queen.update(this);
				if (points == 1000) {
					if (boss) {
						JOptionPane
								.showMessageDialog(
										null,
										"Conglatuation !!! \nYou have completed a great game! \n Just kidding, you have reached the 1000 points mark \n Thats an achivement to brag about anyway \n here is a little fun boss for you! \n Good luck! You will need it!");
						JOptionPane
						.showMessageDialog(
								null,
								"???: Muhahahaha we meet att last!");
						JOptionPane
						.showMessageDialog(
								null,
								name + ": Who is there?");
						JOptionPane
						.showMessageDialog(
								null,
								"???: It is I the master of all evil including the wasp queen Fack Thompson!");
						JOptionPane
						.showMessageDialog(
								null,
								name + ": You monster you tried to kill me!!!");
						JOptionPane
						.showMessageDialog(
								null,
								"Fack Thompson: Finally realizing the truth huh.");
						JOptionPane
						.showMessageDialog(
								null,
								"Fack Thompson: You are the only thing standing in my way of taking away the fun of the world!");
						JOptionPane
						.showMessageDialog(
								null,
								"Fack Thompson: My minions are worthless now meet the real power of boaringness!");
						JOptionPane
						.showMessageDialog(
								null,
								name + ": You will never defeat me!");
					} else {
						JOptionPane
								.showMessageDialog(
										null,
										"Conglatuation !!! \nYou have completed a great game! \n Just kidding, you have reached the 1000 points mark \n Thats an achivement to brag about. \n Well keep going! Good luck!");
					}
				}
				if (boss&&points==500) {
					points++;
					JOptionPane
					.showMessageDialog(
							null,
							"???: Muhahahaha i finally got the radio thing working!");
					JOptionPane
					.showMessageDialog(
							null,
							name + ": Who is there?");
					JOptionPane
					.showMessageDialog(
							null,
							"???: It is I the master of all evil including the wasp queen Fack Thompson!");
					JOptionPane
					.showMessageDialog(
							null,
							name + ": You monster you tried to kill me!!!");
					JOptionPane
					.showMessageDialog(
							null,
							"Fack Thompson: Finally realizing the truth huh.");
					JOptionPane
					.showMessageDialog(
							null,
							"Fack Thompson: You are the only thing standing in my way of taking away the fun of the world!");
					JOptionPane
					.showMessageDialog(
							null,
							"Fack Thompson: Wasp queen attack and win!");
					JOptionPane
					.showMessageDialog(
							null,
							name + ": You will never defeat me!");
				}
				if (boss) {
					if (points % 100 == 0 && !(points == 0)) {
						spawn = false;
						if (points % 1000 == 0) {
							ammo += 100;
							honey++;
							points++;
							jackthomson = new entity_jackthomson(350, 0, 1, true,
									"frienemie", mainpath + "fackthomson.png",
									mainpath + "fackthomson_win.png");
							backgroundmusic();
						} else {
							ammo += 100;
							honey++;
							points++;
							queen = new entity_queen(300, 0, 1, true,
									"frienemie", mainpath + "queen_wasp.png",
									mainpath + "queen_wasp_won.png");
							backgroundmusic();
						}
					}
				}
				for (int i = 0; i < 10; i++) {
					for (int j = 0; j < 10; j++) {
						if (shot[i].intersects(frenemies[j])) {
							if (spawn) {
								frenemies[j].x = -500;
								shot[i].x = 10000;
							}
						}
						if (shot[i].intersects(queen)) {
							queen.hp -= 1;
							shot[i].y = -500;
							if (queen.hp == 0) {
								queen.won = true;
								spawn = true;
								backgroundmusic();
							}
						}
						if (shot[i].intersects(jackthomson)) {
							jackthomson.hp -= 1;
							shot[i].y = -500;
							if (jackthomson.hp == 0) {
								jackthomson.won = true;
								spawn = true;
								backgroundmusic();
							}
						}
					}
				}
				if (player.y + 100 > 500 || player.y < 0) {
					player.stopy();
					if (player.y + 100 > 500) {
						player.y -= 20;
					} else {
						player.y += 20;
					}
				}
				if (points >= goal) {
					if (points >= daycycle) {
						int tmp = daycycle / 10;
						if ((tmp % 2) == 0) {
							caterforest = new ImageIcon(mainpath + "Forest.png")
									.getImage();
						} else {
							caterforest = new ImageIcon(mainpath
									+ "Night-Forest.png").getImage();
						}
						daycycle += 50;
					}
					repaint();
					honey++;
					Random r = new Random();
					int rn = r.nextInt(6);
					// int rn1 = r.nextInt(2000);
					flash.stop();
					if (rn == 0) {
						flashmessage = "Awsome! You got honey!";
					} else if (rn == 1) {
						flashmessage = "Amazing! You got honey!";
					} else if (rn == 2) {
						flashmessage = "Outstanding! You got honey!";
					} else if (rn == 3) {
						flashmessage = "Goodjob! You got honey!";
					} else if (rn == 4) {
						flashmessage = "Keep it going! You got honey!";
					} else if (rn == 5) {
						flashmessage = "Faaaantastic! You got honey!";
					}
					if (1 == 0) {
						flashmessage = "It's you... IT'S YOU!";
						player.move(2);
						scare = true;
					}
					flash.start();
					interval += 500;
					pace = 10;
					goal += 15;
				}
				player.update();
				for (int i = 0; i < 10; i++) {
					shot[i].update();
					lazer[i].update(player);
				}
				for (int i = 0; i < 10; i++) {
					frenemies[i].update();
					if (player.intersects(frenemies[i])) {
						if (frenemies[i].imgsrc.equalsIgnoreCase(mainpath
								+ "apple.png")) {
							points++;
							frenemies[i].y = 1000;
							pace += 1;
							Random r = new Random();
							ammo += 2;
							if (points == 10) {
								if (unlocked == 1) {
									unlocked = 2;
									unlock(true);
								}
								System.out.println("unlocked!");
							}
							if (points == 25) {
								if (unlocked == 2) {
									unlocked = 3;
									unlock(true);
								}

							}
							if (points == 40) {
								if (unlocked == 3) {
									unlocked = 4;
									unlock(true);
								}

							}
							if (points == 55) {
								if (unlocked == 4) {
									unlocked = 5;
									unlock(true);
								}

							}
							if (points == 70) {
								if (unlocked == 5) {
									unlocked = 6;
									unlock(true);
								}

							}
							if (points == 85) {
								if (unlocked == 6) {
									unlocked = 7;
									unlock(true);
								}

							}
							if (points == 100) {
								if (unlocked == 7) {
									unlocked = 8;
									unlock(true);
								}
							}
							if (points == 115) {
								if (unlocked == 8) {
									unlocked = 9;
									unlock(true);
								}
							}
							if (points == 130) {
								if (unlocked == 9) {
									unlocked = 10;
									unlock(true);
								}
							}
							if (points == 145) {
								if (unlocked == 10) {
									unlocked = 11;
									unlock(true);
								}
							}
							if (points == 160) {
								if (unlocked == 11) {
									unlocked = 12;
									unlock(true);
								}
							}
							if (points == 175) {
								if (unlocked == 12) {
									unlocked = 13;
									unlock(true);
								}
							}
							if (points == 190) {
								if (unlocked == 13) {
									unlocked = 14;
									unlock(true);
								}
							}
							unlock(true);
						} else {
							if (honey == 0) {
								dead = true;
								repaint();
								try {
									startdeath();
								} catch (FileNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} else {
								honey--;
								flashmessage = "You ate some honey and felt better!";
								flash.stop();
								flash.start();
								frenemies[i].x = -500;
							}
						}

					}
				}
			}
			for (int i = 0; i < 10; i++) {
				if (player.intersects(lazer[i])) {
					if (honey == 0) {
						dead = true;
						repaint();
						try {
							startdeath();
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						honey--;
						flashmessage = "You ate some honey and felt better!";
						flash.stop();
						flash.start();
						lazer[i].x = -10000;
					}
				}
			}
		}
	}

	public void shoot(int x, int y, boolean l) {
		Random r = new Random();
		if (!(r.nextInt(4) == 0) &&l == false) {
			String src = mainpath + "stone.png";
			for (int i = 0; i < 3; i++) {
				frenemies[i] = new entity(x, y + (80 * i), (pace) * difficulty,
						true, src, src);
				frenemies[i].move(0);
			}
		} else {
			String src = mainpath + "lazer.png";
			lazer[r.nextInt(4) + r.nextInt(3) + r.nextInt(3) + r.nextInt(3)] = new entity_lazer(
					x, y, (pace) * difficulty, true, src, src);
			lazer[r.nextInt(4) + r.nextInt(3) + r.nextInt(3) + r.nextInt(3)] = new entity_lazer(
					x, y+50, (pace) * difficulty, true, src, src);
		}
	}

	public void scaresound() {
		try {
			File yourFile = new File(mainpath + "XSCREAM." + "wav");
			AudioInputStream stream;
			AudioFormat format;
			DataLine.Info info;
			Clip clip;

			stream = AudioSystem.getAudioInputStream(yourFile);
			format = stream.getFormat();
			info = new DataLine.Info(Clip.class, format);
			clip = (Clip) AudioSystem.getLine(info);
			clip.open(stream);
			clip.start();
		} catch (Exception e) {
			// whatevers
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (state.equalsIgnoreCase("play")) {
			if (e.getKeyCode() == e.VK_UP || e.getKeyCode() == e.VK_W) {
				player.move(2);
			} else if (e.getKeyCode() == e.VK_DOWN || e.getKeyCode() == e.VK_S) {
				player.move(3);
			} else if (e.getKeyCode() == e.VK_ESCAPE) {
				state = "menu";
				saveSave();
				backgroundmusic();
			} else if (e.getKeyCode() == e.VK_SPACE) {
				if (ammo>0) {
					if (onbullet == 10) {
						onbullet = 0;
					}
					shot[onbullet] = new entity(player.x, player.y+50, 10,
							true, "bullet", mainpath + "Shot.png");
					shot[onbullet].xspeed = 10;
					onbullet++;
					ammo--;
				}
			}
		} else if (state.equalsIgnoreCase("menu")) {

			if (e.getKeyCode() == e.VK_A) {
				/*if (!(hightscore == 0) && !play == true) {
					state = "play";
					backgroundmusic();
				} else {
					state = "fhtp";
				}*/
				state = "play";
				backgroundmusic();
			} else if (e.getKeyCode() == e.VK_B) {
				state = "htp";
				backgroundmusic();
			} else if (e.getKeyCode() == e.VK_C) {
				state = "avatar";
			} else if (e.getKeyCode() == e.VK_D) {
				state = "settings";
			} else if (e.getKeyCode() == e.VK_E) {
				state = "credits";
			} else if (e.getKeyCode() == e.VK_F) {
				saveSave();
				System.exit(0);
			} else if (e.getKeyCode() == e.VK_F1) {
				reset();
			}
		} else if (state.equalsIgnoreCase("credits")) {
			if (e.getKeyCode() == e.VK_ESCAPE) {
				state = "menu";
				backgroundmusic();
			}
		} else if (state.equalsIgnoreCase("settings")) {
			if (e.getKeyCode() == e.VK_ESCAPE) {
				state = "menu";
				backgroundmusic();
			} else if (e.getKeyCode() == e.VK_A) {
				if (instantdeath) {
					instantdeath = false;
				} else {
					instantdeath = true;
				}
			} else if (e.getKeyCode() == e.VK_B) {
				if (deathbeep) {
					deathbeep = false;
				} else {
					deathbeep = true;
				}
			} else if (e.getKeyCode() == e.VK_C) {
				if (backgroundmusic1) {
					backgroundmusic1 = false;
				} else {
					backgroundmusic1 = true;
				}
			} else if (e.getKeyCode() == e.VK_D) {
				if (heartofthedeveloper) {
					heartofthedeveloper = false;
				} else {
					heartofthedeveloper = true;
				}
			} else if (e.getKeyCode() == e.VK_E) {
				if (boss) {
					boss = false;
				} else {
					boss = true;
				}
			} else if (e.getKeyCode() == e.VK_1) {
				difficulty = 1;
			} else if (e.getKeyCode() == e.VK_2) {
				difficulty = 2;
			} else if (e.getKeyCode() == e.VK_3) {
				difficulty = 3;
			}
		} else if (state.equalsIgnoreCase("avatar")) {
			if (e.getKeyCode() == e.VK_LEFT) {
				if (!(avatar == 1)) {
					avatar--;
					player = new entity(100, 200, 15, true, "player", mainpath
							+ "charactars/" + avatar + ".png");
					repaint();
				}
			} else if (e.getKeyCode() == e.VK_RIGHT) {
				if (!(unlocked == avatar)) {
					avatar++;
					player = new entity(100, 200, 15, true, "player", mainpath
							+ "charactars/" + avatar + ".png");
					repaint();
				}
			}
			if (avatar == 1) {
				name = "Verstek";
			} else if (avatar == 2) {
				name = "Learning Math";
			} else if (avatar == 3) {
				name = "Bowtie Guy";
			} else if (avatar == 4) {
				name = "Harige";
			} else if (avatar == 5) {
				name = "Simpson";
			} else if (avatar == 6) {
				name = "Risf";
			} else if (avatar == 7) {
				name = "Gabriel";
			} else if (avatar == 8) {
				name = "Lucifer";
			} else if (avatar == 9) {
				name = "Bro Fist Cake";
			} else if (avatar == 10) {
				name = "Zebramikon";
			} else if (avatar == 11) {
				name = "Minfavo";
			} else if (avatar == 12) {
				name = "Riddare";
			} else if (avatar == 13) {
				name = "Ajunta Pall";
			} else if (avatar == 14) {
				name = "Lukas Lugloop";
			}
			System.out.println(avatar + " " + unlocked);
			saveSave();
		} else if (state.equalsIgnoreCase("fhtp")) {
			if (e.getKeyCode() == e.VK_A) {
				state = "play";
			}
		}
		if (e.getKeyCode() == e.VK_ESCAPE
				&& !state.equalsIgnoreCase("endcredits")) {
			state = "menu";
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

		if (e.getKeyCode() == e.VK_UP || e.getKeyCode() == e.VK_DOWN) {
			player.stopy();
		} else if (e.getKeyCode() == e.VK_F4) {
			System.out.println(interval + " is the respawn time");
		}
		if (e.getKeyCode() == e.VK_W || e.getKeyCode() == e.VK_S) {
			player.stopy();
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

}