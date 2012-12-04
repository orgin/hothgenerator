package biz.orgin.minecraft.hothgenerator;

import java.io.File;
import java.io.FileWriter;
import java.util.Vector;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import biz.orgin.minecraft.hothgenerator.schematic.LoadedSchematic;
import biz.orgin.minecraft.hothgenerator.schematic.RotatedSchematic;
import biz.orgin.minecraft.hothgenerator.schematic.Schematic;

/**
 * Generic utility functionality
 * @author orgin
 *
 */
public class HothUtils
{
	public static void main(String artgs[])
	{
		File file = new File("/home/orgin/minecraft/bukkit/plugins/HothGenerator/custom/skeleton.sm");
		File ofile = new File("/home/orgin/minecraft/bukkit/plugins/HothGenerator/custom/skeleton.java");
		
		try
		{
			LoadedSchematic schematic = new LoadedSchematic(file);
			String string = HothUtils.schematicToString(schematic);
			System.out.println(string);
			
			FileWriter writer = new FileWriter(ofile);
			writer.write(string);
			writer.flush();
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	
	private static IntSet delays = new IntSet(new int[] {  // Block types to defer until infrastructure is made.
			50,75,76,6,32,37,38,39,40,51,55,26,
			59,31,63,65,66,96,69,77,106,83,115,
			93,94,127,131,132,141,142,143,78,64});

	public static void placeSchematic(Plugin plugin, World world, Schematic schematic, int x, int y, int z, int lootMin, int lootMax)
	{
		HothUtils.placeSchematic(plugin, world, schematic, x, y, z, lootMin, lootMax, LootGenerator.getLootGenerator(), false);

	}
	public static void placeSchematic(Plugin plugin, World world, Schematic schematic, int x, int y, int z, int lootMin, int lootMax, boolean darken)
	{
		HothUtils.placeSchematic(plugin, world, schematic, x, y, z, lootMin, lootMax, LootGenerator.getLootGenerator(), darken);
	}

	public static void placeSchematic(Plugin plugin, World world, Schematic schematic, int x, int y, int z, int lootMin, int lootMax, LootGenerator lootGenerator)
	{
		HothUtils.placeSchematic(plugin, world, schematic, x, y, z, lootMin, lootMax, lootGenerator, false);
	}

	public static void placeSchematic(Plugin plugin, World world, Schematic schematic, int x, int y, int z, int lootMin, int lootMax, LootGenerator lootGenerator, boolean darken)
	{
		int height = schematic.getHeight();
		int length = schematic.getLength();
		int width = schematic.getWidth();
		int[][][] matrix = schematic.getMatrix();
		
		Vector<BlockState> delays = new Vector<BlockState>();
		
		for(int yy=0;yy<height;yy++)
		{
			for(int zz=0;zz<length;zz++)
			{
				for(int xx=0;xx<width;xx++)
				{
					int type = matrix[yy][zz][xx];
					
					if(darken) // Replace lightsources with cobble
					{
						// Glowstone, torch, redstone torch (lit/unlit), redstone lamp (lit/unlit)
						if(type==89 || type==50 || type==75 || type==76 || type==123 || type==124 || type==10)
						{
							type=4; // Turn to cobble
						}
					}
					
					if(type>-1)
					{
						byte data = (byte)matrix[yy][zz][xx+width];
						Block block = world.getBlockAt(x+xx, y-yy, z+zz);
						
						if(type==52) // Spawner, Set some spawner data
						{
							block.setTypeId(type);
							CreatureSpawner spawner = (CreatureSpawner)block.getState();
							if(data==0)
							{
								int creature = x%8;
								switch(creature)
								{
								case 0:
								case 1:
									spawner.setSpawnedType(EntityType.SKELETON); //25
									break;
								case 2:
								case 3:
									spawner.setSpawnedType(EntityType.SPIDER); // 25
									break;
								case 4:
								case 5:
								case 6:
								case 7:
								default:
									spawner.setSpawnedType(EntityType.ZOMBIE); // 50
									break;
								}
							}
							else // Schematic wants to set spawner type
							{
								spawner.setSpawnedType(EntityType.fromId(data));
							}
							
							spawner.update(true);
						}
						else if(type==54) // Chest, set correct rotation and add some random loot
						{
							block.setTypeId(type);
							Chest chest = (Chest)block.getState();
							org.bukkit.material.Chest cst = null;
							switch(data)
							{
							default:
							case 0:
								cst = new org.bukkit.material.Chest(BlockFace.EAST);
								break;
							case 1:
								cst = new org.bukkit.material.Chest(BlockFace.WEST);
								break;
							case 2:
								cst = new org.bukkit.material.Chest(BlockFace.NORTH);
								break;
							case 3:
								cst = new org.bukkit.material.Chest(BlockFace.SOUTH);
								break;
							}
							chest.setData(cst);
							Inventory inv = chest.getInventory();
							lootGenerator.getLootInventory(inv, lootMin, lootMax);
							chest.update(true);

						}
						else if(HothUtils.delays.contains(type)) // Torches and such, delay for later
						{
							BlockState state = block.getState();
							state.setTypeId(type);
							state.setRawData((byte)data);
							delays.add(state);
						}
						else
						{
							block.setTypeIdAndData(type, (byte)data, false);
						}
					}
				}
			}
		}
		
		for(int i=0;i<delays.size();i++) // Insert delayed blocks
		{
			delays.elementAt(i).update(true);
		}
	}
	
	public static void setPos(byte[][] chunk, int x, int y, int z, Material material)
	{
		int type = material.getId();
		HothUtils.setPos(chunk, x, y, z, type);
	}
	
	public static void setPos(byte[][] chunk, int x, int y, int z, int type)
	{
		
		int sub = y/16;
		int rely = y-(sub*16);
		
		if(chunk[sub]==null)
		{
			chunk[sub] = new byte[16*16*16];
		}
		
		HothUtils.setBlock(chunk[sub], x,rely,z, (byte)type);

	}
	
	public static void setBlock(byte[] subchunk, int x, int y, int z, byte blkid)
	{
		subchunk[((y) << 8) | (z << 4) | x] = blkid;
	}

	public static byte getPos(byte[][] chunk, int x, int y, int z)
	{
		int sub = y/16;
		int rely = y-(sub*16);
		
		if(chunk[sub]==null)
		{
			chunk[sub] = new byte[16*16*16];
		}
		
		return HothUtils.getBlock(chunk[sub], x,rely,z);

	}

	public static byte getBlock(byte[] subchunk, int x, int y, int z)
	{
		return subchunk[((y) << 8) | (z << 4) | x];
	}

	public static Schematic rotateSchematic(int direction, Schematic schematic)
	{
		Schematic result = null;
		
		if(direction==0) // North
		{
			result = schematic;
		}
		else if(direction==1) // South
		{
			int[][][] source = schematic.getMatrix();
			
			String name = schematic.getName()+"_SOUTH";
			int width = schematic.getWidth();
			int length = schematic.getLength();
			int height = schematic.getHeight();
			int[][][] matrix = new int[height][length][width*2];
			
			for(int y=0;y<height;y++)
			{
				for(int z=0;z<length;z++)
				{
					for(int x=0;x<width;x++)
					{
						int type = source[y][length-z-1][width-x-1];
						int data = source[y][length-z-1][width-x-1+width];
						
						matrix[y][z][x] = type;
						matrix[y][z][x+width] = HothUtils.rotateData(type, data, 1);
								
					}
				}
			}
			
			result = new RotatedSchematic(name, width, length, height, matrix);
		}
		else if(direction==2) // West
		{
			int[][][] source = schematic.getMatrix();
			
			String name = schematic.getName()+"_WEST";
			int width = schematic.getLength();
			int length = schematic.getWidth();
			int height = schematic.getHeight();
			int[][][] matrix = new int[height][length][width*2];
			
			for(int y=0;y<height;y++)
			{
				for(int z=0;z<length;z++)
				{
					for(int x=0;x<width;x++)
					{
						int type = source[y][x][length-z-1]; // source[y][length-z-1][x];
						int data = source[y][x][length-z-1 + length];// source[y][length-z-1][x+width];
						
						matrix[y][z][x] = type;
						matrix[y][z][x+width] = HothUtils.rotateData(type, data, 2);
								
					}
				}
			}
			
			result = new RotatedSchematic(name, width, length, height, matrix);
		}
		else if(direction==3) // East
		{
			int[][][] source = schematic.getMatrix();
			
			String name = schematic.getName()+"_EAST";
			int width = schematic.getLength();
			int length = schematic.getWidth();
			int height = schematic.getHeight();
			int[][][] matrix = new int[height][length][width*2];
			
			for(int y=0;y<height;y++)
			{
				for(int z=0;z<length;z++)
				{
					for(int x=0;x<width;x++)
					{
						int type = source[y][width-x-1][z]; // source[y][length-z-1][x];
						int data = source[y][width-x-1][z+length];// source[y][length-z-1][x+width];
						
						matrix[y][z][x] = type;
						matrix[y][z][x+width] = HothUtils.rotateData(type, data, 3);
								
					}
				}
			}
			
			result = new RotatedSchematic(name, width, length, height, matrix);
		}

		return result;
		
	}
	
	public static int rotateData(int type, int data, int rot)
	{
		if(type==50)  // torch
		{
			switch(rot)
			{
			case 1:
				switch(data)
				{
				case 1: return 2; // N
				case 2: return 1; // S
				case 3: return 4; // W
				case 4: return 3; // E
				}
			case 2:
				switch(data)
				{
				case 1: return 4; // N
				case 2: return 3; // S
				case 3: return 1; // W
				case 4: return 2; // E
				}
			case 3:
				switch(data)
				{
				case 1: return 3; // N
				case 2: return 4; // S
				case 3: return 2; // W
				case 4: return 1; // E
				}
			}
		}
		else if(type==54) // chest
		{
			switch(rot)
			{
			case 1:
				switch(data)
				{
				case 0: return 1; // N
				case 1: return 0; // S
				case 2: return 3; // W
				case 3: return 2; // E
				}
			case 2:
				switch(data)
				{
				case 0: return 2; // N
				case 1: return 3; // S
				case 2: return 1; // W
				case 3: return 0; // E
				}
			case 3:
				switch(data)
				{
				case 0: return 3; // N
				case 1: return 2; // S
				case 2: return 0; // W
				case 3: return 1; // E
				}
			}
		}
			
		
		
		return data;
	}
	
	public static String schematicToString(Schematic schematic)
	{
		int[][][] source = schematic.getMatrix();
		StringBuffer mySB = new StringBuffer();
		StringBuffer mySB2 = new StringBuffer();
		StringBuffer result = new StringBuffer();
		
		int width = schematic.getWidth();
		int length = schematic.getLength();
		int height = schematic.getHeight();
		
		for(int y=0;y<height;y++)
		{
			result.append("\t{\t// Layer " + y + "\n");
			for(int z=0;z<length;z++)
			{
				for(int x=0;x<width;x++)
				{
					int type = source[y][z][x];
					int data = source[y][z][x+width];
					
					String stype = String.format("%4d", type);
					String sdata;
					if(x==0)
					{
						sdata = String.format("%5d", data);
					}
					else
					{
						sdata = String.format("%3d", data);
					}
					
					if(x>0)
					{
						mySB.append(",");
					}
					mySB.append(stype);
					mySB2.append("," + sdata);
				}
				
				result.append("\t\t{" + mySB.toString() + mySB2.toString() + "}");
				if(z!=length-1)
				{
					result.append(",\n");
				}
				else
				{
					result.append("\n");
				}
				mySB.setLength(0);
				mySB2.setLength(0);
			}
			if(y!=height-1)
			{
			result.append("\t},\n");
			}
			else
			{
				result.append("\t}\n");
			
			}
		}
		
		return result.toString();
	}

}
