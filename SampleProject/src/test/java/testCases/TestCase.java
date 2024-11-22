/*
 * package testCases;
 * 
 * import java.util.HashMap; import java.util.List; import
 * org.openqa.selenium.Keys; import org.openqa.selenium.WebElement; import
 * org.testng.annotations.Test; import configuration.BrowserConfig; import
 * configuration.Global; import configuration.Keywords; import
 * pages.CommonMethods; import utilities.Mob_Locators; import
 * utilities.MyThread; import utilities.UI_Locators;
 * 
 * public class TestCase extends Global { public BrowserConfig config = new
 * BrowserConfig(); public CommonMethods common = new CommonMethods(); public
 * Keywords actions = new Keywords(); public UI_Locators ulocator = new
 * UI_Locators(); public Mob_Locators mlocator = new Mob_Locators();
 * 
 * public static HashMap<String, String> hashMap = new HashMap();
 * 
 * @Test(priority = 0, groups = {"All","UI"}) public void
 * TC_UI_Fetch_Movieand_Wikipedialink() {
 * 
 * common.Launch("Chrome", "https://www.google.co.in/");
 * //logger.logPass("Chrome lauched sucessfully", "Y"); String[] movie = common
 * .csv_FileRead(System.getProperty("user.dir")+"/Testdata/Movielist.csv"); for
 * (int i = 1; i <= movie.length; i += 2) { try {
 * actions.getWebElement(ulocator.search).sendKeys(movie[i] + Keys.ENTER);
 * List<WebElement> links = actions.getWebElementList(ulocator.wikipedia_Link);
 * if(links.size()!=0) hashMap.put(movie[i], links.get(0).getAttribute("href"));
 * else System.out.println(movie[i]+" is not a valid movie name"); } catch
 * (Exception e) { System.out.println(e.getMessage()); }
 * actions.waitExplicit(ulocator.search, 10); actions.clear(ulocator.search);
 * 
 * } }
 * 
 * @Test(priority = 1 , groups = {"All","UI"}) public void
 * TC_UI_ExtractDirName_and_Compare() { try { for (String movie :
 * hashMap.keySet()) { MyThread temp = new MyThread("Thread"+movie);
 * temp.start(); String Wikipedialink = hashMap.get(movie).toString(); try {
 * common.Launch("Chrome", Wikipedialink); String Wiki_Directorname =
 * actions.getWebElement(ulocator.directed_By).getText(); String Imdb_link =
 * actions.getWebElement(ulocator.wiki_imdblink).getAttribute("href");
 * common.Launch("Chrome", Imdb_link);
 * actions.waitExplicit(ulocator.imdblink_directed_By, 30); String
 * Imdb_Directorname =
 * actions.getWebElement(ulocator.imdblink_directed_By).getText();
 * System.out.println(movie+"Dir1:" + Wiki_Directorname + "Dir2:" +
 * Imdb_Directorname); if(Wiki_Directorname.equals(Imdb_Directorname)) {
 * System.out.println(movie+" Passed"); logger.logPass(movie, Wiki_Directorname,
 * Imdb_link); } //Assert.assertEquals(Wiki_Directorname, Imdb_Directorname); }
 * catch (Exception e) { System.out.println(e.getMessage()); } } } catch
 * (Exception e) {
 * 
 * }
 * 
 * }
 * 
 * @Test(priority = 2 , groups = {"All","Mobile"}) public void
 * TC_Mob_Fetch_Movieand_Wikipedialink() {
 * 
 * common.Launch("Chrome", "https://www.google.co.in/"); String[] movie = common
 * .csv_FileRead(System.getProperty("user.dir")+"/Testdata/Movielist.csv"); for
 * (int i = 1; i <= movie.length; i += 2) { try {
 * actions.getWebElement(mlocator.search).sendKeys(movie[i] + Keys.ENTER);
 * List<WebElement> links = actions.getWebElementList(mlocator.wikipedia_Link);
 * hashMap.put(movie[i], links.get(0).getAttribute("href")); } catch (Exception
 * e) { System.out.println(e.getMessage()); }
 * actions.waitExplicit(mlocator.search, 10); actions.clear(mlocator.search);
 * 
 * } }
 * 
 * @Test(priority = 3 , groups = {"All","Mobile"}) public void
 * TC_Mob_ExtractDirName_and_Compare() { try { for (String movie :
 * hashMap.keySet()) { MyThread temp = new MyThread("Thread"+movie);
 * temp.start(); //common.get_DirectorandCompare(movie,
 * hashMap.get(movie).toString()); String Wikipedialink =
 * hashMap.get(movie).toString(); try { common.Launch("Chrome", Wikipedialink);
 * String Wiki_Directorname =
 * actions.getWebElement(mlocator.directed_By).getText(); String Imdb_link =
 * actions.getWebElement(mlocator.wiki_imdblink).getAttribute("href");
 * common.Launch("Chrome", Imdb_link);
 * actions.waitExplicit(mlocator.imdblink_directed_By, 30); String
 * Imdb_Directorname =
 * actions.getWebElement(mlocator.imdblink_directed_By).getText();
 * System.out.println(movie+"Dir1:" + Wiki_Directorname + "Dir2:" +
 * Imdb_Directorname); if(Wiki_Directorname.equals(Imdb_Directorname)) {
 * System.out.println(movie+"Pass"); //logger.logPass(movie, Wiki_Directorname,
 * Imdb_link, Imdb_Directorname); } } catch (Exception e) {
 * System.out.println(e.getMessage()); } } } catch (Exception e) {
 * 
 * }
 * 
 * }
 * 
 * }
 */