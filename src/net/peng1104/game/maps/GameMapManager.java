package net.peng1104.game.maps;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.peng1104.annotation.Default;
import net.peng1104.annotation.NotNull;
import net.peng1104.annotation.Nullable;
import net.peng1104.storage.files.FileBase;
import net.peng1104.storage.game.files.GameMapFile;
import net.peng1104.utils.FileUtils;

/**
 * {@link Class} to manage and create the {@link GameMap}s
 * 
 * @since 1.0.0
 * 
 * @author Peng1104
 */

public class GameMapManager {
	
	/**
	 * The {@link File} representing the map source container
	 * 
	 * @since 1.0.0
	 */
	
	private static final File MAP_SOURCE_CONTAINER = new File(FileBase.getDefaultDirectory(), "Arcade" + File.separator + "Mapas");
	
	/**
	 * The available {@link GameMap}s
	 * 
	 * @since 1.0.0
	 */
	
	private Map<String, GameMap> avalibleMaps = new HashMap<>();
	
	/**
	 * Simple {@link GameMapConfiguration} {@link Constructor}
	 * 
	 * @since 1.0.0
	 */
	
	public GameMapManager() {
		if (!GameMapFile.getGameMapConfigContainer().isDirectory()) {
			GameMapFile.getGameMapConfigContainer().delete();
			GameMapFile.getGameMapConfigContainer().mkdirs();
		}
		for (File configFile : GameMapFile.getGameMapConfigContainer().listFiles()) {
			String name = configFile.getName();
			
			if (name.endsWith(".yml")) {
				register(name.substring(0, name.length() - 4));
			}
		}
	}
	
	/**
	 * Get the {@link GameMap} map source container
	 * 
	 * @return The {@link File} that represents the {@link GameMap} map source
	 * container
	 * 
	 * @since 1.0.0
	 */
	
	@Default(value = File.class)
	public static File getMapSourceContainer() {
		return MAP_SOURCE_CONTAINER;
	}
	
	/**
	 * {@link Method} to create and register a new {@link GameMap} by its name
	 * 
	 * @param name The name of the new {@link GameMap} to register
	 * 
	 * @return True if the {@link GameMap} with the given name has been
	 * registred, false other wise
	 * 
	 * @since 1.0.0
	 */
	
	@Default(Boolean = false)
	public boolean register(@NotNull String name) {
		if (name != null && !name.isEmpty()) {
			File source = new File(MAP_SOURCE_CONTAINER, name);
			
			if (source.isDirectory()) {
				avalibleMaps.put(name, new GameMap(name, source));
				return true;
			}
		}
		return false;
	}
	
	/**
	 * {@link Method} to remove and delete a {@link GameMap}
	 * 
	 * @param name The name of the {@link GameMap} to remove and delete
	 * 
	 * @return True if the given {@link GameMap} by its name has been unregister and deleted (from
	 * the disk) withou any errors
	 * 
	 * @since 1.0.0
	 */
	
	@Default(Boolean = false)
	public boolean unRegister(@NotNull String name) {
		GameMap gameMap = avalibleMaps.remove(name);
		
		if (gameMap != null) {
			boolean sourceDeleted = FileUtils.delete(gameMap.getWorldSource());
			boolean configDeleted = FileUtils.delete(gameMap.getConfigFileLocation());
			
			if (!sourceDeleted && !configDeleted) {
				avalibleMaps.put(name, gameMap);
				return false;
			}
			return sourceDeleted && configDeleted;
		}
		return false;
	}
	
	/**
	 * Check if a registered {@link GameMap} exists by the {@link GameMap} name
	 * 
	 * @param name The name of the {@link GameMap} to check
	 * 
	 * @return True if there is a registered {@link GameMap} whit the given name, false otherwise
	 * 
	 * @since 1.0.0
	 */
	
	@Default(Boolean = false)
	public boolean hasMap(@NotNull String name) {
		return avalibleMaps.containsKey(name);
	}
	
	/**
	 * Get a registered {@link GameMap} by the name of it
	 * 
	 * @param name The name of the {@link GameMap} to get
	 * 
	 * @return The register {@link GameMap} by its name or null if this there is not a registered
	 * {@link GameMap} with the given name
	 * 
	 * @since 1.0.0
	 */
	
	@Nullable
	public GameMap get(@NotNull String name) {
		return avalibleMaps.get(name);
	}
	
	/**
	 * Get all the available {@link GameMap}s by their name
	 * 
	 * @return A {@link List} containing all available {@link GameMap}s by their names
	 * 
	 * @since 1.0.0
	 */
	
	@Default(value = ArrayList.class)
	public List<String> getAvalibleMaps() {
		List<String> result = new ArrayList<>(avalibleMaps.keySet());
		
		Collections.sort(result);
		return result;
	}
	
	/**
	 * Get all the available {@link GameMap}s for a specific {@link GameStyle}
	 * 
	 * @param style The {@link GameStyle} to get the {@link GameMap}s from
	 * 
	 * @return A {@link List} containing all the available {@link GameMap}s for the given
	 * {@link GameStyle} by thier names
	 * 
	 * @since 1.0.0
	 */
	
	@Default(value = ArrayList.class)
	public List<String> getAvalibleMaps(@NotNull GameStyle style) {
		Set<String> options = new HashSet<>();
		
		for (GameMap gameMap : avalibleMaps.values()) {
			if (gameMap.isValidStyle(style)) {
				options.add(gameMap.getName());
			}
		}
		return new ArrayList<>(options);
	}
}