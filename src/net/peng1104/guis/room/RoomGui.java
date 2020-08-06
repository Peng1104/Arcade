package net.peng1104.guis.room;

import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.peng1104.annotation.NotNull;
import net.peng1104.game.Room;
import net.peng1104.gui.Gui;
import net.peng1104.profiles.Profile;
import net.peng1104.utils.Button;
import net.peng1104.utils.Item;
import net.peng1104.utils.MinecraftAPI;

public class RoomGui extends Gui {
	
	private static final ItemStack PANE = new Item(Material.STAINED_GLASS_PANE, 7)
			.setDisplayName("&a").getItemStack(1);
	
	private final Room room;
	
	public RoomGui(@NotNull Room room) {
		super("Administar Sala");
		
		if (room == null) {
			throw new IllegalArgumentException("Room cannot be null");
		}
		this.room = room;
	}
	
	@Override
	public void openGUI(Profile profile) {
		if (room.isInRoom(profile)) {
			Inventory inventory = MinecraftAPI.createInventory(54);
			
			addButton(slot, new Button() {
				
				@Override
				public boolean execute(ClickType clickType, Profile profile) {
					// TODO Auto-generated method stub
					return false;
				}
			});
			
			profile.openInventory(inventory);
		}
	}
}