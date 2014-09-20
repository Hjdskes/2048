package nl.tudelft.ti2206.screens;

import nl.tudelft.ti2206.buttons.PlayButton;
import nl.tudelft.ti2206.gameobjects.StringConstants;
import nl.tudelft.ti2206.handlers.AssetHandler;
import nl.tudelft.ti2206.net.Networking;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class WaitScreen extends Screen {
		/** The main label. */
		private Label label;
		private Table table;
		
		private PlayButton play;
		public WaitScreen() {
			System.out.println("new WaitScreen, kutJente");
			stage = new Stage();
			table = new Table();
			play = new PlayButton();
			label = new Label("Press play when ready!",
					AssetHandler.getSkin());
			
		}
		
		@Override
		public void create() {
			super.create();


			table.add(label);
			table.getCell(label).padTop(20).padBottom(10).row();
			
			table.setFillParent(true);
			stage.addActor(table);
			stage.addActor(play);
			
			addPlayButtonListener();
		}
		
		private void addPlayButtonListener() {
			play.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					System.out.println("CLICK!");
					ScreenHandler.add(new MultiGameScreen());
				}
			});
		}
		
		@Override
		public void update() {
			super.update();
			
			if (Networking.isConnected()) {
				label.setText("Press play when you're ready!");
			}
			else
				label.setText("Connection lost.");
		
			
		}

}
