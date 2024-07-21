/*****************************************
 * 
 *Ike Schmidt
 *Deck.java - Creates a deck of 52 cards which can be shuffled and dealt
 *
 ****************************************/ 

 import java.util.Random;
 public class Deck {
     
     //Instance array
     //Stores the 52 cards in an array
     private Card[] cards;
     {
     cards = new Card[52];
     }
 
     //Instance variables
     private int top = 0; // the index of the top of the deck
 
     //Constructor
     public Deck(){
         
         // make a 52 card deck here
         Card[] tempCards = new Card[52];
 
 
         //Keeps track of the index within the cards array
         int index = 0;
         
         //Iterates through each suit, then rank
         for(int suit = 1; suit <= 4; suit++){
             for(int rank = 1; rank <=13; rank++){
                 Card c = new Card(suit, rank);
                 tempCards[index] = c;
                 index++;
             }
         }
         cards = tempCards;
     }
     
     //Methods
 
     //Shuffles the deck
     public void shuffle(){
 
         //loops through the cards array by shuffling each cards
         Random r = new Random();
         int randIndex;
 
         for (int index = 0; index < cards.length; index++){
 
             //Choses a random index
             randIndex = r.nextInt(cards.length);
 
             //Swaps the position of the card at index with the card at randIndex
             Card temp = cards[randIndex];
             cards[randIndex] = cards[index];
             cards[index] = temp;
         }
         top = 0; //Sets the top of the deck to be index 0
     }
     
     //Returns the top card in the deck
     public Card deal(){
 
         //Keeps track of the top of the deck and loops back to the top if the deck
         //has been exhausted.
         if(top>51){
             top = 0;
         }
         top++;
 
         return cards[top-1];
     }
     
 
     //Check for duplicate cards from the test hand
     public void dupeChecker(Card c){
         int i = -1;
         int index = -1;
         //Search for index of card to be removed
         for(i = 0; i < cards.length; i++){
             if(cards[i].toString().compareTo(c.toString()) == 0){
                 index = i;
                 break;
             }
         }
 
         //If there is a duplicate card at index, the dupe will be removed
         if(index != -1){
         //Shift the cards one position left
             for(i = index; i < cards.length - 1; i++)
                 cards[i] = cards[i+1];
         }
     }
 
 }