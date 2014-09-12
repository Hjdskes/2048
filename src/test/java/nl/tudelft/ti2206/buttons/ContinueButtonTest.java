package nl.tudelft.ti2206.buttons;

import static org.mockito.Mockito.verify;
import nl.tudelft.ti2206.game.GameWorld;
import nl.tudelft.ti2206.game.GameWorld.GameState;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class ContinueButtonTest {

	private static Sprite sprite;
	private static ContinueButton button;
	private static GameWorld world;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		sprite = Mockito.mock(Sprite.class);
		button = new ContinueButton(1, 2, 3, 4, sprite, sprite);
		world = Mockito.mock(GameWorld.class);
	}

	@Test
	public void testOnClick() {
		button.onClick(world);
		verify(world).setGameState(GameState.CONTINUING);
	}
}
