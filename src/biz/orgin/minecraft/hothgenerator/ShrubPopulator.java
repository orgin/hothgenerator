package biz.orgin.minecraft.hothgenerator;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;

public class ShrubPopulator extends BlockPopulator
{
	@SuppressWarnings("unused")
	private int height;
	private HothGeneratorPlugin plugin;

	public ShrubPopulator(int height)
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
		if(this.plugin.isGenerateShrubs())
		{
			this.placeShrubs(world, rx, rz, random);
		}
	}
	
	private void placeShrubs(World world, int rx, int rz, Random random)
	{
		for(int i=0;i<random.nextInt(32);i++)
		{
			int x = random.nextInt(15);
			int z = random.nextInt(15);

			int ran = 0;
			
			Biome biome = world.getBiome(rx+x, rz+z);
			
			if(biome.equals(Biome.EXTREME_HILLS))
			{
				ran = random.nextInt(16);
			}
			else if(biome.equals(Biome.FOREST) || biome.equals(Biome.FOREST_HILLS))
			{
				ran = random.nextInt(6);
			}
			else if(biome.equals(Biome.PLAINS))
			{
				ran = random.nextInt(20);
			}
			else if(biome.equals(Biome.JUNGLE) || biome.equals(Biome.JUNGLE_HILLS))
			{
				ran = random.nextInt(3);
			}
			else if(biome.equals(Biome.SWAMPLAND))
			{
				ran = random.nextInt(14);
			}
			else if(biome.equals(Biome.TAIGA) || biome.equals(Biome.TAIGA_HILLS))
			{
				ran = random.nextInt(12);
			}
			else if(biome.equals(Biome.ROOFED_FOREST) || biome.equals(Biome.ROOFED_FOREST_MOUNTAINS))
			{
				ran = random.nextInt(7);
			}			
			else if(biome.equals(Biome.DESERT))
			{
				ran = random.nextInt(3);
			}			
			else
			{
				ran = random.nextInt(5);
			}
			
			if(ran == 1)
			{
				Block block = world.getHighestBlockAt(rx+x, rz+z);
				block.setType(Material.DEAD_BUSH);
			}
		}
	}
}
