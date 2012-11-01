package biz.orgin.minecraft.hothgenerator;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;

import biz.orgin.minecraft.hothgenerator.schematic.BaseTop;

public class BaseGenerator {

	
	public static void generateBase(Plugin plugin, World world, Random random, int chunkX, int chunkZ)
	{
		int doit = random.nextInt(1024);
		if(doit == 350)
		{
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new PlaceBase(plugin, world, random, chunkX, chunkZ));
		}	
	}
	
	
	static class PlaceBase implements Runnable
	{
		private final Plugin plugin;
		private final World world;
		private final Random random;
		private final int chunkx;
		private final int chunkz;

		public PlaceBase(Plugin plugin, World world, Random random, int chunkx, int chunkz)
		{
			this.plugin = plugin;
			this.world = world;
			this.random = random;
			this.chunkx = chunkx;
			this.chunkz = chunkz;
		}

		@Override
		public void run()
		{	
			int sx = this.chunkx*16 + random.nextInt(16);
			int sz = this.chunkz*16 + random.nextInt(16);
			
			int sy = this.world.getHighestBlockYAt(sx, sz);
			
			boolean safePlace = true;
			
			// Check if there's stone around
			Block block = world.getBlockAt(sx,sy,sz); Material type = block.getType();
			if(safePlace && (type.equals(Material.STONE) || type.equals(Material.GLASS))) safePlace = false;
			block = world.getBlockAt(sx-5,sy+11,sz-5); type = block.getType();
			if(safePlace && !type.equals(Material.AIR)) safePlace = false;
			block = world.getBlockAt(sx+5,sy+11,sz-5); type = block.getType();
			if(safePlace && !type.equals(Material.AIR)) safePlace = false;
			block = world.getBlockAt(sx+5,sy+11,sz+5); type = block.getType();
			if(safePlace && !type.equals(Material.AIR)) safePlace = false;
			block = world.getBlockAt(sx-5,sy+11,sz+5); type = block.getType();
			if(safePlace && !type.equals(Material.AIR)) safePlace = false;
			block = world.getBlockAt(sx-5,sy-2,sz-5); type = block.getType();
			if(safePlace && (type.equals(Material.STONE) || type.equals(Material.GLASS) || type.equals(Material.AIR))) safePlace = false;
			block = world.getBlockAt(sx+5,sy-2,sz-5); type = block.getType();
			if(safePlace && (type.equals(Material.STONE) || type.equals(Material.GLASS) || type.equals(Material.AIR))) safePlace = false;
			block = world.getBlockAt(sx+5,sy-2,sz+5); type = block.getType();
			if(safePlace && (type.equals(Material.STONE) || type.equals(Material.GLASS) || type.equals(Material.AIR))) safePlace = false;
			block = world.getBlockAt(sx-5,sy-2,sz+5); type = block.getType();
			if(safePlace && (type.equals(Material.STONE) || type.equals(Material.GLASS) || type.equals(Material.AIR))) safePlace = false;
			
			if(safePlace)
			{
				this.plugin.getLogger().info("Placing base at " + sx + "," + sy + "," + sz);
				
				HothUtils.placeSchematic(plugin, world, BaseTop.instance, sx-5, sy+11, sz-5);
			}
			else
			{
				this.plugin.getLogger().info("Failed to place base at " + sx + "," + sy + "," + sz);
			}
			
			
		}
	}
}
