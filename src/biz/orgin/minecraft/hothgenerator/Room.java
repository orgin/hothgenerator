package biz.orgin.minecraft.hothgenerator;

import java.util.Random;

public class Room
{
	public static Room DUMMY = new Room(0,0,0,0);
	
	public static int TYPE_NORMAL = 0;   // Normal plain room
	public static int TYPE_CORRIDOR = 0; // Corridor room
	
	public static int FLOOR_NORMAL = 0;  // Normal plain floor
	public static int FLOOR_LAVA = 1;    // Lava pool
	public static int FLOOR_WATER = 2;   // Water pool
	
	public static int LIGHT_NORMAL = 0;  // Normal light
	
	public static int DECORATION_NORMAL = 0;    // Plain or no decoration 
	public static int DECORATION_WATERFOUNTAIN = 1; // Water fountain
	public static int DECORATION_LAVAFOUNTAIN = 2; // Lava fountain
	public static int DECORATION_TREASURE_1 = 3; // Treasure chest 1
	public static int DECORATION_TREASURE_2 = 4; // Treasure chest 2
	public static int DECORATION_TREASURE_3 = 5; // Treasure chest 3
	public static int DECORATION_TREASURE_4 = 6; // Treasure chest 4
	
	public Room[] children;
	// 0 - up
	// 1 - down
	// 2 - north
	// 3 - south
	// 4 - west
	// 5 - east
	
	public int floor;
	public boolean spawner;
	public int decoration;
	
	public int x;
	public int y;
	public int z;
	
	public int id;
	
	public Room parent;
	
	public boolean isPopulated;
	
	public Room(int id, int x, int y, int z)
	{
		this.id = id;

		this.children = new Room[6];
		
		for(int i=0;i<6;i++)
		{
			this.children[i]=null;
		}
		
		this.x = x;
		this.y = y;
		this.z = z;
		
		this.floor = 0;
		this.decoration = 0;
		this.spawner = false;
		
		if(id==0)
		{
			this.isPopulated = false;
		}
		else
		{
			this.isPopulated = false;
		}
	}
	
	public void decorate(Random random)
	{
		// Decorate room here, with all special rules and what not. But after the children has been set.
		
		this.floor = 0;
		this.decoration = 0;
		
		// Floor
		if(this.children[1]==null)
		{
			this.floor = random.nextInt(4);
		}

		// Decoration
		if(this.children[0]==null && this.children[1]==null) // Don't decorate rooms with up or down stairs.
		{
			this.decoration = random.nextInt(9);
		}
		
		if(this.decoration==8) // This is a spawner room.
		{
			this.spawner = true;
		}
	}
	
	public int getChildCount()
	{
		int result = 0;
		for(int i=0;i<6;i++)
		{
			if(this.children[i]!=null && this.children[i]!=Room.DUMMY)
			{
				result++;
			}
		}
		return result;
	}
	
	public boolean Equals(Room other)
	{
		return this.id == other.id;
	}
	
	public String toString()
	{
		StringBuffer mySB = new StringBuffer();
		
		mySB.append("Room ID=" + this.id);
		if(this.children[0]!=null)	mySB.append("[UP: " + this.children[0].id + "] ");
		else mySB.append("[UP: null]");
		if(this.children[1]!=null)	mySB.append("[DOWN: " + this.children[1].id + "] ");
		else mySB.append("[DOWN: null]");
		if(this.children[2]!=null)	mySB.append("[NORTH: " + this.children[2].id + "] ");
		else mySB.append("[NORTH: null]");
		if(this.children[3]!=null)	mySB.append("[SOUTH: " + this.children[3].id + "] ");
		else mySB.append("[SOUTH: null]");
		if(this.children[4]!=null)	mySB.append("[WEST: " + this.children[4].id + "] ");
		else mySB.append("[WEST: null]");
		if(this.children[5]!=null)	mySB.append("[EAST: " + this.children[5].id + "] ");
		else mySB.append("[EAST: null]");
		
		
		return mySB.toString();
	}
}
