package nl.tudelft.ti2206.state;

import nl.tudelft.ti2206.gameobjects.Grid;
import nl.tudelft.ti2206.handlers.InputHandler;
import nl.tudelft.ti2206.screens.GameScreen;
import nl.tudelft.ti2206.screens.LoseScreen;

public class LostState implements GameState{

private InputHandler inputhandler;
	
	public LostState(){
		//inputhandler = handler; 
	}

	@Override
	public void update(Grid grid) {
		//GameScreen.screenHandler.add(new LoseScreen());
		
	}

	@Override
	public void update(Grid localgrid, Grid remotegrid) {
		// TODO Auto-generated method stub
		
	}
	
}
