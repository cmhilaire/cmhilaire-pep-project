package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    AccountService accountService;
    MessageService messageService;


    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::postRegisterAccountHandler);
        app.post("/login", this::postLoginAccountHandler);
        app.post("/messages", this::postCreateMessagesHandler);
        app.get("/messages", this::getCreatedMessagesHandler);
        app.get("/messages/{message_id}", this::getCreatedMessageByIdHandler);
        app.patch("/messages/{message_id}", this::patchUpdateMessageByIdHandler);
        app.get("/accounts/{account_id}/messages", this::getMessagesByAccountIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageById);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    /**
     * Handler to post a new User.
     * The Jackson ObjectMapper will automatically convert the JSON of the POST request into an Account object.
     * If AccountService returns a null account (meaning posting an Accont was unsuccessful), the API will return a 400
     * message (client error). There is no need to change anything in this method.
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin. It will
     *            be available to this method automatically thanks to the app.post method.
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    private void postRegisterAccountHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        List<Account> allAccount = this.accountService.getAllAccounts();
        if(!allAccount.contains(account)){
            Account addedAccount = this.accountService.registerAccount(account);
            if(addedAccount!=null){
                ctx.json(mapper.writeValueAsString(addedAccount));
                ctx.status(200);
            }else{
                ctx.status(400);
            }
        }else{
            ctx.status(400);
        }
        
    }


    /**
     * Handler to post a user login.
     */
    private void postLoginAccountHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account authAccount = this.accountService.loginAccount(account);
        if(authAccount != null){
            ctx.json(authAccount);
            ctx.status(200);
        }else{
            ctx.status(401);
        }
    }


    /**
     * Handler to post a message
     */
    private void postCreateMessagesHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message addedMessage = this.messageService.addMessage(message);
        if(addedMessage != null){
            ctx.json(addedMessage);
            ctx.status(200);
        }else{
            ctx.status(400);
        }
    }


    /**
     * Handler to get messages
     */
    private void getCreatedMessagesHandler(Context ctx) throws JsonProcessingException{
        List <Message> messages = this.messageService.getAllMessages();
        ctx.json(messages);
        ctx.status(200);
    }


    /**
     * Handler to get message by ID
     */
    private void getCreatedMessageByIdHandler(Context ctx) throws JsonProcessingException{
        Message message = null;

        if(ctx.pathParam("message_id").trim() != "" && Integer.parseInt(ctx.pathParam("message_id")) >= 0) 
            message = this.messageService.getMessageById(Integer.parseInt(ctx.pathParam("message_id")));
        
        if(message != null){
            ctx.json(message);
        }else{
            ctx.json("");
        }
        
        ctx.status(200);
    }


    /**
     * Handler to update message By ID
     * @param ctx
     * @throws JsonProcessingException
     */
    private void patchUpdateMessageByIdHandler(Context ctx) throws JsonProcessingException{
        Message updatedMessage = null;
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);

        // if(ctx.pathParam("message_id").trim() != "" && Integer.parseInt(ctx.pathParam("message_id")) >= 0 ) {
        if(ctx.pathParam("message_id").trim() != "" && Integer.parseInt(ctx.pathParam("message_id")) >= 0 ) {
            updatedMessage = this.messageService.patchMessageById(Integer.parseInt(ctx.pathParam("message_id")), message);
        }

        if(updatedMessage != null){
            ctx.json(updatedMessage);
            ctx.status(200);
        }else{
            ctx.status(400);
        }
    }

    /**
     * Handler to get messages by Account ID.
     * 
     * @param ctx
     * @throws JsonProcessingException
     */
    private void getMessagesByAccountIdHandler(Context ctx) throws JsonProcessingException{
        List <Message> messages = null;

        if(ctx.pathParam("account_id").trim() != "" && Integer.parseInt(ctx.pathParam("account_id")) >= 0) 
            messages = this.messageService.getMessageByAccountId(Integer.parseInt(ctx.pathParam("account_id")));
        
        if(messages != null){
            ctx.json(messages);
        }else{
            ctx.json("");
        }
        
        ctx.status(200);
    }


    /**
     * Handler to delete message by ID
     */
    private void deleteMessageById(Context ctx) throws JsonProcessingException{
        Message message = null;

        if(ctx.pathParam("message_id").trim() != "" && Integer.parseInt(ctx.pathParam("message_id")) >= 0) 
            message = this.messageService.deleteMessageById(Integer.parseInt(ctx.pathParam("message_id")));
        
        if(message != null){
            ctx.json(message);
        }else{
            ctx.json("");
        }
        
        ctx.status(200);
    }
}