
package biz.orgin.minecraft.hothgenerator;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
	public static final String LOGFILE = "plugins/HothGenerator/hoth.log";
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private BlockPlaceManager blockPlaceManager;
	private BlockBreakManager blockBreakManager;
	private ToolUseManager toolUseManager;
	private BlockMeltManager blockMeltManager;
	private BlockGrowManager blockGrowManager;
	private StructureGrowManager structureGrowManager;
	private BlockSpreadManager blockSpreadManager;
	
	private FileConfiguration config;
	
    public void onEnable()
    { 
    	this.blockPlaceManager = new BlockPlaceManager(this);
    	this.blockBreakManager = new BlockBreakManager(this);
    	this.toolUseManager = new ToolUseManager(this);
    	this.blockMeltManager = new BlockMeltManager(this);
    	this.blockGrowManager = new BlockGrowManager(this);
    	this.structureGrowManager = new StructureGrowManager(this);
    	this.blockSpreadManager = new BlockSpreadManager(this);
    	
    	this.getServer().getPluginManager().registerEvents(this.blockPlaceManager, this);
    	this.getServer().getPluginManager().registerEvents(this.blockBreakManager, this);
    	this.getServer().getPluginManager().registerEvents(this.toolUseManager, this);
    	this.getServer().getPluginManager().registerEvents(this.blockMeltManager, this);
    	this.getServer().getPluginManager().registerEvents(this.blockGrowManager, this);
    	this.getServer().getPluginManager().registerEvents(this.structureGrowManager, this);
    	this.getServer().getPluginManager().registerEvents(this.blockSpreadManager, this);
    	
    	
		this.saveDefaultConfig();
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
	
	/**
	 * Check if the current world is a hHrld. Hoth worlds are defined in the config.
	 * The comparison is case insensitive.
	 * @param world The world to check
	 * @return True if the world is in the hoth world list
	 */
	public boolean isHothWorld(World world)
	{
		List<String> list = this.config.getStringList("hothworlds");
		
		if(list!=null)
		{
			String current = world.getName().toLowerCase();
			
			for(int i=0;i<list.size();i++)
			{
				String item = list.get(i).toLowerCase();
				
				if(item.equals(current))
				{
					return true;
				}
			}
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
	
	public boolean isDebug()
	{
		return this.config.getBoolean("hoth.debug", false);
	}
	
	public boolean isItemInfoTool()
	{
		return this.config.getBoolean("hoth.iteminfotool", false);
	}
	
	public boolean isSmoothSnow()
	{
		return this.config.getBoolean("hoth.smoothsnow", true);
	}

	public void debugMessage(String message)
	{
		if(this.isDebug())
		{
			this.getLogger().info(message);
		}
	}
	
	public void logMessage(String message)
	{
		this.logMessage(message, false);
	}

	public void logMessage(String message, boolean onConsole)
	{
		if(onConsole)
		{
			this.debugMessage(message);
		}
		
		try
		{
			FileWriter writer = new FileWriter(HothGeneratorPlugin.LOGFILE, true);
			writer.write(HothGeneratorPlugin.dateFormat.format(new Date()) + " " + message);
			writer.write("\n");
			writer.close();
		}
		catch(IOException e)
		{
			this.getLogger().info("Failed to write to log file " + HothGeneratorPlugin.LOGFILE);
		}
		
	}
}