package Utilities;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class GUIUtility
{
	public static void setLookAndFeel(JFrame frame)
    {
        try
        {
            UIManager.setLookAndFeel("de.javasoft.plaf.synthetica.SyntheticaBlackStarLookAndFeel");
            SwingUtilities.updateComponentTreeUI(frame);
        } 
        catch (Exception ex)
        {
            System.err.println("Error" + ex.getMessage());
        }
    }
	
	public static void setLookAndFeel(JPanel panel)
    {
        try
        {
            UIManager.setLookAndFeel("de.javasoft.plaf.synthetica.SyntheticaBlackStarLookAndFeel");
            SwingUtilities.updateComponentTreeUI(panel);
        } 
        catch (Exception ex)
        {
            System.err.println("Error" + ex.getMessage());
        }
    }
}
