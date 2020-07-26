package net.peng1104.game.maps;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;

import net.peng1104.annotation.Default;
import net.peng1104.annotation.NotNull;
import net.peng1104.annotation.Nullable;
import net.peng1104.game.GameWorld;
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
	 * Create a new {@link GameMap}
	 * 
	 * @param name The name of this {@link GameMap}
	 * @param source The source for the {@link #createWorld(GameStyle, int)}
	 * 
	 * @throws IllegalArgumentException If the given name is null or empty or the given source
	 * {@link File} is not a directory
	 * 
	 * @since 1.0.0
	 */
	
	public GameMap(@NotNull String name, @NotNull File source) {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Name cannot be null or empty");
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
	 * Get the locaction of this {@link GameMapFile}
	 * 
	 * @return The {@link File} that represents the location of this {@link GameMap}
	 * 
	 * @since 1.0.0
	 */
	
	@Default(value = File.class)
	public File getConfigFileLocation() {
		return new File(configFile.getFilePath());
	}
	
	/**
	 * Check if this {@link GameMap} can be used
	 * 
	 * @return True if there is any valid {@link GameStyle}
	 * 
	 * @since 1.0.0
	 * 
	 * @see #isValidType(GameType)
	 * @see GameMapFile#canBeUsed()
	 */
	
	@Default(Boolean = false)
	public boolean canBeUsed() {
		return configFile.canBeUsed();
	}
	
	/**
	 * Get if a {@link GameType} is valid for this {@link GameMap}
	 * 
	 * @param gameStyle The {@link GameType} to check
	 * 
	 * @return True if the given {@link GameType} is valid for this
	 * {@link GameMap}
	 * 
	 * @since 1.0.0
	 * 
	 * @see GameMapFile#isValidType(GameType)
	 */
	
	@Default(Boolean = false)
	public boolean isValidType(@NotNull GameType gameType) {
		return configFile.isValidType(gameType);
	}
	
	/**
	 * {@link Method} to create the Maintenance {@link GameWorld} of this {@link GameMap}
	 * 
	 * @return The Maintenance {@link GameWorld} of this {@link GameMap}
	 * 
	 * @since 1.0.0
	 */
	
	@Nullable
	public GameWorld createMaintenanceGameWorld() {
		World world = WorldAPI.createWorld(getWorldSource(), getName());
		
		if (world != null) {
			if (configFile.canBeUsed()) {
				GameType type = configFile.getValidTypes().iterator().next();
				
				return new GameWorld(world, configFile.getSpawnPoints(world, type), type);
			}
			return new GameWorld(world, new ArrayList<>(), GameType.MURDER);
		}
		return null;
	}
	
	/**
	 * Get the spawn points of this {@link GameMap} for a given {@link World} and {@link GameStyle}
	 * 
	 * @param world The {@link World} to create the spawn points for
	 * @param gameStyle The {@link GameStyle} to get the spawn points for
	 * 
	 * @return A {@link List} containg all the spawn poins for the given sytle in the given
	 * {@link World}
	 * 
	 * @since 1.0.0
	 */
	
	@Default(value = ArrayList.class)
	public List<Location> getSpawnPoints(@NotNull World world, @NotNull GameType gameType) {
		if (world == null || !isValidType(gameType)) return new ArrayList<>();
		return configFile.getSpawnPoints(world, gameType);
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
		if (isValidType(type) && id > 0) {
			World world = WorldAPI.createWorld(getWorldSource(), id + '_' + getName());
			
			if (world != null) {
				return new GameWorld(world, getSpawnPoints(world, type), type);
			}
		}
		return null;
	}
}