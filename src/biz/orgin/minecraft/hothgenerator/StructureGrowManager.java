package biz.orgin.minecraft.hothgenerator;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.StructureGrowEvent;

/**
 * Prevent trees and mushrooms from growing under open sky
 * @author orgin
 *
 */
public class StructureGrowManager implements Listener
{

	private HothGeneratorPlugin plugin;

	public StructureGrowManager(HothGeneratorPlugin plugin)
	{
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onStructureGrow(StructureGrowEvent event)
	{
		if(!event.isCancelled())
		{
			World world = event.getWorld();
			
			Location location = event.getLocation();
			
			if(this.plugin.isHothWorld(world) && !this.plugin.isRulesPlantsgrow(location))
			{
				int maxy = this.plugin.getHighestBlockYAt(world, location.getBlockX(), location.getBlockZ());
	
				if(Math.abs(maxy-event.getLocation().getY())<2)
				{
					event.setCancelled(true);
				}
			}
		}
	}
}
