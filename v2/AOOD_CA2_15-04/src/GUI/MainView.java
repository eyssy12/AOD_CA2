
package GUI;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Aidan
 */
public class MainView extends JFrame
{    
    private Controller controller;
    private JButton btnEnter;
    private JButton btnSelectPath;
    private JLabel lblUrl;
    private JLabel lblPathDisplay;
    private JScrollPane scrlPathDisplay;
    private JLabel lblThreadNo;
    private JPanel pnlManager;
    private JPanel pnlUrlInfo;
    private JScrollPane scrlFileDisplay;
    private JLabel lblHour;
    private JSpinner spnrHour;
    private JLabel lblMinute;
    private JSpinner spnrMinute;
    private JTextArea txtFileDisplay;
    private JTextField txtInput;
    private JPanel pnlThreadTable;
    private MyProgressBar myProgressBar;
    private JScrollPane scrlTable;
    private JTable statusTable;
    private ThreadTableModel tableModel;
    private JButton btnStop;
    
    public MainView()
    {
        this.pnlUrlInfo = new JPanel();
        this.txtInput = new JTextField();
        this.lblUrl = new JLabel();
        this.btnEnter = new JButton();
        this.btnSelectPath = new JButton();
        this.lblPathDisplay = new JLabel();
        this.scrlPathDisplay = new JScrollPane();
        this.scrlFileDisplay = new JScrollPane();
        this.txtFileDisplay = new JTextArea();
        this.pnlManager = new JPanel();
        this.lblHour = new JLabel();
        this.spnrHour = new JSpinner();
        this.lblMinute = new JLabel();
        this.spnrMinute = new JSpinner();
        this.lblThreadNo = new JLabel();
        this.pnlThreadTable = new JPanel();
        this.myProgressBar = new MyProgressBar(0, 100);
        this.scrlTable = new JScrollPane();
        this.statusTable = new JTable();
        this.tableModel = new ThreadTableModel();
        this.btnStop = new JButton();
        this.controller = new Controller(this);

        this.setTitle("Xml Web Source Downloader:");
        this.setSize(750, 500);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(null);

        this.pnlUrlInfo.setBorder(BorderFactory.createTitledBorder(null, "Xml Website URL Info:", 
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, 
                new Font("Tahoma", 0, 12), new Color(0, 0, 0)));
        this.pnlUrlInfo.setLayout(null);

        this.txtInput.setForeground(new java.awt.Color(102, 102, 102));
        this.txtInput.setText("Target URL");
        this.pnlUrlInfo.add(this.txtInput);
        this.txtInput.setBounds(110, 30, 180, 20);

        this.lblUrl.setText("Enter target URL:");
        this.pnlUrlInfo.add(this.lblUrl);
        this.lblUrl.setBounds(10, 30, 100, 20);

        this.btnEnter.setText("Enter");
        this.btnEnter.addActionListener(this.controller);
        
        this.pnlUrlInfo.add(this.btnEnter);
        this.btnEnter.setBounds(60, 70, 80, 23);
        
        this.btnSelectPath.setText("Choose Path");
        this.btnSelectPath.addActionListener(this.controller);
        
        this.pnlUrlInfo.add(this.btnSelectPath);
        this.btnSelectPath.setBounds(10, 110, 120, 23);
        
        this.pnlUrlInfo.add(this.scrlPathDisplay);
        this.scrlPathDisplay.setViewportView(this.lblPathDisplay);
        this.scrlPathDisplay.setBorder(BorderFactory.createTitledBorder(null, "Chosen Path:", 
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, 
                new Font("Tahoma", 0, 12), new java.awt.Color(0, 0, 0)));
        this.scrlPathDisplay.setBounds(150, 80, 220, 60);

        this.txtFileDisplay.setEditable(false);
        this.txtFileDisplay.setColumns(20);
        this.txtFileDisplay.setFont(new Font("Tahoma", 0, 11));
        this.txtFileDisplay.setRows(5);
        this.txtFileDisplay.setBorder(BorderFactory.createTitledBorder(null,
                "All Website XML Links:", TitledBorder.DEFAULT_JUSTIFICATION, 
                TitledBorder.DEFAULT_POSITION, new Font("Tahoma", 0, 12),
                new Color(0, 0, 0)));
        this.txtFileDisplay.setDisabledTextColor(new Color(0, 0, 0));
        this.scrlFileDisplay.setViewportView(this.txtFileDisplay);

        this.pnlUrlInfo.add(this.scrlFileDisplay);
        this.scrlFileDisplay.setBounds(390, 20, 290, 120);

        getContentPane().add(this.pnlUrlInfo);
        this.pnlUrlInfo.setBounds(30, 10, 690, 150);

        /*----------------Thread Manager Panel--------------------*/
        this.pnlManager.setBorder(BorderFactory.createTitledBorder(null, "Thread Manager:",
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
                new Font("Tahoma", 0, 12), new Color(0, 0, 0)));
        this.pnlManager.setLayout(null);
        
        this.lblHour.setText("Hour(s)");
        this.pnlManager.add(this.lblHour);
        this.lblHour.setBounds(70, 70, 50, 20);
        this.pnlManager.add(this.spnrHour);
        this.spnrHour.setBounds(20, 70, 29, 20);
        
        this.lblMinute.setText("Minute(s)");
        this.pnlManager.add(this.lblMinute);
        this.lblMinute.setBounds(70, 110, 60, 20);
        this.pnlManager.add(this.spnrMinute);
        this.spnrMinute.setBounds(20, 110, 29, 20);

        this.lblThreadNo.setText("Download Time Details:");
        this.pnlManager.add(this.lblThreadNo);
        this.lblThreadNo.setBounds(16, 31, 150, 14);
        
        getContentPane().add(this.pnlManager);
        this.pnlManager.setBounds(10, 190, 150, 200);
        
        /*----------------Thread Table Panel--------------------*/
        this.statusTable.setDefaultRenderer(MyProgressBar.class, this.myProgressBar);
        this.statusTable.setRowHeight((int) this.myProgressBar.getPreferredSize().getHeight());
        
        this.pnlThreadTable.setBorder(BorderFactory.createTitledBorder(null, "Downloads:", 
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, 
                new Font("Tahoma", 0, 12), new Color(0, 0, 0)));
        this.pnlThreadTable.setLayout(null);
        
        this.btnStop.setText("Stop Downloads");
        this.btnStop.addActionListener(this.controller);
        
        this.pnlThreadTable.add(this.btnStop);
        this.btnStop.setBounds(170, 230, 150, 25);
        
        this.statusTable.setModel(this.tableModel);
        this.scrlTable.setViewportView(this.statusTable);
        
        this.pnlThreadTable.add(this.scrlTable);
        this.scrlTable.setBounds(14, 17, 480, 207);
        
        getContentPane().add(this.pnlThreadTable);
        this.pnlThreadTable.setBounds(180, 170, 500, 230);
    }
    
    public JTextArea getTxtFileDisplay()
    {
        return this.txtFileDisplay;
    }

    public JTextField getTxtInput()
    {
        return this.txtInput;
    }

    public JButton getBtnEnter()
    {
        return this.btnEnter;
    }
    
    public JButton getBtnSelectPath()
    {
        return this.btnSelectPath;
    }

    public JLabel getLblPathDisplay()
    {
        return this.lblPathDisplay;
    }

    public ThreadTableModel getTableModel()
    {
        return this.tableModel;
    }

    public MyProgressBar getMyProgressBar() 
    {
        return this.myProgressBar;
    }
    
    public JButton getBtnStop()
    {
        return this.btnStop;
    }
}
