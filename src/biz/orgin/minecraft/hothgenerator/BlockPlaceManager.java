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
			
	  	  	if(this.plugin.isHothWorld(world))
  	  		{
	  	  		WorldType worldType = this.plugin.getWorldType(world);
	  	  		
	  	  		if(worldType == WorldType.HOTH)
	  	  		{
					if(block.isLiquid() && !this.plugin.canPlaceLiquid(world, block))
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
	  	  		else if(worldType == WorldType.MUSTAFAR)
	  	  		{
					Material type = block.getType();
					if(HothUtils.isTooHot(block.getLocation(), 2))
					{
						if(type.equals(Material.GRASS))
						{
							BlockPlacerThread th = new BlockPlacerThread(world, block.getX(), block.getY(), block.getZ(), Material.GRASS, Material.DIRT);
							plugin.addTask(th);
						}
						else if(type.equals(Material.MYCEL))
						{
							BlockPlacerThread th = new BlockPlacerThread(world, block.getX(), block.getY(), block.getZ(), Material.MYCEL, Material.DIRT);
							plugin.addTask(th);
						}
						else if(type.equals(Material.DOUBLE_PLANT)
								|| type.equals(Material.RED_MUSHROOM)
								|| type.equals(Material.YELLOW_FLOWER)
								|| type.equals(Material.RED_ROSE)
								|| type.equals(Material.BROWN_MUSHROOM)
								|| type.equals(Material.LONG_GRASS)
								|| type.equals(Material.CACTUS)
								|| type.equals(Material.PUMPKIN_STEM)
								|| type.equals(Material.MELON_STEM)
								|| type.equals(Material.SAPLING)
								|| type.equals(Material.VINE)
								|| type.equals(Material.SUGAR_CANE_BLOCK)
								|| type.equals(Material.CROPS)
								)
						{
							event.setCancelled(true);
						}
					}

	  	  		}
			}
		}
	}
}


