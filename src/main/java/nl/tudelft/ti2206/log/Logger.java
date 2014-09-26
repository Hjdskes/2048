
package nl.tudelft.ti2206.log;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
	/** A singleton reference to this class. */
	private static Logger instance = new Logger();
	
	/** The format for timestamps in logging output. */
	private SimpleDateFormat timeFormat = new SimpleDateFormat(
			"YYYY-MM-dd HH:mm:ss");

	/** The logging message format. */
	private String msgFormat = "%s %s: %s";

	PrintWriter file;

	/** Current logging level verbosity. */
	private LogLevel level;

	public enum LogLevel {
		NONE, INFO, ERROR, DEBUG, ALL
	}
	
	/** Private constructor for singleton. */
	private Logger() {
		setLevel(LogLevel.ALL);
	}
	
	/**
	 * Set log filename prefix, such as application name. Timestamp and 
	 * file extension will be appended automatically.
	 * @param prefix the filename prefix
	 */
	public void setLogFile(String prefix) {
		
		SimpleDateFormat format = new SimpleDateFormat("YYYYMMdd_HHmmss");
		Date now = new Date();

		String filename = String.format("%s_%s.log", prefix, format.format(now));

		message(LogLevel.DEBUG, "Logger", 
				"Opening " + filename + " for writing...");

		try {
			file = new PrintWriter(filename, "UTF-8");
			
			message(LogLevel.DEBUG, "Logger", 
					"Logfile " + filename + " opened.");
			
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public static Logger getInstance() {
		return instance;
	}

	public void setLevel(LogLevel level) {
		this.level = level;
	}

	public LogLevel getLevel() {
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
	
	public void info(String tag, String message) {
		message(LogLevel.INFO, tag, message);
	}
	
	public void error(String tag, String message) {
		message(LogLevel.ERROR, tag, message);
	}
	
	public void debug(String tag, String message) {
		message(LogLevel.DEBUG, tag, message);
	}
	
	/**
	 * Process log message.
	 * @param level log level verbosity
	 * @param tag tag for the message
	 * @param message natural language string to log
	 */
	private void message(LogLevel level, String tag, String message) {

		if (getLevel() == LogLevel.NONE)
			return;

		if (level.ordinal() <= getLevel().ordinal()) {
			String output = String.format(msgFormat, level, tag, message);
			print(output);
		}
	}
	
	
	/**
	 * Write message to standard output and to file (if enabled).
	 * @param message
	 */
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