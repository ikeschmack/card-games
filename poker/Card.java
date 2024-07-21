/*****************************************
 * 
 *Ike Schmidt
 *Card.java - Creates cards which have a suit and rank. These cards are
 * are interacted with the Deck, Player, and Game classes
 *
 ****************************************/ 

public class Card implements Comparable<Card>{
	
	//Instance variables
	private int suit; // use integers 1-4 to encode the suit
	private int rank; // use integers 1-13 to encode the rank


	//Constructor

	public Card(int s, int r){
		//make a card with suit s and rank r
		for(int i = 1; i<=4; i++){
			if(i==s){
				suit = s;
			}
		}
		for(int i = 1; i<=13; i++){
			if(i==r){
				rank = r;
			}
		}
	}
	
	//Methods

	//Overwrites the compareTo() method
	//Compares the cards so that they can be sorted by rank
	//Returns the difference between the cards' ranks
	public int compareTo(Card c){
		return getRank() - c.getRank();
	}

	//Compares suits of the cards to determine if they are equal. Returns equality
	//as boolean.
	public boolean compareSuit(Card c){
		if(getSuit() == c.getSuit()){
			return true;
		}
		else{
			return false;
		}
	}


	//Overwrites the toString() method
	//Converts Cards to Strings and returns a String
	public String toString(){
		String suitName = null;

		//Switch is used to check for the suit of the cards (coded to be between 1-4)
		switch(suit){
			case 1:
				suitName = "c";
				break;
			case 2:
				suitName = "d";
				break;
			case 3:
				suitName = "h";
				break;
			case 4:
				suitName = "s";
				break;
		}
		return suitName + getRank();
	}
	
	//Returns the suit of a Card as an integer
	public int getSuit(){
		return suit;
	}

	//Returns the rank of a card as an integer
	public int getRank(){
		return rank;
	}
}
