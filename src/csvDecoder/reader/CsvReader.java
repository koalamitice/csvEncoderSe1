package csvDecoder.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import csvDecoder.util.StudentComparator;
import csvDecoder.util.StudentData;

public class CsvReader {
	
	private static final String[] BOMs = {"ï»¿", "þÿ", "ÿþ"};
	
	/*
	 * format to read: [#BLATT]; [VORNAME, NACHNAME]; [0/1]; [0/1]; ...
	 * 0 = nicht votiert, 1 = votiert
	 */

	public Set<StudentData> readCsvFiles (String directoryPath) {
		//List<StudentData> data = new ArrayList<StudentData>();
		Set<StudentData> data = new TreeSet<StudentData>(new StudentComparator());
		
		File directory = new File(directoryPath);
		if (!directory.isDirectory()) {
			//quit when file is not a dir
			System.exit(1);
		}
		
		File[] csvFiles = directory.listFiles();
		for (int i = 0; i < csvFiles.length; i++) {
			try { 
				//read file:
				BufferedReader csvReader = new BufferedReader(new FileReader(csvFiles[i].getAbsolutePath())); 
				String line = csvReader.readLine();
				if (line == null) {
					System.out.println("[ERROR:] while reading file: \"" + csvFiles[i].getName() + "\"");
					continue;
				}
				
				//format line input:
				line = line.replaceAll(",", ";"); //catch cases where ppls use , as separator
				line = removeByteOrderMarks(line); //replace possible byte oder mark
				String[] lineData = line.split(";");
				//remove possible white spaces from all entries except the name
				for (int j = 0; j < lineData.length; j++) {
					if (j == 1) {
						continue;
					}
					lineData[j] = lineData[j].replaceAll(" ", "");
				}
				
				int blattNr = Integer.valueOf(lineData[0]);
				String name = lineData[1];
				boolean[] votes = new boolean[lineData.length - 2];
				for (int j = 0; j < votes.length; j++) {
					if (Integer.valueOf(lineData[j + 2]) == 0) {
						votes[j] = false;
					} else if (Integer.valueOf(lineData[j + 2]) == 1) {
						votes[j] = true;
					} else {
						System.out.println("[ERROR: ] reading votes in: \"" + csvFiles[i].getName() + "\"");
					}
				}
				
				StudentData studData = new StudentData(name, blattNr, votes);
				//add data:
				data.add(studData);
				//System.out.println("adding student: " + studData.getName());
				
				//close reader:
				csvReader.close();
			} 
			catch (FileNotFoundException e) {} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("[done adding students]");
		
		return data;
	}
	
	/**
	 * removes byte oder marks from UTF encodings
	 */
	private String removeByteOrderMarks(String input) {
		for (String curBOM : BOMs) {
			input = input.replaceAll(curBOM, "");
		}
		return input;
	}
}
