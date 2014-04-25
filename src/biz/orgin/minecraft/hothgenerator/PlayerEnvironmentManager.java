package biz.orgin.minecraft.hothgenerator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/**
 * Handles giving players damage when exposed to the cold.
 * @author orgin
 *
 */
public class PlayerEnvironmentManager
{
	private HothGeneratorPlugin plugin;
	private int taskId;
	
	public PlayerEnvironmentManager(HothGeneratorPlugin plugin)
	{
		this.plugin = plugin;
		
		this.plugin.debugMessage("Initializing PlayerEnvironmentManager. Starting repeating task.");
		
		int period = this.plugin.getRulesEnvironmentPeriod();
		
		this.taskId = -1;
		if(period>0)
		{
			this.taskId = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new DamagePlayers(this.plugin), 10, period * 20);
		}

	}
	
	/**
	 * Stop the scheduled repeating task.
	 * A new instance of the PlayerFreezeManager must be made to restart a new task.
	 * Use this to shut down the currently running freeze task before a reload.
	 */
	public void stop()
	{
		if(this.taskId!=-1)
		{
			Bukkit.getServer().getScheduler().cancelTask(this.taskId);
		}
	}
	
	private class DamagePlayers implements Runnable
	{
		HothGeneratorPlugin plugin;
		
		private Map<UUID, Integer> thirsts;
		
		
		public DamagePlayers(HothGeneratorPlugin plugin)
		{
			this.plugin = plugin;
			this.thirsts = new HashMap<UUID, Integer>();
		}
		
		@Override
		public void run()
		{
			Server server = this.plugin.getServer();
			
			List<World> worlds = server.getWorlds();
			
			// Find all worlds configured as hoth worlds
			for(int i=0;i<worlds.size();i++)
			{
				World world = worlds.get(i);
				if(this.plugin.isHothWorld(world))
				{
					if(this.plugin.getWorldType(world).equals("hoth"))
					{
						this.freeze(world);
					}
					else if(this.plugin.getWorldType(world).equals("tatooine"))
					{
						this.heat(world);
					}
				}
			}
		}
		
		/**
		 * Finds all players on the specified world and applies heat damage.
		 * @param world
		 */
		private void heat(World world)
		{
			
			List<Player> players = world.getPlayers();
			Iterator<Player> iterator = players.iterator();
			
			while(iterator.hasNext())
			{
				Player player = iterator.next();
				UUID uuid = player.getUniqueId();
				
				int thirst = 100;
				
				if(this.thirsts.containsKey(uuid))
				{
					thirst = this.thirsts.get(uuid);
				}
				
				GameMode gm = player.getGameMode();
				if(!gm.equals(GameMode.CREATIVE))
				{
					Location location = player.getLocation();
					int damage = this.plugin.getRulesHeatDamage(location);
					
					if(damage>0)
					{
						String message1 = this.plugin.getRulesHeatMessage1(location);
						String message2 = this.plugin.getRulesHeatMessage2(location);
						String message3 = this.plugin.getRulesHeatMessage3(location);
						String message4 = this.plugin.getRulesHeatMessage4(location);
						
						Block block = world.getBlockAt(location.getBlockX(), location.getBlockY()+1, location.getBlockZ());
						Block block2 = world.getBlockAt(location.getBlockX(), location.getBlockY(), location.getBlockZ());
						
						if(block2.getType().equals(Material.WATER) || block2.getType().equals(Material.STATIONARY_WATER))
						{
							if(thirst!=100)
							{
								thirst = 100;
								plugin.sendMessage(player, message1); // The water removes your thirst.
							}
						}
						else
						{
							// Dry damage happens during the day if exposed to the sky
							if(block.getLightFromSky() > 10 && block.getLightLevel() > 10)
							{
								thirst = thirst - 2;
	
								if(thirst == 50)
								{
									plugin.sendMessage(player, message2); // Your are starting to feel thirsty.
								}
	
								if(thirst == 25)
								{
									plugin.sendMessage(player, message3); // Your feel very thirsty.
								}
							}
							else
							{
								thirst = thirst + 10;
							}
	
	
							// Apply damage to player
							if(thirst<=0)
							{
								double oldDamage = player.getHealth();
								if(oldDamage - damage <= 0)
								{
									thirst = 100; // Player is going to die, reset thirst 
								}
	
								plugin.sendMessage(player, message4); // You are exhausted from the heat. Find water or shelter!
								player.damage(damage);
							}
						}

						if(thirst>100)
						{
							thirst = 100;
						}
						else if(thirst<0)
						{
							thirst = 0;
						}
	
						this.thirsts.put(uuid, new Integer(thirst));
					}
				}
			}
		}
		
		/**
		 * Finds all players on the specified world and applies freeze damage.
		 * @param world
		 */
		private void freeze(World world)
		{
			boolean storm = world.hasStorm();

			List<Player> players = world.getPlayers();
			Iterator<Player> iterator = players.iterator();
			
			while(iterator.hasNext())
			{
				int realDamage = 0;
				Player player = iterator.next();
				GameMode gm = player.getGameMode();
				if(!gm.equals(GameMode.CREATIVE))
				{
					Location location = player.getLocation();
					int damage = this.plugin.getRulesFreezeDamage(location);
					int stormdamage = this.plugin.getRulesFreezeStormdamage(location);
					String message = this.plugin.getRulesFreezeMessage(location);
					
					Block block = world.getBlockAt(location.getBlockX(), location.getBlockY()+1, location.getBlockZ());
					
					// Storm damage happens even during the day if exposed to the sky
					if(storm && stormdamage > 0 && block.getLightFromSky() > 8 && block.getLightFromBlocks()<10)
					{
						realDamage = realDamage + stormdamage;
					}

					// Freeze damage if exposed to the sky
					if(damage > 0 && block.getLightLevel()<10 && block.getLightFromSky() > 8)
					{
						realDamage = realDamage + damage;
					}
					
					// Apply damage to player
					if(realDamage>0)
					{
						plugin.sendMessage(player, message);
						player.damage(realDamage);
					}
				}
			}
		}

	}
	

}
