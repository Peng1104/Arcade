package net.peng1104.storage.game.files;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
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
	 * The {@link Set} containing all the valid {@link GameType}s for this {@link GameMap}
	 * 
	 * @since 1.0.0
	 */
	
	private final Set<GameType> validTypes;
	
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
		
		validTypes = new HashSet<>();
		
		for (String type : getKeys()) {
			validTypes.add(GameType.getByName(type));
		}
		validTypes.remove(null);
	}
	
	/**
	 * Check if the {@link GameMap} represented by this {@link GameMapFile}
	 * can be used
	 * 
	 * @return True if there is any valid {@link GameStyle} configured in this
	 * {@link GameMapFile}
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
	 * Get if a {@link GameType} has any spawn point configured in this
	 * {@link GameMapFile}
	 * 
	 * @param gameStyle The {@link GameType} to check
	 * 
	 * @return True if this {@link GameMapFile} has any spawn point configured
	 * for the given {@link GameType}
	 * 
	 * @since 1.0.0
	 */
	
	@Default(Boolean = false)
	public boolean isValidType(@NotNull GameType gameType) {
		return validTypes.contains(gameType);
	}
	
	/**
	 * Get all the configured {@link GameType}s in this {@link GameMapFile}
	 * 
	 * @return A unmodifiable {@link Set} containing all the {@link GameType}s
	 * that have any spawn point configured in this {@link GameMapFile}
	 * 
	 * @since 1.0.0
	 * 
	 * @see #isValidType(GameType)
	 */
	
	@Default(value = Set.class)
	public Set<GameType> getValidTypes() {
		return Collections.unmodifiableSet(validTypes);
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
		List<Location> resultList = new ArrayList<>();
		
		if (world == null || gameType == null) {
			return resultList;
		}
		String type = gameType.getName();
		
		Object object = get(type);
		
		if (object instanceof String) {
			return getSpawnPoints(world, get(gameType, new HashSet<>()));
		}
		if (object instanceof ConfigurationSection) {
			Set<String> spawns = getSubKeys(type);
			type += '.';
			
			for (String spawn : spawns) {
				spawn = type + spawn;
				
				double x = getDouble(spawn + ".x");
				double y = getDouble(spawn + ".y") + 1.5;
				double z = getDouble(spawn + ".z");
				float yaw = getFloat(spawn + ".yaw");
				float pitch = getFloat(spawn + ".pitch");
				
				resultList.add(new Location(world, x, y, z, yaw, pitch));
			}
		}
		return resultList;
	}
	
	/**
	 * {@link Method} to set the spawn points of one {@link GameType} to
	 * the same spawn points of another {@link GameType}
	 * 
	 * @param type The {@link GameType} to set the spawn points to
	 * @param base The {@link GameType} to get the spawns points from
	 * 
	 * @return True if there was a change in the {@link Configuration}, false
	 * otherwise
	 * 
	 * @since 1.0.0
	 */
	
	@Default(value = HashSet.class)
	public boolean save(@NotNull GameType type, @NotNull GameType base) {
		if (type != null && get(type, new HashSet<>()) != base && get(base, new HashSet<>()) != null) {
			validTypes.add(type);
			set(type.getName(), base.getName());
			return save();
		}
		return false;
	}
	
	/**
	 * {@link Method} to set the spawn points for a {@link GameType}
	 * 
	 * @param gameType The {@link GameType} to set the spawn points to
	 * @param spawnPoints The spawn points to be set to the given {@link GameType}
	 * 
	 * @return True if the {@link GameType} spawn points have been updated,
	 * false otherwise
	 * 
	 * @since 1.0.0
	 */
	
	@Default(Boolean = false)
	public boolean save(@NotNull GameType gameType, @NotNull List<Location> spawnPoints) {
		if (gameType == null || spawnPoints == null || spawnPoints.isEmpty()) {
			return false;
		}
		validTypes.add(gameType);
		
		String type = gameType.getName() + '.';
		
		for (int i = 0; i < spawnPoints.size(); i++) {
			Location location = spawnPoints.get(i);
			
			String spawn = type + i;
			
			set(spawn + ".x", location.getX());
			set(spawn + ".y", location.getY());
			set(spawn + ".z", location.getZ());
			set(spawn + ".yaw", location.getYaw());
			set(spawn + ".pitch", location.getPitch());
		}
		return save();
	}
	
	/**
	 * {@link Method} to remove a {@link GameType} of this {@link GameMapFile},
	 * if another {@link GameType} is linked to the {@link GameType} that will
	 * be removed it also will be removed
	 * 
	 * @param gameType The {@link GameType} to remove
	 * 
	 * @return A {@link Set} containg all the removed {@link GameType}s
	 * 
	 * @since 1.0.0
	 */
	
	@Default(value = HashSet.class)
	public boolean remove(@NotNull GameType gameType) {
		if (validTypes.remove(gameType)) {
			String type = gameType.getName();
			
			for (String type2 : getKeys()) {
				Object object = get(type2);
				
				if (object instanceof String && object.equals(type)) {
					set(type2, null);
					remove0(GameType.getByName(type2));
				}
			}
		}
		if (validTypes.isEmpty()) {
			return FileUtils.delete(getFilePath());
		}
		return save();
	}
	
	/**
	 * Internal {@link Method} to remove a {@link GameType} and all the linked
	 * {@link GameType}s to the given {@link GameType} of this {@link GameMapFile}
	 * 
	 * @param gameType The {@link GameType} to remove
	 * 
	 * @since 1.0.0
	 */
	
	@Default(value = HashSet.class)
	private void remove0(@NotNull GameType gameType) {		
		if (validTypes.remove(gameType)) {
			String type = gameType.getName();
			
			for (String type2 : getKeys()) {
				Object object = get(type2);
				
				if (object instanceof String && object.equals(type)) {
					set(type2, null);
					remove0(GameType.getByName(type2));
				}
			}
		}
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
			
			return get(GameType.getByName((String) object), set);
		}
		if (object instanceof ConfigurationSection) {
			return type;
		}
		return null;
	}
}