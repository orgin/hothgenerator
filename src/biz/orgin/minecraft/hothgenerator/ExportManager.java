package biz.orgin.minecraft.hothgenerator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.command.CommandSender;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.CuboidRegionSelector;

public class ExportManager {
	
	public static void main(String[] args)
	{
		System.out.println(String.format("'%4d'",1,2,3,4));
		
		int y1=5;
		int y2=7;
		
		
		for(int y=y1;y<=y2;y++)
		{
			System.out.println(y);
		}
		for(int y=y2;y>=y1;y--)
		{
			System.out.println(y);
		}

	}
	
	public static void export(HothGeneratorPlugin plugin, World world, CuboidRegionSelector rSelector, CommandSender sender, String filename, int maskid)
	{
		File dataFolder = plugin.getDataFolder();
		String path = dataFolder.getAbsolutePath() + "/custom";
		File customFolder = new File(path);
		if(!customFolder.exists())
		{
			customFolder.mkdir();
		}
		path = path + "/" + filename;
		
		File file = new File(path);
		
		
		try
		{
			if(file.exists())
			{
				file.delete();
			}
			file.createNewFile();

			FileWriter writer = new FileWriter(file);
			
			
			CuboidRegion cRegion = (CuboidRegion)rSelector.getRegion();
			Vector pos1 = cRegion.getPos1();
			Vector pos2 = cRegion.getPos2();

			int t = 0;

			int x1 = pos1.getBlockX();
			int y1 = pos1.getBlockY();
			int z1 = pos1.getBlockZ();

			int x2 = pos2.getBlockX();
			int y2 = pos2.getBlockY();
			int z2 = pos2.getBlockZ();

			if(y1!=y2)
			{

				if(!(x1==x2 && y1==y2 && z1==z2))
				{
					if(x1>x2)
					{
						t = x1;
						x1 = x2;
						x2 = t;
					}

					if(y1>y2)
					{
						t = y1;
						y1 = y2;
						y2 = t;
					}

					if(z1>z2)
					{
						t = z1;
						z1 = z2;
						z2 = t;
					}

					plugin.sendMessage(sender, "Exporting region: ("+x1+","+y1+","+z1+") -> (" +x2+","+y2+","+z2+")");

					int exported = 0;
					
					int width = Math.abs(x2-x1)+1;
					int length = Math.abs(z2-z1)+1;
					int height = Math.abs(y2-y1)+1;
					
					writer.write("ENABLED: false\n");
					writer.write("WIDTH: " + width + "\n");
					writer.write("LENGTH: " + length + "\n");
					writer.write("HEIGHT: " + height + "\n");
					writer.write("TYPE: [Edit]\n");
					writer.write("RARITY: [Edit]\n");
					writer.write("RANDOM: [Edit]\n");
					writer.write("LOOT: [Edit]\n");
					writer.write("LOOTMIN: [Edit]\n");
					writer.write("LOOTMAX: [Edit]\n");
					writer.write("MATRIX:\n");
					
					StringBuffer mySB1 = new StringBuffer();
					StringBuffer mySB2 = new StringBuffer();
					
					int layer = 0;

					//for(int y=y1;y<=y2;y++)
					for(int y=y2;y>=y1;y--)
					{
						writer.write("# Layer " + layer + "\n");
						layer++;
						
						for(int z=z1;z<=z2;z++)
						{
							mySB1.setLength(0);
							mySB2.setLength(0);
							
							for(int x=x1;x<=x2;x++)
							{
								Block block = world.getBlockAt(x, y, z);
								int typeID = block.getTypeId();
								int data = block.getData();
								
								if(typeID==maskid)
								{
									typeID = -1;
									data = 0;
								}
								
								if(block.getType().equals(Material.MOB_SPAWNER))
								{
									CreatureSpawner spawner = (CreatureSpawner)block.getState();
									data = spawner.getSpawnedType().getTypeId();
								}
								
								if(x!=x1)
								{
									mySB1.append(",");
									mySB2.append(",");
								}
								mySB1.append(String.format("%4d",typeID));
								mySB2.append(String.format("%3d",data));
								
								exported++;
							}
							
							writer.write(mySB1.toString());
							writer.write(",  ");
							writer.write(mySB2.toString());
							writer.write("\n");
						}
					}
					
					writer.flush();

					plugin.sendMessage(sender, "Done. Exported " + exported + " blocks to " + filename);
				}
				else
				{
					plugin.sendMessage(sender, "ERROR: Selected region is just one block");
				}	
			}
			else
			{
				plugin.sendMessage(sender, "ERROR: Selected region is just one level high");
			}
		}
		catch(IOException e)
		{
			plugin.sendMessage(sender, "ERROR: Failed to write to file");
		}
		catch(Exception e)
		{
			plugin.sendMessage(sender, "ERROR: Selected region is incomplete");
		}

	}

	
}
