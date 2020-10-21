package csvDecoder;

public class TaskAmountMapping {
	
	public static final int BLATT_1 = 8;
	public static final int BLATT_2 = 6;
	public static final int BLATT_3 = 7;
	public static final int BLATT_4 = 7;
	public static final int BLATT_5 = 7; 
	public static final int BLATT_6 = 9; 
	
	public static int getAmountOfTasks(int blattNr) {
		switch (blattNr) {
		case 1: 
			return TaskAmountMapping.BLATT_1;
		case 2:
			return TaskAmountMapping.BLATT_2;
		case 3:
			return TaskAmountMapping.BLATT_3;
		case 4:
			return TaskAmountMapping.BLATT_4;
		case 5:
			return TaskAmountMapping.BLATT_5;
		case 6:
			return TaskAmountMapping.BLATT_6;
		default:
			return 0;
		}
	}
	
}
