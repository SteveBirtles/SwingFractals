
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class SwingFrame extends JFrame 
{

    private JLabel statusbar;

    public SwingFrame() 
    {
        initUI();
    }

    private void initUI() 
    {
        this.setSize(1280, 1024);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setUndecorated(true);
        this.setDefaultCloseOperation(3);
        this.setTitle("Pi Swing Demo");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);       
        this.setVisible(true);

        statusbar = new JLabel("Ready to rock");
        SwingPanel panel = new SwingPanel(this);

        this.add(panel);
        this.add(statusbar, BorderLayout.SOUTH);

        panel.start();
    }

    public JLabel getStatusBar() 
    {
        return statusbar;
    }

    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(new Runnable() 
            {
                @Override
                public void run() 
                {
                    SwingFrame game = new SwingFrame();
                    game.setVisible(true);
                }
            });                
    } 
}