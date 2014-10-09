///**
// * 
// */
//package nl.tudelft.ti2206.solver;
//
//import static org.junit.Assert.*;
//import nl.tudelft.ti2206.gameobjects.Grid;
//import nl.tudelft.ti2206.gameobjects.Tile;
//
//import org.junit.Before;
//import org.junit.Test;
//
//public class HumanSolverTest {
//
//	private Grid grid;
//	
//	/**
//	 * @throws java.lang.Exception
//	 */
//	@Before
//	public void setUp() throws Exception {
//		grid = new Grid(true);
//	}
//
//	/**
//	 * Test method for {@link nl.tudelft.ti2206.solver.HumanSolver#lowerRowFull(nl.tudelft.ti2206.gameobjects.Grid)}.
//	 */
//	@Test
//	public void testLowerRowFulltrue() {
//		for (int i = 11; i < grid.getTiles().length; i++)
//			grid.setTile(i, 4);
//		
//		assertTrue(HumanSolver.lowerRowFull(grid));
//	}
//
//	/**
//	 * Test method for {@link nl.tudelft.ti2206.solver.HumanSolver#lowerRowFull(nl.tudelft.ti2206.gameobjects.Grid)}.
//	 */
//	@Test
//	public void testLowerRowFullfalse() {
//		Tile[] tiles = grid.getTiles();
//		for (int i = 13; i < tiles.length; i++)
//			tiles[i].setValue(4);
//		
//		assertFalse(HumanSolver.lowerRowFull(grid));
//	}
//
//
//	/**
//	 * Test method for {@link nl.tudelft.ti2206.solver.HumanSolver#isMergePossibleLowerRow(nl.tudelft.ti2206.gameobjects.Tile[])}.
//	 */
//	@Test
//	public void testIsMergePossibleLowerRow() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * Test method for {@link nl.tudelft.ti2206.solver.HumanSolver#nextMove(nl.tudelft.ti2206.gameobjects.Grid, int)}.
//	 */
//	@Test
//	public void testNextMove() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * Test method for {@link nl.tudelft.ti2206.solver.HumanSolver#selectDirectionComplex(nl.tudelft.ti2206.gameobjects.Grid, int)}.
//	 */
//	@Test
//	public void testSelectDirectionComplex() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * Test method for {@link nl.tudelft.ti2206.solver.HumanSolver#selectDirectionSimple(nl.tudelft.ti2206.gameobjects.Grid)}.
//	 */
//	@Test
//	public void testSelectDirectionSimple() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * Test method for {@link nl.tudelft.ti2206.solver.HumanSolver#emptyLowerLeft(nl.tudelft.ti2206.gameobjects.Grid)}.
//	 */
//	@Test
//	public void testEmptyLowerLeft() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * Test method for {@link nl.tudelft.ti2206.solver.HumanSolver#maxLowerLeft(nl.tudelft.ti2206.gameobjects.Grid)}.
//	 */
//	@Test
//	public void testMaxLowerLeft() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * Test method for {@link nl.tudelft.ti2206.solver.HumanSolver#selectMove(nl.tudelft.ti2206.gameobjects.Grid, int)}.
//	 */
//	@Test
//	public void testSelectMove() {
//		fail("Not yet implemented");
//	}
//
//}
