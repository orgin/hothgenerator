package biz.orgin.minecraft.hothgenerator;

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
	HothGeneratorPlugin plugin;
	
	public ToolUseManager(HothGeneratorPlugin plugin)
	{
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		if(!event.isCancelled())
		{
			World world = event.getPlayer().getWorld();
	
			Action action = event.getAction();
	
			if(action.equals(Action.RIGHT_CLICK_BLOCK))
			{
	
				Player player = event.getPlayer();
				ItemStack item = player.getItemInHand();
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
								this.plugin.logMessage("Exception while trying to register WaterPlacerThread. You probably need to restart yoru server.", true);
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
