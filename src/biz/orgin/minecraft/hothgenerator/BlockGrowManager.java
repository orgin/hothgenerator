package biz.orgin.minecraft.hothgenerator;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockGrowEvent;

/**
 * BlockGrowEvent listener. Cancels any grow events on a hoth world that is directly exposed to sunlight.
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
			
			if(this.plugin.isHothWorld(world))
			{
				int maxy = this.plugin.getHighestBlockYAt(world, block.getX(), block.getZ());
	
				if(maxy==block.getY())
				{
					event.setCancelled(true);
					block.breakNaturally();
				}
			}
		}
	}
}
