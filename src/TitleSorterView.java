
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class TitleSorterView extends JFrame
{

	private final ProductionList allTitles;
	private Production[] listToDisplay;
	JTextField searchEntry = new JTextField();
	DefaultListModel<String> listModel = new DefaultListModel<String>();
	JList<String> listDisplay = new JList<String>();
	JButton searchButton = new JButton("Search");
	private String sortingType;

	public TitleSorterView(ProductionList model)
	{
		allTitles = model;
		listToDisplay = model.getProductionArray();

		// Setup the main frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 800);
		setTitle("Netflix Title Sorter");
		setLayout(new BorderLayout());
		setVisible(true);

		/*
		 * Creating the top information panel.
		 */

		// Instruction label for the search method drop-down
		JLabel sortLabel = new JLabel("Please select a search Method: ");

		// Create a combo box for selection of search method
		String[] sortMethods = { "All Titles" , "Movies", "TV-Shows", "Genre", "Director",
				"Cast"};
		JComboBox<String> sortMethod = new JComboBox<String>(sortMethods);
	
		SortMethodListener sortMethodListener = new SortMethodListener(this, sortMethod);
		sortMethod.addItemListener(sortMethodListener);

		searchEntry.setPreferredSize(new Dimension(350, 20));
		searchEntry.setText("Genre, Director or Cast Member to search for.");
		searchEntry.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				searchEntry.setText("");
			}
		});
		
			
		searchEntry.setEnabled(false);

		// Create a button to execute the search
		searchButton.setEnabled(false);
		
		searchButton.addActionListener(new SearchButtonListener(this, allTitles, searchEntry));

		// Add components to the top panel
		JPanel topPanel = new JPanel(new FlowLayout());
		topPanel.add(sortLabel);
		topPanel.add(sortMethod);
		topPanel.add(searchEntry);
		topPanel.add(searchButton);

		// Add the top panel to the north region of frame
		add(topPanel, BorderLayout.NORTH);
		
		/*
		 * Creating a Bottom-Panel for Production description and Programmer
		 * info
		 */

		JPanel bottomPanel = new JPanel(new BorderLayout());

		// Create a label for the programmer information.
		JLabel programmerName = new JLabel("Programmed By: Jordan Roby");
		programmerName.setFont(programmerName.getFont().deriveFont(Font.BOLD));
		programmerName.setHorizontalAlignment(JTextField.CENTER);

		JTextArea descriptionBox = new JTextArea(
				"Production information will be displayed here.");
		descriptionBox.setLineWrap(true);
		descriptionBox.setPreferredSize(new Dimension(800,200));

		bottomPanel.add(descriptionBox, BorderLayout.NORTH);
		bottomPanel.add(programmerName, BorderLayout.SOUTH);

		/*
		 * Creating a CenterPanel to display productions to the user.
		 */

		JPanel centerPanel = new JPanel();


		// Populate the model with the title of each production
		for (int i = 0; i < allTitles.getProductionArray().length; i++)
		{
			listModel.addElement(listToDisplay[i].getTitle());
		}

		// Add the model component to a JList to display title to the user
		listDisplay.setModel(listModel);
		listDisplay.setSelectedIndex(0);
		listDisplay.addListSelectionListener(new ListSelectionListener()
		{
			public void valueChanged(ListSelectionEvent e)
			{	
				if (!e.getValueIsAdjusting())
				{	
					int index = listDisplay.getSelectedIndex();
					if (index == -1)
					{
						listDisplay.setSelectedIndex(0);
					}
					else if (listToDisplay.length == 0)
					{
						JOptionPane.showMessageDialog(null, "No Titles Found.");
					}
					else if (index != -1 && listModel.size() > 0)
					{
					descriptionBox.setText(listToDisplay[index].getInfo());
					}
				}
			}
		});

		// Trying to figure out how to make it scroll
		listDisplay.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listDisplay.setLayoutOrientation(JList.VERTICAL);
		listDisplay.setVisibleRowCount(20);

		JScrollPane productionListScroller = new JScrollPane(listDisplay);
		productionListScroller.setPreferredSize(new Dimension(500, 400));

		centerPanel.add(productionListScroller);


		add(bottomPanel, BorderLayout.SOUTH);
		add(centerPanel, BorderLayout.CENTER);
		pack();

	}

	public static void main(String[] args)
	{
		new TitleSorterView(
				new ProductionList("netflix_titles_smallSample.csv", 100));
	}

	public void setListToDisplay(Production[] sortedList)
	{
		listToDisplay = sortedList;
	}

	public ProductionList getAllTitles()
	{
		return allTitles;
	}

	public void updateUI()
	{
		listDisplay.clearSelection();
		listModel.clear();
		for (int i = 0; i < listToDisplay.length; i++)
		{
			listModel.addElement(listToDisplay[i].getTitle());
		}
		// listDisplay.setModel(listModel);

	}

	public String getSortType()
	{
		
		return sortingType;
	}
	
	public void setSortType(String newSortType)
	{
		sortingType = newSortType;
	}
}
