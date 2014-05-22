package biz.orgin.minecraft.hothgenerator;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Slime;
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

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onCreatureSpawn(CreatureSpawnEvent event)
	{
		if(!event.isCancelled())
		{
			Location location = event.getLocation();
			World world = location.getWorld();
			
			if(this.plugin.isHothWorld(world) && this.plugin.isRulesLimitslime(location))
			{
				int surfaceOffset = this.plugin.getWorldSurfaceoffset(world);
				
				LivingEntity entity = event.getEntity();
				
				if(entity instanceof Slime && location.getBlockY()>(27 + surfaceOffset) &&
						event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.NATURAL))
				{
					event.setCancelled(true);
				}
			}
		}
	}
}
