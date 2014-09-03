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
		if (keycode == Keys.DPAD_DOWN) {
			Square[] square = world.getGrid().getSquares();
			updateMove(square);
			return true;
		}

		if (keycode == Keys.DPAD_UP) {
			Square[] orginSquare = world.getGrid().getSquares();
			Square[] square = new Square[16];
			for (int i = 0; i < 16; i++) {
				square[i] = orginSquare[15 - i];
			}
			updateMove(square);
			return true;
		}

		if (keycode == Keys.DPAD_LEFT) {
			Square[] square = sideGrid(world.getGrid().getSquares());
			updateMove(square);
			return true;
		}

		if (keycode == Keys.DPAD_RIGHT) {
			Square[] orginSquare = sideGrid(world.getGrid().getSquares());
			Square[] square = new Square[16];
			for (int i = 0; i < 16; i++) {
				square[i] = orginSquare[15 - i];
			}
			updateMove(square);
			return true;

		}

		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
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

	public void updateMove(Square[] square) {
		for (int i = 0; i < 12; i++) {
			if (square[i].getValue() > 0 && square[i + 4].getValue() == 0) {
				square[i + 4].setValue(square[i].getValue());
				square[i].setValue(0);
			}
			if (square[i].getValue() == square[i + 4].getValue()) {
				square[i].setValue(0);
				square[i + 4].doubleValue();
			}

		}
		if (!world.getGrid().isFull()) {
			world.getGrid().addBlock();
		}

	}

	/**
	 * Moves the grid on it's side. Can be made more efficient
	 * 
	 * @param square
	 * @return
	 */
	public Square[] sideGrid(Square[] square) {
		Square[] result = new Square[16];
		result[0] = square[3];
		result[1] = square[7];
		result[2] = square[11];
		result[3] = square[15];
		result[4] = square[2];
		result[5] = square[6];
		result[6] = square[10];
		result[7] = square[14];
		result[8] = square[1];
		result[9] = square[5];
		result[10] = square[9];
		result[11] = square[13];
		result[12] = square[0];
		result[13] = square[4];
		result[14] = square[8];
		result[15] = square[12];
		return result;
	}

}
