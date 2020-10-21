package csvDecoder.util;

/**
 * class for student data 
 */
public class StudentData {
	
	private int blattNr;
	private String name;
	private boolean[] votierungen;
	
	public StudentData(String name, int blattNr, boolean[] votierungen) {
		//error check:
		if (blattNr > 6 || blattNr < 1) {
			System.out.println("BlattNr bei Student " + name + " nicht passend");
		}
		//set attributes:
		this.blattNr = blattNr;
		this.name = name;
		this.votierungen = new boolean[votierungen.length];
		for (int i = 0; i < votierungen.length; i++) {
			this.votierungen[i] = votierungen[i];
		}
	}
	
	public String getName() {
		return name;
	}
	
	public int getBlattNr() {
		return blattNr;
	}
	
	public boolean[] getVotierungen() {
		return votierungen;
	}
	
}
