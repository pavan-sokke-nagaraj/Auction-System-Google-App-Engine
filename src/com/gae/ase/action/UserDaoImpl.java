package com.gae.ase.action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class UserDaoImpl implements AuctionDAO {

	private Connection connection;
	DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

	public UserDaoImpl() {
		connection = DBConnectionFactory.getConnection();
	}

	@Override
	public void storeUserTemp(UserDTO user) {
		Statement stmnt = null;
		try {
			stmnt = connection.createStatement();

			System.out.println("Userr ccn = " + user.getCreditcardnumber());
			String sqlString = "INSERT INTO USER "
					+ "(USERNAME, PASSWORD, EMAIL_ADDRESS, NICKNAME, FIRSTNAME, LASTNAME, DOB, FULLADDRESS, CREDITCARDNUMBER)"
					+ " VALUES(" + "'" + user.getUsername() + "','" + user.getPassword() + "'," + "'"
					+ user.getEmailadd() + "', " + "'" + user.getNickname() + "'," + "'" + user.getFirstname() + "',"
					+ "'" + user.getLastname() + "'," + "'" + user.getYearofbirth() + "'," + "'" + user.getFulladdress()
					+ "'," + "'" + user.getCreditcardnumber() + "')";
			int result = stmnt.executeUpdate(sqlString);
			System.out.println(sqlString);
			System.out.println("Statement successfully executed " + result);
			stmnt.close();
			System.out.println("sql string is " + sqlString);
		} catch (Exception e) {
			System.out.println("Unable to store a user temp! ");
			e.printStackTrace();
		}
	}

	@Override
	public UserDTO getUserDetails(String userName) {
		UserDTO user = null;
		try {
			Statement stmnt = connection.createStatement();
			ResultSet results = stmnt.executeQuery("SELECT * FROM TBL_USERS WHERE USERNAME = '" + userName + "'");
			while (results.next()) {
				String Usern = results.getString("USERNAME");
				String passw = results.getString("PASSWORD");
				String eMail = results.getString("EMAIL_ADDRESS");
				String nickName = results.getString("NICKNAME");
				String firstName = results.getString("FIRSTNAME");
				String lastName = results.getString("LASTNAME");
				String yob = results.getString("DOB");
				String fullAdd = results.getString("FULLADDRESS");
				String ccn = results.getString("CREDITCARDNUMBER");
				user = new UserDTO(Usern, passw, eMail, nickName, firstName, lastName, yob, fullAdd, ccn);
			}
			results.close();
			stmnt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

}
