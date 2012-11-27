
package biz.orgin.minecraft.hothgenerator;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
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
	private CreatureSpawnManager creatureSpawnManager;
	
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
    	this.creatureSpawnManager = new CreatureSpawnManager(this);
    	
    	this.getServer().getPluginManager().registerEvents(this.blockPlaceManager, this);
    	this.getServer().getPluginManager().registerEvents(this.blockBreakManager, this);
    	this.getServer().getPluginManager().registerEvents(this.toolUseManager, this);
    	this.getServer().getPluginManager().registerEvents(this.blockMeltManager, this);
    	this.getServer().getPluginManager().registerEvents(this.blockGrowManager, this);
    	this.getServer().getPluginManager().registerEvents(this.structureGrowManager, this);
    	this.getServer().getPluginManager().registerEvents(this.blockSpreadManager, this);
    	this.getServer().getPluginManager().registerEvents(this.creatureSpawnManager, this);
    	
    	
		this.saveDefaultConfig();
    	this.config = this.getConfig();
    }
    
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
    	// Log all actions
    	StringBuffer full = new StringBuffer();
    	for(int i=0;i<args.length;i++)
    	{
    		if(full.length()>0)
    		{
        		full.append(" ");
    		}
    		full.append(args[i]);
    	}
    	this.getLogger().info("[PLAYER COMMAND] " + sender.getName() + ": /" + cmd.getName() + " " + full.toString());

    	
    	if(cmd.getName().equalsIgnoreCase("hothreload"))
    	{
    		this.sendMessage(sender, "&bReloading HothGenerator config...");
    		
    		this.saveDefaultConfig(); // In case the file has been deleted
    		this.reloadConfig();
    		this.config = this.getConfig();

    		this.sendMessage(sender, "&b... reloading done.");
    		
    		return true;
    	}

    	return false;
    }
    
    
    
    public void sendMessage(CommandSender sender, String message)
    {
    	sender.sendMessage(MessageFormatter.format(message));
    }

    public void sendMessage(Server sender, String message)
    {
    	sender.broadcastMessage(MessageFormatter.format(message));
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
	 * Check if the current world is a hothworld. Hoth worlds are defined in the config.
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
	
	/**
	 * Check if the given block is the highest at that x,z position.
	 * Only air blocks are treated as empty.
	 * @param world The world to check
	 * @param block The block to check
	 * @return true if it's the highest block
	 */
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
	
	/**
	 * Get the highest block y value for the given world and coordinate.
	 * Only air is considered empty.
	 * @param world
	 * @param x
	 * @param z
	 * @return
	 */
	public int getHighestBlockYAt(World world, int x, int z)
	{
		int airID = Material.AIR.getId();
		
		int y = world.getMaxHeight();
		while(y>0)
		{
			if(world.getBlockTypeIdAt(x, y, z) != airID)
			{
				return y;
			}
			y--;
		}
		
		return -1;
	}
	
	/**
	 * Returns true if the given block is allowed to be liquid
	 * @param world The world to check
	 * @param block The block to check
	 * @return True if allowed
	 */
	public boolean canPlaceLiquid(World world, Block block)
	{
		int y = block.getY();
		
		return !(y>63 || (y>26 && this.blockIsHighest(world, block)));
	}
	
	/* Config - Start
	 * 
	 * This section defines methods for retrieving information from the config file.
	 * Some of the methods contain non standard behavior which is why the config object
	 * isn't used directly where config information is needed throughout this plugin.
	 */
	
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
	
	public int getStructureGardens()
	{
		int result = this.config.getInt("hoth.structure.gardens", 2);
		if(result<0 || result>10)
		{
			result = 2;
		}
		return result;
	}

	public int getStructureDomes()
	{
		int result = this.config.getInt("hoth.structure.domes", 2);
		if(result<0 || result>10)
		{
			result = 2;
		}
		return result;
	}

	public int getStructureDomesPlantstem()
	{
		return this.config.getInt("hoth.structure.domes.plantstem", 19);
	}

	public int getStructureDomesPlanttop()
	{
		return this.config.getInt("hoth.structure.domes.planttop", 89);
	}
	
	public int getStructureDomesFloor()
	{
		return this.config.getInt("hoth.structure.domes.floor", 3);
	}

	public int getStructureDomesFloorrandom()
	{
		return this.config.getInt("hoth.structure.domes.floorrandom", 89);
	}
	
	public boolean isStructureDomesPlaceminidome()
	{
		return this.config.getBoolean("hoth.structure.domes.placeminidome", true);
	}

	public int getStructureBases()
	{
		int result = this.config.getInt("hoth.structure.bases", 2);
		if(result<0 || result>10)
		{
			result = 2;
		}
		return result;
	}

	public boolean isStructureBasesSpawner()
	{
		return this.config.getBoolean("hoth.structure.bases.spawner", true);
	}

	public int getStructureMazes()
	{
		int result = this.config.getInt("hoth.structure.mazes", 2);
		if(result<0 || result>10)
		{
			result = 2;
		}
		return result;
	}
	
	public int getStructureMazesMinrooms()
	{
		int min = this.config.getInt("hoth.structure.mazes.minrooms", 8);
		int max = this.config.getInt("hoth.structure.mazes.maxrooms", 32);
		
		if(min>max || min<1 || max<1 || max>100)
		{
			min = 8;
		}
		
		return min;
	}

	public int getStructureMazesMaxrooms()
	{
		int min = this.config.getInt("hoth.structure.mazes.minrooms", 8);
		int max = this.config.getInt("hoth.structure.mazes.maxrooms", 32);
		
		if(min>max || min<1 || max<1 || max>100)
		{
			max = 32;
		}
		
		return max;
	}
	
	public boolean isStructureMazesSpawner()
	{
		return this.config.getBoolean("hoth.structure.mazes.spawner", true);
	}

	public boolean isGenerateLogs()
	{
		return this.config.getBoolean("hoth.generate.logs", true);
	}

	public int getGenerateCaves()
	{
		int result = this.config.getInt("hoth.generate.caves", 2);
		if(result<0 || result>10)
		{
			result = 2;
		}
		return result;
	}
	
	public boolean isGenerateOres()
	{
		return this.config.getBoolean("hoth.generate.ores", true);
	}

	public boolean isRulesDropice()
	{
		return this.config.getBoolean("hoth.rules.dropice", true);
	}

	public boolean isRulesDropsnow()
	{
		return this.config.getBoolean("hoth.rules.dropsnow", true);
	}

	public boolean isRulesFreezewater()
	{
		return this.config.getBoolean("hoth.rules.freezewater", true);
	}
	
	public boolean isRulesFreezelava()
	{
		return this.config.getBoolean("hoth.rules.freezelava", true);
	}
	
	public boolean isRulesPlantsgrow()
	{
		return this.config.getBoolean("hoth.rules.plantsgrow", false);
	}

	public boolean isRulesGrassspread()
	{
		return this.config.getBoolean("hoth.rules.grassspread", false);
	}
	
	public boolean isRulesStopmelt()
	{
		return this.config.getBoolean("hoth.rules.stopmelt", true);
	}

	public boolean isRulesLimitslime()
	{
		return this.config.getBoolean("hoth.rules.limitslime", true);
	}
	
	/*
	  +structure.gardens: 2
	  +structure.domes: 2
	  +structure.domes.plantstem: 19
	  +structure.domes.planttop: 89
	  +structure.domes.floor: 3
	  +structure.domes.floorrandom: 89
	  +structure.domes.placeminidome: true
	  +structure.bases: 2
	  +structure.bases.spawner: true
	  +structure.roomcluster: 2
	  +structure.roomcluster.minrooms: 8
	  +structure.roomcluster.maxrooms: 32
	  +structure.roomcluster.spawner: true
	  +generate.logs: true
	  +generate.caves: 2
	  +generate.ores: true
	  +rules.dropice: true
	  +rules.dropsnow: true
	  +rules.freezewater: true
	  +rules.freezelava: true
	  +rules.plantsgrow: false
	  +rules.grassspread: false
	  +rules.stopmelt: true
	  +rules.limitslime: true
	  */
	
	
	/* Config - End */

	/**
	 * Logs the given message on the console as an info message.
	 * Messages are only logged if the hoth.debug flag is set to true in the config file.
	 * @param message The message to log.
	 */
	public void debugMessage(String message)
	{
		if(this.isDebug())
		{
			this.getLogger().info(message);
		}
	}
	
	/**
	 * Sends a message to the log file.
	 * @param message The message to send.
	 */
	public void logMessage(String message)
	{
		this.logMessage(message, false);
	}

	/**
	 * Sends a message to the log file and optionally to the console.
	 * The console message is sent by the debugMessage() method.
	 * @param message The message to send.
	 * @param onConsole Whether it should be sent to teh console or not.
	 */
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