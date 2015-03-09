package biz.orgin.minecraft.hothgenerator;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

public class VolcanoManager
{
	
	private HothGeneratorPlugin plugin;
	private int taskId;
	private VolcanoThread volcanoThread;

	private static int[] heights = { 1,  2,  3,  4,  3,  2};

	private static int[][] level0 = { {0,0} };
	private static int[][] level1 = { { 0,-1}, { 1, 0}, { 0, 1}, {-1, 0} };
	private static int[][] level2 = { { 0,-2}, { 1,-1}, { 2, 0}, { 1, 1}, { 0, 2}, {-1, 1}, {-2, 0}, {-1,-1} };
	private static int[][] level3 = { { 0,-2}, { 1,-2}, { 2,-1}, { 2, 0}, { 2, 1}, { 1, 2}, { 0, 2}, {-1, 2}, {-2, 1}, {-2, 0}, {-2,-1}, {-1,-2} };
	private static int[][] level4 = { { 0,-3}, { 1,-3}, { 2,-2}, { 3,-1}, { 3, 0}, { 3, 1}, { 2, 2}, { 1, 3}, { 0, 3}, {-1, 3}, {-2, 2}, {-3, 1}, {-3, 0}, {-3,-1}, {-2,-2}, {-1,-3} };
	private static int[][] level5 = { { 0,-4}, { 1,-4}, { 2,-3}, { 3,-2}, { 4,-1}, { 4, 0}, { 4, 1}, { 3, 2}, { 2, 3}, { 1, 4}, { 0, 4}, {-1, 4}, {-2, 3}, {-3, 2}, {-4,-1}, {-4, 0}, {-4, 1}, {-3,-2}, {-2,-3}, {-1,-4} };
	private static int[][][] levels = {level0, level1, level2, level3, level4, level5};

	public VolcanoManager(HothGeneratorPlugin plugin)
	{
		this.plugin = plugin;
		
		this.plugin.debugMessage("Initializing VolcanoManager. Starting repeating task.");
		
		int period = ConfigManager.getRulesEnvironmentPeriod(this.plugin);
		
		this.taskId = -1;
		if(period>0)
		{
			this.volcanoThread = new VolcanoThread(this.plugin);
			this.taskId = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, this.volcanoThread, 10, period * 5);
		}

	}
	
	/**
	 * Stop the scheduled repeating task.
	 * A new instance of the VolcanoManager must be made to restart a new task.
	 * Use this to shut down the currently running volcano task before a reload.
	 */
	public void stop()
	{
		if(this.taskId!=-1)
		{
			this.volcanoThread.stopVolcanoes();
			Bukkit.getServer().getScheduler().cancelTask(this.taskId);
		}
	}

	
	private class VolcanoThread implements Runnable
	{
		private HothGeneratorPlugin plugin;
		private Vector<Volcano> volcanoes;
		private Random random;
		
		public VolcanoThread(HothGeneratorPlugin plugin)
		{
			this.plugin = plugin;
			this.random = new Random(System.currentTimeMillis());
			this.volcanoes = new Vector<Volcano>();
		}
		
		public void run()
		{
			// Find new volcanoes
			if(this.random.nextInt(10)==0)
			{
				Server server = plugin.getServer();
				List<World> worlds = server.getWorlds();
				for(int i=0;i<worlds.size();i++)
				{
					World world = worlds.get(i);
					if(this.plugin.isHothWorld(world) && this.plugin.getWorldType(world) == WorldType.MUSTAFAR)
					{
						List<Player> players = world.getPlayers();
						
						for(int j=0;j<players.size();j++)
						{
							Player player = players.get(j);
							for(int k=0;k<1;k++) // More attempts?
							{
								int x = player.getLocation().getBlockX() + this.random.nextInt(50)-25;
								int z = player.getLocation().getBlockZ() + this.random.nextInt(50)-25;
								
								Block block = world.getHighestBlockAt(x, z);
								
								System.out.println("Attempting to find new volcano at " + x + ", " + z);
								
								Location loc = this.findVolcanoLocation(block);
								
								if(loc!=null)
								{
									System.out.println("Found volcano at " + block.getLocation());
									this.addVolcano(loc);
								}
								else
								{
									System.out.println("Did not find volcano at " + block.getLocation());
								}
							}
						}
					}
				}
			}
			
			Volcano[] vols = this.volcanoes.toArray(new Volcano[this.volcanoes.size()]);
			
			for(int i=0;i<vols.length;i++)
			{
				System.out.println("Executing volcano " + i);
				this.executeVolcano(vols[i]);
			}
		}
		
		private void executeVolcano(Volcano volcano)
		{
			int n=10;
			int level = volcano.getLevel();
			System.out.println("Volcano level: " + level);
			if(volcano.getLevel()>n+6)
			{
				System.out.println("Removing volcano");
				this.removeVolcano(volcano);
			}
			else
			{
				Location location = volcano.getLocation();
				World world = location.getWorld();
				
				int sx = volcano.getLocation().getBlockX();
				int sy = volcano.getLocation().getBlockY();
				int sz = volcano.getLocation().getBlockZ();
				
				if(level>=0 && level<=5)
				{
					int idx = level;
					this.paintLava(world, idx, sx, sy, sz, Material.AIR, Material.LAVA);
				}
				else if(level>=(n+1) && level<=(n+6))
				{
					int idx = level-(n+1);
					this.paintLava(world, idx, sx, sy, sz, Material.LAVA, Material.AIR);
				}
				volcano.setNextLevel();
			}
		}
		
		private void paintLava(World world, int idx, int sx, int sy, int sz, Material from, Material to)
		{
			int[][] poss = levels[idx];
			int y = heights[idx];

			for(int i=0;i<poss.length;i++)
			{
				int[] pos = poss[i];
				int x = pos[0];
				int z = pos[1];
				
				Block block = world.getBlockAt(sx+x, sy+y, sz+z);
				
				if(from.equals(Material.LAVA))
				{ // Lava can be both lava and stationary_lava for some stupid reason
					if(block.getType().equals(Material.LAVA) || block.getType().equals(Material.STATIONARY_LAVA))
					{
						block.setType(to);
					}
				}
				else
				{
					if(block.getType().equals(from))
					{
						block.setType(to);
					}
				}
			}
		}
		
		private Location findVolcanoLocation(Block block)
		{
			boolean found = false;
			
			@SuppressWarnings("unused")
			HashSet<Block> blockList = new HashSet<Block>(); // To prevent checking the same block twice
			
			Block curr = block;
			int ctr = 0;
			
			while(!found)
			{
				System.out.println("findVolcano() iteration: " + ctr);
				System.out.println("Coords: " + curr.getX() + "," + curr.getY() + "," + curr.getZ());
				if(ctr>500) // Stop searching after a while
				{
					return null;
				}
				
				Block up = curr.getRelative(BlockFace.UP);
				if(!up.getType().equals(Material.STONE))
				{
					Block north = curr.getRelative(BlockFace.NORTH);
					if(!north.getType().equals(Material.STONE))
					{
						Block east = curr.getRelative(BlockFace.EAST);
						if(!east.getType().equals(Material.STONE))
						{
							Block south = curr.getRelative(BlockFace.SOUTH);
							if(!south.getType().equals(Material.STONE))
							{
								Block west = curr.getRelative(BlockFace.WEST);
								if(!west.getType().equals(Material.STONE))
								{
									Location loc = curr.getLocation();
									if(loc.getBlockY()>120)
									{
										return loc;
									}
									else
									{
										return null;
									}
								}
								else
								{
									curr = west;
								}
							}
							else
							{
								curr = south;
							}
						}
						else
						{
							curr = east;
						}
					}
					else
					{
						curr = north;
					}
				}
				else
				{
					curr = up;
				}
				
				ctr++;
			}
			
			return null;
		}
		
		public void addVolcano(Location location)
		{
			Volcano volcano = this.getVolcano(location);
			if(volcano==null)
			{
				this.volcanoes.add(new Volcano(location, 0));
			}
		}
		
		public void removeVolcano(Volcano volcano)
		{
			for(int i=0;i<this.volcanoes.size();i++)
			{
				Volcano vol = volcanoes.elementAt(i);
				if(vol.equals(volcano))
				{
					this.volcanoes.removeElementAt(i);
					return;
				}
			}
		}
		
		public Volcano getVolcano(Location location)
		{
			for(int i=0;i<this.volcanoes.size();i++)
			{
				Volcano volcano = this.volcanoes.elementAt(i);
				if(volcano.equals(location))
				{
					return volcano;
				}
			}
			
			return null;
		}
		
		public void stopVolcanoes()
		{
			for(int i=0;i<this.volcanoes.size();i++)
			{
				Volcano volcano = this.volcanoes.elementAt(i);
				Location location = volcano.getLocation();
				World world = location.getWorld();

				int sx = location.getBlockX();
				int sy = location.getBlockY();
				int sz = location.getBlockZ();

				for(int j=0;j<levels.length;j++)
				{
					int y = sy+heights[j];
					int[][] coords = levels[j];
					for(int k=0;k<coords.length;k++)
					{
						int x = sx+coords[k][0];
						int z = sz+coords[k][1];

						Block block = world.getBlockAt(x,y,z);
						if(block.getType().equals(Material.LAVA) || block.getType().equals(Material.STATIONARY_LAVA))
						{
							block.setType(Material.AIR);
						}
					}
				}
			}

			this.volcanoes.clear();
		}
		
		private class Volcano
		{
			private Location location;
			private int level;
			
			public Volcano(Location location, int level)
			{
				this.location = location;
				this.level = level;
			}
			
			public void setNextLevel()
			{
				this.level++;
			}
			
			public boolean equals(Volcano other)
			{
				Location location = other.getLocation();
				
				return this.equals(location);
			}
			
			public boolean equals(Location other)
			{
				Location l1 = this.location;
				Location l2 = other;
				
				return l1.getWorld().equals(l2.getWorld())
						&& l1.getBlockX()==l2.getBlockX()
						&& l1.getBlockY()==l2.getBlockY()
						&& l1.getBlockZ()==l2.getBlockZ();
			}

			public Location getLocation()
			{
				return this.location;
			}
			
			public int getLevel()
			{
				return this.level;
			}
		}
	}

}
