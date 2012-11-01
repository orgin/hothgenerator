package biz.orgin.minecraft.hothgenerator;

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

import biz.orgin.minecraft.hothgenerator.schematic.Schematic;

/**
 * Generic utility functionality
 * @author orgin
 *
 */
public class HothUtils
{
	private static IntSet delays = new IntSet(new int[] {  // Block types to defer until infrastructure is made.
			50,75,76,6,32,37,38,39,40,51,55,26,
			59,31,63,65,66,96,69,77,106,83,115,
			93,94,127,131,132,141,142,143,78,64});
	
	public static void placeSchematic(Plugin plugin, World world, Schematic schematic, int x, int y, int z)
	{
		HothUtils.placeSchematic(plugin, world, schematic, x, y, z, false);
	}

	public static void placeSchematic(Plugin plugin, World world, Schematic schematic, int x, int y, int z, boolean darken)
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
							Loot.getLoot(inv);
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

	
	
}
