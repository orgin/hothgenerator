package biz.orgin.minecraft.hothgenerator;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
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
			WorldType worldType = this.plugin.getWorldType(world);
			Location location = event.getBlock().getLocation();
			
			if(this.plugin.isHothWorld(world))
			{
				if(!ConfigManager.isRulesPlantsgrow(this.plugin, block.getLocation())
					&& (worldType == WorldType.HOTH || worldType == WorldType.TATOOINE))
				{
					int maxy = world.getHighestBlockYAt(location);
		
					if(maxy==block.getY())
					{
						if(worldType == WorldType.HOTH || (worldType == WorldType.TATOOINE && !HothUtils.isWatered(block.getRelative(BlockFace.DOWN))))
						{
							event.setCancelled(true);
							block.breakNaturally();
						}
					}
				}
				else if(worldType == WorldType.MUSTAFAR)
				{
					BlockState state = event.getNewState();
					Material material = state.getType();
					
					boolean toohot = HothUtils.isTooHot(state.getLocation(), 2);
					
					// Check if it's too hot to grow here
					if(toohot)
					{
						if(material.equals(Material.YELLOW_FLOWER) || material.equals(Material.RED_ROSE))
						{	// Flowers to dead bush
							state.setType(Material.DEAD_BUSH);
							state.update(true, false);
							event.setCancelled(true);
						}
						else if(material.equals(Material.LONG_GRASS) || material.equals(Material.DOUBLE_PLANT))
						{	// Don't render long grass
							event.setCancelled(true);
						}
						else
						{	// Everything else just drops
							block.breakNaturally();
							event.setCancelled(true);
						}
					}
				}
				
			}
			
		}
	}
}
