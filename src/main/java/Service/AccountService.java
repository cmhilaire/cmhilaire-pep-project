package Service;

import Model.Account;

import java.util.List;

import DAO.AccountDAO;

/**
 * The purpose of a Service class is to contain "business logic" that sits between the web layer (controller) and
 * persistence layer (DAO). That means that the Service class performs tasks that aren't done through the web or
 * SQL: programming tasks like checking that the input is valid, conducting additional security checks, or saving the
 * actions undertaken by the API to a logging file.
 *
 * It's perfectly normal to have Service methods that only contain a single line that calls a DAO method. An
 * application that follows best practices will often have unnecessary code, but this makes the code more
 * readable and maintainable in the long run!
 */
public class AccountService {
    private AccountDAO accountDAO;
    /**
     * no-args constructor for creating a new AccountService with a new AccountDAO.
     * There is no need to change this constructor.
     */
    public AccountService(){
        accountDAO = new AccountDAO();
    }
    /**
     * Constructor for a AccountService when a AccountDAO is provided.
     * This is used for when a mock AccountDAO that exhibits mock behavior is used in the test cases.
     * This would allow the testing of AccountService independently of AccountDAO.
     * There is no need to modify this constructor.
     * @param accountDAO
     */
    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }
    /**
     * TODO: Use the AccountDAO to retrieve all accounts.
     *
     * @return all accounts
     */
    public List<Account> getAllAccounts() {
        return this.accountDAO.getAllAccounts();
    }
    /**
     * TODO: The registration will be successful if and only if the username is not blank, 
     * the password is at least 4 characters long, and an Account with that username does not already exist.
     *
     * @param account an account object.
     * @return The persisted account if the persistence is successful.
     */
    public Account registerAccount(Account account) {
        // Check if username exist in DB AND Passwordt is at least lenght > 3.
        if(account.getUsername()!="" && account.getPassword().length() > 3 ){
            return this.accountDAO.registerAccount(account);
        }
        return null;
    }

    public Account loginAccount(Account account) {
        return this.accountDAO.getAuthentificateAccount(account);
    } 
}