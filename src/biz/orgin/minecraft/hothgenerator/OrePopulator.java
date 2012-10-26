package biz.orgin.minecraft.hothgenerator;

import java.util.Random;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;

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
	private static final int STONE = Material.STONE.getId();

	@Override
	public void populate(World world, Random random, Chunk source)
	{
		for (int i = 0; i < type.length; i++)
		{
			for (int j = 0; j < iterations[i]; j++)
			{
				internal(source, random, random.nextInt(16),
						random.nextInt(maxHeight[i]), random.nextInt(16),
						amount[i], type[i]);
			}
		}
	}

	private static void internal(Chunk source, Random random, int originX,
			int originY, int originZ, int amount, int type)
	{
		int x = originX;
		int y = originY;
		int z = originZ;
		
		for (int i = 0; i < amount; i++)
		{
			int dir = random.nextInt(6);
			switch(dir)
			{
			case 0:
			    x++;
				break;
			case 1:
		        x--;
		        break;
			case 2:
			    y++;
				break;
			case 3:
		        y--;
		        break;
			case 4:
			    z++;
				break;
			case 5:
		        z--;
		        break;
			}

			x &= 0xf;
			z &= 0xf;
			if (y > 127 || y < 0) {
				continue;
			}

			Block block = source.getBlock(x, y, z);
			if (block.getTypeId() == STONE) {
				block.setTypeId(type, false);
			}
		}
	}
}