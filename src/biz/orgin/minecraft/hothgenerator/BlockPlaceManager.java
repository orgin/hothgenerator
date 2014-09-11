package biz.orgin.minecraft.hothgenerator;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * Listens for BlockPlaceEvents. Makes sure that water and lava is placed as ice and stone.
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
			
	  	  	if (this.plugin.isHothWorld(world) && block.isLiquid() && this.plugin.getWorldType(world).equals("hoth"))
			{
				if(!this.plugin.canPlaceLiquid(world, block))
				{
					Material type = block.getType();
				
					if(type.equals(Material.WATER) ||
						type.equals(Material.WATER_BUCKET))
					{
						if(ConfigManager.isRulesFreezewater(this.plugin, block.getLocation()))
						{
							BlockPlacerThread th = new BlockPlacerThread(world, block.getX(), block.getY(), block.getZ(), Material.WATER, Material.ICE);
							plugin.addTask(th);
						}
					}
					else if(type.equals(Material.LAVA) ||
						type.equals(Material.LAVA_BUCKET))
					{
						if(ConfigManager.isRulesFreezelava(this.plugin, block.getLocation()))
						{
							BlockPlacerThread th = new BlockPlacerThread(world, block.getX(), block.getY(), block.getZ(), Material.LAVA, Material.STONE);
							plugin.addTask(th);
						}
					}
				}
			}
		}
	}
}


