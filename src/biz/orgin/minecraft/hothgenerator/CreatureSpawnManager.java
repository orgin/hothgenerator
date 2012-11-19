package biz.orgin.minecraft.hothgenerator;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

/**
 * Prevents slime from spawning above the stone layer
 * @author orgin
 *
 */

public class CreatureSpawnManager implements Listener
{
	private HothGeneratorPlugin plugin;

	public CreatureSpawnManager(HothGeneratorPlugin plugin)
	{
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onCreatureSpawn(CreatureSpawnEvent event)
	{
		if(!event.isCancelled())
		{
			Location location = event.getLocation();
			World world = location.getWorld();
			
			if(this.plugin.isHothWorld(world))
			{
				EntityType type = event.getEntityType();
				
				if(type.equals(EntityType.SLIME) && location.getBlockY()>27 &&
						event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.NATURAL))
				{
					event.setCancelled(true);
				}
			}
		}
	}
}
