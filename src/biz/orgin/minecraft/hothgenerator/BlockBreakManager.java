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
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.Plugin;

/**
 * BlockBreakEvent listener. Makes sure that ice and snow blocks breaks into blocks that the player can pick up. 
 * @author orgin
 *
 */
public class BlockBreakManager implements Listener
{
	private HothGeneratorPlugin plugin;
	
	private static Permission EssentialsBuildAll = new Permission("essentials.build.*","",PermissionDefault.TRUE);
	private static Permission EssentialsBuildBreakAll = new Permission("essentials.build.break.*","",PermissionDefault.TRUE);
	private static Permission EssentialsBuildBreakICE = new Permission("essentials.build.break." + Material.ICE.getId(),"",PermissionDefault.TRUE);
	private static Permission EssentialsBuildBreakSNOW_BLOCK = new Permission("essentials.build.break." + Material.SNOW_BLOCK.getId(),"",PermissionDefault.TRUE);

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
			
			// Don't do anything in creative mode
			if (!gamemode.equals(GameMode.CREATIVE))
			{
				boolean gotPermsICE = true;
				boolean gotPermsSNOWBLOCK = true;
				
				// Special Essentials permissions check. For some reason Essentials executes its block break event handling
				// after HothGenerator so the event isn't cancelled properly before executing this method.
				// Thus a special essentials only check had to be added here.
				Plugin tempPlugin = this.plugin.getServer().getPluginManager().getPlugin("Essentials");
				if(tempPlugin!=null)
				{
					//if(!player.hasPermission("essentials.build.break." + Material.ICE.getId()) && !player.hasPermission("essentials.build.break.*"))
					if(!player.hasPermission(BlockBreakManager.EssentialsBuildBreakICE)
						|| !player.hasPermission(BlockBreakManager.EssentialsBuildBreakAll)
						|| !player.hasPermission(BlockBreakManager.EssentialsBuildAll))
					{
						gotPermsICE = false;
					}
					//if(!player.hasPermission("essentials.build.break." + Material.SNOW_BLOCK.getId()) && !player.hasPermission("essentials.build.break.*"))
					if(!player.hasPermission(BlockBreakManager.EssentialsBuildBreakSNOW_BLOCK)
						|| !player.hasPermission(BlockBreakManager.EssentialsBuildBreakAll)
						|| !player.hasPermission(BlockBreakManager.EssentialsBuildAll))
					{
						gotPermsSNOWBLOCK = false;
					}
				}				
				
				// Ice breaking
				if (gotPermsICE && this.plugin.isHothWorld(world) && block.getType().equals(Material.ICE))
				{
					if(this.plugin.isRulesDropice(block.getLocation()))
					{
						block.setType(Material.AIR);
						ItemStack iceStack = new ItemStack(Material.ICE);
						iceStack.setAmount(1);
						block.getWorld().dropItemNaturally(block.getLocation(), iceStack);
						
					}
				}
				// Snow block breaking
				else if (gotPermsSNOWBLOCK && this.plugin.isHothWorld(world) && block.getType().equals(Material.SNOW_BLOCK))
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