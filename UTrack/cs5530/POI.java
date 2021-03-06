package cs5530;

import java.sql.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.Date;


public class POI {

    String pid;
    String name;    
    
    public POI()
    {}

    public boolean addPOI(String _name, String _category, String _address, String _URL, 
			 String _tel_num, int _yr_est, String _hours, int _price, 
			 Connection con, Statement stmt){

	String sql = 
	    "INSERT INTO POI (name, category, address, URL, tel_num, yr_est, hours, price)" +
	    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
       
	String output = "";
	ResultSet rs = null;
	System.out.println("executing " + sql);
	try{       
	    PreparedStatement preparedStatement = con.prepareStatement(sql);

	    preparedStatement.setString(1, _name);
	    preparedStatement.setString(2, _category);
	    preparedStatement.setString(3, _address);
	    preparedStatement.setString(4, _URL);
	    preparedStatement.setString(5, _tel_num);
	    preparedStatement.setInt(6, _yr_est);
	    preparedStatement.setString(7, _hours);
	    preparedStatement.setInt(8, _price);

	    preparedStatement.executeUpdate();

	    System.out.println("Successfully added " + _name  + "as a new POI");
	    return true;
	}
	catch(Exception e){	    
	    System.out.println(e.toString());
	    System.out.println("Cannot execute the query");
	}
	
	return false;
    }

    public boolean updatePOI(String poiName,
			    String setQuery,
			    Connection con, Statement stmt){

	String sql = 
	    "UPDATE POI" +
	    " SET " + setQuery + 
	    " WHERE name = '" + poiName + "'";
       
	String output = "";
	ResultSet rs = null;
	System.out.println("executing " + sql);
	try{       
	    PreparedStatement preparedStatement = con.prepareStatement(sql);
	    preparedStatement.executeUpdate();
	}
	catch(Exception e){	    
	    System.out.println(e.toString());
	    System.out.println("Cannot execute the query");
	    return false;
	}

	System.out.println("Successfully updated " + poiName);
	return true;
    }

    public HashMap<String, String> getPOI(String pname, Statement stmt){

        String sql = "SELECT * " +
            "FROM POI WHERE name = '" + pname + "'";

        HashMap<String, String> output = new HashMap<String, String>();
        ResultSet rs = null;
        System.out.println("Executing: " + sql);
        try{
            // Execute sql query                                                                                                                                                                                                              
            rs = stmt.executeQuery(sql);

            String p;
            while (rs.next()){
                pid = rs.getString("pid");
                name = rs.getString("name");                


		output.put("pid", rs.getString("pid"));
		output.put("name", rs.getString("name"));
		output.put("category", rs.getString("category"));
		output.put("address", rs.getString("address"));
		output.put("URL", rs.getString("URL"));
		output.put("tel_num", rs.getString("tel_num"));
		output.put("yr_est", rs.getString("yr_est"));
		output.put("hours", rs.getString("hours"));
		output.put("price", rs.getString("price"));

		return output;
		    
            }
            rs.close();
        }
        catch(Exception e){
            System.out.println(e.toString());
            System.out.println("Cannot execute the query");
        }
        finally{
            try{
                if (rs!=null && !rs.isClosed())
                    rs.close();
            }
            catch(Exception e){
                System.out.println("Cannot close resultset");
            }
        }
        //return output;
	return new HashMap<String, String>();
    }



    public String getPOIs(Statement stmt){
    
	String sql = "SELECT * " +
	    "FROM POI ";

	String output = "";
	ResultSet rs = null;
	System.out.println("Executing: " + sql);
	try{
	    // Execute sql query
	    rs = stmt.executeQuery(sql);

	    String p;
	    while (rs.next()){
		pid = rs.getString("pid");
		name = rs.getString("name");

		output += 		  
		    "name: " + name  + "\n";
	    }			     
	    rs.close();
	}
	catch(Exception e){
	    System.out.println(e.toString());
	    System.out.println("Cannot execute the query");
	}
	finally{	 
	    try{
		if (rs!=null && !rs.isClosed())
		    rs.close();
	    }
	    catch(Exception e){
		System.out.println("Cannot close resultset");
	    }
	}
	return output;
    }

    public ArrayList<String> getPOIList(Statement stmt){
    
	String sql = "SELECT * " +
	    "FROM POI ";

	
	ArrayList<String> output = new ArrayList<String>();
	ResultSet rs = null;
	System.out.println("Executing: " + sql);
	try{
	    // Execute sql query
	    rs = stmt.executeQuery(sql);

	    String p;
	    while (rs.next()){
		pid = rs.getString("pid");
		name = rs.getString("name");

		output.add(name);

	    }			     
	    rs.close();
	}
	catch(Exception e){
	    System.out.println(e.toString());
	    System.out.println("Cannot execute the query");
	}
	finally{	 
	    try{
		if (rs!=null && !rs.isClosed())
		    rs.close();
	    }
	    catch(Exception e){
		System.out.println("Cannot close resultset");
	    }
	}
	return output;
    }
    
    public int getPid(String pname, Statement stmt){
	String sql = "SELECT pid " +
	    "FROM POI " +
	    "WHERE name = '" + pname + "'";

	String output = "";
	ResultSet rs = null;
	System.out.println("Executing: " + sql);
	try{
	    rs = stmt.executeQuery(sql);

	    String p;
	    if (rs.next()){
		pid = rs.getString("pid");
	    }	    	    
	    rs.close();
	    
	    return Integer.parseInt(pid);
	}
	catch(Exception e){
	    System.out.println(e.toString());
	    System.out.println("Cannot execute the query");
	}
	finally{	 
	    try{
		if (rs!=null && !rs.isClosed())
		    rs.close();
	    }
	    catch(Exception e){
		System.out.println("Cannot close resultset");
	    }
	}
	return -1;
    }


    public ArrayList<String> getTopFeedbackArr(String pname, int n,
				 Statement stmt, Connection con){
	String sql  = 
	    "SELECT P.pid, P.name pname, F.login, F.text, F.fbdate, F.score, av.avg_score " +
	    "FROM Feedback F, POI P, " + 
	    "(SELECT fid, rating, AVG(rating) avg_score " +
	    "FROM Rates GROUP BY fid) av " + 
	    "where F.fid = av.fid " +
	    "AND P.pid = F.pid " +
	    "AND P.name = '" + pname + "' " +
	    "ORDER BY av.avg_score DESC " + 
	    "LIMIT " + n;

	ArrayList<String> output = new ArrayList<String>();
	ResultSet rs = null;
	try{
	    // Execute sql query
	    rs = stmt.executeQuery(sql);

	    String p;
	    while (rs.next()){

		output.add( 
		    "Login: " + rs.getString("login") + " " +
		    "Text: " + rs.getString("text") + " " +
		    "Date: " + rs.getString("fbdate") + " " +
		    "Score: " + rs.getString("score") + " " +
		    "Average Score: " + rs.getString("avg_score") + "\n");	
	    }			     
	    rs.close();
	}
	catch(Exception e){
	    System.out.println(e.toString());
	    System.out.println("Cannot execute the query");
	}
	finally{	 
	    try{
		if (rs!=null && !rs.isClosed())
		    rs.close();
	    }
	    catch(Exception e){
		System.out.println("Cannot close resultset");
	    }
	}
	return output;
    }

    public String getTopFeedback(String pname, int n,
					       Statement stmt, Connection con){
        String sql  =
            "SELECT P.pid, P.name pname, F.login, F.text, F.fbdate, F.score, av.avg_score " +
            "FROM Feedback F, POI P, " +
            "(SELECT fid, rating, AVG(rating) avg_score " +
            "FROM Rates GROUP BY fid) av " +
            "where F.fid = av.fid " +
            "AND P.pid = F.pid " +
            "AND P.name = '" + pname + "' " +
            "ORDER BY av.avg_score DESC " +
            "LIMIT " + n;

        String output = "";
        ResultSet rs = null;
        try{
            // Execute sql query                                                                                                                                                                                                              
            rs = stmt.executeQuery(sql);

            String p;
            while (rs.next()){

                output += 
			   "Login: " + rs.getString("login") + " " +
			   "Text: " + rs.getString("text") + " " +
			   "Date: " + rs.getString("fbdate") + " " +
			   "Score: " + rs.getString("score") + " " +
			   "Average Score: " + rs.getString("avg_score") + "\n";
            }
            rs.close();
        }
        catch(Exception e){
            System.out.println(e.toString());
            System.out.println("Cannot execute the query");
        }
        finally{
            try{
                if (rs!=null && !rs.isClosed())
                    rs.close();
            }
            catch(Exception e){
                System.out.println("Cannot close resultset");
            }
        }
        return output;
    }


    public String setFavoritePOI(String pname, String login,
				 Statement stmt, Connection con){		
	int pid = getPid(pname, stmt);
	System.out.println(pid);
	String date;
	String sql = "INSERT INTO Favorites (pid, login, fvdate)" + 
	    "VALUES (?, ?, ?)";
       
	String output = "";
	ResultSet rs = null;
	System.out.println("executing " + sql);
	try{       
	    PreparedStatement preparedStatement = con.prepareStatement(sql);
	    preparedStatement.setInt(1, pid);
	    preparedStatement.setString(2, login);
	    

	    String d = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
	    System.out.println(d);
	    preparedStatement.setDate(3, java.sql.Date.valueOf(d));


	    preparedStatement.executeUpdate();
	}
	catch(Exception e){	    
	    System.out.println(e.toString());
	    System.out.println("This POI is already in your favorites");
	}

	return output;
    }


    public int getFid(int pid, String login, Statement stmt){
	String sql = "SELECT fid " +
	    "FROM Feedback " +
	    "WHERE login = '" + login + "' " + 
	    "AND pid = '" + pid  + "'";

	String fid = "";
	String output = "";
	ResultSet rs = null;
	System.out.println("Executing: " + sql);
	try{
	    rs = stmt.executeQuery(sql);

	    String p;
	    if (rs.next()){
		fid = rs.getString("fid");
	    }	    	    
	    rs.close();
	    
	    return Integer.parseInt(fid);
	}
	catch(Exception e){
	    System.out.println(e.toString());
	    System.out.println("Cannot execute the query");
	}
	finally{	 
	    try{
		if (rs!=null && !rs.isClosed())
		    rs.close();
	    }
	    catch(Exception e){
		System.out.println("Cannot close resultset");
	    }
	}
	return -1;
    }
    public String giveFeedback(String pname, String login, String text, int score,
			       Statement stmt, Connection con){
	int pid = getPid(pname, stmt);
	System.out.println(pid);
	String date;
	String sql = "INSERT INTO Feedback (pid, login, text, fbdate, score)" + 
	    "VALUES (?, ?, ?, ?, ?)";
       
	String output = "";
	ResultSet rs = null;
	System.out.println("executing " + sql);
	try{       
	    PreparedStatement preparedStatement = con.prepareStatement(sql);
	    preparedStatement.setInt(1, pid);
	    preparedStatement.setString(2, login);
	    preparedStatement.setString(3, text);
	    //preparedStatement.setDate(4, java.sql.Date.valueOf(java.time.LocalDate.now()));

	    String d = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
            System.out.println(d);
            preparedStatement.setDate(4, java.sql.Date.valueOf(d));


	    preparedStatement.setInt(5, score);

	    preparedStatement.executeUpdate();
	}
	catch(Exception e){	    
	    System.out.println(e.toString());
	    System.out.println("Cannot execute the query");
	}

	return output;
    }

    public ArrayList<String> getFeedbackRecordsArr(String pname, Statement stmt, Connection con){
	String sql = "SELECT * " +
            "FROM Feedback F, POI P " +
	    "WHERE F.pid = P.pid AND name='" + pname  + "'";

        ArrayList<String> output = new ArrayList<String>();
        ResultSet rs = null;
        System.out.println("Executing: " + sql);
        try{
            // Execute sql query                                                                                                                                                                                                              
            rs = stmt.executeQuery(sql);

            String p;
            while (rs.next()){
                output.add(
                    "Username:  " + rs.getString("login") +  "\t " +
                    "POI: " + rs.getString("name") + ",\t" +
                    "Text: " + rs.getString("text") + ",\t" +
                    "Score: " + rs.getString("score") + "\n");
            }
            rs.close();
        }
        catch(Exception e){
            System.out.println(e.toString());
            System.out.println("Cannot execute the query");
        }
        finally{
            try{
                if (rs!=null && !rs.isClosed())
                    rs.close();
            }
            catch(Exception e){
                System.out.println("Cannot close resultset");
            }
        }
        return output;
    }



    public String getFeedbackRecords(Statement stmt, Connection con){
	String sql = "SELECT * " +
	    "FROM Feedback F, POI P " + 
	    "WHERE F.pid = P.pid";

	String output = "";
	ResultSet rs = null;
	System.out.println("Executing: " + sql);
	try{
	    // Execute sql query
	    rs = stmt.executeQuery(sql);

	    String p;
	    while (rs.next()){
		output += 
		    "Username:  " + rs.getString("login") +  ",\t " + 
		    "POI: " + rs.getString("name") + ",\t" +
		    "Text: " + rs.getString("text") + ",\t" +
		    //		    "Date: " + rs.getString("fbdate") + ", " + 
		    "Score: " + rs.getString("score") + "\n";
	    }			     
	    rs.close();
	}
	catch(Exception e){
	    System.out.println(e.toString());
	    System.out.println("Cannot execute the query");
	}
	finally{	 
	    try{
		if (rs!=null && !rs.isClosed())
		    rs.close();
	    }
	    catch(Exception e){
		System.out.println("Cannot close resultset");
	    }
	}
	return output;
    }

    public String addUsefulnessRating(String login, String ulogin, String pname, int rating,
				      Statement stmt, Connection con){
	
	int pid = getPid(pname, stmt);
	int fid = getFid(pid, ulogin, stmt);

	System.out.println(pid);
	System.out.println(fid);
	String date;
	String sql = "INSERT INTO Rates (login, fid, rating)" + 
	    "VALUES (?, ?, ?)";
       
	String output = "";
	ResultSet rs = null;
	System.out.println("executing " + sql);
	try{       
	    PreparedStatement preparedStatement = con.prepareStatement(sql);
	    preparedStatement.setString(1, login);
	    preparedStatement.setInt(2, fid);
	    preparedStatement.setInt(3, rating);

	    preparedStatement.executeUpdate();
	}
	catch(Exception e){	    
	    System.out.println(e.toString());
	    System.out.println("Cannot execute the query");
	}

	return output;
    }

    public String createRangeQuery(int min, int max){	
	return min <= 0 &&  max <= 0 ? "" :
	    "price >= " + min + 
	    " AND price <= " + max;
    }  

    public String createQuery(String query, String name, String value){
	if(!value.equals("")){
	    if(query.equals(""))
		return " " + name + " LIKE '%" + value + "%'";	
	    else
		return " AND " + name + " LIKE '%" + value + "%'";   
	}
	return "";
    }

    public String createSortbyQuery(String query, String name, String value){
	if(!value.equals("")){
	    if(query.equals(""))
		return " " + name + " LIKE '%" + value + "%'";	
	    else
		return " AND " + name + " LIKE '%" + value + "%'";   
	}
	return "";
    }

    public String createSortbyFeedbackQuery(String query){

	return "SELECT P.pid, P.name, P.category, P.address, P.URL," +
	    " P.tel_num, P.yr_est, P.hours, P.price,  av.avg_score" + 
	    " FROM (" + query  + ") P," + 
	    " (SELECT pid, AVG(score) avg_score" + 
	    " FROM Feedback GROUP BY pid) av" + 
	    " WHERE P.pid = av.pid" + 
	    " ORDER BY av.avg_score DESC;";
    }
    
    public String createSortbyTrustedFeedbackQuery (String query){
	return "SELECT P.pid, P.name, P.category, P.address, P.URL," + 
	    " P.tel_num, P.yr_est, P.hours, P.price, av.avg_score" + 
	    " FROM (" +  query + ") P," + 
	    " (SELECT pid, F.login, F.text, AVG(score) avg_score" + 
	    " FROM Feedback F, Trust T" + 
	    " WHERE T.login2 = F.login" + 
	    " AND isTrusted = 1" + 
	    " GROUP BY pid) av" + 
	    " WHERE P.pid = av.pid" +
	    " ORDER BY av.avg_score DESC";
    }

    public String getKeywordTableQuery(String keyword){
	return " ,(SELECT h.pid" + 
	    " FROM HasKeywords h, Keywords k" +
	    " WHERE h.wid = k.wid" + 
	    " AND k.word LIKE '%" + keyword + "%') K";
    }

    public String createKeywordQuery(String query){
	if(query.equals(""))
	    return "  K.pid = P.pid";	
	else
	    return " AND K.pid = P.pid";

    }
    
    public String poiBrowsing(String query, String keywordQuery, String sortby,
			      Statement stmt, Connection con){
	String sql = "SELECT * " +	   
	    "FROM POI P " + keywordQuery + 
	    " WHERE " + query;

	if(sortby.equals("a")){
	    sql += " ORDER BY price DESC";
	}
	if(sortby.equals("b")){
	    sql = createSortbyFeedbackQuery(sql);		
	}
	if(sortby.equals("c")){
	    sql = createSortbyTrustedFeedbackQuery(sql);		
	}

	String output = "";
	ResultSet rs = null;
	System.out.println("Executing: " + sql);
	try{
	    // Execute sql query
	    rs = stmt.executeQuery(sql);

	    String p;
	    while (rs.next()){

		output += 
		    "Name: " + rs.getString("name") +
		    " Category: " + rs.getString("category") + 
		    " Address: " + rs.getString("address") + 
		    " URL: " + rs.getString("URL") + 
		    " Phone Number: " + rs.getString("tel_num") + 
		    " Hours: " + rs.getString("hours") +
		    " Price: " + rs.getInt("price") + "\n";
	    }			     
	    rs.close();
	}
	catch(Exception e){
	    System.out.println(e.toString());
	    System.out.println("Cannot execute the query");
	}
	finally{	 
	    try{
		if (rs!=null && !rs.isClosed())
		    rs.close();
	    }
	    catch(Exception e){
		System.out.println("Cannot close resultset");
	    }
	}
	return output;
    }

    public ArrayList<String> poiBrowsingArr(String query, String keywordQuery, String sortby,
			      Statement stmt, Connection con){
	String sql = "SELECT * " +	   
	    "FROM POI P " + keywordQuery + 
	    " WHERE " + query;

	if(sortby.equals("a")){
	    sql += " ORDER BY price DESC";
	}
	if(sortby.equals("b")){
	    sql = createSortbyFeedbackQuery(sql);		
	}
	if(sortby.equals("c")){
	    sql = createSortbyTrustedFeedbackQuery(sql);		
	}

	ArrayList<String> output = new ArrayList<String>();
	ResultSet rs = null;
	System.out.println("Executing: " + sql);
	try{
	    // Execute sql query
	    rs = stmt.executeQuery(sql);

	    String p;
	    while (rs.next()){

		output.add( 
		    "Name: " + rs.getString("name") +
		    " Category: " + rs.getString("category") + 
		    " Address: " + rs.getString("address") + 
		    " URL: " + rs.getString("URL") + 
		    " Phone Number: " + rs.getString("tel_num") + 
		    " Hours: " + rs.getString("hours") +
		    " Price: " + rs.getInt("price") + "\n");
	    }			     
	    rs.close();
	}
	catch(Exception e){
	    System.out.println(e.toString());
	    System.out.println("Cannot execute the query");
	}
	finally{	 
	    try{
		if (rs!=null && !rs.isClosed())
		    rs.close();
	    }
	    catch(Exception e){
		System.out.println("Cannot close resultset");
	    }
	}
	return output;
    }
    

    public ArrayList<String> getRecomendedPOIsArr(String login, int pid, Statement stmt){

        String sql =
            "select *" +
            " from" +
            " (select v1.login, v1.pid v1pid, count(*) num_visits" +
            " from Visit v1" +
            " group by v1.login, v1.pid) C1," +
            " (select P.pid c2pid, upoi.login, " +
            " P.name, P.category, P.address, P.URL, P.tel_num, P.yr_est, P.hours, P.price" +
            " from POI P," +
            " (select distinct V.pid, S.login" +
            " from" +
            " (select V.login" +
            " from Visit V " +
            " where V.pid = "+ pid +" and V.login != '"+ login +"' limit 1) S, Visit V" +
            " where S.login = V.login" +
            " and V.pid <> "+ pid  +") upoi" +
            " where P.pid = upoi.pid) C2" +
            " where C1.v1pid = C2.c2pid" +
            " and C1.login = C2.login" +
            " order by C1.num_visits DESC;";

        ArrayList<String> output = new ArrayList<String>();
        ResultSet rs = null;
        try{
            // Execute sql query                                                                                                                                                                                                                                                              
            rs = stmt.executeQuery(sql);

            String p;
            while (rs.next()){

                output.add(
                    "Name: " + rs.getString("name") +
                    " Category: " + rs.getString("category") +
                    " Address: " + rs.getString("address") +
                    " URL: " + rs.getString("URL") +
                    " Phone Number: " + rs.getString("tel_num") +
                    " Hours: " + rs.getString("hours") +
                    " Price: " + rs.getInt("price") +
                    " Visits: " + rs.getInt("num_visits") + "\n");

            }
            rs.close();
        }
        catch(Exception e){
            System.out.println(e.toString());
            System.out.println("Cannot execute the query");
        }
        finally{
            try{
                if (rs!=null && !rs.isClosed())
                    rs.close();
            }
            catch(Exception e){
                System.out.println("Cannot close resultset");
            }
        }
        return output;
    }


    public String getRecomendedPOIs(String login, int pid, Statement stmt){

	String sql =
	    "select *" +
	    " from" +
	    " (select v1.login, v1.pid v1pid, count(*) num_visits" +
	    " from Visit v1" +
	    " group by v1.login, v1.pid) C1," +
	    " (select P.pid c2pid, upoi.login, " +
	    " P.name, P.category, P.address, P.URL, P.tel_num, P.yr_est, P.hours, P.price" +
	    " from POI P," +
	    " (select distinct V.pid, S.login" +
	    " from" +
	    " (select V.login" +
	    " from Visit V " +
	    " where V.pid = "+ pid +" and V.login != '"+ login +"' limit 1) S, Visit V" +
	    " where S.login = V.login" +
	    " and V.pid <> "+ pid  +") upoi" +
	    " where P.pid = upoi.pid) C2" +
	    " where C1.v1pid = C2.c2pid" +
	    " and C1.login = C2.login" +
	    " order by C1.num_visits DESC;";

	String output = "*****RECOMENDED POI'S FOR YOU*****\n\n\n";
	ResultSet rs = null;
	try{
	    // Execute sql query
	    rs = stmt.executeQuery(sql);

	    String p;
	    while (rs.next()){
		
		output += 
		    "Name: " + rs.getString("name") +
		    " Category: " + rs.getString("category") + 
		    " Address: " + rs.getString("address") + 
		    " URL: " + rs.getString("URL") + 
		    " Phone Number: " + rs.getString("tel_num") + 
		    " Hours: " + rs.getString("hours") +
		    " Price: " + rs.getInt("price") + 
		    " Visits: " + rs.getInt("num_visits") + "\n";

	    }
	    output += "\n\n*****************************\n";
	    rs.close();
	}
	catch(Exception e){
	    System.out.println(e.toString());
	    System.out.println("Cannot execute the query");
	}
	finally{	 
	    try{
		if (rs!=null && !rs.isClosed())
		    rs.close();
	    }
	    catch(Exception e){
		System.out.println("Cannot close resultset");
	    }
	}
	return output;
    }

    public String getPopularForEachCategory(int limit, Statement stmt, Connection con){

	ArrayList<String> cats = getCategories(stmt);
	
	String output = "";
	for(String s : cats)	    
	    output += s + "  " + getPopularPOIbyCategory(s, limit, stmt, con) + "\n";
	
	return output;
    }
   
    public ArrayList<String> getPopularForEachCategoryArray(int limit, Statement stmt, Connection con){

        ArrayList<String> cats = getCategories(stmt);

        ArrayList<String> output = new ArrayList<String>();
        for(String s : cats)
            output.add(getPopularPOIbyCategory(s, limit, stmt, con));

	return output;
    }

    public String getPopularPOIbyCategory(String category, int limit, 
					  Statement stmt, Connection con){
	String sql = "select * " +
	    "from POI P, " +
	    "(select pid, count(*) num_visits from Visit group by pid) V " +
	    "where V.pid = P.pid " +
	    "and P.category = ? " +
	    "order by V.num_visits DESC " + 
	    "limit ?";

	String output = "";
	ResultSet rs = null;       
	try{  
	    
	    PreparedStatement preparedStatement = con.prepareStatement(sql);
	    preparedStatement.setString(1, category);
	    preparedStatement.setInt(2, limit);

	    rs = preparedStatement.executeQuery();

	    String p;
	    while (rs.next()){
		
		output += 
		    "Name: " + rs.getString("name") + "\n" +
		    " Category: " + rs.getString("category") + "\n" +
		    " Address: " + rs.getString("address") + "\n" +
		    " URL: " + rs.getString("URL") + "\n" +
		    " Phone Number: " + rs.getString("tel_num") + "\n" +
		    " Hours: " + rs.getString("hours") + "\n" +
		    " Price: " + rs.getInt("price") + "\n" +
		    " Visits: " + rs.getInt("num_visits") + "\n";
	    }
	    rs.close();
	}
	catch(Exception e){	    
	    System.out.println(e.toString());
	    System.out.println("Cannot execute the query");
	}
	finally{	 
	    try{
		if (rs!=null && !rs.isClosed())
		    rs.close();
	    }
	    catch(Exception e){
		System.out.println("Cannot close resultset");
	    }
	}


	return output;
    }

    public ArrayList<String> getPopularPOIbyCategoryArray(String category, int limit,
                                          Statement stmt, Connection con){
        String sql = "select * " +
            "from POI P, " +
            "(select pid, count(*) num_visits from Visit group by pid) V " +
            "where V.pid = P.pid " +
            "and P.category = ? " +
            "order by V.num_visits DESC " +
            "limit ?";

        ArrayList<String> output = new ArrayList<String>();
        ResultSet rs = null;
        try{

            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, category);
            preparedStatement.setInt(2, limit);

            rs = preparedStatement.executeQuery();

            String p;
            while (rs.next()){

                output.add(
                    "Name: " + rs.getString("name") +
                    " Category: " + rs.getString("category") +
                    " Address: " + rs.getString("address") +
                    " URL: " + rs.getString("URL") +
                    " Phone Number: " + rs.getString("tel_num") +
                    " Hours: " + rs.getString("hours") +
                    " Price: " + rs.getInt("price") +
                    " Visits: " + rs.getInt("num_visits") + "\n");
            }
            rs.close();
        }
        catch(Exception e){
            System.out.println(e.toString());
            System.out.println("Cannot execute the query");
        }
        finally{
            try{
                if (rs!=null && !rs.isClosed())
                    rs.close();
            }
            catch(Exception e){
                System.out.println("Cannot close resultset");
            }
        }


        return output;
    }

    
    public ArrayList<String> getCategories(Statement stmt){
	String sql = "select category from POI group by category";
	ResultSet rs = null;
	ArrayList<String> categories = new ArrayList<String>();
	try{
	    // Execute sql query
	    rs = stmt.executeQuery(sql);

	    String p;
	    while (rs.next()){		
		categories.add(rs.getString("category"));
	    }
	    rs.close();
	}
	catch(Exception e){
	    System.out.println(e.toString());
	    System.out.println("Cannot execute the query");
	}
	finally{	 
	    try{
		if (rs!=null && !rs.isClosed())
		    rs.close();
	    }
	    catch(Exception e){
		System.out.println("Cannot close resultset");
	    }
	}
	return categories;
    }
    
    public String getCostliestForEachCategory(int limit, Statement stmt, Connection con){

	ArrayList<String> cats = getCategories(stmt);
	
	String output = "";
	for(String s : cats)
	    
	    output += s + ":\n" + getMostExpensiveByCategory(s, limit, stmt, con) + "\n";
	
	return output;
    }
    /*
    public HashMap<String, String> getCostliestForEachCategoryHM(int limit, Statement stmt, Connection con){

	ArrayList<String> cats = getCategories(stmt);
	
	String output = "";
	for(String s : cats)
	    
	    output += s + ":\n" + getMostExpensiveByCategory(s, limit, stmt, con) + "\n";
	
	return output;
	}
    */

    public String getMostExpensiveByCategory(String category, int limit, 
					     Statement stmt, Connection con){
	String sql = 
	    "select * " +
	    "from POI P, " +
	    "(select V.login, V.pid, AVG(cost/numberofheads) cst_per_head " +
	    "from Visit V, VisEvent ve where V.vid =ve.vid " +
	    "group by pid) V " +
	    "where V.pid = P.pid " +
	    "and P.category = ? " +
	    "order by V.cst_per_head DESC " +
	    "limit ?;";
	    
	String output = "";
	ResultSet rs = null;       
	try{  
	    
	    PreparedStatement preparedStatement = con.prepareStatement(sql);
	    preparedStatement.setString(1, category);
	    preparedStatement.setInt(2, limit);

	    rs = preparedStatement.executeQuery();

	    String p;
	    while (rs.next()){
		
		output += 
		    "Name: " + rs.getString("name") +
		    " Category: " + rs.getString("category") + 
		    " Address: " + rs.getString("address") + 
		    " URL: " + rs.getString("URL") + 
		    " Phone Number: " + rs.getString("tel_num") + 
		    " Hours: " + rs.getString("hours") +
		    " Price: " + rs.getInt("price") + 
		    " Average Cost / Person: " + rs.getInt("cst_per_head") + "\n";
	    }
	    rs.close();
	}
	catch(Exception e){	    
	    System.out.println(e.toString());
	    System.out.println("Cannot execute the query");
	}
	finally{	 
	    try{
		if (rs!=null && !rs.isClosed())
		    rs.close();
	    }
	    catch(Exception e){
		System.out.println("Cannot close resultset");
	    }
	}


	return output;
    }

public String getBestRatedForEachCategory(int limit, Statement stmt, Connection con){

	ArrayList<String> cats = getCategories(stmt);
	
	String output = "";
	for(String s : cats)
	    
	    output += s + ":\n" + getBestRatedByCategory(s, limit, stmt, con) + "\n";
	
	return output;
    }

    public String getBestRatedByCategory(String category, int limit, 
					     Statement stmt, Connection con){
	String sql = 
	    "select * " +
	    "from POI P, " +
	    "(select pid, AVG(score) cnt from Feedback group by pid)	V " +
	    "where V.pid = P.pid " +
	    "and P.category = ? " +
	    "order by V.cnt DESC " +
	    "limit ?";
	    	    
	String output = "";
	ResultSet rs = null;       
	try{  
	    
	    PreparedStatement preparedStatement = con.prepareStatement(sql);
	    preparedStatement.setString(1, category);
	    preparedStatement.setInt(2, limit);

	    rs = preparedStatement.executeQuery();

	    String p;
	    while (rs.next()){
		
		output += 
		    "Name: " + rs.getString("name") +
		    " Category: " + rs.getString("category") + 
		    " Address: " + rs.getString("address") + 
		    " URL: " + rs.getString("URL") + 
		    " Phone Number: " + rs.getString("tel_num") + 
		    " Hours: " + rs.getString("hours") +
		    " Price: " + rs.getInt("price") + 
		    " Average Feedback score: " + rs.getInt("cnt") + "\n";
	    }
	    rs.close();
	}
	catch(Exception e){	    
	    System.out.println(e.toString());
	    System.out.println("Cannot execute the query");
	}
	finally{	 
	    try{
		if (rs!=null && !rs.isClosed())
		    rs.close();
	    }
	    catch(Exception e){
		System.out.println("Cannot close resultset");
	    }
	}


	return output;
    }
}
