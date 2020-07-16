package net.peng1104.storage.game.enums;

import net.peng1104.game.Room;
import net.peng1104.game.games.Game;

/**
 * {@link Enum} containg all the {@link Room} state values
 * 
 * @since 1.0.0
 * 
 * @author Peng1104
 */

public enum RoomState {
	
	/**
	 * The {@link Enum} representing if the game is wating for more players
	 * to start the
	 * 
	 * @since 1.0.0
	 */
	
	WAITING,
	
	/**
	 * The {@link Enum} representing the voting fase
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