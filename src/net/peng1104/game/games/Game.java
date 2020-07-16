package net.peng1104.game.games;

import org.bukkit.event.Event;

import net.peng1104.annotation.NotNull;
import net.peng1104.game.GameWorld;
import net.peng1104.profiles.Profile;

public abstract class Game {
	
	private final Room room;
	
	private final GameWorld gameWorld;
	
	public Game(@NotNull GameWorld gameWorld) {
		this.gameWorld = gameWorld;
	}
	
	public abstract void randomTeleport(@NotNull Profile profile);
	
	public abstract void start();
	
	public abstract void end();
	
	public abstract void handleEvent(@NotNull Event event);
}