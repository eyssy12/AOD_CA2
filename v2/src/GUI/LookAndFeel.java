package GUI;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class LookAndFeel
{    
    public LookAndFeel(JFrame frame)
    {
        final UIManager.LookAndFeelInfo[] lookAndFeelInfo;
        lookAndFeelInfo = UIManager.getInstalledLookAndFeels();
        try
        {
            UIManager.setLookAndFeel("de.javasoft.plaf.synthetica.SyntheticaBlackStarLookAndFeel");
            UIManager.getCrossPlatformLookAndFeelClassName();
            // refresh all components to display with new Look and Feel 
            SwingUtilities.updateComponentTreeUI(frame);
        } 
        catch (Exception ex)
        {
            System.err.println("Error" + ex.getMessage());
        }
    }
}
