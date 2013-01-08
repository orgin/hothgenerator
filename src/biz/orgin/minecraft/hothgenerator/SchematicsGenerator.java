package biz.orgin.minecraft.hothgenerator;

import java.io.IOException;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import biz.orgin.minecraft.hothgenerator.schematic.LoadedSchematic;

/**
 * Used to generate structures that are defined in the form of schematics files.
 * It basically does the same job as the CustomGenerator but it loads its schematics from
 * internal resource files instead of external ones.
 * Which schematic files to use are hard coded. Which means that new schematics cannot be
 * added without changing the code in this class.
 * @author orgin
 *
 */
public class SchematicsGenerator
{
	private static LoadedSchematic skeleton = null;
	private static LoadedSchematic skeleton2 = null;
	private static LoadedSchematic[][] schematics = new LoadedSchematic[2][4];
	
	public static void generateSchematics(HothGeneratorPlugin plugin, World world, Random random, int chunkX, int chunkZ)
	{
		try
		{
			if(SchematicsGenerator.skeleton==null)
			{
				SchematicsGenerator.skeleton = new LoadedSchematic(plugin.getResource("schematics/skeleton.sm"),"skeleton");
				SchematicsGenerator.skeleton.setRarity(plugin.getStructureSkeletonsRarity()*SchematicsGenerator.skeleton.getRarity()/2);
				SchematicsGenerator.skeleton.setRandom(plugin.getStructureSkeletonsRarity()*SchematicsGenerator.skeleton.getRandom()/2);
				SchematicsGenerator.schematics[0][0] = SchematicsGenerator.skeleton;
				SchematicsGenerator.schematics[0][1] = SchematicsGenerator.skeleton.cloneRotate(1);
				SchematicsGenerator.schematics[0][2] = SchematicsGenerator.skeleton.cloneRotate(2);
				SchematicsGenerator.schematics[0][3] = SchematicsGenerator.skeleton.cloneRotate(3);
				
				SchematicsGenerator.skeleton2 = SchematicsGenerator.skeleton.cloneRotate(0);
				SchematicsGenerator.skeleton2.setType(1);
				SchematicsGenerator.schematics[1][0] = SchematicsGenerator.skeleton2;
				SchematicsGenerator.schematics[1][1] = SchematicsGenerator.skeleton2.cloneRotate(1);
				SchematicsGenerator.schematics[1][2] = SchematicsGenerator.skeleton2.cloneRotate(2);
				SchematicsGenerator.schematics[1][3] = SchematicsGenerator.skeleton2.cloneRotate(3);
			}
			
			for(int i=0;i<schematics.length;i++)
			{
				Random newRandom = new Random(random.nextLong());
				
				LoadedSchematic schematic = schematics[i][newRandom.nextInt(4)];
				int rarity = schematic.getRarity();
				if(rarity!=0)
				{
					int rnd = schematic.getRandom();
					
					if(rnd==newRandom.nextInt(rarity))
					{
						Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new PlaceSchematic(plugin, world, newRandom, chunkX, chunkZ, schematic));
					}
				}
			}
		}
		catch(IOException e)
		{
			
		}
	}

	static class PlaceSchematic implements Runnable
	{
		private final HothGeneratorPlugin plugin;
		private final World world;
		private final Random random;
		private final int chunkx;
		private final int chunkz;
		private final LoadedSchematic schematic;

		public PlaceSchematic(HothGeneratorPlugin plugin, World world, Random random, int chunkx, int chunkz, LoadedSchematic schematic)
		{
			this.plugin = plugin;
			this.world = world;
			this.random = random;
			this.chunkx = chunkx;
			this.chunkz = chunkz;
			this.schematic = schematic;
		}

		@Override
		public void run()
		{
			int x = this.random.nextInt(16) + this.chunkx * 16 - this.schematic.getWidth()/2;
			int z = this.random.nextInt(16) + this.chunkz * 16 - this.schematic.getLength()/2;
			int y = 128;
			
			int miny, maxy;
			
			int w = this.schematic.getWidth();
			int l = this.schematic.getLength();
			int h = this.schematic.getHeight();
			
			int hw = w/2;
			int hl = l/2;

			boolean safe = true;

			switch(this.schematic.getType())
			{
			case 0: // On surface
				
				int yoffset = this.schematic.getYoffset();
				
				for(int zz=z-hl;zz<z+hl;zz++)
				{
					for(int xx=x-hw;xx<x+hw;xx++)
					{
						int ty = this.world.getHighestBlockYAt(xx,zz);
						if(ty<y)
						{
							y=ty;
						}
					}
				}
				
				y = y + h + yoffset;
				
				Block block = world.getBlockAt(x,y - h,z); Material type = block.getType();
				if(safe && (type.equals(Material.STONE) || type.equals(Material.GLASS))) safe = false;

				if(yoffset==0) // Only do air safety checks if the offset value is 0
				{
					block = world.getBlockAt(x-hw,y,z-hl); type = block.getType();
					if(safe && !type.equals(Material.AIR)) safe = false;
					block = world.getBlockAt(x+hw,y,z-hl); type = block.getType();
					if(safe && !type.equals(Material.AIR)) safe = false;
					block = world.getBlockAt(x+hw,y,z+hl); type = block.getType();
					if(safe && !type.equals(Material.AIR)) safe = false;
					block = world.getBlockAt(x-hw,y,z+hl); type = block.getType();
					if(safe && !type.equals(Material.AIR)) safe = false;

					block = world.getBlockAt(x-hw,y-h,z-hl); type = block.getType();
					if(safe && type.equals(Material.AIR)) safe = false;
					block = world.getBlockAt(x+hw,y-h,z-hl); type = block.getType();
					if(safe && type.equals(Material.AIR)) safe = false;
					block = world.getBlockAt(x+hw,y-h,z+hl); type = block.getType();
					if(safe && type.equals(Material.AIR)) safe = false;
					block = world.getBlockAt(x-hw,y-h,z+hl); type = block.getType();
					if(safe && type.equals(Material.AIR)) safe = false;
				
				}
				block = world.getBlockAt(x-hw,y-h,z-hl); type = block.getType();
				if(safe && (type.equals(Material.STONE) || type.equals(Material.GLASS))) safe = false;
				block = world.getBlockAt(x+hw,y-h,z-hl); type = block.getType();
				if(safe && (type.equals(Material.STONE) || type.equals(Material.GLASS))) safe = false;
				block = world.getBlockAt(x+hw,y-h,z+hl); type = block.getType();
				if(safe && (type.equals(Material.STONE) || type.equals(Material.GLASS))) safe = false;
				block = world.getBlockAt(x-hw,y-h,z+hl); type = block.getType();
				if(safe && (type.equals(Material.STONE) || type.equals(Material.GLASS))) safe = false;
				break;
			case 1: // In ice layer
				miny = 30 + this.schematic.getHeight();
				maxy = 65;
				y = miny + this.random.nextInt(maxy-miny);
				break;
			case 2: // In stone layer
				miny = 6 + this.schematic.getHeight();
				maxy = 26;
				y = miny + this.random.nextInt(maxy-miny);
				break;
			}
			
			if(safe)
			{
				LootGenerator generator = LootGenerator.getLootGenerator(schematic.getLoot());
				if(generator==null)
				{
					HothUtils.placeSchematic(plugin, world, schematic, x-hw, y, z-hl, schematic.getLootMin(), schematic.getLootMax());
				}
				else
				{
					HothUtils.placeSchematic(plugin, world, schematic, x-hw, y, z-hl, schematic.getLootMin(), schematic.getLootMax(), generator);
				}
	
				this.plugin.logMessage("Placing " + schematic.getName() + " at " + x + "," + y + "," + z, true);
			}
			else
			{
				this.plugin.logMessage("Failed to place " + this.schematic.getName() + " at " + x + "," + y + "," + z, true);
			}
		}
	}

}
