package highcardgui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class FirstSceneController implements Initializable {
        
    @FXML private TextField textInput;
    private int balance = 0;
    private boolean changeScene = false;
    
    /**
     * When this method is called it will change the label in the second scene
     * to an integer amount
     */
    public void enterAmount(){
        
        try{
            String amount = textInput.getText();            
            balance = Integer.parseInt(amount);
            changeScene = true;            
        }
        catch(Exception e){
            System.out.println("Not an integer");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Enter an integer value");
            alert.setContentText("Ex. for $1,000 enter 1000");
            alert.showAndWait();
            textInput.clear();
            textInput.requestFocus();
        }        
    }
    
    /**
     * When this method is called it will change the scene
     */
     public void changeScreenButtonPushed(ActionEvent event) throws IOException{
         enterAmount();
         if(changeScene){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
            Parent highCardGameParent = loader.load(); 
            
            Scene higCardGameScene = new Scene(highCardGameParent);
         
            FXMLDocumentController highCardController = loader.getController();
            highCardController.setAmount(textInput.getText());

            //add stage information
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(higCardGameScene);
            window.show();
        }
     }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
