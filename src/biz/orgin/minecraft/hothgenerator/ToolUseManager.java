package biz.orgin.minecraft.hothgenerator;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Listens to player interaction events, specifically using a
 * water or lava bucket so that any placed water or lava can
 * be frozen into ice or stone.
 * Bonemealing exposed blocks are prevented.
 * @author orgin
 *
 */
public class ToolUseManager implements Listener
{
	HothGeneratorPlugin plugin;
	
	public ToolUseManager(HothGeneratorPlugin plugin)
	{
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		if(!event.isCancelled())
		{
			World world = event.getPlayer().getWorld();
	
			Action action = event.getAction();
	
			if(action.equals(Action.RIGHT_CLICK_BLOCK))
			{
	
				Player player = event.getPlayer();
				ItemStack item = player.getItemInHand();
	
				if(plugin.isItemInfoTool() && item.getType().equals(Material.CLAY_BALL))
				{
					Block block = event.getClickedBlock();
					player.sendMessage("Item: name = " + block.getType().name() + " type = " + block.getTypeId() + ", data = " + block.getData());
				}
	
				if(this.plugin.isHothWorld(world))
				{
					if(item.getType().equals(Material.WATER_BUCKET))
					{
						Block block = event.getClickedBlock();
						if(!block.getType().equals(Material.SNOW))
						{
							block = block.getRelative(event.getBlockFace());
						}
	
						if(!this.plugin.canPlaceLiquid(world, block))
						{
							BlockPlacerThread th = new BlockPlacerThread(world, block.getX(), block.getY(), block.getZ(), Material.WATER, Material.ICE);
							Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, th);
						}
					}
					else if(item.getType().equals(Material.LAVA_BUCKET))
					{
						Block block = event.getClickedBlock();
						if(!block.getType().equals(Material.SNOW))
						{
							block = block.getRelative(event.getBlockFace());
						}
	
						if(!this.plugin.canPlaceLiquid(world, block))
						{
							BlockPlacerThread th = new BlockPlacerThread(world, block.getX(), block.getY(), block.getZ(), Material.LAVA, Material.STONE);
							Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, th);
						}
					}
					else if(item.getType().equals(Material.INK_SACK) && item.getDurability() == 15)
					{
						// User is bonemealing something
						Block block = event.getClickedBlock();
						Material type = block.getType();
						
						int maxy = this.plugin.getHighestBlockYAt(world, block.getX(), block.getZ());
						
						if(Math.abs(maxy-block.getY())<2)
						{
							if( type.equals(Material.CARROT) ||
								type.equals(Material.POTATO) ||
								type.equals(Material.PUMPKIN_STEM) ||
								type.equals(Material.MELON_STEM) ||
								type.equals(Material.GRASS) ||
								type.equals(Material.SAPLING) ||
								type.equals(Material.CROPS)	)
							{
								event.setCancelled(true);
							}
						}
	
					}
				}
			}
		}
	}
}
