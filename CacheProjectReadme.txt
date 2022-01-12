//Nicholas Wade
//COSC 4310
//Cache Project
//Nov 29, 2021

How to compile:

	-In terminal, change directory to the file holding the files: Cache.java and CacheProject.java

	-Then since this is a java file type “javac Cache.java” and then “javac CacheProject.java” to compile both files.

How to run:

	-Also in terminal, type “java CacheProject” which will run the program.

How to test:

	-The console will prompt you to enter the number of blocks, so input an appropriate integer value such as 4, 6, 8, etc.

	-Then you will be prompted to choose a set-associative option from a list of integers, be sure to input either 1, 2, 3, or 4, as any other int value will not correspond to an appropriate set-associative option.

	-Then you will be prompted to enter the number of memory addresses that will be inputed, it is important that this matches the number of memory addresses that you actually input in the next step.

	-Finally you will enter the memory address values (in base 10) with only spaces in between ex: 0 8 0 6 8

Result:

	-The program should display the final content of the cache after performing the set-associativity option.

	-The program will also display the miss count, hit count, and the miss rate.
