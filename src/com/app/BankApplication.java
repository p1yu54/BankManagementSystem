package com.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.Model.Customer;
import com.Util.DBConnection;
import com.Util.InputUtil;

public class BankApplication {
	public static void main(String[] args) throws Exception{
		String name ;
		String phoneNum;
		int customerId;
		// main menu page
		while (true) {
			System.out.println("\n--- BANK MANAGEMENT SYSTEM ---");
			System.out.println("1. Register Customer");
			System.out.println("2. Customer Login");
			System.out.println("3. Exit");

			int input = InputUtil.readInt("Enter your choice: ");

			switch (input) {
			case 1:
				name = InputUtil.readString("Enter your Name");
				phoneNum = InputUtil.readString("Enter your phone Number: ");
				Customer customer = new Customer(name, phoneNum);
				customer.registerCustomer();
				break;

			case 2:
				Connection con = DBConnection.getConnection();
				
				name = InputUtil.readString("Enter your name: ");
				phoneNum = InputUtil.readString("Enter your phone number: "); 
				
				String query = "SELECT customerId FROM customer WHERE name = ? AND phoneNumber = ?";
				PreparedStatement stmt = con.prepareStatement(query);
				
				stmt.setString(1, name);
				stmt.setString(2, phoneNum);
				
				ResultSet res = stmt.executeQuery();
				
				if(!res.next()) {
					throw new Exception("No such customer found!");
				}
				
				customerId = res.getInt("customerId");
				CustomerMenu .customerMenu(customerId);
				break;

			case 3:
				System.out.println("GoodBye!!");
				System.exit(0);

			default:
				System.out.println("Invaild Input!!");
				break;
			}
		}
	}
}
