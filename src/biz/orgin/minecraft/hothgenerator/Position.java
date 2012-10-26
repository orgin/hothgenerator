package biz.orgin.minecraft.hothgenerator;

public class Position
{
	public int x,y,z;

	public Position(int x, int y, int z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Position()
	{
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}

	@Override
	public int hashCode()
	{
		int result = 1;
		result = 43 * result + x;
		result = 43 * result + y;
		result = 43 * result + z;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null || !(obj instanceof Position))
		{
			return false;
		}
		Position other = (Position) obj;
		if (x != other.x || y != other.y || z != other.z)
		{
			return false;
		}
		return true;
	}
}
