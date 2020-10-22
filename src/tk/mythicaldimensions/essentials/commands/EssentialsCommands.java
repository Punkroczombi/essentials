package tk.mythicaldimensions.essentials.commands;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tk.mythicaldimensions.essentials.EssentialsPlugin;
import tk.mythicaldimensions.essentials.utility.EssentialsUtil;
import tk.mythicaldimensions.essentials.utility.Inventory;

/**
 * A command executor class that is used whenever the Essentials command is run
 * It's best practice to have each command in its own class implementing
 * CommandExecutor It makes our code much more clean and ensures that our
 * onCommand() will only be executed for the command this executor is registered
 * to
 * 
 * @author [Empty]
 */
public class EssentialsCommands implements CommandExecutor {

	/**
	 * Executed when Essentials command is run CommandSender will generally be a Player,
	 * Command Block, or Console but we should always check before doing an action
	 * that not all of them support Command will always be the command that this
	 * executor is registered to: In this case essentials label is in the case that an
	 * alias is used instead of Essentials We don't really need to worry about this but
	 * be aware that it might not be the same as the name of the command args is an
	 * array of all other arguments entered, we always want to check the length of
	 * args in case there aren't as many as you would expect!
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		boolean commandReturn = false;
		
		// Check for 3 args
		if (args.length == 3) {
			// Check for savekit
			if (args[0].equalsIgnoreCase("savekit")) {
				// Check for perms
				if (sender.hasPermission(EssentialsUtil.ESSENTIALS_RELOAD_PERM)) {
					// Check if passed arg is null
					if (Bukkit.getPlayer(args[1]) != null && args[2] != null) {
						// Cast the passed arg as a Player object
						Player player = Bukkit.getPlayer(args[1]);
						
						// Save the inventory
						Inventory.saveInventory(player.getInventory(), args[2]);
						
						// Return true
						commandReturn = true;
					} else {
						// Send a usage message
						sender.sendMessage(
								ChatColor.translateAlternateColorCodes('&', "&F/essentials &6savekit &F<name> <kit name>"));

						// Return false
						commandReturn = false;
					}
				}
			} else if (args[0].equalsIgnoreCase("loadkit")) {
				// Check for perms
				if (sender.hasPermission(EssentialsUtil.ESSENTIALS_RELOAD_PERM)) {
					// Check if passed arg is null
					if (Bukkit.getPlayer(args[1]) != null && args[2] != null) {
						// Cast the passed arg as a Player object
						Player player = Bukkit.getPlayer(args[1]);
						
						// Load the inventory
						Inventory.loadInventory(player.getDisplayName(), args[2]);
						
						// Return true
						commandReturn = true;
					} else {
						// Send a usage message
						sender.sendMessage(
								ChatColor.translateAlternateColorCodes('&', "&F/essentials &6loadkit &F<name> &F<kit name>"));

						// Return false
						commandReturn = false;
					}
				}
			} 
		// Check for 2 args
		} else if (args.length == 2) {
			if (args[0].equalsIgnoreCase("delkit")) {
				// Check for perms
				if (sender.hasPermission(EssentialsUtil.ESSENTIALS_RELOAD_PERM)) {
					// Check if passed arg is null
					if (args[1] != null) {
						
						// Delete the kit
						File file = new File(EssentialsPlugin.getInstance().getDataFolder() + File.separator + "kits"
								+ File.separator + args[1] + ".txt"); 
					    if (file.delete()) { 
					    	EssentialsPlugin.getInstance().getLogger().info("Deleted the kit: " + file.getName());
					    } else {
					    	EssentialsPlugin.getInstance().getLogger().info("Failed to delete the kit");
					    } 
						
						// Return true
						commandReturn = true;
					} else {
						// Send a usage message
						sender.sendMessage(
								ChatColor.translateAlternateColorCodes('&', "&F/essentials &6delkit &F<kit name>"));

						// Return false
						commandReturn = false;
					}
				}
			}
		// Check for 1 args
		} else if (args.length == 1) {
			// Check for reload
			if (args[0].equalsIgnoreCase("reload")) {
				// Check for perms
				if (sender.hasPermission(EssentialsUtil.ESSENTIALS_RELOAD_PERM)) {
					// Reload internal config
					EssentialsPlugin.getInternalConfig().reloadConfig();
							
					// Send reload message
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
							EssentialsPlugin.getInternalConfig().getConfigReloadedMessage()));

					// Return true
					commandReturn = true;

					// Sender doesn't have perm
					} else {
					// Send a no Perm message
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
							EssentialsPlugin.getInternalConfig().getNoPermMessage()));

					// Return false
					commandReturn = false;
					}
						
			// Check for help
			} else if (args[0].equalsIgnoreCase("help")) {
				// Send help message
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&F/essentials &6savekit &F<name> <kit name>" + "\n&F/essentials &6loadkit &F<name> &F<kit name>" 
						+ "\n&F/essentials &6delkit &F<kit name>" + "\n&F/essentials &6reload" + "\n&F/essentials &6debug" + "\n&F/essentials &6help"));
				
				// Return true
				commandReturn = true;
					
			// Check for debug
			} else if (args[0].equalsIgnoreCase("debug")) {
				// Check for perms
				if (sender.hasPermission(EssentialsUtil.ESSENTIALS_RELOAD_PERM)) {
					// check the state of debug
					if (EssentialsPlugin.debug == true) {
						// Send message to sender and set false
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"Debug is &6Disabled"));
						EssentialsPlugin.debug = false;
						} else {
						// Send message to sender and set true
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"Debug is &6Enabled"));
						EssentialsPlugin.debug = true;
						}
							
				// Return true
				commandReturn = true;
				}
		
				return commandReturn;
			} else {
				// Send invalid
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
						EssentialsPlugin.getInternalConfig().getCommandInvalidMessage()));

				// Return false
				commandReturn = false;
			}
		
		} else {
			// Return invalid message
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
					EssentialsPlugin.getInternalConfig().getCommandInvalidMessage()));

			// Return false
			commandReturn = false;
		}
		return commandReturn;
	}
}