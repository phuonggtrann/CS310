// --------------------------------------------------------
// DO NOT EDIT ANYTHING BELOW THIS LINE (except to add JavaDocs)
// --------------------------------------------------------
import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * main class to run syeda.
 */
public class Simulate {
	/**
	 * main method.
	 * @param args for main method
	 */
	public static void main(String[] args) {
		String usage = "Usage: java Simulate [numPlates > 0]";
		
		if(args.length != 1) { System.out.println(usage); return; }
		
		int numPlates = 0;
		try { numPlates = Integer.parseInt(args[0]); }
		catch(NumberFormatException e) { System.out.println(usage); return; }
		if(numPlates < 1) { System.out.println(usage); return; }
		
		Spinner syeda = new Spinner(numPlates);
		System.out.println("\nSyeda, the plate spinner, is learning to do a shower trick...");
		System.out.println("She has " + numPlates + " plate(s) to work with");
		Scanner in = new Scanner(System.in);
		
		while(true) {
			System.out.println("\n" + syeda);
			int choice = doMenu(in);
			try {
				switch(choice) {
					case 1: syeda.pickUpPlate(); break;
					case 2: syeda.spinPlate(); break;
					case 3: syeda.catchPlate(); break;
					case 4: syeda.passPlate(); break;
					case 5: syeda.putDownPlate(); break;
					case 6: in.close(); System.exit(0);
				}
			}
			catch(RuntimeException e) {
				System.out.println("\n"+e.getMessage());
				System.out.println("Syeda dropped everything!");
				syeda = new Spinner(numPlates);
				System.out.println("Syeda wants to try again...\n");
			}
		}
	}
	
	/**
	 * print out menu and take in user input.
	 * @param in scanner
	 * @return int input that user enter
	 */
	public static int doMenu(Scanner in) {
		while(true) {
			try {
				System.out.println("\nSyeda can:");
				System.out.println(" 1) Pick up a plate from the bin");
				System.out.println(" 2) Spin a plate into the air");
				System.out.println(" 3) Catch a plate from the air");
				System.out.println(" 4) Pass a plate between hands");
				System.out.println(" 5) Put a plate down on the bin");
				System.out.println(" 6) Quit");
				System.out.print("\nWhat should she do? ");
				int choice = in.nextInt();
				in.nextLine();
				
				if(choice < 1 || choice > 6) {
					System.out.println("Invalid selection");
					continue;
				}
				
				return choice;
			}
			catch(InputMismatchException e) {
				System.out.println("Invalid selection");
				in.nextLine();
			}
		}
	}
}
