package net.peng1104;

import net.peng1104.annotation.Default;
import net.peng1104.annotation.Nullable;
import net.peng1104.game.GameManager;
import net.peng1104.game.maps.GameMapManager;

public class Arcade extends PengPlugin {
	
	/**
	 * The {@link Arcade} instatnce
	 * 
	 * @since 1.0.0
	 */
	
	private static Arcade instance;
	
	/**
	 * The {@link GameMapManager} instance
	 * 
	 * @since 1.0.0
	 */
	
	private GameMapManager gameMapManager;
	
	/**
	 * The {@link GameManager} instance
	 * 
	 * @since 1.0.0
	 */
	
	private GameManager gameManager;
	
	@Override
	public void onEnable() {
		if (hasBeenEnabled()) return;
		
		instance = this;
		super.onEnable();
		
		if (!hasLicense()) return;
		
		gameMapManager = new GameMapManager();
		gameManager = new GameManager();
	}
	
	/**
	 * Get the {@link Arcade} instance
	 * 
	 * @return The {@link Arcade} instance
	 * 
	 * @since 1.0.0
	 */
	
	@Nullable
	public static Arcade getInstance() {
		return instance;
	}
	
	@Default(value = GameMapManager.class)
	public GameMapManager getGameMapManager() {
		return gameMapManager;
	}
	
	@Default(value = GameManager.class)
	public GameManager getGameManager() {
		return gameManager;
	}
}