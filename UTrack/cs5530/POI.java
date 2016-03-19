package cs5530;

import java.sql.*;
import java.util.HashMap;

public class POI {

    String pid;
    String name;    
    
    public POI()
    {}

    public String addPOI(String _name, String _category, String _address, String _URL, 
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
	}
	catch(Exception e){	    
	    System.out.println(e.toString());
	    System.out.println("Cannot execute the query");
	}

	return output;
    }

    public String updatePOI(String poiName,
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
	}

	return output;
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
		    "pid: " + pid +  " -> " + 
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
	System.out.println("Executing: " + sql);
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
	    preparedStatement.setDate(3, java.sql.Date.valueOf(java.time.LocalDate.now()));

	    preparedStatement.executeUpdate();
	}
	catch(Exception e){	    
	    System.out.println(e.toString());
	    System.out.println("Cannot execute the query");
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
	    preparedStatement.setDate(4, java.sql.Date.valueOf(java.time.LocalDate.now()));
	    preparedStatement.setInt(5, score);

	    preparedStatement.executeUpdate();
	}
	catch(Exception e){	    
	    System.out.println(e.toString());
	    System.out.println("Cannot execute the query");
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

    

    public String getRecomendedPOIs(String login, int pid, Statement stmt){

	String sql = 
	    " select *" +
	    " from POI P," +
	    " (select distinct V.pid" +
	    " from" +
	    " (select V.login" +
            " from Visit V, (select login, count(*) cnt from Visit group by login) mto" +
            " where V.pid = " + pid +
            " and V.login != '" + login + "'" +
            " and V.login = mto.login" +
	    " and mto.cnt > 1 limit 1) S, Visit V" +
	    " where S.login = V.login" +
	    " and V.pid <> " + pid + ") upoi" +
	    " where P.pid = upoi.pid;";

	sql =
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

    public String getUserPOIVisitCount(){
	String sql = 
	    "select login, pid, count(*)" +
	    " from Visit" + 
	    " group by login, pid;";

	    return sql;
	    /*
	String output = "*****RECOMENDED POI'S FOR YOU*****\n\n\n";
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
	    */
    }

}
