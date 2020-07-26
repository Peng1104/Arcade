package net.peng1104.storage.files.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.peng1104.Arcade;
import net.peng1104.annotation.Default;
import net.peng1104.storage.Storage;
import net.peng1104.storage.enums.main.ArcadeConfig;

public class ArcadeConfigFile extends MainFileConfiguration {
	
	private final Map<Long, String> deleteMessages;
	
	public ArcadeConfigFile() {
		super(Arcade.getInstance());
		
		deleteMessages = new HashMap<>();
		
		for (String string : getStringListC("Mensagens.Sala Será Apagada")) {
			if (string.contains(", ")) {
				String time = string.substring(0, string.indexOf(", "));
				
				if (isNumber(time)) {
					deleteMessages.put(((Double) Double.valueOf(time)).longValue(), string.substring(string.indexOf(", ") + 2));
				}
			}
		}
	}
	
	@Override
	public void mkDefaults() {
		set("Tempo.Espera", 150);
		set("Tempo.Remoção", 300);
		set("Mensagens.Sala Será Apagada", new ArrayList<>());
	}
	
	@Override
	public void saveToStorage() {
		Storage.store(ArcadeConfig.GAME_WAIT_TIME, getLong("Tempo.Espera"));
		Storage.store(ArcadeConfig.PRIVATE_ROOM_DELETE_TIME, getLong("Tempo.Remoção"));
	}
	
	@Default(value = HashMap.class)
	public Map<Long, String> getDeleteMessages() {
		return deleteMessages;
	}
}