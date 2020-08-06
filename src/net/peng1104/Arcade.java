package net.peng1104;

import net.peng1104.annotation.Default;
import net.peng1104.annotation.Nullable;
import net.peng1104.game.GameManager;
import net.peng1104.game.RoomManager;
import net.peng1104.game.maps.MapManager;
import net.peng1104.storage.files.main.ArcadeConfigFile;

/**
 * {@link Class} to start and manage the {@link Arcade} {@link PengPlugin}
 * 
 * @since 1.0.0
 * 
 * @author Peng1104
 */

public class Arcade extends PengPlugin {
	
	/**
	 * The {@link Arcade} instatnce
	 * 
	 * @since 1.0.0
	 */
	
	private static Arcade instance;
	
	/**
	 * The {@link ArcadeConfigFile} instance
	 * 
	 * @since 1.0.0
	 */
	
	private ArcadeConfigFile configFile;
	
	/**
	 * The {@link RoomManager} instance
	 * 
	 * @since 1.0.0
	 */
	
	private RoomManager roomManager;
	
	/**
	 * The {@link MapManager} instance
	 * 
	 * @since 1.0.0
	 */
	
	private MapManager mapManager;
	
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
		
		configFile = new ArcadeConfigFile();
		
		mapManager = new MapManager();
		gameManager = new GameManager();
		roomManager = new RoomManager();
	}
	
	@Override
	public ArcadeConfigFile getConfiguration() {
		return configFile;
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
	
	/**
	 * Get the {@link RoomManager} instance
	 * 
	 * @return The {@link RoomManager} instance
	 * 
	 * @since 1.0.0
	 */
	
	@Default(value = RoomManager.class)
	public RoomManager getRoomManager() {
		return roomManager;
	}
	
	/**
	 * Get the {@link MapManager} instance
	 * 
	 * @return The {@link MapManager} instance
	 * 
	 * @since 1.0.0
	 */
	
	@Default(value = MapManager.class)
	public MapManager getMapManager() {
		return mapManager;
	}
	
	/**
	 * Get the {@link GameManager} instance
	 * 
	 * @return The {@link GameManager} instance
	 * 
	 * @since 1.0.0
	 */
	
	@Default(value = GameManager.class)
	public GameManager getGameManager() {
		return gameManager;
	}
}