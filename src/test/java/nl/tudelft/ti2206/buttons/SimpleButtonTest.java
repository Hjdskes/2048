package nl.tudelft.ti2206.buttons;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import nl.tudelft.ti2206.game.GameWorld;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SimpleButtonTest {

	private static Sprite sprite;
	private static SimpleButton button;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		sprite = Mockito.mock(Sprite.class);

		button = new SimpleButton(1, 2, 3, 4, sprite, sprite) {

			@Override
			public void onClick(GameWorld world) {
			}
		};
	}

	@Test
	public void testConstructor() {
		assertNotNull(button);
	}

	@Test
	public void testIsTouchDown() {
		assertTrue(button.isTouchDown(2, 3));
	}

	@Test
	public void testIsTouchDownFalse() {
		assertFalse(button.isTouchDown(20, 30));
	}
	
	@Test
	public void testIsTouchUp() {
		assertTrue(button.isTouchUp(2, 3));
	}

	@Test
	public void testIsTouchUpFalse() {
		assertFalse(button.isTouchUp(20, 30));
	}

	@Test
	public void testDrawNotPressed() {
		SpriteBatch batcher = Mockito.mock(SpriteBatch.class);
		button.isPressed = false;
		button.draw(batcher);
		verify(batcher).draw(sprite, 1, 2, 3, 4);
	}

	@Test
	public void testDrawPressed() {
		SpriteBatch batcher = Mockito.mock(SpriteBatch.class);
		button.isPressed = true;
		button.draw(batcher);
		verify(batcher).draw(sprite, 1, 2, 3, 4);
	} 
}
