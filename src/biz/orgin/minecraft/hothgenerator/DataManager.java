package biz.orgin.minecraft.hothgenerator;

import org.bukkit.DyeColor;
import org.bukkit.GrassSpecies;
import org.bukkit.Material;
import org.bukkit.SandstoneType;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.material.LongGrass;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Sandstone;
import org.bukkit.material.Stairs;
import org.bukkit.material.Wool;

/*
 * Handles old school block data. Since bukkit has decided to get rid of the setData methods each plugin must handle this themselves 
 */
public class DataManager
{
	public static void setData(Block block, byte data)
	{
		DataManager.setData(block, data, true);
	}

	@SuppressWarnings("deprecation")
	public static final void setData(Block block, byte data, boolean applyPhysics)
	{
		Material material = block.getType();
		
		if(material.equals(Material.SNOW))
		{
			// No bukkit api for handling snow and snow heights so using deprecated function for this.
			block.setData(data, applyPhysics);
		}
		else if(material.equals(Material.CHEST))
		{
			Chest chest = (Chest)block.getState();
			org.bukkit.material.Chest cst = null;
			switch(data)
			{
			default:
			case 2:
				cst = new org.bukkit.material.Chest(BlockFace.NORTH);
				break;
			case 3:
				cst = new org.bukkit.material.Chest(BlockFace.SOUTH);
				break;
			case 4:
				cst = new org.bukkit.material.Chest(BlockFace.WEST);
				break;
			case 5:
				cst = new org.bukkit.material.Chest(BlockFace.EAST);
				break;
			}
			
			chest.setData(cst);
			chest.update();
		}
		else if(material.equals(Material.SAND))
		{
			// No bukkit api for handling sand and sand types so using deprecated function for this.
			block.setData(data, applyPhysics);
		}
		else if(material.equals(Material.HUGE_MUSHROOM_1) || material.equals(Material.HUGE_MUSHROOM_2))
		{
			// The bukkit api for handling mushroom faces is totally borked, usign deprecated function
			block.setData(data, applyPhysics);
			/*
			System.out.println("======= START");
			System.out.println("Block: " + block.getType().toString() + " " + block.getData());
			BlockState state = block.getState();
			Mushroom mushroom = (Mushroom)state.getData();
			switch(data)
			{
				case 1:
				mushroom.setFacePainted(BlockFace.UP, true);
				mushroom.setFacePainted(BlockFace.WEST, true);
				mushroom.setFacePainted(BlockFace.SOUTH, true);
				break;
				case 2:
				mushroom.setFacePainted(BlockFace.UP, true);
				mushroom.setFacePainted(BlockFace.WEST, true);
				break;
				case 3:
				mushroom.setFacePainted(BlockFace.UP, true);
				mushroom.setFacePainted(BlockFace.NORTH, true);
				mushroom.setFacePainted(BlockFace.WEST, true);
				break;
				case 4:
				mushroom.setFacePainted(BlockFace.UP, true);
				mushroom.setFacePainted(BlockFace.SOUTH, true);
				break;
				case 5:
				mushroom.setFacePainted(BlockFace.UP, true);
				break;
				case 6:
				mushroom.setFacePainted(BlockFace.UP, true);
				mushroom.setFacePainted(BlockFace.NORTH, true);
				break;
				case 7:
				mushroom.setFacePainted(BlockFace.UP, true);
				mushroom.setFacePainted(BlockFace.SOUTH, true);
				mushroom.setFacePainted(BlockFace.EAST, true);
				break;
				case 8:
				mushroom.setFacePainted(BlockFace.UP, true);
				mushroom.setFacePainted(BlockFace.EAST, true);
				break;
				case 9:
				mushroom.setFacePainted(BlockFace.UP, true);
				mushroom.setFacePainted(BlockFace.NORTH, true);
				mushroom.setFacePainted(BlockFace.EAST, true);
				break;
				case 10:
				mushroom.setStem();
				break;
				case 14:
				mushroom.setFacePainted(BlockFace.EAST, true);
				mushroom.setFacePainted(BlockFace.UP, true);
				break;
				case 15:
				mushroom.setStem();
				mushroom.setFacePainted(BlockFace.NORTH, true);
				mushroom.setFacePainted(BlockFace.EAST, true);
				mushroom.setFacePainted(BlockFace.UP, true);
				break;
			}
			state.update();
			
			System.out.println("Mushroom data was: " + data);
			System.out.println("Mushroom data ended up as: " + state.getRawData());
			
			Set<BlockFace> faceSet = mushroom.getPaintedFaces();
			BlockFace[] faces = faceSet.toArray(new BlockFace[0]);
			for(int i=0;i<faces.length;i++)
			{
				System.out.println(block.getType().name() + " face: " + faces[i].name());
			}
			*/

			
		}
		else if(material.equals(Material.SANDSTONE))
		{
			BlockState state = block.getState();
			Sandstone sandstone = (Sandstone)state.getData();
			switch(data)
			{
				case 0: sandstone.setType(SandstoneType.CRACKED); break;
				case 1: sandstone.setType(SandstoneType.GLYPHED); break;
				case 2: sandstone.setType(SandstoneType.SMOOTH); break;
				default: sandstone.setType(SandstoneType.CRACKED); break;
			}
			state.update();
		}
		/*
		else if(material.equals(Material.LEAVES))
		{
			BlockState state = block.getState();
			Leaves leaves = (Leaves)state.getData();
			switch(data)
			{
				case 0: leaves.setSpecies(TreeSpecies.GENERIC); break;
				case 1: leaves.setSpecies(TreeSpecies.REDWOOD); break;
				case 2: leaves.setSpecies(TreeSpecies.BIRCH); break;
				case 3: leaves.setSpecies(TreeSpecies.JUNGLE); break;
				case 4: leaves.setSpecies(TreeSpecies.ACACIA); break;
				case 5: leaves.setSpecies(TreeSpecies.REDWOOD); break;
				default: leaves.setSpecies(TreeSpecies.GENERIC); break;
			}
			state.update();
		}
		*/
		else if(material.equals(Material.LONG_GRASS))
		{
			BlockState state = block.getState();
			LongGrass longgrass = (LongGrass)state.getData();
			switch(data)
			{
				case 0: longgrass.setSpecies(GrassSpecies.DEAD); break;
				case 1: longgrass.setSpecies(GrassSpecies.NORMAL); break;
				case 2: longgrass.setSpecies(GrassSpecies.FERN_LIKE); break;
				default: longgrass.setSpecies(GrassSpecies.DEAD); break;
			}
			state.update();
		}
		else if(material.equals(Material.WOOL))
		{
			BlockState state = block.getState();
			Wool wool = (Wool)state.getData();
			switch(data)
			{
			case  0:	wool.setColor(DyeColor.WHITE); break;
			case  1:	wool.setColor(DyeColor.ORANGE); break;
			case  2:	wool.setColor(DyeColor.MAGENTA); break;
			case  3:	wool.setColor(DyeColor.LIGHT_BLUE); break;
			case  4:	wool.setColor(DyeColor.YELLOW); break;
			case  5:	wool.setColor(DyeColor.LIME); break;
			case  6:	wool.setColor(DyeColor.PINK); break;
			case  7:	wool.setColor(DyeColor.SILVER); break;
			case  8:	wool.setColor(DyeColor.GRAY); break;
			case  9:	wool.setColor(DyeColor.CYAN); break;
			case 10:	wool.setColor(DyeColor.PURPLE); break;
			case 11:	wool.setColor(DyeColor.BLUE); break;
			case 12:	wool.setColor(DyeColor.BROWN); break;
			case 13:	wool.setColor(DyeColor.GREEN); break;
			case 14:	wool.setColor(DyeColor.RED); break;
			case 15:	wool.setColor(DyeColor.BLACK); break;
			default:	wool.setColor(DyeColor.WHITE); break;
			}
			state.update(true, applyPhysics);
		}
		else if(material.equals(Material.STAINED_CLAY))
		{
			// No bukkit api for handling color of stained_clay so using deprecated function for this.
			block.setData(data, applyPhysics);
		}
		else if(material.equals(Material.STAINED_GLASS))
		{
			// No bukkit api for handling color of stained_glass so using deprecated function for this.
			block.setData(data, applyPhysics);
		}
		else if(material.equals(Material.STAINED_GLASS_PANE))
		{
			// No bukkit api for handling color of stained_glass_pane so using deprecated function for this.
			block.setData(data, applyPhysics);
		}
		else if(material.equals(Material.LADDER))
		{
			// The Ladder.setFacingDirection() function is erroneous so using deprecated function for this. 
			block.setData(data, applyPhysics);

			/*
			BlockFace blockFace = BlockFace.DOWN;
			switch(data)
			{
			case 0: blockFace = BlockFace.DOWN;
			case 1: blockFace = BlockFace.WEST;
			case 2: blockFace = BlockFace.EAST;
			case 3: blockFace = BlockFace.NORTH;
			case 4: blockFace = BlockFace.SOUTH;
			}

			BlockState state = block.getState();
			Ladder ladder = (Ladder)state.getData();
			ladder.setFacingDirection(blockFace);
			state.update(true, false);
			
			state = block.getState();
			ladder = (Ladder)state.getData();
			BlockFace blockFaceOut = ladder.getAttachedFace();
			
			System.out.println("Data: " + data + " newdata: " + block.getData() + " blockface in: " + blockFace.name() + " blockface out: " + blockFaceOut.name());
			*/
		}
		else if(material.equals(Material.TORCH))
		{
			// The Torch.setFacingDirection() function is erroneous so using deprecated function for this. 
			block.setData(data, applyPhysics);

			/*
			BlockFace blockFace = BlockFace.DOWN;
			switch(data)
			{
			case 0: blockFace = BlockFace.DOWN;
			case 1: blockFace = BlockFace.WEST;
			case 2: blockFace = BlockFace.EAST;
			case 3: blockFace = BlockFace.NORTH;
			case 4: blockFace = BlockFace.SOUTH;
			}
			
			BlockState state = block.getState();
			Torch torch = (Torch)state.getData();
			torch.setFacingDirection(blockFace);
			state.update(true, false);
			
			state = block.getState();
			torch = (Torch)state.getData();
			BlockFace blockFaceOut = torch.getAttachedFace();
			
			
			System.out.println("Data: " + data + " newdata: " + block.getData() + " blockface in: " + blockFace.name() + " blockface out: " + blockFaceOut.name());
			*/
		}
		else if(material.equals(Material.WOOD_STAIRS) ||
				material.equals(Material.SPRUCE_WOOD_STAIRS) ||
				material.equals(Material.BIRCH_WOOD_STAIRS) ||
				material.equals(Material.JUNGLE_WOOD_STAIRS) ||
				material.equals(Material.ACACIA_STAIRS) ||
				material.equals(Material.DARK_OAK_STAIRS) ||
				material.equals(Material.COBBLESTONE_STAIRS) ||
				material.equals(Material.BRICK_STAIRS) ||
				material.equals(Material.SMOOTH_STAIRS) ||
				material.equals(Material.NETHER_BRICK_STAIRS) ||
				material.equals(Material.SANDSTONE_STAIRS) ||
				material.equals(Material.QUARTZ_STAIRS))
		{
			byte direction = (byte) (data & 0x03);
			byte upside = (byte) (data & 0x04);

			BlockFace blockFace = BlockFace.NORTH;
			switch(direction)
			{
			case 0: blockFace = BlockFace.EAST; break;
			case 1: blockFace = BlockFace.WEST; break;
			case 2: blockFace = BlockFace.SOUTH; break;
			case 3: blockFace = BlockFace.NORTH; break;
			}
			
			BlockState state = block.getState();
			Stairs stairs = (Stairs)state.getData();
			stairs.setFacingDirection(blockFace);
			stairs.setInverted(upside>0);
			state.update(true, applyPhysics);
			
			//System.out.println("Indata = " + data + " Outdata = " + DataManager.getData(block));
		}
		else
		{
			// Unknown material, no way to handle this properly
			block.setData(data, applyPhysics);
		}
	}

	@SuppressWarnings("deprecation")
	public static void setData(MaterialData materialData, byte data)
	{
		materialData.setData(data);
	}

	
	@SuppressWarnings("deprecation")
	public static byte getData(Block block)
	{
		Material material = block.getType();
		
		if(material.equals(Material.WOOD_STAIRS) ||
				material.equals(Material.SPRUCE_WOOD_STAIRS) ||
				material.equals(Material.BIRCH_WOOD_STAIRS) ||
				material.equals(Material.JUNGLE_WOOD_STAIRS) ||
				material.equals(Material.ACACIA_STAIRS) ||
				material.equals(Material.DARK_OAK_STAIRS) ||
				material.equals(Material.COBBLESTONE_STAIRS) ||
				material.equals(Material.BRICK_STAIRS) ||
				material.equals(Material.SMOOTH_STAIRS) ||
				material.equals(Material.NETHER_BRICK_STAIRS) ||
				material.equals(Material.SANDSTONE_STAIRS) ||
				material.equals(Material.QUARTZ_STAIRS))
		{
			// getFacing() does not return the same values as setFacingDirection() sets! Falling back to deprecated functions.
			return block.getData();
			
			/*
			byte data = 0;

			BlockState state = block.getState();
			Stairs stairs = (Stairs)state.getData();
			if(stairs.isInverted())
			{
				data = (byte) (data | 0x04);
			}
			BlockFace blockFace = stairs.getFacing();
			if(blockFace.equals(BlockFace.EAST)) data = (byte) (data | 1);
			if(blockFace.equals(BlockFace.WEST)) data = (byte) (data | 0);
			if(blockFace.equals(BlockFace.SOUTH)) data = (byte) (data | 3);
			if(blockFace.equals(BlockFace.NORTH)) data = (byte) (data | 2);

			return data;
			*/
		}
		else
		{
			return block.getData();
		}
	}


}
