package net.peng1104.game.maps;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.bukkit.World;

import net.peng1104.annotation.Default;
import net.peng1104.annotation.NotNull;
import net.peng1104.annotation.Nullable;
import net.peng1104.storage.game.enums.GameType;
import net.peng1104.storage.game.files.GameMapFile;
import net.peng1104.utils.WorldAPI;

/**
 * {@link Class} to create and manage a {@link GameMap}
 * 
 * @since 1.0.0
 * 
 * @author Peng1104
 */

public class GameMap {
	
	/**
	 * See {@link #getName()}
	 * 
	 * @since 1.0.0
	 */
	
	private final String name;
	
	/**
	 * See {@link #getWorldSource()}
	 * 
	 * @since 1.0.0
	 */
	
	private final File source;
	
	/**
	 * The config {@link File} of this {@link GameMap}
	 * 
	 * @since 1.0.0
	 */
	
	private final GameMapFile configFile;
	
	/**
	 * The maintance {@link GameWorld}
	 * 
	 * @since 1.0.0
	 */
	
	private GameWorld maintanceGameWorld;
	
	/**
	 * Create a new {@link GameMap}
	 * 
	 * @param name The name of this {@link GameMap}
	 * @param source The source for the {@link #createWorld(GameStyle, int)}
	 * 
	 * @throws IllegalArgumentException If the given name is null or empty or the given source
	 * {@link File} is null
	 * 
	 * @since 1.0.0
	 */
	
	public GameMap(@NotNull String name, @NotNull File source) {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Name cannot be null or empty");
		}
		if (source == null) {
			throw new IllegalArgumentException("Source cannot be null");
		}
		this.name = name;
		this.source = source;
		configFile = new GameMapFile(name);
	}
	
	/**
	 * Get the name of this {@link GameMap}
	 * 
	 * @return The name of this {@link GameMap}
	 * 
	 * @since 1.0.0
	 */
	
	@Default(value = String.class)
	public String getName() {
		return name;
	}
	
	/**
	 * Get the {@link #createWorld(GameStyle, int)} {@link File} source
	 * 
	 * @return The {@link File} sorce for the creation of this {@link GameMap} {@link GameWorld}
	 * 
	 * @since 1.0.0
	 */
	
	@Default(value = File.class)
	public File getWorldSource() {
		return source;
	}
	
	/**
	 * The {@link GameMapFile} of this {@link GameMap}
	 * 
	 * @return The {@link GameMapFile} of this {@link GameMap}
	 * 
	 * @since 1.0.0
	 */
	
	@Default(value = GameMapFile.class)
	public GameMapFile getConfigFile() {
		return configFile;
	}
	
	/**
	 * Get the maintenance {@link GameWorld} of this {@link GameMap}
	 * 
	 * @return The maintenance {@link GameWorld} of this {@link GameMap}
	 * 
	 * @since 1.0.0
	 */
	
	@Nullable
	public GameWorld getMaintenanceGameWorld() {
		if (maintanceGameWorld == null) {
			World world;
			
			if (!source.isDirectory()) {
				world = WorldAPI.createVoidWolrd(name);
			}
			else {
				world = WorldAPI.createWorld(source, name);
			}
			if (world != null) {
				if (configFile.canBeUsed()) {
					GameType type = configFile.getValidTypes().iterator().next();
					
					maintanceGameWorld = new GameWorld(world,
							configFile.getSpawnPoints(world, type), type);
				}
				else {
					maintanceGameWorld = new GameWorld(world, new ArrayList<>(), GameType.MURDER);
				}
			}
		}
		return maintanceGameWorld;
	}
	
	/**
	 * {@link Method} to reset the {@link #getMaintenanceGameWorld()}
	 * 
	 * @since 1.0.0
	 */
	
	public void resetMaintenanceGameWorld() {
		if (maintanceGameWorld != null) {
			maintanceGameWorld.delete();
			maintanceGameWorld = null;
		}
	}
	
	/**
	 * {@link Method} to create a {@link GameWorld} using this {@link GameMap}
	 * 
	 * @param style The {@link GameStyle} of the {@link Game} that wants to create the
	 * {@link GameWorld}
	 * 
	 * @param id The id of the {@link Game} that wants to create the {@link GameWorld}
	 * 
	 * @return The created {@link GameWorld} using the given options
	 * 
	 * @since 1.0.0
	 */
	
	@Nullable
	public GameWorld createGameWorld(@NotNull GameType type, int id) {
		if (configFile.isValidType(type) && id > 0) {
			World world = WorldAPI.createWorld(getWorldSource(), id + "_" + getName());
			
			if (world != null) {
				return new GameWorld(world, configFile.getSpawnPoints(world, type), type);
			}
		}
		return null;
	}
}