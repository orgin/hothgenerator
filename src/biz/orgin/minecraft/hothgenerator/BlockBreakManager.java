package biz.orgin.minecraft.hothgenerator;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

/**
 * BlockBreakEvent listener. Makes sure that ice and snow blocks breaks into blocks that the player can pick up. 
 * @author orgin
 *
 */
public class BlockBreakManager implements Listener
{
	private HothGeneratorPlugin plugin;

	public BlockBreakManager(HothGeneratorPlugin plugin)
	{
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockBreak(BlockBreakEvent event)
	{
		if(!event.isCancelled())
		{
			Block block = event.getBlock();
			World world = block.getWorld();
			Player player = event.getPlayer(); 
	
			GameMode gamemode = player.getGameMode();
			
			
			if (!gamemode.equals(GameMode.CREATIVE))
			{
				if (this.plugin.isHothWorld(world) && block.getType().equals(Material.ICE))
				{
					if(this.plugin.isRulesDropice(block.getLocation()))
					{
						block.setType(Material.AIR);
						ItemStack iceStack = new ItemStack(Material.ICE);
						iceStack.setAmount(1);
						block.getWorld().dropItemNaturally(block.getLocation(), iceStack);
						
					}
				}
				else if (this.plugin.isHothWorld(world) && block.getType().equals(Material.SNOW_BLOCK))
				{
					if(this.plugin.isRulesDropsnow(block.getLocation()))
					{
						block.setType(Material.AIR);
						ItemStack snowStack = new ItemStack(Material.SNOW_BLOCK);
						snowStack.setAmount(1);
						block.getWorld().dropItemNaturally(block.getLocation(), snowStack);

					}
				}
			}
		}
	}

}