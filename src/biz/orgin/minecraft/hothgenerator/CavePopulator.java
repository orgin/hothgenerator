package biz.orgin.minecraft.hothgenerator;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;

/**
 * A general cave generator. Generates simple twisting strands of empty caves.
 * @author orgin
 *
 */
public class CavePopulator extends BlockPopulator
{
	private HothGeneratorPlugin plugin;
	
	public CavePopulator(HothGeneratorPlugin plugin)
	{
		this.plugin = plugin;
	}
	
	static class PlaceCave implements Runnable
	{
		private final World world;
		private final Position[] snake;

		public PlaceCave(World world, Set<Position> cave)
		{
			this.world = world;
			this.snake = cave.toArray(new Position[0]);
		}

		@Override
		public void run()
		{
			finishCave(world, snake);
			for (Position pos : snake)
			{
				world.unloadChunkRequest(pos.x / 16, pos.z / 16);
			}
		}
	}

	@Override
	public void populate(final World world, final Random random, Chunk source)
	{
		if (random.nextInt(100) < 4)
		{
			final int x = 4 + random.nextInt(8) + source.getX() * 16;
			final int z = 4 + random.nextInt(8) + source.getZ() * 16;
			int maxY = world.getHighestBlockYAt(x, z);
			if (maxY < 16)
			{
				maxY = 32;
			}
	
			final int y = random.nextInt(maxY);
			new Thread()
			{
				@Override
				public void run()
				{
					Set<Position> cave = startCave(world, random, x, y, z);
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new PlaceCave(world, cave));
	
					if (random.nextInt(16) > 5) {
						if (y > 36) {
							cave = startCave(world, random, x, y / 2, z);
							Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new PlaceCave(world, cave));
						} else if (y < 24) {
							cave = startCave(world, random, x, y * 2, z);
							Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new PlaceCave(world, cave));
						}
					}
				}
			}.start();
		}
	}

	static Set<Position> startCave(World world, Random random, int blockX, int blockY, int blockZ)
	{
		Set<Position> caveBlocks = new HashSet<Position>();
	
		int airHits = 0;
		Position block = new Position();
		while (true) {
			if (airHits > 1024) {
				break;
			}
	
			if (random.nextInt(20) == 0) {
				blockY++;
			} else if (world.getBlockTypeIdAt(blockX, blockY + 2, blockZ) == 0) {
				blockY += 2;
			} else if (world.getBlockTypeIdAt(blockX + 2, blockY, blockZ) == 0) {
				blockX++;
			} else if (world.getBlockTypeIdAt(blockX - 2, blockY, blockZ) == 0) {
				blockX--;
			} else if (world.getBlockTypeIdAt(blockX, blockY, blockZ + 2) == 0) {
				blockZ++;
			} else if (world.getBlockTypeIdAt(blockX, blockY, blockZ - 2) == 0) {
				blockZ--;
			} else if (world.getBlockTypeIdAt(blockX + 1, blockY, blockZ) == 0) {
				blockX++;
			} else if (world.getBlockTypeIdAt(blockX - 1, blockY, blockZ) == 0) {
				blockX--;
			} else if (world.getBlockTypeIdAt(blockX, blockY, blockZ + 1) == 0) {
				blockZ++;
			} else if (world.getBlockTypeIdAt(blockX, blockY, blockZ - 1) == 0) {
				blockZ--;
			} else if (random.nextBoolean()) {
				if (random.nextBoolean()) {
					blockX++;
				} else {
					blockZ++;
				}
			} else {
				if (random.nextBoolean()) {
					blockX--;
				} else {
					blockZ--;
				}
			}
	
			if (world.getBlockTypeIdAt(blockX, blockY, blockZ) != 0) {
				int radius = 1 + random.nextInt(2);
				int radius2 = radius * radius + 1;
				for (int x = -radius; x <= radius; x++) {
					for (int y = -radius; y <= radius; y++) {
						for (int z = -radius; z <= radius; z++) {
							if (x * x + y * y + z * z <= radius2 && y >= 0
									&& y < 128)
							{
								int type = world.getBlockTypeIdAt(blockX + x, blockY	+ y, blockZ + z);
								if (type == 0)
								{
									airHits++;
								}
								else if(type == Material.SNOW_BLOCK.getId() ||
										type == Material.ICE.getId() ||
										type == Material.SNOW.getId() ||
										type == Material.STONE.getId())
								{
									block.x = blockX + x;
									block.y = blockY + y;
									block.z = blockZ + z;
									if (caveBlocks.add(block))
									{
										block = new Position();
									}
								}
							}
						}
					}
				}
			} else {
				airHits++;
			}
		}
	
		return caveBlocks;
	}

	static void finishCave(World world, Position[] caveBlocks)
	{
		for (Position pos : caveBlocks)
		{
			Block block = world.getBlockAt(pos.x, pos.y, pos.z);
			if (!block.isEmpty() && !block.isLiquid() && block.getType() != Material.BEDROCK)
			{
				block.setType(Material.AIR);
			}
		}
	}
}