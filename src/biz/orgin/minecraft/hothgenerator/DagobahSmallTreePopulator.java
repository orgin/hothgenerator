package biz.orgin.minecraft.hothgenerator;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.material.Tree;
import org.bukkit.material.Vine;

public class DagobahSmallTreePopulator extends BlockPopulator
{
	@SuppressWarnings("unused")
	private int height;
	@SuppressWarnings("unused")
	private HothGeneratorPlugin plugin;

	public DagobahSmallTreePopulator(int height)
	{
		this.plugin = HothGenerator.getPlugin();
		this.height = height;
	}

	@Override
	public void populate(World world, Random random, Chunk chunk)
	{
		int rand = random.nextInt(40);
		if(rand>5)
		{
			for(int i=5;i<rand;i++)
			{
				this.placeTree(world, random, chunk);
			}
		}
	}
	
	private void placeTree(World world, Random random, Chunk chunk)
	{
		int x = 16*chunk.getX() + 8+random.nextInt(16);
		int z = 16*chunk.getZ() + 8+random.nextInt(16);
		
		Biome biome = world.getBiome(x, z);
		
		if(!biome.equals(Biome.MUSHROOM_ISLAND) && !biome.equals(Biome.MUSHROOM_ISLAND_SHORE))
		{
		
			Block block = world.getHighestBlockAt(x, z);
			block = world.getBlockAt(x, block.getY()-1, z);
			while(HothUtils.isTransparent(block.getRelative(BlockFace.DOWN)))
			{
				block = block.getRelative(BlockFace.DOWN);
			}
			Material type = block.getType();
			if(type.equals(Material.DIRT) || type.equals(Material.GRASS))
			{
				this.renderTreeAt(world, random, x, block.getY(), z, 0, true);
			}
			else if(type.equals(Material.STATIONARY_WATER)) // Depth 1 water
			{
				block = world.getBlockAt(x, block.getY()-1, z);
				type = block.getType();
				if(type.equals(Material.DIRT) || type.equals(Material.GRAVEL) || type.equals(Material.SAND) || type.equals(Material.HARD_CLAY))
				{
					this.renderTreeAt(world, random, x, block.getY(), z, 1, true);
				}
				else if(type.equals(Material.STATIONARY_WATER)) // Depth 2 water
				{
					block = world.getBlockAt(x, block.getY()-1, z);
					type = block.getType();
					if(type.equals(Material.DIRT) || type.equals(Material.GRAVEL) || type.equals(Material.SAND) || type.equals(Material.HARD_CLAY))
					{
						this.renderTreeAt(world, random, x, block.getY(), z, 2, true);
					}
				}
			}
		}
	}
	
	private int[] probability1 = new int[] // For terrain generator
			{
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0
				,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1
				,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2
				,3,3,3,3,3,3,3,4,4,4,4,4,5,5,5,5,5,6,6,6,6
				,7,7,7,8,8,8,15
			};
	
	private int[] probability2 = new int[] // For player made trees
			{
				0,0,0,0
				,1,1,1,1
				,2,2,2,2
				,3,3,4,4,5,5,6,6
				,7,7,8,8,15
			};

	public boolean renderTreeAt(World world, Random random, int x, int y, int z, int heightOffset, boolean vines)
	{
		int probability;
		if(vines) // Terrain generated tree
		{
			probability = this.probability1[random.nextInt(this.probability1.length)];
		}
		else // Assume that this is a player generated tree
		{
			probability = this.probability2[random.nextInt(this.probability2.length)];
		}
		int height = heightOffset+2+probability;
		
		double radians = 2*Math.PI*random.nextDouble();
		
		double dx = Math.cos(radians)*0.3;
		double dz = Math.sin(radians)*0.3;
		
		if(probability==15) // High tree, reduce the slope
		{
			dx = dx/2;
			dz = dz/2;
		}
		
		HothSet tree = new HothSet();
		
		for(int i=0;i<height;i++) // render main trunk
		{
			double xx = dx * (i+1) + x;
			double zz = dz * (i+1) + z;
			
			Block block = world.getBlockAt((int)Math.floor(xx), y+i, (int)Math.floor(zz));

			// Add root on high trees
			if(probability==15 && i==0)
			{
				this.addCrown(world, random, tree, block);
			}
			
			BlockState state = block.getState();
			state.setType(Material.LOG);
			Tree tTree = (Tree)state.getData();
			tTree.setSpecies(TreeSpecies.GENERIC);
			tree.add(new Position(state));
			
			if(vines && i>0 && ((i<4+heightOffset && height+heightOffset>4) || (i<2+heightOffset && height>3+heightOffset)))
			{
				this.addVinesToLog(world, tree, block);
			}
			
			if(height>4+heightOffset && i==4+heightOffset && random.nextInt(10)<3) // Branch
			{
				int length = 2+random.nextInt(3);
				double bdx = 0 - dx * 1.2;
				double bdz = 0 - dz * 1.2;
				
				for(int j=0;j<length;j++)
				{
					double bxx = bdx * j + xx;
					double bzz = bdz * j + zz;

					Block bblock = world.getBlockAt((int)bxx, y+i+j, (int)bzz);
					BlockState bstate = bblock.getState();
					bstate.setType(Material.LOG);
					Tree bTree = (Tree)state.getData();
					bTree.setSpecies(TreeSpecies.GENERIC);
					tree.add(new Position(bstate));
					
					bdx=bdx*1.1;
					bdz=bdz*1.1;
					
					if(j==length-1)
					{
						this.addLeaves(world, random, tree, bblock, height/2, vines);
						if(height>7)
						{
							this.addCrown(world, random, tree, bblock);
						}
						
						if(probability==15 && random.nextInt(10)==1)
						{
							Block gblock = bblock.getRelative(BlockFace.DOWN);
							BlockState gstate = gblock.getState();
							gstate.setType(Material.GLOWSTONE);
							tree.add(new Position(gstate));
						}
					}
				}
			}
			
			dx=dx*1.07;
			dz=dz*1.07;

			if(i==height-1)
			{
				this.addLeaves(world, random, tree, block, height, vines);
				if(height>7)
				{
					this.addCrown(world, random, tree, block);
				}

				if(probability==15)
				{
					Block gblock = block.getRelative(BlockFace.DOWN);
					BlockState gstate = gblock.getState();
					gstate.setType(Material.GLOWSTONE);
					tree.add(new Position(gstate));
				}
			}

		}
		
		return this.renderBlob(world, tree.toArray(), y);
	}
	
	private void addCrown(World world, Random random, HothSet tree, Block block)
	{
		Block[] blocks = new Block[]{
				block.getRelative(BlockFace.NORTH),
				block.getRelative(BlockFace.EAST),
				block.getRelative(BlockFace.SOUTH),
				block.getRelative(BlockFace.WEST)
				};
		
		BlockFace[] faces = new BlockFace[]{
				BlockFace.NORTH,
				BlockFace.EAST,
				BlockFace.SOUTH,
				BlockFace.WEST
		};
	
		for(int i=0;i<blocks.length;i++)
		{
			Block logBlock = blocks[i];
			BlockState state = logBlock.getState();
			state.setType(Material.LOG);
			Tree dirlog = (Tree)state.getData();
			dirlog.setDirection(faces[i]);
			dirlog.setSpecies(TreeSpecies.GENERIC);
			tree.add(new Position(state));
		}
	}

	private void addLeaves(World world, Random random, HothSet tree, Block block, int height, boolean vines)
	{
		int x = block.getX();
		int y = block.getY();
		int z = block.getZ();
		
		if(height<4) // Tiny top
		{
			Block t = world.getBlockAt(x, y+1, z);
			if(HothUtils.isTransparent(t))
			{
				this.addLeaf(world, random, tree, t, null, null, 0, vines);
			}
			t = world.getBlockAt(x+1, y, z);
			if(HothUtils.isTransparent(t))
			{
				this.addLeaf(world, random, tree, t, BlockFace.EAST, null, 8, vines);
			}
			t = world.getBlockAt(x-1, y, z);
			if(HothUtils.isTransparent(t))
			{
				this.addLeaf(world, random, tree, t, BlockFace.WEST, null, 8, vines);
			}
			t = world.getBlockAt(x, y, z+1);
			if(HothUtils.isTransparent(t))
			{
				this.addLeaf(world, random, tree, t, BlockFace.SOUTH, null, 8, vines);
			}
			t = world.getBlockAt(x, y, z-1);
			if(HothUtils.isTransparent(t))
			{
				this.addLeaf(world, random, tree, t, BlockFace.NORTH, null, 8, vines);
			}
			
			if((x+z)%2>0)
			{
				t = world.getBlockAt(x+1, y, z+1);
				if(HothUtils.isTransparent(t))
				{
					this.addLeaf(world, random, tree, t, BlockFace.EAST, BlockFace.SOUTH, 5, vines);
				}
				t = world.getBlockAt(x+1, y, z-1);
				if(HothUtils.isTransparent(t))
				{
					this.addLeaf(world, random, tree, t, BlockFace.EAST, BlockFace.NORTH, 5, vines);
				}
				t = world.getBlockAt(x-1, y, z+1);
				if(HothUtils.isTransparent(t))
				{
					this.addLeaf(world, random, tree, t, BlockFace.WEST, BlockFace.SOUTH, 5, vines);
				}
				t = world.getBlockAt(x-1, y, z-1);
				if(HothUtils.isTransparent(t))
				{
					this.addLeaf(world, random, tree, t, BlockFace.WEST, BlockFace.NORTH, 5, vines);
				}
			}
		}
		else
		{
			int size = height/2;
			
			// ring 1
			this.addLeafRing(world, random, tree, block, size, size-size*0.3, size, vines);
			if(vines)
			{
				this.addLeafRingVines(world, random, tree, block, size, size-size*0.3, size, 0);
			}
			// ring 2
			size = height/2 -1;
			if(size>0)
			{
				block = block.getRelative(BlockFace.UP);
				this.addLeafRing(world, random, tree, block, size, 0, size, vines);
			}
			// ring 3
			size = height/2-3;
			if(size>0)
			{
				block = block.getRelative(BlockFace.UP);
				this.addLeafRing(world, random, tree, block, size-1, 0, size-1, vines);
			}
		}
	}
	
	private void addLeafRingVines(World world, Random random, HothSet tree, Block block, int size, double inner, double outer, int vineProbability)
	{
		// Add vines
		int x = block.getX();
		int y = block.getY();
		int z = block.getZ();
		
		Position test = new Position();
		test.y = y;
		for(int i=x-size;i<x+size+1;i++)
		{
			for(int j=z-size;j<z+size+1;j++)
			{
				int dx = x-i;
				int dz = z-j;
				
				test.x = i;
				test.z = j;
				
				Position pos = tree.get(test);
				
				if(pos!=null && pos.blockState.getType().equals(Material.LEAVES))
				{
				
				if(dx<0)
				{
					// EAST
					test.x = i+1;
					test.z = j;
					if(!tree.contains(test))
					{
						Block vBlock = world.getBlockAt(test.x, test.y, test.z);
						this.addVinesToLeaf(world, random, tree, vBlock, BlockFace.EAST, 3);
					}
				}
				else if(dx>0)
				{
					// WEST
					test.x = i-1;
					test.z = j;
					if(!tree.contains(test))
					{
						Block vBlock = world.getBlockAt(test.x, test.y, test.z);
						this.addVinesToLeaf(world, random, tree, vBlock, BlockFace.WEST, 3);
					}
				}
				
				if(dz<0)
				{
					// SOUTH
					test.x = i;
					test.z = j+1;
					if(!tree.contains(test))
					{
						Block vBlock = world.getBlockAt(test.x, test.y, test.z);
						this.addVinesToLeaf(world, random, tree, vBlock, BlockFace.SOUTH, 3);
					}
				}
				else if(dz>0)
				{
					// NORTH
					test.x = i;
					test.z = j-1;
					if(!tree.contains(test))
					{
						Block vBlock = world.getBlockAt(test.x, test.y, test.z);
						this.addVinesToLeaf(world, random, tree, vBlock, BlockFace.NORTH, 3);
					}
				}
				}
			}
		}
	}
	
	private void addLeafRing(World world, Random random, HothSet tree, Block block, int size, double inner, double outer, boolean vines)
	{
		int x = block.getX();
		int y = block.getY();
		int z = block.getZ();
		
		for(int i=x-size;i<x+size+1;i++)
		{
			for(int j=z-size;j<z+size+1;j++)
			{
				double dist = Math.sqrt( (double)( (i-x)*(i-x)+(j-z)*(j-z) ) );
				
				if(dist>=inner && dist<=outer)
				{
					Block newBlock = world.getBlockAt(i, y, j);
					this.addLeaf(world, random, tree, newBlock, null, null, 0, vines);
				}
				
			}
		}
		
	}
	
	private void addLeaf(World world, Random random, HothSet tree, Block block, BlockFace face1, BlockFace face2, int vineProbability, boolean vines)
	{
		BlockState state = block.getState();
		state.setType(Material.LEAVES);
		
		Position position = new Position(state);
		if(!tree.contains(position))
		{
			tree.add(position);
			if(vines)
			{
				if(face1!=null)
				{
					this.addVinesToLeaf(world, random, tree, block.getRelative(face1), face1, vineProbability);
				}
				if(face2!=null)
				{
					this.addVinesToLeaf(world, random, tree, block.getRelative(face2), face2, vineProbability);
				}
			}
		}
	}
	
	private void addVinesToLeaf(World world, Random random, HothSet tree, Block block, BlockFace face, int vineProbability)
	{
		int addVine = random.nextInt(10);
		if(addVine>vineProbability)
		{
			return;
		}
		
		BlockFace reverse = face;
		if(face.equals(BlockFace.NORTH))
		{
			reverse = BlockFace.SOUTH;
		}
		else if(face.equals(BlockFace.EAST))
		{
			reverse = BlockFace.WEST;
		}
		else if(face.equals(BlockFace.SOUTH))
		{
			reverse = BlockFace.NORTH;
		}
		else if(face.equals(BlockFace.WEST))
		{
			reverse = BlockFace.EAST;
		}
		
		Block curr = block;
		
		while(curr.isEmpty())
		{
			BlockState state = curr.getState();
			state.setType(Material.VINE);
			Vine vine = (Vine)state.getData();
			vine.putOnFace(reverse);
			Position newPos = new Position(state);
			if(!tree.contains(newPos))
			{
				tree.add(newPos); // Vines does not replace anything
			}
			else
			{
				break;
			}
			curr = curr.getRelative(BlockFace.DOWN);
		}
	}
	
	
	private void addVinesToLog(World world, HothSet tree, Block block)
	{
		Block north = block.getRelative(BlockFace.NORTH);
		if(north.isEmpty())
		{
			BlockState state = north.getState();
			state.setType(Material.VINE);
			Vine vine = (Vine)state.getData();
			vine.putOnFace(BlockFace.SOUTH);
			tree.add(new Position(state));
		}
		Block east = block.getRelative(BlockFace.EAST);
		if(east.isEmpty())
		{
			BlockState state = east.getState();
			state.setType(Material.VINE);
			Vine vine = (Vine)state.getData();
			vine.putOnFace(BlockFace.WEST);
			tree.add(new Position(state));
		}
		Block south = block.getRelative(BlockFace.SOUTH);
		if(south.isEmpty())
		{
			BlockState state = south.getState();
			state.setType(Material.VINE);
			Vine vine = (Vine)state.getData();
			vine.putOnFace(BlockFace.NORTH);
			tree.add(new Position(state));
		}
		Block west = block.getRelative(BlockFace.WEST);
		if(west.isEmpty())
		{
			BlockState state = west.getState();
			state.setType(Material.VINE);
			Vine vine = (Vine)state.getData();
			vine.putOnFace(BlockFace.EAST);
			tree.add(new Position(state));
		}
	}
	
	private boolean renderBlob(World world, Position[] blob, int y)
	{
		// Check for free space
		for(int i=0;i<blob.length;i++)
		{
			BlockState state = blob[i].blockState;
			int yy = state.getY();
			if(yy-y > 2)
			{
				Material newType = state.getType();
				if(newType.equals(Material.LOG))
				{
					Block block = state.getBlock();
					Material oldType = block.getType();
					if(!oldType.equals(Material.AIR)
							&& !oldType.equals(Material.LOG) 
							&& !oldType.equals(Material.VINE) 
							&& !oldType.equals(Material.LEAVES) 
							&& !oldType.equals(Material.LONG_GRASS) 
							&& !oldType.equals(Material.RED_ROSE) 
							&& !oldType.equals(Material.YELLOW_FLOWER) 
							&& !oldType.equals(Material.RED_MUSHROOM) 
							&& !oldType.equals(Material.BROWN_MUSHROOM) 
							)
					{
						return false; // Can't render this tree.
					}
				}
			}
		}
		
		// Insert it
		// Logs replace everything. Leafs only replace transparent blocks.
		for(int i=0;i<blob.length;i++)
		{
			BlockState state = blob[i].blockState;
			Block block = state.getBlock();
			Material newType = state.getType();
			
			if(newType.equals(Material.LOG))
			{
				state.update(true, false);
			}
			else if(newType.equals(Material.GLOWSTONE))
			{
				state.update(true, false);
			}
			else
			{
				if(HothUtils.isTransparent(block))
				{
					state.update(true, false);
				}
			}
		}
		
		return true;
	}
	
	
}
