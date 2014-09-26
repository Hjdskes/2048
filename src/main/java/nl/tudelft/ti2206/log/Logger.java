/**
 * 
 */
package nl.tudelft.ti2206.log;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
	/** A singleton reference to this class. */
	private static Logger instance = new Logger();

	private SimpleDateFormat timeFormat = new SimpleDateFormat(
			"YYYY-MM-dd HH:mm:ss");

	private String msgFormat = "%s %s: %s";

	PrintWriter file;

	/** Current logging level verbosity. */
	private Level level;

	public enum Level {
		NONE, INFO, ERROR, DEBUG, ALL
	}

	private Logger() {
		setLevel(Level.ALL);
	}
	
	public void setLogFile(String prefix) {
		
		SimpleDateFormat format = new SimpleDateFormat("YYYYMMdd_HHmmss");

		Date now = new Date();

		String filename = String.format("%s_%s.log", prefix, format.format(now));

		message(Level.DEBUG, "Logger", "Opening " + filename
				+ " for writing...");

		try {
			file = new PrintWriter(filename, "UTF-8");
			
			message(Level.DEBUG, "Logger", "Logfile " + filename
					+ " opened.");
			
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public static Logger getInstance() {
		return instance;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public Level getLevel() {
		return level;
	}

	public void setTimeFormat(String newFormat) {
		timeFormat = new SimpleDateFormat(newFormat);
	}

	public SimpleDateFormat getTimeFormat() {
		return timeFormat;
	}

	public String getMessageFormat() {
		return msgFormat;
	}

	public void setMessageFormat(String msgFormat) {
		this.msgFormat = msgFormat;
	}

	public void message(String message) {
		message(Level.INFO, message);
	}

	public void message(Level level, String message) {
		message(level, "", message);
	}

	public void message(Level level, String tag, String message) {

		if (getLevel() == Level.NONE)
			return;

		if (level.ordinal() <= getLevel().ordinal()) {
			String output = String.format(msgFormat, level, tag, message);
			print(output);
		}
	}

	private void print(String message) {
		Date now = new Date();
		String output = "[" + timeFormat.format(now) + "]: " + message;

		System.out.println(output);

		if (file != null) {
			file.println(output);
			file.flush();
		}
	}

	public void dispose() {

		if (file != null)
			file.close();
		
	}

}
