package biz.orgin.minecraft.hothgenerator;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class UndoBuffer
{
	private Map<String, Buffer> buffers;
	
	public UndoBuffer()
	{
		this.buffers = new HashMap<String, Buffer>();
	}

	public void pushBlob(String player, Blob blob)
	{
		Buffer buffer;
		if(this.buffers.containsKey(player))
		{
			buffer = this.buffers.get(player);
		}
		else
		{
			buffer = new Buffer();
			this.buffers.put(player, buffer);
		}
		
		buffer.pushBlob(blob);
	}
	
	/**
	 * Get the latest undo entry. Will return null if there is no undo set left
	 * @param player Name of player
	 * @return A Blob from the stack associated with the player name
	 */
	public Blob popBlob(String player)
	{
		Buffer buffer;
		if(this.buffers.containsKey(player))
		{
			buffer = this.buffers.get(player);
		}
		else
		{
			buffer = new Buffer();
			this.buffers.put(player, buffer);
		}
		
		return buffer.popBlob();
	}
	
	
	
	private class Buffer
	{
		private Stack<Blob> stack;
		
		public Buffer()
		{
			this.stack = new Stack<Blob>();
		}
		
		public void pushBlob(Blob blob)
		{
			this.stack.push(blob);
		}
		
		public Blob popBlob()
		{
			try
			{
				return this.stack.pop();
			}
			catch(EmptyStackException e)
			{
				return null;
			}
		}
	}
}
