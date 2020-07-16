package net.peng1104.game;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.peng1104.annotation.Default;

public class GameManager {
	
	private final Map<Integer, LobbyGame> games = new HashMap<>();
	
	
	
	@Default(value = Collection.class)
	public Collection<LobbyGame> getGames() {
		return games.values();
	}
}