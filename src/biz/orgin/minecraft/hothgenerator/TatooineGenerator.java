package biz.orgin.minecraft.hothgenerator;

import java.util.Random;

import org.bukkit.World;

public class TatooineGenerator extends HothGenerator
{
	@Override
	public short[][] generateExtBlockSections(World world, Random random, int chunkx, int chunkz, BiomeGrid biomes)
	{
		super.forceWorldType(world, WorldType.TATOOINE);
		return super.generateExtBlockSectionsTatooine(world, random, chunkx, chunkz, biomes);
	}
}
