package nl.tudelft.ti2206.graphics.screens.menuscreens;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import nl.tudelft.ti2206.game.HeadlessLauncher;
import nl.tudelft.ti2206.graphics.buttons.MenuButton;
import nl.tudelft.ti2206.utils.handlers.PreferenceHandler;
import nl.tudelft.ti2206.utils.log.Logger;
import nl.tudelft.ti2206.utils.log.Logger.LogLevel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;

public class SettingsScreenTest {

	@Mock
	private MenuButton menuButton;
	@Mock
	private Slider slider;
	@Mock
	private Label label;
	@Mock
	private CheckBox checkBox;
	@Mock
	private Stage stage;

	private SettingsScreen screen;
	private Logger logger = Logger.getInstance();

	@Before
	public void setUp() {
		new HeadlessLauncher().launch();
		MockitoAnnotations.initMocks(this);
		screen = new SettingsScreen(stage, menuButton, slider, label, checkBox);
	}

	@Test
	public void testInit() {
		verify(menuButton).setX(10);
		verify(menuButton).setY(10);

		verify(slider).setValue(anyInt());
		verify(slider).setX(100);
		verify(slider).setY(350);
		verify(slider).setWidth(400);

		verify(label).setX(300);
		verify(label).setY(400);

		verify(checkBox).setChecked(anyBoolean());
		verify(checkBox).setX(100);
		verify(checkBox).setY(200);

		verify(slider).addListener(any(EventListener.class));
		verify(checkBox).addListener(any(EventListener.class));
		verify(stage).addListener(any(EventListener.class));

		verify(stage).addActor(checkBox);
		verify(stage).addActor(label);
		verify(stage).addActor(menuButton);
		verify(stage).addActor(slider);
	}

	@Test
	public void testUpdateLevel() {
		
		when(slider.getValue()).thenReturn(300f);
		String level = screen.updateLevel();
		assertEquals("INFO", level);
		assertEquals(LogLevel.INFO, logger.getLevel());

		when(slider.getValue()).thenReturn(200f);
		level = screen.updateLevel();
		assertEquals("DEBUG", level);
		assertEquals(LogLevel.DEBUG, logger.getLevel());

		when(slider.getValue()).thenReturn(400f);
		level = screen.updateLevel();
		assertEquals("ALL", level);
		assertEquals(LogLevel.ALL, logger.getLevel());

		when(slider.getValue()).thenReturn(100f);
		level = screen.updateLevel();
		assertEquals("ERROR", level);
		assertEquals(LogLevel.ERROR, logger.getLevel());

		when(slider.getValue()).thenReturn(0f);
		level = screen.updateLevel();
		assertEquals("NONE", level);
		assertEquals(LogLevel.NONE, logger.getLevel());
	}

	@Test
	public void testGetSliderValue() {
		logger.setLevel(LogLevel.INFO);
		assertEquals(300, screen.getSliderValue());

		logger.setLevel(LogLevel.DEBUG);
		assertEquals(200, screen.getSliderValue());

		logger.setLevel(LogLevel.ALL);
		assertEquals(400, screen.getSliderValue());

		logger.setLevel(LogLevel.ERROR);
		assertEquals(100, screen.getSliderValue());

		logger.setLevel(LogLevel.NONE);
		assertEquals(0, screen.getSliderValue());
	}

	@Test
	public void testDispose() {
		screen.dispose();
		verify(stage).dispose();
		assertEquals(logger.getLevel(), PreferenceHandler.getInstance()
				.getLogLevel());
		boolean isLogFileEnabled = logger.getFile() != null;
		assertEquals(isLogFileEnabled, PreferenceHandler.getInstance()
				.isLogFileEnabled());
	}
}
