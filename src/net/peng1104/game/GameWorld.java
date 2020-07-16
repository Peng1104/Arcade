package net.peng1104.game;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;

import net.peng1104.annotation.Default;
import net.peng1104.annotation.NotNull;
import net.peng1104.game.games.Game;
import net.peng1104.game.maps.GameMap;
import net.peng1104.profiles.Profile;
import net.peng1104.storage.Storage;
import net.peng1104.storage.enums.main.PengAPIConfig;
import net.peng1104.storage.game.enums.GameType;
import net.peng1104.utils.ChatAPI;
import net.peng1104.utils.WorldAPI;

/**
* {@link Class} to manage the operations related to the {@link World} created
* by a {@link GameMap} for a {@link Game}
* 
* @author Peng1104
* 
* @since 1.0.0
*/

public class GameWorld {
	
	/**
	 * The {@link Random} instance unsed to make {@link Random} operations in the
	 * {@link GameWorld} {@link Class}
	 * 
	 * @since 1.0.0
	 */
	
	private static final Random RANDOM = new Random();
	
	/**
	 * See {@link #getWorld()}
	 * 
	 * @since 1.0.0
	 */
	
	private final World world;
	
	/**
	 * See {@link #getSpawnPoints()}
	 * 
	 * @since 1.0.0
	 */
	
	private final List<Location> spawnPoints;
	
	/**
	 * See #get
	 * 
	 * @since 1.0.0
	 */
	
	private GameType gameType;
	
	/**
	 * The {@link GameWorld} {@link Constructor}
	 * 
	 * @param world The {@link World} that the {@link GameWorld} will manage
	 * @param spawnPoints The spawn points {@link Location}s of the
	 * {@link GameWorld}
	 * 
	 * @param gameType The {@link GameType} of the {@link GameWorld}
	 * 
	 * @throws NullPointerException If any of the given parameters is null
	 * 
	 * @since 1.0.0
	 */
	
	public GameWorld(@NotNull World world, @NotNull List<Location> spawnPoints,
			@NotNull GameType gameType) {
		
		if (world == null) {
			throw new NullPointerException("World cannot be null");
		}
		if (spawnPoints == null) {
			throw new NullPointerException("SpawnPoints cannot be null");
		}
		if (gameType == null) {
			throw new NullPointerException("GameType cannot be null");
		}
		this.world = world;
		this.spawnPoints = spawnPoints;
		setGameType(gameType);
	}
	
	/**
	 * Get the {@link World} that this {@link GameWorld} is managing
	 * 
	 * @return The {@link World} of this {@link GameWorld}
	 * 
	 * @since 1.0.0
	 */
	
	@Default(value = World.class)
	public World getWorld() {
		return world;
	}
	
	/**
	 * Get the spawn points {@link Location}s of this {@link GameWorld}
	 * 
	 * @return A {@link List} contaning all the spawn points {@link Location}s
	 * of this {@link GameWorld}
	 * 
	 * @since 1.0.0
	 */
	
	@Default(value = List.class)
	public List<Location> getSpawnPoints() {
		return spawnPoints;
	}
	
	/**
	 * {@link Method} to set the {@link GameType} of this {@link GameWorld}
	 * 
	 * @param gameType The {@link GameType} to be set
	 * 
	 * @since 1.0.0
	 */
	
	public void setGameType(@NotNull GameType gameType) {
		if (gameType != null) {
			this.gameType = gameType;
		}
	}
	
	/**
	 * Get the {@link GameType} of this {@link GameWorld}
	 * 
	 * @return The {@link GameType} of this {@link GameWorld}
	 * 
	 * @since 1.0.0
	 */
	
	@Default(value = GameType.class)
	public GameType getGameType() {
		return gameType;
	}
	
	/**
	 * Get a {@link Random} index {@link Location} from the
	 * {@link #getSpawnPoints()}
	 * 
	 * @return A {@link Random} index {@link Location} from the
	 * {@link #getSpawnPoints()}
	 * 
	 * @since 1.0.0
	 */
	
	@Default(Int = 0)
	public int getRandomIndex() {
		if (spawnPoints.isEmpty()) {
			return 0;
		}
		return RANDOM.nextInt(spawnPoints.size());
	}
	
	/**
	 * Internal {@link Method} to get a {@link Random} {@link Location} from
	 * the {@link #getSpawnPoints()}
	 * 
	 * @return A {@link Random} {@link Location} from the
	 * {@link #getSpawnPoints()}
	 * 
	 * @since 1.0.0
	 */
	
	@Default(value = Location.class)
	private Location getRandomLocation() {
		return spawnPoints.get(RANDOM.nextInt(spawnPoints.size()));
	}
	
	/**
	 * Spawn an {@link Entity} of a specific {@link Class} in the
	 * {@link #getWorld()}
	 * 
	 * @param <T> The {@link Class} of the {@link Entity} to spawn
	 * @param entityClass The {@link Class} of the {@link Entity} to spawn
	 * 
	 * @return An instance of the spawned {@link Entity}
	 * 
	 * @since 1.0.0
	 */
	
	public <T extends Entity> T spawn(@NotNull Class<T> entityClass) {
		if (entityClass != null && !spawnPoints.isEmpty()) {
			return world.spawn(getRandomLocation(), entityClass);
		}
		return null;
	}
	
	/**
	 * {@link Method} to {@link Random} teleport {@link Profile}s to the
	 * {@link #getWorld()}
	 * 
	 * @param profiles The {@link Profile}s to teleport into the
	 * {@link #getWorld()}
	 * 
	 * @since 1.0.0
	 */
	
	public void randomTeleport(@NotNull Iterable<Profile> profiles) {
		if (profiles != null && !spawnPoints.isEmpty()) {
			for (Profile profile : profiles) {
				if (profile != null) {
					profile.teleport(getRandomLocation());
				}
			}
		}
	}
	
	/**
	 * {@link Method} to {@link Random} teleport a {@link Profile} 
	 * to the {@link #getWorld()}
	 * 
	 * @param profile The {@link Profile} to teleport into the {@link #getWorld()}
	 * 
	 * @since 1.0.0
	 */
	
	public void randomTeleport(@NotNull Profile profile) {
		if (profile != null) {
			profile.teleport(getRandomLocation());
		}
	}
	
	/**
	 * {@link Method} to teleport {@link Profile}s to the {@link #getWorld()}
	 * 
	 * @param profiles The {@link Profile}s to teleport into the
	 * {@link #getWorld()}
	 * 
	 * @param index The index of the spawn point to teleport the {@link Profile}
	 * to
	 * 
	 * @since 1.0.0
	 */
	
	public void teleportToLocation(@NotNull Iterable<Profile> profiles, int index) {
		if (profiles != null && index >= 0 && index < spawnPoints.size()) {
			Location location = spawnPoints.get(index);
			
			for (Profile profile : profiles) {
				if (profile != null) {
					profile.teleport(location);
				}
			}
		}
	}
	
	/**
	 * {@link Method} to teleport a {@link Profile} to the {@link #getWorld()}
	 * 
	 * @param profile The {@link Profile} to teleport into the {@link #getWorld()}
	 * @param index The index of the spawn point to teleport the {@link Profile}s
	 * to
	 * 
	 * @since 1.0.0
	 */
	
	public void teleportToLocation(@NotNull Profile profile, int index) {
		if (profile != null && index >= 0 && index < spawnPoints.size()) {
			profile.teleport(spawnPoints.get(index));
		}
	}
	
	/**
	 * {@link Method} to {@link Random} teleport {@link Profile}s to this {@link GameWorld}, trying
	 * not to teleport to {@link Profile} to the same {@link Location}
	 * 
	 * @param profiles The {@link Profile}s to teleport into this {@link GameWorld}
	 * 
	 * @since 6.6.7
	 */
	
	public void randomTeleportNotRepeated(@NotNull Collection<Profile> profiles) {
		if (profiles != null && !spawnPoints.isEmpty()) {
			List<Location> locations = new ArrayList<>(spawnPoints);
			boolean informed = false;
			
			for (Profile profile : profiles) {
				if (locations.isEmpty()) {
					if (!informed) {
						ChatAPI.sendBrocastMessage("&c[&4Arcade&c] &fO Mapa &c" +
								getName() + " &fnão possui o suficiente de spawn points configurados para o modo &c"
								+ getGameType() + "&f, faltam &c" + (profiles.size() - spawnPoints.size())
								+ " &flugares."
							, Storage.getString(PengAPIConfig.PERMISSÃO_RECEBER_AVISOS));
						informed = true;
					}
					randomTeleport(profile);
				}
				else if (profile != null && profile.isOnline()) {
					Location location = locations.get(RANDOM.nextInt(locations.size()));
					profile.teleport(location);
					locations.remove(location);
				}
			}
		}
	}
	
	/**
	 * Get the name of the {@link GameMap} that generated this {@link GameWorld}
	 * 
	 * @return The name of the {@link GameMap} that generated this
	 * {@link GameWorld}
	 * 
	 * @since 1.0.0
	 */
	
	@Default(string = "?")
	public String getName() {
		String name = world.getName();
		
		if (name.contains("_")) {
			return name.substring(name.indexOf('_'));
		}
		return name;
	}
	
	/**
	 * {@link Method} to delete this {@link GameWorld}, this {@link Method} will
	 * clear all the spawn points {@link Location}s and delete the {@link World}
	 * that is been managet
	 * 
	 * @return True if there was no erros during the deletion of this
	 * {@link GameWorld}, false otherwise
	 * 
	 * @since 1.0.0
	 */
	
	@Default(Boolean = false)
	public boolean delete() {
		spawnPoints.clear();
		return WorldAPI.deleteWorld(world);
	}
}