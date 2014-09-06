package nl.tudelft.ti2206.gameobjects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import nl.tudelft.ti2206.game.GameWorld;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class GridTest {

	Grid grid;
	Random random;
	@Mock
	GameWorld world;

	@Before
	public void setup() {
		grid = new Grid(world);
		random = new Random();
	}

	@Test
	public void testConstructor() {
		assertNotNull(grid);
	}

	@Test
	public void testConstructor2() {
		int TWO = 0;
		int FOUR = 0;
		for (int i = 0; i < 16; i++) {
			if (grid.getTiles()[i].getValue() == 2) {
				TWO++;
			}
			if (grid.getTiles()[i].getValue() == 4) {
				FOUR++;
			}
		}
		assertEquals(TWO + FOUR, 2);
	}

	@Test
	public void testHighest() {
		int empty = getRandomEmptyLocation();
		grid.getTiles()[empty].setValue(1024);
		int empty2 = getRandomEmptyLocation();
		grid.getTiles()[empty2].setValue(1025);
		grid.updateHighest();
		assertEquals(grid.getHighest(), 1025);
	}

	@Test
	public void testIsFull() {
		for (int i = 0; i < 16; i++) {
			grid.getTiles()[i].setValue(2);
		}
		assertTrue(grid.isFull());

	}

	@Test
	public void testIsFull2() {
		for (int i = 0; i < 16; i++) {
			grid.getTiles()[i].setValue(2);
			i++;
		}
		assertFalse(grid.isFull());
	}

	public int getRandomEmptyLocation() {
		int index = random.nextInt(grid.getTiles().length);
		while (!grid.getTiles()[index].isEmpty()) {
			index = random.nextInt(grid.getTiles().length);
		}
		return index;
	}
}
