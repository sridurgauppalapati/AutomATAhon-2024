package utilities;

public class Mob_Locators {

	public String searchBtn = "//div[@class='mobile-topbar-header-content non-search-mode cbox']//button[@aria-label='Search YouTube']//c3-icon";

	public  String search = "//input[@class='searchbox-input title']";

	public  String step_in_channel = "//h4[contains(text(),'STeP-IN Forum')]";

	public String Video="//a[contains(text(),'Videos')]";
	
	public String VideoSettings = "//div[@id='movie_player']//button[@aria-label='Settings']";

	public String QualityMenu = "//div[@role='menu']//div[@class='ytp-menuitem-label'][text()='Quality']";
	
	public String QualityList = "//div[@class='ytp-panel ytp-quality-menu']";
	
	public String upnext="//h4[@class='compact-media-item-headline'][text()]";
	
	public String SelectVideo = "//h4[@class='compact-media-item-headline'][text()='##']";
	
	public String ShowMore = "//div[@class='cbox'][text()='Show more']";

}
