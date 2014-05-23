package biz.orgin.minecraft.hothgenerator;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockGrowEvent;

/**
 * BlockGrowEvent listener. Cancels any growth events on a hoth and tatooine world that is directly exposed to sunlight.
 * In tatooine things may grow if there's water around the block beneath.
 * @author orgin
 *
 */
public class BlockGrowManager implements Listener
{
	private HothGeneratorPlugin plugin;

	public BlockGrowManager(HothGeneratorPlugin plugin)
	{
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockGrow(BlockGrowEvent event)
	{
		if(!event.isCancelled())
		{
			Block block = event.getBlock();
			World world = block.getWorld();
			String type = this.plugin.getWorldType(world);
			Location location = event.getBlock().getLocation();
			
			if(this.plugin.isHothWorld(world) && !this.plugin.isRulesPlantsgrow(block.getLocation())
					&& (type.equals("hoth") || type.equals("tatooine")))
			{
				int maxy = world.getHighestBlockYAt(location);
	
				if(maxy==block.getY())
				{
					if(type.equals("hoth") || (type.equals("tatooine") && !HothUtils.isWatered(block.getRelative(BlockFace.DOWN))))
					{
						event.setCancelled(true);
						block.breakNaturally();
					}
				}
			}
		}
	}
	
}
