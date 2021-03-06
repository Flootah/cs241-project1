package cs241.proj1;

import java.util.Scanner;

/**
 * This class represents the User Interface for the program.
 * 
 * 
 * @author Eduardo S.
 *
 */
public class UI {
	//current menu 'page' that the program is on.
	int page;
	//Scanner to get user input.
	Scanner sc;
	//Binary Search Tree to be created and modified.
	BinaryTree tree;
	
	/**
	 * Constructor for the UI.
	 * page always begins at 0
	 * initializes Scanner
	 */
	UI() {
		page = 0;
		sc = new Scanner(System.in);
	}

	/**
	 * Main vehicle for the User Interface.
	 * 
	 * Has three phases, or "pages"
	 * 1. Initialize. This prompts the user for the values they desire the BST to have, then creates it.
	 * 2. Print. The created tree is called to print each traversal once it is initialized.
	 * 3. Main Menu. From here, the user can modify the tree, view the help page, or exit the program.
	 */
	public void menuEngine() {
		System.out.println("CS241 Project 1: Binary Search Tree \n");
		while(true) {
			switch(page){
			case 0:
				initialize();
				break;
			case 1:
				printAll();
				break;
			case 2:
				System.out.println("Main Menu:");
				System.out.println("A: add a value");
				System.out.println("R: remove a value");
				System.out.println("H: help");
				System.out.println("E: exit program");
				mainMenu();
				break;
			default:
				System.out.println("UI navigation error, restarting...");
				page = 0;
				break;
			}
		}
	}
	
	/**
	 * Prints all traversals of the current tree, then sends the Menu Engine to page 2 (main menu)
	 */
	private void printAll() {
		//print all traversals
		tree.preOrder();
		tree.inOrder();
		tree.postOrder();
		//next UI page
		page = 2;
	}

	/**
	 * UI to initialize the tree.
	 * 
	 * first prompts the user for input,
	 * then creates a tree by parsing the input into an array of integers, 
	 * then using the BinaryTree constructor with it.
	 */
	private void initialize() {
		//prompt and receive user input
		System.out.println("Enter initial values for the tree:");
		String choice = sc.nextLine();
		//if input is empty, create an empty tree.
		if(choice.equals("")) {
			try {
				tree = new BinaryTree();
			} catch (DuplicateValueException e) {
				System.out.println("How did you get duplicate values with an empty tree?");
				System.out.println(e.getMessage());
				return;
			}
			page = 1;
			return;
		}
		//splits input by spaces, placed into String array strings[]
		String strings[] = choice.split(" ");
		//create new int array numbers[] of same size as strings[]
		int[] numbers = new int[strings.length];
		//loop thru strings[], parsing each into a number, placed in numbers[]
		for(int i = 0; i < strings.length; i++) {
			try {
				int num  = Integer.parseInt(strings[i]);
				numbers[i] = num;
			} catch (NumberFormatException e) {
				System.out.println("Error reading input format, try again");
				System.out.println(e.getMessage());
				return;
			} //end try...catch
		} //end for
		
		//create BinaryTree using int array we made
		try {
			tree = new BinaryTree(numbers);
		} catch (DuplicateValueException e) {
			System.out.println("Duplicate values not allowed!");
			return;
		}
		//send to next page (printing traversals)
		page = 1;
	}
	
	/**
	 * Main Menu of the program.
	 * 
	 * Takes in several input commands, and can help the user:
	 * modify the tree,
	 * view help page,
	 * or exit the program.
	 */
	private void mainMenu() {
		while(true) {
			//prompt and recieve input from user.
			System.out.println("Enter a command:");
			String choice = sc.nextLine();
			//send user to corresponding menu, based on input (not case sensitive)
			switch(choice.toUpperCase()) {
			//A = add a value to the tree
			case "A":
				add();
				tree.inOrder();
				continue;
			//R = remove a value from the tree
			case "R":
				remove();
				tree.inOrder();
				continue;
			//H = view the help page
			case "H":
				help();
				break;
			//E = exit the program
			case "E":
				System.out.println("Exiting...");
				System.exit(0);
				break;
			//All other inputs print this error message
			default:
				System.out.println("command not recognized... try again");
				continue;
			}
			break;
		}
	}

	/**
	 * Prints the Help page, then sends the user to the main menu.
	 * This breaks the switch statement in mainMenu, and thus will also print the Main Menu commands again.
	 */
	private void help() {
		System.out.println("CS214 Project 1: Binary Search Tree");
		System.out.println(	  "This program creates a Binary Tree from a list of values. \n"
							+ "This list cannot contain duplicates. \n"
							+ "Users can add and remove value from the BST once it is initialized. \n");
	}

	/**
	 * Method to add a value to the tree.
	 * 
	 * prompts the user for a value, and receives an integer to add.
	 * then simply calls an add() on the current tree with the input.
	 */
	private void add() {
		System.out.println("Enter a value to add:");
		int choice = sc.nextInt();
		sc.nextLine();
		
		try {
			tree.add(choice);
		} catch (DuplicateValueException e) {
			System.out.println(choice + " already exists! Duplicate values are not allowed.");
			return;
		}
		page = 2;
	}
	
	/**
	 * Method to remove a value from the tree.
	 * 
	 * prompts the user for a value, and receives an integer to remove.
	 * then simply calls an remove() on the current tree with the input.
	 */
	private void remove() {
		System.out.println("Enter a value to remove:");
		int choice = sc.nextInt();
		sc.nextLine();
		
		try {
			tree.remove(choice);
		} catch (ValueNotFoundException e) {
			System.out.println(choice + " doesn't exist in this tree!");
		}
		page = 2;
	}
	
}
