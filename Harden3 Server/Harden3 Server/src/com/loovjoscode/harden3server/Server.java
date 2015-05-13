package com.loovjoscode.harden3server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.URL;
import java.util.logging.Level;

public class Server {
	public static Logger log = new Logger("Server");

	public static void main(String[] args) {
		String ip = getIp();
		log.log("Your ip is " + ip);
		
		ServerSocket ss = new ServerSocket();
	}

	private static String getIp() {
		try {
			URL wtfIsMyIp = new URL("http://whatismijnip.nl/");
			BufferedReader bis = new BufferedReader(new InputStreamReader(
					wtfIsMyIp.openStream()));
			String s = bis.readLine();
			bis.close();
			return s.substring(19);
		} catch (Exception e) {
			e.printStackTrace();
			log.log(Level.WARNING,
					"Problem getting URL info. pl0x restart the server.");
		}

		return null;
	}
}
