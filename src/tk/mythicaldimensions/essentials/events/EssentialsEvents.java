package tk.mythicaldimensions.essentials.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import tk.mythicaldimensions.essentials.utility.Npc;
import tk.mythicaldimensions.essentials.utility.PacketReader;

/**
 * A listener class containing all events for our plugin Currently there is only
 * one event, however we could easily add more
 * 
 * @author [Empty]
 */
public class EssentialsEvents implements Listener {
	// Show NPCs
	@EventHandler
	public void onServerJoinEvent(PlayerJoinEvent event) {
		// Check if null
		if (Npc.getNPCs() == null)
			return;
		if (Npc.getNPCs().isEmpty())
			return;
		Npc.addJoinPacket(event.getPlayer());
			
		// Inject Packet reader
		PacketReader reader = new PacketReader();
		reader.inject(event.getPlayer());
	}
	
	// Server Leave
	@EventHandler
	public void onServerLeaveEvent(PlayerQuitEvent event) {	
		// UnInject Packet Reader
		PacketReader reader = new PacketReader();
		reader.uninject(event.getPlayer());
			
	}
}
