package net.peng1104.guis.room;

import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.peng1104.Arcade;
import net.peng1104.annotation.NotNull;
import net.peng1104.game.Room;
import net.peng1104.gui.Gui;
import net.peng1104.profiles.Profile;
import net.peng1104.utils.Button;
import net.peng1104.utils.Item;
import net.peng1104.utils.MinecraftAPI;

public class GameTypeGui extends Gui {
	
	private static final ItemStack PANE = new Item(Material.STAINED_GLASS_PANE, 7)
			.setDisplayName("&a").getItemStack(1);
	
	private static final Item NORMAL_MODE_ITEM = new Item(Material.CHEST).setDisplayName("&aModos");
	private static final Item EVENT_MODE_ITEM = new Item(Material.ENDER_CHEST)
			.setDisplayName("&cEventos");
	
	private final Room room;
	
	public GameTypeGui(@NotNull Room room) {
		super("Modos de Jogo");
		
		if (room == null) {
			throw new IllegalArgumentException("Room cannot be null");
		}
		this.room = room;
	}
	
	@Override
	public void openGUI(Profile profile) {
		if (room.isInRoom(profile) && (room.isModerator(profile) || room.isOwner(profile))) {
			Inventory inventory = MinecraftAPI.createInventory(27);
			
			addButton(13, new Button(NORMAL_MODE_ITEM) {
				
				@Override
				public boolean execute(ClickType clickType, Profile profile) {
					profile.closeInventory();
					
					if (room.isInRoom(profile) && room.isModerator(profile)) {
						Arcade.getGuiButtonManager().addGui(profile, new NormalGameTypeGui(room));
						return true;
					}
					return false;
				}
			});
			
			addButton(15, new Button(EVENT_MODE_ITEM) {
				
				@Override
				public boolean execute(ClickType clickType, Profile profile) {
					profile.closeInventory();
					
					if (room.isInRoom(profile) && room.isModerator(profile)) {
						Arcade.getGuiButtonManager().addGui(profile, new EventGameTypeGui(room));
						return true;
					}
					return false;
				}
			});
			
			int index;
			
			while ((index = inventory.firstEmpty()) != -1) {
				inventory.setItem(index, PANE);
			}
			profile.openInventory(inventory);
		}
	}
	
}