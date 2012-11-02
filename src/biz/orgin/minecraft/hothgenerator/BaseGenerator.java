package biz.orgin.minecraft.hothgenerator;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;

import biz.orgin.minecraft.hothgenerator.schematic.BaseRoom;
import biz.orgin.minecraft.hothgenerator.schematic.BaseRoom2;
import biz.orgin.minecraft.hothgenerator.schematic.BaseRoom3;
import biz.orgin.minecraft.hothgenerator.schematic.BaseSection;
import biz.orgin.minecraft.hothgenerator.schematic.BaseTop;
import biz.orgin.minecraft.hothgenerator.schematic.Schematic;

public class BaseGenerator {

	
	private static Schematic[][] rooms = new Schematic[][]
			{
				{ BaseRoom.instance, BaseRoom.instance.rotate(1), BaseRoom.instance.rotate(2),  BaseRoom.instance.rotate(3)},  
				{ BaseRoom2.instance, BaseRoom2.instance.rotate(1), BaseRoom2.instance.rotate(2),  BaseRoom2.instance.rotate(3)},  
				{ BaseRoom3.instance, BaseRoom3.instance.rotate(1), BaseRoom3.instance.rotate(2),  BaseRoom3.instance.rotate(3)}  
			};
	
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
				
				int sections = 3+random.nextInt(3);
				
				for(int i=0;i<sections;i++)
				{
					int px = sx-3;
					int py = sy-5-i*5;
					int pz = sz-3;
					
					HothUtils.placeSchematic(plugin, world, BaseSection.instance, px, py, pz);
					
					int rooms = random.nextInt(16);
					if((rooms&0x1)!=0) // North
					{
						Schematic roomN = BaseGenerator.rooms[random.nextInt(3)][0];
						HothUtils.placeSchematic(plugin, world, roomN, px, py, pz-9);
					}
					else
					{
						Block door = world.getBlockAt(px+3, py-2, pz); door.setType(Material.COBBLESTONE);
						door = world.getBlockAt(px+3, py-3, pz); door.setType(Material.COBBLESTONE);
					}
					if((rooms&0x2)!=0) // South
					{
						Schematic roomN = BaseGenerator.rooms[random.nextInt(3)][1];
						HothUtils.placeSchematic(plugin, world, roomN, px, py, pz+6);
					}
					else
					{
						Block door = world.getBlockAt(px+3, py-2, pz+6); door.setType(Material.COBBLESTONE);
						door = world.getBlockAt(px+3, py-3, pz+6); door.setType(Material.COBBLESTONE);
					}
					if((rooms&0x4)!=0) // West
					{
						Schematic roomW = BaseGenerator.rooms[random.nextInt(3)][2];
						HothUtils.placeSchematic(plugin, world, roomW, px-9, py, pz);
					}
					else
					{
						Block door = world.getBlockAt(px, py-2, pz+3); door.setType(Material.COBBLESTONE);
						door = world.getBlockAt(px, py-3, pz+3); door.setType(Material.COBBLESTONE);
					}
					if((rooms&0x8)!=0) // East
					{
						Schematic roomE = BaseGenerator.rooms[random.nextInt(3)][3];
						HothUtils.placeSchematic(plugin, world, roomE, px+6, py, pz);
					}
					else
					{
						Block door = world.getBlockAt(px+6, py-2, pz+3); door.setType(Material.COBBLESTONE);
						door = world.getBlockAt(px+6, py-3, pz+3); door.setType(Material.COBBLESTONE);
					}
				}
				
			}
			else
			{
				this.plugin.getLogger().info("Failed to place base at " + sx + "," + sy + "," + sz);
			}
			
			
		}
	}
}
