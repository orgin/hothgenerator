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
 * water och lava bucket so that any placed water or lava can
 * be frozen into ice or stone.
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
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		World world = event.getPlayer().getWorld();
		
		if(this.plugin.isHothWorld(world))
		{
			Action action = event.getAction();
			
			if(action.equals(Action.RIGHT_CLICK_BLOCK))
			{
				Player player = event.getPlayer();
				
				ItemStack item = player.getItemInHand();
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
			}
		}
	}
}
