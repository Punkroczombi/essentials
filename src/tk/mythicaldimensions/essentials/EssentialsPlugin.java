package tk.mythicaldimensions.essentials;

/**
 * The main class that extends JavaPlugin This is where we register any event
 * listeners or take any actions that are required on startup or shutdown
 * 
 * @author [Empty]
 */
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import tk.mythicaldimensions.essentials.commands.EssentialsCommands;
import tk.mythicaldimensions.essentials.config.EssentialsConfig;
import tk.mythicaldimensions.essentials.events.EssentialsEvents;
import tk.mythicaldimensions.essentials.utility.EssentialsUtil;
import tk.mythicaldimensions.essentials.utility.PacketReader;

public class EssentialsPlugin extends JavaPlugin {
	
	// Logger instance for logging debug messages and information about what the
	// plugin is doing
	private final Logger logger = Logger.getLogger("Mythical_Essentials");
	
	// Our internal config object for storing the configuration options that server
	private static EssentialsConfig config;

	// An instance of this plugin for easy access
	private static EssentialsPlugin plugin;
	
	// Heart Beat
	public static int beatTime = 0;
	// Start time
	public static long startTime = System.currentTimeMillis();
	
	// Debug
	public static boolean debug = false;
	
	/**
	 * Ran when plugin is enabled Set static instance of this class Register event
	 * listeners Create configuration object Set command executor Register
	 * permissions
	 */
	@Override
	public void onEnable() {
		plugin = this;
		// Inject packet reader
		getLogger().info("[Essentials] Injecting Packet Reader");
		// Inject Packet Reader
		if (!Bukkit.getOnlinePlayers().isEmpty()) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				PacketReader reader = new PacketReader();
				reader.inject(player);
			}
		}
		// Register Events
		getLogger().info("[Essentials] Registering Events");
		Bukkit.getPluginManager().registerEvents(new EssentialsEvents(), this);
		// Getting config
		getLogger().info("[Essentials] Getting Config");
		config = new EssentialsConfig();
		// Register Commands
		getLogger().info("[Essentials] Registering Commands");
		this.getCommand("essentials").setExecutor(new EssentialsCommands());
		// Register Perms
		getLogger().info("[Essentials] Registering Permissions");
		EssentialsUtil.registerPermissions();
		getLogger().info("[Essentials] Plugin Enabled");
		
		/**
		 * Ran constantly aslong the plugin is registered Controls all plugin logic
		 */
		BukkitScheduler scheduler = getServer().getScheduler();
		scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {
				/** 
				 * Timing
				 */
				long elapsedTime = System.currentTimeMillis() - startTime;
				long elapsedSeconds = elapsedTime / 1000;
				beatTime = (int) elapsedSeconds;
				
				/**
				 *  Debug scripts
				 */
				if (elapsedSeconds % 60 == 0 && debug == true) {
					// Heart Beat
					getLogger().info("[SCPSL] Beep Boop Uptime: [" + elapsedSeconds + "] s");
				}
				
			}
		}, 0L, 20L);
	}
	
	/**
	 * Ran when plugin is disabled Remove permissions to clean
	 * up in case plugin is added again before server restart
	 */
	@Override
	public void onDisable() {
		getLogger().info("[Essentials] Uninjecting Packer reader");
		// UnInject Packet Reader
		for (Player player : Bukkit.getOnlinePlayers()) {
			PacketReader reader = new PacketReader();
			reader.uninject(player);
		}
		getLogger().info("[Essentials] Plugin Disabled");
	}
	/**
	 * Gets the logger for this plugin
	 */
	@Override
	public Logger getLogger() {
		return logger;
	}

	/**
	 * Gets an instance of this plugin
	 * 
	 * @return The static instance of this plugin
	 */
	public static EssentialsPlugin getInstance() {
		return plugin;
	}

	/**
	 * Gets the internal config
	 * 
	 * @return The internal config
	 */
	public static EssentialsConfig getInternalConfig() {
		return config;
	}
}
