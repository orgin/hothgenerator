package biz.orgin.minecraft.hothgenerator;

import java.util.Random;
import java.util.Vector;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.plugin.Plugin;

import biz.orgin.minecraft.hothgenerator.schematic.*;

/**
 * Main class for rendering the undergound mazes. It can be used
 * both as a BlockPopulator and by a ChunkGenerator.
 * 
 * @author orgin
 *
 */
public class RoomPopulator extends BlockPopulator
{
	/**
	 * Maximum number of rooms in a cluster
	 */
	public static int MAXROOMS = 32;
	/**
	 * Minimum number of rooms in a cluster
	 */
	public static int MINROOMS = 8;
	
	private HothGeneratorPlugin plugin = null;
	
	private static Position[] positions = new Position[]
		{
			new Position(0, 6,0), new Position(0,-6,0),
			new Position(0,0,-14), new Position(0,0,14),
			new Position(-14,0,0), new Position(14,0,0)
		};
	
	private static Exit[] exits = new Exit[]
			{
		        //        X  Y  Z
				new Exit( 2, 0, 2, StairsUp.instance),   // up
				new Exit( 2,-4, 2, StairsDown.instance), // down
				new Exit( 2, 0,-6, CorridorNS.instance), // north
				new Exit( 2, 0, 8, CorridorNS.instance), // south
				new Exit(-6, 0, 2, CorridorEW.instance), // west
				new Exit( 8, 0, 2, CorridorEW.instance)  // east
			};

	private static Exit[] doors = new Exit[]
			{
		        //        X  Y  Z
				new Exit( 3, 0, 3, DoorUD.instance), // up
				new Exit( 3,-4, 3, DoorUD.instance), // down
				new Exit( 3,-1, 0, DoorNS.instance), // north
				new Exit( 3,-1, 8, DoorNS.instance), // south
				new Exit( 0,-1, 3, DoorEW.instance), // west
				new Exit( 8,-1, 3, DoorEW.instance)  // east
			};
	
	
	private static int[] reverse = new int[] { 1,0,3,2,5,4 };
	
	private Cluster getNewCluster(Random random)
	{
		return new Cluster(random);
	}
	
	// For testing
	public static void main(String [] args)
	{
		RoomPopulator populator = new RoomPopulator(null);
		Cluster cluster = populator.getNewCluster(new Random(8));
		
		System.out.println("MaxRooms = " + cluster.maxRooms);
		
		System.out.println("Populating ..");
		Room start = populator.getEmptyRoom(cluster, null, 0, 15, 0);
		cluster.rooms = start;

		populator.populateRoom(cluster, start);
		populator.decorateRoom(cluster, start);
		
		System.out.println("Printing ..");
		
		populator.print(cluster);
	}
	
	/**
	 * Generate an underground maze. This method is intended to be used
	 * as a Populator rather than from the terrain generator.
	 * @param world The world to generate in
	 * @param random A random instance
	 * @param chunk The chunk to originate from
	 */
	@Override
	public void populate(World world, Random random, Chunk chunk)
	{
		int doit = random.nextInt(256);
		if(doit == 39)
		{
			int x = random.nextInt(16) + chunk.getX()*16;
			int y = 11 + random.nextInt(16);
			int z = random.nextInt(16) + chunk.getZ()*16;
			
			Cluster cluster = this.getNewCluster(random);
			Room start = this.getEmptyRoom(cluster, null, x, y, z);
			cluster.rooms = start;
			this.populateRoom(cluster, start); // Populate start room and all children
			this.decorateRoom(cluster, start); // Decorate start room and all children

			// Place the cluster into the world
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new PlaceCluster(this.plugin, world, cluster),5);
		}
	}
	
	/**
	 * Generate an underground maze. This method is intended to be called
	 * from the terrain generator rather than from a Populator.
	 * @param world The world to generate in
	 * @param plugin The plugin used
	 * @param random A random instance
	 * @param chunkx The chunk x coordinate to originate from
	 * @param chunkz The chunk x coordinate to originate from
	 */
	public static void generateRooms(World world, Plugin plugin, Random random, int chunkx, int chunkz)
	{
		int doit = random.nextInt(256);
		if(doit == 39)
		{
			RoomPopulator populator = new RoomPopulator((HothGeneratorPlugin)plugin);

			
			int x = random.nextInt(16) + chunkx*16;
			int y = 11 + random.nextInt(16);
			int z = random.nextInt(16) + chunkz*16;
			
			Cluster cluster = populator.getNewCluster(random);
			Room start = populator.getEmptyRoom(cluster, null, x, y, z);
			cluster.rooms = start;
			populator.populateRoom(cluster, start); // Populate start room and all children
			populator.decorateRoom(cluster, start); // Decorate start room and all children

			// Place the cluster into the world
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new PlaceCluster(plugin, world, cluster));	
		}
	}
	
	private static void renderRoom(Plugin plugin, World world, Room room)
	{
		// place room
		RoomPopulator.placeBasicRoom(plugin, world, room);
		// place floor
		RoomPopulator.placeFloor(plugin, world, room);
		// place decor
		RoomPopulator.placeDecor(plugin, world, room);
		// place corridors
		RoomPopulator.placeRoomExits(plugin, world, room);
		// place doors
		RoomPopulator.placeRoomDoors(plugin, world, room);
		// place lights
		
		// Render children
		for(int i=0;i<6;i++)
		{
			Room child = room.children[i];
			if(child!=null && !child.equals(Room.DUMMY))
			{
				RoomPopulator.renderRoom(plugin, world, child);
			}
		}
	}

	private static void placeDecor(Plugin plugin, World world, Room room)
	{
		Schematic schematic = null;
		
		switch(room.decoration)
		{
		case 1: schematic = Decor1.instance; break;
		case 2: schematic = Decor2.instance; break;
		case 3: schematic = Decor3.instance; break;
		case 4: schematic = Decor4.instance; break;
		case 5: schematic = Decor5.instance; break;
		case 6: schematic = Decor6.instance; break;
		case 7: schematic = Decor7.instance; break;
		case 8: schematic = Decor8.instance; break;
		}
		if(schematic!=null)
		{
			HothUtils.placeSchematic(plugin, world, schematic, room.x+3, room.y-1, room.z+3, room.spawner);
		}
	}
	
	private static void placeFloor(Plugin plugin, World world, Room room)
	{
		if(room.floor!=0)
		{
			Schematic schematic = null;
			
			switch(room.floor)
			{
			case 1: schematic = Floor1.instance; break;
			case 2: schematic = Floor2.instance; break;
			case 3: schematic = Floor3.instance; break;
			}
			if(schematic!=null)
			{
				HothUtils.placeSchematic(plugin, world, schematic, room.x+1, room.y-4, room.z+1, room.spawner);
			}
		}
	}
	
	private static void placeBasicRoom(Plugin plugin, World world, Room room)
	{
		HothUtils.placeSchematic(plugin, world, NormalRoom.instance, room.x, room.y, room.z, room.spawner);
	}
	
	private static void placeRoomExits(Plugin plugin, World world, Room room)
	{
		for(int i=0;i<6;i++)
		{
			Room child = room.children[i];
			if(child!=null && (i<2 || !child.equals(Room.DUMMY)))
			{
				int x = RoomPopulator.exits[i].x + room.x;
				int y = RoomPopulator.exits[i].y + room.y;
				int z = RoomPopulator.exits[i].z + room.z;
				Schematic schematic = RoomPopulator.exits[i].schematic;
				
				if(schematic!=null)
				{
					HothUtils.placeSchematic(plugin, world, schematic, x, y, z);
				}
			}
		}
	}

	private static void placeRoomDoors(Plugin plugin, World world, Room room)
	{
		for(int i=0;i<6;i++)
		{
			Room child = room.children[i];
			if(child==null)
			{
				int x = RoomPopulator.doors[i].x + room.x;
				int y = RoomPopulator.doors[i].y + room.y;
				int z = RoomPopulator.doors[i].z + room.z;
				Schematic schematic = RoomPopulator.doors[i].schematic;
				
				if(schematic!=null && !(room.floor>0 && i==1))
				{
					HothUtils.placeSchematic(plugin, world, schematic, x, y, z, room.spawner);
				}
			}
		}
	}
	
	
	public RoomPopulator(HothGeneratorPlugin plugin)
	{
		this.plugin = plugin;
	}
	
	private Room getEmptyRoom(Cluster cluster, Room parent, int x, int y, int z)
	{
		cluster.ctr++;
		Room room = new Room(cluster.ctr, x, y, z);
		cluster.list.add(new Position(x,y,z));
		return room;
	}
	
	private Room populateRoom(Cluster cluster, Room room)
	{
		if(cluster.stopper<cluster.maxRooms && !room.isPopulated)
		{
			int x = room.x;
			int y = room.y;
			int z = room.z;
			
			int subs = 0;
			int prob = cluster.random.nextInt(100);
			if(prob>85) subs = 5;
			else if(prob>75) subs = 4;
			else if(prob>60) subs = 3;
			else if(prob>40) subs = 2;
			else if(prob>30) subs = 1;
			
			subs = 4;
			
			int free = cluster.getFreePositionCount(room);
			if(subs>free)
			{
				subs = free;
			}
			
			cluster.stopper = cluster.stopper + subs;

			int added = 0, i = 0;
			int[] dirs = cluster.getRandomDirectionArray();
			while(added<subs && i<6) // Add children
			{
				
				int sub = dirs[i];
				
				if(room.children[sub]==null)
				{
					Position delta = RoomPopulator.positions[sub];
					if(cluster.isPositionFree(delta, x, y, z))
					{
						room.children[sub] = this.getEmptyRoom(cluster, room, x+delta.x, y+delta.y, z+delta.z);
						room.children[sub].children[RoomPopulator.reverse[sub]] = Room.DUMMY;
						added++;
					}
				}
				i++;
			}
			
			
			// Populate children
			i=0;
			int done = 0;
			dirs = cluster.getRandomDirectionArray();
			while(i<6 && done<subs)
			{
				
				int sub = dirs[i];
				Room child = room.children[sub];
				if(child!=null && !child.isPopulated)
				{
					this.populateRoom(cluster, child);
					done++;
				}
				i++;
			}
		}
		
		room.isPopulated = true;
		
		return room;
	}
	
	/**
	 * Recursively decorate a Room using the random instance from a cluster
	 * @param cluster The cluster instance
	 * @param room The start room
	 */
	public void decorateRoom(Cluster cluster, Room room)
	{
		room.decorate(cluster.random);
		for(int i=0;i<6;i++)
		{
			Room child = room.children[i];
			if(child!=null && !child.equals(Room.DUMMY))
			{
				this.decorateRoom(cluster,child);
			}
		}
	}

	public void print(Cluster cluster)
	{
		this.print(cluster.rooms);
		
		System.out.println("Cluster has " + cluster.list.size() + " rooms");
	}
	
	
	public void print(Room room)
	{
		System.out.println("id = " + room.id + " " + room.x + "," + room.y + "," + room.z);
		for(int i=0;i<6;i++)
		{
			Room child = room.children[i];
			if(child!=null && !child.equals(Room.DUMMY))
			{
				System.out.println(room.id + " has child[" + i + "] = " + room.children[i].id);
				this.print(room.children[i]);
			}
		}
	}
	
	private class Cluster
	{
		public int[][] randomDirections = new int[][]
				{
				{4,1,5,2,3,0},
				{5,1,0,3,4,2},
				{4,0,2,5,3,1},
				{0,1,4,5,3,2},
				{4,3,2,5,1,0},
				{3,4,5,0,2,1},
				{2,5,4,0,3,1},
				{4,5,0,1,2,3},
				{0,1,4,2,5,3},
				{4,5,0,2,3,1},
				{4,3,5,0,2,1},
				{2,5,3,1,4,0},
				{3,1,0,4,2,5},
				{0,1,2,5,4,3},
				{3,2,1,0,4,5},
				{5,3,1,0,2,4},
				{4,0,3,1,5,2},
				{4,2,1,0,5,3},
				{5,1,0,3,4,2},
				{3,1,5,2,4,0},
				{4,2,1,0,5,3},
				{4,1,2,0,5,3},
				{0,2,5,4,3,1},
				{3,0,2,4,1,5},
				{0,3,2,4,1,5},
				{4,0,3,2,1,5},
				{2,3,0,1,5,4},
				{4,1,0,3,2,5},
				{2,0,4,3,1,5},
				{4,1,5,2,0,3},
				{5,3,4,0,2,1},
				{4,3,2,0,1,5},
				{5,4,0,2,1,3},
				{3,4,5,2,1,0},
				{4,0,1,5,2,3},
				{5,4,3,0,2,1},
				{1,0,2,5,3,4},
				{1,2,0,4,3,5},
				{3,4,0,1,2,5},
				{5,2,1,0,4,3},
				{0,4,5,1,3,2},
				{3,2,0,5,1,4},
				{1,5,4,3,0,2},
				{3,5,2,4,0,1},
				{3,5,0,1,2,4},
				{4,1,5,0,2,3},
				{5,3,2,0,4,1},
				{0,2,5,4,1,3},
				{0,1,2,3,5,4},
				{3,2,0,5,4,1},
				{3,0,4,5,1,2},
				{3,5,2,1,4,0},
				{1,5,3,0,2,4},
				{0,5,3,4,2,1},
				{5,1,4,0,3,2},
				{0,2,3,4,1,5},
				{5,0,3,2,1,4},
				{3,5,0,1,2,4},
				{2,0,3,1,4,5},
				{1,5,2,0,3,4},
				{3,1,4,5,2,0},
				{2,3,5,1,4,0},
				{5,4,1,2,3,0},
				{4,5,1,3,2,0}
				};
		
		public Cluster(Random random)
		{
			this.random = random;
			this.list = new Vector<Position>();
			this.ctr = 0;
			
			this.maxRooms = RoomPopulator.MINROOMS + random.nextInt(RoomPopulator.MAXROOMS - RoomPopulator.MINROOMS);
			this.ctr = 0;
			this.stopper = 1;
		}
		
		public Room rooms;
		public Vector<Position> list;
		public int maxRooms;
		public int ctr;
		public int stopper;
		public Random random;
		
		public int[] getRandomDirectionArray()
		{
			return this.randomDirections[this.random.nextInt(64)];
		}
		
		public boolean isPositionUsed(Position delta, int x, int y, int z)
		{
			return this.isPositionUsed(x+delta.x, y+delta.y, z+delta.z);
		}

		public boolean isPositionUsed(int x, int y, int z)
		{
			for(int i=0;i<this.list.size();i++)
			{
				Position pos = this.list.elementAt(i);
				if(pos.x == x && pos.y == y && pos.z == z)
				{
					return true;
				}
			}
			
			return false;
		}
		
		public boolean isPositionValid(Position delta, int x, int y, int z)
		{
			int ry = y + delta.y;
			return ry > 10 & ry < 26; 
		}
		
		public int getFreePositionCount(Room room)
		{
			int ctr = 0;
			for(int i=0;i<6;i++)
			{
				if(!this.isPositionUsed(RoomPopulator.positions[i], room.x, room.y, room.z)
						&& this.isPositionValid(RoomPopulator.positions[i], room.x, room.y, room.z))
				{
					ctr++;
				}
			}
			
			return ctr;
		}
		
		public boolean isPositionFree(Position delta, int x, int y, int z)
		{
			return (!this.isPositionUsed(delta, x, y, z) && this.isPositionValid(delta, x, y, z));
		}
	}
	
	static class PlaceCluster implements Runnable
	{
		private final World world;
		private final Cluster cluster;
		private final Plugin plugin;

		public PlaceCluster(Plugin plugin, World world, Cluster cluster)
		{
			this.world = world;
			this.cluster = cluster;
			this.plugin = plugin;
		}

		@Override
		public void run()
		{
			RoomPopulator.renderRoom(this.plugin, this.world, this.cluster.rooms);
			// @ToDO: remove in a final release. (Or write into a log file so admins
			// can find the structures?)
			System.out.println("Placing cluster at " + this.cluster.rooms.x + "," + this.cluster.rooms.y + "," + this.cluster.rooms.z);
		}
	}
	

}
