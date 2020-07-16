package net.peng1104.game;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import net.peng1104.Arcade;
import net.peng1104.annotation.Default;
import net.peng1104.annotation.NotNull;
import net.peng1104.annotation.Nullable;
import net.peng1104.game.games.Game;
import net.peng1104.game.maps.GameMap;
import net.peng1104.profiles.Profile;
import net.peng1104.special.TimedThread;
import net.peng1104.storage.game.enums.GameType;
import net.peng1104.storage.game.enums.RoomState;

public class Room {
	
	/**
	 * The {@link Random} instance unsed to make {@link Random} operations in the {@link Room}
	 * {@link Class}
	 * 
	 * @since 1.0.0
	 */
	
	private static final Random RANDOM = new Random();
	
	/**
	 * The owner of this {@link Room} if private
	 * 
	 * @since 1.0.0
	 */
	
	private final UUID owner;
	
	/**
	 * The {@link Set} containing all the {@link Profile}s that are in this {@link Room}
	 * 
	 * @since 1.0.0
	 */
	
	private final Set<UUID> playersSet = new HashSet<>();
	
	/**
	 * The {@link Set} containing all the moderators {@link Profile}s of this {@link Room}
	 * 
	 * @since 1.0.0
	 */
	
	private final Set<UUID> moderatorsSet = new HashSet<>();
	
	/**
	 * The {@link Set} containing all the banned {@link Profile}s by their {@link UUID} of this
	 * {@link Room}
	 * 
	 * @since 1.0.0
	 */
	
	private final Set<UUID> banSet = new HashSet<>();
	
	/**
	 * The vote {@link Map} of this {@link Room}, this map contains all the {@link GameMap} options
	 * for the next {@link Game}
	 * 
	 * @since 1.0.0
	 */
	
	private final Map<String, Set<UUID>> voteMap = new HashMap<>();
	
	/**
	 * The id of this {@link Room}
	 * 
	 * @since 1.0.0
	 */
	
	private final int id;
	
	/**
	 * The amount of {@link Profile}s that chan join this {@link Room}
	 * 
	 * @since 1.0.0
	 */
	
	private int slots = 12;
	
	/**
	 * The amount of vote options in the {@link #getVoteMap()}
	 * 
	 * @since 1.0.0
	 */
	
	private int options = 3;
	
	/**
	 * If the {@link #gameType} is in event mode
	 * 
	 * @since 1.0.0
	 */
	
	private boolean event;
	
	/**
	 * The pre selected {@link GameMap} by its name
	 * 
	 * @since 1.0.0
	 */
	
	private String preMap;
	
	/**
	 * The password to join this {@link Room}
	 * 
	 * @since 1.0.0
	 */
	
	private String password;
	
	/**
	 * The {@link GameType} that is been played or will be played
	 * 
	 * @since 1.0.0
	 */
	
	private GameType gameType;
	
	/**
	 * The state of this {@link Room}
	 * 
	 * @since 1.0.0
	 */
	
	private RoomState state;
	
	/**
	 * The {@link Room} {@link TimedThread}
	 * 
	 * @since 1.0.0
	 */
	
	private TimedThread roomThread;
	
	/**
	 * The private {@link Room} delete {@link TimedThread}
	 * 
	 * @since 1.0.0
	 */
	
	private TimedThread deleteThread;
	
	/**
	 * Create a new public {@link Room}
	 * 
	 * @param id The id of this {@link Room}
	 * @param gameType The type of {@link Game} that will be played in this {@link Room}
	 * 
	 * @throws NullPointerException If the given {@link GameType} is null
	 * 
	 * @since 1.0.0
	 */
	
	public Room(int id, @NotNull GameType gameType) {
		this(id, gameType, "");
	}
	
	/**
	 * Create a new public {@link Room} with a password to join
	 * 
	 * @param id The id of this {@link Room}
	 * @param gameType The type of {@link Game} that will be played in this {@link Room}
	 * @param password The password to join in this {@link Room}
	 * 
	 * @throws NullPointerException If the given {@link GameType} or password {@link String} is null
	 * 
	 * @since 1.0.0
	 */
	
	public Room(int id, @NotNull GameType gameType, @NotNull String password) {
		if (gameType == null) {
			throw new NullPointerException("GameType cannot be null");
		}
		if (password == null) {
			throw new NullPointerException("Password cannot be null");
		}
		this.id = id;
		this.gameType = gameType;
		this.owner = null;
		this.password = password;
		resetRoom();
	}
	
	/**
	 * Create a new private {@link Room}
	 * 
	 * @param id The id of this {@link Room}
	 * @param gameType The type of {@link Game} that will be played in this {@link Room}
	 * @param owner The {@link Profile} {@link UUID} that has creted this {@link Room}
	 * @param password The password of this {@link Room}
	 * 
	 * @throws NullPointerException If the given {@link GameType}, owner {@link UUID} or password
	 * {@link String} is null
	 * 
	 * @since 1.0.0
	 */
	
	public Room(int id, @NotNull GameType gameType, @NotNull UUID owner, @NotNull String password) {
		if (gameType == null) {
			throw new NullPointerException("GameType cannot be null");
		}
		if (owner == null) {
			throw new NullPointerException("Owner cannot be null");
		}
		if (password == null) {
			throw new NullPointerException("Password cannot be null");
		}
		this.id = id;
		this.gameType = gameType;
		this.owner = owner;
		this.password = password;
		resetRoom();
	}
	
	/**
	 * Get the instance of this {@link Room}
	 * 
	 * @return The instance of this {@link Room}
	 * 
	 * @since 1.0.0
	 */
	
	@Default(value = Room.class)
	public Room getInstance() {
		return this;
	}
	
	/**
	 * Get the id of this {@link Room}
	 * 
	 * @return The id of this {@link Room}
	 * 
	 * @since 1.0.0
	 */
	
	@Default(Int = 1)
	public int getId() {
		return id;
	}
	
	/**
	 * {@link Method} to set the time of this {@link Room}
	 * 
	 * @param time The time to be set (need to be more that 10)
	 * 
	 * @since 1.0.0
	 */
	
	public void setRoomTimer(int time) {
		if (time > 10 && (state == RoomState.WAITING || state == RoomState.VOTING)) {
			if (state == RoomState.WAITING) {
				if (isPrivate()) {
					if (roomThread != null) {
						roomThread.cancel();
						roomThread = null;
					}
					deleteThread = new TimedThread(time) {
						
						@Override
						public void onLoop() {
							
							
							//TODO fazer sistema de alerta que a sala será apagata em x tempo
							
							if (getCount() == 120) {
								//Aviso 2 minutos para
							}
							if (getCount() == 60) {
								//Aviso 1 minuto va
							}
						}
						
						@Override
						public void execute() {
							//TODO apagar sala
						}
					};
					deleteThread.start();
				}
			}
			else {
				if (deleteThread != null) {
					deleteThread.cancel();
					deleteThread = null;
				}
				roomThread = new TimedThread(time) {
					
					@Override
					public void onLoop() {
						//TODO fazer loop
					}
					
					@Override
					public void execute() {
						//startGame();
					}
				};
				roomThread.start();
			}
		}
	}
	
	@Default(Long = -1)
	public long getRoomTime() {
		return roomThread == null ? -1 : roomThread.getCount();
	}
	
	/**
	 * {@link Method} to pause the {@link Room} timer
	 * 
	 * @param pause True if the {@link Room} timer should be paused
	 * 
	 * @return True if there was a change in the {@link Room} timer state, false otherwise
	 * 
	 * @since 1.0.0
	 */
	
	@Default(Boolean = false)
	public boolean setPaused(boolean pause) {
		if (roomThread != null) {
			if (pause) {
				if (!roomThread.isPaused()) {
					roomThread.setPaused(true);
					return true;
				}
			}
			else if (roomThread.isPaused()) {
				roomThread.setPaused(false);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Get if this {@link Room} is a private {@link Room}
	 * 
	 * @return True if this {@link Room} has no owner, false otherwise
	 * 
	 * @since 1.0.0
	 */
	
	@Default(Boolean = false)
	public boolean isPrivate() {
		return owner != null;
	}
	
	/**
	 * {@link Method} to reset this {@link Room}
	 * 
	 * @since 1.0.0
	 */
	
	public void resetRoom() {
		state = RoomState.WAITING;
		
		if (event) {
			gameType = GameType.MURDER;
			event = false;
		}
		if (playersSet.size() >= getMinPlayersAmount()) {
			state = RoomState.VOTING;
		}
		checkPreMap();
		setRoomTimer(180);
	}
	
	/**
	 * {@link Method} to get the minimum amount of {@link Profile}s to start the voting fase
	 * 
	 * @return The minimum amount of {@link Profile}s to start the voting fase
	 * 
	 * @since 1.0.0
	 */
	
	public int getMinPlayersAmount() {
		switch (gameType) {
			case MURDER:
				return 4;
			case MURDER_DOUBLE:
				return 8;
			default:
				return 2;
		}
	}
	
	/**
	 * {@link Method} to check if the {@link #preMap} can be used
	 * 
	 * @since 1.0.0
	 */
	
	private void checkPreMap() {
		if (preMap != null) {
			GameMap gameMap = Arcade.getInstance().getGameMapManager().get(preMap);
			
			if (gameMap == null || !gameMap.isValidType(gameType)) {
				setPreMap(null);
			}
		}
	}
	
	public void updateVoteMap() {
		List<String> options = Arcade.getInstance().getGameMapManager()
				.getAvalibleMaps(gameType);
		
		while (!options.isEmpty() && voteMap.size() < this.options) {
			String option = options.get(RANDOM.nextInt(options.size()));
			
			if (!voteMap.containsKey(option)) {
				voteMap.put(option, new HashSet<>());
			}
			options.remove(option);
		}
	}
	
	@Default(value = HashMap.class)
	public Map<String, Set<UUID>> getVoteMap() {
		return voteMap;
	}
	
	@Default(value = RoomState.class)
	public RoomState getState() {
		return state;
	}
	
	@Default(value = GameStyle.class)
	public GameStyle getGameStyle() {
		return gameStyle;
	}
	
	public void setPreMap(@Nullable String preMap) {
		if (gameState == GameState.WAITING || gameState == GameState.VOTING) {
			if (preMap != null) {
				if (preMap.isEmpty()) return;
				
				// TODO Notificar que mapa foi selecionado
			}
			this.preMap = preMap;
			updateAllVoteGuis();
		}
	}
	
	@Nullable
	public String getPreMap() {
		return preMap;
	}
}