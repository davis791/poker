import java.util.ArrayList;
import java.util.Scanner;

public class Hand {
	static ArrayList<Player> players = new ArrayList<Player>();
	static ArrayList<Card> pairOuts = new ArrayList<Card>();
	static ArrayList<Card> tripOuts = new ArrayList<Card>();
	static ArrayList<Card> straightOuts = new ArrayList<Card>();
	static ArrayList<Card> flushOuts = new ArrayList<Card>();
	static ArrayList<Card> fullOuts = new ArrayList<Card>();
	static ArrayList<Card> quadOuts = new ArrayList<Card>();
	static ArrayList<Card> straightFlushOuts = new ArrayList<Card>();
	static ArrayList<Card> royalFlushOuts = new ArrayList<Card>();
	static ArrayList<String> stringOuts = new ArrayList<String>();
	static Card[] cards = new Card[52];
	static Card[] board = new Card[5];
	static Card[] hand = new Card[2];
	static final Value[] VALUES = Value.values();
	static final Suit[] SUITS = Suit.values();
	static Scanner scan = new Scanner(System.in);
	public static void main(String[] args) {
		//initPlayers();
		initCards();

		//int BigBlind = 0;
		//System.out.print("\nBig Blind? : ");
		//BigBlind = scan.nextInt();

		preFlop();
		flop();
		turn();
		river();

		scan.close();
	}
	public static void nullCards(Card card1) {
		for (int i = 0; i < 52; i++) {
			if (cardsEqual(cards[i], card1)) {
				cards[i] = null;
			}
		}
	}
	public static void preFlop() {
		System.out.println("Enter First Card : ");
		String value1 = scan.next();
		String suit1 = scan.next();
		Card myCard1 = new Card(convertToValue(value1), convertToSuit(suit1));

		System.out.println("Enter Second Card : ");
		String value2 = scan.next();
		String suit2 = scan.next();
		Card myCard2 = new Card(convertToValue(value2), convertToSuit(suit2));

		nullCards(myCard1);
		nullCards(myCard2);

		hand[0] = myCard1;
		hand[1] = myCard2;

		check();
	}
	public static void flop() {
		System.out.println("Enter First Flop Card : ");
		String value1 = scan.next();
		String suit1 = scan.next();
		Card flopCard1 = new Card(convertToValue(value1), convertToSuit(suit1));
		nullCards(flopCard1);
		board[0] = flopCard1;

		System.out.println("Enter Second Flop Card : ");
		String value2 = scan.next();
		String suit2 = scan.next();
		Card flopCard2 = new Card(convertToValue(value2), convertToSuit(suit2));
		nullCards(flopCard2);
		board[1] = flopCard2;

		System.out.println("Enter Third Flop Card : ");
		String value3 = scan.next();
		String suit3 = scan.next();
		Card flopCard3 = new Card(convertToValue(value3), convertToSuit(suit3));
		nullCards(flopCard3);
		board[2] = flopCard3;

		check();
	}
	public static void turn() {
		System.out.println("Enter Turn Card : ");
		String value1 = scan.next();
		String suit1 = scan.next();
		Card turnCard = new Card(convertToValue(value1), convertToSuit(suit1));
		nullCards(turnCard);
		board[3] = turnCard;

		check();
	}
	public static void river() {
		System.out.println("Enter River Card : ");
		String value1 = scan.next();
		String suit1 = scan.next();
		Card riverCard = new Card(convertToValue(value1), convertToSuit(suit1));
		nullCards(riverCard);
		board[4] = riverCard;

		finalHand();
	}
	public static void check() {
		Card card1 = hand[0];
		Card card2 = hand[1];
		String response = "";
		System.out.println("What do you need to get? (Pair,Trips,Straight,Flush,Full,Quads,SF,RF)");
		scan.nextLine();
		response = scan.nextLine();
		boolean orBetter = false;
		if (!response.equals("")) {
			System.out.println("Or Better?");
			orBetter = scan.next().equals("y") ? true : false;
		}
		boolean reached = false;
		if (response.contains("Pair")) {
			checkPair(card1, card2);
			reached = true;
		}
		if (response.contains("Trips") || (orBetter && reached)) {
			checkThree(card1, card2);
			reached = true;
		}
		if (response.contains("Straight") || (orBetter && reached)) {
			checkStraight(card1, card2);
			reached = true;
		}
		if (response.contains("Flush") || (orBetter && reached)) {
			checkFlush(card1, card2);
			reached = true;
		}
		if (response.contains("Full") || (orBetter && reached)) {
			checkFull(card1, card2);
			reached = true;
		}
		if (response.contains("Quads") || (orBetter && reached)) {
			checkFour(card1, card2);
			reached = true;
		}
		if (response.contains("SF") || (orBetter && reached)) {
			checkStraightFlush(card1, card2);
			reached = true;
		}
		if (response.contains("RF") || (orBetter && reached)) {
			checkRoyalFlush(card1, card2);
			reached = true;
		}
		printOuts();
		resetOuts();
	}
	public static void checkPair(Card card1, Card card2) {
		stringOuts.add("Pair_Outs_______");
		Card[] testCards = {card1, card2};
		if (cardValueEqual(card1, card2)) {return;} 
		else {
			for (Card card : testCards) {
				boolean match = false;
				for (int i = 0; i < 5; i++) {
					if (cardValueEqual(card, board[i])) {
						match = true;
					}
				}
				if (!match) {
					for (int i = 0; i < 52; i++) {
						if (cardValueEqual(cards[i], card)) {
							addOut(cards[i], pairOuts);
						}
					}
				}
			}
		}
	}
	public static void checkThree(Card card1, Card card2) {
		stringOuts.add("Trip_Outs_______");
		Card[] testCards = {card1, card2};
		boolean match = false;
		for (int i = 0; i < 5; i++) {
			if (cardValueEqual(card1, card2) && cardValueEqual(card1, board[i])) {
				match = true;
			}
		}
		if (!match) {
			for (Card card : testCards) {
				boolean test = false;
				for (int i = 0; i < 5; i++) {
					for (int j = 0; j < 5; j++) {
						if (cardValueEqual(board[i], board[j]) && cardValueEqual(board[i], card) && (j != i)) {
							test = true;
						}
					}
				}
				if (!test) {
					for (int i = 0; i < 52; i++) {
						if (cardValueEqual(cards[i], card) || cardValueEqual(cards[i], card)) {
							addOut(cards[i], tripOuts);
						}
					}
				}
			}
		}
	}
	public static void checkStraight(Card card1, Card card2) {
		stringOuts.add("Straight_Outs___");
		Card[] current = {card1, card2};
		for (Card car : current) {
			int val = car.toInt();
			for (int i = 0; i < 52; i++) {
				if (cardValueEqual(cards[i], car)) {
					continue;
				}
				for (int j = (0 - 4); j < 5; j++) {
					if (cardValueEqual(cards[i], val + j)) {
						addOut(cards[i], straightOuts);
					}
					if (val == 14) {
						if (cardValueEqual(cards[i], 1 + j)) {
							addOut(cards[i], straightOuts);
						}
					}
				}
			}
		}
	}
	public static void checkFlush(Card card1, Card card2) {
		stringOuts.add("Flush_Outs______");
		Card[] current = {card1, card2};
		for (Card car : current) {
			for (int i = 0; i < 52; i++) {
				if (cardSuitEqual(cards[i], car)) {
					addOut(cards[i], flushOuts);
				}
			}
		}
	}
	public static void checkFull(Card card1, Card card2) {
		stringOuts.add("Full_House_Outs_");
		Card[] current = {card1, card2};
		for (Card car : current) {
			for (int i = 0; i < 52; i++) {
				if (cardValueEqual(cards[i], car)) {
					addOut(cards[i], fullOuts);
				}
			}
		}
	}
	public static void checkFour(Card card1, Card card2) {
		stringOuts.add("Quad_Outs_______");
		Card[] current = {card1, card2};
		for (Card car : current) {
			for (int i = 0; i < 52; i++) {
				if (cardValueEqual(cards[i], car)) {
					addOut(cards[i], quadOuts);
				}
			}
		}

	}
	public static void checkStraightFlush(Card card1, Card card2) {
		stringOuts.add("Strat_Flush_Outs");


	}
	public static void checkRoyalFlush(Card card1, Card card2) {
		stringOuts.add("Royal_Flush_Outs");


	}
	public static void resetOuts() {
		pairOuts = new ArrayList<Card>();
		tripOuts = new ArrayList<Card>();
		straightOuts = new ArrayList<Card>();
		flushOuts = new ArrayList<Card>();
		fullOuts = new ArrayList<Card>();
		quadOuts = new ArrayList<Card>();
		straightFlushOuts = new ArrayList<Card>();
		royalFlushOuts = new ArrayList<Card>();
		stringOuts = new ArrayList<String>();
	}
	public static void initPlayers() {
		int playerAmount = 0;
		int position = 0;
		int stack = 0;
		System.out.print("Amount of Players? : ");
		playerAmount = scan.nextInt();

		System.out.print("\nStart Position Left of Dealer? : ");
		position = scan.nextInt();

		System.out.print("\nStarting Stacks? : ");
		stack = scan.nextInt();

		for (int i = 0; i < playerAmount; i++) {
			players.add(new Player(stack, position));
			if (position < playerAmount - 1) {
				position++;
			}
			else {
				position = 0;
			}
		}

	}
	public static void initCards() {
		for (int i = 0; i < 5; i++) {
			board[i] = null;
		}
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 4; j++) {
				cards[(i * 4) + j] = new Card(VALUES[i], SUITS[j]);
			}
		}
	}
	public static void printOuts() {
		System.out.println("Outs : " + (pairOuts.size() + tripOuts.size() + straightOuts.size() + flushOuts.size() 
				+ fullOuts.size() + quadOuts.size() + straightFlushOuts.size() + royalFlushOuts.size()));
		for (int i = 0; i < stringOuts.size(); i++) {
			System.out.println(stringOuts.get(i));
		}
	}
	public static void addOut(Card card1, ArrayList<Card> outs) {
		for (int i = 0; i < outs.size(); i++) {
			if (cardsEqual(outs.get(i), card1)) {return;}
		}
		outs.add(card1);
		stringOuts.add(outsToString(card1));
	}
	public static String outsToString(Card card1) {
		String val = card1.value.toString();
		String sui = card1.suit.toString();
		while (val.length() < 6) {
			val += " ";
		}
		val += "of";
		while (sui.length() < 8) {
			sui = " " + sui;
		}
		return val + sui;
	}
	public static Value convertToValue(String value) {
		for (int i = 0; i < 9; i++) {
			String val = Integer.toString(i + 2);
			if (value.equals(val)) {return VALUES[12 - i];}
		}
		if (value.equals("A") || value.equals("a")) {return Value.ACE;}
		else if (value.equals("K") || value.equals("k")) {return Value.KING;}
		else if (value.equals("Q") || value.equals("q")) {return Value.QUEEN;}
		else if (value.equals("J") || value.equals("j")) {return Value.JACK;}
		else {return null;}
	}
	public static Suit convertToSuit(String suit) {
		if (suit.equals("S") || suit.equals("s")) {return Suit.SPADE;}
		else if (suit.equals("H") || suit.equals("h")) {return Suit.HEART;}
		else if (suit.equals("C") || suit.equals("c")) {return Suit.CLUB;}
		else if (suit.equals("D") || suit.equals("d")) {return Suit.DIAMOND;}
		else {return null;}
	}
	public static boolean cardsEqual(Card test, Card card1) {
		if (cardValueEqual(test, card1) && cardSuitEqual(test, card1)) {
			return true;
		}
		else {
			return false;
		}
	}
	public static boolean cardValueEqual(Card test, Card card1) {
		if (test == null || card1 == null) {
			return false;
		}
		Value testVal = test.value;
		Value val1 = card1.value;
		if (testVal.equals(val1)) {
			return true;
		}
		else {
			return false;
		}
	}
	public static boolean cardValueEqual(Card test, int val) {
		if (test == null) {
			return false;
		}
		int holder = val - 2;
		Value testVal = test.value;
		Value[] hold = Value.values();
		Value val1 = null;
		if (12 - holder >= 0 && 12 - holder < 13) {
			val1 = hold[12 - holder];
		}
		else {
			return false;
		}
		if (testVal.equals(val1)) {
			return true;
		}
		else {
			return false;
		}
	}
	public static boolean cardSuitEqual(Card test, Card card1) {
		if (test == null && card1 != null) {
			return false;
		}
		Suit testSuit = test.suit;
		Suit suit1 = card1.suit;
		if (testSuit.equals(suit1)) {
			return true;
		}
		else {
			return false;
		}
	}
	private static void finalHand() {

	}
}

