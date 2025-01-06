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
	public String MissingFields = "#form-errors";
	public String ErrorModal = "#form-errors";
	public String ISBN = "//*[@id='isbn']";
	public String LibraryOfCongress_checkbox = "//*[@id='z3950_1']";
	public String MissingFieldsList = "//*[@id='form-errors']";
	public String kohaItemTypeLink = "//li[.//strong/em[text()='Koha item type']]//a[@class='linkfield btn btn-link']";
	public String kohaItemType = "//*[@class='select2-selection select2-selection--single subfield_not_filled']";
	public String kohaItemTypeInput = "//*[@class='select2-search__field']";
	public String Book_submit = "//*[@id='submit_z3950_search']";
	public String carat_dropdown = "//button[@class='btn-xs dropdown-toggle']";
	public String Import = "(//a[@class='chosen' and @title='Import' and @data-action='import'])[2]";
	public String Save_Dropdown = "//*[@class='btn btn-primary dropdown-toggle']//*[@class='caret']";
	public String SaveAndViewRecord = "//a[text()='Save and view record']";
	public String GoToField = "//a[text()=' Go to field']";
	public String BookTitleField = "//*[@id='catalogue_detail_biblio']//*[@class='title']";
	public String TagName = "//*[@class='subfield_line']";
	public String Cat_search = "//input[@id='cat_search']";
	public String cat_searchResult="//*[@class='biblio-title']";
	public String bookDetail_edit = "//*[text()=' Edit ']";
	public String bookDetail_editItem = "//*[text()='Edit items']";
	public String Shelving_location = "//select[@name='items.location']//following::span[1]";
	public String Date_acquired = "//label/span[@title='Date acquired']";
	public String Date_acquiredInput = "//*[@class='input_marceditor items.dateaccessioned noEnterSubmit flatpickr-input active']";
//	public String Date_acquiredInput = "//input[@name='items.dateaccessioned' and contains(@class, 'flatpickr-input')]";
	
//	public String Current_library = "//span[@id='select2-tag_952_subfield_b_154714-container']";
	public String Current_library = "//*[@title='Current library']//following::span[4]";
	public String Home_library = "//*[@title='Home library']//following::span[4]";
//	public String Home_library = "//span[@id='select2-tag_952_subfield_a_34781-container']";
	public String Barcode = "//*[@class='input_marceditor items.barcode noEnterSubmit']";
	public String multi_option = "//*[@name='add_multiple_copies']";
	public String no_of_copies = "//*[@id='number_of_copies']";
	public String add_copies = "//*[@id='add_multiple_copies_submit']";
	public String item_table = "//table[@id='itemst']//td[8]";
	public String book_AddToCart = "//*[@id='cart1']";
	public String cart = "//*[@id='cartmenulink']";
	public String cart_iconcount = "//*[@id='basketcount']";
	public String cart_Checkbox = "//table[@id='itemst']//tr[1]//input[@type='checkbox']";
	public String placehold = "//*[@id='place_hold']";
	public String placeholdPatronSearch = "//*[@value='Search']";
	public String confirmhold = "//*[@id='hold_grp_btn']";
	public String bookTitle = "//*[@class='biblio-title']";
	public String Patron_Search = "//*[@value='Search']";
	public String Hold_History = "//*[text()='Holds history']";
	public String holdsHistoryTableRows = "//table[@id='table_holdshistory']//tr//td";
	public String  holdStatus = "//table[@id='table_holdshistory']//tr//td[10]";
	public String checkout ="//a[text()='Check out']";
	public String hold = "//*[@id='holds-tab']";
	public String holdsTabBookTitle = "//*[@id='holds-table']//tr//td[2]";
	public String holdsTabBookDate = "//*[@id='holds-table']//tr//td[1]";
	public String checkoutBarcodeInput="//*[@id='barcode']";
	
	public String checkoutsTableRows = "//table[@id='issues-table']//tr//td"	;	
	public String checkoutTab = "//*[@id='checkouts-tab']";
	public String checkoutsTabCount = "//*[@class='checkout_count']";
	public String circulationTab= "//*[@class='icon_general icon_circulation']";
	public String checkInTab="//*[text()=' Check in']";
	public String checkInBarcodeInput ="//*[@class='barcode focus']" ;
	public String checkInButton = "//button[text()='Check in']";
	public String checkedInBookTitle = "//table[@id='checkedintable']//tr//td[2]";
	public String checkedInItemsTable ="//table[@id='checkedintable']";

	
	
	
	
	
	
}
