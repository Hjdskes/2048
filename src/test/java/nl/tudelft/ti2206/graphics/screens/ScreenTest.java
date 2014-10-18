package nl.tudelft.ti2206.graphics.screens;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.graphics.screens.drawbehaviour.DrawBeige;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

public class ScreenTest {
	
	@Mock
	private Stage stage;
	@Mock
	private Input input;
	@Mock
	private DrawBeige beige;
	@Mock
	private Grid grid;
	@Mock
	private Array<Actor> actors;
	
	private TestingScreen screen;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		Gdx.input = input;
		screen = new TestingScreen(stage);
		when(stage.getActors()).thenReturn(actors);
	}

	@Test
	public void testCreate() {
		screen.create();
		verify(input).setInputProcessor(stage);
	}
	
	@Test
	public void testSetDrawBehavior() {
		screen.setDrawBehavior(beige);
		assertEquals(beige, screen.drawbehavior);
	}
	
	@Test
	public void testDraw() {
		screen.setDrawBehavior(beige);
		screen.draw();
		verify(beige).draw();
	}
	
	@Test
	public void testUpdate() {
		screen.update();
		verify(stage).act();
	}
	
	@Test
	public void testRestart() {
		reset(input);
		screen.restart();
		verify(stage).getActors();
		verify(actors).clear();
		verify(input).setInputProcessor(stage);
		assertFalse(screen.hasOverlay());
	}
	
	@Test
	public void testGetStage() {
		assertEquals(stage, screen.getStage());
	}
	
	@Test
	public void testHasOverlay() {
		assertFalse(screen.hasOverlay());
	}

}
