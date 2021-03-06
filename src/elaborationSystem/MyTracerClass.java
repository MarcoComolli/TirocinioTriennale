package elaborationSystem;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.TreeSet;

public class MyTracerClass {
//
	private static HashMap<String, Integer> countMap = new HashMap<String, Integer>();
	private static HashMap<String, Integer> instructionsCountMap = new HashMap<String, Integer>();
//	private static HashMap<String, LinkedList<String>> pathMap = new HashMap<String, LinkedList<String>>();
	private static boolean recordPath = false;
//	private static LinkedList<String> blockList = new LinkedList<String>();
	private static String currentObjectIDPath;
	private static int currentexecutionNumberPath = -1;
	private static boolean firstTime = true, firstTimeTest = true, firstTimeBlock = true, firstTimeStat = true;
	private static String filesPath = "C:\\Users\\Marco\\Desktop\\files";
	private static int coveredBlocksTest = 0, totalCoveredBlocksTest = 0, newUniqueCoveredBloksTest = 0;
	private static int totalTest = 0, totalTestClasses = 0, totalUniqueBlocksCovered = 0;
	private static boolean recordTest = false;
	private static int blockCount = 0;
	private static TreeSet<String> testedBlockSet = new TreeSet<String>();;
	static int c = 0;
	private static int pathNumber = 0, totalPathSize = 0;
	private static double totalTimeSec;
	private static DecimalFormat form = new DecimalFormat("#.####");
	private static boolean startOfPath = false;
	private static PrintWriter printWriter;
	
	static{
		try {
			printWriter = new PrintWriter(new FileWriter(filesPath + "\\FilePercorsi.txt"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//
//
//
//
//
	public static void tracer(String objectID, int blockCode, int blockID) {
		try {
			

			Integer n = countMap.get(objectID+"@"+blockID+"#"+blockCode);
			if(n != null){
				n++;			
			}
			else{
				n = 1;
			}


			if(recordPath){
				writePathsFile(objectID, blockID);
				totalPathSize++;
//				blockList.add(objectID + "@" + blockID);
			}


			countMap.put(objectID+"@"+blockID+"#"+blockCode, n);


			if(recordTest){
				coveredBlocksTest++;
				if(n == 1){
					newUniqueCoveredBloksTest++;
				}
			testedBlockSet.add(objectID+"@"+blockID);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
//
	public static void tracer(String objectID, int blockCode, int blockID, boolean[] ilMioArrayDiBooleani){
		try {
			

			Integer n = countMap.get(objectID+"@"+blockID+"#"+blockCode);
			if(n != null){
				n++;			
			}
			else{
				n = 1;
			}
			
			
			if(recordPath){
				writePathsFile(objectID, blockID, ilMioArrayDiBooleani);
				totalPathSize++;
//				blockList.add(objectID + "@" + blockID);
			}

			countMap.put(objectID+"@"+blockID+"#"+blockCode, n);

			if(recordTest){
				coveredBlocksTest++;
				if(n == 1){
					newUniqueCoveredBloksTest++;
				}
			testedBlockSet.add(objectID+"@"+blockID);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
//
	private static void writeStatisticsData(String objectID, int blockCode, int blockID, int n) throws IOException {

		PrintWriter printWriter;
		String numberOfInstructionsFilePath= filesPath + "\\DatiStatistici.txt";


		if(firstTimeStat){
			printWriter = new PrintWriter(new FileWriter(numberOfInstructionsFilePath));
			firstTimeStat=false;
		}else{
			printWriter = new PrintWriter(new FileWriter(numberOfInstructionsFilePath,true));
		}

		Integer instructions = instructionsCountMap.get(objectID+"@"+blockID);
		if(instructions == null){
			c++;
			System.out.println(c + " E' nullo " + objectID+"@"+blockID);
			instructions = -1;
		}
		printWriter.println(objectID +" #c" + blockCode + " @" + blockID + " #v " + n +" #i " +instructions);
		printWriter.flush();


		printWriter.close();
	}
//
	private static void insertIstructionsNumber() throws FileNotFoundException, IOException {

		BufferedReader br = new BufferedReader(new FileReader(filesPath + "\\NumeroIstruzioni.txt"));
		try {
			String line = br.readLine();
			while (line != null) {
				String []lineArray=line.split("#");
				instructionsCountMap.put(lineArray[0],Integer.parseInt(lineArray[1]));
				line = br.readLine();
			}

		} finally {
			br.close();
		}

	}
//
//	
//
	private static void writePathsFile(String objectID, int blockID, boolean[] ilMioArrayDiBooleani) throws IOException {
		String booleanString="";
		for (int i=0; i<ilMioArrayDiBooleani.length; i++){
			booleanString+=ilMioArrayDiBooleani[i] +" ";
		}
		
		// String pathsFilePath=filesPath + "\\FilePercorsi.txt";

//		if(firstTime){
//			printWriter = new PrintWriter(new FileWriter(pathsFilePath));
//			firstTime=false;
//		}else{
//			printWriter = new PrintWriter(new FileWriter(pathsFilePath,true));
//		}

		//printWriter.println(objectID + " code: " + blockCode + " IDblocco: " + blockID + " numero di volte: " + n
		//		+" numero istruzioni nel blocco: " +instructionsNumber);
		if(startOfPath){
			startOfPath = false;
			printWriter.println("�"+currentObjectIDPath+"*"+currentexecutionNumberPath);
		}
		printWriter.println(objectID +"@" + blockID + "-"+booleanString);
		printWriter.flush();


	//	printWriter.close();
	}
//
	private static void writePathsFile(String objectID, int blockID) throws IOException {
//		PrintWriter printWriter;
//		String pathsFilePath= filesPath + "\\FilePercorsi.txt";
//
//		if(firstTime){
//			printWriter = new PrintWriter(new FileWriter(pathsFilePath));
//			firstTime=false;
//		}else{
//			printWriter = new PrintWriter(new FileWriter(pathsFilePath,true));
//		}
//
//		//printWriter.println(objectID + " code: " + blockCode + " IDblocco: " + blockID + " numero di volte: " + n
//		//		+" numero istruzioni nel blocco: " +instructionsNumber);
		if(startOfPath){
			startOfPath = false;
			printWriter.println("�"+currentObjectIDPath+"*"+currentexecutionNumberPath);
		}
		printWriter.println(objectID +"@" + blockID );
		printWriter.flush();
//
//
//		printWriter.close();
	}
//
//	//inizia a registrare un percorso
	public static void recordPath(String objectID){
		if(recordPath == false){
			recordPath = true;
			startOfPath  = true;
			//inizializza i campi
			currentexecutionNumberPath = countMap.get(objectID+"@0#-1"); //il codice � 0 perch� � l'inizio del metodo
			currentObjectIDPath = objectID;
//			blockList.clear();
			pathNumber++;
		}
	}
//
	public static void endRecordPath(String objectID){
		if(objectID == currentObjectIDPath){ //se l'ordine di fermarsi arriva dalla fine del metodo giusto
			recordPath = false;
//			LinkedList<String> newList = (LinkedList<String>) blockList.clone();
//			pathMap.put(objectID+"*"+ currentexecutionNumberPath, newList);
		}

	}
//
	public static void setFilesPath(String path){
//		filesPath = path;
	}
//
	public static void startRecordTestCoverage() {
		if(recordTest == false){
			recordTest = true;
		}
		coveredBlocksTest = 0;
		newUniqueCoveredBloksTest = 0;
		testedBlockSet.clear();
	}
//	
	public static void endRecordTestCoverage(String fullname, float timeSec, int testNumber, int failCount){
		try{
			
			PrintWriter printWriter;
			String pathsFilePath= filesPath + "\\TestCoverage.txt";
			totalTest += testNumber;
			totalTimeSec += timeSec;
			totalTestClasses++;
			totalUniqueBlocksCovered += testedBlockSet.size();
			totalCoveredBlocksTest += coveredBlocksTest;
			if(firstTimeTest){
				printWriter = new PrintWriter(new FileWriter(pathsFilePath));
				firstTimeTest=false;
			}else{
				printWriter = new PrintWriter(new FileWriter(pathsFilePath,true));
			}

			double blockPercentage = (double)testedBlockSet.size()/blockCount*100;
			double uniqueBlockPercentage = (double)newUniqueCoveredBloksTest/blockCount*100;
			int tnc50 = (coveredBlocksTest != 0) ? (int)Math.ceil(50 / blockPercentage) : 0;
			int tnc75 = (coveredBlocksTest != 0) ? (int)Math.ceil(75 / blockPercentage) : 0;
			int tnc85 = (coveredBlocksTest != 0) ? (int)Math.ceil(85 / blockPercentage) : 0;
			int tnt50 = (coveredBlocksTest != 0) ? (int)Math.ceil(50/(blockPercentage /testNumber)) : 0;
			int tnt75 = (coveredBlocksTest != 0) ? (int)Math.ceil(75/(blockPercentage /testNumber)) : 0;
			int tnt85 = (coveredBlocksTest != 0) ? (int)Math.ceil(85/(blockPercentage /testNumber)) : 0;

//			printWriter.println(fullname + " #c " + coveredBlocksTest + " #n " + testNumber + " #f " + failCount + " #t " + timeSec +
//					" #ub " + newUniqueCoveredBloksTest + " #ubp " + form.format(uniqueBlockPercentage) +
//					" #b " + testedBlockSet.size() + " #bp " +  form.format(blockPercentage) +
//					" #tnc " + (int)Math.ceil(50 / blockPercentage) + "-" + (int)Math.ceil(75 / blockPercentage) + "-" + (int)Math.ceil(85 / blockPercentage) +
//					" #tnt " + (int)Math.ceil(50/(blockPercentage /testNumber)) + "-" + (int)Math.ceil(75 /(blockPercentage / testNumber)) + "-" + (int)Math.ceil(85 / (blockPercentage / testNumber)));
			printWriter.println(fullname + " #" + coveredBlocksTest + "#" + testNumber + "#" + failCount + "#" + timeSec +
					"#" + newUniqueCoveredBloksTest + "#" + form.format(uniqueBlockPercentage) +
					"#" + testedBlockSet.size() + "#" +  form.format(blockPercentage) +
					"#" + tnc50 + "-" + tnc75 + "-" + tnc85 + "#" + tnt50 + "-" + tnt75 + "-" + tnt85);
			
			printWriter.flush();
			printWriter.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
//	
	public static void endOfTests(){
		try{
			printWriter.close();

			int testedBlockCount = 0, uncoveredBlocks = 0;
			String objectID, blockCode, blockID;
			insertIstructionsNumber();
			
			for(Entry<String,Integer> entry : countMap.entrySet()){
				String key = entry.getKey();
				objectID = key.substring(0, key.indexOf("@"));
				blockCode = key.substring(key.indexOf("#")+1);
				blockID = key.substring(key.indexOf("@") +1, key.indexOf("#"));
				

				writeStatisticsData(objectID, Integer.parseInt(blockCode), Integer.parseInt(blockID), entry.getValue());
				if(entry.getValue() != 0){
					testedBlockCount++;
				}
				else{
					uncoveredBlocks++;
				}
			}

			
			DecimalFormat form2 = new DecimalFormat("#.##");
			double avgBlockPerClass = (double)totalUniqueBlocksCovered/totalTestClasses;
			double percentageTestedBlock = (double)testedBlockCount/(double)blockCount*100;
			double percentageAverageBlockCoveredClass = avgBlockPerClass/blockCount *100;
			double percentageAverageBlockCoveredTest = (avgBlockPerClass/blockCount *100)/((double)totalTest/totalTestClasses);
			PrintWriter printWriter;
			String pathsFilePath= filesPath + "\\GlobalData.txt";
			printWriter = new PrintWriter(new FileWriter(pathsFilePath));

			printWriter.println("Total block code: " +  blockCount);
			printWriter.println("Total block code tested (cumulative): " + totalCoveredBlocksTest);
			printWriter.println("Total block code tested: " + testedBlockCount);
			printWriter.println("Uncovered block: " + uncoveredBlocks);
			printWriter.println("Percentage test coverage: " + form.format(percentageTestedBlock) +"%");
			printWriter.println("Percentage test uncovered: " + form.format((double)uncoveredBlocks/(double)blockCount*100) +"%");
			printWriter.println("-----------------");
			printWriter.println("Total number of path: " + pathNumber);
			printWriter.println("Total number of path-block covered: " + totalPathSize);
			printWriter.println("Average path size: " + form.format((double)totalPathSize/pathNumber));
			printWriter.println("-----------------");
			printWriter.println("Total time for testing " + totalTimeSec + " sec.");
			printWriter.println("Total number of test classes: " + totalTestClasses);
			printWriter.println("Total number of tests: " + totalTest);
			printWriter.println("Average tests for test class: " + form.format((double)totalTest/totalTestClasses));
			printWriter.println("Average block covered by test class: " + form.format(avgBlockPerClass) + " (" + 
						form.format(avgBlockPerClass/testedBlockCount*100) +"% of tested blocks and " +
						form.format(percentageAverageBlockCoveredClass) +"% of total blocks)");
			printWriter.println("-----------------");
			printWriter.println("Number of additional class to reach 50% coverage: " + form2.format((50- percentageTestedBlock)/percentageAverageBlockCoveredClass));
			printWriter.println("Number of additional class to reach 75% coverage: " + form2.format((75- percentageTestedBlock)/percentageAverageBlockCoveredClass));
			printWriter.println("Number of additional class to reach 85% coverage: " + form2.format((85- percentageTestedBlock)/percentageAverageBlockCoveredClass));
			printWriter.println("Number of additional tests to reach 100% coverage: " + form2.format((100- percentageTestedBlock)/percentageAverageBlockCoveredClass));
			printWriter.println();
			printWriter.println("Number of additional tests to reach 50% coverage: " + form2.format((50- percentageTestedBlock)/percentageAverageBlockCoveredTest));
			printWriter.println("Number of additional tests to reach 75% coverage: " + form2.format((75- percentageTestedBlock)/percentageAverageBlockCoveredTest));
			printWriter.println("Number of additional tests to reach 85% coverage: " + form2.format((85- percentageTestedBlock)/percentageAverageBlockCoveredTest));
			printWriter.println("Number of additional tests to reach 100% coverage: " + form2.format((100- percentageTestedBlock)/percentageAverageBlockCoveredTest));
			
			printWriter.flush();
			printWriter.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
//	
//	//se � necessario un file con l'elenco dei blocchi basta scommentare il codice qua sotto
	public static void addBlock(String blockName, int blockID, int blockCode){

		try{
			PrintWriter printWriter;
			String pathsFilePath= filesPath + "\\Blocks.txt";
			
			if(firstTimeBlock){
				printWriter = new PrintWriter(new FileWriter(pathsFilePath));
				firstTimeBlock=false;
			}else{
				printWriter = new PrintWriter(new FileWriter(pathsFilePath,true));
			}
			blockCount++;
			countMap.put(blockName+"@"+blockID+"#"+blockCode, 0);

			printWriter.println(blockName+"@"+blockID+"#"+blockCode);
			printWriter.flush();
			printWriter.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
//	
	public static int getTotalPathsSize(){
		return totalPathSize;
	}


}