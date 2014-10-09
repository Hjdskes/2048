package nl.tudelft.ti2206.drawables;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import nl.tudelft.ti2206.game.HeadlessLauncher;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.handlers.AssetHandler;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class ScoresTest {
	@Mock
	private Skin skin;
	@Mock
	private Label label;
	@Mock
	private LabelStyle style;
	@Mock
	private BitmapFont font;
	@Mock
	private Grid grid;

	private Scores scores;

	@Before
	public void setUp() {
		new HeadlessLauncher().launch();
		MockitoAnnotations.initMocks(this);
		AssetHandler.getInstance().setSkin(skin);

		style.font = font;

		doNothing().when(label).setStyle(any(LabelStyle.class));
		when(skin.get(anyString(), eq(LabelStyle.class))).thenReturn(style);

		scores = new Scores(label);
	}

	@Test
	public void testGetSetHighScore() {
		scores.setHighScore(20000);
		assertEquals(20000, scores.getHighScore());
		scores.setHighScore(10000);
		assertEquals(20000, scores.getHighScore());
	}

	@Test
	public void testGetSetHighestTile() {
		scores.setHighestTile(99999999);
		assertEquals(99999999, scores.getHighestTile());
		scores.setHighestTile(1);
		assertEquals(99999999, scores.getHighestTile());
	}

	@Test
	public void testGetX() {
		assertEquals(100, (int) scores.getX());
	}

	@Test
	public void testGetY() {
		assertEquals(520, (int) scores.getY());
	}

	@Test
	public void testScoreDisplay() {
		// make sure the labels are added to the group
		assertTrue(scores.getChildren().size == 3);

		// make sure the locations are set
		verify(label, times(3)).setX(anyInt());
		verify(label, times(3)).setY(anyInt());
		verify(label, times(3)).setAlignment(anyInt(), anyInt());
	}

	@Test
	public void testUpdate() {
		scores.update(grid, null);
		verify(label, times(3)).setText(anyString());
	}
}
