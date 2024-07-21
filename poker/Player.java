/*****************************************
 * 
 *Ike Schmidt
 *ijs2130
 *Player.java - The class which defines the player and their attributes in
 * the Game class. The player has a hand and bankroll which can be mutated.
 *
 ****************************************/ 

 import java.util.ArrayList;
 public class Player {
     
     //Instanca ArrayList
     private ArrayList<Card> hand = new ArrayList<>();// the player's cards
 
     //Instance Variables
     private double bankroll;
     private double bet;
 
     //Constructor
     public Player(){	
         //Sets and initializies bankroll and bet	
         bankroll = 50;
         bet = 0;
     }
 
     //Methods
     //Adds the card c to the player's hand
     public void addCard(Card c){
 
         hand.add(c);
     }
 
     //Removes the card c from the player's hand
     public void removeCard(Card c){
         
         //Searches array to find the index of Card c, by comparing the toString
         //of c to the toString of the index. Removes the card at index i.
         for(int i = 0; i<hand.size(); i++){
             if(c.toString().equals(hand.get(i).toString())){
                 hand.remove(i);
             }
         }
     }
         
     //Records the bet that the player makes. Affects bankroll immediately
     public void bets(double amt){
         bet = amt;
         bankroll -= bet;
     }
 
     //Adjusts the player's bankroll by multiplying odds of the winning hand
     //to the bet made
     public void winnings(double odds){
         bet = bet * odds;
         bankroll += bet;
         bet = 0;
     }
 
     //Returns the current bankroll of the player as a double
     public double getBankroll(){
         return bankroll;
     }
 
     //Returns the hand of the player
     public ArrayList<Card> getHand(){
         return hand;
     }
 }
 