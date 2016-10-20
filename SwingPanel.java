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
import javax.imageio.*;
import java.util.Random;
import java.awt.image.BufferedImage;
import java.io.File;

public class SwingPanel extends JPanel 
implements ActionListener {

    private Timer timer;
    private JLabel statusbar;
    private Random rnd;
    private int step = 128;
    private int oct, iterations;
    private double centreX, centreY, scale;

    public int pixel[][] = new int[1280][1024];

    public SwingPanel(SwingFrame parent, int inO, int inI, double inX, double inY, double inS) 
    {
        initSwingPanel(parent);
        rnd = new Random();
        oct = inO;
        iterations = inI;
        centreX = inX;
        centreY = inY;
        scale = inS;
    }

    private void initSwingPanel(SwingFrame parent) 
    {
        setFocusable(true);
        timer = new Timer(0, this);
        timer.start(); 

        statusbar =  parent.getStatusBar();
        addKeyListener(new TAdapter());       
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        Thread quarter1 = new Calculator(oct, 1, pixel, step, iterations, centreX, centreY, scale);
        Thread quarter2 = new Calculator(oct, 2, pixel, step, iterations, centreX, centreY, scale);
        Thread quarter3 = new Calculator(oct, 3, pixel, step, iterations, centreX, centreY, scale);
        Thread quarter4 = new Calculator(oct, 4, pixel, step, iterations, centreX, centreY, scale);

        quarter1.start();
        quarter2.start();
        quarter3.start();
        quarter4.start();
        try
        {
            quarter1.join();
            quarter2.join();
            quarter3.join();
            quarter4.join();
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }

	double x0 = centreX;
	double y0 = centreY;
	
        switch (oct)
        {

            case 0: x0 += -0.5 * 1280 / scale;     y0 -= 0.5 * 1024 / scale;    break;

            case 1: x0 += -2 * 1280 / scale; y0 -= 1024 / scale;   break;
            case 3: x0 += -1 * 1280 / scale; y0 -= 1024 / scale;   break;
            case 5:                          y0 -= 1024 / scale;   break;
            case 7: x0 += 1 * 1280 / scale;  y0 -= 1024 / scale;   break;

            case 2: x0 += -2 * 1280 / scale;       break;
            case 4: x0 += -1 * 1280 / scale;       break;
            case 6:                                break;
            case 8: x0 += 1 * 1280/ scale;         break;
        }

	double x1 = x0 + 1280 / scale;
	double y1 = y0 + 1024 / scale;
	
        statusbar.setText("Calculating (with " + iterations + " iterations) " + (int) (100.0 / 128 * 1/((double) step / 128)) + "% "
		+ " ("+ x0 + ", " + y0 + ")-(" + x1, + ", " + y1 + ")");

        if (step > 1)
        {
            step /= 2;
        }
        else
        {        
            timer.stop();
            statusbar.setVisible(false);
            System.out.println("* * * Processing Complete * * *");
        }

        repaint();

    }

    public void start()  
    {
        timer.start();
    }

    private void doDrawing(Graphics g) 
    {       

        System.out.print("Drawing image...");

        Color color;
        BufferedImage image = new BufferedImage(1280, 1024, BufferedImage.TYPE_INT_ARGB);

        for (int x = 0; x < 1280; x++)
        {
            for (int y = 0; y < 1024; y++)
            {
                double z = Math.abs((double) pixel[x][y] / (double) iterations);
                if (z < 1)      {           color = new Color((int) (256 * z), 0, 0); }
                else if (z < 2) {z -= 1;    color = new Color(255, (int) (255 * z), 0);}
                else if (z < 3) {z -= 2;    color = new Color((int) (255 * (1 - z)), 255, 0);}
                else if (z < 4) {z -= 3;    color = new Color(0, 255, (int) (255 * z));}
                else if (z < 5) {z -= 4;    color = new Color(0, (int) (255 * (1 - z)), 255);}
                else {z -= 5;    color = new Color(0, 0, (int) (255 * (1 - z)));}                          

                image.setRGB(x, y, color.getRGB());                
            }
        }

        g.drawImage(image, 0, 0, this);

        System.out.println(" done.");

	if (step == 1) 
	{
		try
		{
			File outputfile = new File(oct + ".png");
			ImageIO.write(image, "png", outputfile);
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage());
		}
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
