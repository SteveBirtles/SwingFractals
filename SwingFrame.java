
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class SwingFrame extends JFrame 
{

    private JLabel statusbar;
    private static int oct, iterations;

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
        SwingPanel panel = new SwingPanel(this, oct, iterations);

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
        if (args.length == 0)
        {
            oct = 3;
            iterations = 50;
        }
        else
        {
            oct = Integer.parseInt(args[0]);
            if (args.length > 1)
            {
                iterations = Integer.parseInt(args[1]);
            }
        }

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