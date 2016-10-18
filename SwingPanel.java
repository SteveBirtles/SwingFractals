import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.Random;

public class SwingPanel extends JPanel 
implements ActionListener {

    private Timer timer;
    private JLabel statusbar;
    private Random rnd;

    public SwingPanel(SwingFrame parent) 
    {
        initSwingPanel(parent);
        rnd = new Random();
    }

    private void initSwingPanel(SwingFrame parent) 
    {
        setFocusable(true);
        timer = new Timer(17, this);
        timer.start(); 

        statusbar =  parent.getStatusBar();
        addKeyListener(new TAdapter());       
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        repaint();
    }

    public void start()  
    {
        timer.start();
    }

    private void doDrawing(Graphics g) 
    {

        for (int i = 0; i < 1000; i++)
        {
            int x = rnd.nextInt(1280);
            int y = rnd.nextInt(1024);

            Color color = new Color(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            g.setColor(color);
            g.fillRect(x, y, rnd.nextInt(256), rnd.nextInt(256));
        }

    }

    @Override
    public void paintComponent(Graphics g) { 

        super.paintComponent(g);
        doDrawing(g);
    }

    class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int keycode = e.getKeyCode();

            if (keycode == 'q' || keycode == 'Q') {
                System.exit(0);
                return;
            }            
        }
    }
}