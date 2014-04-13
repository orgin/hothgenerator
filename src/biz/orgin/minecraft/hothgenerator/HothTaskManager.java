package biz.orgin.minecraft.hothgenerator;

import java.util.Vector;

import org.bukkit.Bukkit;

public class HothTaskManager
{
	private int taskId;
	
	private HothGeneratorPlugin plugin;
	private Vector<HothRunnable> taskList;
	
	public HothTaskManager(HothGeneratorPlugin plugin)
	{
		this.plugin = plugin;
		this.taskList = new Vector<HothRunnable>();
		this.resume();
	}
	
	public void addTask(HothRunnable task)
	{
		this.taskList.add(task);
	}
	
	public void pause()
	{
		if(this.taskId!=-1)
		{
			Bukkit.getServer().getScheduler().cancelTask(this.taskId);
		}
	}
	
	public void resume()
	{
		this.taskId = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new TaskExecutor(this.plugin), 10, 5);
	}
	
	private class TaskExecutor implements Runnable
	{
		private HothGeneratorPlugin plugin;
		
		public TaskExecutor(HothGeneratorPlugin plugin)
		{
			this.plugin = plugin;
		}

		@Override
		public void run()
		{
			if(taskList.size()>0)
			{
				HothRunnable task = taskList.get(0);
				this.plugin.logMessage("Executing task: " + task.getName() + " with parameters: " + task.getParameterString(), true);
				taskList.remove(task);
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, task);
			}
			
		}
	}
	
}
