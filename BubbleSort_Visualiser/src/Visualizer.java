import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class Visualizer extends JPanel {
    private final int WIDTH = 1000, HEIGHT = 560;
    private final int SIZE = 200;
    private final float BAR_WIDTH = (float)WIDTH / SIZE;
    private float[] bar_height = new float[SIZE];
    public SwingWorker<Void,Void> shuffle,sort;


    private Visualizer() {
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        fillarray();
        shuffler();
        bubblesort();


    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(Color.PINK);
        Rectangle2D.Float bar;


        for(int i = 0; i < SIZE; i++) {
            bar = new Rectangle2D.Float(i * BAR_WIDTH, HEIGHT-bar_height[i], BAR_WIDTH, bar_height[i]);
            g2d.fill(bar);
        }

    }





    private void fillarray() {
        float step = (float)HEIGHT / SIZE;


        for(int i = 0; i < SIZE; i++)
            bar_height[i] = i * step;
        System.out.println(Arrays.toString(bar_height));
    }

    private void bubblesort(){

        sort = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {


                for(int i = 0; i<bar_height.length; i++){
                    for(int j = 0; j<bar_height.length-1; j++){

                        if(bar_height[j] > bar_height[j+1]){
                            float temp = bar_height[j];
                            bar_height[j] = bar_height[j+1];
                            bar_height[j+1] = temp;

                            Thread.sleep(1);
                            repaint();
                        }
                    }
                }


               return null;
            }


        };

    }

    private void shuffler(){

        shuffle = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {

               for(int i = 0; i<SIZE; i++){
                   Random rand = new Random();
                   int pos = rand.nextInt(SIZE);
                   float temp = bar_height[pos];
                   bar_height[pos] = bar_height[i];
                   bar_height[i] = temp;
                   Thread.sleep(4);
                    repaint();

               }

                return null;
            }

            @Override
            protected void done() {
                super.done();
                sort.execute();
            }
        };
        shuffle.execute();
    }



    public static void main(String args[]) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    JFrame frame = new JFrame("Bubble sort");
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setContentPane(new Visualizer());
                    frame.setResizable(false);
                    frame.setVisible(true);
                    frame.validate();
                    frame.pack();
                    frame.setLocationRelativeTo(null);
                }
            });


    }
}
