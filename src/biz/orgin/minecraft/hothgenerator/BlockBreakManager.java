package biz.orgin.minecraft.hothgenerator;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class BlockBreakManager implements Listener
{
	private HothGeneratorPlugin plugin;

	public BlockBreakManager(HothGeneratorPlugin plugin)
	{
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onBlockBreak(BlockBreakEvent event)
	{
		Block block = event.getBlock();
		World world = block.getWorld();

		GameMode gamemode = event.getPlayer().getGameMode();

		if (!gamemode.equals(GameMode.CREATIVE))
		{
			if (this.plugin.isHothWorld(world) && block.getType().equals(Material.ICE))
			{
				ItemStack iceStack = new ItemStack(Material.ICE);
				iceStack.setAmount(1);

				block.setType(Material.AIR);
				block.getWorld().dropItem(block.getLocation(), iceStack);
			}
			else if (this.plugin.isHothWorld(world) && block.getType().equals(Material.SNOW_BLOCK))
			{
				ItemStack iceStack = new ItemStack(Material.SNOW_BLOCK);
				iceStack.setAmount(1);

				block.setType(Material.AIR);
				block.getWorld().dropItem(block.getLocation(), iceStack);
			}
		}
	}

}