package tk.mythicaldimensions.essentials.utility;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

public final class Gun {

	// Pistol
	public static final Material PISTOL = Material.WOODEN_HOE;
	
	// SMG
	public static final Material SMG = Material.STONE_HOE;
	
	// Assualt
	public static final Material ASSUALT = Material.IRON_HOE;
	
	// LMG
	public static final Material LMG = Material.GOLDEN_HOE;
	
	// Heavy Pistol
	public static final Material HEAVY_PISTOL = Material.DIAMOND_HOE;
	
	// Pistol ammo
	public static final Material PISTOL_AMMO = Material.RED_DYE;

	// SMG ammo
	public static final Material SMG_AMMO = Material.BLUE_DYE;

	// Assualt rifle ammo
	public static final Material ASSUALT_AMMO = Material.GREEN_DYE;

	// LMG ammo
	public static final Material LMG_AMMO = Material.YELLOW_DYE;

	// Heavy pistol ammo
	public static final Material HEAVY_PISTOL_AMMO = Material.PURPLE_DYE;
	/**
	 * Stop people from creating instances of this class as this is supposed to be a
	 * class for guns
	 */
	private Gun() {
	}

	/**
	 * Use Gun
	 * 
	 * @param playerName
	 */
	public static void useGun(String playerName) {
		Player shooter = Bukkit.getPlayer(playerName);
		int ifShoot = 0;
		double multi = 0;
		
		// Check for Pistol
		if (!shooter.hasCooldown(PISTOL) && shooter.getInventory().getItemInMainHand().getType() == PISTOL) {
			// Check if theres ammo
			if (shooter.getInventory().contains(PISTOL_AMMO)) {
				// Shoot/Set power
				shooter.setCooldown(PISTOL, 15);
				ifShoot = 1;
				multi = 1.25;
				
				// Check if in creative
				if (shooter.getGameMode() != GameMode.CREATIVE) {
					// Remove one ammo
					int slot = Inventory.findItem(playerName, PISTOL_AMMO);
					Inventory.subtractByValueSlot(playerName, slot, 1);
				} 
			} else {ifShoot = 2;}
		} else
		// Check for SMG
		if (!shooter.hasCooldown(SMG) && shooter.getInventory().getItemInMainHand().getType() == SMG) {
			// Check if theres ammo
			if (shooter.getInventory().contains(SMG_AMMO)) {
				// Shoot/Set power
				shooter.setCooldown(SMG, 1);
				ifShoot = 1;
				multi = 1.5;
				
				// Check if in creative
				if (shooter.getGameMode() != GameMode.CREATIVE) {
					// Remove one ammo
					int slot = Inventory.findItem(playerName, SMG_AMMO);
					Inventory.subtractByValueSlot(playerName, slot, 1);
				} 
			} else {ifShoot = 2;}
		} else
		// Check for Assualt Rifle
		if (!shooter.hasCooldown(ASSUALT) && shooter.getInventory().getItemInMainHand().getType() == ASSUALT) {
			// Check if theres ammo
			if (shooter.getInventory().contains(ASSUALT_AMMO)) {
				// Shoot/Set power
				shooter.setCooldown(ASSUALT, 2);
				ifShoot = 1;
				multi = 1.75; 
				
				// Check if in creative
				if (shooter.getGameMode() != GameMode.CREATIVE) {
					// Remove one ammo
					int slot = Inventory.findItem(playerName, ASSUALT_AMMO);
					Inventory.subtractByValueSlot(playerName, slot, 1);
				} 
			} else {ifShoot = 2;}
		} else
		// Check for LMG Rifle
		if (!shooter.hasCooldown(LMG) && shooter.getInventory().getItemInMainHand().getType() == LMG) {
			// Check if theres ammo
			if (shooter.getInventory().contains(LMG_AMMO)) {
				// Shoot/Set power
				shooter.setCooldown(LMG, 1);
				ifShoot = 1;
				multi = 1.60; 
				
				// Check if in creative
				if (shooter.getGameMode() != GameMode.CREATIVE) {
					// Remove one ammo
					int slot = Inventory.findItem(playerName, LMG_AMMO);
					Inventory.subtractByValueSlot(playerName, slot, 1);				} 
			} else {ifShoot = 2;}
		} else
		// Check for Heavy Pistol	
		if (!shooter.hasCooldown(HEAVY_PISTOL) && shooter.getInventory().getItemInMainHand().getType() == HEAVY_PISTOL) {
			// Check if theres ammo
			if (shooter.getInventory().contains(HEAVY_PISTOL_AMMO)) {
				// Shoot/Set power
				shooter.setCooldown(HEAVY_PISTOL, 25);
				ifShoot = 1;
				multi = 2; 
				
				// Check if in creative
				if (shooter.getGameMode() != GameMode.CREATIVE) {
					// Remove one ammo
					int slot = Inventory.findItem(playerName, HEAVY_PISTOL_AMMO);
					Inventory.subtractByValueSlot(playerName, slot, 1);
				} 
			} else {ifShoot = 2;}
		}
		
		// Finally
		if (ifShoot == 1) {
			// Shoot/Player Shoot
			shootBullet(playerName, multi);
			shooter.playSound(shooter.getLocation(), Sound.BLOCK_METAL_BREAK, 1, 1);
		} else if (ifShoot == 2) {
			shooter.playSound(shooter.getLocation(), Sound.BLOCK_METAL_PRESSURE_PLATE_CLICK_ON, 1, 1);
		}
	}

	/**
	 * Shoot Projectile
	 * 
	 * @param playerName
	 */
	public static void shootBullet(String playerName, double multiplier) {
		Player shooter = Bukkit.getPlayer(playerName);
		ProjectileSource projectileSource = shooter;
		double vecX = (shooter.getTargetBlock(null, 100).getLocation().getX() + 0.5) - shooter.getLocation().getX();
		double vecY = ((shooter.getTargetBlock(null, 100).getLocation().getY() + 0.5) - shooter.getLocation().getY()) - 2;
		double vecZ = (shooter.getTargetBlock(null, 100).getLocation().getZ() + 0.5) - shooter.getLocation().getZ();

		// Shoot
		projectileSource.launchProjectile(Snowball.class,
				new Vector(vecX, vecY, vecZ).normalize().multiply(multiplier));
		
	}
}
