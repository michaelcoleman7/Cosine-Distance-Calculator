# Multi-Threaded-Cosine-Distance-Calculator
Object Oriented Programming - Multithreaded Cosine Distance Computer  

## About:
1. Program asks user to enter file directories which files are to be generated,
e.g. if files are in same directory as project user would type just the foldername e.g. books 
or if doing a folder outside the project directory, 
enter full directory e.g. C:\Users\Michael\Desktop\foldername

2. These files are then taken and processed using threads, 
The threads are FileParser's which take the file 
and break up into shingles when a space is encountered and adds them to a blocking queue.

3. A ShingleTaker thread is then called to add the shingles to a ConcurrentHashMap, 
and return it using callables and futures.

4. The returned HashTables(One for query and One for all Subject files) are then sent
to a CosineCalculator class, which calculates the cosine distance and returns results to the menu
via an ArrayList where they are displayed.

## Instructions:
CD into project folder
To run jar - use following command:
java -cp ./oop.jar ie.gmit.sw.Runner

Note: Example files provided if needed, in query and books folders
