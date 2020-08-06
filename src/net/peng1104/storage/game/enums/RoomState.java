package net.peng1104.storage.game.enums;

import net.peng1104.game.Room;
import net.peng1104.game.games.Game;
import net.peng1104.game.maps.GameMap;
import net.peng1104.profiles.Profile;

/**
 * {@link Enum} containg all the {@link Room} state values
 * 
 * @since 1.0.0
 * 
 * @author Peng1104
 */

public enum RoomState {
	
	/**
	 * The {@link Enum} representing if the {@link Room} has been force stoped
	 * 
	 * @since 1.0.0
	 */
	
	STOPED,
	
	/**
	 * The {@link Enum} representing if the {@link Room} is waiting for more {@link Profile}s to
	 * join to start the {@link #VOTING} fase
	 * 
	 * @since 1.0.0
	 */
	
	WAITING,
	
	/**
	 * The {@link Enum} representing the voting fase, where the {@link Room} is selecting the
	 * {@link GameType} that will be played and the {@link GameMap} 
	 * 
	 * @since 1.0.0
	 */
	
	VOTING,
	
	/**
	 * The {@link Enum} representing the starting of the {@link Game} fase
	 * 
	 * @since 1.0.0
	 */
	
	STARTING,
	
	/**
	 * The {@link Enum} represeting the playing the {@link Game} fase
	 * 
	 * @since 1.0.0
	 */
	
	PLAYING,
	
	/**
	 * The {@link Enum} representing the ending the {@link Game} fase
	 * 
	 * @since 1.0.0
	 */
	
	ENDING
}