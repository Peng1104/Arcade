package net.peng1104.game.maps;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.Configuration;

import net.peng1104.Arcade;
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
	 * The {@link Set} containing all the valid {@link GameType}s for this {@link GameMap}
	 * 
	 * @since 1.0.0
	 */
	
	private final Set<GameType> validTypes = new HashSet<>();
	
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
		if (source == null || !source.isDirectory()) {
			throw new IllegalArgumentException("Source file must be a directory");
		}
		this.name = name;
		this.source = source;
		
		configFile = new GameMapFile(name);
		
		for (String key : configFile.getKeys()) {
			validTypes.add(GameType.getGameTypeByName(key));
		}
		validTypes.remove(null);
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
	 * @see #isValidStyle(GameStyle)
	 */
	
	@Default(Boolean = false)
	public boolean canBeUsed() {
		return !validTypes.isEmpty();
	}
	
	/**
	 * Get if a {@link GameType} is valid for this {@link GameMap}
	 * 
	 * @param gameStyle The {@link GameType} to check
	 * 
	 * @return True if the {@link GameType} for this {@link GameMap} is valid, false otherwise
	 * 
	 * @since 1.0.0
	 */
	
	@Default(Boolean = false)
	public boolean isValidType(@NotNull GameType gameType) {
		return validTypes.contains(gameType);
	}
	
	/**
	 * {@link Method} to create the Maintenance {@link GameWorld} of this {@link GameMap}
	 * 
	 * @return The Maintenance {@link GameWorld} of this {@link GameMap}
	 * 
	 * @since 6.6.7
	 */
	
	@Nullable
	public GameWorld createMaintenanceGameWorld() {
		World world = WorldAPI.createWorld(getWorldSource(), getName());
		
		if (world != null) {
			if (canBeUsed()) {
				GameType type = validTypes.iterator().next();
				
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
	
	@Default(Boolean = false)
	public boolean saveConfig(@NotNull GameType gameType, @Nullable GameType base) {
		if (gameType == null) return false;
		
		if (base == null) {
			
		}
	}
	
	/**
	 * Save all the spawn points from a given {@link GameStyle} into the {@link Configuration}
	 * {@link File}
	 * 
	 * @param spawnPoints The spawn points to save
	 * @param gameStyle The {@link GameStyle} of the spawn points to save
	 * 
	 * @return True if there was no error during the saving of the data to the YAML file
	 * 
	 * @since 1.0.0
	 */
	
	@Default(Boolean = false)
	public boolean saveConfig(@NotNull GameType type, @NotNull List<Location> spawnPoints) {
		if (type != null && spawnPoints != null) {
			boolean result = configFile.save(type, spawnPoints);
			
			if (result) {
				if (spawnPoints.isEmpty()) {
					if (validTypes.remove(type)) {
						for (LobbyGame game : Arcade.getInstance().getGameManager().getGames()) {
							if (style == game.getGameStyle()) {
								switch (game.getGameState()) {
									case WAITING:
									case VOTING:
										if (style.getName().equals(game.getPreSelectedMap())) {
											game.setPreSelectedMap(null);
										}
										if (game.getVoteMap().containsKey(getName())) {
											game.getVoteMap().remove(getName());
											game.updateVoteMap();
										}
										break;
									default:
										break;
								}
							}
						}
					}
				}
				else {
					validStyles.add(style);
				}
			}
			return result;
		}
		return false;
	}
}