/**
 * 
 */
package nl.tudelft.ti2206.utils.ai.solvers;

import static org.junit.Assert.*;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.Grid.Direction;
import nl.tudelft.ti2206.gameobjects.Tile;
import nl.tudelft.ti2206.utils.ai.solvers.HumanSolver;

import org.junit.Before;
import org.junit.Test;

public class HumanSolverTest {

	private Grid grid;
	private HumanSolver solver;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		grid = new Grid(true);
		solver = (HumanSolver) HumanSolver.getInstance();
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2206.utils.ai.solvers.HumanSolver#lowerRowFull(nl.tudelft.ti2206.gameobjects.Grid)}
	 * .
	 */
	@Test
	public void testLowerRowFulltrue() {
		Tile[] tiles = grid.getTiles();
		tiles[12].setValue(4);
		tiles[13].setValue(8);
		tiles[14].setValue(4);
		tiles[15].setValue(8);

		assertTrue(solver.lowerRowFull(grid));
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2206.utils.ai.solvers.HumanSolver#lowerRowFull(nl.tudelft.ti2206.gameobjects.Grid)}
	 * .
	 */
	@Test
	public void testLowerRowFullfalse() {
		Tile[] tiles = grid.getTiles();
		for (int i = 12; i < tiles.length; i++)
			tiles[i].setValue(4);

		assertFalse(solver.lowerRowFull(grid));
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2206.utils.ai.solvers.HumanSolver#isMergePossibleLowerRow(nl.tudelft.ti2206.gameobjects.Tile[])}
	 * .
	 */
	@Test
	public void testIsMergePossibleLowerRowtrue() {
		Tile[] tiles = grid.getTiles();
		for (int i = 12; i < tiles.length; i++)
			tiles[i].setValue(4);

		assertTrue(solver.isMergePossibleLowerRow(grid));
	}

	@Test
	public void testIsMergePossibleLowerRowfalse() {
		Tile[] tiles = grid.getTiles();
		tiles[12].setValue(4);
		tiles[13].setValue(8);
		tiles[14].setValue(4);
		tiles[15].setValue(8);
		assertFalse(solver.isMergePossibleLowerRow(grid));
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2206.utils.ai.solvers.HumanSolver#nextMove(nl.tudelft.ti2206.gameobjects.Grid, int)}
	 * .
	 */
	@Test
	public void testNextMove() {
		// On an empty grid, nextMove() should not give us any points.
		assertEquals(0, solver.nextMove(grid, 3));
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2206.utils.ai.solvers.HumanSolver#selectDirectionComplex(nl.tudelft.ti2206.gameobjects.Grid, int)}
	 * .
	 */
	@Test
	public void testSelectDirectionComplex() {

		Tile[] tiles = grid.getTiles();
		tiles[0].setValue(16);
		tiles[1].setValue(8);
		tiles[2].setValue(32);
		tiles[3].setValue(32);

		Direction direction = solver.selectDirectionComplex(grid, 1);

		assertEquals(Direction.DOWN, direction);
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2206.utils.ai.solvers.HumanSolver#selectDirectionComplex(nl.tudelft.ti2206.gameobjects.Grid, int)}
	 * .
	 */
	@Test
	public void testSelectDirectionComplexLeft() {

		grid.setTile(12, 0); // lower left is empty
		grid.setTile(13, 2);
		grid.setTile(14, 2);
		grid.setTile(15, 2);

		Direction direction = solver.selectDirectionComplex(grid, 1);

		assertEquals(Direction.LEFT, direction);
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2206.utils.ai.solvers.HumanSolver#selectDirectionSimple(nl.tudelft.ti2206.gameobjects.Grid)}
	 * .
	 */
	@Test
	public void testSelectDirectionSimpleLeft() {
		Tile[] tiles = grid.getTiles();
		for (int i = 0; i < tiles.length; i++)
			tiles[i].setValue(8);

		tiles[12].setValue(16);
		tiles[13].setValue(16);

		Direction direction = solver.selectDirectionSimple(grid);

		assertEquals(Direction.LEFT, direction);
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2206.utils.ai.solvers.HumanSolver#selectDirectionSimple(nl.tudelft.ti2206.gameobjects.Grid)}
	 * .
	 */
	@Test
	public void testSelectDirectionSimpleRight() {
		Tile[] tiles = grid.getTiles();

		tiles[12].setValue(16);

		Direction direction = solver.selectDirectionSimple(grid);

		assertEquals(Direction.RIGHT, direction);
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2206.utils.ai.solvers.HumanSolver#selectDirectionSimple(nl.tudelft.ti2206.gameobjects.Grid)}
	 * .
	 */
	@Test
	public void testSelectDirectionSimpleDown() {
		Tile[] tiles = grid.getTiles();

		tiles[0].setValue(2);
		tiles[1].setValue(4);
		tiles[4].setValue(2);

		Direction direction = solver.selectDirectionSimple(grid);

		assertEquals(Direction.DOWN, direction);
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2206.utils.ai.solvers.HumanSolver#selectDirectionSimple(nl.tudelft.ti2206.gameobjects.Grid)}
	 * .
	 */
	@Test
	public void testSelectDirectionSimpleUp() {
		Tile[] tiles = grid.getTiles();

		for (int i = 8; i < tiles.length; i++)
			grid.setTile(i, (int) Math.pow(2, i));

		Direction direction = solver.selectDirectionSimple(grid);

		assertEquals(Direction.UP, direction);
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2206.utils.ai.solvers.HumanSolver#emptyLowerLeft(nl.tudelft.ti2206.gameobjects.Grid)}
	 * .
	 */
	@Test
	public void testEmptyLowerLeft() {
		assertTrue(solver.emptyLowerLeft(grid));
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2206.utils.ai.solvers.HumanSolver#maxLowerLeft(nl.tudelft.ti2206.gameobjects.Grid)}
	 * .
	 */
	@Test
	public void testMaxLowerLeftfalse() {
		Tile[] tiles = grid.getTiles();
		tiles[12].setValue(4);
		tiles[13].setValue(8);
		tiles[14].setValue(4);
		tiles[15].setValue(8);

		assertFalse(solver.maxLowerLeft(grid));
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2206.utils.ai.solvers.HumanSolver#maxLowerLeft(nl.tudelft.ti2206.gameobjects.Grid)}
	 * .
	 */
	@Test
	public void testMaxLowerLefttrue() {
		Tile[] tiles = grid.getTiles();

		for (int i = 0; i < tiles.length; i++) {
			grid.setTile(i, 2);
		}

		grid.setTile(12, 4);

		assertTrue(solver.maxLowerLeft(grid));
	}

	/**
	 * Test method for
	 * {@link nl.tudelft.ti2206.utils.ai.solvers.HumanSolver#selectMove(nl.tudelft.ti2206.gameobjects.Grid, int)}
	 * .
	 */
	@Test
	public void testSelectMove() {

		grid.restart();
		Direction direction = solver.findMove(grid, 1);

		// since our grid is empty, the only direction
		// selectMove should return is LEFT
		assertEquals(Direction.LEFT, direction);

	}

}
