package csvDecoder.util;

import java.util.Comparator;

public class StudentComparator implements Comparator<StudentData> {

	@Override
	public int compare(StudentData arg0, StudentData arg1) {
		return arg0.getName().compareTo(arg1.getName());
	}

}
