package biz.orgin.minecraft.hothgenerator;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;

/**
 * Generates random frozen logs and sets biome
 * @author orgin
 *
 */
public class LogPopulator extends BlockPopulator
{
	@SuppressWarnings("unused")
	private int height;

	public LogPopulator(int height)
	{
		this.height = height;
	}

	@Override
	public void populate(World world, Random random, Chunk chunk)
	{
		int x = chunk.getX();
		int z = chunk.getZ();
		
		int rx = x * 16;
		int rz = z * 16;
		
		// Logs
		this.placeLogs(world, rx, rz, random);
		
		// Set biome for proper weather effect
	
		for(int i=0;i<16;i++)
		{
			for(int j=0;j<16;j++)
			{
				world.setBiome(rx+j, rz+i, Biome.FROZEN_OCEAN);
			}
		}
	}
	
	private byte getRandomSpecies(Random random)
	{
		int ran = random.nextInt(4);
		byte result = 0;
		switch(ran)
		{
		case 0:
			result = TreeSpecies.GENERIC.getData();
			break;
		case 1:
			result = TreeSpecies.BIRCH.getData();
			break;
		case 2:
			result = TreeSpecies.REDWOOD.getData();
			break;
		case 3:
			result = TreeSpecies.JUNGLE.getData();
			break;
		}
		
		return result;
	}
	
	private void placeLogs(World world, int rx, int rz, Random random)
	{
		int cnt = random.nextInt(32);
		
		for(int i=0;i<cnt;i++)
		{
			boolean addLog = false;
			int prob = random.nextInt(256);
			Material material = null;
			byte data = 0;
			int x = random.nextInt(16);
			int z = random.nextInt(16);
			
			Biome biome = world.getBiome(rx+x, rz+z);
			
			if(biome.equals(Biome.EXTREME_HILLS))
			{
				material = Material.LOG;
				data = TreeSpecies.GENERIC.getData();
				addLog = prob<64;
			}
			else if(biome.equals(Biome.FOREST) || biome.equals(Biome.FOREST_HILLS))
			{
				material = Material.LOG;
				data = this.getRandomSpecies(random);
				addLog = prob<128;
			}
			else if(biome.equals(Biome.PLAINS))
			{
				material = Material.LOG;
				data = this.getRandomSpecies(random);
				addLog = prob<32;
			}
			else if(biome.equals(Biome.JUNGLE) || biome.equals(Biome.JUNGLE_HILLS))
			{
				material = Material.LOG;
				data = TreeSpecies.JUNGLE.getData();
				addLog = prob<128;
			}
			else if(biome.equals(Biome.SWAMPLAND))
			{
				material = Material.LOG;
				data = TreeSpecies.GENERIC.getData();
				addLog = prob<128;
			}
			else if(biome.equals(Biome.TAIGA) || biome.equals(Biome.TAIGA_HILLS))
			{
				material = Material.LOG;
				data = TreeSpecies.GENERIC.getData();
				addLog = prob<32;
			}
			
			if(addLog)
			{
				int blocks = 3 + random.nextInt(2);
				int direction = random.nextInt(3);
				
				int ry = random.nextInt(44 + 32) + 20;
				
				switch(direction)
				{
				case 0: // Y
					for(int j=0;j<blocks;j++)
					{
						Block block = world.getBlockAt(rx+x, ry+j, rz+z);
						if((block.getType().equals(Material.ICE) || block.getType().equals(Material.SNOW_BLOCK)) && isBlockSubmerged(world, block))
						{
							block.setType(material);
							block.setData(data);
						}
					}
					break;
				case 1: // X
					data = (byte)(data | 0x04);
					for(int j=0;j<blocks;j++)
					{
						Block block = world.getBlockAt(rx+x+j, ry, rz+z);
						if((block.getType().equals(Material.ICE) || block.getType().equals(Material.SNOW_BLOCK)) && isBlockSubmerged(world, block))
						{
							block.setType(material);
							block.setData(data);
						}
					}
					break;
				case 2: // Z
					data = (byte)(data | 0x08);
					for(int j=0;j<blocks;j++)
					{
						Block block = world.getBlockAt(rx+x, ry, rz+z+j);
						if((block.getType().equals(Material.ICE) || block.getType().equals(Material.SNOW_BLOCK)) && isBlockSubmerged(world, block))
						{
							block.setType(material);
							block.setData(data);
						}
					}
					break;
				}
			}
		}
		
		
		
	}
	
	private boolean isBlockSubmerged(World world, Block block)
	{
		int x = block.getX();
		int y = block.getY();
		int z = block.getZ();

		if(		world.getBlockTypeIdAt(x+1, y, z) == 0 ||
				world.getBlockTypeIdAt(x-1, y, z) == 0 ||
				world.getBlockTypeIdAt(x, y, z+1) == 0 ||
				world.getBlockTypeIdAt(x, y, z-1) == 0 ||
				world.getBlockTypeIdAt(x+1, y, z) == Material.SNOW.getId() ||
				world.getBlockTypeIdAt(x-1, y, z) == Material.SNOW.getId() ||
				world.getBlockTypeIdAt(x, y, z+1) == Material.SNOW.getId() ||
				world.getBlockTypeIdAt(x, y, z-1) == Material.SNOW.getId()				
				)
		{
			return false;
		}
		
		return true;
	}

}
