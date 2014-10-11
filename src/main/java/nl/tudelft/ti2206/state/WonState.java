package nl.tudelft.ti2206.state;

import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.handlers.InputHandler;
import nl.tudelft.ti2206.screens.GameScreen;
import nl.tudelft.ti2206.screens.WinScreen;

public class WonState implements GameState{

private InputHandler inputhandler;
	
	public WonState(){
		//inputhandler = handler; 
	}
	

	@Override
	public void update(Grid grid) {
		
		
	}


	@Override
	public void update(Grid localgrid, Grid remotegrid) {
		// TODO Auto-generated method stub
		
	}

}
