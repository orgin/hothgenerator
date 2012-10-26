package biz.orgin.minecraft.hothgenerator;

import java.util.Random;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;

/**
 * A populator that replaces stone blocks into strands of ore blocks
 * @author orgin
 *
 */
public class OrePopulator extends BlockPopulator
{
	private static final int[] iterations = new int[] {10, 10, 20, 20, 2, 8, 1, 1, 1};
	private static final int[] amount =     new int[] {32, 32, 16,  8, 8, 7, 7, 6, 6};
	private static final int[] type =       new int[] {
		Material.DIRT.getId(),         // 60
		Material.GRAVEL.getId(),       // 26
		Material.COAL_ORE.getId(),     // 128
		Material.IRON_ORE.getId(),     // 128
		Material.GOLD_ORE.getId(),     // 26
		Material.REDSTONE_ORE.getId(), // 16
		Material.DIAMOND_ORE.getId(),  // 16
		Material.LAPIS_ORE.getId(),    // 26
		Material.EMERALD_ORE.getId()}; // 128
	private static final int[] maxHeight = new int[] {60, 26, 128, 128, 26, 16, 16,	26, 128};
	private static int REPLACE = Material.STONE.getId();

	@Override
	public void populate(World world, Random random, Chunk source)
	{
		OrePopulator.generateOres(world, random, source.getX(), source.getZ());
	}
	
	public static void generateOres(World world, Random random, int chunkx, int chunkz)
	{
		for (int i = 0; i < type.length; i++)
		{
			for (int j = 0; j < iterations[i]; j++)
			{
				OrePopulator.vein(world, chunkx*16, chunkz*16, random, random.nextInt(16),
						random.nextInt(maxHeight[i]), random.nextInt(16),
						amount[i], type[i]);
			}
		}
	}

	private static void vein(World world, int x, int z, Random random, int originX,
			int originY, int originZ, int amount, int type)
	{
		int dx = originX;
		int dy = originY;
		int dz = originZ;
		
		for (int i = 0; i < amount; i++)
		{
			int dir = random.nextInt(6);
			switch(dir)
			{
			case 0:
			    dx++;
				break;
			case 1:
		        dx--;
		        break;
			case 2:
			    dy++;
				break;
			case 3:
		        dy--;
		        break;
			case 4:
			    dz++;
				break;
			case 5:
		        dz--;
		        break;
			}

			if (dy > 127 || dy < 0) {
				continue;
			}

			Block block = world.getBlockAt(x+dx, dy, z+dz);
			if (block.getTypeId() == REPLACE) {
				block.setTypeId(type, false);
			}
		}
	}
}