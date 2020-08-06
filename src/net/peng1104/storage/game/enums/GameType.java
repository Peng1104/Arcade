package net.peng1104.storage.game.enums;

import java.lang.reflect.Constructor;

import org.bukkit.scoreboard.Scoreboard;

import net.peng1104.annotation.Default;
import net.peng1104.annotation.NotNull;
import net.peng1104.annotation.Nullable;

/**
 * {@link Enum} containing all the possible {@link GameType}s
 * 
 * @since 1.0.0
 * 
 * @author Peng1104
 */

public enum GameType {
	
	/**
	 * The murder {@link GameType}
	 * 
	 * @since 1.0.0
	 */
	
	MURDER("Detetive"),
	
	/**
	 * The two {@link #MURDER}s {@link GameType}
	 * 
	 * @since 1.0.0
	 */
	
	MURDER_DOUBLE("Detetive em Dupla"),
	
	/**
	 * The free for all {@link GameType} with guns
	 * 
	 * @since 1.0.0
	 */
	
	FREE_FOR_ALL_GUN("Todos contra Todos-Armas", "Todos Armas"),
	
	/**
	 * The free for all {@link GameType} with swords
	 * 
	 * @since 1.0.0
	 */
	
	FREE_FOR_ALL_SWORD("Todos contra Todos-Espadas", "Todos Espadas"),
	
	/**
	 * The team {@link GameType} with guns only
	 * 
	 * @since 1.0.0
	 */
	
	TEAM_GUNN_VRS_GUNN("Time vs Time Armas", "Time Armas"),
	
	/**
	 * The team {@link GameType} with swords only
	 * 
	 * @since 1.0.0
	 */
	
	TEAM_GUNN_VRS_SWORD("Time vs Time Espadas", "Time Espadas"),
	
	/**
	 * The team {@link GameType} with swords agains guns
	 * 
	 * @since 1.0.0
	 */
	
	TEAM_SWORD_VRS_SWORD("Armas vs Espadas"),
	
	/**
	 * The hotpotato {@link GameType}
	 * 
	 * @since 1.0.0
	 */
	
	HOTPOTATO("Batata Quente"),
	
	/**
	 * The two {@link #HOTPOTATO}s {@link GameType}
	 * 
	 * @since 1.0.0
	 */
	
	HOTPOTATO_DOUBLE("Batata Quente em Dupla"),
	
	/**
	 * The one in the chamber {@link GameType}
	 * 
	 * @since 1.0.0
	 */
	
	OTIC("One In The Chamber", "OITC"),
	
	/**
	 * The lava floor {@link GameType}
	 * 
	 * @since 1.0.0
	 */
	
	LAVAFLOOR("Chão é Lava"),
	
	/**
	 * The hide and seek {@link GameType}
	 * 
	 * @since 1.0.0
	 */
	
	HIDE_AND_SEEK("Esconde-Esconde"),
	
	/**
	 * The build battle {@link GameType}
	 * 
	 * @since 1.0.0
	 */
	
	BUILD_BATTLE("Batalha das Contruções"),
	
	/**
	 * The {@link #BUILD_BATTLE} {@link GameType} in team mode
	 * 
	 * @since 1.0.0
	 */
	
	TEAM_BUILD_BATTLE("Batalha das Contruções em Time", "Contruções em Time"),
	
	/**
	 * The speed builders {@link GameType}
	 * 
	 * @since 1.0.0
	 */
	
	SPEED_BUILDERS("Speed Builders"),
	
	/**
	 * The halloween event {@link GameType}
	 * 
	 * @since 1.0.0
	 */
	
	HALLOWEEN("Dia das Bruxas"),
	
	/**
	 * The chrismas event {@link GameType}
	 */
	
	CHRISTMAS("Natal");
	
	/**
	 * The custom name of this {@link GameState}
	 * 
	 * @since 1.0.0
	 */
	
	private final String name;
	
	/**
	 * The {@link Scoreboard} display of this {@link GameState}
	 * 
	 * @since 1.0.0
	 */
	
	private final String scoreboard;
	
	/**
	 * {@link Constructor} to create a new {@link GameType} using only the
	 * custom name
	 * 
	 * @param name The custom name and {@link Scoreboard} display of the
	 * {@link GameType}
	 * 
	 * @since 1.0.0
	 */
	
	private GameType(@NotNull String name) {
		this(name, name);
	}
	
	/**
	 * {@link Constructor} to create a new {@link GameType} using the custom
	 * name and the {@link Scoreboard} display
	 * 
	 * @param name The custom name of the {@link GameType}
	 * @param scoreboard The {@link Scoreboard} display of the {@link GameType}
	 * 
	 * @since 1.0.0
	 */
	
	private GameType(@NotNull String name, @NotNull String scoreboard) {
		this.name = name;
		this.scoreboard = scoreboard;
	}
	
	/**
	 * Get a {@link GameType} by its name
	 * 
	 * @param name The name of the {@link GameType} to get
	 * 
	 * @return The {@link GameType} with the given name, null otherwise
	 * 
	 * @since 1.0.0
	 */
	
	@Nullable
	public static GameType getByName(@NotNull String name) {
		for (GameType gameStyle : values()) {
			if (gameStyle.name.equals(name)) {
				return gameStyle;
			}
		}
		return null;
	}
	
	/**
	 * Get the custom name of this {@link GameType}
	 * 
	 * @return The custom name of this {@link GameType}
	 * 
	 * @since 1.0.0
	 */
	
	@Default(value = String.class)
	public String getName() {
		return name;
	}
	
	/**
	 * Get the {@link Scoreboard} display of this {@link GameType}
	 * 
	 * @return The {@link Scoreboard} display of this {@link GameType}
	 * 
	 * @since 1.0.0
	 */
	
	@Default(value = String.class)
	public String getScoreboard() {
		return scoreboard;
	}
	
	/**
	 * Check if this {@link GameType} is a team mode {@link GameType}
	 * 
	 * @return True if this {@link GameType} is a team mode {@link GameType}
	 * 
	 * @since 1.0.0
	 */
	
	@Default(Boolean = false)
	public boolean isTeamMode() {
		switch (this) {
			case TEAM_GUNN_VRS_GUNN:
			case TEAM_SWORD_VRS_SWORD:
			case TEAM_GUNN_VRS_SWORD:
			case TEAM_BUILD_BATTLE:
				return true;
			default:
				return false;
		}
	}
	
	/**
	 * Check if this {@link GameType} can have a event mode
	 * 
	 * @return True if this {@link GameType} has a event mode
	 * 
	 * @since 1.0.0
	 */
	
	@Default(Boolean = false)
	public boolean hasEventMode() {
		switch (this) {
			case FREE_FOR_ALL_GUN:
			case FREE_FOR_ALL_SWORD:
			case HOTPOTATO:
			case HOTPOTATO_DOUBLE:
			case OTIC:
			case LAVAFLOOR:
			case HIDE_AND_SEEK:
				return true;
			default:
				return false;
		}
	}
	
	/**
	 * Check if this {@link GameType} is a special event {@link GameType}
	 * only
	 * 
	 * @return True if this {@link GameType} is {@link #HALLOWEEN} or
	 * {@link #CHRISTMAS}
	 * 
	 * @since 1.0.0
	 */
	
	@Default(Boolean = false)
	public boolean isSpecialEvent() {
		switch (this) {
			case HALLOWEEN:
			case CHRISTMAS:
				return true;
			default:
				return false;
		}
	}
}