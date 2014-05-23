
package biz.orgin.minecraft.hothgenerator;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import biz.orgin.minecraft.hothgenerator.schematic.LoadedSchematic;
import biz.orgin.minecraft.hothgenerator.schematic.Schematic;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldedit.regions.CuboidRegionSelector;
import com.sk89q.worldedit.regions.RegionSelector;

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
	private BlockFormManager blockFormManager;
	private StructureGrowManager structureGrowManager;
	private BlockSpreadManager blockSpreadManager;
	private CreatureSpawnManager creatureSpawnManager;
	private PlayerEnvironmentManager playerFreezeManager;
	private BlockGravityManager blockGravityManager;
	private RegionManager regionManager;
	private MobSpawnManager mobSpawnManager;
	private DagobahSpiderForestManager spiderForestManager = null;
	private HothTaskManager taskManager;
	
	private FileConfiguration config;
	
	private UndoBuffer undoBuffer;
	
	private long id = System.currentTimeMillis();
	
	public long getID()
	{
		return this.id;
	}
	
    public void onEnable()
    { 
    	HothGenerator.setPlugin(this);
    	
    	this.blockPlaceManager = new BlockPlaceManager(this);
    	this.blockBreakManager = new BlockBreakManager(this);
    	this.toolUseManager = new ToolUseManager(this);
    	this.blockMeltManager = new BlockMeltManager(this);
    	this.blockGrowManager = new BlockGrowManager(this);
    	this.blockFormManager = new BlockFormManager(this);
    	this.structureGrowManager = new StructureGrowManager(this);
    	this.blockSpreadManager = new BlockSpreadManager(this);
    	this.creatureSpawnManager = new CreatureSpawnManager(this);
    	this.blockGravityManager = new BlockGravityManager(this);
    	this.regionManager = RegionManagerFactory.getRegionmanager(this);
    	
    	this.getServer().getPluginManager().registerEvents(this.blockPlaceManager, this);
    	this.getServer().getPluginManager().registerEvents(this.blockBreakManager, this);
    	this.getServer().getPluginManager().registerEvents(this.toolUseManager, this);
    	this.getServer().getPluginManager().registerEvents(this.blockMeltManager, this);
    	this.getServer().getPluginManager().registerEvents(this.blockGrowManager, this);
    	this.getServer().getPluginManager().registerEvents(this.blockFormManager, this);
    	this.getServer().getPluginManager().registerEvents(this.structureGrowManager, this);
    	this.getServer().getPluginManager().registerEvents(this.blockSpreadManager, this);
    	this.getServer().getPluginManager().registerEvents(this.creatureSpawnManager, this);
    	this.getServer().getPluginManager().registerEvents(this.blockGravityManager, this);
    	
		this.saveDefaultConfig();
    	this.config = this.getConfig();
    	LootGenerator.load(this);
    	CustomGenerator.load(this);
    	OreGenerator.load(this);
    	this.saveResource("custom/example.sm", true);
    	this.saveResource("custom/example.ll", true);
    	this.saveResource("custom/example_ores.ol", true);
    	
    	this.regionManager.load();

    	this.playerFreezeManager = new PlayerEnvironmentManager(this);
    	this.mobSpawnManager = new MobSpawnManager(this);
    	if(this.spiderForestManager==null)
    	{
    		this.spiderForestManager = new DagobahSpiderForestManager(this);
    	}
    	if(this.taskManager==null)
    	{
    		this.taskManager = new HothTaskManager(this);
    	}
    	else
    	{
    		this.taskManager.resume();
    	}
    	
    	this.undoBuffer = new UndoBuffer();
    }
    
    public void onDisable()
    {
    	if(this.playerFreezeManager!=null)
    	{
    		this.playerFreezeManager.stop();
    	}
    	if(this.mobSpawnManager!=null)
    	{
    		this.mobSpawnManager.stop();
    	}
    	if(this.spiderForestManager!=null)
    	{
    		this.mobSpawnManager.stop();
    	}
    	if(this.taskManager!=null)
    	{
    		this.taskManager.pause();
    	}

    	// Unload worlds to make sure the ChunkGenerator is reloaded properly
    	List<World> worlds = this.getServer().getWorlds();
    	for(int i=0;i<worlds.size();i++)
    	{
			World world = worlds.get(i);
    		if(this.isHothWorld(world))
    		{
    			List<Player> players = world.getPlayers();
    			for(int j=0;j<players.size();j++)
    			{
    				Player player = players.get(j);
    				player.kickPlayer("Server reloading");
    			}
    			this.getServer().unloadWorld(world, true);
    		}
    	}

    }
    
    public void addTask(HothRunnable task)
    {
    	this.taskManager.addTask(task, false);
    }

    public void addTask(HothRunnable task, boolean prioritized)
    {
    	this.taskManager.addTask(task, prioritized);
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
    		
    		LootGenerator.load(this);
    		CustomGenerator.load(this);
        	OreGenerator.load(this);
        	this.saveResource("custom/example.sm", true);
        	this.saveResource("custom/example.ll", true);
        	this.saveResource("custom/example_ores.ol", true);
        	
        	this.regionManager.load();

        	if(this.playerFreezeManager!=null)
        	{
        		this.playerFreezeManager.stop();
        	}
    		this.playerFreezeManager = new PlayerEnvironmentManager(this);

    		if(this.mobSpawnManager!=null)
        	{
        		this.mobSpawnManager.stop();
        	}
        	this.mobSpawnManager = new MobSpawnManager(this);

    		this.sendMessage(sender, "&b... reloading done.");

    		return true;
    	}
    	else if(cmd.getName().equalsIgnoreCase("hothexport"))
       	{
    		if(args.length>0)
    		{
    			int maskid = -1;
    			
    			if(args.length==2)
    			{
    				try
    				{
    					maskid = Integer.parseInt(args[1]);
    					if(maskid<0)
    					{
    						this.sendMessage(sender, "&cERROR: Invalid mask: " + args[1]);
    						return false;
    					}
    				}
    				catch(NumberFormatException e)
    				{
    					this.sendMessage(sender, "&cERROR: Invalid mask: " + args[1]);
    					return false;
    				}
    			}
    			
	       		if(sender instanceof Player)
	       		{
	       			World world = ((Player) sender).getWorld();
	       			Plugin plugin = getServer().getPluginManager().getPlugin("WorldEdit");
	       			if(plugin!=null && plugin instanceof WorldEditPlugin)
	       			{
	       				WorldEditPlugin wePlugin = (WorldEditPlugin)plugin;
	       				Selection selection = wePlugin.getSelection((Player)sender);
	       				if(selection!=null && selection instanceof CuboidSelection)
	       				{
	       					CuboidSelection cSelection = (CuboidSelection)selection;
	        					
	       					RegionSelector rSelector = cSelection.getRegionSelector();
	       					if(rSelector!=null && rSelector instanceof CuboidRegionSelector)
	       					{
	       						ExportManager.export(this, world, (CuboidRegionSelector)rSelector, sender, args[0], maskid);
	       						CustomGenerator.load(this);
	       					}
							else
							{
								this.sendMessage(sender, "&cERROR: Selected region is not cuboid");
							}
						}
						else
						{
							this.sendMessage(sender, "&cERROR: Selected region is not cuboid");
						}
	       			}
	       			else
	       			{
	       				this.sendMessage(sender, "&cERROR: WorldEdit plugin not installed");
	       			}
		       	}
				return true;
    		}
       	}
    	else if(cmd.getName().equalsIgnoreCase("hothsavell"))
       	{
    		if(args.length>0)
    		{
    			LootGenerator generator = null;
    			String name = args[0].toLowerCase();
    			
    			if(name.equals("default.ll"))
    			{
    				generator = LootGenerator.getLootGenerator();
    			}
    			else
    			{
    				generator = LootGenerator.getLootGenerator(name);
    			}
    			
    			if(generator!=null)
    			{
    				try
    				{
    					generator.save(this);
    				}
    				catch(Exception e)
    				{
        				this.sendMessage(sender, "&cFailed to save loot list: " + name);
    				}
    			}
    			else
    			{
    				this.sendMessage(sender, "&cCould not find any loot list with name: " + name);
    			}
    			return true;
    		}
       	}
    	else if(cmd.getName().equalsIgnoreCase("hothregion"))
    	{
    		if(args.length>0)
    		{
    			String command = args[0].toLowerCase();
    			if(command.equals("info"))
    			{
    				if(args.length>1)
    				{
    					String region = args[1].toLowerCase();
    					if(this.regionManager.isValidRegion(region))
    					{
    						this.sendMessage(sender, "&9Region: " + region + ": " + this.regionManager.getInfo(region));
    					}
    					else
    					{
        					this.sendMessage(sender, "&cERROR: " + region + " is not a valid region");
    					}
    				}
    				else
    				{
    					this.sendMessage(sender, "Usage: /hothregion info [region]");
    				}
    				return true;
    			}
    			if(command.equals("remove"))
    			{
    				if(args.length>1)
    				{
    					String region = args[1].toLowerCase();
    					if(this.regionManager.isValidRegion(region))
    					{
    						this.regionManager.remove(region);
        					this.sendMessage(sender, "&bRegion removed");
    					}
    					else
    					{
        					this.sendMessage(sender, "&cERROR: " + region + " is not a valid region");
    					}
    				}
    				else
    				{
    					this.sendMessage(sender, "Usage: /hothregion info [region]");
    				}
    				return true;
    			}
    			else if(command.equals("flag"))
    			{
    				if(args.length>2)
    				{
    					String region = args[1].toLowerCase();
    					if(this.regionManager.isValidRegion(region))
    					{
    						String flag = args[2].toLowerCase();
	    					if(this.regionManager.isValidFlag(flag))
	    					{
	    						String value = "";
	    						for(int i=3;i<args.length;i++)
	    						{
	    							value = value + args[i] + " ";
	    						}
	    						value = value.trim();
		    					if(this.regionManager.isValidFlagValue(flag, value))
		    					{
		    						regionManager.set(region, flag, value);
		    						if(value.equals(""))
		    						{
		    							this.sendMessage(sender, "&bRegion flag &9" + flag + "&b cleared");
		    						}
		    						else
		    						{
		    							this.sendMessage(sender, "&bRegion flag &9" + flag + "&b set to &f" + value);
		    						}
		    					}
		    					else
		    					{
		    						this.sendMessage(sender, "&cERROR: Valid values for " + flag + " are: " + this.regionManager.getValidFlagValues(flag));
		    					}
	    					}
	    					else
	    					{
	    						this.sendMessage(sender, "&cERROR: Valid flags are: " + this.regionManager.getValidFlags() );
	    					}
    					}
    					else
    					{
        					this.sendMessage(sender, "&cERROR: " + region + " is not a valid region");
    					}
    				}
    				else
    				{
    					this.sendMessage(sender, "Usage: /hothregion flag [region] [flag] <value>");
    				}
    				return true;
    			}
    		}
    	}
    	else if(cmd.getName().equalsIgnoreCase("hothpaste"))
    	{
    		if(args.length>1 && sender instanceof Player && CustomGenerator.schematics!=null)
    		{
    			Player player = (Player)sender;
    			Location loc = player.getLocation();
    			World world = player.getWorld();
    			int x = loc.getBlockX();
    			int y = loc.getBlockY();
    			int z = loc.getBlockZ();

    			String method = args[0].toLowerCase();

				int direction = -1;
				String directionStr = "south";
				if(args.length>2)
				{
					directionStr = args[2].toLowerCase();
					if(directionStr.equals("south"))
					{
						direction = 0;
					}
					else if(directionStr.equals("west"))
					{
						direction = 1;
					}
					else if(directionStr.equals("north"))
					{
						direction = 2;
					}
					else if(directionStr.equals("east"))
					{
						direction = 3;
					}
				}
				else
				{
					direction = 0;
				}
				
				if(direction!=-1)
				{

					if(method.equals("ext"))
					{

						String schematic = args[1].toLowerCase();

						boolean found = false;

						for(int i=0;i<CustomGenerator.schematics.size() && found == false;i++)
						{
							LoadedSchematic lschem = CustomGenerator.schematics.elementAt(i);
							if(lschem.getName().toLowerCase().equals(schematic))
							{
								LoadedSchematic schem = lschem.cloneRotate(direction);
								this.sendMessage(sender, "&bPlacing " + schematic + " at " + x + "," + y + "," + z + " direction: " + directionStr);
								this.undoBuffer.pushBlob(player.getUniqueId(), HothUtils.getUndoBlob(this, world, schem, x, y, z));
								HothUtils.placeSchematic(this, world, schem, x, y, z, lschem.getLootMin(), lschem.getLootMax());

								found = true;
							}
						}

						if(!found)
						{
							this.sendMessage(sender, "&cCould not find schematic: " + schematic);
						}

						return true;
					}
					else if(method.equals("int"))
					{
						String schematic = args[1].toLowerCase();

						boolean found = false;
						
						List<Schematic> schematics = InternalSchematics.getSchematics(this);

						for(int i=0;i<schematics.size() && found == false;i++)
						{
							Schematic lschem = schematics.get(i);
							if(lschem.getName().toLowerCase().equals(schematic))
							{
								Schematic schem = HothUtils.rotateSchematic(direction, lschem);
								this.sendMessage(sender, "&bPlacing " + schematic + " at " + x + "," + y + "," + z + " direction = " + directionStr);
								this.undoBuffer.pushBlob(player.getUniqueId(), HothUtils.getUndoBlob(this, world, schem, x, y, z));
								HothUtils.placeSchematic(this, world, schem, x, y, z, 2, 10);

								found = true;
							}
						}

						if(!found)
						{
							this.sendMessage(sender, "&cCould not find schematic: " + schematic);
						}


						return true;
					}
				}
    		}
    	}
    	else if(cmd.getName().equalsIgnoreCase("hothundo"))
    	{
    		if(sender instanceof Player)
    		{
    			Player player = (Player)sender;
    			
    			Blob blob = this.undoBuffer.popBlob(player.getUniqueId());
    			if(blob!=null)
    			{
    				this.sendMessage(player, "&bUndo schedueled");
    				blob.instantiate();
    			}
    			else
    			{
    				this.sendMessage(player, "&bUndo buffer is empty");
    			}
    			
    			return true;
    		}

    	}
    	else if(cmd.getName().equalsIgnoreCase("hothlist"))
    	{
    		if(args.length>0 && sender instanceof Player && CustomGenerator.schematics!=null)
    		{
    			String mode = args[0];
    			if(mode.equals("int"))
    			{
    				this.sendMessage(sender, "&bInternal scematics:");
					List<Schematic> schematics = InternalSchematics.getSchematics(this);
    				for(int i=0;i<schematics.size();i++)
    				{
    					Schematic schematic = schematics.get(i);
    					
    					this.sendMessage(sender, "&b " + schematic.getName());
    				}
        			return true;
    			}
    			else if(mode.equals("ext"))
    			{
    				this.sendMessage(sender, "&bExternal scematics:");
    				for(int i=0;i<CustomGenerator.schematics.size();i++)
    				{
    					Schematic schematic = CustomGenerator.schematics.elementAt(i);
    					
    					this.sendMessage(sender, "&b " + schematic.getName());
    				}
        			return true;
    			}
    		}
    	}
    	else if(cmd.getName().equalsIgnoreCase("hothinfo"))
    	{
    		PluginDescriptionFile descriptionFile = this.getDescription();
    		String version = descriptionFile.getVersion();
    		String name = descriptionFile.getName();
    		String website = descriptionFile.getWebsite();
    		List<String> authors = descriptionFile.getAuthors();
    		String description = descriptionFile.getDescription();
    		
    		this.sendMessage(sender, "&b" + name + " " + version);
    		this.sendMessage(sender, "&b" + description);
    		this.sendMessage(sender, "&b" + website);
    		
    		if(sender instanceof Player)
    		{
    			Player player = (Player)sender;
    			World world = player.getWorld();
    			this.sendMessage(sender, "&bWorld: " + world.getName() + " type: " + this.getWorldType(world));
    		}

    		String author = "";
    		
    		for(String authr : authors)
    		{
    			if(author.length()!=0)
    			{
    				author = author + ", ";
    			}
    			author = author + authr;
    		}

    		this.sendMessage(sender, "&bCreated by: " + author);
    	}
    	return false;
    }
    
    public void sendMessage(Player sender, String message)
    {
    	sender.sendMessage(MessageFormatter.format(message));
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
				int height = this.getHeight();
				height = Integer.parseInt(id);
				if (height <= 0)
				{
					height = this.getHeight();
				}
				return new HothGenerator(height);
			}
			catch (NumberFormatException e)
			{
				return new HothGenerator(this.getHeight());
			}
		}
		return new HothGenerator();
	}
	
	public int getHeight()
	{
		return 256;
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
	
	public String getWorldType(World world)
	{
		List<String> list = this.config.getStringList("hothworlds");
		String name = world.getName().toLowerCase();
		
		for(int i=0;i<list.size();i++)
		{
			String worldName = list.get(i);
			if(worldName.equals(name))
			{
				String type = this.config.getString("hothworldsdata." + worldName + ".type", "hoth");
				if(type.equals("hoth") || type.equals("tatooine") || type.equals("dagobah"))
				{
					return type;
				}
				else
				{
					return "hoth";
				}
			}
		}
		
		return "hoth";
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
		
		if(y<255)
		{
			y++;
			while(y<256)
			{
				if(!world.getBlockAt(x, y, z).equals(Material.AIR))
				{
					return false;
				}
				y++;
			}
		}
		
		return true;
	}
	
	public void addSpiderForest(World world, int x, int y, int z, int size)
	{
    	if(this.spiderForestManager==null)
    	{
    		this.spiderForestManager = new DagobahSpiderForestManager(this);
    	}

		this.spiderForestManager.add(world, x, y, z, size);
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
		int surfaceOffset = this.getWorldSurfaceoffset(world);
		
		return !(y>(63 + surfaceOffset) || (y>(26 + surfaceOffset) && this.blockIsHighest(world, block)));
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
	
	
	private int getConfigInt(World world, String tag, int def)
	{
		String name = world.getName();
		String worldPath = "hothworldsdata." + name + "." + tag;
		String defaultPath = "hoth." + tag;

		if(this.config.isSet(worldPath))
		{
			return this.config.getInt(worldPath, def);
		}
		else
		{
			return this.config.getInt(defaultPath, def);
		}
	}
	
	private boolean getConfigBoolean(World world, String tag, boolean def)
	{
		String name = world.getName();
		String worldPath = "hothworldsdata." + name + "." + tag;
		String defaultPath = "hoth." + tag;

		if(this.config.isSet(worldPath))
		{
			return this.config.getBoolean(worldPath, def);
		}
		else
		{
			return this.config.getBoolean(defaultPath, def);
		}
	}

	private String getConfigString(World world, String tag, String def)
	{
		String name = world.getName();
		String worldPath = "hothworldsdata." + name + "." + tag;
		String defaultPath = "hoth." + tag;

		if(this.config.isSet(worldPath))
		{
			return this.config.getString(worldPath, def);
		}
		else
		{
			return this.config.getString(defaultPath, def);
		}
	}

	public int getWorldSurfaceoffset(World world)
	{
		int result = this.getConfigInt(world, "world.surfaceoffset", 0);
		
		if(result<0)
		{
			result = 0;
		}
		else if(result>127)
		{
			result = 127;
		}
		return result;
	}


	
	public int getStructureSpikesRarity(World world)
	{
		int result = this.getConfigInt(world, "structure.spikes.rarity", 2);
		
		if(result<0 || result>10)
		{
			result = 2;
		}
		return result;
	}

	public int getStructureGardensRarity(World world)
	{
		int result = this.getConfigInt(world, "structure.gardens.rarity", 2);
		if(result<0 || result>10)
		{
			result = 2;
		}
		return result;
	}

	public int getStructureDomesRarity(World world)
	{
		int result = this.getConfigInt(world, "structure.domes.rarity", 3);
		if(result<0 || result>10)
		{
			result = 2;
		}
		return result;
	}

	public int getStructureDomesPlantstem(World world)
	{
		return this.getConfigInt(world, "structure.domes.plantstem", 19);
	}

	public int getStructureDomesPlanttop(World world)
	{
		return this.getConfigInt(world, "structure.domes.planttop", 89);
	}
	
	public int getStructureDomesFloor(World world)
	{
		return this.getConfigInt(world, "structure.domes.floor", 3);
	}

	public int getStructureDomesFloorrandom(World world)
	{
		return this.getConfigInt(world, "structure.domes.floorrandom", 89);
	}
	
	public boolean isStructureDomesPlaceminidome(World world)
	{
		return this.getConfigBoolean(world, "structure.domes.placeminidome", true);
	}

	public int getStructureBasesRarity(World world)
	{
		int result = this.getConfigInt(world, "structure.bases.rarity", 2);
		if(result<0 || result>10)
		{
			result = 2;
		}
		return result;
	}

	public boolean isStructureBasesSpawner(World world)
	{
		return this.getConfigBoolean(world, "structure.bases.spawner", true);
	}

	public int getStructureMazesRarity(World world)
	{
		int result = this.getConfigInt(world, "structure.mazes.rarity", 2);
		if(result<0 || result>10)
		{
			result = 2;
		}
		return result;
	}
	
	public int getStructureMazesMinrooms(World world)
	{
		int min = this.getConfigInt(world, "structure.mazes.minrooms", 8);
		int max = this.getConfigInt(world, "structure.mazes.maxrooms", 32);
		
		if(min>max || min<1 || max<1 || max>100)
		{
			min = 8;
		}
		
		return min;
	}

	public int getStructureMazesMaxrooms(World world)
	{
		int min = this.getConfigInt(world, "structure.mazes.minrooms", 8);
		int max = this.getConfigInt(world, "structure.mazes.maxrooms", 32);
		
		if(min>max || min<1 || max<1 || max>100)
		{
			max = 32;
		}
		
		return max;
	}
	
	public boolean isStructureMazesSpawner(World world)
	{
		return this.getConfigBoolean(world, "structure.mazes.spawner", true);
	}

	public int getStructureSkeletonsRarity(World world)
	{
		int result = this.getConfigInt(world, "structure.skeletons.rarity", 2);
		if(result<0 || result>10)
		{
			result = 2;
		}
		return result;
	}
	
	public int getStructureOasisRarity(World world)
	{
		int result = this.getConfigInt(world, "structure.oasis.rarity", 2);
		if(result<0 || result>10)
		{
			result = 2;
		}
		return result;
	}
	
	public int getStructureSandCastleRarity(World world)
	{
		int result = this.getConfigInt(world, "structure.sandcastle.rarity", 2);
		if(result<0 || result>10)
		{
			result = 2;
		}
		return result;
	}

	public int getStructureVillageRarity(World world)
	{
		int result = this.getConfigInt(world, "structure.village.rarity", 2);
		if(result<0 || result>10)
		{
			result = 2;
		}
		return result;
	}

	public int getStructureSuperGardenRarity(World world)
	{
		int result = this.getConfigInt(world, "structure.supergarden.rarity", 2);
		if(result<0 || result>10)
		{
			result = 2;
		}
		return result;
	}
	
	public int getStructureSarlaccRarity(World world)
	{
		int result = this.getConfigInt(world, "structure.sarlacc.rarity", 2);
		if(result<0 || result>10)
		{
			result = 2;
		}
		return result;
	}

	public int getStructureHugeTreeRarity(World world)
	{
		int result = this.getConfigInt(world, "structure.hugetree.rarity", 2);
		if(result<0 || result>10)
		{
			result = 2;
		}
		return result;
	}

	public int getStructureSpiderForestRarity(World world)
	{
		int result = this.getConfigInt(world, "structure.spiderforest.rarity", 2);
		if(result<0 || result>10)
		{
			result = 2;
		}
		return result;
	}

	public int getStructureSwampTempleRarity(World world)
	{
		int result = this.getConfigInt(world, "structure.swamptemple.rarity", 2);
		if(result<0 || result>10)
		{
			result = 2;
		}
		return result;
	}

	public int getStructureTreeHutRarity(World world)
	{
		int result = this.getConfigInt(world, "structure.treehut.rarity", 2);
		if(result<0 || result>10)
		{
			result = 2;
		}
		return result;
	}

	public boolean isGenerateLogs(World world)
	{
		return this.getConfigBoolean(world, "generate.logs", true);
	}

	public int getGenerateCavesRarity(World world)
	{
		int result = this.getConfigInt(world, "generate.caves.rarity", 2);
		if(result<0 || result>10)
		{
			result = 2;
		}
		return result;
	}
	
	public boolean isGenerateOres(World world)
	{
		return this.getConfigBoolean(world, "generate.ores", true);
	}

	public boolean isGenerateExtendedOre()
	{
		return this.config.getBoolean("hoth.generate.extendedore", false);
	}

	public boolean isGenerateCactuses(World world)
	{
		return this.getConfigBoolean(world, "generate.cactuses", true);
	}

	public boolean isGenerateShrubs(World world)
	{
		return this.getConfigBoolean(world, "generate.shrubs", true);
	}

	public boolean isGenerateMushroomHuts(World world)
	{
		return this.getConfigBoolean(world, "generate.mushroomhuts", true);
	}

	public boolean isRulesDropice(Location location)
	{
		return this.regionManager.getBoolean("dropice", location, this.getConfigBoolean(location.getWorld(), "rules.dropice", true));
	}

	public boolean isRulesDroppackedice(Location location)
	{
		return this.regionManager.getBoolean("droppackedice", location, this.getConfigBoolean(location.getWorld(), "rules.droppackedice", true));
	}

	public boolean isRulesDropsnow(Location location)
	{
		return this.regionManager.getBoolean("dropsnow", location, this.getConfigBoolean(location.getWorld(), "rules.dropsnow", true));
	}

	public boolean isRulesFreezewater(Location location)
	{
		return this.regionManager.getBoolean("freezewater", location, this.getConfigBoolean(location.getWorld(), "rules.freezewater", true));
	}
	
	public boolean isRulesFreezelava(Location location)
	{
		return this.regionManager.getBoolean("freezelava", location, this.getConfigBoolean(location.getWorld(), "rules.freezelava", true));
	}
	
	public boolean isRulesPlantsgrow(Location location)
	{
		return this.regionManager.getBoolean("plantsgrow", location, this.getConfigBoolean(location.getWorld(), "rules.plantsgrow", false));
	}

	public boolean isRulesGrassspread(Location location)
	{
		return this.regionManager.getBoolean("grassspread", location, this.getConfigBoolean(location.getWorld(), "rules.grassspread", false));
	}
	
	public boolean isRulesStopmelt(Location location)
	{
		return this.regionManager.getBoolean("stopmelt", location, this.getConfigBoolean(location.getWorld(), "rules.stopmelt", true));
	}

	public boolean isRulesLimitslime(Location location)
	{
		return this.regionManager.getBoolean("limitslime", location, this.getConfigBoolean(location.getWorld(), "rules.limitslime", true));
	}
	
	public boolean isRulesSnowgravity(Location location)
	{
		return this.regionManager.getBoolean("snowgravity", location, this.getConfigBoolean(location.getWorld(), "rules.snowgravity", false));
	}

	public int getRulesEnvironmentPeriod()
	{
	    int period = this.config.getInt("hoth.rules.environment.period", 0);

	    if(period<0)
	    {
	    	period = 5;
	    }
	    return period;
	}

	public int getRulesFreezeDamage(Location location)
	{
	    int damage = this.regionManager.getInt("freeze.damage", location, this.getConfigInt(location.getWorld(), "rules.freeze.damage", 2));
	    if(damage<0)
	    {
	    	damage = 2;
	    }
	    return damage;
	}

	public int getRulesFreezeStormdamage(Location location)
	{
	    int damage = this.regionManager.getInt("freeze.stormdamage", location, this.getConfigInt(location.getWorld(), "rules.freeze.stormdamage", 1));
	    if(damage<0)
	    {
	    	damage = 2;
	    }
	    return damage;
	}

	public String getRulesFreezeMessage(Location location)
	{
		return this.regionManager.get("freeze.message", location, this.getConfigString(location.getWorld(), "rules.freeze.message", "&bYou are freezing. Find shelter!"));
	}
	
	
	public int getRulesHeatDamage(Location location)
	{
	    int damage = this.regionManager.getInt("heat.damage", location, this.getConfigInt(location.getWorld(), "rules.heat.damage", 2));
	    if(damage<0)
	    {
	    	damage = 2;
	    }
	    return damage;
	}

	public String getRulesHeatMessage1(Location location)
	{
		return this.regionManager.get("heat.message1", location, this.getConfigString(location.getWorld(), "rules.heat.message1", "&6The water removes your thirst."));
	}

	public String getRulesHeatMessage2(Location location)
	{
		return this.regionManager.get("heat.message2", location, this.getConfigString(location.getWorld(), "rules.heat.message2", "&6Your are starting to feel thirsty."));
	}

	public String getRulesHeatMessage3(Location location)
	{
		return this.regionManager.get("heat.message3", location, this.getConfigString(location.getWorld(), "rules.heat.message3", "&6Your feel very thirsty."));
	}

	public String getRulesHeatMessage4(Location location)
	{
		return this.regionManager.get("heat.message4", location, this.getConfigString(location.getWorld(), "rules.heat.message4", "&6You are exhausted from the heat. Find water or shelter!"));
	}

	public int getRulesMosquitoDamage(Location location)
	{
	    int damage = this.regionManager.getInt("mosquito.damage", location, this.getConfigInt(location.getWorld(), "rules.mosquito.damage", 1));
	    if(damage<0)
	    {
	    	damage = 1;
	    }
	    return damage;
	}
	
	public int getRulesMosquitoRarity(Location location)
	{
		int result = this.regionManager.getInt("mosquito.rarity", location, this.getConfigInt(location.getWorld(), "rules.mosquito.rarity", 5));
		if(result<1 || result>10)
		{
			result = 2;
		}
		return result;
	}

	public int getRulesMosquitoRunFree(Location location)
	{
		int result = this.regionManager.getInt("mosquito.runfree", location, this.getConfigInt(location.getWorld(), "rules.mosquito.runfree", 10));
		if(result<10 || result>100)
		{
			result = 10;
		}
		return result;
	}

	public String getRulesMosquitoMessage1(Location location)
	{
		return this.regionManager.get("mosquito.message1", location, this.getConfigString(location.getWorld(), "rules.mosquito.message1", "&2You seem to have lost the mosquito swarm."));
	}

	public String getRulesMosquitoMessage2(Location location)
	{
		return this.regionManager.get("mosquito.message2", location, this.getConfigString(location.getWorld(), "rules.mosquito.message2", "&2You hear some buzzing nearby."));
	}

	public String getRulesMosquitoMessage3(Location location)
	{
		return this.regionManager.get("mosquito.message3", location, this.getConfigString(location.getWorld(), "rules.mosquito.message3", "&2You can see a swarm of huge mosquitos nearby."));
	}

	public String getRulesMosquitoMessage4(Location location)
	{
		return this.regionManager.get("mosquito.message4", location, this.getConfigString(location.getWorld(), "rules.mosquito.message4", "&2Mosquitos are attacking you! Run!!"));
	}
	
	public int getRulesLeechDamage(Location location)
	{
	    int damage = this.regionManager.getInt("leech.damage", location, this.getConfigInt(location.getWorld(), "rules.leech.damage", 1));
	    if(damage<0)
	    {
	    	damage = 1;
	    }
	    return damage;
	}
	
	
	public int getRulesLeechRarity(Location location)
	{
		int result = this.regionManager.getInt("leech.rarity", location, this.getConfigInt(location.getWorld(), "rules.leech.rarity", 5));
		if(result<1 || result>10)
		{
			result = 2;
		}
		return result;
	}

	public String getRulesLeechMessage1(Location location)
	{
		return this.regionManager.get("leech.message1", location, this.getConfigString(location.getWorld(), "rules.leech.message1", "&2You manage to remove all leeches."));
	}

	public String getRulesLeechMessage2(Location location)
	{
		return this.regionManager.get("leech.message2", location, this.getConfigString(location.getWorld(), "rules.leech.message2", "&2You notice something moving in the water nearby."));
	}

	public String getRulesLeechMessage3(Location location)
	{
		return this.regionManager.get("leech.message3", location, this.getConfigString(location.getWorld(), "rules.leech.message3", "&2You can see leeches moving in the water."));
	}

	public String getRulesLeechMessage4(Location location)
	{
		return this.regionManager.get("leech.message4", location, this.getConfigString(location.getWorld(), "rules.leech.message4", "&2Leeches are attacking you! Get out of the water!"));
	}

	public int getRulesSpawnNeutralRarity(Location location)
	{
	    int rarity = this.regionManager.getInt("spawn.neutral.rarity", location, this.getConfigInt(location.getWorld(), "rules.spawn.neutral.rarity", 2));
	    if(rarity<1 || rarity>10)
	    {
	    	rarity = 2;
	    }
	    return rarity;
	}
	
	public String getRulesSpawnNeutralMobs(Location location)
	{
		return this.regionManager.get("spawn.neutral.mobs", location, this.getConfigString(location.getWorld(), "rules.spawn.neutral.mobs", "chicken,cow,mushroom_cow,ocelot,pig,sheep,wolf"));
	}
	
	public boolean isRulesSpawnNeutralOn(World world)
	{
		return this.getConfigBoolean(world, "rules.spawn.neutral.on", true);
	}

	
	/*
	  +structure.spikes.rarity: 2
	  +structure.gardens.rarity: 2
	  +structure.domes.rarity: 2
	  +structure.domes.plantstem: 19
	  +structure.domes.planttop: 89
	  +structure.domes.floor: 3
	  +structure.domes.floorrandom: 89
	  +structure.domes.placeminidome: true
	  +structure.bases.rarity: 2
	  +structure.bases.spawner: true
	  +structure.roomcluster.rarity: 2
	  +structure.roomcluster.minrooms: 8
	  +structure.roomcluster.maxrooms: 32
	  +structure.roomcluster.spawner: true
	  +generate.logs: true
	  +generate.caves.rarity: 2
	  +generate.ores: true
	  +generate.extendedores: false
	  +rules.dropice: true
	  +rules.droppackedice: true
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