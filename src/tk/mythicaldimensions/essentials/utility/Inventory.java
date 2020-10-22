package tk.mythicaldimensions.essentials.utility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Base64;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import tk.mythicaldimensions.essentials.EssentialsPlugin;

public class Inventory {

	/**
	 * Stop people from creating instances of this class as this is supposed to be a
	 * class for inventories
	 */
	private Inventory() {
	}

	/**
	 * Find item
	 */
	public static int findItem(String playerName, Material query) {
		Player player = Bukkit.getPlayer(playerName);
		int itemIndexedAt = 0;

		// Search for item
		for (ItemStack it : player.getInventory().getContents()) {
			// Check if iterator is equal to query
			if (it != null && it.getType() == query) {
				break;
			} else {
				itemIndexedAt++;
			}
		}
		return itemIndexedAt;
	}

	/**
	 * subtract item by passed value in slot
	 */
	public static void subtractByValueSlot(String playerName, int slot, int value) {
		Player player = Bukkit.getPlayer(playerName);

		// Subtract the ammount
		if (player.getInventory().getItem(slot) != null) {
			// Subtract
			player.getInventory().setItem(slot, new ItemStack(player.getInventory().getItem(slot).getType(),
					(player.getInventory().getItem(slot).getAmount() - value)));
		} else {
			return;
		}
	}

	/**
	 * subtract item by passed value in offHand
	 */
	public static void subtractByValueOffHand(String playerName, int value) {
		Player player = Bukkit.getPlayer(playerName);
		int prevValue = player.getInventory().getItemInOffHand().getAmount();

		// Subtract the ammount
		if (prevValue != 0) {
			// Subtract
			player.getInventory().setItemInOffHand(new ItemStack(player.getInventory().getItemInOffHand().getType(),
					(player.getInventory().getItemInOffHand().getAmount() - value)));
		} else {
			return;
		}
	}
	
	/**
	 * add item by passed value in slot
	 */
	public static void addByValueSlot(String playerName, int slot, int value) {
		Player player = Bukkit.getPlayer(playerName);
		
		// Add
		player.getInventory().setItem(slot, new ItemStack(player.getInventory().getItem(slot).getType(),
				(player.getInventory().getItem(slot).getAmount() + value)));
	}
	
	/**
	 * add item by passed value in mainHand
	 */
	public static void addByValueMainHand(String playerName, int value) {
		Player player = Bukkit.getPlayer(playerName);
		
		// Add
		player.getInventory().setItemInMainHand(new ItemStack(player.getInventory().getItemInMainHand().getType(),
				(player.getInventory().getItemInMainHand().getAmount() + value)));
	}

	/**
	 * Save players inventory
	 */
	public static void saveInventory(PlayerInventory inventory, String kitName) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		try (final BukkitObjectOutputStream data = new BukkitObjectOutputStream(stream)) {
			// Write the size of the inventory
			data.writeInt(inventory.getSize());
			for (int i = 0; i < inventory.getSize(); i++) {
				data.writeObject(inventory.getItem(i));
			}
			data.flush();
			data.close();
			String output = Base64.getEncoder().encodeToString(stream.toByteArray());
			;

			// Write to file
			File folder = new File(EssentialsPlugin.getInstance().getDataFolder() + File.separator + "kits");
			if (!folder.exists())
				folder.mkdir();
			File file = new File(folder + File.separator + kitName + ".txt");
			if (file.createNewFile()) {
				EssentialsPlugin.getInstance().getLogger().info("[Essentials] Kit created: " + file.getName());
				FileWriter myWriter = new FileWriter(file);
				myWriter.write(output);
				myWriter.close();

			} else {
				EssentialsPlugin.getInstance().getLogger().info("[Essentials] kit already exists");
			}
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Load players inventory
	 */
	public static void loadInventory(String playerName, String kitName) {
		try {
			// Cast player
			Player player = Bukkit.getPlayer(playerName);

			// Get file
			File file = new File(EssentialsPlugin.getInstance().getDataFolder() + File.separator + "kits"
					+ File.separator + kitName + ".txt");
			if (file.exists()) {
				EssentialsPlugin.getInstance().getLogger().info("[Essentials] Kit loaded: " + file.getName());

				// Read File
				Scanner myReader = new Scanner(file);
				while (myReader.hasNextLine()) {
					String encodedInventorySet = myReader.nextLine();
					// Get Input stream
					ByteArrayInputStream inputStream = new ByteArrayInputStream(
							Base64.getDecoder().decode(encodedInventorySet));
					BukkitObjectInputStream data = new BukkitObjectInputStream(inputStream);

					// Set data
					final int invSize = data.readInt();
					for (int i = 0; i < invSize; i++) {
						player.getInventory().setItem(i, (ItemStack) data.readObject());
					}
				}
				myReader.close();
			} else {
				EssentialsPlugin.getInstance().getLogger().info("[Essentials] kit doesn't exist");
			}
		} catch (final IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
