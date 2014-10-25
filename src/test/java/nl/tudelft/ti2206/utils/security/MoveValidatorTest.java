package nl.tudelft.ti2206.utils.security;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.utils.commands.UndoCommand;
import nl.tudelft.ti2206.utils.security.MoveValidator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

public class MoveValidatorTest {

	/** The object we are going to be testing*/
	private MoveValidator validator;

	/** The needed objects to help set the validations */
	private Grid grid;
	private UndoCommand cmd;

	/**
	 * Set up the objects before testing
	 */
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		grid = new Grid(false);
		validator = new MoveValidator(grid, true);
		cmd = new UndoCommand(grid);
	}

	/**
	 * Test if the validation is correct on a new grid.
	 */
	@Test
	public void testStartingGrid() {
		validator.validate();
		assertFalse(validator.getIrregularity());
	}

	/**
	 * Test the validation on an unchanged grid.
	 */
	@Test
	public void testGridUnchanged() {
		cmd.setStringAsGrid("1,1,1,1,0,0,0,0,1,1,1,1,0,0,0,0");
		validator.validate();
		validator.validate();
		assertFalse(validator.getIrregularity());
	}

	/**
	 * Test the validation of a valid move, no without differences. 
	 */
	@Test
	public void testValidMove() {
		cmd.setStringAsGrid("0,0,0,0,1,1,1,1,0,0,0,0,1,1,1,1,");
		validator.validate();
		cmd.setStringAsGrid("2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,");
		validator.validate();
		assertFalse(validator.getIrregularity());
	}
	/**
	 * Test the validation on a invalid move. Value too high to be a random tile. 
	 */
	@Test
	public void testInvalidMove() {
		cmd.setStringAsGrid("0,0,0,0,1,1,1,1,0,0,0,0,1,1,1,1,");
		validator.validate();
		cmd.setStringAsGrid("7,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,");
		validator.validate();
		assertTrue(validator.getIrregularity());
	}

	/**
	 * Test if the validation is correct when the random tiles are not the same.
	 */
	@Test
	public void testWrongValueOfRandom() {
		cmd.setStringAsGrid("3,6,1,2,2,1,2,3,2,5,1,5,1,3,4,1");
		validator.validate();
		cmd.setStringAsGrid("3,6,1,1,2,1,2,3,2,5,1,5,1,3,4,1");
		validator.validate();
		assertFalse(validator.getIrregularity());
	}

	/**
	 * Test if the validation is correct when the tiles remain unchanged with
	 * an added value.
	 */
	@Test
	public void testNotRandomTile() {
		cmd.setStringAsGrid("0,0,0,0,1,1,1,1,0,0,0,0,1,1,1,1,");
		validator.validate();
		cmd.setStringAsGrid("0,3,0,0,1,1,1,1,0,0,0,0,1,1,1,1,");
		validator.validate();
		assertTrue(validator.getIrregularity());
	}

	/**
	 * Test if the validation is correct on a Validator that is set to validate
	 * a remote grid.
	 */
	@Test
	public void testInvalidRemote() {
		validator = new MoveValidator(grid, false);
		cmd.setStringAsGrid("0,0,0,0,1,1,1,1,0,0,0,0,1,1,1,1,");
		validator.validate();
		cmd.setStringAsGrid("7,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,");
		validator.validate();
		assertTrue(validator.getIrregularity());
	}

	/**
	 * Test if the validation is correct when the grids have to many differences.
	 */
	@Test
	public void testErrorCounter() {
		cmd.setStringAsGrid("0,1,0,0,1,1,1,1,0,0,0,0,1,1,1,1,");
		validator.validate();
		cmd.setStringAsGrid("1,0,1,0,1,1,1,1,0,0,0,0,1,1,1,1,");
		validator.validate();
		assertTrue(validator.getIrregularity());
	}
}
