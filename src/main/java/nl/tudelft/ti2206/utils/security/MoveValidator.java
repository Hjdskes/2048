package nl.tudelft.ti2206.utils.security;

import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.Grid.Direction;
import nl.tudelft.ti2206.utils.commands.Command;
import nl.tudelft.ti2206.utils.commands.UndoCommand;
import nl.tudelft.ti2206.utils.states.DisqualifiedState;

public class MoveValidator {

	/** The grid holding all the Tiles. */
	private Grid grid;

	/** An array of string representation for possible moves */
	private String[] possibleMoves;

	/** Boolean that is true if an irregularity is found */
	private boolean irregularity;

	/** Boolean that is true if a local grid is being validated */
	private boolean isLocal;

	/** A string representation of the last grid move*/
	private String savedBoard;

	public MoveValidator(Grid grid, boolean islocal) {
		this.irregularity = false;
		this.grid = grid;
		this.isLocal = islocal;
		possibleMoves = new String[4];
		savedBoard = grid.toString();

	}

	/**
	 * Validate the grid if it has changed since the last validation.
	 */
	public void validate() {		
		if (!savedBoard.equals(grid.toString())) {
			if (possibleMoves[0] != null) {
				validateByLastMove();
			}
			setPossibleMoves();
		}
	}

	/**
	 * Validate the grid by comparing it to a set of possible moves.
	 */
	private void validateByLastMove() {
		boolean similarFound = false;
		int i = 0;
		while (!similarFound && (i < 4)) {
			similarFound = compare(possibleMoves[i], grid.toString());
			i++;
		}
		if (!similarFound)
			irregularityFound(isLocal);

	}

	/**
	 * Sets irregularity to try and calls a state change if needed.
	 * 		@param isLocal is true if the local grid is being validated
	 */
	private void irregularityFound(boolean isLocal){
		irregularity = true;
		if(isLocal){
			TwentyFourtyGame.setState(DisqualifiedState.getInstance());
		}		
	}

	/**
	 * Compares two grids in string representation and looks if they are similar
	 * and thus could be the same grid with a different  
	 * 
	 * 		@param str1, str2: string that will be compared for similarity.
	 * 		
	 * 		@return true if the two grids are similar.
	 */
	private boolean compare(String str1, String str2) {
		String t1 = str1.replace(",", "");
		String t2 = str2.replace(",", "");
		int errorCount = 0;
		for (int i = 0; i < t1.length(); i++) {

			int a = Character.getNumericValue(t1.charAt(i));
			int b = Character.getNumericValue(t2.charAt(i));

			if (!(a == b)) {
				if (((a != 0) && (b != 0)) || errorCount > 1) {

					// The tiles are not just different randomly generated tiles
					if (!(((a == 1) && (b == 2)) || ((a == 2) && (b == 1)))) {
						return false;
					}
				}
				// One of the tiles is not a randomly generated tile.
				else if ((a > 2) || (b > 2)) {
					return false;
				}
				errorCount++;
			}
		}
		return true;
	}

	/**
	 * Save all possible moves for the current grid.
	 * Used to validate the next move after it is made.
	 */
	private void setPossibleMoves() {
		Grid ctrlGrid = grid.clone();
		savedBoard = ctrlGrid.toString();
		int i = 0;
		for (Direction dir : Direction.values()) {
			ctrlGrid.move(dir);
			possibleMoves[i] = ctrlGrid.toString();
			Command command = new UndoCommand(ctrlGrid);
			command.setStringAsGrid(savedBoard);
			i++;
		}
	}

	/**
	 * @return returns true if irregularities where found.
	 */
	public boolean getIrregularity() {
		return irregularity;

	}
}
