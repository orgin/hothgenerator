package biz.orgin.minecraft.hothgenerator;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.plugin.Plugin;

import biz.orgin.minecraft.hothgenerator.schematic.GreenGarden;
import biz.orgin.minecraft.hothgenerator.schematic.GreyGarden;
import biz.orgin.minecraft.hothgenerator.schematic.Schematic;

// @ToDo: replace this with schematics

/**
 * A Populator that places underground gardens into the world.
 * @author orgin
 *
 */
public class GardenPopulator extends BlockPopulator
{
	private Plugin plugin;
	
	public GardenPopulator(Plugin plugin)
	{
		this.plugin = plugin;
	}
	

	@Override
	public void populate(World world, Random random, Chunk source)
	{
		GardenPopulator.generateGarden(this.plugin, world, random, source.getX(), source.getZ());
	}
	
	public static void generateGarden(Plugin plugin, World world, Random random, int chunkX, int chunkZ)
	{
		int place = random.nextInt(100);
		
		if(place==37)
		{
			int x = random.nextInt(16) + chunkX * 16;
			int z = random.nextInt(16) + chunkZ * 16;
			int y = 9 + random.nextInt(15) + 100;
			
			int cnt = 1;
			
			Schematic garden;
			switch(random.nextInt(4))
			{
			case 0:
				garden = GreyGarden.instance; // Mushroom garden
				break;
			case 1:
			case 2:
			case 3:
			default:
				garden = GreenGarden.instance; // Green garden
				cnt = 2;  // To get the lights right.
				break;
			}
			
			for(int i=0;i<cnt;i++)
			{
				HothUtils.placeSchematic(plugin, world, garden, x, y, z);
			}
		}
	}

}
