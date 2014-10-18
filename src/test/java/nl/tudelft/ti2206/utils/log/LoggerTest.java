package nl.tudelft.ti2206.utils.log;

import static org.junit.Assert.*;
import nl.tudelft.ti2206.utils.log.Logger;

import org.junit.Before;
import org.junit.Test;

public class LoggerTest {
	
	/** The singleton reference to the Logger instance. */
	private static Logger logger = Logger.getInstance();

	/** Get current class name, used for logging output. */
	private final String className = this.getClass().getSimpleName();

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testSetLogFile() {
		logger.setLogFile("2048");
	}

	@Test
	public void testGetInstance() {
		assertFalse(Logger.getInstance() == null);
	}

	@Test
	public void testLevel() {
		logger.setLevel(Logger.LogLevel.ERROR);
		assertEquals(Logger.LogLevel.ERROR, logger.getLevel());
	}

	@Test
	public void testInfo() {
		logger.info(className, "Test for info message.");
	}

	@Test
	public void testError() {
		logger.error(className, "Test for error message.");
	}

	@Test
	public void testDebug() {
		logger.debug(className, "Test for debug message.");
	}

	@Test
	public void testDispose() {
		logger.dispose();
	}

}
