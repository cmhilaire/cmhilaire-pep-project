package Service;

import Model.Message;

import java.util.List;

import DAO.MessageDAO;

public class MessageService {
    private MessageDAO messageDAO;
    /**
     * no-args constructor for creating a new MessageService with a new MessageDAO.
     * There is no need to change this constructor.
     */
    public MessageService(){
        messageDAO = new MessageDAO();
    }

    /**
     * Constructor for a MessageService when a MessageDAO is provided.
     * This is used for when a mock MessageDAO that exhibits mock behavior is used in the test cases.
     * This would allow the testing of MessageService independently of MessageDAO.
     * There is no need to modify this constructor.
     * @param messageDAO
     */
    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    /**
     * TODO: Use the MessageDAO to retrieve all messages.
     *
     * @return all messages
     */
    public List<Message> getAllMessages() {
        return this.messageDAO.getAllMessages();
    }

    /**
     * Use MessageDAO to retrieve a message by ID
     * 
     * @param message_id
     * @return
     */
    public Message getMessageById(int message_id){
        return this.messageDAO.getMessageById(message_id);
    }

    /**
     * TODO: he creation of the message will be successful if and only if the message_text is not blank, 
     * is not over 255 characters.
     *
     * @param message an message object.
     * @return The persisted message if the persistence is successful.
     */
    public Message addMessage(Message message) {
        if(message.getMessage_text() != "" && message.getMessage_text().length() <= 255 ){
            return this.messageDAO.addMessage(message);
        }
        return null;
    }

    /**
     * Use MessageDAO to update a message by ID
     * 
     * @param message_id
     * @param message_text
     * @return
     */
    public Message patchMessageById(int message_id, Message message){
        if(message_id >= 0 && message.getMessage_text().trim() != "" && message.getMessage_text().trim().length() <= 255){
            return this.messageDAO.patchMessageById(message_id, message);
        }
        return null;
    }

    /**
     * Use MessageDAO to retrieve a message by Account ID
     * 
     * @param message_id
     * @return
     */
    public List<Message> getMessageByAccountId(int account_id){
        return this.messageDAO.getMessageByAccountId(account_id);
    }

    /**
     * Use MessageDAO to delete a message by ID
     * 
     * @param message_id
     * @return
     */
    public Message deleteMessageById(int message_id){
        return this.messageDAO.deleteMessageById(message_id);
    }
}
