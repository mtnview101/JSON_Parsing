	package core;

	import javax.json.Json;
	import javax.json.stream.JsonParser;
	import javax.json.stream.JsonParser.Event;
	import java.io.*;
	import java.math.*;
	import java.net.URL;
	import java.util.concurrent.TimeUnit;
	import java.util.logging.*;
	import org.openqa.selenium.*;
	import org.openqa.selenium.chrome.*;
	import org.openqa.selenium.edge.EdgeDriver;
	import org.openqa.selenium.firefox.FirefoxDriver;
	import org.openqa.selenium.htmlunit.HtmlUnitDriver;
	import org.openqa.selenium.ie.InternetExplorerDriver;
	import org.openqa.selenium.remote.CapabilityType;
	import org.openqa.selenium.remote.DesiredCapabilities;
	import org.openqa.selenium.safari.SafariDriver;
	import org.openqa.selenium.safari.SafariOptions;
	import java.util.Properties;
	import java.io.FileInputStream;

	public class JSON_API_eCOM_PF_External_CLA {
		static WebDriver driver;
	       Properties p = new Properties();
	       String getPropertyOut(String prop_file, String property) throws IOException { // method takes data from property file (internal)
	           // p.load(ClassLoader.getSystemResourceAsStream(prop_file));
	              p.load(new FileInputStream(prop_file));
	              return p.getProperty(property);} //return "property" value
		
		public static void setWebDriver(String browser) throws IOException {
			   Logger logger = Logger.getLogger("");
			   logger.setLevel(Level.OFF);
			   String driverPath = "";
			   
			  if ((browser == "Firefox") && (System.getProperty("os.name").toUpperCase().contains("MAC"))) 
			           {driverPath = "./src/main/resources/webdrivers/mac/geckodriver.sh";}

			else if ((browser == "Firefox") && (System.getProperty("os.name").toUpperCase().contains("WINDOWS"))) 
			           {driverPath = "./src/main/resources/webdrivers/pc/geckodriver.exe";}

			else if ((browser == "Chrome") && (System.getProperty("os.name").toUpperCase().contains("MAC"))) 
			           {driverPath = "./src/main/resources/webdrivers/mac/chromedriver";}

			else if ((browser == "Chrome") && (System.getProperty("os.name").toUpperCase().contains("WINDOWS"))) 
			           {driverPath = "./src/main/resources/webdrivers/pc/chromedriver.exe";}

			else if ((browser == "Safari") && (System.getProperty("os.name").toUpperCase().contains("MAC")))
			          {}

			else if ((browser == "Safari") && (System.getProperty("os.name").toUpperCase().contains("WINDOWS"))) 
			          {throw new IllegalArgumentException("Safari is not available for Windows");}

			else if ((browser == "IE") && (System.getProperty("os.name").toUpperCase().contains("MAC"))) 
			          {throw new IllegalArgumentException("Internet Explorer is not available for macOS");}

			else if ((browser == "IE") && (System.getProperty("os.name").toUpperCase().contains("WINDOWS"))) 
			          {driverPath = "./src/main/resources/webdrivers/pc/IEDriverServer.exe";}

			else if ((browser == "Edge") && (System.getProperty("os.name").toUpperCase().contains("MAC"))) 
			          {throw new IllegalArgumentException("Microsoft Edge is not available for macOS");}

			else if ((browser == "Edge") && (System.getProperty("os.name").toUpperCase().contains("WINDOWS")))
			          {driverPath = "./src/main/resources/webdrivers/pc/MicrosoftWebDriver.exe";}

			else if (browser == "HtmlUnit") {}

			else   {throw new IllegalArgumentException("Unknown OS");}

			switch (browser) {

			           case "Firefox":
			                  System.setProperty("webdriver.gecko.driver", driverPath);
			                  driver = new FirefoxDriver();
			                  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			                  driver.manage().window().maximize();
			                  break;

			           case "Chrome":
			                  System.setProperty("webdriver.chrome.driver", driverPath);
			                  System.setProperty("webdriver.chrome.silentOutput", "true");
			                  ChromeOptions option = new ChromeOptions();
			                  if (System.getProperty("os.name").toUpperCase().contains("MAC")) 
			                  {option.addArguments("-start-fullscreen");}
			                  else if (System.getProperty("os.name").toUpperCase().contains("WINDOWS")) 
			                  {option.addArguments("--start-maximized");}
			                  else {throw new IllegalArgumentException("Unknown OS");}
			                  driver = new ChromeDriver(option);
			                  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			                  break;

			           case "Safari":
			                  SafariOptions options = new SafariOptions();
			                  options.setUseCleanSession(true);
			                  options.setPort(55555);
			                  driver = new SafariDriver(options);
			                  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			                  driver.manage().window().maximize();
			                  break;

			           case "IE":
			                 DesiredCapabilities IEDesiredCapabilities = DesiredCapabilities.internetExplorer();
						      IEDesiredCapabilities.setCapability (InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
						      IEDesiredCapabilities.setCapability (InternetExplorerDriver.INITIAL_BROWSER_URL, "");
			                  IEDesiredCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			                  IEDesiredCapabilities.setJavascriptEnabled(true);
			                  IEDesiredCapabilities.setCapability("enablePersistentHover", false);

			                  System.setProperty("webdriver.ie.driver", driverPath);
			                  driver = new InternetExplorerDriver(IEDesiredCapabilities);
			                  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			                  driver.manage().window().maximize();
			                  break;

			           case "Edge":
			                  System.setProperty("webdriver.edge.driver", driverPath);
			                  driver = new EdgeDriver();
			                  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			                  driver.manage().window().maximize();
			                  break;

			           case "HtmlUnit":
			                  driver = new HtmlUnitDriver();
			                  ((HtmlUnitDriver) driver).setJavascriptEnabled(true);
			                  break;

			           default:
			                  throw new IllegalArgumentException("Unknown Browser");
			           }
			    }

		public static void main(String[] args) throws InterruptedException, IOException {
	          if (args.length == 0) {System.err.println("Please enter a path as an argument");System.exit(1);}
	          if (!args[0].contains(".properties")) {System.err.println("Argument must be an properties file");System.exit(1);} //extension properties file handling
	          String pf_out = args[0]; // String pf_out = "./src/main/resources/in.properties";
			  JSON_API_eCOM_PF_External_CLA p = new JSON_API_eCOM_PF_External_CLA();
	              
			  	  String us_currency_symbol = "$";
			  	  
			  	  String Euro 	= p.getPropertyOut(pf_out, "Euro"); // 1
	              String Yuan 	= p.getPropertyOut(pf_out, "Yuan");// 2
	              String Pound 	= p.getPropertyOut(pf_out, "Pound"); // 3
	              String Hryvnia = p.getPropertyOut(pf_out, "Hryvnia"); // 4
	              String Ruble 	= p.getPropertyOut(pf_out, "Ruble"); // 5
	              String Australian_dollar  = p.getPropertyOut(pf_out, "Australian_dollar"); // 6
	              String Norwegian_krone 	= p.getPropertyOut(pf_out, "Norwegian_krone"); // 7
	              String Tögrög 			= p.getPropertyOut(pf_out, "Tögrög"); // 8
	              String Peso 				= p.getPropertyOut(pf_out, "Peso"); //9 
	              String Guinean_franc 		= p.getPropertyOut(pf_out, "Guinean_franc"); //10 			  	
			  	

/*	              
  				  String Euro 	= "88.191.179.56"; // 1
	              String Yuan 	= "61.135.248.220";// 2
	              String Pound 	= "92.40.254.196"; // 3
	              String Hryvnia = "93.183.203.67"; // 4
	              String Ruble 	= "213.87.141.36"; // 5
	              String Australian_dollar  	= "27.111.255.255"; // 6
	              String Norwegian_krone 	= "46.249.255.255"; // 7
	              String Tögrög 	= "182.160.63.255"; // 8
	              String Peso 	= "158.170.255.255"; //9 
	              String Guinean_franc 	= "197.149.255.255"; //10 
*/	              
	              String ip_local 	= " ";
	              String currency_local=" ";

	             ////////////////////////////////////////////////////////////////////////////////

	              Logger logger = Logger.getLogger("");
	              logger.setLevel(Level.OFF);
	              
	           //String in_browser = System.getProperty("browser");
	    	   	 String in_browser = "HtmlUnit"; // "HtmlUnit" "Firefox" or "Chrome" or Safari or IE or Edge
	             setWebDriver(in_browser);
	             String url = "https://www.amazon.com/All-New-Amazon-Echo-Dot-Add-Alexa-To-Any-Room/dp/B01DFKC2SO";
	              driver.get(url);
	             

	 // All-New Echo Dot (2nd Generation) - Black
	  String product_title = driver.findElement(By.id("productTitle")).getText();    
	  double original_price = Double.parseDouble(driver.findElement(By.id("priceblock_ourprice")).getText().replace("$", "")); // 49.99
	  driver.quit();
	  System.out.println("Item: " + product_title + "; " + "US Price: " + us_currency_symbol + original_price + "; ");

	////////////////////////////////////////////////////////////////////////////////
	for(int i_ip=1; i_ip<=10 ; i_ip++){
	switch (i_ip){
	case 1: ip_local=Euro; currency_local="Euro"; break;
	case 2: ip_local=Yuan; currency_local="Yuan"; break;
	case 3: ip_local=Pound; currency_local="Pound"; break;
	case 4: ip_local=Hryvnia; currency_local="Hryvnia"; break;
	case 5: ip_local=Ruble; currency_local="Ruble"; break;
	case 6: ip_local=Australian_dollar; currency_local="Australian_dollar"; break;
	case 7: ip_local=Norwegian_krone; currency_local="Norwegian_krone"; break;
	case 8: ip_local=Tögrög; currency_local="Tögrög"; break;
	case 9: ip_local=Peso; currency_local="Peso"; break;
	case 10: ip_local=Guinean_franc; currency_local="Guinean_franc"; break;
	}
	URL api_url = new URL("http://www.geoplugin.net/json.gp?ip=" + ip_local);

	              final String e_cName = "geoplugin_countryName";
	              final String e_cCode = "geoplugin_currencyCode";
	              final String e_cSymbol = "geoplugin_currencySymbol_UTF8";
	              String country_name = null;
	              String currency_code = null;
	              String currency_symbol = null;
	              InputStream is = api_url.openStream();
	              JsonParser parser = Json.createParser(is);

	              while (parser.hasNext()) {
	                     Event e = parser.next();
	                     if (e == Event.KEY_NAME) {
	             switch (parser.getString()) {
		             case e_cName: parser.next(); country_name = parser.getString();break;   // France
		             case e_cCode:parser.next(); currency_code = parser.getString();break;      // EUR
		             case e_cSymbol:parser.next(); currency_symbol = parser.getString();break;}}} // €
	////////////////////////////////////////////////////////////////////////////////

	              double rate = 0;
	              String rate_id = "USD" + currency_code;          // USDEUR
	             // select * from yahoo.finance.xchange where pair in ("USDEUR")

	String rate_sql = "select%20*%20from%20yahoo.finance.xchange%20where%20pair%20in%20(\"" + rate_id + "\")"; 
	URL rate_url = new URL("http://query.yahooapis.com/v1/public/yql?q=" + rate_sql + "&format=json&env=store://datatables.org/alltableswithkeys");


	              InputStream is2 = rate_url.openStream();
	              JsonParser jp = Json.createParser(is2);
	              is2 = rate_url.openStream();
	              jp = Json.createParser(is2);

	              while (jp.hasNext()) {
	                     Event e = jp.next();
	                     if(e == Event.KEY_NAME) {switch (jp.getString()) {

	             case "Rate": jp.next(); rate = Double.parseDouble(jp.getString()); break;}}} // 0.9345

	             ////////////////////////////////////////////////////////////////////////////////

	       double local_price = new BigDecimal(original_price * rate).setScale(2, RoundingMode.HALF_UP).doubleValue();
	       //System.out.println("Item: " + product_title + "; " + "US Price: " + us_currency_symbol + original_price + "; ");
	       System.out.println(i_ip+". Price for " + country_name +  " is: in " +currency_local +" " + currency_symbol + local_price);
	       
	} //for i_ip
	} // main
	} // class


