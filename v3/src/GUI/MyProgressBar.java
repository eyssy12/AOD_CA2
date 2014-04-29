package GUI;


import java.awt.Component;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Aidan
 */
public class MyProgressBar extends JProgressBar implements TableCellRenderer
{
    public MyProgressBar(int min, int max)
    {
        super(min, max);
        this.setOpaque(true);

        // display the % progress on the progressBar
        this.setStringPainted(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column)
    {
        setValue((int) ((Float) value).floatValue());
        return this;
    }
}
