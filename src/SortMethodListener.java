import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;

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
 *         Responsibilities of class: Handles events from our sortMethod
 *         ComboBox to sort ProductionList if possible, and if not enables
 *         widgets for user to input additional search criteria to complete the
 *         sorting.
 */
public class SortMethodListener implements ItemListener
{
	private TitleSorterView titleSorterView;
	private JComboBox<String> sortMethod;

	public SortMethodListener(TitleSorterView titleSorterView,
			JComboBox<String> sortMethod)
	{
		this.titleSorterView = titleSorterView;
		this.sortMethod = sortMethod;
	}

	@Override
	/**
	 * Purpose: Determine the sorting choice from sortMethod ComboBox. If
	 * possible, perform the selected sort method and set the listToDisplay to
	 * our newly
	 * sorted list.
	 * 
	 * For Genre, Cast, and Director, additional input is needed to perform the
	 * sorting. If these options are selected, enable our searchEntry TextField
	 * and
	 * the searchButton to prompt user to enter the needed criteria.
	 */
	public void itemStateChanged(ItemEvent e)
	{
		if (e.getStateChange() == ItemEvent.SELECTED)
		{

			// Sets the sortType to the text currently selected in the ComboBox
			String sortType = sortMethod.getSelectedItem().toString();

			// Sets the sortType field from our titleSorterView (passed to our
			// SearchButtonListener if additional input is needed for the
			// sorting)
			titleSorterView.setSortType(sortType);

			// If All Titles is selected, listToDisplay is allTitles field
			if (sortType.equals("All Titles"))
			{
				titleSorterView.setListToDisplay(
						titleSorterView.getAllTitles().getProductionArray());
				titleSorterView.searchEntry.setEnabled(false);
				titleSorterView.searchButton.setEnabled(false);
			}

			// If Movies is selected. Call .sortMovies from Production list and
			// the listToDisplay as an array of the sorted productions.
			if (sortType.equals("Movies"))
			{
				titleSorterView.setListToDisplay(titleSorterView.getAllTitles()
						.sortMovies().getProductionArray());
				titleSorterView.searchEntry.setEnabled(false);
				titleSorterView.searchButton.setEnabled(false);
			}
			// If TV-Shows is selected. Call .sortTvShows from ProductionList
			// ans set the listToDisplay field as an array of the newly sorted
			// productions.
			if (sortType.equals("TV-Shows"))
			{
				titleSorterView.setListToDisplay(titleSorterView.getAllTitles()
						.sortTVShows().getProductionArray());
				titleSorterView.searchEntry.setEnabled(false);
				titleSorterView.searchButton.setEnabled(false);
			}
			// If Genre, Cast, or Director is selected additional input is
			// needed. Enable the widgets for the user to enter more
			// information.
			if (sortType.equals("Genre") || sortType.equals("Cast")
					|| sortType.equals("Director"))
			{
				titleSorterView.searchEntry.setEnabled(true);
				titleSorterView.searchButton.setEnabled(true);
			}
		}

		// Call updateUI to reflect these changes to the user
		titleSorterView.updateUI();
	}

}
