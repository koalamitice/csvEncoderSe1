package csvDecoder.gui;

import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import csvDecoder.TaskAmountMapping;
import csvDecoder.util.StudentData;

public class Gui {
	
	private Device device = Display.getCurrent ();
	private Color green = new Color(device, 0, 204, 102);
	private Color red = new Color(device, 255, 122, 122);
	private Color blue = new Color(device, 52, 122, 235);
	
	private Shell parentShell;
	private Table table;
	private int taskNumber = 0;
	private int exercise = 0;
	private Set<StudentData> studentData;
	
	public Gui(Shell parent, int exercise) {
		parentShell = parent;
		this.taskNumber = TaskAmountMapping.getAmountOfTasks(exercise);
		this.exercise = exercise;
		createLayout();
	}
	
	private void createLayout() {
		parentShell.setLayout(new GridLayout(1, false));
		
		//Export Button:
//		Button exportButton = new Button(parentShell, SWT.NONE);
//		exportButton.addListener(SWT.Selection, new Listener() {
//			@Override
//			public void handleEvent(Event event) {
//				DirectoryDialog dd = new DirectoryDialog(new Shell());
//				String path = dd.open();
//				if (path == null) {
//					return;
//				}
//				try {
//					new CsvWriter().exportCsv(studentData, path);
//				} catch (FileNotFoundException e) {
//					e.printStackTrace();
//				}
//			}
//		});
//		GridData buttonData = new GridData(SWT.FILL, SWT.NONE, true, false);
//		exportButton.setLayoutData(buttonData);
//		exportButton.setText("export");
		
		//student table:
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		table = new Table(parentShell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(data);
		
		table.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				table.deselectAll();
			}
		});
		
		String[] colNames = new String[3 + taskNumber];
		colNames[0] = "Pos.";
		colNames[1] = "Blatt";
		colNames[2] = "Studenten Name";
		for (int i = 0; i < taskNumber; i++) {
			int AufgabenNr = i + 1;
			colNames[i + 3] = "Aufgabe " + AufgabenNr;
		}
		
		for (int i = 0; i < colNames.length; i++) {
			TableColumn tc = new TableColumn(table, SWT.BORDER);
			tc.setText(colNames[i]);
			tc.pack();
		}
	}
	
	public void fillTable(Set<StudentData> data) {
		this.studentData = data;
		int counter = 0;
		for (StudentData curData : data) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(0, String.valueOf(counter));
			item.setText(1, String.valueOf(curData.getBlattNr()));
			if (curData.getBlattNr() != exercise) {
				item.setBackground(0, blue);
			}
			item.setText(2, curData.getName());
			
			for (int i = 0; i < taskNumber; i++) {
				if (i >= curData.getVotierungen().length) {
					continue;
				}
				item.setText(i + 3, String.valueOf(curData.getVotierungen()[i]));
				if (curData.getVotierungen()[i] == true) {
					item.setBackground(i + 3, green);
				} else {
					item.setBackground(i + 3, red);
				}
			}
			counter++;
		}
		
		System.out.println("[table filled]");
	}
	
	public void setTaskNumber (int excercise) {
		if (excercise < 1 || excercise > 6) {
			System.out.println("[Error] excercise number lower 1 or higher 6: [=" + excercise + "]");
			System.out.println("---> calling System.exit");
			System.exit(0);
		}
		this.taskNumber = TaskAmountMapping.getAmountOfTasks(excercise);
	}
}
