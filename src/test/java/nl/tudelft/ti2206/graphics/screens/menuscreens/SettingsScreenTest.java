package nl.tudelft.ti2206.graphics.screens.menuscreens;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.times;
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
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
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
	private SelectBox<String> select;
	@Mock
	private Stage stage;
	@Mock
	private List<String> list;

	private SettingsScreen screen;
	private Logger logger = Logger.getInstance();

	@Before
	public void setUp() {
		new HeadlessLauncher().launch();
		MockitoAnnotations.initMocks(this);
		when(select.getList()).thenReturn(list);
		
		screen = new SettingsScreen(stage, menuButton, slider, label, checkBox,
				select);
	}

	@Test
	public void testInit() {
		verify(slider, times(2)).setValue(anyInt());
		verify(slider, times(2)).setX(100);
		verify(slider).setY(460);
		verify(slider).setY(130);
		verify(slider, times(2)).setWidth(400);

		verify(label, times(3)).setX(100);
		verify(label).setY(520);
		verify(label).setY(310);
		verify(label).setY(190);

		verify(checkBox).setChecked(anyBoolean());
		verify(checkBox).setX(100);
		verify(checkBox).setY(370);

		verify(slider, times(2)).addListener(any(EventListener.class));
		verify(checkBox).addListener(any(EventListener.class));
		verify(stage).addListener(any(EventListener.class));

		verify(stage).addActor(checkBox);
		verify(stage, times(3)).addActor(label);
		verify(stage).addActor(menuButton);
		verify(stage, times(2)).addActor(slider);
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
		logger.setLogFile(null);

		when(select.getSelected()).thenReturn(" EXPECTIMAX");

		screen.dispose();
		verify(stage).dispose();
		assertEquals(logger.getLevel(), PreferenceHandler.getInstance()
				.getLogLevel());
		boolean isLogFileEnabled = logger.getFile() != null;
		assertEquals(isLogFileEnabled, PreferenceHandler.getInstance()
				.isLogFileEnabled());
	}
}
