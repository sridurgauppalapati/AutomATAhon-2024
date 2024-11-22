package Main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.testng.TestNG;
import org.testng.annotations.Test;

import utilities.MyThread;

public class mainRun {
	
	public static void main(String[] args) {
		//final CyclicBarrier gate = new CyclicBarrier(3);
		mainRun mr = new mainRun();
		 MyThread R1 = new MyThread("testngUI");
	      R1.start();
	      
	      MyThread R2 = new MyThread("testngMobile");
	      R2.start();
		}
	
	@Test
	public void main() {
		//final CyclicBarrier gate = new CyclicBarrier(3);
		mainRun mr = new mainRun();
		 MyThread R1 = new MyThread("testngUI");
	      R1.start();
	      
	      MyThread R2 = new MyThread("testngMobile");
	      R2.start();
		}
	
	public void runTestNgXml_UI() {
		TestNG runner = new TestNG();
		try {
			List<String> suitefiles = new ArrayList<String>();
			suitefiles.add(System.getProperty("user.dir")+"\\src\\test\\resources\\testngUI.xml");
			runner.setTestSuites(suitefiles);
			runner.run();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void runTestNgXml_Mob() {
		TestNG runner = new TestNG();
		try {
			List<String> suitefiles = new ArrayList<String>();
			suitefiles.add(System.getProperty("user.dir")+"\\src\\test\\resources\\testngMobile.xml");
			runner.setTestSuites(suitefiles);
			runner.run();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
