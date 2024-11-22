package utilities;

import java.util.ArrayList;
import java.util.List;

import org.testng.TestNG;

public class MyThread extends Thread{
	
	private Thread t;
	   private String threadName;
	   
	   public MyThread( String xmlFile) {
	      threadName = xmlFile;
	     System.out.println("Executing xml "+xmlFile);
	   }
	   
	   public void run() {
	      System.out.println("Running " +  threadName );
	      try {
	    			TestNG runner = new TestNG();
	    			try {
	    				List<String> suitefiles = new ArrayList<String>();
	    				suitefiles.add(System.getProperty("user.dir")+"\\src\\test\\resources\\"+threadName+".xml");
	    				runner.setTestSuites(suitefiles);
	    				runner.run();
	    			}catch(Exception e) {
	    				System.out.println(e.getMessage());
	    		}
	      } catch (Exception e) {
	         System.out.println("Thread " +  threadName + " interrupted.");
	      }
	    //  System.out.println("Thread " +  threadName + " exiting.");
	   }
	   
	   public void start () {
	      System.out.println("Starting " +  threadName );
	      if (t == null) {
	         t = new Thread (this, threadName);
	         t.start ();
	      }
	   }

}
