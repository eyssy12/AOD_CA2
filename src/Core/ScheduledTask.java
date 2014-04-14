package Core;

import Interfaces.ITask;


/**
 * This class is used to load tasks into the timer.
 * Q. Why?
 * A. The timer only accepts objects that extend TimerTask. 
 * @author mcguinnn
 *
 */
public class ScheduledTask extends java.util.TimerTask
{
   private ITask task;

   public ScheduledTask(ITask task)
   {
	   this.task = task;
   }

   public void run()
   {
	   task.execute();
   }
}
