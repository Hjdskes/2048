package nl.tudelft.ti2206.helpers;

import nl.tudelft.ti2206.game.GameWorld;
import nl.tudelft.ti2206.gameobjects.Square;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

public class InputHandler implements InputProcessor {

	private GameWorld world;
	private float scale;
	
	public InputHandler(GameWorld world, int screenWidth) {
		this.world = world;
		
		scale = 600f / screenWidth;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		
//		System.out.println("keyDown: keycode = " + keycode);
		Square[] grid ;
		switch (keycode)
		{
		
		case Keys.DOWN: // arrow down
			break;
		case Keys.LEFT: // arrow left
			
			grid = world.getGrid().getSquares();
			
			
			
			for (int i = 0; i < grid.length; i++) {
				
				if ((grid[i].getIndex() % 4) != 0)  {
					
						// simply move to the left
						grid[i].setIndex(grid[i].getIndex() - 1);
				
				}
			}
			
			
			
			break;
		case Keys.RIGHT: // arrow right
			
			grid = world.getGrid().getSquares();
			
			for (int i = 0; i < grid.length; i++) {
				
				if ((grid[i].getIndex() % 4) != 3) {
					grid[i].setIndex(grid[i].getIndex() + 1);
			
				}
			}
			
			break;
			
		default:
				break;
		}
		
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

}
