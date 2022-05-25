
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

/**
 * Lead Author(s):
 * 
 * @author Jordan Roby, 5550070367
 * 
 *         References:
 *         Morelli, R., & Walde, R. (2016). Java, Java, Java: Object-Oriented
 *         Problem Solving.
 *         Retrieved from
 *         https://open.umn.edu/opentextbooks/textbooks/java-java-java-object-oriented-problem-solving
 * 
 *         Version/date: v2 25 May 2022
 * 
 *         Responsibilities of class: Create a GUI to display ProductionList to
 *         the user. Handle events to sort through the ProductionList and
 *         display the sorted list to the user.
 */
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

		// Setup the main window
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 800);
		setTitle("Netflix Title Sorter");
		setLayout(new BorderLayout());
		setVisible(true);

		/*
		 * Creating a panel for information. Will be placed in
		 * BorderLayour.North
		 * This panel will contain a Label, ComboBox, TextField, and Button.
		 */
		JPanel topPanel = new JPanel(new FlowLayout());

		// Instruction label for the search method drop-down
		JLabel sortLabel = new JLabel("Please select a search Method: ");

		// Create a combo box for a drop-down selection of search methods
		String[] sortMethods = { "All Titles", "Movies", "TV-Shows", "Genre",
				"Director", "Cast" };
		JComboBox<String> sortMethod = new JComboBox<String>(sortMethods);

		// Create a new Item Listener to retrieve the selection from the
		// ComboBox
		SortMethodListener sortMethodListener = new SortMethodListener(this,
				sortMethod);
		sortMethod.addItemListener(sortMethodListener);

		/*
		 * Format the searchEntry JTextField to make it cleaner for the user.
		 * setPreferredSize will prevent the text field from resizing after the
		 * text is
		 * changed.
		 * Add a mouseListener so that the we can clear the text automatically
		 * when the
		 * user clicks on the textField. This makes it so the user doesn't have
		 * to
		 * manually clear out the instructions/ entry each time.
		 */
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

		/*
		 * TextField and Button are disable until a searchMethod that needs them
		 * is
		 * selected.
		 * Our SortMethodListener will enable these widgets should they be
		 * needed.
		 */
		searchEntry.setEnabled(false);
		searchButton.setEnabled(false);

		// Create and add listener with parameters (TitleSorterview,
		// ProductionList,
		// JTextfield)
		searchButton.addActionListener(
				new SearchButtonListener(this, allTitles, searchEntry));

		// Add components to the top panel
		topPanel.add(sortLabel);
		topPanel.add(sortMethod);
		topPanel.add(searchEntry);
		topPanel.add(searchButton);

		// Add the top panel to the north region of frame
		add(topPanel, BorderLayout.NORTH);

		/*
		 * Creating a Bottom-Panel for Production description and Programmer
		 * info.
		 * This panel will contain a Label for programmer Info, and a TextArea
		 * for title
		 * descriptions. Panel is in BorderLayout to "stack" components in North
		 * and
		 * South regions.
		 */
		JPanel bottomPanel = new JPanel(new BorderLayout());

		// Create and format a label for the programmer information.
		JLabel programmerName = new JLabel("Programmed By: Jordan Roby");
		programmerName.setFont(programmerName.getFont().deriveFont(Font.BOLD));
		programmerName.setHorizontalAlignment(JTextField.CENTER);

		// Make a new text area to hold production information when selected
		// from our
		// list.
		JTextArea descriptionBox = new JTextArea(
				"Production information will be displayed here.");
		// Wraps text to prevent scrolling if text is longer than our width.
		descriptionBox.setLineWrap(true);
		// Set size to TextArea box doesnt change with each production
		descriptionBox.setPreferredSize(new Dimension(800, 200));

		// Add the components to our bottom panel
		bottomPanel.add(descriptionBox, BorderLayout.NORTH);
		bottomPanel.add(programmerName, BorderLayout.SOUTH);

		// Add the bottomPanel to the south region of our frame
		add(bottomPanel, BorderLayout.SOUTH);

		/*
		 * Creating a CenterPanel to display productions to the user.
		 */
		JPanel centerPanel = new JPanel();

		// Populate the model with the title of each production in the
		// listToDisplay
		for (int i = 0; i < allTitles.getProductionArray().length; i++)
		{
			listModel.addElement(listToDisplay[i].getTitle());
		}

		// Add the model component to a JList to display titles to the user
		listDisplay.setModel(listModel);
		// Default selection will be the first title of the list
		listDisplay.setSelectedIndex(0);
		// Add a listener to get the index of the selected element
		listDisplay.addListSelectionListener(new ListSelectionListener()
		{
			public void valueChanged(ListSelectionEvent e)
			{
				if (!e.getValueIsAdjusting())
				{
					int index = listDisplay.getSelectedIndex();
					// If the list is empty, display a dialogue box
					if (listToDisplay.length == 0)
					{
						JOptionPane.showMessageDialog(null, "No Titles Found.");
					}
					// If there are titles in the list, set the descriptionBox
					// to the Info of the
					// Production at the selected index
					else if (index != -1 && listModel.size() > 0)
					{
						descriptionBox.setText(listToDisplay[index].getInfo());
					}
				}
			}
		});

		// Format the list to make it cleaner and easier to navigate
		listDisplay.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listDisplay.setLayoutOrientation(JList.VERTICAL);
		listDisplay.setVisibleRowCount(20);

		// Create a scroll pane to enable scrolling through the list
		JScrollPane productionListScroller = new JScrollPane(listDisplay);
		productionListScroller.setPreferredSize(new Dimension(500, 400));

		// Add component to our centerPanel.
		centerPanel.add(productionListScroller);

		// Add the center panel to the center region of the frame
		add(centerPanel, BorderLayout.CENTER);
		pack();

	}

	public static void main(String[] args)
	{
		new TitleSorterView(
				new ProductionList("netflix_titles_smallSample.csv", 100));
	}

	/**
	 * Purpose: Setter to change the listToDisplay (titles being displayed in
	 * the
	 * JList) whenever sort methods are selected to sort through our list of
	 * titles.
	 * 
	 * @param sortedList, the list after sorting methods have been selected
	 */
	public void setListToDisplay(Production[] sortedList)
	{
		listToDisplay = sortedList;
	}

	/**
	 * Purpose: Getter for the allTitles list, the list of allTitles is used to
	 * display allTitles, and is the default list to be passed to sorting
	 * methods.
	 * 
	 * @return allTitles
	 */
	public ProductionList getAllTitles()
	{
		return allTitles;
	}

	/**
	 * Purpose: Updates the Productions being displayed in the in our JList.
	 */
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

	/**
	 * Purpose: Getter for the sortingType, sortingType is updated by the
	 * ComboBox
	 * listener whenever a new option from the ComboBox is selected.
	 * 
	 * @return sortingType, a string representing the selection of the
	 *         sortMethod
	 *         ComboBox
	 */
	public String getSortType()
	{
		return sortingType;
	}

	/**
	 * Purpose: Called by the SortMethodListener to change the value whenever a
	 * new
	 * selection is made from the sortMethod comboBox
	 * 
	 * @param newSortType
	 */
	public void setSortType(String newSortType)
	{
		sortingType = newSortType;
	}
}
