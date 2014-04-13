package biz.orgin.minecraft.hothgenerator;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

/*
 * Makes the snow cover made by HothGenerator into a smooth one.
 */
public class SnowGenerator
{

	public static void generateSnowCover(HothGeneratorPlugin plugin, World world, Position[][] snowcover)
	{
		if(plugin.isSmoothSnow())
		{
			//plugin.addTask(new PlaceSnowCover(world, snowcover));
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new PlaceSnowCover(world, snowcover));
		}
	}

	static class PlaceSnowCover implements HothRunnable
	{
		private final World world;
		private final Position[][] snowcover;

		public PlaceSnowCover(World world, Position[][] snowcover)
		{
			this.world = world;
			this.snowcover = snowcover;
		}
		
		public String getName() {
			return "PlaceSnowCover";
		}

		public String getParameterString() {
			return "";
		}

		@Override
		public void run()
		{	
			for(int z=0;z<16;z++)
			{
				for(int x=0;x<16;x++)
				{
					int rx = this.snowcover[z][x].x;
					int ry = this.snowcover[z][x].y;
					int rz = this.snowcover[z][x].z;

					Block block = this.world.getBlockAt(rx, ry, rz);
					
					if(block.getType().equals(Material.SNOW))
					{
						byte data = (byte)this.snowcover[z][x].type;
						DataManager.setData(block, data, false);
					}
				}
			}
		}
	}
}
