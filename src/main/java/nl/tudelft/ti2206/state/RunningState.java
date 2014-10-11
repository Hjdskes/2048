package nl.tudelft.ti2206.state;

import nl.tudelft.ti2206.game.TwentyFourtyGame;
import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.handlers.InputHandler;
import nl.tudelft.ti2206.screens.GameScreen;
import nl.tudelft.ti2206.screens.LoseScreen;
import nl.tudelft.ti2206.screens.WinScreen;

public class RunningState implements GameState{

	private InputHandler inputhandler;

	public RunningState(){
		//inputhandler = handler; 
	}	

	@Override
	public void update(Grid grid) {
		if (grid.getCurrentHighestTile() == 2048) {
			TwentyFourtyGame.setState(TwentyFourtyGame.getWonState());
			GameScreen.screenHandler.add(new WinScreen());
		} else if (grid.getPossibleMoves() == 0) {
			TwentyFourtyGame.setState(TwentyFourtyGame.getLostState());
			GameScreen.screenHandler.add(new LoseScreen());
		}
	}

	@Override
	public void update(Grid localgrid, Grid remotegrid) {
		// TODO Auto-generated method stub
		
	}
}
