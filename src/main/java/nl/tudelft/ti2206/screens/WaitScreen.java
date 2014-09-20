package nl.tudelft.ti2206.screens;

import nl.tudelft.ti2206.gameobjects.StringConstants;
import nl.tudelft.ti2206.handlers.AssetHandler;
import nl.tudelft.ti2206.net.Networking;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class WaitScreen extends Screen {
		/** The main label. */
		private Label label;
		private Table table;
		
		public WaitScreen() {
			stage = new Stage();
			table = new Table();
		}
		
		@Override
		public void create() {
			super.create();

			label = new Label("Waiting for host to start the game...",
					AssetHandler.getSkin());
			
			table.add(label);
			table.getCell(label).padBottom(60).row();
			

			table.setFillParent(true);
			
			stage.addActor(table);
		}
		
		@Override
		public void update() {
			super.update();
			
			if (Networking.isConnected()) {
					label.setText("Waiting for host to start the game...");
			
					if (Networking.isStartReceived())
						ScreenHandler.add(new MultiGameScreen());
			}
			else
				label.setText("Connection lost.");
		
			
		}

}
