package nl.tudelft.ti2206.gameobjects;

import java.util.Stack;

/**
 * This class is used to undo and redo moves on the chosen grid.
 * The undo and redo currently only work with moves made in the current session.
 * 
 * @author group-21
 */
public class MoveUtility {
	/** The current grid */
	private Grid grid;
	
	/** The Stack containing all of the moves made in the current session */
	private Stack<String> movesBackward;
	
	/** The Stack containing all of the moves saved after undoing */
	private Stack<String> movesForward;
	
	/**
	 * Construct a new MoveUtility object for the given grid.
	 * @param grid, The current grid.
	 */
	public MoveUtility(Grid grid) {
		this.grid = grid;
		movesBackward = new Stack<String>();
		movesForward = new Stack<String>();
	}
	
	/**
	 * Push the last move onto the Stack with the previous moves. 
	 */
	public void update() {
		movesBackward.push(grid.toString());
		movesForward = new Stack<String>();
	}
	
	/**
	 * Undo the last move made in the game.
	 * 
	 * Undo only works with moves made in the current session.
	 */
	public void undo() {
		if(!movesBackward.isEmpty()){
			movesForward.push(grid.toString());
			
			String[] temp = movesBackward.pop().split(",");
			for(int i = 0; i < temp.length; i++) {
				grid.setTile(i, Integer.parseInt(temp[i]));
			}
		}
	}
	
	/**
	 * Redo a move that was previously undone.
	 */
	public void redo() {
		if(!movesForward.isEmpty()){
			movesBackward.push(grid.toString());
			
			String[] temp = movesForward.pop().split(",");
			for(int i = 0; i < temp.length; i++) {
				grid.setTile(i, Integer.parseInt(temp[i]));
			}
		}
	}

	/**
	 * @return movesBackward, The stack containing previous moves.
	 */
	public Stack<String> getMovesBackward() {
		return movesBackward;
	}

	/**
	 * @return movesForward, The stack containing undone moves.
	 */
	public Stack<String> getMovesForward() {
		return movesForward;
	}
	
}
