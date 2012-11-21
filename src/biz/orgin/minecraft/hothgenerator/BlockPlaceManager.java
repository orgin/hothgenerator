package biz.orgin.minecraft.hothgenerator;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * Listens for BlockPlacEvents. Makes sure that water and lava is placed as ice and stone.
 * @author orgin
 *
 */
public class BlockPlaceManager implements Listener
{
	private HothGeneratorPlugin plugin;
	
	public BlockPlaceManager(HothGeneratorPlugin plugin)
	{
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void placeBlock(BlockPlaceEvent event)
	{
		if(!event.isCancelled())
		{
			
			Block block = event.getBlock();
			
			World world = block.getWorld();
			
	  	  	if (this.plugin.isHothWorld(world) && block.isLiquid())
			{
				if(!this.plugin.canPlaceLiquid(world, block))
				{
					Material type = block.getType();
				
					if(type.equals(Material.WATER) ||
						type.equals(Material.WATER_BUCKET))
					{
						BlockPlacerThread th = new BlockPlacerThread(world, block.getX(), block.getY(), block.getZ(), Material.WATER, Material.ICE);
						Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, th);
					}
					else if(type.equals(Material.LAVA) ||
						type.equals(Material.LAVA_BUCKET))
					{
						BlockPlacerThread th = new BlockPlacerThread(world, block.getX(), block.getY(), block.getZ(), Material.LAVA, Material.STONE);
						Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, th);
					}
				}
			}
		}
	}
}


