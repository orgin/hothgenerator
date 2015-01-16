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
							Entity mite = world.spawnEntity(location, EntityType.ENDERMITE);
							Endermite m = (Endermite)mite;
							if(this.random.nextInt(10)==1)
							{
								m.setCustomName("Prime Fire beetle");
								m.setMaxHealth(128.0);
								m.setHealth(128.0);
							}
							else
							{
								m.setCustomName("Fire beetle");
								m.setMaxHealth(32.0);
								m.setHealth(32);
							}
							m.setCustomNameVisible(true);
						}
					}
				}
			}
		}
	}
}
