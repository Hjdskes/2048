package nl.tudelft.ti2206.helpers;

import static org.junit.Assert.*;

import java.util.Random;

import nl.tudelft.ti2206.game.GameWorld;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.Grid.Direction;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class TileMoverTest {

	Grid grid;
	Random random;
	@Mock
	GameWorld world;

	@Before
	public void setup() {
		grid = new Grid(world, true);
		random = new Random();
	}


	@After
	public void tearDown() throws Exception {
	}

//	@Test
//	public void test() {
//		fail("Not yet implemented");
//	}

	
	public void testMoveUp(){
		grid.move(Direction.UP);
		
	}
}
