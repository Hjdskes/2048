/**
 * 
 */
package nl.tudelft.ti2206.solver;

import static org.junit.Assert.*;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.Grid.Direction;
import nl.tudelft.ti2206.gameobjects.Tile;

import org.junit.Before;
import org.junit.Test;

public class HumanSolverTest {

	private Grid grid;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		grid = new Grid(true);
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2206.solver.HumanSolver#lowerRowFull(nl.tudelft.ti2206.gameobjects.Grid)}
	 * .
	 */
	@Test
	public void testLowerRowFulltrue() {
		Tile[] tiles = grid.getTiles();
		tiles[12].setValue(4);
		tiles[13].setValue(8);
		tiles[14].setValue(4);
		tiles[15].setValue(8);

		assertTrue(HumanSolver.lowerRowFull(grid));
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2206.solver.HumanSolver#lowerRowFull(nl.tudelft.ti2206.gameobjects.Grid)}
	 * .
	 */
	@Test
	public void testLowerRowFullfalse() {
		Tile[] tiles = grid.getTiles();
		for (int i = 12; i < tiles.length; i++)
			tiles[i].setValue(4);

		assertFalse(HumanSolver.lowerRowFull(grid));
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2206.solver.HumanSolver#isMergePossibleLowerRow(nl.tudelft.ti2206.gameobjects.Tile[])}
	 * .
	 */
	@Test
	public void testIsMergePossibleLowerRowtrue() {
		Tile[] tiles = grid.getTiles();
		for (int i = 12; i < tiles.length; i++)
			tiles[i].setValue(4);

		// tiles[12].setValue(4);
		// tiles[13].setValue(4);
		// tiles[14].setValue(4);
		// tiles[15].setValue(4);

		assertTrue(HumanSolver.isMergePossibleLowerRow(grid));
	}

	@Test
	public void testIsMergePossibleLowerRowfalse() {
		Tile[] tiles = grid.getTiles();
		tiles[12].setValue(4);
		tiles[13].setValue(8);
		tiles[14].setValue(4);
		tiles[15].setValue(8);
		assertFalse(HumanSolver.isMergePossibleLowerRow(grid));
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2206.solver.HumanSolver#nextMove(nl.tudelft.ti2206.gameobjects.Grid, int)}
	 * .
	 */
	@Test
	public void testNextMove() {
		// On an empty grid, nextMove() should not give us any points.
		assertEquals(0, HumanSolver.nextMove(grid, 3));
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2206.solver.HumanSolver#selectDirectionComplex(nl.tudelft.ti2206.gameobjects.Grid, int)}
	 * .
	 */
//	@Test
//	public void testSelectDirectionComplex() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * Test method for
//	 * {@link nl.tudelft.ti2206.solver.HumanSolver#selectDirectionSimple(nl.tudelft.ti2206.gameobjects.Grid)}
//	 * .
//	 */
//	@Test
//	public void testSelectDirectionSimple() {
//		fail("Not yet implemented");
//	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2206.solver.HumanSolver#emptyLowerLeft(nl.tudelft.ti2206.gameobjects.Grid)}
	 * .
	 */
	@Test
	public void testEmptyLowerLeft() {
		assertTrue(HumanSolver.emptyLowerLeft(grid));
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2206.solver.HumanSolver#maxLowerLeft(nl.tudelft.ti2206.gameobjects.Grid)}
	 * .
	 */
	@Test
	public void testMaxLowerLeftfalse() {
		Tile[] tiles = grid.getTiles();
		tiles[12].setValue(4);
		tiles[13].setValue(8);
		tiles[14].setValue(4);
		tiles[15].setValue(8);
		
		System.out.println(grid.toString());
		
		assertFalse(HumanSolver.maxLowerLeft(grid));
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2206.solver.HumanSolver#maxLowerLeft(nl.tudelft.ti2206.gameobjects.Grid)}
	 * .
	 */
	@Test
	public void testMaxLowerLefttrue() {
		Tile[] tiles = grid.getTiles();

		for (int i = 0; i < tiles.length; i++) {
			grid.setTile(i, 2);
		}

		grid.setTile(12, 4);
		// tiles[12].setValue(4);
		System.out.println("highest tile = " + grid.getCurrentHighestTile());
		assertTrue(HumanSolver.maxLowerLeft(grid));
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2206.solver.HumanSolver#selectMove(nl.tudelft.ti2206.gameobjects.Grid, int)}
	 * .
	 */
	@Test
	public void testSelectMove() {

		Direction direction = HumanSolver.selectMove(grid, 1);

		// since our grid is empty, the only direction
		// selectMove should return is UP
		assertEquals(Direction.UP, direction);

	}

}
