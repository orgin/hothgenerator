package biz.orgin.minecraft.hothgenerator;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;

public class SnowGenerator
{

	public static void generateSnowCover(Plugin plugin, World world, Position[][] snowcover)
	{
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new PlaceSnowCover(world, snowcover));
	}

	static class PlaceSnowCover implements Runnable
	{
		private final World world;
		private final Position[][] snowcover;

		public PlaceSnowCover(World world, Position[][] snowcover)
		{
			this.world = world;
			this.snowcover = snowcover;
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
						block.setData((byte)this.snowcover[z][x].data, false);
					}
				}
			}
		}
	}
}
