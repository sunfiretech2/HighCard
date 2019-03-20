
package highcardgui;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;

//custom class
import shoe.Deck;

public class FXMLDocumentController implements Initializable {
    
    @FXML private Label betLabel;
    @FXML private Label totalBetLabel;
    @FXML private Label dealerLabel;
    @FXML private Label dealerTotalLabel;
    @FXML private Label winLossLabel;
    @FXML private Label playerLabel;
    @FXML private Label playerTotalLabel;
    @FXML private Label balanceLabel;
    @FXML private Label totalBalanceLabel;
    @FXML private Button fiveBetButton;
    @FXML private Button twentyFiveBetButton;
    @FXML private Button oneHundredBetButton;
    @FXML private Button betButton;
    @FXML private Button clearBetButton;
    
    private int totalBet = 0;
    private int balance = 0;
    private int counter = 0;
    private Deck deck;
    
    public FXMLDocumentController(){
        //constructor creates a new deck, populates with cards, and shuffles.
        deck = new Deck();
        deck.init();
        deck.shuffler();
    }
    
    public void setAmount(String input){
        balance = Integer.parseInt(input);
        totalBalanceLabel.setText("$" + input);
        System.out.println(balance);
        System.out.println(totalBet);
        betButtonDisable();
    }    
    public void betButtonDisable(){
        boolean k;
        k = ((balance-totalBet)<100);
        oneHundredBetButton.setDisable(k);
        k = ((balance-totalBet)<25);
        twentyFiveBetButton.setDisable(k);
        k = ((balance-totalBet)<5);
        fiveBetButton.setDisable(k);        
    }    
    public void betFive() {
        totalBet+=5; 
        betHelper();
    }
    public void betTwentyFive() {
        totalBet += 25;
        betHelper();       
    }
    public void betOneHundred() {
        totalBet += 100;
        betHelper();
    }    
    public void betHelper() {
        totalBetLabel.setText("$" + String.valueOf(totalBet));
        totalBalanceLabel.setText("$" + String.valueOf(balance-totalBet));
        playerTotalLabel.setText("__");       
        dealerTotalLabel.setText("__");       
        winLossLabel.setText("");
        betButtonDisable();        
        betButton.setDisable(false);
        clearBetButton.setDisable(false);        
    }     
    public void placeBet(){ 
        balance -= totalBet;  
        disableBetAndClearButtons();
        printAndShowWinner();        
        if(balance<5)
            addCash();        
        betButtonDisable();
        if(counter==52)
            reshuffle();
    }    
    public void disableBetAndClearButtons() {
        betButton.setDisable(true);
        clearBetButton.setDisable(true);
    }    
    public void printAndShowWinner(){
        //dealing out 2 cards
        for(int i=0; i<2; i++){                
            if(counter%2==0) {               
                playerTotalLabel.setText(deck.getMyCard(counter).toString());
            }
            else{
                dealerTotalLabel.setText(deck.getMyCard(counter).toString());
            }
            counter++;
        }
        if(deck.getMyCard(counter-2).getRank() > deck.getMyCard(counter-1).getRank()){
            winLossLabel.setText("Win");
            balance += 2*totalBet;
        }
        else if(deck.getMyCard(counter-2).getRank() < deck.getMyCard(counter - 1).getRank())
            winLossLabel.setText("Lose");
        else{
            winLossLabel.setText("Draw");
            balance+=totalBet;
        }
        totalBet = 0;
        totalBalanceLabel.setText("$" + String.valueOf(balance));
        totalBetLabel.setText("$"+String.valueOf(totalBet));
    }    
    public void addCash(){
        TextInputDialog addCash = new TextInputDialog("0"); 
        addCash.setTitle("Add Cash");
        addCash.setHeaderText("Balance is at $0");
        addCash.setContentText("To continue playing enter amount");
        Optional<String> result = addCash.showAndWait();
        if(result.isPresent()){
            try{
                balance = Integer.parseInt(result.get());
                totalBalanceLabel.setText("$" + balance); 
            }
            catch(NumberFormatException e){
                System.out.println("Printing");
               totalBetLabel.setText("0");
            }
        }
    }    
    public void reshuffle(){
        deck.shuffler();
        counter = 0;
    }    
    public void clearBet(){         
        totalBet = 0;
        totalBetLabel.setText("$" + String.valueOf(totalBet));  
        totalBalanceLabel.setText("$" + String.valueOf(balance));
        betButtonDisable();
        disableBetAndClearButtons();
        
    }
    
     /*
    @FXML
    private void handleButtonAction(ActionEvent event) {
        //System.out.println("You clicked me!");
        //label.setText("Hello World!");
    }
    */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        totalBetLabel.setText("$0");        
        playerTotalLabel.setText("__");       
        dealerTotalLabel.setText("__");       
        winLossLabel.setText("");
        betButton.setDisable(true);
        clearBetButton.setDisable(true);
       
    }    
    
}
