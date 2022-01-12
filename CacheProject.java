//Nicholas Wade
//COSC 4310
//Simple Cache
//Nov 29, 2021
import java.util.LinkedList;
import java.util.Scanner;

public class CacheProject {
	

	public static void main(String[] args) {
		
		int numOfCaches;
		int setAssoc;
		LinkedList<Integer> blockAddr = new LinkedList<Integer>();
			
		
		Scanner kb = new Scanner(System.in);
		System.out.print("Please input # of cache blocks: ");
		numOfCaches = kb.nextInt();
		System.out.print("\nPlease input set associativity option (1 for direct-mapped, 2 for 2-way set associative, 3 for 4-way, 4 for fully associative): ");
		setAssoc = kb.nextInt();
		System.out.print("\nPlease input the # of block address references: ");
		int numOfBlockAddr = kb.nextInt();
		System.out.print("\nPlease input block address references: ");
		for (int i = 0; i<numOfBlockAddr; i++) {
			blockAddr.add(kb.nextInt());
		}

		kb.close();
		
		if (setAssoc==1) {
			Cache.directMapped(numOfCaches, blockAddr);
		} else if(setAssoc==2) {
			Cache.setAssoc2way(numOfCaches, blockAddr);
		} else if(setAssoc==3) {
			Cache.setAssoc4way(numOfCaches, blockAddr);
		} else if(setAssoc==4) {
			Cache.fullyAssoc(numOfCaches, blockAddr);
		} else {
			System.out.println("Incorrect set associativity input number.");
		}
	}
}
