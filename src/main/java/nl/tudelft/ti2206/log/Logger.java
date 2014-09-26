/**
 * 
 */
package nl.tudelft.ti2206.log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
	/** A singleton reference to this class. */
	private static Logger instance = new Logger();
	
	private SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
	
	/** Current logging level verbosity. */
	private Level level;
	
	public enum Level {
		NONE, INFO, ERROR, DEBUG, ALL
	}
	
	private Logger() {
		setLevel(Level.INFO);
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
	
	public void setFormat(String newFormat) {
		format = new SimpleDateFormat(newFormat);
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

		
		Date now = new Date();
		String output = "[" + format.format(now) + "]: " + level + " " + tag + ": " + message;
		
		System.out.println(output);
	}
	
}
