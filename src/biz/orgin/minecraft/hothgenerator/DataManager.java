package biz.orgin.minecraft.hothgenerator;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.material.MaterialData;
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
		else if(material.equals(Material.WOOL))
		{
			Wool wool = (Wool)block.getState();
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
			default:	wool.setColor(DyeColor.WHITE);
			}
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
		return block.getData();
	}


}
