import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class SearchButtonListener implements ActionListener
{
	private TitleSorterView titleSorterView;
	private String userSearchInput;
	private ProductionList allTitles;
	private JTextField searchEntry;
	
	public SearchButtonListener(TitleSorterView titleSorterView, ProductionList titleSorterModel, JTextField searchEntry)
	{
		this.titleSorterView = titleSorterView;
		this.allTitles = titleSorterModel;
		this.searchEntry = searchEntry;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		userSearchInput = searchEntry.getText();
		
		if (userSearchInput != "" && titleSorterView.getSortType() == "Genre")
		{
			titleSorterView.setListToDisplay(titleSorterView.getAllTitles().sortGenre(userSearchInput).getProductionArray());
		}
		else if (userSearchInput != "" && titleSorterView.getSortType()== "Cast")
		{
			titleSorterView.setListToDisplay(titleSorterView.getAllTitles().sortCast(userSearchInput).getProductionArray());
		}
		else if (userSearchInput != "" && titleSorterView.getSortType() == "Director")
		{
			titleSorterView.setListToDisplay(titleSorterView.getAllTitles().sortDirector(userSearchInput).getProductionArray());
		}
		
		titleSorterView.updateUI();
	}

}
