

//this program belongs to the author Dallin Stewart
//this program allows a user to play a game called Coup against a computer, or watch the computer play against itself
//if you are not familiar with the game, instructions are accessible at any time while playing
//I started working on this program as a side project during my AP Computer Science Principles class because
//my friends and I were having a lot of fun playing this game and  I wanted to get some more practice to test out 
//different strategies and starting conditions in the game
//I did not invent this game, but this program is original and of my own design
//I collaborated with Liam Multhaup for a small part of the debugging process


import java.util.Scanner;

import java.util.ArrayList;

import java.util.Random;


public class game {

    

    //ludio[playerNumber][0] => lives

    //ludio[playerNumber][1] => money

    //ludio[playerNumber][2] => card one

    //ludio[playerNumber][3] => card two

    //ludio[playerNumber][4] => temporary card location

    //ludio[playerNumber][5] => temporary card location

    //ludio[playerNumber][6] => takeTurn modifier for preventing players from skipping their turn or taking two actions


    //actual players for simulator

    static int[][] ludio = new int[11][8];        //array that holds all the information about each player

    static int[][] Players = new int[11][7];    //probability that Players has a card

    static double[] probCard = new double[6];    //array that holds the mathematical probability that an opponent has a certain card

    

    static int deck = 15;                        //size of the deck (starting)

    static int discard = 0;                        //size of the discard (staring)

    static boolean user = false;                //whether or not a user is playing against the computer

    static int startingPlayer = 1;                //used to skip certain players each round if necessary

    static int numPlay = 0;                        //holds the number of players in a game

    static int numAlive = numPlay;                //holds the number of players still alive in a game

    static int cards_per_value = 3;                //holds the number of each type of card

    static int turnNumber = 1;                    //round number in a game

    static boolean shouldTake = true;            //boolean that allows players to take a turn and prevents players from taking two turns

    static boolean contGame = true;                //determines if someone has already won

    static int countRepeatTurn = 0;    

    static int hard = 1;                        //difficulty level

    

    //starting number of cards per value for each card type (converts card values to integers):

    static int numKing = 3;                        //1

    static int numQueen = 3;                    //2

    static int numJack = 3;                        //3

    static int numAce = 3;                        //4

    static int numSeven = 3;                    //5

    static int deckSize = numKing + numQueen + numJack + numAce + numSeven; //15

    static ArrayList <Integer> Deck1 = new ArrayList<Integer>();    //holds the cards in the current deck

    static ArrayList <Integer> Discard1 = new ArrayList<Integer>();    //holds the cards in the current discard pile

    

    //SET UP AND UPDATE OF GAME

    public static void main(String[] args) {


        Scanner reader = new Scanner(System.in);


        int money = 3;                                                //amount of money each player starts with

        int lives = 2;                                                //amount of lives each player starts with

        int playerNum = 0;                                            //index for the first player

        

        //***************************************************SET UP AND UPDATE SIMULATION***********************************************************

        int a = 0;

        while (a == 0){                                                //while the input is invalid

            System.out.println("Enter '1' to play against the computer and any other number to watch the AI player themselves: ");

            while (!reader.hasNextInt()){                            //prevent non-integer input

                reader.nextLine();                                     //clear the invalid input before prompting again

                System.out.println("Please enter an integer: ");    //repeat instructions

            }

            a = reader.nextInt();                                    //update answer variable to input

            if (a == 0)                                                //if HELP menu called

                Help();                                                //open the help menu

        }

        if (a == 1){                                                //if the user wants to play against the computer

            user = true;                                            //update user variable to show that a user is playing

            System.out.println("Enter '0' at any time to access the Help Menu.");//print instructions

        }

        while (numPlay == 0){                                        //while there are no players yet

            System.out.println("Enter the total number of players (3-6): ");//print instructions

            while (!reader.hasNextInt()){                            //prevent non-integer input

                reader.nextLine();                                     //clear the invalid input before prompting again

                System.out.println("Please enter an integer. Enter the total number of players (3-6): ");

            }

            numPlay = reader.nextInt();                                //update answer variable to input

            if (numPlay == 0)                                        //if HELP menu called

                Help();                                                //open the help menu

        }

        if (numPlay < 3 || numPlay > 6){                            //if input is an integer but not in the correct range

            System.out.println(numPlay+" is not a valid number of players. Recorded response: 4");//reset numPlay variable

            numPlay = 4;                                            //to 4

        }

        deal(numPlay, lives, money);                                //call the deal method to 

        if (user && ludio[1][0] > 0){                                //if a user is player and they are alive

            int instr = 0;                                            //set temporary variable

            String cont = "";                                        //set temporary variable

            while (hard == 9){                                        //while input is invalid
            	System.out.println("\nEnter the difficulty you would like to play against: "

                        + "\n0 for a joke\n1 for easy\n2 for normal\n3 for difficult\n4 for impossible");
                /*System.out.println("\nEnter the difficulty you would like to play against: "

                        + "\n0 for a joke\n1 for super easy\n2 for very easy\n3 for easy\n4 for normal"

                        + "\n5 for mild disadvantage\n6 for moderate disadvantage\n7 for severe disadvantage\n8 for extreme disadvantage");*/

                while (!reader.hasNextInt()){                        //prevent invalid input

                    reader.nextLine();                                 //clear the invalid input before prompting again

                    System.out.println("Please enter an integer. Enter the total number of players (3-6): ");

                }

                hard = reader.nextInt();                            //update difficulty to input

                if (hard == 9)                                        //if the user calls the help menu

                    Help();                                            //open help menu

            }

            difficulty(hard);                                        //call difficulty method

            while (instr == 0){                                        //while temp variable is invalid

                System.out.println("Enter '1' if you would like to view the instructions or any other number to skip: ");

                while (!reader.hasNextInt()){                        //prevent invalid input

                    reader.nextLine();                                 //clear the invalid input before prompting again

                    System.out.println("Please enter an integer: ");

                }

                instr = reader.nextInt();                            //update instruction variable to input

                if (instr == 0)                                        //if the user calls the help menu

                    Help();                                            //open help menu

            }    

            if (instr == 1){                                        //proceed to instruction display if the user opens the instructions

                System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

                System.out.println("This game is called Coup. It is a bluffing game.");

                System.out.println("\tEnter any character to continue: ");

                cont = reader.next();

                if (cont != "a"){

                    System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

                    System.out.println("The goal of the game is to eliminate all other players by reducing their lives to zero.");

                    System.out.println("\nYou have two cards to start with. The number of cards you have is equal to the number of lives you have.");

                    System.out.println("You also start with three coins.");

                    System.out.println("\nThe game is played in rounds, with each player taking one action per round.");

                    System.out.println("\tEnter any character to continue: ");

                    cont = reader.next();

                    if (cont != "a"){

                        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

                        System.out.println("There are seven possible actions: ");

                        System.out.println("\tTax, Steal, Assassinate, Ambassitate, Foreign Aid, Income, and Coup.");

                        System.out.println("The deck is made up of 15 cards with five different types of cards: ");

                        System.out.println("\tKings, Contessas, Dukes, Assassins, and Ambassadors.");

                        System.out.println("Enter any character to continue: ");

                        cont = reader.next();

                        if (cont != "a"){

                            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

                            System.out.println("KINGS (KING) allow you to Tax, increasing your coins by three. They also block calls for foreign aid.");

                            System.out.println("CONTESSAS (QUEEN) allow you to block assasination attempts.");

                            System.out.println("Dukes (JACK) allow you to Steal two coins from another player, and can block steal attempts.");

                            System.out.println("Assassins (ACE) allow you to Assassinate, reducing another player's lives by one, but costs three coins.");

                            System.out.println("Ambassadors (SEVEN) allow you to draw two cards from the deck and put two cards back into the deck.");

                            System.out.println("\nAny player can call the player's bluff on the action they take.");

                            System.out.println("\tEnter any character to continue: ");

                            cont = reader.next();

                            if (cont != "a"){

                                System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

                                System.out.println("If the player taking the action does not have the card they pretended to, they lose a life and cannot take any action that round.");

                                System.out.println("If the player taking the action does have the card they said, the player that called their bluff loses a card.");

                                System.out.println("\tEnter any character to continue: ");

                                cont = reader.next();

                                if (cont != "a"){

                                    System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

                                    System.out.println("Other players cannot call opponents' bluff on the last three possible actions:");

                                    System.out.println("Calling for Foreign Aid increases your coins by two but can be blocked by a King.");

                                    System.out.println("Taking income increase your coins by one and cannot be blocked.");

                                    System.out.println("Couping costs seven coins but reduces the chosen player's lives by one automatically.\nGood luck!\n");

                                    System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

                                }

                            }

                        }

                    }

                }

            }    

        }

        System.out.println("Round: 1");

        do{

            if (turnNumber == 0)                                    //if this is the first round

                System.out.println("\nRound: 1\n");                    //print 'Round 1' because the loop I used for round number doesn't work on the first round

            for (int j = 0; j < Deck1.size(); j++){                    //for each card in the deck

                if (Deck1.get(j) == 0)                                //check the card value

                    Deck1.remove(j);}                                //and remove all zeros

            for (int j = 0; j < Discard1.size(); j++){                //for each card in the discard

                if (Discard1.get(j) == 0)                            //check the card value

                    Discard1.remove(j);}                            //remove all zeros

            for (playerNum = 1; playerNum <= numPlay; playerNum++){    //for each player

                ludio[playerNum][6] = 0;}                            //reset the state of the take turn value in the player's array

            

            

            if (user && ludio[1][0] > 0){                            //if a user is player

                if (ludio[1][2] != 0 && ludio[1][3] == 0){            //check their hand

                    ludio[1][0] = 1;                                //if they still have one card, set their lives to one

                }                                                    //I added this because sometimes the player would have a card,

                int action = 0;                                        //but have zero lives

                startingPlayer = 2;                                    //skip the user when going through the take turn loop

                if (ludio[1][0] == 2 && user)

                    System.out.println("Your cards are "+displayHand(1,2)+" and "+displayHand(1,3)+".");

                else if (ludio[1][2] != 4 && user)

                    System.out.println("You have a "+displayHand(1,2)+".");

                else if (user)

                    System.out.println("You have an "+displayHand(1,2)+".");

                while (action == 0){

                    if (ludio[1][0] > 0){

                        System.out.println("Enter '1' to tax, '3' to steal, '4' to assassinate, '5' to ambassitate, '6' to Coup someone, "

                                + "'7' to call for foreign aid, or '8' to income.");//display options

                        while (!reader.hasNextInt()){                //prevent non-integer input

                            reader.nextLine();                         //clear the invalid input before prompting again

                            System.out.println("Please enter an valid integer (1, 3, 4, 5, 6, 7, or 8):");//reprint instructions

                        }

                        action = reader.nextInt();

                        if (action == 0)                            //if the user opens the Help Menu

                            Help();                                    //open the help menu

                    }

                }

                if (action < 1 || action > 8 || action == 2){        //if the action is invalid

                    System.out.println("Your input, "+action+", is not a valid action. Recorded response: Income.");//be a savage

                    action = 8;                                        //set their action to income

                }

                takeTurn(1, action, true);                            //call taketurn with income

            }

            else

                System.out.println("Player 1 is out.");                //display failure

            

            for (playerNum = startingPlayer; playerNum <= numPlay; playerNum++){//random player take turn loop

                takeTurn(playerNum, 0, false);                        //call takeTurn

                if (ludio[playerNum][6] != 1){                        //if they didn't make an action

                    takeTurn(playerNum, 0, false);                    //call takeTurn again

                }

                if (ludio[playerNum][2] == 0 && ludio[playerNum][3] != 0){//move the second card to the first card

                    ludio[playerNum][2] = ludio[playerNum][3];        //position if the first card position

                    ludio[playerNum][3] = 0;                        //is empty, then delete the second card

                }

            }

            turnNumber++;                                            //increment round number

            numAlive = 0;                                            //set temporary variable to zero

            for (playerNum = 1; playerNum <= numPlay; playerNum++){    //check and update the number of players for win condition

                if (ludio[playerNum][0] > 0){

                    numAlive++;

                }

                if (ludio[playerNum][0] == 0){                        //delete money from dead players

                    ludio[playerNum][1] = 0;

                }

            }

            if (numAlive == 1){                                        //end game if there is only one player left

                contGame = false;

            }

            System.out.println("");                                    //skip a line

            

            String cardOne = "0";

            String cardTwo = "0";

            for (int i = 1; i <= numPlay; i++){                        //convert numerical value of card to readable output

                cardOne = displayHand(i, 2);

                cardTwo = displayHand(i, 3);

                //print player information

                System.out.println("\tPlayer " + (i) + ": Lives: " + ludio[i][0] + ". Money " + ludio[i][1] + /*".\tCard One:" + cardOne + "\tCard Two:" + cardTwo + */".");

            }

            System.out.println("\nRound: "+turnNumber+"\n");        //print round number

            /*System.out.print("Deck:\t");

            for (int m = 0; m < Deck1.size(); m++){

                System.out.print(Deck1.get(m)+" ");

            }

            System.out.print("\nDiscard:");

            for (int p = 0; p < Discard1.size(); p++){

                System.out.print(Discard1.get(p)+" ");

            }

            System.out.print("\n");*/

        }

        while (contGame && turnNumber < 50);                        //keep moving through loop while no one has won and there have been less than 25 rounds

        for (playerNum = 1; playerNum <= numPlay; playerNum++){        //output winning player

            if (ludio[playerNum][0] > 0){

                System.out.println("\n\n\n\n\nPlayer "+playerNum+" won!");

                if (user && playerNum != 1){

                    System.out.println("You lost!");

                }

            }

        }

    }


    //-----------------------------------------------Probability methods---------------------------------------------------------

    static double calcProb(int playerNumber, int player2, int card){
    	int card1 = ludio[playerNumber][2];
    	int card2 = ludio[playerNumber][3];
        int numHandCards = 0;

        for (int i = 1; i <= numPlay; i++){                            //for each player

            numHandCards += ludio[i][0];                            //increase the numHandcards variable for each card in a player's hand

        }

        int deck4Calc = Deck1.size() + numHandCards - ludio[playerNumber][0];                //set the deck size for probability to the size of the deck plus numHandCards

        int kingDiscard = 0;                                        //initialize variables

        int queenDiscard = 0;

        int jackDiscard = 0;

        int aceDiscard = 0;

        int sevenDiscard = 0;

        for (int i = 0; i < Discard1.size(); i++){                            //for each card in the discard pile
        	if (Discard1.size() > 0) {
	            if (Discard1.get(i) == 1)                                //check the card's value and increase the number of cards that the players knows exactly where they are
	
	                kingDiscard++;
	
	            if (Discard1.get(i) == 2)
	
	                queenDiscard++;
	
	            if (Discard1.get(i) == 3)
	
	                jackDiscard++;
	
	            if (Discard1.get(i) == 4)
	
	                aceDiscard++;
	
	            if (Discard1.get(i) == 5)
	
	                sevenDiscard++;   
        	}

        }

        if (card1 == 1)                                                //check the player's hand and increase the number of cards that the players knows exactly where they are

            kingDiscard++;                    

        if (card2 == 1)

            kingDiscard++;

        if (card1 == 2)

            queenDiscard++;

        if (card2 == 2)

            queenDiscard++;

        if (card1 == 3)

            jackDiscard++;

        if (card2 == 3)

            jackDiscard++;

        if (card1 == 4)

            aceDiscard++;

        if (card2 == 4)

            aceDiscard++;

        if (card1 == 5)

            sevenDiscard++;

        if (card2 == 5)

            sevenDiscard++;

        probCard[1] = probFunction(kingDiscard, deck4Calc, playerNumber, player2);            //set the player's perceived probability that another player has a King to the value that 

        probCard[2] = probFunction(queenDiscard, deck4Calc, playerNumber, player2);        //the method probFunction returns

        probCard[3] = probFunction(jackDiscard, deck4Calc, playerNumber, player2);            //do that for each card value

        probCard[4] = probFunction(aceDiscard, deck4Calc, playerNumber, player2);

        probCard[5] = probFunction(sevenDiscard, deck4Calc, playerNumber, player2);

        //System.out.println("\tDeck size for calculation: "+deck4Calc);
        
        return probCard[card];

    }

    static double probFunction(int numKnown, int deck, int player, int player2) {
		double D = deckSize - Discard1.size() - ludio[player][0];
		double T = 3 - numKnown;
		double H = ludio[player2][0];
		
		double a = f(D-T);
		double b = f(D-H);
		double c = f(D);
		double d = f(D-T-H);
		
		//System.out.println("D: "+D+". T: "+T+", H: 1, "+a+", "+b+", "+c+", "+d);
		//System.out.println((1 - ((a * b) / (c * d))) * 100);
		//System.out.println("Deck size: "+D+". Target cards: "+T);
		return (1 - (a * b / c / d)) * 100;
        //return (1 - ((deck - numKnown) * (deck - numKnown - 1)) / ((deck) * (deck - 1))) * 100;//do the math
    }

    static double f(double num) {
    	double fact = 1;
    	//System.out.println("\n"+num);
    	for (int i = 1; i <= num; i++) {
    		fact *= i;
    		//System.out.print(fact+", ");
    	}
    	return fact;
    }
    
    static void printProbCardFor(int playerNumber){

        calcProb(playerNumber, 1, 1);    //update probability variables

        System.out.println("\tPlayer "+playerNumber+"'s perceived probabilities that another player has the card they say:");//print probabilities

        System.out.print("\tKing: ");                                                                                        //(for checking the method)

        System.out.printf("%.2f", probCard[1]);

        System.out.print("% Queen: ");

        System.out.printf("%.2f", probCard[2]);

        System.out.print("% Jack: ");

        System.out.printf("%.2f", probCard[3]);

        System.out.print("% Ace: ");

        System.out.printf("%.2f", probCard[4]);

        System.out.print("% Seven: ");

        System.out.printf("%.2f", probCard[5]);

        System.out.print("%\n");

        //update action modifiers

    }

    //-----------------------------------------------End Probability methods-----------------------------------------------------


    //************************************************START GAME METHODS*********************************************************


    //start CARD PLAY abilities~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    //play duke

    public static void King(int player){

        Scanner reader = new Scanner(System.in);

        boolean called = false;
        
        boolean canCall = true;

        int ans = 0;

        System.out.println("Player "+player+" taxed.");                //display action

        for (int playerNum = 1; playerNum <= numPlay; playerNum++){    //each player has a chance to call bluff on the King

            if (!called && user && player != 1 && playerNum == 1 && ludio[1][0] > 0 && canCall){    /*if no one has called bluff onf the King, the user is not the one playing the king, 

                                                                    and the playerNumber is the user*/

                while (ans == 0){

                    System.out.println("Enter '1' if you would like to call bluff on Player "+player+"'s King or any other number to skip.");//display options

                    while (!reader.hasNextInt()){                    //prevent non-integer input

                        reader.nextLine();                             //clear the invalid input before prompting again

                        System.out.println("Please enter an integer: ");//repeat instructions

                    }

                    ans = reader.nextInt();                            //set a temporary variable

                    if (ans == 0)                                    //if the user opens the help menu

                        Help();                                        //open the help menu

                }

                if (ans == 1)                                        //if the user calls bluff

                    called = callBluff(1, player, 0, 1, true);        //call the callBluff method from the user, on the player taking the action about their King
                	canCall = false;
            }

            else if (!called && playerNum != player && playerNum != 1 && ludio[playerNum][0] > 0 && canCall){

                                                                    /*otherwise if the player taking the action has not been called, 

                                                                    the playerNumber is not the player that took the action, 

                                                                    if playerHumber is still alive, and the playerNumber is not the user*/

                called = callBluff(playerNum, player, 10, 1, false);//call the callBluff method from the playerNumber on player with a 10% chance
                canCall = false;
            }

        }

        if (!called){                                                //if the player had the card or was not called

            ludio[player][1] += 3;                                    //they get their coins

        }

    }

    //Play defense (not necessarily a Queen)

    public static boolean Queen(int player, int player2, int card, boolean isUser){//1,player1,2,true

        Scanner reader = new Scanner(System.in);

        boolean claimC = false;

        int ans = 0;

        int anw = 0;
        
        int chance = randum(100, 1, -1);

        if (isUser && ludio[1][0] > 0){                                //if the user is blocking

            if (card == 2){                                            //if the card is a queen

                while (ans == 0){

                    System.out.println("Enter '1' if you would like to block with Queen or '2' if you would like to call their bluff: ");//display options

                    while (!reader.hasNextInt()){                    //prevent non-integer input

                        reader.nextLine();                             //clear the invalid input before prompting again

                        System.out.println("Please enter an integer: ");//repeat instructions

                    }

                    ans = reader.nextInt();                            //set a temporary variable

                    if (ans == 0){                                    //if they open the help menu

                        Help();                                        //open the help menu

                    }

                }

                if (ans == 1){                                        //if they block with queen

                    claimC = true;                                    //set blocked variable to true

                    System.out.println("Player 1 blocked with Queen.");//display action

                }

                else if (ans == 2)

                    claimC = callBluff(1, player2, 0, 3, true);

                else                                                //if they did not block

                    claimC = false;                                    //set blocked variable to false

                }

            else if (card == 3){                                    //if the card is a Jack

                while (anw == 0){

                    System.out.println("Enter '1' if you would like to block with Jack or '2' if you would like to block with Ambassador: ");//display options

                    while (!reader.hasNextInt()){                    //prevent non-integer input

                        reader.nextLine();                             //clear the invalid input before prompting again

                        System.out.println("Please enter an integer: ");//repeat instructions

                    }

                    anw = reader.nextInt();                            //set a temporary variable

                    if (anw == 0)                                    //if they open the help menu

                        Help();                                        //open the help menu

                }

                if (anw == 1){                                        //if they block with jack

                    claimC = true;                                    //set blocked variable to true

                    System.out.println("Player 1 blocked with Jack.");//display action

                }

                else if (anw == 2){                                    //if they block with ambassador

                    claimC = true;                                    //set blocked variable to true

                    System.out.println("Player 1 blocked with Ambassador.");//display action

                }

                else                                                //if they don't block

                    claimC = false;                                    //set blocked variable to false

            }

        }

        else if (card == 2){                                        //if the player is not blocking an ace but the card is a queen

            if (ludio[player][2] == card || ludio[player][3] == card || chance >= 80){//if one of the player's cards is a queen,

                claimC = true;                                        //claim you have the card

            }

            if (claimC){                                            //if they claim they have a queen

                System.out.println("Player "+player+" claimed Queen.");//display action

            }

        }

        else if (card == 3){                                        //if the player is not blocking but the card is a Jack

            if (ludio[player][2] == 3 || ludio[player][3] == 3 || chance >= 85){    //if one of the player's cards is a jack,

                claimC = true;                                        //claim you have a jack

                if (claimC){                                        //if they claim they have a jack

                    System.out.println("Player "+player+" claimed Jack.");//display action

                }

            }

            else if (ludio[player][2] == 5 || ludio[player][3] == 5 || chance >= 70){//if one of the player's cards is a seven,

                claimC = true;                                        //claim you have the card

                if (claimC){                                        //if they claim they have a seven

                    System.out.println("Player "+player+" claimed Ambassador.");//display action

                }

            }

        }

        /*else if (card == 5){                                        //if the player is not blocking but the card is a seven 

            if (ludio[player][2] == card || ludio[player][3] == card){//if one of the player's cards is a seven,

                claimC = true;                                        //claim you have the card

            }

            if (claimC){                                            //if they claim they have a seven

                System.out.println("Player "+player+" claimed Ambassador.");//display action

            }

        }*/

        return claimC;                                                //return whether they blocked or not

    }

    //play Captain

    public static void Jack(int player1, int player2){

        Scanner reader = new Scanner(System.in);

        boolean defended = false;

        boolean hayUser = false;

        boolean stealerCall = false;

        boolean dBSC = false;                                         //defender bluffed that they had a card and the stealer called their bluff 

                                                                    //(only for user stealer)

        System.out.println("Player "+player1+" tried to steal from Player "+player2+".");//display action

        if(user && player2 == 1 && ludio[1][0] > 0){                //if the player being attacked is the user

            hayUser = true;                                            //there is a user

            int response = 0;

            while (response == 0){

                System.out.println("Enter '1' if you would like to block with Jack, '2' for Seven, '3' to call bluff on their Jack, "

                        + "or '4' to let them steal: ");            //display options

                while (!reader.hasNextInt()){                        //prevent non-integer input

                    reader.nextLine();                                 //clear the invalid input before prompting again

                    System.out.println("Please enter an integer (1, 2, 3, or 4): ");//repeat instructions

                }

                response = reader.nextInt();                        //set input to a temporary variable

                if (response == 0)                                    //if the user wants the help menu

                    Help();                                            //display the help menu

            }

            if(response == 1){                                        //if the user blocks with Jack

                System.out.println("Player 1 blocked the steal with a Jack.");//display action

                stealerCall = callBluff(player1, 1, 30, 3, false);    //stealer can call bluff on jack

                if(!stealerCall)                                    //if the stealer didn't call or was wrong

                    defended = true;                                //the user successfully defended

            }

            else if(response == 2){                                    //if the user blocks with Ambassador

                System.out.println("Player 1 blocked the steal with a Seven.");//display action

                stealerCall = callBluff(player1, 1, 30, 5, false);    //stealer can call bluff on seven

                if(!stealerCall)                                    //if the stealer didn't call or was wrong

                    defended = true;                                //the user successfully defended

            }

            else if(response == 3){                                    //if the user calls bluff on the stealer's Jack

                defended = callBluff(1, player1, 0, 3, true);        //call the callBluff method for the user on the stealer about their Jack

            }

            else if(response == 4){                                    //if the user lets it happen

                System.out.println("Player 1 let Player "+player1+" steal from them.");//display action

                defended = false;                                    //the user did not defend

            }

            if (defended){

                

            }

        }

        else {                                                        //if the player being stolen from is not the user

            int response2 = 0;

            if (Queen(player2, player1, 3, false) || randum(100, 1, -1) < 20){//player2 can claim they have a seven or randomly claim jack

                //defense[0] = 1;

                if (user && player1 == 1 && ludio[1][0] > 0){        //if the user is the stealer

                    while (response2 == 0){

                        System.out.println("Enter '1' to call Player "+player2+"'s bluff.");//display options

                        while (!reader.hasNextInt()){                //prevent non-integer input

                            reader.nextLine();                         //clear the invalid input before prompting again

                            System.out.println("Please enter an integer.");//repeat instructions

                        }

                        response2 = reader.nextInt();                //set input to a temporary variable

                        if (response2 == 0)                            //if the user wants the help menu

                            Help();                                    //display the help menu

                    }

                    if(response2 == 1){                                //if the user calls bluff

                        dBSC = callBluff(1, player2, 0, 3, true);    //user calls player2's bluff with jack

                        if (!dBSC)                                    //if the stealer (the user) didn't call or was wrong

                            defended = true;                        //defended is true

                    }

                }

                else{                                                //if the stealer is not the user

                    stealerCall = callBluff(player1, player2, 30, 3, false);//stealer can call bluff on jack

                    if(!stealerCall)                                //if the stealer didn't call or was wrong

                        defended = true;                            //defended is true

                }

            }

            else if (Queen(player2, player1, 5, false) || randum(100, 1, -1) < 20){    //player2 can claim they have a seven or randomly claim seven

                //defense[1] = 1;

                response2 = 0;

                if (user && player1 == 1 && ludio[1][0] > 0){        //if the user is the stealer

                    while (response2 == 0){

                        System.out.println("Enter '1' to call Player "+player2+"'s bluff about their Seven.");//display options

                        while (!reader.hasNextInt()){                //prevent non-integer input

                            reader.nextLine();                         //clear the invalid input before prompting again

                            System.out.println("Please enter an integer.");//repeat instructions

                        }

                        response2 = reader.nextInt();                //set input to a temporary variable

                        if (response2 == 0)                            //if the user wants the help menu

                            Help();                                    //display the help menu

                    }

                    if(response2 == 1){

                        dBSC = callBluff(1, player2, 0, 5, true);    //user calls player2's bluff with seven

                        if (!dBSC)                                    //if the stealer (the user) didn't call or was wrong

                            defended = true;                        //defended is true

                    }

                }

                else{                                                //if the stealer is not the user

                    stealerCall = callBluff(player1, player2, 30, 5, false);//stealer can call bluff on seven

                    if(!stealerCall)                                //if the stealer didn't call or was wrong

                        defended = true;                            //defended is true

                }                                                            

            }

            else{                                                    //else player2 can call bluff on the stealer's jack

                defended = callBluff(player2, player1, 30, 3, false);//defended is true if they were right

            }

        }

        if (!defended){                                                //if player2 could not block

            boolean stealing = true;

            if (ludio[player2][1] >= 2 && stealing){                //steal two coins if possible

                ludio[player1][1] += 2;

                ludio[player2][1] -= 2;

                stealing = false;

            }

            if (ludio[player2][1] == 1 && stealing){                //steal one if player only has one

                ludio[player1][1] += 1;

                ludio[player2][1] -= 1;

                stealing = false;

            }

            System.out.println("Player "+player1+" stole from Player "+player2+".");//display successful result

        }

        else{                                                        //if player2 could block

            System.out.println("Player "+player1+" did not steal from Player "+player2+".");//display unsuccessful result

        }

    }

    //play Assassin

    public static void Ace(int player1, int player2){

        Scanner reader = new Scanner(System.in);

        boolean called = false;

        boolean claimQ = false;

        boolean queenCall = false;

        int ans = 0;

        System.out.println("Player "+player1+" tried to assasinate Player "+player2+".");

        ludio[player1][1] -= 3;                                        //take three coins from the attacker

        if (player2 == 1 && user && ludio[1][0] > 0){                //if the player being attacked is the user

            claimQ = Queen(1, player1, 2, true);                    //let them choose to call Queen

        }

        else                                                        //if the player being attacked is not the user

            claimQ = Queen(player2, player1, 2, false);                //let the AI choose to call Queen

        if (claimQ){                                                //if the attacked player claims they have a queen

            if (user && player1 == 1 && ludio[1][0] > 0){

                while (ans == 0){

                    System.out.println("Enter '1' if you would like to call bluff on Player "+player2+"'s Queen: ");

                    while (!reader.hasNextInt()){                    //prevent non=integer input

                        reader.nextLine();                             //clear the invalid input before prompting again

                        System.out.println("Please enter an integer: ");//repeat instructions

                    }

                    ans = reader.nextInt();                            //set a temporary variable

                    if (ans == 0)                                    //if the ask for the help menu

                        Help();                                        //display the help menu

                }

                if (ans == 1)

                    queenCall = callBluff(1, player2, 0, 2, true);

            }

            else

                queenCall = callBluff(player1, player2, 30, 2, false);//the attacking player can call bluff on their queen

        }

        else if (!claimQ)                                            //if the attacked player doesn't claim they have a queen, they can call bluff on the assassin

            called = callBluff(player2, player1, 20, 4, false);

        if (!called && !claimQ){                                    //if the attacker did not get called or did get called and had an assassin

            if (user && player2 == 1 && ludio[1][0] > 0){

                userBluffReturn();

            }

            else{    

                if (ludio[player2][0] == 1){                        //if the attacked player has one life

                    death(player2,2);                                //return the first card to the discard pile from the attacked person

                }

                else if (ludio[player2][0] == 2){                    //if the attacked player has two lives

                    death(player2,3);                                //return the second card to the discard pile from the attacked person

                }

            }

            System.out.println("Player "+player1+" successfully assasinated Player "+player2+".");//display result

        }

        else{

            System.out.println("Player "+player2+" did not get assassinated.");//display result

        }

    }

    //play Ambassador

    public static void Seven(int player){

        Scanner reader = new Scanner(System.in);

        boolean called = false;

        int ans = 0;

        System.out.println("Player "+player+" tried to change cards.");//display action

        for (int playerNum = 1; playerNum <= numPlay; playerNum++){    //each player decides if it wants to call the seven

            if (!called && user && player != 1 && playerNum == 1 && user && ludio[1][0] > 0){/*if no one has called bluff, if the player taking the action is not the user, 

                                                                    if the number of the player in the player for loop is 1, and a user is playing, and the user is alive, then*/

                while (ans == 0){

                    System.out.println("Enter '1' if you would like to call bluff on Player "+player+"'s Seven.");//display user options

                    while (!reader.hasNextInt()){                    //prevent non=integer input

                        reader.nextLine();                             //clear the invalid input before prompting again

                        System.out.println("Please enter an integer: ");//repeat instructions

                    }

                    ans = reader.nextInt();                            //set a temporary variable

                    if (ans == 0)                                    //if the ask for the help menu

                        Help();                                        //display the help menu

                }

                if (ans == 1)                                        //if the user decides to call it

                    called = callBluff(1, player, 0, 5, true);        //call the callBluff method

            }

            else if (!called && playerNum != player && playerNum != 1 && ludio[playerNum][0] > 0){

                                                                    /*otherwise, if no one has called bluff, if the player that could call is 

                                                                    not the player taking the action, the player that could call is not the user, 

                                                                    and the player has lives*/

                called = callBluff(playerNum, player, 10, 5, false);//call the callBluff method with a 10% probability

            }

        }

        if (!called){                                                //if the player was not called

            ludio[player][4] = ludio[player][2];                    //move original cards to knew locations

            ludio[player][5] = ludio[player][3];                    //do it twice in case you have two cards

            int newCard1 = Deck1.remove(0);                            //draw a card from the deck and set it to a temporary variable

            int newCard2 = Deck1.remove(0);                            //draw a card from the deck and set it to a temporary variable

            ludio[player][2] = newCard1;                            //put the first card into old card position

            ludio[player][3] = newCard2;                            //put the second card into old card position

            if (user && player == 1 && ludio[1][0] > 0){                                //if a user is playing and the player taking the action is the user

                int handIndex = 0;                                    //hand index starts at an invalid integer to activate the while loop

                int count = 0;

                int count1 = 0;

                while (handIndex < 2 || handIndex > 5 && count1 < 10){                                                //while the input is an incorrect integer

                    System.out.println("You drew a "+displayHand(1,2)+" and a "+displayHand(1,3));    //display what the user drew

                    while (handIndex == 0 && count < 10){

                        if (ludio[1][0] == 2)

                            System.out.println("You must discard two cards now.\nEnter '2' to return your "+displayHand(1,2)+

                                ", '3' for "+displayHand(1,3)+", '4' for "+displayHand(1,4)+", or '5' for "+displayHand(1,5));//display options

                        else

                            System.out.println("You must discard two cards now.\nEnter '2' to return your "+displayHand(1,2)+

                                    ", '3' for "+displayHand(1,3)+", or '4' for "+displayHand(1,4));//display options

                        while (!reader.hasNextInt()){                                                    //prevent non=integer input    

                            reader.nextLine();                                                             //clear the invalid input before prompting again

                            System.out.println("Please enter an integer (2, 3, 4, or 5): ");            //repeat instructions

                        }

                        handIndex = reader.nextInt();                //set a temporary variable

                        if (handIndex == 0)                            //if the ask for the help menu

                            Help();                                    //display the help menu

                        if (count > 1){                                //if the user has entered invalid input more than once

                            System.out.println("You must enter a valid number.");//repeat instructions

                            count++;

                        }

                        if (count == 9){

                            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nEither you or the computer messed up bad. Sorry for any inconvenience, but your discarded card will default to your "+displayHand(1,2)+".");

                            handIndex = 2;

                        }

                    }

                    if (count1 > 0)                                    //if the user has entered invalid input more than once

                        System.out.println("You must enter a valid number.");//repeat instructions

                    count1++;

                    if (count1 == 9){

                        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nEither you or the computer messed up bad. Sorry for any inconvenience, but your discarded card will default to your "+displayHand(1,2)+".");

                        handIndex = 2;                                //add one to the count variable

                    }

                }

                if (handIndex < 2 || handIndex > 5)                    //if the input is invalid

                    handIndex = 2;                                    //set the hand index to the default of '2'

                Deck1.add(handIndex);                                //add the inputed card to the deck

                for (int i = handIndex; i <= 4; i++){                //eliminate spaces in the hand by moving everything forward one, starting at the input hand index

                    ludio[1][i] = ludio[1][i + 1];                    //move the card forward one

                }

                ludio[1][5] = 0;                                    //delete the last card index

                count = 0;                                            //reset the count variable

                handIndex = 0;                                        //reset the hand index so that it is invalid again

                while (handIndex < 2 || handIndex > 4){                //while the hand index is an invalid integer

                    while (handIndex == 0 && count < 10){

                        System.out.println("You must discard one more card.\nEnter '2' to return your "+displayHand(1,2)+

                                ", '3' for "+displayHand(1,3)+", or '4' for "+displayHand(1,4));//display options

                        while (!reader.hasNextInt()){                //prevent non=integer input    

                            reader.nextLine();                         //clear the invalid input before prompting again

                            System.out.println("Please enter an integer (2, 3, 4, or 5): ");//repeat instructions

                        }

                        handIndex = reader.nextInt();                //set a temporary variable

                        if (handIndex == 0)                            //if the ask for the help menu

                            Help();                                    //display the help menu

                    }

                    if (count > 0)                                    //if the user has entered invalid input more than once

                        System.out.println("You must enter a valid number.");//repeat instruction

                    count++;//add one to the count variable

                    if (count == 9){

                        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nEither you or the computer messed up bad. Sorry for any inconvenience, but your discarded card will default to your "+displayHand(1,2)+".");

                        handIndex = 2;

                    }

                }

                Deck1.add(handIndex);                                //add the inputted hand index to the deck

                for (int i = handIndex; i <= 3; i++){                //move every card over starting from the input index

                    ludio[1][i] = ludio[1][i + 1];        

                }

                ludio[1][4] = 0;                                    //delete the card in the last index

                ludio[1][6] = 0;                                    //update takeTurn modifier

            }

            else{                                                    //if the user is not the one taking the action

                Deck1.add(ludio[player][4]);                        //return the first card from the deck and place it in a temporary storage index

                Deck1.add(ludio[player][5]);                        //return the second card from the deck and place it in a temporary storage index

                ludio[player][4] = 0;                                //delete the first old card

                ludio[player][5] = 0;                                //delete the second old card

                System.out.println("Player "+player+" changed cards.");    //display action

            }

        }

        else{                                                            //if the player was successfully called

            System.out.println("Player "+player+" did not change cards.");//display result

        }

    }

    //coup a player

    public static void Coup(int player1, int player2){

        ludio[player1][1] -= 7;                                        //subtract seven from player1

        System.out.println("Player "+player1+" couped Player "+player2+".");//display action

        if (ludio[player2][0] == 1){                                //if the player being attacked has one life

            death(player2,2);                                        //call death for their first card index

        }

        else if (ludio[player2][0] == 2){                            //otherwise if the player being attacked has two live

            if (user && player2 == 1){

                userBluffReturn();

            }

            else

                death(player2,3);                                    //call death for their second card index

        }

    }

    //card for foreign aid

    public static void fAid(int player){

        Scanner reader = new Scanner(System.in);

        boolean calledK = false;

        boolean callR = false;

        int callingP = 0;

        int ans = 0;

        System.out.println("Player "+player+" called for foreign aid.");//print the action

        if (user && player != 1 && ludio[1][0] > 0){                //if there is a user but the player is not the user

            while (ans == 0){

                System.out.println("Enter '1' if you would like to block with a King: ");//let the user choose to block with King

                while (!reader.hasNextInt()){                        //prevent non-integer input

                    reader.nextLine();                                 //clear the invalid input before prompting again

                    System.out.println("Please enter an integer: ");//reprint instructions

                }

                ans = reader.nextInt();                                //set a temporary variable

                if (ans == 0)                                        //if help menu called

                    Help();                                            //open the help method

            }

            if (ans == 1){                                            //if the user chooses to block with king

                System.out.println("You blocked Player "+player+"'s call for Foreign Aid with a King.");//print the action

                calledK = true;                                        //update variables

                callingP = 1;                                        //update variables

            }

        }

        for (int playerNum = 1; playerNum <= numPlay; playerNum++){    //for each player

            if ((!calledK && playerNum != player && ludio[playerNum][0] > 0 && (ludio[playerNum][2] == 1 || ludio[playerNum][3] == 1))){

                                                                    /*if no one has blocked with a King, 

                                                                    and the player number is not the player that called FA, 

                                                                    and at least one of their cards is a King*/

                if (!(user && playerNum == 1)){                        //if the player is not the user or the player that called FA

                    System.out.println("Player "+playerNum+" blocked with King.");//print the block

                    calledK = true;                                    //update variables

                    callingP = playerNum;                            //update variables

                }

            }

        }

        if (calledK && user && ludio[1][0] > 0 && player == 1){        //if someone attempted to block with King

            System.out.println("Enter '1' to call bluff on Player "+callingP+"'s King or any other number to skip: ");

            while (!reader.hasNextInt()){                            //prevent non-integer input

                reader.nextLine();                                     //clear the invalid input before prompting again

                System.out.println("Please enter an integer: ");    //reprint instructions

            }

            ans = reader.nextInt();                                    //set a temporary variable

            if (ans == 0)                                            //if help menu called

                Help();

            if (ans == 1)

                callR = callBluff(1, callingP, 0, 1, true);                    

        }

        else if (calledK)

            callR = callBluff(player, callingP, 30, 1, false);

        if (!calledK || callR){                                        //if no one blocked with a King or the player successfully called bluff on a King                

            ludio[player][1] += 2;                                    //add two to the player's money

        }

    }

    //income

    public static void Income(int player){

        ludio[player][1]++;                                            //add one to the player's money

        System.out.println("Player "+player+" incomed.");            //display action

    }

    //end CARD PLAY abilities~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    

    //start BLUFF section--------------------------------------------------------------------------------------------------------

    public static boolean callBluff(int callingP, int calledP, int callProb, int card, boolean isUser){

        boolean called = false;                                        //var called means no one has called bluff
        boolean haveCalled = false;
        boolean shouldCall = false;
        
        int varCall1 = 0;
        int varCall2 = 0;
        int chance = 0;
        
        if (user && calledP == 1) {
	        chance = randum(100, 1, -1);                            //set a random number for the probability of calling
	
	        varCall1 = hard * 10 + 10 + 2 * turnNumber;
	        //System.out.println(varCall1);
	        varCall2 = 100 - (4 - hard) * 10 - 2 * turnNumber;
	        //System.out.println(varCall2);
	        //System.out.println(chance);
        }
        
        int probBluff = (int) Math.round(calcProb(callingP, calledP, card));

        if (user && calledP == 1 && (ludio[1][2] != card && ludio[1][3] != card) && varCall1 >= chance){

            shouldCall = true;
           // System.out.println("a");
        }

        else if (user && calledP == 1 && (ludio[1][2] == card || ludio[1][3] == card) && varCall2 <= chance){

            shouldCall = true;
           // System.out.println("b");
        }
        //System.out.println(ludio[1][0] > 0 && shouldCall && isUser);
        if (!haveCalled && ludio[1][0] > 0 && shouldCall && user && calledP == 1){                //if there is a user
        	haveCalled = true;
            called = bluffSupport(callingP, calledP, card);//callBluff without a chance variable
            //System.out.println("c");
        }

        else if (!haveCalled && !(user && calledP == 1) && (probBluff <= (34 - hard * 6) || chance > 90)  && ludio[callingP][0] > 0 && callingP != calledP){//if an AI made the call
        	haveCalled = true;
            called = bluffSupport(callingP, calledP, card);//callBluff with a chance variable
            //System.out.println("d");
        }
        
        if (user && callingP == 1 && isUser && !called && !haveCalled) {
        	called = bluffSupport(1, calledP, card);
        	//System.out.println("e");
        	haveCalled = true;
        }

        return called;                                                //return whether someone successfully called bluff 

                                                                    //on another player

    }

    public static boolean bluffSupport(int callingP, int calledP, int card){

        Scanner reader = new Scanner(System.in);

        int cardIndex = 0;

        boolean called = false;
        
        if (card == 1)                                                //if the card is a King

            System.out.println("Player "+callingP+" called bluff on Player "+calledP+"'s King!");//say that they are calling a King

        else                                                        //otherwise

            System.out.println("Player "+callingP+" called Player "+calledP+"'s bluff!");//say that they are calling a bluff

        if (ludio[calledP][2] == card || ludio[calledP][3] == card){//if the CALLED player's  card is the card they claimed,

            System.out.println("Player "+callingP+" was wrong!");    //say the calling player was wrong

            if (ludio[calledP][2] == card){

                cardIndex = 2;}

            else if (ludio[calledP][3] == card){

                cardIndex = 3;}

            if (user && callingP == 1 && ludio[1][0] > 0)            //if a user is the one calling the bluff

                userBluffReturn();                                    //the user must discard a card

            else{                                                    //otherwise

                System.out.println("Player "+callingP+" discarded a "+displayHand(callingP, 2)+".");//display that  the calling player discards their first card

                death(callingP, 2);                                    //make the calling Player discard a card

                if (user && calledP == 1 && ludio[1][0] > 0){                                //if the user is the called player

                    System.out.println("You must return your "+displayHand(1, cardIndex)+".");//display options

                    returnCard(calledP, cardIndex);                    //return the correct card back to the deck

                    ludio[calledP][cardIndex] = Deck1.remove(0);    //draw a new card and place it in the correct index

                    System.out.println("You drew a "+displayHand(1, cardIndex)+".");//display new card

                }

                else{

                    returnCard(calledP, cardIndex);                    //let the called Player return the called card to the deck

                    ludio[calledP][cardIndex] = Deck1.remove(0);    //and draw a new card

                }

            }

        }

        else{                                                        //if the CALLED player does not have the card they claimed,

            System.out.println("Player "+callingP+" was right!");    //say the calling player was right

            called = true;                                            /*var called means that the called player was called by the calling player

                                                                    and the calling player was right*/

            if (user && calledP == 1 && ludio[1][0] > 0){                                    //if a user is the called player 

                userBluffReturn();                                    //call the user return card method

            }

            else if (ludio[calledP][3] != 0){                        //if the user is not the one called and the player has two lives

                System.out.println("Player "+calledP+" discarded a "+displayHand(calledP, 3)+".");//display that

                death(calledP, 3);                                    //the called player must return their second card

            }

            else{                                                    //if the player that is not the user has only one life

                System.out.println("Player "+calledP+" discarded a "+displayHand(calledP, 2)+".");//display that 

                death(calledP, 2);                                    //the called player must return their first card

            }

        }

        return called;

    }

    public static void userBluffReturn(){

        Scanner reader = new Scanner(System.in);

        int ans = 0;

        if(ludio[1][0] == 2){                                        //if the user has two lives

            while (ans == 0){

                System.out.println("Choose a card to return to the deck. Enter '2' for "+displayHand(1,2)+" or '3' for "+displayHand(1,3));//print their options

                while (!reader.hasNextInt()){                        //prevent non-integer input

                    reader.nextLine();                                 //clear the invalid input before prompting again    

                    System.out.println("Please enter an integer (2 or 3): ");//if they do not enter an integer, print this

                }

                ans = reader.nextInt();                                //set a temporary variable

                if (ans == 0)                                        //if they enter the Help Menu key command

                    Help();                                            //open the Help Menu

            }

            if (ans != 2 && ans != 3 && ludio[1][0] == 2){            //if the user has two lives and their integer input is not 2 or 3

                 System.out.println(ans+" is not a valid input. Recorded response: 3");//print the catch

                 ans = 3;                                            //and set the answer to '2'

            }

            else if (ans != 2  && ludio[1][0] == 1){                //if the user has one life and their integer input is not 2

                 System.out.println(ans+" is not a valid input. Recorded response: 3");    //print the catch

                 ans = 3;                                            //and set the answer to '2'

            }

            Deck1.add(ludio[1][ans]);                                //add the desired card to the deck

            ludio[1][ans] = 0;                                        //set the value of the card's hand index to '0'

            if (ans == 2 && ludio[1][0] == 2){                        //if the first card is null and the second is not

                ludio[1][2] = ludio[1][3];                            //move the second card to the first index

                ludio[1][3] = 0;                                    //and set the value of the second card's index to '0'

            }

            ludio[1][0]--;                                            //reduce their lives by one

        }

        else{                                                        //if the user only has one life

            Deck1.add(ludio[1][2]);                                    //add the first card to the deck

            ludio[1][2] = 0;                                        //delete the first card from the hand

            ludio[1][0] = 0;                                        //set lives to '0'

            ludio[1][1] = 0;                                        //set money to '0'

            ludio[1][6] = 1;                                        //set the letPlay value to 1 (don't let play)

        }

    }

    public static void AIBluffReturn(int player, int cardPos){

        System.out.println("Player "+player+" returned their "+displayHand(player, cardPos)+" and drew a new card.");

        Discard1.add(ludio[player][cardPos]);

        ludio[player][2] = Deck1.remove(0);

    }

    //end bluff section----------------------------------------------------------------------------------------------------------

    

    //start HELPER methods:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

    public static void returnCard(int player, int index){
    	System.out.println("Player "+player+" returned their "+displayHand(player, index)+" to the deck.");
        deckSize++;

        /*if (user && player == 1){

            System.out.println("Enter '2' to return your "+displayHand(1,2)+" or '3' to return your "+displayHand(1,3));

        }*/

        if (ludio[player][index] == 1){                                //check if the card is a King

            numKing++;

            Deck1.add(1);                                            //add it to the deck

        }

        if (ludio[player][index] == 2){                                //check if the card is a Queen

            numQueen++;                                                //add it to the deck

            Deck1.add(2);    

        }

        if (ludio[player][index] == 3){                                //check if the card is a jack

            numJack++;                                                //add it to the deck

            Deck1.add(3);    

        }

        if (ludio[player][index] == 4){                                //check if the card is a Ace

            numAce++;                                                //add it to the deck

            Deck1.add(4);    

        }

        if (ludio[player][index] == 5){                                 //check if the card is a Seven

            numSeven++;                                                //add it to the deck

            Deck1.add(5);    

        }

    }

    public static void death(int player, int index){

        discard++;                                                    //add one to the number of cards in the discard pile

        Discard1.add(ludio[player][index]);                            //add the card to the discard array

        ludio[player][index] = 0;                                    //delete the card from the player's hand

        ludio[player][0]--;                                            //reduce the player's lives by one

        if (ludio[player][0] == 0)

            ludio[player][6] = -1;

    }

    public static void Help(){

        Scanner reader = new Scanner(System.in);

        int need = 1;

        int cont = 0;

        System.out.println("\nHELP MENU! enter 0 to continue or any other number to exit: ");

        while (!reader.hasNextInt()){

            reader.nextLine();                                         

            //clear the invalid input before prompting again

            System.out.println("Please enter a one digit integer.");

        }

        cont  = reader.nextInt();

        if (cont == 0){

            System.out.println("\nEnter:\n'1' to view the instructions again,\n'2' to view information about possible actions,\n"

                    + "'3' to view deck and discard information,\n'4' to view your cards,\n'5' to view player information,\n'6' for a suggested action,"

                    + "\nor any other number to exit: ");

            while (!reader.hasNextInt()){

                reader.nextLine();                                         

                //clear the invalid input before prompting again

                System.out.println("Please enter a one digit integer.");

            }

            need = reader.nextInt();

            if (need == 1){                                            //reprint instructions

                System.out.println("This game is called Coup. It is a bluffing game.");

                System.out.println("The goal of the game is to eliminate all other players by reducing their lives to zero.");

                System.out.println("\nYou have two cards to start with. The number of cards you have is equal to the number of lives you have.");

                System.out.println("You also start with three coins.");

                System.out.println("\nThe game is played in rounds, with each player taking one action per round.");

                System.out.println("The deck is made up of 15 cards with five different types of cards: ");

                System.out.println("\nAny player can call the player's bluff on the action they take.");

                System.out.println("If the player taking the action does not have the card they pretended to, the lose a life "

                        + "and cannot take any action that round.");

                System.out.println("If the player taking the action does have the card they said, the player that called their bluff loses a card.");

                Help();

            }

            else if (need == 2){                                    //reprint actions

                System.out.println("There are seven possible actions: ");

                System.out.println("\tTax, Steal, Assassinate, Ambassitate, Foreign Aid, Income, and Coup.");

                System.out.println("Kings (King)\t\tallow you to Tax,\t\tincreasing your coins by three. They also block calls for foreign aid.");

                System.out.println("Contessas (Queen)\tallow you to block\t\tassasination attempts.");

                System.out.println("Dukes (Jack)\t\tallow you to Steal\t\ttwo coins from another player, and can block steal attempts.");

                System.out.println("Assassins (Ace)\t\tallow you to Assassinate,\treducing another player's lives by one, but costs three coins.");

                System.out.println("Ambassadors (Seven)\tallow you to Draw two cards\tfrom the deck and put two cards back into the deck.");

                System.out.println("\nAny player can call the player's bluff on these actions.");

                System.out.println("The last three possible actions cannot be called:");

                System.out.println("Calling for Foreign Aid\tincreases your coins by two but can be blocked by a King.");

                System.out.println("Taking Income\t\tincrease your coins by one and cannot be blocked.");

                System.out.println("Couping\t\t\tcosts seven coins but reduces the chosen player's lives by one automatically.\n");

                Help();

            }

            else if (need == 3){                                    //print game state information

                System.out.println("\nDeck Size: " + deckSize);

                System.out.print("\nDiscard: ");

                for (int i = 0; i < Discard1.size(); i++){

                    System.out.print(displayCard(Discard1.get(i))+", ");

                }

                System.out.print("\nDeck: ");

                for (int i = 0; i < Deck1.size(); i++){

                    System.out.print(displayCard(Deck1.get(i))+", ");

                }

                System.out.print("\nDiscard: ");

                for (int i = 0; i < Discard1.size(); i++){

                    System.out.print(displayCard(Discard1.get(i))+", ");

                }

                Help();

            }

            else if (need == 4){                                    //display player's hand

                if (ludio[1][0] == 2)

                    System.out.println("Your cards are "+displayHand(1,2)+" and "+displayHand(1,3)+".");

                else

                    System.out.println("Your card is "+displayHand(1,2));

                Help();

            }

            else if (need == 5){                                    //display other player's information

                String cardOne = "0";

                String cardTwo = "0";

                for (int i = 1; i <= numPlay; i++){                    //convert numerical value of card to readable output

                    cardOne = displayHand(i, 2);

                    cardTwo = displayHand(i, 3);

                    //print player information

                    System.out.println("\tPlayer " + (i) + ": Lives: " + ludio[i][0] + ". Money " + ludio[i][1] + /*".\tCard One:" + cardOne + "\tCard Two:" + cardTwo + */".");

                }

                System.out.println("\nRound: "+turnNumber+"\n");    //print round number

                Help();

            }

            else if (need == 6){

                System.out.println("Sorry, the suggestion feature is not currently available.");

                Help();

            }

            System.out.println("");

        }

    }

    public static void difficulty(int hard){                        //update difficulty

        /*if (user){                                                    //if the user is playing

            if (hard == 0){                                            //if difficulty is set to 0

                for (int playerNum = 2; playerNum <= numPlay; playerNum++){    

                    ludio[playerNum][0] = 1;                        //all other players have one life

                }

            }

            else if (hard == 1){                                    //if difficulty is set to 1

                for (int playerNum = 2; playerNum <= numPlay; playerNum++){    

                    ludio[playerNum][1] = 0;                        //all other players have 0 money

                }

            }

            else if (hard == 2){                                    //if difficulty is set to 2

                for (int playerNum = 2; playerNum <= numPlay; playerNum++){    

                    ludio[playerNum][1] = 1;                        //all other players have 1 money

                }

            }

            else if (hard == 3){                                    //if difficulty is set to 3

                for (int playerNum = 2; playerNum <= numPlay; playerNum++){    

                    ludio[playerNum][1] = 2;                        //all other players have 2 money

                }

            }

            else if (hard == 5){                                    //if difficulty is set to 5

                for (int playerNum = 2; playerNum <= numPlay; playerNum++){    

                    ludio[playerNum][1] = 4;                        //all other players have 4 money

                }

            }

            else if (hard == 6){                                    //if difficulty is set to 6

                for (int playerNum = 2; playerNum <= numPlay; playerNum++){    

                    ludio[playerNum][1] = 5;                        //all other players have 5 money

                }


            }

            else if (hard == 7){                                    //if difficulty is set to 7

                for (int playerNum = 2; playerNum <= numPlay; playerNum++){    

                    ludio[playerNum][1] = 6;                        //all other players have 6 money

                }

            }

            else if (hard == 8){                                    //if difficulty is set to 8

                for (int playerNum = 2; playerNum <= numPlay; playerNum++){    

                    ludio[playerNum][0] = 3;                        //all other players have 3 lives

                }

            }

            else{

                for (int playerNum = 1; playerNum <= numPlay; playerNum++){    

                    ludio[playerNum][0] = 2;

                    ludio[playerNum][1] = 3;

                }

            }

        }*/

    }

    public static String displayHand(int playerNum, int position){    //converts a player's hand from integers to the names of the cards they represent

        String cardOne = "0";

        //String cardTwo = "0";

        if(ludio[playerNum][position] == 1){                        //1 goes to King

            cardOne = "King";

        }

        else if(ludio[playerNum][position] == 2){                    //2 goes to Queen

            cardOne = "Queen";

        }

        else if(ludio[playerNum][position] == 3){                    //3 goes to Jack

            cardOne = "Jack";

        }

        else if(ludio[playerNum][position] == 4){                    //4 goes Ace

            cardOne = "Ace";

        }

        else if(ludio[playerNum][position] == 5){                    //5 goes to Seven

            cardOne = "Seven";

        }

        else

            cardOne = "Null";

        return cardOne;

    }

    public static String displayCard(int card){                        //converts a number from integers to the names of the cards they represent

        String cardOne = "0";

        //String cardTwo = "0";

        if(card == 1){                                                //1 goes to King

            cardOne = "King";

        }

        else if(card == 2){                                            //2 goes to Queen

            cardOne = "Queen";

        }

        else if(card == 3){                                            //3 goes to Jack

            cardOne = "Jack";

        }

        else if(card == 4){                                            //4 goes to Ace

            cardOne = "Ace";

        }

        else if(card == 5){                                            //5 goes to Seven

            cardOne = "Seven";

        }

        else

            cardOne = "Null";

        return cardOne;

    }

    //choose a random number

    public static int randum(int max, int plusOne, int notThisNumber){

        Random rand = new Random();

        int number = -2;

        do{

            number = rand.nextInt(max) + plusOne;                    //make a random number

            if (number != notThisNumber && number >=0){                //if the random number is not the number it can't be and the number is >= 0

                break;                                                //if it is break the do-while loop

            }

        }

        while (number != notThisNumber);                            //make a new random number while the number is not the one number it cannot be

        return number;                                                //return the valid random number

    }

    //deal the cards randomly

    public static void deal(int numPlay, int lives, int money){

        int whichcard;

        int update;

        int dealt = 0;

        int deckNum = deckSize;

        do{

            update = 0;

            if (update == 0){                                        //this function determines which card it is

                whichcard = randum(5, 1, -1);                        //choose a random card from the deck

                if (whichcard == 1 && numKing != 0){                //if the random card is 1 and there are still kings left in the deck

                    numKing--;                                        //reduce the number of kings in the deck by one

                    dealt++;                                        //increase the number of cards dealt

                    update = 1;                                        //update the update variable

                }

                if (whichcard == 2 && numQueen != 0){

                    numQueen--;

                    dealt++;

                    update = 1;

                }

                if (whichcard == 3 && numJack != 0){

                    numJack--;

                    dealt++;

                    update = 1;

                }

                if (whichcard == 4 && numAce != 0){

                    numAce--;

                    dealt++;

                    update = 1;

                }

                if (whichcard == 5  && numSeven != 0){

                    numSeven--;

                    dealt++;

                    update = 1;

                }

                if(update == 1){                                    //if a card was drawn

                    Deck1.add(whichcard);                            //add the card drawn to the deck

                }

            }

        }while (dealt < 15);                                        //keep drawing while the number of cards dealt is less than 15

        for (int i = 1; i <= numPlay; i++){                            //for each player playing

            int card1 = Deck1.remove(0);                            //remove one card from the deck and set it to a temporary variable

            int card2 = Deck1.remove(0);                            //remove another card from the deck and set it to a temporary variable

            deckNum -= 2;                                            //reduce the number of cards in the deck by two

            ludio[i][0] = lives; //number of lives                    set the number of lives

            ludio[i][1] = money; //number of coins                    set the number of coins

            ludio[i][2] = card1;                                    //set the first card

            ludio[i][3] = card2;                                    //set the second card

        }

        int k=0; int q=0; int j=0; int a=0; int s=0;                //initialize temporary variables

        for (int i = 0; i < Deck1.size(); i++){                        //for each card in the deck

            if (Deck1.get(i) == 1)                                    //if the card is a one, 

                k++;                                                //increase the number of kings

            if (Deck1.get(i) == 2)                                    //etc

                q++;

            if (Deck1.get(i) == 3)

                j++;

            if (Deck1.get(i) == 4)

                a++;

            if (Deck1.get(i) == 5)

                s++;

        }

        //Print the remaining deck (CHEAT BY REMOVING THE '//' IN FRONT OF THE NEXT TWO LINES)

        //System.out.println("Deck Size: " + deckNum + ". Kings: " + k + ", Queens: " + q + ", Jacks: " + j + ", Aces: " + a + ", Sevens: " +s + ".");

        //System.out.println();

        //if (user)

        //    ludio[1][1] = 999;

        //    ludio[0][1] = 999;

    }

    public static boolean UserCall(boolean hayUser, int actionMade, int calledP){

        Scanner reader = new Scanner(System.in);

        boolean called = false;

        int a = 0;

        if (hayUser && ludio[1][0] > 0){                            //if the user is the one calling the bluff

            while (a == 0){

                System.out.println("Enter '1' to call their bluff and any other number to skip: ");//print the options

                while (!reader.hasNextInt()){                        //prevent non-integer input

                    reader.nextLine();                                 //clear the invalid input before prompting again

                    System.out.println("Please enter an integer: ");//print the instructions again if invalid input is detected

                }

                a = reader.nextInt();                                //set a temporary variable

                if (a == 0)                                            //if they enter the Help Menu key command

                    Help();                                            //open the help menu

            }

            if (a == 1){                                            // if they called bluff

                called = callBluff(1, calledP, 0, actionMade, true);//call the callBluff Method

            }

        }

        return called;

    }

    //end HELPER methods:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

    

    //take player turn

    public static void takeTurn(int playerNumber, int action, boolean hayUser){

        //ludio[playerNumber][0] => lives

        //ludio[playerNumber][1] => money

        //ludio[playerNumber][2] => card one

        //ludio[playerNumber][3] => card two

        //ludio[playerNumber][4] => temporary card three

        //ludio[playerNumber][5] => temporary card four

        //ludio[playerNumber][6] => takeTurn modifier


        //take a random action

        Scanner reader1 = new Scanner(System.in);

        if (ludio[playerNumber][0] > 0 && ludio[playerNumber][6] == 0){    /*check to make sure player is still in the game 

                                                                        and has not already taken a turn that round*/

            int player2 = playerNumber;

            if (!hayUser){

                //action = randum(8, 1, 2);
            	action = decision(playerNumber);
                //action = Cody.AIturn(playerNumber, numPlay);

            }

            if (hayUser && (action == 3 || action == 4 || action == 6) && ludio[1][0] > 0){

                player2 = 0;

                while (player2 == 0){

                    System.out.println("Enter the number of the player you would like to attack: ");

                    while (!reader1.hasNextInt()){

                        reader1.nextLine();                         //clear the invalid input before prompting again

                        System.out.println("Please enter an integer (2-"+numPlay+"): ");

                    }

                    player2 = reader1.nextInt();

                    if (player2 == 0)

                        Help();

                }

                if (player2 < 2 || player2 > numPlay){

                    System.out.println(player2+" is an invalid player number. Your opponent will be selected randomly lol: ");

                    player2 = 1;

                }

            }

                

            while (player2 == playerNumber || ludio[player2][0] == 0){//prevent the player from attacking themselves

                player2 = randum(numPlay, 1, playerNumber);    

            }
            choosePlayer2(playerNumber);

            if (ludio[playerNumber][1] >= 10 && hayUser){            //force player to coup if they have more than 10 coins

                player2 = 0;

                if (action != 6)

                    System.out.println("You must coup someone when you have over 10 coins.");

                while (player2 == 0){

                    System.out.println("Enter the number of the player you would like to attack: ");

                    while (!reader1.hasNextInt()){

                        reader1.nextLine();                         //clear the invalid input before prompting again

                        System.out.println("Please enter an integer (2-"+numPlay+"): ");

                    }

                    //System.out.println("player2 is "+player2);

                    player2 = reader1.nextInt();

                    if (player2 == 0)

                        Help();

                }

                Coup(playerNumber, player2);                        //call the coup method

                shouldTake = false;                                    //update the action modifier

                ludio[playerNumber][6] = 1;                            //update the turn modifier

            }

            else if (ludio[playerNumber][1] >= 10){

                Coup(playerNumber, player2);

                shouldTake = false;

                ludio[playerNumber][6] = 1;

            }

            else if (action == 1 && shouldTake == true){            //all the actions follow this format

                King(playerNumber);                                    //call the method

                shouldTake = false;                                    //update the action modifier

                ludio[playerNumber][6] = 1;                            //update the turn modifier

            }

            else if (action == 3 && ludio[player2][1] > 0 && shouldTake == true && ludio[player2][0] > 0){

                Jack(playerNumber, player2);

                shouldTake = false;

                ludio[playerNumber][6] = 1;

            }

            else if (action == 4 && ludio[player2][0] > 0 && ludio[playerNumber][1] >= 3 && shouldTake == true){

                Ace(playerNumber, player2);

                shouldTake = false;

                ludio[playerNumber][6] = 1;

            }

            else if (action == 5 && shouldTake == true && Deck1.size() > 1){

                Seven(playerNumber);

                shouldTake = false;

                ludio[playerNumber][6] = 1;

            }

            else if (action == 6 && ludio[playerNumber][1] >= 7 && ludio[player2][0] > 0 && shouldTake == true){

                Coup(playerNumber, player2);

                shouldTake = false;

                ludio[playerNumber][6] = 1;

            }

            else if (action == 7 && shouldTake == true){

                fAid(playerNumber);

                shouldTake = false;

                ludio[playerNumber][6] = 1;

            }

            else if (action == 8 && shouldTake == true){

                Income(playerNumber);

                shouldTake = false;

                ludio[playerNumber][6] = 1;

            }

            else if (action == 2 && shouldTake == true){

                ludio[playerNumber][6] = 0;                            //you can't play a queen unless you are being assassinated

            }

            if (shouldTake){

                ludio[playerNumber][6] = 0;

                countRepeatTurn++;

                if (countRepeatTurn > 9)

                    shouldTake = false;

                takeTurn(playerNumber, 0, hayUser);

            }

        }

        else{

            if (user && playerNumber == 1 && ludio[1][0] > 0){

                System.out.println("Trying to take an invalid action skips your turn.");

            }

            System.out.println("Player "+playerNumber+" is out.");

            ludio[playerNumber][6] = 1;

        }
        calcProb(playerNumber, 1, 1);
        //printProbCardFor(playerNumber);                            //print probability list

        shouldTake = true;

    }

    
    public static int decision(int playerNum) {
    	 int chance = randum(100, 1, -1);
    	 int action = 0;
    	 int probBluff[][] = new int[numPlay + 1][6];
    	 int maxProb[] = new int[6];
    	 boolean choose = true;
    	 for (int l = 1; l <= 5; l++) {
	    	 for (int k = 1; k <= numPlay; k++) {
	    		 probBluff[k][l] = (int) Math.round(calcProb(playerNum, k, l));
	    		 if (probBluff[k][l] > probBluff[k][l-1])
	    			 maxProb[l] = probBluff[k][l];
	    	 }
    	 }
    	 
    	 if (ludio[playerNum][0] == 2) {
	    	 for (int i = 1; i <= 5; i++) {
	    		 if (ludio[playerNum][3] == i && choose) {
	    			 if (chance < 20) {
	    				 action = i;
	    				 choose = false;
	    			 }
	    		 }
	    		 else if (ludio[playerNum][2] == i && choose) {
	    			 if (chance < 40) {
	    				 action = i;
	    				 choose = false;
	    			 }
	    		 }
	    	 }
    	 }
    	 else {
    		 for (int i = 1; i <= 5; i++) {
	    		 if (ludio[playerNum][2] == i && choose) {
	    			 if (chance < 40) {
	    				 action = i;
	    				 choose = false;
	    			 }
	    		 }
    		 }
    	 }
    	 if (ludio[playerNum][2] == ludio[playerNum][3] && choose) {
    		 action = 5;
    		 choose = false;
    	 }
    	 else if (turnNumber < 3 && choose && chance < 75){
 		    action = 1;
 		    choose = false;
 		 }
 		 else if (turnNumber > 7 && choose && chance < 75){
 		    action = 3;
 		    choose = false;
 		 }
 		 else if (turnNumber > 7 && choose && chance < 100 && ludio[playerNum][1] > 2){
 		    action = 4;
 		    choose = false;
 		 }
    	 else if (ludio[playerNum][1] >= 7) {
    		 action = 6;
    		 choose = false;
    	 }
    	 else if (choose && maxProb[5] < 25){
 		    action = 5;
 		}
    	 else if (choose) {
    		 action = 8;
    		 choose = false;
    	 }
    	 
    	 return action;
		
    }
    public static int choosePlayer2(int playerNum) {
    	int player2 = 0;
    	int j = 0;
    	int[] opponents = new int[numPlay];
    	for (int i = 0; i < numPlay; i++) {
    		if (ludio[i][0] == 2) {
    			opponents[i] = 1;
    		}
    		else if (ludio[i][0] == 1) {
    			opponents[i] = 1;
    		}
    		for ( j = 0; j < numPlay; j++) {
    			if (opponents[j] == 1) {
    				if (ludio[j][1] > 2) {
    					opponents[j] = 0;
    				}
    			}
    		}
    	}
    	for (int k = 0; k < numPlay; k++) {
    		if (opponents[k] == 1) {
    			player2 = k;
    		}
    	}
    	return player2;
    }
    
    //*************************************************END GAME METHODS**********************************************************

}










