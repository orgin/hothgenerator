package biz.orgin.minecraft.hothgenerator;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

/**
 * Places blocks into the world. Can verify that the block it is replacing is of the correct type.
 * @author orgin
 *
 */
public class BlockPlacerThread implements HothRunnable
{
	private int x,y,z;
	private Material from;
	private Material to;
	private World world;

	public BlockPlacerThread(World world, int x, int y, int z, Material to)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.from = null;
		this.to = to;
		this.world = world;
	}
	
	
	public BlockPlacerThread(World world, int x, int y, int z, Material from, Material to)
	{
			this.x = x;
			this.y = y;
			this.z = z;
			this.from = from;
			this.to = to;
			this.world = world;
	}
	
	public String getName() {
		return "BlockPlacerThread";
	}

	public String getParameterString() {
		return "x="+x+" y="+y+" z="+z+" from="+from.name()+" to="+to.name();
	}
	
	@Override
	public void run()
	{
		try
		{
			Thread.sleep(400);
			Block block = this.world.getBlockAt(this.x, this.y, this.z);
			if(from==null || block.getType().equals(this.from))
			{
				block.setType(this.to);
			}
		}
		catch(Exception e)
		{
		}
	}
}
