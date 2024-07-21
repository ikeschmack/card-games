/*****************************************
 * 
 *Ike Schmidt
 *Game.java - Creates and runs the game of Poker. Allows user to interact with
 *their hand and play.
 *
 ****************************************/ 

 import java.util.ArrayList;
 import java.util.Collections;
 import java.util.Scanner;
 
 public class Game {
     //Instance ArrayLists
     //Temporary hand to create the player's hand from a test hand
     private ArrayList<Card> tempHand = new ArrayList<>();
     //Stores cards to be removed
     private ArrayList<Card> removed = new ArrayList<>();
 
 
     //Instance Variables
     private Player p = null;
     private Deck cards;
 
     private int suit = 0;//Stores card suit
     private int rank = 0;//Stores card rank
     private int odds = 0;//Odds of winning, used in Player's winnings() method
     private boolean loser = true;//Flags if player loses
     private String handValue = null;//Stores the String returned from checkHand() method
 
     //Temporary variables to store cards when checking for pairs: isPair, is2Pair
     private static Card pair_1 = null;
     private static Card pair_2 = null;
 
     //Temporary variables to store cards when checking: is3OfAKind
     private static Card triplet1 = null;
     private static Card triplet2 = null;
     private static Card triplet3 = null;
     
 
     //Constructors
     //Constructor which takes test hand as a parameter
     public Game(String[] testHand){
     //Notes/parameters for the game:
         // This constructor is to help test your code.
         // use the contents of testHand to
         // make a hand for the player
         // use the following encoding for cards
         // c = clubs
         // d = diamonds
         // h = hearts
         // s = spades
         // 1-13 correspond to ace-king
         // example: s1 = ace of spades
         // example: testhand = {s1, s13, s12, s11, s10} = royal flush
 
         //Initializes player
         p = new Player();
 
         //Convers testHand to a ArrayList of type Card
         tempHand = cardTranslator(testHand);
         
         //Copies tempHand to hand in the Player class
         for(int i = 0; i<tempHand.size(); i++){
             Card temp = tempHand.get(i);
             p.addCard(temp);
         }
         
         //Initializes a new deck
         cards = new Deck();
         //Shuffles the deck
         //cards.shuffle();
 
         //Checks for duplicate cards and removes them from the deck
         for(Card c : tempHand){
             cards.dupeChecker(c);
         }
         
     }
     
     //No-argument constructor to play poker
     public Game(){
         //Initializes a new deck
         cards = new Deck();
 
         //Shuffles deck
         cards.shuffle();
 
         //Initializes player
         p = new Player();
 
         //Deals cards from shuffled deck and adds them in the player's hand
         for(int i = 0; i<5; i++){
             p.addCard(cards.deal());
         }
 
     }
     
     //Methods
     //This method is to play the game
     public void play(){
         
         //Method variables
         int cont = 0;//Integer which determines whether to continue playing the game
         int input = 0;//Player's integer input, stores amount of cards to remove
         int bet = 0;//Player's bet
 
 
 
         System.out.println("Welcome to poker!");
 
         System.out.println("Your bankroll is: " + p.getBankroll());
         
         //Scanners to allow for player inputs
         Scanner s = new Scanner(System.in);
         Scanner n1 = new Scanner(System.in);
         Scanner n2 = new Scanner(System.in);
         Scanner b = new Scanner(System.in);
 
         //The game runs within a while loop
         while(true){
             String card = null;
             
             //Prompts and recieves bet, then adds bet to Player class
             System.out.print("How much do you want to bet? ");
             bet = b.nextInt();
             p.bets(bet);
 
             System.out.println("Your hand is: " + p.getHand());
 
             //Prompts player to chose amount of cards to remove and stores the amount
             System.out.print("How many cards would you like to remove? ");
             input = n1.nextInt();
 
             //Remove cards
             //String array to store cards that will be removed
             String [] storeCards = new String[(input)];
             int i = 0;
             //Iterates through card array to recieve the player's inputs
             while(i<storeCards.length){
                 System.out.print("Type the card you want to remove: ");
                 card = s.nextLine();
 
                 storeCards[i] = card;
                 i++;
             }
 
             //Converts storeCards array into an ArrayList of type Card
             //This array list is removed. removed is iteracted through to tell the Player
             //class which cards should be removed from the hand
             removed = cardTranslator(storeCards);
             for(Card c : removed){
                 p.removeCard(c);
             }
 
             //Cards are dealt back in to replace the removed cards
             for(i = 0; i<removed.size(); i++){
                 p.addCard(cards.deal());
             }
 
             System.out.println("Your hand is now: " + p.getHand());
 
             //Checks the hand of the player for what type of hand they have
             handValue = checkHand(p.getHand());
 
             //Two options, win/lose.
             if(loser){
                 System.out.println("You lost this round! You got " + handValue + ". ");
             }
             else{
                 System.out.println("You won this round! You got " + handValue + ". ");				
             }
 
             //Player's bankroll is altered through the winnings method, recieving the 
             //odds of the player's hand
             p.winnings(odds);
             System.out.println("Your bankroll is now : " + p.getBankroll());
             
             //Ends game if player loses all of their money.
             if(p.getBankroll() <= 0){
                 System.out.println("You're out of money, go home.");
                 break;
             }
 
             //Determines whether the player wants to continue playing
             System.out.print("Would you like to keep playing?	"
                 + "(For YES PRESS: 1, For NO PRESS: 0): ");
             cont = n2.nextInt();
             
             //If/else for whether they would like to continue
             if(cont == 0){
                 break;//Breaks loop and ends game
             }
 
             else{
                 //Clears old hand and creates a new one. Shuffles deck
                 i = p.getHand().size()-1;
                 while(i>=0){
                     p.removeCard(p.getHand().get(0));
                     i--;
                 }
                 cards.shuffle();
                 i=0;
                 while(i<5){
                     p.addCard(cards.deal());
                     i++;
                 }
 
                 continue;
             }
         }
         
         n1.close();
         n2.close();
         s.close();
         b.close();
         System.out.println("Thank you for playing!" + 
             " Remember, the House always wins.");
         //Game ends
     }
     
     //checkHand() uses other methods to determine which type of hand the player 
     //has and returns a String stating the type of hand
         //Takes ArrayList<Card> parameter
     public String checkHand(ArrayList<Card> hand){
         
         if(isRoyalFlush(hand)){
             loser = false;
             odds = 250;
             return "Royal Flush";
         }
         else if(isStraightFlush(hand)){
             loser = false;
             odds = 50;
             return "Straight Flush";
         }
         else if(is4OfAKind(hand)){
             loser = false;
             odds = 25;
             return "Four of a Kind";
         }
         else if(isFullHouse(hand)){
             loser = false;
             odds = 6;
             return "Full House";
         }
         else if(isFlush(hand)){
             loser = false;
             odds = 5;
             return "Flush";
         }
         else if(isStraight(hand)){
             loser = false;
             odds = 4;
             return "Straight";
         }
         else if(is3OfAKind(hand)){
             loser = false;
             odds = 3;
             return "Three of a kind";
         }
         else if(is2Pair(hand)){
             loser = false;
             odds = 2;
             return "Two pairs";
         }
         else if(isPair(hand)){
             loser = false;
             odds = 1;
             return "One pair";
         }
         else{
             loser = true;
             odds = 0;
             return "No pair";
             
         }		
     }
 
     //Checks for a pair within the hand. Returns boolean
     public boolean isPair(ArrayList<Card> hand){
         //Iterate through the hand and compare each card to to the next,
         boolean flag = false;
 
         //First card compared
         for(int i = 0; i<hand.size(); i++){
             int k = 0;
 
             //Second card compares
             while(k<hand.size() && !flag){
                 if(hand.get(i) == hand.get(k)){
                     k++;
                 }
 
                 else if((hand.get(i)).compareTo(hand.get(k)) == 0){
                     flag = true;
                     //Stores cards to remove in is2Pair()
                     pair_1 = hand.get(i);
                     pair_2 = hand.get(k);
                     break;
                 }
 
                 else{
                     k++;
                 }
             }
         }
         return flag;
     }
 
     //Checks for 3 cards of the same value. Returns boolean
     public boolean is3OfAKind(ArrayList<Card> hand){
 
         boolean flag = false;
         //Same concept as isPair, but iterates through 3 cards at the same time
         //and compares their values
         for(int i = 0; i<hand.size(); i++){
             Card tempi = hand.get(i);
 
             for(int k = i + 1; k<hand.size(); k++){
                 Card tempk = hand.get(k);
 
                 for(int m = k + 1; m<hand.size(); m++){
                     Card tempm = hand.get(m);
 
                     if((tempi.compareTo(tempk) == 0) && (tempk.compareTo(tempm) == 0)){
                         flag = true;
                         //Stores values to remove in isFullHouse()
                         triplet1 = tempi;
                         triplet2 = tempk;
                         triplet3 = tempm;
                         break;
                     }
                 }
             }
         }
         return flag;
     }
 
     //Checks for 2 pairs in the hand. Returns boolean.
     public boolean is2Pair(ArrayList<Card> hand){
         
         //Stores boolean from checking for one pair.
         boolean pair1 = isPair(hand);
 
 
         //If statement shortcuts the method to save time
         //If there is no pair at all, then there can't be 2 pairs
         if(pair1 == false){
             return false;
         }
 
         //Removes cards from the first pair so they do not interfere with a 
         //check for a second pair
         hand.remove(pair_1);
         hand.remove(pair_2);
 
         //Stores pair when checking the rest of the hand
         boolean pair2 = isPair(hand);
         
         //Adds first pair back to hand
         hand.add(pair_1);
         hand.add(pair_2);
 
         if(pair1 == true && pair2 ==true){
             return true;
         }
         else{
             return false;
         }
     }
 
     //Checks for 4 cards of the same rank. Returns boolean.
     public boolean is4OfAKind(ArrayList<Card> hand){
 
         //Check is 3 of a kind exist, if there is no 3 of a kind, then there 
         //can't be 4 of a kind
         if(!is3OfAKind(hand)){
             return false;
         }
 
         hand.remove(triplet1);
         hand.remove(triplet2);
         hand.remove(triplet3);
         //triplet3 is left in there so that I can compare it to the other
         //values in the hand
 
         //Checks for a full house, by removing the 3 of a kind
         if(isPair(hand)){
             hand.add(triplet1);
             hand.add(triplet2);
             hand.add(triplet3);
             
             return false;
         }
         
         //Adds triplet3 back so that it can be compared to the rest of the hand
         hand.add(triplet3);
 
         //Checks if it is 4 of a kind
         if(isPair(hand)){
             hand.add(triplet1);
             hand.add(triplet2);
 
             return true;
         }
 
         else{
             hand.add(triplet1);
             hand.add(triplet2);
             return false;
         }
     }
 
     //Checks for a straight, returns boolean.
     public boolean isStraight(ArrayList<Card> hand){
         //For straight, ace can be both, check first as 1, then as 14
         boolean aceFlag = false;
         Card ace = null;
 
         //Sorts so that array is in increasing order
         Collections.sort(hand);
 
         //Calls straight helper to tell whether ace works as 1,
         //will also work if there are no aces at all
         if(straightHelper(hand)){
             return true;
         }
 
         //If there is no straight, check for aces
         for(Card c : hand){
             if(c.getRank() == 1){
 
                 ace = c;
                 aceFlag = true;
             }
         }
 
         //If there is no ace or there is no king, then there can't be straight
         //with ace high, king would be the last index because it 
         //is the highest value when ace == 1
         if(!aceFlag || hand.get(4).getRank() != 13){
             return false;
         }
 
         //If method continues, then there is an ace == 14
         hand.remove(ace);
         Collections.sort(hand);//Re-sorts array without hand
 
 
         if(straightHelper(hand)){
             hand.add(ace);
             return true;
         }	
 
         else{
             hand.add(ace);
             return false;
         }
     }
 
     //Helper method for finding straights, returns boolean. Assumes hand is 
     //sorted from least to greatest
     public boolean straightHelper(ArrayList<Card> hand){
         //Store indexes of cards following one another
         int i = 0;
         int k = 1;
         int m = 2;
 
         //Iterates through hand
         while(m<hand.size()){
             //If the cards are not following in increasing order, then there 
             //is no straight
             if(hand.get(k).compareTo(hand.get(i)) != 1 || 
                 hand.get(m).compareTo(hand.get(k)) != 1){
                 
                 return false;
             }
 
             else{
                 i++;
                 k++;
                 m++;
             }
         }
 
         return true;
     }
 
     //Checks for a Full House, returns boolean.
     public boolean isFullHouse(ArrayList<Card> hand){
         //If there is no 3 of a kind, there can't be a full hosue
         if(!is3OfAKind(hand)){
             return false;
         }
 
         //Removes triplets from 3 of a kind
         hand.remove(triplet1);
         hand.remove(triplet2);
         hand.remove(triplet3);
 
         //Cheaper big-O than iterating through isPair()
         if((hand.get(1)).compareTo(hand.get(0)) == 0){	
             hand.add(triplet1);
             hand.add(triplet2);
             hand.add(triplet3);
             return true;
         }
         else{
             hand.add(triplet1);
             hand.add(triplet2);
             hand.add(triplet3);
             return false;
         }
     }
 
     //Checks for Flush, returns boolean.
     public boolean isFlush(ArrayList<Card> hand){
         //Uses compareSuit method, iterates through hand array
         for(int i = 1; i<hand.size(); i++){
             //If any cards do not match, it cannot be a Flush
             if(!hand.get(0).compareSuit(hand.get(i))){
                 return false;
             }		
         }
         return true;
     }
 
     //CHecks for Straigh Flush, returns boolean.
     public boolean isStraightFlush(ArrayList<Card> hand){
         //Checks for flush, because Flush is quicker to run first
         if(isFlush(hand)){
             //Checks for Straight
             if(isStraight(hand)){
                 //If and only if both are true, then there is a Straight Flush
                 return true;
             }
             else{
                 return false;
             }
         }
         else{
             return false;
         }
     }
 
     //Checks for Royal Flush, returns boolean.
     public boolean isRoyalFlush(ArrayList<Card> hand){
         //Same concept as Straight flush
         if(isFlush(hand)){
             if(isStraight(hand)){
                 //Sorts hand
                 Collections.sort(hand);
                 //If hand is a Royal Flush, then index 1 will be a 10
                     //[s1, s10, s11, s12, s13]
                 if(hand.get(1).getRank() == 10){
                 return true;
                 }
             }
         }
         return false;
     }
 
     //Translates String [] to ArrayList<Card>.
     public ArrayList<Card> cardTranslator(String[] tempHand){
         //Temporary ArrayList to store translated cards and eventually return.
         ArrayList<Card> futureHand = new ArrayList<>();
 
         for(String s : tempHand){
             Character [] cardChar = new Character [s.length()];
             
             //Isolate each character
             cardChar[0] = s.charAt(0);
             cardChar[1] = s.charAt(1);
             if(s.length() == 3){
                 cardChar[2] = s.charAt(2);
             }
 
             //Identify the suit of the card
             switch(Character.toString(cardChar[0])){
                 
                 case "c":
                     suit = 1;
                     break;
 
                 case "d":
                     suit = 2;
                     break;
 
                 case "h":
                     suit = 3;
                     break;
 
                 case "s":
                     suit = 4;
                     break;
             }
 
             //Identify the rank of the card
             //If the card has 3 characters, it is double digets
             if(s.length() == 3){
                 String temp = Character.toString(s.charAt(1))+
                     Character.toString(s.charAt(2));
                 rank = Integer.parseInt(temp);
 
                 //Creates card and adds to hand
                 Card c = new Card(suit, rank);
                 futureHand.add(c);
             }
 
             //If the card has 2 character, it has single digets
             else{
                 String temp = Character.toString(s.charAt(1));
                 rank = Integer.parseInt(temp);
                 
                 //Creates card and adds to hand
                 Card c = new Card(suit, rank);
                 futureHand.add(c);
             }
         }
         return futureHand;
     }
 }