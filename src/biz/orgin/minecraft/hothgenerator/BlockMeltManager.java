package biz.orgin.minecraft.hothgenerator;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFadeEvent;

public class BlockMeltManager implements Listener
{
	HothGeneratorPlugin plugin;
	
	public BlockMeltManager(HothGeneratorPlugin plugin)
	{
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockFade(BlockFadeEvent event)
	{
		Block block = event.getBlock();
		World world = block.getWorld();
		
		if(this.plugin.isHothWorld(world))
		{
			int y = block.getY();
			
			if(y>26)
			{
				Material type = block.getType();
				
				if(type.equals(Material.ICE) ||
				   type.equals(Material.SNOW) ||
				   type.equals(Material.SNOW_BLOCK))
				{
					event.setCancelled(true);
				}
			}
		}
	}
}
