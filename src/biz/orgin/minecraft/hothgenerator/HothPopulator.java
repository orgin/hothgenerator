package biz.orgin.minecraft.hothgenerator;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.material.Tree;

/**
 * Generates random frozen logs and sets biome
 * @author orgin
 *
 */
public class HothPopulator extends BlockPopulator
{
	@SuppressWarnings("unused")
	private int height;
	private HothGeneratorPlugin plugin;

	public HothPopulator(int height)
	{
		this.plugin = HothGenerator.getPlugin();
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
		if(this.plugin.isGenerateLogs())
		{
			this.placeLogs(world, rx, rz, random);
		}
		
		// Set biome for proper weather effect
		for(int i=0;i<16;i++)
		{
			for(int j=0;j<16;j++)
			{
				world.setBiome(rx+j, rz+i, Biome.FROZEN_OCEAN);
			}
		}
	}
	
	private TreeSpecies getRandomSpecies(Random random)
	{
		int ran = random.nextInt(6);
		TreeSpecies result = TreeSpecies.GENERIC;
		switch(ran)
		{
		case 0:
			result = TreeSpecies.GENERIC;
			break;
		case 1:
			result = TreeSpecies.BIRCH;
			break;
		case 2:
			result = TreeSpecies.REDWOOD;
			break;
		case 3:
			result = TreeSpecies.JUNGLE;
			break;
		case 4:
			result = TreeSpecies.ACACIA;
			break;
		case 5:
			result = TreeSpecies.DARK_OAK;
			break;
		}
		
		return result;
	}
	
	private void placeLogs(World world, int rx, int rz, Random random)
	{
		int surfaceOffset = this.plugin.getWorldSurfaceoffset();
		int cnt = random.nextInt(32);
		
		for(int i=0;i<cnt;i++)
		{
			boolean addLog = false;
			int prob = random.nextInt(256);
			Material material = null;
			TreeSpecies species = TreeSpecies.GENERIC;
			int x = random.nextInt(16);
			int z = random.nextInt(16);
			
			Biome biome = world.getBiome(rx+x, rz+z);
			
			if(biome.equals(Biome.EXTREME_HILLS))
			{
				material = Material.LOG;
				species = TreeSpecies.GENERIC;
				addLog = prob<64;
			}
			else if(biome.equals(Biome.FOREST) || biome.equals(Biome.FOREST_HILLS))
			{
				material = Material.LOG;
				species = this.getRandomSpecies(random);
				addLog = prob<128;
			}
			else if(biome.equals(Biome.PLAINS))
			{
				material = Material.LOG;
				species = this.getRandomSpecies(random);
				addLog = prob<32;
			}
			else if(biome.equals(Biome.JUNGLE) || biome.equals(Biome.JUNGLE_HILLS))
			{
				material = Material.LOG;
				species = TreeSpecies.JUNGLE;
				addLog = prob<128;
			}
			else if(biome.equals(Biome.SWAMPLAND))
			{
				material = Material.LOG;
				species = TreeSpecies.GENERIC;
				addLog = prob<128;
			}
			else if(biome.equals(Biome.TAIGA) || biome.equals(Biome.TAIGA_HILLS))
			{
				material = Material.LOG;
				species = TreeSpecies.GENERIC;
				addLog = prob<32;
			}
			else if(biome.equals(Biome.ROOFED_FOREST) || biome.equals(Biome.ROOFED_FOREST_MOUNTAINS))
			{
				material = Material.LOG;
				species = TreeSpecies.DARK_OAK;
				addLog = prob<32;
			}
			
			
			if(addLog)
			{
				
				int blocks = 3 + random.nextInt(2);
				int direction = random.nextInt(3);
				
				int ry = random.nextInt(44 + 32) + 20 + surfaceOffset;
				
				switch(direction)
				{
				case 0: // Y
					for(int j=0;j<blocks;j++)
					{
						Block block = world.getBlockAt(rx+x, ry+j, rz+z);
						if((block.getType().equals(Material.ICE) || block.getType().equals(Material.SNOW_BLOCK) || block.getType().equals(Material.PACKED_ICE)) && isBlockSubmerged(world, block))
						{
							block.setType(material);
							BlockState state = block.getState();
							Tree tree = (Tree)state.getData();
							tree.setSpecies(species);
							tree.setDirection(BlockFace.DOWN);
							state.update(true, false);
						}
					}
					break;
				case 1: // X
					for(int j=0;j<blocks;j++)
					{
						Block block = world.getBlockAt(rx+x+j, ry, rz+z);
						if((block.getType().equals(Material.ICE) || block.getType().equals(Material.SNOW_BLOCK) || block.getType().equals(Material.PACKED_ICE)) && isBlockSubmerged(world, block))
						{
							block.setType(material);
							BlockState state = block.getState();
							Tree tree = (Tree)state.getData();
							tree.setSpecies(species);
							tree.setDirection(BlockFace.EAST);
							state.update(true, false);
						}
					}
					break;
				case 2: // Z
					for(int j=0;j<blocks;j++)
					{
						Block block = world.getBlockAt(rx+x, ry, rz+z+j);
						if((block.getType().equals(Material.ICE) || block.getType().equals(Material.SNOW_BLOCK) || block.getType().equals(Material.PACKED_ICE)) && isBlockSubmerged(world, block))
						{
							block.setType(material);
							BlockState state = block.getState();
							Tree tree = (Tree)state.getData();
							tree.setSpecies(species);
							tree.setDirection(BlockFace.NORTH);
							state.update(true, false);
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

		if(		world.getBlockAt(x+1,y,z).getType().equals(Material.AIR) ||
				world.getBlockAt(x-1, y, z).getType().equals(Material.AIR) ||
				world.getBlockAt(x, y, z+1).getType().equals(Material.AIR) ||
				world.getBlockAt(x, y, z-1).getType().equals(Material.AIR) ||
				world.getBlockAt(x+1, y, z).getType().equals(Material.SNOW) ||
				world.getBlockAt(x-1, y, z).getType().equals(Material.SNOW) ||
				world.getBlockAt(x, y, z+1).getType().equals(Material.SNOW) ||
				world.getBlockAt(x, y, z-1).getType().equals(Material.SNOW)				
				)
		{
			return false;
		}
		
		return true;
	}

}
