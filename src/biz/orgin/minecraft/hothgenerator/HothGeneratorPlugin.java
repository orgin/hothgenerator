
package biz.orgin.minecraft.hothgenerator;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main plugin class
 * @author orgin
 *
 */
public class HothGeneratorPlugin extends JavaPlugin
{
	private BlockPlaceManager blockPlaceManager;
	private BlockBreakManager blockBreakManager;
	private ToolUseManager toolUseManager;
	private BlockMeltManager blockMeltManager;
	
	private FileConfiguration config;
	
    public void onEnable()
    { 
    	this.blockPlaceManager = new BlockPlaceManager(this);
    	this.blockBreakManager = new BlockBreakManager(this);
    	this.toolUseManager = new ToolUseManager(this);
    	this.blockMeltManager = new BlockMeltManager(this);
    	
    	this.getServer().getPluginManager().registerEvents(this.blockPlaceManager, this);
    	this.getServer().getPluginManager().registerEvents(this.blockBreakManager, this);
    	this.getServer().getPluginManager().registerEvents(this.toolUseManager, this);
    	this.getServer().getPluginManager().registerEvents(this.blockMeltManager, this);
    	
    	this.config = this.getConfig();
    }
    
 	
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id)
	{
		if (id != null && !id.isEmpty())
		{
			try
			{
				int height = 256;
				height = Integer.parseInt(id);
				if (height <= 0)
				{
					height = 256;
				}
				return new HothGenerator(this, height);
			}
			catch (NumberFormatException e)
			{
				
			}
		}
		return new HothGenerator(this);
	}
	
	public static int maxHeight(World world, int size)
	{
		if (world.getMaxHeight() < size)
		{
			return world.getMaxHeight();
		}
		else
		{
			return size;
		}
	}
	
	public boolean isHothWorld(World world)
	{
		List<String> list = this.config.getStringList("hothworlds");
		
		if(list!=null)
		{
			System.out.println("Hothworlds:");
			for(int i=0;i<list.size();i++)
			{
				System.out.println(list.get(i));
			}
			
			String current = world.getName();
			
			for(int i=0;i<list.size();i++)
			{
				if(list.get(i).equals(current))
				{
					return true;
				}
			}
		}
		else
		{
			System.out.println("Config file was empty..");
		}
		
		return false;
	}
	
	public boolean blockIsHighest(World world, Block block)
	{
		int x = block.getX();
		int y = block.getY();
		int z = block.getZ();
		int airID = Material.AIR.getId();
		
		if(y<255)
		{
			y++;
			while(y<256)
			{
				if(world.getBlockTypeIdAt(x, y, z) != airID)
				{
					return false;
				}
				y++;
			}
		}
		
		return true;
	}
	
	public boolean canPlaceLiquid(World world, Block block)
	{
		int y = block.getY();
		
		return !(y>63 || (y>26 && this.blockIsHighest(world, block)));
	}

}