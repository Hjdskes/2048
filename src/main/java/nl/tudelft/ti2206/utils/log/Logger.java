
package nl.tudelft.ti2206.utils.log;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.badlogic.gdx.utils.Disposable;

/**
 * The Logger is used to log all events to the console.
 * Several LogLevels can be set and logging to a file is also 
 * possible.
 */
public class Logger implements Disposable {
	/** A singleton reference to this class. */
	private static Logger instance = new Logger();
	
	/** The format for timestamps in logging output. */
	private SimpleDateFormat timeFormat = new SimpleDateFormat(
			"YYYY-MM-dd HH:mm:ss");

	/** The logging message format. */
	private String msgFormat = "%s %s: %s";

	/** Prints formatted representations of objects to a text-output stream. */
	private PrintWriter file;

	/** Current logging level verbosity. */
	private LogLevel level = LogLevel.ALL;

	/** This enumeration indicates which messages shall be logged. */
	public enum LogLevel {
		NONE, INFO, ERROR, DEBUG, ALL
	}
	
	/** Private constructor for singleton. */
	private Logger() {}
	
	/**
	 * Set log filename prefix, such as application name. Timestamp and 
	 * file extension will be appended automatically.
	 * @param prefix the filename prefix
	 */
	public void setLogFile(String prefix) {
		if (prefix == null) {
			file = null;
			message(LogLevel.DEBUG, "Logger", "Closed log file");
			return;
		}
		
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

	/**
	 * @return The singleton instance of the Logger.
	 */
	public static Logger getInstance() {
		return instance;
	}

	/**
	 * Sets the LogLevel to use.
	 * @param level The new LogLevel.
	 */
	public void setLevel(LogLevel level) {
		this.level = level;
	}

	/**
	 * @return The currently used LogLevel.
	 */
	public LogLevel getLevel() {
		return level;
	}

	/**
	 * @return The file currently being written to.
	 */
	public PrintWriter getFile() {
		return file;
	}

	/**
	 * Logs a message with LogLevel.INFO.
	 * @param tag The identifier for this message.
	 * @param message The message to log.
	 */
	public void info(String tag, String message) {
		message(LogLevel.INFO, tag, message);
	}

	/**
	 * Logs a message with LogLevel.ERROR.
	 * @param tag The identifier for this message.
	 * @param message The message to log.
	 */
	public void error(String tag, String message) {
		message(LogLevel.ERROR, tag, message);
	}

	/**
	 * Logs a message with LogLevel.DEBUG.
	 * @param tag The identifier for this message.
	 * @param message The message to log.
	 */
	public void debug(String tag, String message) {
		message(LogLevel.DEBUG, tag, message);
	}
	
	/**
	 * Logs a message.
	 * @param level The LogLevel to log this message with.
	 * @param tag The identifier for this message.
	 * @param message The message to log.
	 */
	private void message(LogLevel level, String tag, String message) {
		if (getLevel() == LogLevel.NONE) {
			return;
		}

		if (level.ordinal() <= getLevel().ordinal()) {
			String output = String.format(msgFormat, level, tag, message);

			if (level == LogLevel.ERROR) {
				error(output);
			} else {
				print(output);
			}
		}
	}

	/**
	 * Writes the supplied message to standard output and to file (if enabled).
	 * @param message The message to write.
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

	/**
	 * Writes the supplied message to standard error and to file (if enabled).
	 * @param message The message to write.
	 */
	private void error(String message) {
		Date now = new Date();
		String output = "[" + timeFormat.format(now) + "]: " + message;

		System.err.println(output);

		if (file != null) {
			file.println(output);
			file.flush();
		}
	}

	@Override
	public void dispose() {
		if (file != null) {
			file.close();
		}
	}
}
