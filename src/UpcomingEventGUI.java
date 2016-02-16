import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JLabel;


public class UpcomingEventGUI extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JTable table;
	private JTable table1;
	private DefaultTableModel nrTable;
	private LinkedList<Event> urList;
	
	
	
	public void actionPerformed(ActionEvent e) {
		int action = Integer.parseInt(e.getActionCommand());
		switch (action) {
		case 0: //Add Event
			AddEventGUI newAE=new AddEventGUI();
			newAE.openAE();
			break;
		case 1: //Change Date
			int rowSelected = table1.getSelectedRow();
			int selectedDate = urList.get(rowSelected).getRemindID();
			String selectedName = urList.get(rowSelected).getEventName();
			System.out.println(selectedName+selectedDate);
			int dateValue = urList.get(rowSelected).getEventDate().getID();
			ChangeDateGUI newCD=new ChangeDateGUI();
			newCD.openCD();
			System.out.println(0);
			break;

		}
		
	}
	/**
	 * Launch the application.
	 */
	public static void openUE() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UpcomingEventGUI frame = new UpcomingEventGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public UpcomingEventGUI() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 392);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		
		EventList eTable = new EventList();
		eTable.readFile();
		
		//Create TableModel
		String[] col1 = {"Date","Name","Type"};
		nrTable = new DefaultTableModel(col1,0);
		
		//temp variables to populate Table
		EventDate tempDate = new EventDate();
		String tempDateName = "Jan 1 - 2015";
		String tempName = "Mario";
		String tempType = "Game";
		
		//compare to today
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar today = Calendar.getInstance();
		int todayID = Integer.valueOf(sdf.format(today.getTime()));
		
		//usrList will be an EventList with only upcoming release titles
		urList = new LinkedList<Event>();
		
		//weeds out input file Events that have already released
		for (int i = 0; i <eTable.getList().size();i++) {
			Event tempEvent = new Event();
			tempEvent = eTable.getList().get(i);
			int idCheck = tempEvent.getRemindID();
			if(idCheck > todayID) {
				tempDate = tempEvent.getEventDate();
				tempDateName = tempDate.toString();
				tempName = tempEvent.getEventName();
				tempType = tempEvent.getEventTypeName();
				Object[] data = {tempDateName, tempName, tempType};
				urList.add(tempEvent);
				nrTable.addRow(data);
			}
		}
		
		table1 = new JTable(nrTable);
		table1.setFont(new Font("Arial", Font.PLAIN, 10));
		table1.setBackground(new Color(255, 255, 255));
		JScrollPane tableContainer = new JScrollPane(table1);
		
		
		
		tableContainer.setBounds(10, 23, 414, 228);
		contentPane.add(tableContainer);
		
		JButton btnEditEvent = new JButton("Change Date");
		btnEditEvent.addActionListener(this);
		btnEditEvent.setActionCommand("1");
		btnEditEvent.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnEditEvent.setBounds(163, 278, 89, 23);
		contentPane.add(btnEditEvent);
		
		JButton btnAddEvent = new JButton("Add Event");
		btnAddEvent.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnAddEvent.addActionListener(this);
		btnAddEvent.setActionCommand("0");
		btnAddEvent.setBounds(34, 278, 89, 23);
		contentPane.add(btnAddEvent);
		
		JButton btnRemoveEvent = new JButton("Remove Event");
		btnRemoveEvent.addActionListener(this);
		btnRemoveEvent.setActionCommand("2");
		btnRemoveEvent.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnRemoveEvent.setBounds(302, 278, 110, 23);
		contentPane.add(btnRemoveEvent);
		
		JLabel lblUpcomingReleases = new JLabel("Upcoming Releases");
		lblUpcomingReleases.setBounds(163, -2, 137, 14);
		contentPane.add(lblUpcomingReleases);
	}
}
