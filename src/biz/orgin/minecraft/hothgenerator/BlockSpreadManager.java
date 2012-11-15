package biz.orgin.minecraft.hothgenerator;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockSpreadEvent;

/**
 * Prevents grass and mycelium that is exposed to the sky from spreading.
 * Source block will "die" when trying to spread.
 * @author orgin
 *
 */
public class BlockSpreadManager implements Listener
{
	private HothGeneratorPlugin plugin;

	public BlockSpreadManager(HothGeneratorPlugin plugin)
	{
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onBlockSpread(BlockSpreadEvent event)
	{
		Block block = event.getSource();
		World world = block.getWorld();
		
		if(this.plugin.isHothWorld(world))
		{
			Block block2 = event.getBlock(); // destination.
			int maxy = world.getHighestBlockYAt(block.getX(), block.getZ());
			int maxy2 = world.getHighestBlockYAt(block2.getX(), block2.getZ());
			Material type = block.getType();

			if(type.equals(Material.GRASS) || type.equals(Material.MYCEL))
			{
				// Check both source block and destination
				boolean exposed = Math.abs(maxy-block.getY())<2;
				boolean exposed2 = Math.abs(maxy2-block2.getY())<2;
				if(exposed2) // The destinaton is exposed. Cancel spreading
				{
					event.setCancelled(true);
				}

				if(exposed) // The source was exposed. Kill it. But allow spreading if not already canceled.
				{
					block.setType(Material.DIRT);
				}
				
				
			}
		}
	}
}
