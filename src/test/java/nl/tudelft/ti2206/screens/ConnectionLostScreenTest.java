package nl.tudelft.ti2206.screens;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class ConnectionLostScreenTest {

	@Mock
	private Skin skin;
	@Mock
	private Stage stage;
	@Mock
	private Label label;
	@Mock
	private TextButton button;
	@Mock
	private Input input;
	
	private ConnectionLostScreen screen;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		screen = new ConnectionLostScreen(stage, label, button);
		
		Gdx.input = input;
		doNothing().when(input).setInputProcessor(stage);
	}

	@Test
	public void testCreate() {
		screen.create();
		
		verify(label).setAlignment(Align.center);
		verify(label).setX(anyInt());
		verify(label).setY(anyInt());
		
		verify(button).setX(anyInt());
		verify(button).setY(anyInt());
		verify(button).addListener(any(EventListener.class));
		
		verify(stage).addActor(label);
		verify(stage).addActor(button);
	}

	@Test
	public void testConnectionLostScreen() {
		assertNotNull(screen);
	}

}
