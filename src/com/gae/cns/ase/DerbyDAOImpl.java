package com.gae.cns.ase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class DerbyDAOImpl implements AuctionDAO {

	private Connection connection;
	DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

	public DerbyDAOImpl() {
		connection = DBConnectionFactory.getConnection();
	}

	@Override
	public void storeUserTemp(UserDTO user) {
		// TODO Auto-generated method stub
		Statement stmnt = null;
		try {
			stmnt = connection.createStatement();
			// INSERT INTO TBL_USERS VALUES ('sercan', 'berkay',
			// 'sercan@gmail.com', 'sj', 'sercan','bilge','2000-03-18','19 Mc
			// Cabe Street', '1234567-7');
			/*
			 * USERNAME VARCHAR(20) PRIMARY KEY, PASSWORD VARCHAR(20), EMAIL_ADD
			 * VARCHAR(40), NICKNAME VARCHAR(40), FIRSTNAME VARCHAR(50),
			 * LASTNAME VARCHAR(30), YEAROFBIRTH DATE, FULLADDRESS VARCHAR(200),
			 * CREDITCARDNUMBER VARCHAR(50)
			 */
			System.out.println("Userr ccn = " + user.getCreditcardnumber());
			String sqlString = "INSERT INTO USER "
					+ "(USERNAME, PASSWORD, EMAIL_ADDRESS, NICKNAME, FIRSTNAME, LASTNAME, DOB, FULLADDRESS, CREDITCARDNUMBER)"
					+ " VALUES("
					+ "'" + user.getUsername() + "','" + user.getPassword() + "'," + "'"
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

}
