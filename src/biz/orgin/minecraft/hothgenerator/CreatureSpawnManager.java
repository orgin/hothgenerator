package biz.orgin.minecraft.hothgenerator;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Endermite;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

/**
 * Prevents slime from spawning above the stone layer
 * Spawns Fire Beetles in Mustafar
 * @author orgin
 *
 */

public class CreatureSpawnManager implements Listener
{
	private HothGeneratorPlugin plugin;
	private Random random;

	public CreatureSpawnManager(HothGeneratorPlugin plugin)
	{
		this.plugin = plugin;
		this.random = new Random(System.currentTimeMillis());
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onCreatureSpawn(CreatureSpawnEvent event)
	{
		if(!event.isCancelled())
		{
			Location location = event.getLocation();
			World world = location.getWorld();
			
			if(this.plugin.isHothWorld(world))
			{
				if(ConfigManager.isRulesLimitslime(this.plugin, location))
				{
					int surfaceOffset = ConfigManager.getWorldSurfaceoffset(this.plugin, world);
					
					LivingEntity entity = event.getEntity();
					
					if(entity instanceof Slime && location.getBlockY()>(27 + surfaceOffset) &&
							event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.NATURAL))
					{
						event.setCancelled(true);
					}
				}
				
				// Don't allow naturally spawned mobs in mustafar worlds
				if(this.plugin.getWorldType(world)==WorldType.MUSTAFAR)
				{
					SpawnReason reason = event.getSpawnReason();
					
					if(reason.equals(CreatureSpawnEvent.SpawnReason.NATURAL) || 
							reason.equals(CreatureSpawnEvent.SpawnReason.MOUNT))
					{
						event.setCancelled(true);
						
						Block block = event.getLocation().getBlock().getRelative(BlockFace.DOWN);
						
						if(HothUtils.isTooHot(location, 2) && block.getType().equals(Material.STONE))
						{
							int rnd = this.random.nextInt(10);
							if(rnd==1)
							{
								CreatureSpawnManager.spawnFireBeetle(location, FireBeetleType.PRIME);
							}
							else if(rnd<5)
							{
								CreatureSpawnManager.spawnFireBeetle(location, FireBeetleType.REGULAR);
							}
							else
							{
								CreatureSpawnManager.spawnFireBeetle(location, FireBeetleType.HATCHLING);
							}
						}
					}
				}
			}
		}
	}
	
	public static Endermite spawnFireBeetle(Location location, FireBeetleType fireBeetleType)
	{
		World world = location.getWorld();
		Entity mite = world.spawnEntity(location, EntityType.ENDERMITE);
		Endermite m = (Endermite)mite;
		
		switch(fireBeetleType)
		{
		case PRIME:
			m.setCustomName(FireBeetleType.PRIME.getName());
			m.setMaxHealth(128.0);
			m.setHealth(128.0);
			break;
		case REGULAR:
			m.setCustomName(FireBeetleType.REGULAR.getName());
			m.setMaxHealth(32.0);
			m.setHealth(32);
			break;
		case HATCHLING:
			m.setCustomName(FireBeetleType.HATCHLING.getName());
			m.setMaxHealth(8.0);
			m.setHealth(8);
			break;
		}

		m.setCustomNameVisible(true);

		return m;
	}
	
	public static FireBeetleType getFireBeetleType(String type)
	{
		 for(FireBeetleType t : FireBeetleType.values())
		 {
			 if(t.name.equals(type))
			 {
				 return t;
			 }
		 }
		 
		 return null;
	}
	
	public enum FireBeetleType {
		 PRIME ("Prime fire beetle"),
		 REGULAR ("Fire beetle"),
		 HATCHLING ("Fire beetle hatchling");
		 
		 private final String name;
		 
		 private FireBeetleType(String name)
		 {
			 this.name = name;
		 }
		 
		 public String getName()
		 {
			 return this.name;
		 }
		 
		 public String toString()
		 {
			 return this.name;
		 }
	}
}
