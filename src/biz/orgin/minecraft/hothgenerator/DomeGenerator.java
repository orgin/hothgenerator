package biz.orgin.minecraft.hothgenerator;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;

public class DomeGenerator
{
	public static void main(String[] args)
	{
		int radius = 20;
		
		Position centerPos = new Position(0,0,0);
		
		StringBuffer mySB = new StringBuffer();
		
		for(int y = centerPos.y;y<=centerPos.y+radius;y++)
		{
			for(int z =-radius;z<=radius;z++)
			{
				for(int x =-radius;x<=radius;x++)
				{
					Position currPos = new Position(x,y,z);
					
					int dist = (int)Math.ceil(DomeGenerator.distance(centerPos, currPos));
					if(dist==radius)
					{
						mySB.append("#");
					}
					else if(dist<radius)
					{
						mySB.append("-");
					}
					else
					{
						mySB.append(".");
					}
					
				}
				
				System.out.println(mySB);
				mySB.setLength(0);
			}
			
			System.out.println("");
			
		}
	}

	public static double distance(Position pos1, Position pos2)
	{
		double xd = (pos1.x-pos2.x)*(pos1.x-pos2.x);
		double yd = (pos1.y-pos2.y)*(pos1.y-pos2.y);
		double zd = (pos1.z-pos2.z)*(pos1.z-pos2.z);
		
		double result = Math.sqrt(xd + yd + zd);
		
		return result;
	}

	
	public static void generateDome(Plugin plugin, World world, Random random, int chunkX, int chunkZ)
	{
		int doit = random.nextInt(1024);
		if(doit == 536)
		{
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new PlaceDome(plugin, world, random, chunkX, chunkZ));
		}	
	}

	static class PlaceDome implements Runnable
	{
		private final Plugin plugin;
		private final World world;
		private final Random random;
		private final int chunkx;
		private final int chunkz;

		public PlaceDome(Plugin plugin, World world, Random random, int chunkx, int chunkz)
		{
			this.plugin = plugin;
			this.world = world;
			this.random = random;
			this.chunkx = chunkx;
			this.chunkz = chunkz;
		}
		
		private void placeTop(int sx, int sy, int sz)
		{
			this.plugin.getLogger().info("Planting top at " + sx + "," + sy + "," + sz);

			Position centerPos = new Position(sx, sy, sz);
			
			int radius = 1+this.random.nextInt(3);

			for(int y=sy-radius;y<=sy+radius;y++)
			{
				for(int z=sz-radius;z<=sz+radius;z++)
				{
					for(int x=sx-radius;x<=sx+radius;x++)
					{
						Position currPos = new Position(x,y,z);
						int dist = (int)Math.ceil(DomeGenerator.distance(centerPos, currPos));
						if(dist==radius)
						{
							Block block = this.world.getBlockAt(x, y, z);
							Material type = block.getType();
							if(type.equals(Material.AIR))
							{
								block.setType(Material.GLOWSTONE);
							}
						}

					}
				}
			}
		}
		

		@Override
		public void run()
		{	
			int sx = this.chunkx*16 + random.nextInt(16);
			int sz = this.chunkz*16 + random.nextInt(16);
			int sy = 26;
			
			this.plugin.getLogger().info("Placing Dome at " + sx + "," + sy + "," + sz);

			int radius = 46;

			Position centerPos = new Position(sx, sy, sz);

			// First generate a tiny .. well .. not so tiny dome

			for(int y = centerPos.y;y<=centerPos.y+radius;y++)
			{
				for(int z =-radius + centerPos.z;z<=radius + centerPos.z;z++)
				{
					for(int x =-radius + centerPos.x;x<=radius + centerPos.x;x++)
					{
						Position currPos = new Position(x,y,z);

						int dist = (int)Math.ceil(DomeGenerator.distance(centerPos, currPos));

						if(dist==radius) // Dome
						{
							Block block = this.world.getBlockAt(x, y, z);
							Material type = block.getType();
							if(type.equals(Material.ICE)
									|| type.equals(Material.SNOW_BLOCK)
									|| type.equals(Material.SNOW)
									|| type.equals(Material.AIR)
									|| type.equals(Material.LOG)
									|| type.equals(Material.SAND)
									|| type.equals(Material.SANDSTONE)
									|| type.equals(Material.CLAY)
									|| type.equals(Material.GRAVEL))
							{
								block.setType(Material.GLASS);
							}
						}
						else if(dist<radius) // Inside dome
						{
							Block block = this.world.getBlockAt(x, y, z);
							Material type = block.getType();
							if(type.equals(Material.ICE) // Make air
									|| type.equals(Material.SNOW_BLOCK)
									|| type.equals(Material.SNOW)
									|| type.equals(Material.LOG))
							{
								block.setType(Material.AIR);
							}
							else if(type.equals(Material.SAND) // Make dirty floor
									|| type.equals(Material.SANDSTONE)
									|| type.equals(Material.CLAY)
									|| type.equals(Material.GRAVEL))
							{
								int glow = this.random.nextInt(40);
								if(glow==5)
								{
									block.setType(Material.GLOWSTONE);
								}
								else
								{
									block.setType(Material.DIRT);
								}
							}
						}
						else
						{
							// Ignore, outside dome.
						}
					}
				}
			}

			int cnt = this.random.nextInt(20);
			this.plugin.getLogger().info("Tryign to place " + cnt + " plants.");
			for(int i=0;i<20+cnt;i++)
			{
				int ix = sx+random.nextInt(36*2)-36;
				int iy = 26;
				int iz = sz+random.nextInt(36*2)-36;
				
				Position currPos = new Position(ix,iy,iz);
				double dist = DomeGenerator.distance(centerPos, currPos);
				this.plugin.getLogger().info("Testing planting at " + ix + "," + iy + "," + iz + " distance = " + dist);
				if(dist<36)
				{
					int len = 10 + random.nextInt(20);
					
					boolean done = false;
					int j=0;
					
					float growx = 0;
					float growz = 0;
					
					int currx = 0;
					int curry = 0;
					int currz = 0;
					
					this.plugin.getLogger().info("Planting at " + ix + "," + iy + "," + iz);
					while(j<len && !done)
					{
						currx = ix+(int)growx;
						curry = iy+j;
						currz = iz+(int)growz;
						
						Block block = world.getBlockAt(currx, curry, currz);
						Material type = block.getType();
						if(type.equals(Material.GLASS))
						{
							done = true;
						}
						else if(type.equals(Material.AIR))
						{
							block.setType(Material.SPONGE);
						}
						
						j++;
					}
					
					if(!done) // PLace top piece
					{
						this.placeTop(currx, curry, currz);
					}
					
					// Grow stem
				}
			}
			// Place central schematics
		}
	}
}
