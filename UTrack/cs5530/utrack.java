package cs5530;

import java.lang.*;
import java.sql.*;
import java.io.*;

public class utrack {

    static String name;
    static String login = null;
    static String password;
    static String address;
    static String phone_num;
    static Connector con = null;
    static String choice;
    static String sql = null;
    static int c = 0;
    static User user;
    static boolean logedIn = false;

    /**
     * @param args
     */
    public static void displayMenu()
    {
	System.out.println("        Welcome to the UTrack System     ");
	if(!logedIn){
	    System.out.println("1. Login: ");
	    System.out.println("2. Register user:");
	}
	System.out.println("3. Add Visit to POI:");
	System.out.println("4. Add new POI:");
	System.out.println("5. Update POI:");
	System.out.println("6. Set favorite POI:");
	System.out.println("7. Give feedback to a POI:");
	System.out.println("8. Rate feedback:");
	System.out.println("9. Set user as trusted or untrusted:");
	System.out.println("10. Show useful feedback:");
	System.out.println("11. POI browsing:");
	System.out.println("please enter your choice:");
    }
	
    public static void main(String[] args) {
	user = new User();

	System.out.println("Example for cs5530");

	try{	    
	    //remember to replace the password
	    con = new Connector();
	    System.out.println ("Database connection established");
		    
	    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	            
	    while(true)
		{
		    displayMenu();
		    while ((choice = in.readLine()) == null && choice.length() == 0);
		    try{
			c = Integer.parseInt(choice);
		    }catch (Exception e){	            		 
			continue;
		    }
		    
		    if (c == 0){
			POI poi =  new POI();
			user.setLogin("jay8chuy");
			

			String _date = "09-22-1990";
			System.out.println(_date + " to: ");
			String[] d = _date.split("-");
			_date = d[2] +"-"+d[0]+"-"+d[1];

			System.out.println(_date);
			//java.sql.Date.valueOf("2013-09-04");
			System.out.println(java.sql.Date.valueOf(_date));

			/*			
			System.out.println(
					   poi.getRecomendedPOIs("chuy8jay", 200,
								 con.stmt));

			String pname = "Starbucks";
			System.out.println(pname +" pid => " + 
					   poi.getPid(pname, con.stmt));
			
			poi.setFavoritePOI(pname, user.getLogin(), con.stmt, con._con);

			poi.addPOI("Tony's", "Restaurant", "222 s main", "tonys.com", 
				   "(801)123-45678", 1990, "10am-3am", 10,
				   con._con, con.stmt);
			*/	 
			continue;
		    }
		    
		    //if (c<1 | c>5)
		    //continue;

		    switch (c){
		    case 1: // Login
			login(in);
			break;
		    case 2: // Register
			registerUser(in);
			break;
		    case 3: // Add visit
			if(logedIn)
			    addVisit(in);
			else
			    System.out.println(
					       "You are not logged in. Log in and try again.");
			break;
		    case 4:
			if(logedIn && user.isAdmin())
			    addPOI(in);
			else
			    System.out.println(
					       "You are not logged in. Log in and try again.");
			    break;
		    case 5:
			if(logedIn && user.isAdmin())
			    updatePOI(in);
			else
			    System.out.println(
					       "You are not logged in. Log in and try again.");
			break;
		    case 6:
			if(logedIn)
			    setFavoritePOI(in);
			else
			    System.out.println(
					       "You are not logged in. Log in and try again.");
			break;
		    case 7:
			if(logedIn)
			    giveFeedback(in);
			else
			    System.out.println(
					       "You are not logged in. Log in and try again.");
			break;
		    case 8:
			if(logedIn)
			    addUsefulnessRating(in);
			else
			    System.out.println(
					       "You are not logged in. Log in and try again.");
			break;
		    case 9:
			if(logedIn)
			    setTrustOrUntrust(in);
			else
			    System.out.println(
					       "You are not logged in. Log in and try again.");
			break;
		    case 10:
			if(logedIn)
			    getTopFeedback(in);
			else
			    System.out.println(
					       "You are not logged in. Log in and try again.");
			break;
		    case 11:
			//if(logedIn)
			    poiBrowsing(in);
			    //else
			    //System.out.println(
			    //"You are not logged in. Log in and try again.");
			break;

		    default:
			System.out.println("Remeber to pay us!");
			con.stmt.close(); 				    
			break;
		    }
		}
	}
	catch (Exception e){
	    e.printStackTrace();
	    System.err.println ("Either connection error or query execution error!");
	}
	finally{
	    if (con != null){
		try{
		    con.closeConnection();
		    System.out.println ("Database connection terminated");
		}
			 
		catch (Exception e) { /* ignore close errors */ }
	    }	 
	}
    }

    public static void registerUser(BufferedReader in) throws IOException {
	System.out.println("Enter your user login:");
	while ((login = in.readLine()) == null && login.length() == 0);
				    
	System.out.println("Enter your full name:");
	while ((name = in.readLine()) == null && name.length() == 0);

	System.out.println("Enter your password:");
	while ((password = in.readLine()) == null && password.length() == 0);

	System.out.println("Enter your address:");
	while ((address = in.readLine()) == null && address.length() == 0);

	System.out.println("Enter your phone number:");
	while ((phone_num = in.readLine()) == null && phone_num.length() == 0);
				           
	System.out.println(
			   user.registerUser(login, name, true, password, address, 
					     phone_num, con._con, con.stmt));
    }

    public static void login(BufferedReader in) throws IOException {

	System.out.println("Enter your user login:");
	while ((login = in.readLine()) == null && login.length() == 0);
	
	System.out.println("Enter your password:");
	while ((password = in.readLine()) == null && password.length() == 0);

	if (user.loginUser(login, password, con.stmt)){
	    logedIn = true;
	    System.out.println("Login Successfull!");	    
	}
	else{
	    login = null;
	    password = null;
	    System.out.println("Invalid password or username");
	}
    }

    public static void updatePOI(BufferedReader in) throws IOException{
	String choice;
	String setQuery = "";
	String poiName;
	System.out.println("Which POI would you like to modify:");
	while ((choice = in.readLine()) == null && choice.length() == 0);
	poiName = choice;
	
	boolean incomingInput = true;
	while(incomingInput){
	    int c = 0;
	    System.out.println("Enter 0 when done");
	    System.out.println("1: name");
	    System.out.println("2, category");
	    System.out.println("3, address");
	    System.out.println("4, URL");
	    System.out.println("5, tel_num");
	    System.out.println("6, yr_est");
	    System.out.println("7, hours");
	    System.out.println("8, price");

	    System.out.println("Which field woud you like to modify:");
	    while ((choice = in.readLine()) == null && choice.length() == 0);

	    try{
		c = Integer.parseInt(choice);
	    }
	    catch(Exception e){
		System.out.println("Must be a choice within range");
		continue;
	    }

	    switch(c){
	    case 0:
		POI poi = new POI();
		setQuery = setQuery.substring(0, setQuery.length() - 2);
		System.out.println("\n" + setQuery + "\n");
		poi.updatePOI(poiName, setQuery, con._con, con.stmt);
		incomingInput = false;
		break;
	    case 1:
		System.out.println("Enter new name:");
		while ((choice = in.readLine()) == null && choice.length() == 0);
		setQuery += "name = '" + choice + "', ";
		break;
	    case 2:
		System.out.println("Enter new category:");
		while ((choice = in.readLine()) == null && choice.length() == 0);
		setQuery += "category = '" + choice + "', ";
		break;
	    case 3:
		System.out.println("Enter new address:");
		while ((choice = in.readLine()) == null && choice.length() == 0);
		setQuery += "address = '" + choice + "', ";
		break;
	    case 4:
		System.out.println("Enter new URL:");
		while ((choice = in.readLine()) == null && choice.length() == 0);
		setQuery += "URL = '" + choice + "', ";
		break;
	    case 5:
		System.out.println("Enter new phone number:");
		while ((choice = in.readLine()) == null && choice.length() == 0);
		setQuery += "tel_num = '" + choice + "', ";
		break;
	    case 6:
		System.out.println("Enter new year of establishment:");
		while ((choice = in.readLine()) == null && choice.length() == 0);
		setQuery += "yr_est = '" + choice + "', ";
		break;
	    case 7:
		System.out.println("Enter new hours of business:");
		while ((choice = in.readLine()) == null && choice.length() == 0);
		setQuery += "hours = '" + choice + "' ";
		break;
	    case 8:
		System.out.println("Enter new price:");
		while ((choice = in.readLine()) == null && choice.length() == 0);
		setQuery += "price = '" + choice + "', ";
		break;
	    default:
		System.out.println("No available choice");
	    }
	}

    }

    public static void addPOI(BufferedReader in) throws IOException{	
	POI poi = new POI();
	String name;
	String category;
	String address;
	String URL;
	String tel_num;
	int yr_est;
	String hours;
	int price;

	try{
	    System.out.println("What is the name of the POI:");
	    while ((name = in.readLine()) == null && name.length() == 0);
	
	    System.out.println("What category does it fall in:");
	    while ((category = in.readLine()) == null && category.length() == 0);

	    System.out.println("What is the POI's address:");
	    while ((address = in.readLine()) == null && address.length() == 0);

	    System.out.println("What is the POI's URL:");
	    while ((URL = in.readLine()) == null && URL.length() == 0);

	    System.out.println("What is the POI's phone number:");
	    while ((tel_num = in.readLine()) == null && tel_num.length() == 0);
	    
	    String yr;
	    System.out.println("What is the POI's year of establishment:");
	    while ((yr = in.readLine()) == null && yr.length() == 0);
	    
	    System.out.println("What are the hours of operation:");
	    while ((hours = in.readLine()) == null && hours.length() == 0);

	    String pr;
	    System.out.println("What is the estimated price per person:");
	    while ((pr = in.readLine()) == null && pr.length() == 0);
	    
	    yr_est = Integer.parseInt(yr);
	    price = Integer.parseInt(pr);	    

	    poi.addPOI(name, category, address, URL, tel_num, yr_est, hours, price,
		       con._con, con.stmt);
	}
	catch (Exception e){
	    System.out.println("Must provide a number");
	}       
	
    }

    public static void setFavoritePOI(BufferedReader in) throws IOException{
	String pname;
	POI poi = new POI();
	System.out.println(poi.getPOIs(con.stmt));

	System.out.println("What is the name of the POI you want to favorite:");
	while ((pname = in.readLine()) == null && pname.length() == 0);
	
	poi.setFavoritePOI(pname, user.getLogin(), con.stmt, con._con);
    }

    public static void giveFeedback(BufferedReader in) throws IOException{
	String pname;
	String text;
	int score;
	POI poi = new POI();
	System.out.println(poi.getPOIs(con.stmt));

	System.out.println("What is the name of the POI you want to give feedback:");
	while ((pname = in.readLine()) == null && pname.length() == 0);

	String s;
	System.out.println("What score would you give this POI:\n" + 
			   "0= terrible, 10= excellent");
	while ((s = in.readLine()) == null && s.length() == 0);

	try{
	    score = Integer.parseInt(s);
	}
	catch(Exception e){
	    System.out.println("Score must be a number");
	    return;
	}

	System.out.println("Text:");
	while ((text = in.readLine()) == null && text.length() == 0);
	
	poi.giveFeedback(pname, login, text, score, con.stmt, con._con);
    }

    public static void addUsefulnessRating(BufferedReader in) throws IOException{
	String ulogin;
	String pname;
	String rating;

	POI poi = new POI();
	System.out.println(poi.getFeedbackRecords(con.stmt, con._con));

	System.out.println("What is the name of the user that gave the feedback:");
	while ((ulogin = in.readLine()) == null && ulogin.length() == 0);

	System.out.println("What is the name of the feedback POI:");
	while ((pname = in.readLine()) == null && pname.length() == 0);

	System.out.println("What rating would you give this feedback:\n"+
			   "useless, useful, or very useful");
	while ((rating = in.readLine()) == null && rating.length() == 0);
	
	int r = 0;
	try{
	    rating = rating.toLowerCase();
	    if(rating.equals("useless"))
		r = 0;
	    else if (rating.equals("useful"))
		r = 1;
	    else if (rating.equals("very useful"))
		r = 2;
	    else{
		System.out.println("Please pick one of the choices provided");
		return;
	    }
	    
	}
	catch(Exception e){
	    System.out.println("Rating must be a number");
	}
	
	poi.addUsefulnessRating(user.getLogin(), ulogin, pname, r, con.stmt, con._con);
    }

    public static void addVisit(BufferedReader in) throws IOException{
	POI poi = new POI();
	String pname;
	String date;
	String answer;
	String scost;
	String snumOfPeople;

	System.out.println("\n" + poi.getPOIs(con.stmt));
	
	System.out.println("Which POI did you visit:");
	while ((pname = in.readLine()) == null && pname.length() == 0);	

	System.out.println("What was the cost of the visit: ");
	while ((scost = in.readLine()) == null && scost.length() == 0);	

	System.out.println("How many people attended: ");
	while ((snumOfPeople = in.readLine()) == null && snumOfPeople.length() == 0);

	System.out.println("What was the date of the visit\n"+
			   "Add in this format with no spaces (MM-DD-YYYY):");
	while ((date = in.readLine()) == null && date.length() == 0);	

	System.out.println("You visited " + pname + " on " + date + "\n" +
			   snumOfPeople + " attended\n" +
			   "Cost: " + scost  + "\n " +
			   "\nAre you sure you want to add this to your visits?" + 
			   "\nType yes/no");
	while ((answer = in.readLine()) == null && answer.length() == 0);	

	int cost;
	int numOfPeople;
	try{
	    cost = Integer.parseInt(scost);
	    numOfPeople = Integer.parseInt(snumOfPeople);
	}
	catch(Exception e){
	    System.out.println("Make sure the cost and the number of people is a number");
	    return;
	}
	answer = answer.toLowerCase();
	if (answer.equals("yes")){
	    int pid = user.addVisit(pname, cost, numOfPeople, date, con.stmt, con._con);
	    System.out.println(poi.getRecomendedPOIs(user.getLogin(), pid, con.stmt));
				  
	}
    }
    
    public static void setTrustOrUntrust(BufferedReader in) throws IOException{
	String other_login;
	boolean isTrusted = true;
	System.out.println("Which user would you like to set: ");
	while ((other_login = in.readLine()) == null && other_login.length() == 0);

	String t;
	System.out.println("Is this user trusted: " + 
			   "\nyes/no?");
	while ((t = in.readLine()) == null && t.length() == 0);	
	
	if (t.equals("yes")){
	    isTrusted = true;
	}
	else if (t.equals("no")){
	    isTrusted = false;
	}
	else {
	    System.out.println("Answer must be yes or no");
	    return;
	}
	
	user.setTrustOrUntrust(other_login, isTrusted, con.stmt, con._con);
    }
    
    public static void getTopFeedback(BufferedReader in) throws IOException{	
	POI poi = new POI();
	String pname;
	int n;
	
	System.out.println("Which POI's feedbacks would you like to see: ");
	while ((pname = in.readLine()) == null && pname.length() == 0);

	String num;
	System.out.println("How many items: ");
	while ((num = in.readLine()) == null && num.length() == 0);

	try{
	    n = Integer.parseInt(num);
	}
	catch(Exception e){
	    	System.out.println("\nNumber of items should be a number\n");
		return;
	}
	System.out.println(
			   poi.getTopFeedback(pname, n, con.stmt, con._con));
    }

    public static void poiBrowsing(BufferedReader in) throws IOException{
	// Get rid of me after testing
	user.setLogin("jay8chuy");
	
	POI poi = new POI();
	String query;
	System.out.println("Pick your search queries by their number" +
			   "(Seperate your answers by a comma)");
	System.out.println("1. Price Range");
	System.out.println("2. Address City/State");
	System.out.println("3. Name");
	System.out.println("4. Keywords");
	System.out.println("5. Category");
	while ((query = in.readLine()) == null && query.length() == 0);

	String[] queries = query.split(",");
	
	int num;
	int minRange = 0;
	int maxRange = 0;
	String keywords;
	String address;
	String pname;
	String category;
	boolean haskeyword = false;
	String keywordQuery = "";
	query = "";
	for (int i = 0; i < queries.length; i++){
	    try{
		num = Integer.parseInt(queries[i]);
		
		switch(num){
		case 1:	
		    String min;
		    String max;
		    System.out.println("Min price range");
		    while ((min = in.readLine()) == null && min.length() == 0);

		    System.out.println("Max price range");
		    while ((max = in.readLine()) == null && max.length() == 0);
		    
		    try{
			minRange = Integer.parseInt(min);
			maxRange = Integer.parseInt(max);			
		    }
		    catch(Exception e){
			System.out.println("Ranges should be a number: ");
		    }
		    query += poi.createRangeQuery(minRange, maxRange);
		    
		    break;
		case 2:
		    System.out.println("City or State: ");
		    while ((address = in.readLine()) == null && address.length() == 0);
		    query += poi.createQuery(query, "address", address);
		    		    
		    break;
		case 3:
		    System.out.println("POI name: ");
		    while ((pname = in.readLine()) == null && pname.length() == 0);
		    query += poi.createQuery(query, "name", pname);

		    break;
		case 4:
		    System.out.println("What Keyword:");
		    while ((keywords = in.readLine()) == null && keywords.length() == 0);
		    /*** Not quite working yer ***\
		    keywordQuery = poi.getKeywordTableQuery(keywords);

		    query += poi.createKeywordQuery(query);
		    haskeyword = true;
		    */
		    break;
		case 5:	
		    System.out.println("What Category: ");
		    while ((category = in.readLine()) == null && category.length() == 0);

		    query += poi.createQuery(query, "category", category);

		    break;
		default:
		    System.out.println("Your number must be in the range");
		}
	    }
	    catch(Exception e){
		System.out.print("All of your choices must be numbers");
	    }
	}	

	String sortby;
	System.out.println("Sort by:");
	System.out.println("(a) by price\n" + 
			   "(b) by average numerical score of feedback\n" +
			   "(c) by the average numerical score of the trusted user feedbacks\n" +
			   "(d) Any order: ");
	while ((sortby = in.readLine()) == null && sortby.length() == 0);
	
	if(sortby.equals("a") | sortby.equals("b") | sortby.equals("c") | sortby.equals("d")){
	    
	    if(haskeyword)
		System.out.println(poi.poiBrowsing(query, keywordQuery, sortby, 
						   con.stmt, con._con));
	    else
		System.out.println(poi.poiBrowsing(query, "", sortby, con.stmt, con._con));
	}
	else{
	    System.out.println("Input must be a-d");
	    return;
	}
	    
	
    }
}
