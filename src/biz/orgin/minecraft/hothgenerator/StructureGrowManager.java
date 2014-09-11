package biz.orgin.minecraft.hothgenerator;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.StructureGrowEvent;

/**
 * Prevent trees and mushrooms from growing under open sky in hoth and on unwatered blocks in tatooine.
 * Makes regular trees spawn dagobah trees in dagobah.
 * @author orgin
 *
 */
public class StructureGrowManager implements Listener
{

	private HothGeneratorPlugin plugin;

	public StructureGrowManager(HothGeneratorPlugin plugin)
	{
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onStructureGrow(StructureGrowEvent event)
	{
		if(!event.isCancelled())
		{
			World world = event.getWorld();
			
			Location location = event.getLocation();
			
			
			if(this.plugin.isHothWorld(world))
			{
				String worldtype = this.plugin.getWorldType(world);

				if(!ConfigManager.isRulesPlantsgrow(this.plugin, location) && (worldtype.equals("hoth") || worldtype.equals("tatooine")))
				{
					int maxy = world.getHighestBlockYAt(location);
					
					if(Math.abs(maxy-location.getBlockY())<2)
					{
						if(worldtype.equals("hoth") || (worldtype.equals("tatooine") && !HothUtils.isWatered(location.getBlock().getRelative(BlockFace.DOWN))))
						{
							event.setCancelled(true);
						}
					}
				}
				
				if(!event.isCancelled() && this.plugin.getWorldType(world).equals("dagobah"))
				{
					TreeType type = event.getSpecies();
					
					// Prevent regular trees from generating
					if(type.equals(TreeType.TREE) || type.equals(TreeType.BIG_TREE))
					{
						int x = location.getBlockX();
						int y = location.getBlockY();
						int z = location.getBlockZ();

						plugin.addTask(new PlaceDagobahTree(world, x, y, z), true);
	
						// Always cancel the event
						event.setCancelled(true);
					}
				}
			}
		}
	}
	
	private class PlaceDagobahTree extends HothRunnable
	{
		private static final long serialVersionUID = 2967615175022427407L;
		private int x;
		private int y;
		private int z;
		
		@Override
		public String getParameterString() {
			return "x="+x+",y="+y+"z="+z;
		}

		public PlaceDagobahTree(World world, int x, int y, int z)
		{
			this.setName("PlaceDagobahTree");
			this.setWorld(world);
			this.setPlugin(null);
			this.x = x;
			this.y = y;
			this.z = z;
		}

		@Override
		public void run()
		{
			World world = this.getWorld();
			HothGeneratorPlugin plugin = this.getPlugin();

			Random random = new Random(System.currentTimeMillis());
			DagobahSmallTreePopulator dagobahSmallTreePopulator = new DagobahSmallTreePopulator(plugin.getHeight());

			Block block = world.getBlockAt(this.x, this.y, this.z);
			block = block.getRelative(BlockFace.DOWN);
			int j=0;
			while(block.isEmpty() && j<3)
			{
				block = block.getRelative(BlockFace.DOWN);
				j++;
			}
			
			if(j<3)
			{
				// Attempt to place dagobah tree
				if(dagobahSmallTreePopulator.renderTreeAt(world, random, x, block.getY(), z, 0, false))
				{
					// Make sure that we don't leave the sapling around
					block = world.getBlockAt(this.x, this.y, this.z);
					if(block.getType().equals(Material.SAPLING))
					{
						block.setType(Material.AIR);
					}
				}
			}

		}

	}
}
