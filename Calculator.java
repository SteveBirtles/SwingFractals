class Calculator extends Thread
{
    private int x0, y0, x1, y1, xx, yy;
    private int[][] pixel;  
    private int step, iterations;
    private double centreX, centreY, scale;

    public Calculator(int oct, int quad, int inpixel[][], int instep, int inI, double inX, double inY, double inS)
    {        

        pixel = inpixel;
        step = instep;
        iterations = inI;
        centreX = inX;
        centreY = inY;
        scale = inS;

        switch (quad)
        {
            case 1:     x0 = 0;     y0 = 0;     x1 = 640;   y1 = 512;   break;
            case 2:     x0 = 640;   y0 = 0;     x1 = 1280;  y1 = 512;   break;
            case 3:     x0 = 0;     y0 = 512;   x1 = 640;   y1 = 1024;  break;
            case 4:     x0 = 640;   y0 = 512;   x1 = 1280;  y1 = 1024;  break;
        }

        xx = x0;
        yy = y0;

        switch (oct)
        {

            case 0: x0 += -0.5 * 1280; y0 -= 0.5 * 1024;     x1 += -0.5 * 1280; y1 -= 0.5 * 1024;     break;

            case 1: x0 += -2 * 1280; y0 -= 1024;     x1 += -2 * 1280; y1 -= 1024;     break;
            case 3: x0 += -1 * 1280; y0 -= 1024;     x1 += -1 * 1280; y1 -= 1024;     break;
            case 5:                  y0 -= 1024;                      y1 -= 1024;     break;
            case 7: x0 += 1 * 1280;  y0 -= 1024;     x1 += 1 * 1280;  y1 -= 1024;     break;

            case 2: x0 += -2 * 1280;        x1 += -2 * 1280;    break;
            case 4: x0 += -1 * 1280;        x1 += -1 * 1280;    break;
            case 6:                                             break;
            case 8: x0 += 1 * 1280;         x1 += 1 * 1280;     break;
        }

    }

    public void run()
    {
        System.out.println("Started thread: (" + x0 + ", " + y0 + ")-(" + x1 + ", " + y1 + ")");

        for (double x = x0; x < x1; x += step)
        {
            for (double y = y0; y < y1; y += step)
            {

                if (pixel[(int) x - x0 + xx][(int) y - y0 + yy] < 0) continue;

                Complex z=new Complex(0,0);
                Complex c=new Complex(centreX + x / scale, centreY + y / scale);

                int i;
                for(i = 0; i < iterations && (z.getReal()<=2.0E+307||z.getImaginary()<=2.0E+307); i++){
                    z = (z.square()).plus(c);
                }              

                for (int u = 0; u < step; u++)
                {
                    for (int v = 0; v < step; v++)
                    {
                        if (u == 0 && v == 0) 
                            pixel[(int) x - x0 + u + xx][(int) y - y0 + v + yy] = -i * 6;
                        else
                            pixel[(int) x - x0 + u + xx][(int) y - y0 + v + yy] = i * 6;
                    }
                }

            }
        }

        System.out.println("Finished thread: (" + x0 + ", " + y0 + ")-(" + x1 + ", " + y1 + ")");

    }
}