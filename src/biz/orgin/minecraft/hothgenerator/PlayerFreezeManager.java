package biz.orgin.minecraft.hothgenerator;

import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/**
 * Handles giving players damage when exposed to the cold.
 * @author orgin
 *
 */
public class PlayerFreezeManager
{
	private HothGeneratorPlugin plugin;
	private int taskId;
	
	public PlayerFreezeManager(HothGeneratorPlugin plugin)
	{
		this.plugin = plugin;
		
		this.plugin.debugMessage("Initializing PlayerFreezeManager. Starting repeating task.");
		
		int period = this.plugin.getRulesFreezePeriod();
		
		this.taskId = -1;
		if(period>0)
		{
			this.taskId = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new FreezePlayers(this.plugin), 10, period * 20);
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
	
	private class FreezePlayers implements Runnable
	{
		HothGeneratorPlugin plugin;
		
		public FreezePlayers(HothGeneratorPlugin plugin)
		{
			this.plugin = plugin;
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
				if(this.plugin.isHothWorld(world) && this.plugin.getWorldType(world).equals("hoth"))
				{
					this.freeze(world);
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
