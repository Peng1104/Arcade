package net.peng1104.guis.room;

import net.peng1104.annotation.NotNull;
import net.peng1104.game.Room;
import net.peng1104.gui.Gui;
import net.peng1104.profiles.Profile;

public class NormalGameTypeGui extends Gui {
	
	private final Room room;
	
	public NormalGameTypeGui(@NotNull Room room) {
		super("Modos de Jogo");
		
		if (room == null) {
			throw new IllegalArgumentException("Room cannot be null");
		}
		this.room = room;
	}
	
	@Override
	public void openGUI(Profile profile) {
		// TODO Auto-generated method stub
		
	}
}