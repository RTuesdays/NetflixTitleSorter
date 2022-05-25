import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

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
 *         Responsibilities of class: Pulls information from sortMethod ComboBox
 *         and searchEntry TextField to perform a search by genre, cast member,
 *         or director. Sets the listToDisplay in TitleSorterView as the sorted
 *         list.
 */
public class SearchButtonListener implements ActionListener
{
	private TitleSorterView titleSorterView;
	private String userSearchInput;
	private ProductionList allTitles;
	private JTextField searchEntry;

	public SearchButtonListener(TitleSorterView titleSorterView,
			ProductionList titleSorterModel, JTextField searchEntry)
	{
		this.titleSorterView = titleSorterView;
		this.allTitles = titleSorterModel;
		this.searchEntry = searchEntry;
	}

	@Override
	/**
	 * Purpose: Whenever our searchButton is pressed, performs sorting methods
	 * from
	 * all the information selected.
	 */
	public void actionPerformed(ActionEvent e)
	{
		// Set the SearchInput as whatever was entered into the TextField
		userSearchInput = searchEntry.getText();

		// If the user did not input search criteria, show a message prompt
		if (userSearchInput.equals(""))
		{
			JOptionPane.showMessageDialog(null,
					"Please enter a Genre, Cast Member, or Director to search for.");
		}

		// If genre is selected as the searchMethod, call the sortGenre method
		// from ProductionList with text entered by the user as a parameter. Set
		// listToDisplay as the sorted list.
		else if (titleSorterView.getSortType().equals("Genre"))
		{
			titleSorterView.setListToDisplay(titleSorterView.getAllTitles()
					.sortGenre(userSearchInput).getProductionArray());
		}
		// If cast is selected as the searchMethod, call the sortCast method
		// from ProductionList with the text entered by the user as a parameter,
		// update listToDisplay.
		else if (titleSorterView.getSortType().equals("Cast"))
		{
			titleSorterView.setListToDisplay(titleSorterView.getAllTitles()
					.sortCast(userSearchInput).getProductionArray());
		}
		// If director is selected as the searchMethod, call sortDirector with
		// text entered by the user as a parameter, update listToDisplay.
		else if (titleSorterView.getSortType().equals("Director"))
		{
			titleSorterView.setListToDisplay(titleSorterView.getAllTitles()
					.sortDirector(userSearchInput).getProductionArray());
		}

		// Call updateUI to reflect changes to the list in our GUI.
		titleSorterView.updateUI();
	}

}
