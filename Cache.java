//Nicholas Wade
//COSC 4310
//Simple Cache
//Nov 29, 2021
import java.util.LinkedList;

public class Cache extends CacheProject{
	
	public static void directMapped(int numOfCaches, LinkedList<Integer>memAddr) {
		
		System.out.println("\nDirect-mapped:");
		
		
		int numOfAddr = memAddr.size();
		int numOfMiss = 0;
		int numOfHit = 0;
		LinkedList<Integer>SetAssocContent = new LinkedList<Integer>();
		
		//fills list with -1s which are placeholders
		for (int i = 0; i<numOfCaches; i++) {
			SetAssocContent.add(-1);
		}
		for (int i = 0; i<numOfAddr; i++) {
			
			System.out.println("\n");
				
			//which block address needs to be placed in
			Integer blockDest = memAddr.get(i)%numOfCaches;
			
			//if memory address is already in block
			if(SetAssocContent.get(blockDest)==memAddr.get(i)) {
				numOfHit++;
				System.out.println("Hit");
			}
			//if memory address is not in block
			else {
				SetAssocContent.set(blockDest, memAddr.get(i));
				numOfMiss++;
				System.out.println("Miss");
			}	
			
			//print for each iteration of the cache content
			for(int i2 = 0; i2<numOfCaches; i2++) {
				if(SetAssocContent.get(i2)!=-1) {
					System.out.printf("Mem[%2d]    ",SetAssocContent.get(i2));
				}else {
					System.out.print("empty      ");
				}
			}
		}
		
		
		//print for cache final content
		System.out.println("\n\nFinal Content of Cache Blocks after Reference");
		for(int i = 0; i<numOfCaches; i++) {
			System.out.printf(" %2d        ",i);
		}
		System.out.println();
		for(int i = 0; i<numOfCaches; i++) {
			if(SetAssocContent.get(i)!=-1) {
				System.out.printf("Mem[%2d]    ",SetAssocContent.get(i));
			}else {
				System.out.print("empty      ");
			}
		}
		
		System.out.println();
		System.out.println("\nNum of Misses "+numOfMiss);
		System.out.println("Num of Hits "+numOfHit);
		
		//calculation of the miss rate
		double missRate = ((double)numOfMiss/(double)numOfAddr)*100;
		System.out.printf("Miss Rate: %.2f",missRate);
	}
	public static void setAssoc2way(int numOfCaches, LinkedList<Integer>memAddr) {

		int numOfAddr = memAddr.size();
		int numOfMiss = 0;
		int numOfHit = 0;
		
		//lists to store least recently used indices 
		LinkedList<Integer>LRU0 = new LinkedList<Integer>();
		LinkedList<Integer>LRU1 = new LinkedList<Integer>();
		
		//lists to hold memory addresses for each set
		LinkedList<Integer>Set0 = new LinkedList<Integer>();
		LinkedList<Integer>Set1 = new LinkedList<Integer>();
		
		//fills empty lists with place holders
		for (int i = 0; i<numOfCaches/2; i++) {
			Set0.add(-1);
			Set1.add(-1);
		}
		
		for (int i = 0; i<numOfAddr; i++) {
			
			//desired set number for memory address
			Integer setDest = memAddr.get(i)%2;
			
			//set0
			if(setDest==0) {
				//hit
				if(Set0.contains((Integer)memAddr.get(i))) {
					for(int subI = 0; subI<Set0.size(); subI++){
						if(Set0.get(subI)==memAddr.get(i)) {
							for(int subI2 = 0; subI2<LRU0.size(); subI2++) {
								if(LRU0.get(subI2)==subI) {
									LRU0.remove(subI2);
									LRU0.add(subI);
								}
							}
						}
					}
					numOfHit++;
					continue;
				}
				
				//no hit, use empty space
				if(Set0.contains(-1)) {
					for (int subI = 0; subI<Set0.size(); subI++) {
						if(Set0.get(subI)==-1) {
							Set0.set(subI, memAddr.get(i));
							LRU0.add(subI);
							numOfMiss++;
							break;
						}
					}
				}
				
				//no hit, use LRU
				else {
					LRU0.add(LRU0.peek());
					Set0.set(LRU0.remove(), memAddr.get(i));
					numOfMiss++;
				}
			}
			//set1
			else {
				//hit
				if(Set1.contains((Integer)memAddr.get(i))) {
					for(int subI = 0; subI<Set1.size(); subI++){
						if(Set1.get(subI)==memAddr.get(i)) {
							for(int subI2 = 0; subI2<LRU1.size(); subI2++) {
								if(LRU1.get(subI2)==subI) {
									LRU1.remove(subI2);
									LRU1.add(subI);
								}
							}
						}
					}
					numOfHit++;
					continue;
				}
				//miss, use empty space
				if(Set1.contains(-1)) {
					for (int subI = 0; subI<Set1.size(); subI++) {
						if(Set1.get(subI)==-1) {
							Set1.set(subI, memAddr.get(i));
							LRU1.add(subI);
							numOfMiss++;
							break;
						}
					}
				}
				//miss, use LRU
				else {
					LRU1.add(LRU1.peek());
					Set1.set(LRU1.remove(), memAddr.get(i));
					numOfMiss++;
				}
			}	
		}
		
		//print for final content of cache
		System.out.println("\n2-way Associative: ");
		System.out.println("Final Content of Cache Blocks after Reference");
		for(int i = 0; i<2; i++) {
			for(int i2 = 0; i2<Set0.size(); i2++) {
			System.out.printf("Set%2d      ",i);
			}
		}
		System.out.println();
		for(int i = 0; i<Set0.size(); i++) {
			if(Set0.get(i)!=-1) {
				System.out.printf("Mem[%2d]    ",Set0.get(i));
			}else {
				System.out.print("empty       ");
			}
		}
		for(int i = 0; i<numOfCaches/2; i++) {
			if(Set1.get(i)!=-1) {
				System.out.printf("Mem[%2d]    ",Set1.get(i));
			}else {
				System.out.print("empty      ");
			}
		}
		System.out.println("\n\nNum of Misses "+numOfMiss);
		System.out.println("Num of Hits "+numOfHit);
		
		//calculation of miss rate
		double missRate = ((double)numOfMiss/(double)numOfAddr)*100;
		System.out.printf("Miss Rate: %.2f",missRate);
	}
	public static void setAssoc4way(int numOfCaches, LinkedList<Integer>memAddr) {
	
		int numOfAddr = memAddr.size();
		int numOfMiss = 0;
		int numOfHit = 0;
		
		//lists to store indices for least recently used for each set
		LinkedList<Integer>LRU0 = new LinkedList<Integer>();
		LinkedList<Integer>LRU1 = new LinkedList<Integer>();
		LinkedList<Integer>LRU2 = new LinkedList<Integer>();
		LinkedList<Integer>LRU3 = new LinkedList<Integer>();
		
		//lists to story memory addresses for each set
		LinkedList<Integer>Set0 = new LinkedList<Integer>();
		LinkedList<Integer>Set1 = new LinkedList<Integer>();
		LinkedList<Integer>Set2 = new LinkedList<Integer>();
		LinkedList<Integer>Set3 = new LinkedList<Integer>();
		
		//filling lists with placeholders
		for (int i = 0; i<numOfCaches/4; i++) {
			Set0.add(-1);
			Set1.add(-1);
			Set2.add(-1);
			Set3.add(-1);
		}
		
		for (int i = 0; i<numOfAddr; i++) {
			
			//desired set number for current memory address
			Integer setDest = memAddr.get(i)%4;
			
			//set0
			if(setDest==0) {
				//hit
				if(Set0.contains((Integer)memAddr.get(i))) {
					for(int subI = 0; subI<Set0.size(); subI++){
						if(Set0.get(subI)==memAddr.get(i)) {
							for(int subI2 = 0; subI2<LRU0.size(); subI2++) {
								if(LRU0.get(subI2)==subI) {
									LRU0.remove(subI2);
									LRU0.add(subI);
								}
							}
						}
					}
					numOfHit++;
					continue;
				}
				
				//no hit, use empty space
				if(Set0.contains(-1)) {
					for (int subI = 0; subI<Set0.size(); subI++) {
						if(Set0.get(subI)==-1) {
							Set0.set(subI, memAddr.get(i));
							LRU0.add(subI);
							numOfMiss++;
							break;
						}
					}
				}
				
				//no hit, use LRU
				else {
					LRU0.add(LRU0.peek());
					Set0.set(LRU0.remove(), memAddr.get(i));
					numOfMiss++;
				}
			}
			//set1
			if(setDest==1) {
				//hit
				if(Set1.contains((Integer)memAddr.get(i))) {
					for(int subI = 0; subI<Set1.size(); subI++){
						if(Set1.get(subI)==memAddr.get(i)) {
							for(int subI2 = 0; subI2<LRU1.size(); subI2++) {
								if(LRU1.get(subI2)==subI) {
									LRU1.remove(subI2);
									LRU1.add(subI);
								}
							}
						}
					}
					numOfHit++;
					continue;
				}
				
				//no hit, use empty space
				if(Set1.contains(-1)) {
					for (int subI = 0; subI<Set1.size(); subI++) {
						if(Set1.get(subI)==-1) {
							Set1.set(subI, memAddr.get(i));
							LRU1.add(subI);
							numOfMiss++;
							break;
						}
					}
				}
				
				//no hit, use LRU
				else {
					LRU1.add(LRU1.peek());
					Set1.set(LRU1.remove(), memAddr.get(i));
					numOfMiss++;
				}
			}
			//set2
			if(setDest==2) {
				//hit
				if(Set2.contains((Integer)memAddr.get(i))) {
					for(int subI = 0; subI<Set2.size(); subI++){
						if(Set2.get(subI)==memAddr.get(i)) {
							for(int subI2 = 0; subI2<LRU2.size(); subI2++) {
								if(LRU2.get(subI2)==subI) {
									LRU2.remove(subI2);
									LRU2.add(subI);
								}
							}
						}
					}
					numOfHit++;
					continue;
				}
				
				//no hit, use empty space
				if(Set2.contains(-1)) {
					for (int subI = 0; subI<Set2.size(); subI++) {
						if(Set2.get(subI)==-1) {
							Set2.set(subI, memAddr.get(i));
							LRU2.add(subI);
							numOfMiss++;
							break;
						}
					}
				}
				
				//no hit, use LRU
				else {
					LRU2.add(LRU2.peek());
					Set2.set(LRU2.remove(), memAddr.get(i));
					numOfMiss++;
				}
			}
			//set3
			if(setDest==3){
				//hit
				if(Set3.contains((Integer)memAddr.get(i))) {
					for(int subI = 0; subI<Set3.size(); subI++){
						if(Set3.get(subI)==memAddr.get(i)) {
							for(int subI2 = 0; subI2<LRU3.size(); subI2++) {
								if(LRU3.get(subI2)==subI) {
									LRU3.remove(subI2);
									LRU3.add(subI);
								}
							}
						}
					}
					numOfHit++;
					continue;
				}
				//no hit, use empty space
				if(Set3.contains(-1)) {
					for (int subI = 0; subI<Set3.size(); subI++) {
						if(Set3.get(subI)==-1) {
							Set3.set(subI, memAddr.get(i));
							LRU3.add(subI);
							numOfMiss++;
							break;
						}
					}
				}
				//no hit, use LRU
				else {
					LRU3.add(LRU3.peek());
					Set3.set(LRU3.remove(), memAddr.get(i));
					numOfMiss++;
				}
			}	
		}
		
		//print for final content of cache
		System.out.println("\n4-way Associative: ");
		System.out.println("Final Content of Cache Blocks after Reference");
		for(int i = 0; i<4; i++) {
			for(int i2 = 0; i2<Set0.size(); i2++) {
				System.out.printf("Set%2d      ",i,i);
				}
		}
		System.out.println();
		for(int i = 0; i<numOfCaches/4; i++) {
			if(Set0.get(i)!=-1) {
				System.out.printf("Mem[%2d]    ",Set0.get(i));
			}else {
				System.out.print("empty       ");
			}
		}
		for(int i = 0; i<numOfCaches/4; i++) {
			if(Set1.get(i)!=-1) {
				System.out.printf("Mem[%2d]    ",Set1.get(i));
			}else {
				System.out.print("empty      ");
			}
		}
		for(int i = 0; i<numOfCaches/4; i++) {
			if(Set2.get(i)!=-1) {
				System.out.printf("Mem[%2d]    ",Set2.get(i));
			}else {
				System.out.print("empty       ");
			}
		}
		for(int i = 0; i<numOfCaches/4; i++) {
			if(Set3.get(i)!=-1) {
				System.out.printf("Mem[%2d]    ",Set3.get(i));
			}else {
				System.out.print("empty     ");
			}
		}
		System.out.println("\n\nNum of Misses "+numOfMiss);
		System.out.println("Num of Hits "+numOfHit);
		
		//calculation of miss rate
		double missRate = ((double)numOfMiss/(double)numOfAddr)*100;
		System.out.printf("Miss Rate: %.2f",missRate);
	}
	public static void fullyAssoc(int numOfCaches, LinkedList<Integer>memAddr) {
		
		System.out.println("\nFully Associative: ");
		
		int numOfAddr = memAddr.size();
		int numOfMiss = 0;
		int numOfHit = 0;
		
		//list to store indices for least recently used 
		LinkedList<Integer>LRU = new LinkedList<Integer>();
		
		//list to story memory addresses in cache
		LinkedList<Integer>SetAssocContent = new LinkedList<Integer>();
		
		//fills list with placeholders
		for (int i = 0; i<numOfCaches; i++) {
			SetAssocContent.add(-1);
		}
		
		for (int i = 0; i<numOfAddr; i++) {
			//hit
			if(SetAssocContent.contains(memAddr.get(i))) {
				for(int subI = 0; subI<SetAssocContent.size(); subI++){
					if(SetAssocContent.get(subI)==memAddr.get(i)) {
						for(int subI2 = 0; subI2<LRU.size(); subI2++) {
							if(LRU.get(subI2)==subI) {
								LRU.remove(subI2);
								LRU.add(subI);
								numOfHit++;
								System.out.println("\nHit");
								break;
							}
						}
					}
				}
			} else {
				//no hit empty space
				if(SetAssocContent.contains(-1)) {
					for (int subI = 0; subI<SetAssocContent.size(); subI++) {
						if(SetAssocContent.get(subI)==-1) {
							SetAssocContent.set(subI, memAddr.get(i));
							LRU.add(subI);
							numOfMiss++;
							System.out.println("\nMiss");
							break;
						}
					}
				}
				//no hit, use LRU
				else {
					LRU.add(LRU.peek());
					SetAssocContent.set(LRU.remove(), memAddr.get(i));
					numOfMiss++;
					System.out.println("\nMiss");
				}
			}
			
			//print for each iteration of cache
			for(int i2 = 0; i2<numOfCaches; i2++) {
				if(SetAssocContent.get(i2)!=-1) {
					System.out.printf("Mem[%2d]    ",SetAssocContent.get(i2));
				}else {
					System.out.print("empty    ");
				}
			}
			System.out.println();
		}
		
		//print for final content of cache
		System.out.println("\nFinal Content of Cache Blocks after Reference");
		for(int i = 0; i<numOfCaches; i++) {
			System.out.printf("Block %2d    ",i);
		}
		System.out.println();
		for(int i = 0; i<numOfCaches; i++) {
			if(SetAssocContent.get(i)!=-1) {
				System.out.printf("Mem[%2d]     ",SetAssocContent.get(i));
			}else {
				System.out.print("empty    ");
			}
		}
		System.out.println("\nNum of Misses "+numOfMiss);
		System.out.println("Num of Hits "+numOfHit);
		
		//calculation of miss rate
		double missRate = ((double)numOfMiss/(double)numOfAddr)*100;
		System.out.printf("Miss Rate: %.2f",missRate);

	}
}
