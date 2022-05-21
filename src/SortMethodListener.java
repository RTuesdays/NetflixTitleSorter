import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;

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
	public void itemStateChanged(ItemEvent e)
	{
		if (e.getStateChange() == ItemEvent.SELECTED)
		{
			String sortType = sortMethod.getSelectedItem().toString();
			
			titleSorterView.setSortType(sortType);

			if (sortType == "All Titles")
			{
				titleSorterView.setListToDisplay(titleSorterView.getAllTitles().getProductionArray());
				titleSorterView.searchEntry.setEnabled(false);
				titleSorterView.searchButton.setEnabled(false);
			}

			if (sortType == "Movies")
			{
				titleSorterView.setListToDisplay(titleSorterView.getAllTitles()
						.sortMovies().getProductionArray());
				titleSorterView.searchEntry.setEnabled(false);
				titleSorterView.searchButton.setEnabled(false);
			}
			if (sortType == "TV-Shows")
			{
				titleSorterView.setListToDisplay(titleSorterView.getAllTitles()
						.sortTVShows().getProductionArray());
				titleSorterView.searchEntry.setEnabled(false);
				titleSorterView.searchButton.setEnabled(false);
			}
			if (sortType == "Genre" || sortType == "Cast"
					|| sortType == "Director" || sortType == "Release Year")
			{
				titleSorterView.searchEntry.setEnabled(true);
				titleSorterView.searchButton.setEnabled(true);
			}
		}

		titleSorterView.updateUI();
	}

}
