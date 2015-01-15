package biz.orgin.minecraft.hothgenerator;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

public class BlockFromToManager implements Listener
{
	private HothGeneratorPlugin plugin;

	public BlockFromToManager(HothGeneratorPlugin plugin)
	{
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockFromTo(BlockFromToEvent event)
	{
		if(!event.isCancelled())
		{
			Block block = event.getBlock();
			World world = block.getWorld();
			
			if(this.plugin.isHothWorld(world) && this.plugin.getWorldType(world) == WorldType.MUSTAFAR)
			{
				Material material = block.getType();
				
				if(material.equals(Material.WATER) || material.equals(Material.STATIONARY_WATER))
				{
					Block to = event.getToBlock();
					if(this.tooHot(to))
					{
						world.playEffect(to.getLocation(), Effect.SMOKE, BlockFace.UP);
						event.setCancelled(true);
					}
				}
			}
		}
	}
	
	private boolean tooHot(Block block)
	{
		World world = block.getWorld();
		int x = block.getX();
		int y = block.getY();
		int z = block.getZ();
		
		for(int xx=x-2;xx<x+2;xx++)
		{
			for(int zz=z-2;zz<z+2;zz++)
			{
				for(int yy=y-2;yy<y+2;yy++)
				{
					Block blck = world.getBlockAt(xx, yy, zz);

					Material mat = blck.getType();
					
					if(mat.equals(Material.LAVA) || mat.equals(Material.STATIONARY_LAVA))
					{
						return true;
					}
				}
			}
		}
		
		return false;
	}

}