package testCases;
import java.io.IOException;
import org.testng.annotations.Test;

import koha.Koha_start;


public class Step_IN_API {
	public Koha_start kohaStart = new Koha_start();
   

  
      
    @Test(priority = 0)
    public void createPatron() throws IOException {
    	kohaStart.createPatronWithAPI();
       
    }
   
    

}
