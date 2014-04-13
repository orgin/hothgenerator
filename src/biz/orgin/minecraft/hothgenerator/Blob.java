package biz.orgin.minecraft.hothgenerator;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.inventory.Inventory;

public class Blob
{
	private Set<Position>primary;
	private Set<Position>secondary;
	private World world;
	private HothGeneratorPlugin plugin;
	private String parentName;
	
	private static IntSet delays = new IntSet(new int[] {  // Block types to defer until infrastructure is made.
			50,75,76,6,32,37,38,39,40,51,55,26,
			59,31,63,65,66,96,69,77,106,83,115,
			93,94,127,131,132,141,142,143,78,64});
	
	private static int blocksPerInteration = 500;
	
	public Blob(HothGeneratorPlugin plugin, World world, String parentName)
	{
		this.plugin = plugin;
		this.world = world;
		this.primary = new HashSet<Position>();
		this.secondary = new HashSet<Position>();
		this.parentName = parentName;
	}
	
	public void addPosition(Position position)
	{
		int type = position.data;
		if(Blob.delays.contains(type))
		{
			this.secondary.add(position);
		}
		else
		{
			this.primary.add(position);
		}
	}
	
	public void instantiate()
	{
		Position[] blocks = this.primary.toArray(new Position[0]);
		this.finishBlob(blocks);
		blocks = this.secondary.toArray(new Position[0]);
		this.finishBlob(blocks);
	}
	
	private void finishBlob(Position[] blocks)
	{
		plugin.addTask(new PlaceBlob(this.plugin, this.world, this.parentName, blocks, 0, Blob.blocksPerInteration));
	}
	
	static class PlaceBlob implements HothRunnable
	{
		private final HothGeneratorPlugin plugin;
		private final World world;
		private final Position[] blocks;
		private final int start;
		private final int count;
		private final String parentName;

		public String getName()
		{
			return parentName + ".PlaceBlob";
		}
		
		public String getParameterString()
		{
			return "parentName = " + this.parentName + " start=" + this.start + " count=" + this.count;
		}
		
		public PlaceBlob(HothGeneratorPlugin plugin, World world, String parentName, Position[] blocks, int start, int count)
		{
			this.world = world;
			this.blocks = blocks;
			this.start = start;
			this.count = count;
			this.plugin = plugin;
			this.parentName = parentName;
		}

		@Override
		public void run()
		{
			Position[] blocks = this.blocks;
			int start = this.start;
			int count = this.count;
			
			for(int i=start;i<start+count && i<blocks.length;i++)
			{
				Position pos = blocks[i];
				
				Block block = this.world.getBlockAt(pos.x, pos.y, pos.z);
				// Check if chest, rotation and what not
				int type = pos.type;
				byte data = pos.data;
				if(type==52) // Spawner
				{
					block.setType(MaterialManager.toMaterial(type));
					CreatureSpawner spawner = (CreatureSpawner)block.getState();
					spawner.setSpawnedType(EntityTypeManager.toEntityType(data));
					spawner.update(true);
				}
				else if(type==54) // Chest, set correct rotation and add some random loot
				{
					block.setType(MaterialManager.toMaterial(type));
					Chest chest = (Chest)block.getState();
					org.bukkit.material.Chest cst = null;
					switch(data)
					{
					default:
					case 0:
						cst = new org.bukkit.material.Chest(BlockFace.EAST);
						break;
					case 1:
						cst = new org.bukkit.material.Chest(BlockFace.WEST);
						break;
					case 2:
						cst = new org.bukkit.material.Chest(BlockFace.NORTH);
						break;
					case 3:
						cst = new org.bukkit.material.Chest(BlockFace.SOUTH);
						break;
					}
					chest.setData(cst);
					Inventory inv = chest.getInventory();
					int lootMin = pos.lootMin;
					int lootMax = pos.lootMax;
					LootGenerator lootGenerator = pos.lootGenerator;
					if(lootGenerator!=null)
					{
						lootGenerator.getLootInventory(inv, lootMin, lootMax);
					}
					chest.update(true);
				}
				else
				{
					block.setType(MaterialManager.toMaterial(type));
					DataManager.setData(block, data, false);
				}
			}
			
			if(start+count < blocks.length)
			{
				this.plugin.addTask(new PlaceBlob(this.plugin, this.world, this.parentName, blocks, start+count, count));
			}

		}
	}

}
