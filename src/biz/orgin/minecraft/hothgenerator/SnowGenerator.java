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
			try
			{
				//plugin.addTask(new PlaceSnowCover(world, snowcover));
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new PlaceSnowCover(world, snowcover));
			}
			catch(Exception e)
			{
				plugin.logMessage("Exception while trying to schedule PlaceSnowCover task. You probably need to restart the server", true);
				if(!plugin.isEnabled())	System.out.println("The plugin has been disabled. Plugin ID = " + plugin.getID());
			}
		}
	}

	static class PlaceSnowCover extends HothRunnable
	{
		private static final long serialVersionUID = -8662915904475816785L;
		private Position[][] snowcover;
		
		public PlaceSnowCover(World world, Position[][] snowcover)
		{
			this.setName("PlaceSnowCover");
			this.setWorld(world);
			this.snowcover = snowcover;
			this.setPlugin(null);
		}
		
		public String getParameterString() {
			return "";
		}

		@Override
		public void run()
		{	
			World world = this.getWorld();
			
			for(int z=0;z<16;z++)
			{
				for(int x=0;x<16;x++)
				{
					int rx = this.snowcover[z][x].x;
					int ry = this.snowcover[z][x].y;
					int rz = this.snowcover[z][x].z;

					Block block = world.getBlockAt(rx, ry, rz);
					
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
