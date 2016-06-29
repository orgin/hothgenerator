package biz.orgin.minecraft.hothgenerator;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Mushroom;
import java.util.UUID;


/**
 * Listens to player interaction events, specifically using a
 * water or lava bucket so that any placed water or lava can
 * be frozen into ice or stone.
 * Bonemealing exposed blocks are prevented.
 * @author orgin
 *
 */
public class ToolUseManager implements Listener
{
	private Map<UUID, PlayerSelection> playerSelections = new HashMap<UUID, PlayerSelection>();
	
	HothGeneratorPlugin plugin;
	
	public ToolUseManager(HothGeneratorPlugin plugin)
	{
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		Action action = event.getAction();
		
		/*
		{
			Player player = event.getPlayer();
			ItemStack item = player.getInventory().getItemInMainHand();
			ItemMeta itemMeta = item.getItemMeta();
			Set<ItemFlag> flags = itemMeta.getItemFlags();

			MaterialData materialData = item.getData();
			int data = materialData.getData();

			this.plugin.sendMessage(event.getPlayer(), "data = " + data);

			this.plugin.sendMessage(event.getPlayer(), "class = " + itemMeta.getClass().getName());

			this.plugin.sendMessage(event.getPlayer(), "displayname = " + itemMeta.getDisplayName());


			for(ItemFlag flag: flags)
			{
				this.plugin.sendMessage(event.getPlayer(), "Flag " + flag.name());
			}
			

		}
		*/


		// Hothexport primary position
		if(action.equals(Action.LEFT_CLICK_BLOCK))
		{
			Player player = event.getPlayer();
			ItemStack item = player.getInventory().getItemInMainHand();

			if(player.hasPermission("hothgenerator.hothexport") && !ConfigManager.isWorldEditSelection(this.plugin) && item.getType().equals(ConfigManager.getSelectionToolMaterial(plugin)))
			{
				UUID uuid = player.getUniqueId();
				Block block = event.getClickedBlock();
				
				PlayerSelection ps = this.playerSelections.get(uuid);
				if(ps==null)
				{
					ps = new PlayerSelection(block.getLocation(), null);
					this.playerSelections.put(uuid, ps);
				}
				else
				{
					ps.setPos1(block.getLocation());
				}
				
				this.plugin.sendMessage(player, "&b" + ps.getPrimaryPosition());
				event.setCancelled(true);
			}
		}
		
		// Hothexport secondary position
		if(action.equals(Action.RIGHT_CLICK_BLOCK))
		{

			Player player = event.getPlayer();
			ItemStack item = player.getInventory().getItemInMainHand();

			if(player.hasPermission("hothgenerator.hothexport") && !ConfigManager.isWorldEditSelection(this.plugin) && item.getType().equals(ConfigManager.getSelectionToolMaterial(plugin)))
			{
				UUID uuid = player.getUniqueId();
				Block block = event.getClickedBlock();
				
				PlayerSelection ps = this.playerSelections.get(uuid);
				if(ps==null)
				{
					ps = new PlayerSelection(null, block.getLocation());
					this.playerSelections.put(uuid, ps);
				}
				else
				{
					ps.setPos2(block.getLocation());
				}
				
				this.plugin.sendMessage(player, "&b" + ps.getSecondaryPosition());
			}
		}

		
		if(!event.isCancelled())
		{
			World world = event.getPlayer().getWorld();
	
	

			if(action.equals(Action.RIGHT_CLICK_BLOCK))
			{
	
				Player player = event.getPlayer();
				ItemStack item = player.getInventory().getItemInMainHand();
				WorldType worldtype = this.plugin.getWorldType(world);
	
				if(ConfigManager.isItemInfoTool(this.plugin) && item.getType().equals(Material.CLAY_BALL))
				{
					Block block = event.getClickedBlock();
					player.sendMessage("Item: name = " + block.getType().name() + " type = " + MaterialManager.toID(block.getType()) + ", data = " + DataManager.getData(block));
					BlockState state = block.getState();
					if(block.getType().equals(Material.HUGE_MUSHROOM_1) || block.getType().equals(Material.HUGE_MUSHROOM_2))
					{
						Mushroom mushroom = (Mushroom)state.getData();
						Set<BlockFace> faceSet = mushroom.getPaintedFaces();
						BlockFace[] faces = faceSet.toArray(new BlockFace[0]);
						for(int i=0;i<faces.length;i++)
						{
							player.sendMessage(block.getType().name() + " face: " + faces[i].name());
						}
					}
				}
				
	
				if(this.plugin.isHothWorld(world) && worldtype == WorldType.HOTH)
				{
					if(item.getType().equals(Material.WATER_BUCKET))
					{
						Block block = event.getClickedBlock();
						block = block.getRelative(event.getBlockFace());

						if(ConfigManager.isRulesFreezewater(this.plugin, block.getLocation()) && !this.plugin.canPlaceLiquid(world, block))
						{
							BlockPlacerThread th = new BlockPlacerThread(world, block.getX(), block.getY(), block.getZ(), Material.WATER, Material.ICE);
							try
							{
							Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, th);
							}
							catch(Exception e)
							{
								this.plugin.logMessage("Exception while trying to register BlockPlacerThread. You probably need to restart yoru server.", true);
							}
						}
					}
					else if(item.getType().equals(Material.LAVA_BUCKET))
					{
						Block block = event.getClickedBlock();
						block = block.getRelative(event.getBlockFace());

						if(ConfigManager.isRulesFreezelava(this.plugin, block.getLocation()) && !this.plugin.canPlaceLiquid(world, block))
						{
							BlockPlacerThread th = new BlockPlacerThread(world, block.getX(), block.getY(), block.getZ(), Material.LAVA, Material.STONE);
							Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, th);
						}
					}
				}
				
				if(this.plugin.isHothWorld(world) && worldtype == WorldType.MUSTAFAR)
				{
					if(item.getType().equals(Material.WATER_BUCKET))
					{
						Block block = event.getClickedBlock();
						block = block.getRelative(event.getBlockFace());

						if(ConfigManager.isRulesPlaceWater(this.plugin, block.getLocation()))
						{
							WaterPlacerThread th = new WaterPlacerThread(player, block.getLocation());
							try
							{
								Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, th);
							}
							catch(Exception e)
							{
								this.plugin.logMessage("Exception while trying to register WaterPlacerThread. You probably need to restart your server.", true);
							}
						}
					}
				}

				
				if(this.plugin.isHothWorld(world) && (worldtype == WorldType.HOTH || worldtype == WorldType.TATOOINE))
				{
					if(item.getType().equals(Material.INK_SACK) && item.getDurability() == 15)
					{
						Block block = event.getClickedBlock();
						if(!ConfigManager.isRulesPlantsgrow(this.plugin, block.getLocation()))
						{
							// User is bonemealing something
							Material type = block.getType();
							
							int maxy = world.getHighestBlockYAt(block.getLocation());
							
							if(Math.abs(maxy-block.getY())<2)
							{
								if( type.equals(Material.CARROT) ||
									type.equals(Material.POTATO) ||
									type.equals(Material.PUMPKIN_STEM) ||
									type.equals(Material.MELON_STEM) ||
									type.equals(Material.SAPLING) ||
									type.equals(Material.CROPS)	)
								{
									if(worldtype == WorldType.HOTH || (worldtype == WorldType.TATOOINE && !HothUtils.isWatered(block.getRelative(BlockFace.DOWN))))
									{
										event.setCancelled(true);
										block.breakNaturally();
									}
								}
								
								if(type.equals(Material.GRASS))
								{
									if(worldtype == WorldType.HOTH || (worldtype == WorldType.TATOOINE && !HothUtils.isWatered(block)))
									{
										event.setCancelled(true);
									}
								}
							}
						}
					}
				}
			}
		}
	}

	public Location getPrimaryPosition(Player player)
	{
		PlayerSelection ps = this.playerSelections.get(player.getUniqueId());
		if(ps==null)
		{
			return null;
		}
		
		return ps.getPos1();
	}

	public Location getSecondaryPosition(Player player)
	{
		PlayerSelection ps = this.playerSelections.get(player.getUniqueId());
		if(ps==null)
		{
			return null;
		}
		
		return ps.getPos2();
	}

	private class PlayerSelection
	{
		private Location pos1;
		private Location pos2;
		
		public PlayerSelection(Location pos1, Location pos2)
		{
			this.pos1 = pos1;
			this.pos2 = pos2;
		}

		public Location getPos1()
		{
			return pos1;
		}
		
		public void setPos1(Location pos1)
		{
			this.pos1 = pos1;
			if(this.pos2!=null && !this.pos1.getWorld().equals(this.pos2.getWorld()))
			{
				this.pos2 = null;
			}
		}

		public Location getPos2()
		{
			return pos2;
		}

		public void setPos2(Location pos2)
		{
			this.pos2 = pos2;
			if(this.pos1!=null && !this.pos1.getWorld().equals(this.pos2.getWorld()))
			{
				this.pos1 = null;
			}
		}
		
		public String getPrimaryPosition()
		{
			int cnt = this.getBlockCnt();
			
			if(cnt>0)
			{
				return "First position set to " + this.locToString(this.pos1) + ". ("  + this.getBlockCnt() + ")";
			}
			else
			{
				return "First position set to " + this.locToString(this.pos1) + ".";
			}
		}

		public String getSecondaryPosition()
		{
			int cnt = this.getBlockCnt();
			if(cnt>0)
			{
				return "Second position set to " + this.locToString(this.pos2) + ". ("  + this.getBlockCnt() + ")";
			}
			else
			{
				return "Second position set to " + this.locToString(this.pos2) + ".";
			}
		}
		
		private String locToString(Location location)
		{
			int x = location.getBlockX();
			int y = location.getBlockY();
			int z = location.getBlockZ();
			
			return x + ", " + y + ", " + z;
		}
		
		private int getBlockCnt()
		{
			if(pos1==null || pos2==null)
			{
				return -1;
			}
			
			int dx = Math.abs(pos1.getBlockX() - pos2.getBlockX()) + 1;
			int dy = Math.abs(pos1.getBlockY() - pos2.getBlockY()) + 1;
			int dz = Math.abs(pos1.getBlockZ() - pos2.getBlockZ()) + 1;
			
			return dx*dy*dz;
		}

	}
	
	private class WaterPlacerThread implements Runnable
	{
		@SuppressWarnings("unused")
		private Player player;
		private Location location;
		
		private WaterPlacerThread(Player player, Location location)
		{
			this.player = player;
			this.location = location;
		}
		
		@Override
		public void run()
		{
			if(!HothUtils.isTooHot(this.location, 2))
			{
				Block block = this.location.getBlock();
				block.setType(Material.WATER);
			}
		}
	}
}
