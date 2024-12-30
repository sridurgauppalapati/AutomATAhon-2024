package utilities;

import groovyjarjarantlr.collections.List;

public class Koha_locator {

	public String admin = "//a[text()='Koha administration']";
	public String Library = "//a[text()='Libraries']";
	public String add_lib = "//a[text()=' New library']";
	public String branchname = "//*[@id='branchname']";
	public String branchcode = "//*[@id='branchcode']";
	public String library_submit = "//*[@id='Aform']/fieldset[3]/input";
	public String Home= "//*[@title='Home']";
	public String Patrons = "//a[text()='Patrons']";
	public String New_Patrons = "//*[@id='new-patron-button']/button";
	public String Patron_lib = "//a[text()='my first library']";
	public String Patron_Surname = "//*[@id='surname']";
	public String Patron_Firstname = "//*[@id='firstname']";
	public String Patron_Email = "//*[@id='email']";
	public String Patron_CardNumber = "//*[@id='cardnumber']";
	public String Patron_Save = "//*[@id='saverecord']";
	
	
	public String Cataloging = "//a[@class='icon_general icon_cataloging' and text()='Cataloging']";
	public String New_Record = "//*[@id='newRecord']";
	public String Server_Search = "//a[text()=' Z39.50/SRU search']";
	public String MissingFieldsList = "//div[@id='form-errors']//ul//li//strong";
//	public String SearchFrame = 
	public String ErrorModal = "//*[@id ='check_errors']";
	public String ISBN = "//*[@id='isbn']";
	public String LibraryOfCongress_checkbox = "//*[@id='z3950_1']";
	public String Book_submit = "//*[@id='submit_z3950_search']";
	public String carat_dropdown = "//button[@class='btn-xs dropdown-toggle']";
	public String Import = "(//a[@class='chosen' and @title='Import' and @data-action='import'])[2]";
	public String Save_Dropdown = "//*[@class='btn btn-primary dropdown-toggle']//*[@class='caret']";
	public String SaveAndViewRecord = "//a[text()='Save and view record']";
	public String GoToField = "//a[text()=' Go to field']";
	public String BookTitleField = "//*[@id='catalogue_detail_biblio']//*[@class='title']";
	
	
	
	
	
	
	
}
