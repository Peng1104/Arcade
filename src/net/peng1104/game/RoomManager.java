package net.peng1104.game;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.peng1104.annotation.NotNull;
import net.peng1104.annotation.Nullable;
import net.peng1104.profiles.Profile;
import net.peng1104.storage.game.enums.GameType;

public class RoomManager {
	
	private int idCounter = 0;
	
	private Map<Integer, Room> rooms;
	
	public RoomManager() {
		rooms = new HashMap<>();
	}
	
	@Nullable
	public Room createRoom(@NotNull GameType gameType) {
		return createRoom(gameType, "");
	}
	
	@Nullable
	public Room createRoom(@NotNull GameType gameType, @NotNull String password) {
		if (gameType != null && Room.isValidPassword(password)) {
			int id = idCounter++;
			
			Room room = new Room(id, gameType, password);
			
			rooms.put(id, room);
			return room;
		}
		return null;
	}
	
	@Nullable
	public Room createRoom(@NotNull Profile owner, @NotNull String password) {
		return owner == null ? null : createRoom(owner.getUUID(), password);
	}
	
	@Nullable
	public Room createRoom(@NotNull UUID uuid, @NotNull String password) {
		if (uuid != null && Room.isValidPassword(password)) {
			int id = idCounter++;
			
			Room room = new Room(id, GameType.MURDER, uuid, password);
			
			rooms.put(id, room);
			return room;
		}
		return null;
	}
}