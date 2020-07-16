package net.peng1104.storage.game.files;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;

import net.peng1104.annotation.Default;
import net.peng1104.annotation.NotNull;
import net.peng1104.annotation.Nullable;
import net.peng1104.game.games.Game;
import net.peng1104.game.maps.GameMap;
import net.peng1104.storage.files.FileBase;
import net.peng1104.storage.game.enums.GameType;
import net.peng1104.utils.FileUtils;

/**
 * {@link Class} to create and manage a {@link GameMap} {@link Configuration}
 * {@link File}
 * 
 * @since 1.0.0
 * 
 * @author Peng1104
 */

public class GameMapFile extends FileBase {
	
	/**
	 * The {@link GameMap} {@link Configuration} {@link File} container location
	 * 
	 * @since 1.0.0
	 */
	
	private static final String CONTAINER_NAME = "Aracade" + File.separator + "Configurações";
	
	/**
	 * The {@link File} that represents the {@link GameMapFile}s container
	 * location
	 * 
	 * @since 1.0.0
	 */
	
	private static final File GAME_MAP_FILE_CONTAINER = new File(getDefaultDirectory(), CONTAINER_NAME);
	
	/**
	 * Create or load a {@link GameMapFile} using the name of a {@link GameMap}
	 * 
	 * @param name The name of the {@link GameMap}
	 * 
	 * @since 1.0.0
	 * 
	 * @see GameMap#getName()
	 */
	
	public GameMapFile(@NotNull String name) {
		super(new File(CONTAINER_NAME, name + ".yml"));
	}
	
	/**
	 * Get the {@link File} that represents the {@link GameMapFile}s container
	 * location
	 * 
	 * @return The {@link File} that represents the {@link GameMapFile}s
	 * container location
	 * 
	 * @since 1.0.0
	 */
	
	@Default(value = File.class)
	public static File getGameMapConfigContainer() {
		return GAME_MAP_FILE_CONTAINER;
	}
	
	/**
	 * {@link Method} to get the spawn points configured for the {@link GameMap}
	 * that this {@link GameMapFile} represents
	 * 
	 * @param world The {@link World} to create the spawn points for
	 * @param gameType The {@link GameType} to get the configured spawn points from
	 * 
	 * @return A {@link List} containing all the spawn points configured for the
	 * given {@link GameType} in the given {@link World}
	 * 
	 * @since 1.0.0
	 */
	
	@Default(value = ArrayList.class)
	public List<Location> getSpawnPoints(@NotNull World world, @NotNull GameType gameType) {
		if (world == null) return new ArrayList<>();
		return getSpawnPoints0(world, gameType);
	}
	
	/**
	 * Internal {@link Method} to get the spawn points configured for, the
	 * {@link GameMap} that this {@link GameMapFile} represents
	 * 
	 * @param world The {@link World} to create the spawn points for
	 * @param style The {@link GameType} to get the configured spawn points from
	 * 
	 * @return A {@link List} containing all the spawn points configured for the
	 * given {@link GameType} in the given {@link World}
	 * 
	 * @since 1.0.0
	 */
	
	@Default(value = ArrayList.class)
	private List<Location> getSpawnPoints0(@NotNull World world, @NotNull GameType gameType) {
		List<Location> result = new ArrayList<>();
		
		if (gameType == null) return result;
		
		String type = gameType.getName();
		
		Object object = get(type);
		
		if (object instanceof String) {
			return getSpawnPoints0(world, get(gameType, new HashSet<>()));
		}
		if (object instanceof ConfigurationSection) {
			Set<String> spawns = getSubKeys(type);
			
			String base = type + '.';
			
			for (String spawn : spawns) {
				String spawnPoint = base + spawn;
				
				double x = getDouble(spawnPoint + ".x");
				double y = getDouble(spawnPoint + ".y") + 1.5;
				double z = getDouble(spawnPoint + ".z");
				float yaw = getFloat(spawnPoint + ".yaw");
				float pitch = getFloat(spawnPoint + ".pitch");
				
				result.add(new Location(world, x, y, z, yaw, pitch));
			}
		}
		return result;
	}
	
	/**
	 * {@link Method} to set the spawn points of one {@link GameType} to
	 * the same spawn points of another {@link GameType}
	 * 
	 * @param style The {@link GameType} to set the spawn points to
	 * @param base The {@link GameType} to get the spawns points from
	 * 
	 * @return 
	 * 
	 * @since 1.0.0
	 */
	
	@Default(value = HashSet.class)
	public Set<GameType> save(@NotNull GameType type, @Nullable GameType base) {
		if (type == null) {
			return new HashSet<>();
		}
		if (base == null) {
			return remove(type);
		}
		else {
			if (get(type, new HashSet<>()) == null) {
				return false;
			}
			set(type.getName(), base.getName());
		}
		return save();
	}
	
	@Default(value = HashSet.class)
	public Set<GameType> removeSpawnPoints(@NotNull GameType gameType) {
		Set<GameType> result = new HashSet<>();
		
		if (gameType != null) {
			result.add(gameType);
			
			String gameTypeName = gameType.getName();
			
			for (String gameType2 : getKeys()) {
				Object value = get(gameType2);
				
				if (value instanceof String && value.equals(gameTypeName)) {
					set(gameType2, null);
					result.addAll(removeSpawnPoints(GameType.getByName(gameType2)));
				}
			}
		}
		return result;
	}
	
	/**
	 * {@link Method} to set the spawn points for a {@link GameType}
	 * 
	 * @param style The {@link GameType} to set the spawn points to
	 * @param spawnPoints The spawn points to be set to the given {@link GameType}
	 * 
	 * @return True if the {@link GameType} spawn points have been updated,
	 * false otherwise
	 * 
	 * @since 1.0.0
	 */
	
	@Default(Boolean = false)
	public boolean save(@NotNull GameType gameType, @NotNull List<Location> spawnPoints) {
		if (gameType == null || spawnPoints == null) return false;
		
		if (spawnPoints.isEmpty()) {
			String type = gameType.getName();
			
			set(type, null);
			
			for (String styleName2 : getKeys()) {
				Object object = get(styleName2);
				
				if (object instanceof String && ((String) object).equals(type)) { 
					set(styleName2, null);
				}
			}
			if (getKeys().isEmpty()) {
				return FileUtils.delete(getFilePath());
			}
			return save();
		}
		String base = gameType.getName() + '.';
		
		for (int i = 0; i < spawnPoints.size(); i++) {
			Location location = spawnPoints.get(i);
			
			String spawnPoint = base + i;
			
			set(spawnPoint + ".x", location.getX());
			set(spawnPoint + ".y", location.getY());
			set(spawnPoint + ".z", location.getZ());
			set(spawnPoint + ".yaw", location.getYaw());
			set(spawnPoint + ".pitch", location.getPitch());
		}
		return save();
	}
	
	/**
	 * {@link Method} get the {@link GameType}, that another {@link GameType}
	 * is configured to
	 * 
	 * @param gameStyle The {@link GameType} to get the real configuration
	 * {@link GameType} from
	 * 
	 * @param gameStyles The {@link Set} containg all the {@link GameType}s
	 * that have been already tested
	 * 
	 * @return The {@link GameType} that contains the spawn points configuration
	 * for the given {@link GameType}, null otherwise
	 * 
	 * @since 1.0.0
	 */
	
	@Nullable
	private GameType get(@NotNull GameType type, @NotNull Set<GameType> set) {
		if (type == null || set.contains(type)) return null;
		
		Object object = get(type.getName());
		
		if (object instanceof String) {
			set.add(type);
			
			return get(GameType.getGameTypeByName((String) object), set);
		}
		if (object instanceof ConfigurationSection) {
			return type;
		}
		return null;
	}
}