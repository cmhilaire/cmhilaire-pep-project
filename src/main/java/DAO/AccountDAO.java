package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {

    /**
     * TODO: Retrieve all accounts from the account table.
     *
     * @return all accounts.
     */
    public List<Account> getAllAccounts(){
        Connection connection = ConnectionUtil.getConnection();
        List<Account> accounts = new ArrayList<>();
        try {
            //Write SQL logic here
            String sql = "SELECT * FROM account";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account account = new Account(rs.getInt("account_id"), 
                        rs.getString("username"),
                        rs.getString("password"));
                accounts.add(account);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return accounts;
    }

    
    
    /**
     * Add an account record into the database which matches the values contained in the account object.
     */
    public Account registerAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO account (username, password) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //write preparedStatement's setString and setInt methods here.
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());


            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_account_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_account_id, account.getUsername(), account.getPassword());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }


    public Account getAuthentificateAccount(Account account){
        if(account != null && account.getUsername() != "" && account.getPassword().length() > 3){
            Connection connection = ConnectionUtil.getConnection();
            try {
                //Write SQL logic here
                String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
                
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
    
                // preparedStatement methods.
                preparedStatement.setString(1, account.getUsername());
                preparedStatement.setString(2, account.getPassword());
    
                ResultSet rs = preparedStatement.executeQuery();
                while(rs.next()){
                    return new Account(rs.getInt("account_id"), 
                        rs.getString("username"),
                        rs.getString("password"));
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
        
        return null;
    }

}
