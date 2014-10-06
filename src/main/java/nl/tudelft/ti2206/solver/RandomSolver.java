package nl.tudelft.ti2206.solver;

import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.gameobjects.Grid.Direction;

public class RandomSolver {

	public static Direction selectMove(Grid grid) {
		Direction direction = Grid.Direction.values()[(int) (Math.random() * Grid.Direction.values().length)];
		return direction;
	}
}
