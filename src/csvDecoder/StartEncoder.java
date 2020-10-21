package csvDecoder;

import java.util.Set;

import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import csvDecoder.gui.Gui;
import csvDecoder.reader.CsvReader;
import csvDecoder.util.StudentData;

public class StartEncoder {
	
	private static int numberOfExercises = 6;
	
	public static void main (String[] args) {
		//create Shell:
		Display display = new Display();
		Shell shell = new Shell(display);
		
		//read data:
		DirectoryDialog dd = new DirectoryDialog(new Shell());
		String path = dd.open();
		if (path == null) {
			System.out.println("[ERROR] selected path not found");
			System.out.println("---> calling System.exit");
			System.exit(0);
		}
		Set<StudentData> data = new CsvReader().readCsvFiles(path);
		
		//fill Gui:
		Gui gui = new Gui(shell, getExerciseNumber(data));
		gui.fillTable(data);
		
		//open gui:
		shell.open();
		System.out.println("[gui openend]");
		
		//keep open:
		while (!shell.isDisposed()) {
		    if (!display.readAndDispatch())
		     {
		        display.sleep();
		     }
		}
		display.dispose();
	}
	
	/**
	 * returns the exercise number that was chosen the most in the csv files
	 * => taking only one or two csv in advantage here could results in unwanted behavior when students make erros
	 */
	private static int getExerciseNumber(Set<StudentData> dataSet) {
		//count which exercise was selected most:
		int[] choosedExerciseCounter = new int[numberOfExercises]; 
		for (StudentData data : dataSet) {
			if (data.getBlattNr() < 0 || data.getBlattNr() > numberOfExercises) {
				System.out.println("[ERROR:] student " + data.getName() + " selected a wrong exercise number");
				continue;
			}
			choosedExerciseCounter[data.getBlattNr() - 1]++;
		}
		
		//get exercise that was selected most:
		int max = -1;
		int exercise = -1;
		for (int i = 0; i < choosedExerciseCounter.length; i++) {
			if (choosedExerciseCounter[i] > max) {
				max = choosedExerciseCounter[i];
				exercise = i + 1;
			}
		}
		
		return exercise;
	}
	
}
