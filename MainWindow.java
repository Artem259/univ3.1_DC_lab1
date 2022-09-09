import javax.swing.*;
import java.awt.*;

public class MainWindow extends JDialog {
    private Thread thread1, thread2;
    MySemaphore semaphore = new MySemaphore();
    private int counter;
    private JPanel contentPane;
    private JSlider slider;
    private JButton startButton1;
    private JButton startButton2;
    private JButton stopButton1;
    private JButton stopButton2;
    private JProgressBar progressBar;

    public MainWindow() {
        JFrame win = new JFrame();
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setSize(500,300);
        setModal(true);

        startButton1.addActionListener(e -> {
            if(semaphore.isLocked())
                return;
            semaphore.lock();

            thread1 = new Thread(
                    () -> {
                        while (true) {
                            if(counter>10)
                                counter--;
                            slider.setValue(counter);
                            try {
                                Thread.sleep(5);
                            } catch (InterruptedException ex) {
                                System.out.println("Thread1 is interrupted");
                                break;
                            }
                        }
                    }
            );
            thread1.setPriority(Thread.MIN_PRIORITY);

            startButton1.setEnabled(false);
            stopButton2.setEnabled(false);
            progressBar.setForeground(new Color(255,0,0));
            thread1.start();
        });
        startButton2.addActionListener(e -> {
            if(semaphore.isLocked())
                return;
            semaphore.lock();

            thread2 = new Thread(
                    () -> {
                        while (true) {
                            if(counter<90)
                                counter++;
                            slider.setValue(counter);
                            try {
                                Thread.sleep(5);
                            } catch (InterruptedException ex) {
                                System.out.println("Thread2 is interrupted");
                                break;
                            }
                        }
                    }
            );
            thread2.setPriority(Thread.MAX_PRIORITY);

            startButton2.setEnabled(false);
            stopButton1.setEnabled(false);
            progressBar.setForeground(new Color(255,0,0));
            thread2.start();
        });

        stopButton1.addActionListener(e -> {
            if(semaphore.isFree())
                return;

            startButton1.setEnabled(true);
            stopButton2.setEnabled(true);
            progressBar.setForeground(new Color(0,255,0));
            thread1.interrupt();
            semaphore.free();
        });
        stopButton2.addActionListener(e -> {
            if(semaphore.isFree())
                return;

            startButton2.setEnabled(true);
            stopButton1.setEnabled(true);
            progressBar.setForeground(new Color(0,255,0));
            thread2.interrupt();
            semaphore.free();
        });

        counter = slider.getValue();

        pack();
        win.setContentPane(contentPane);
        win.setVisible(true);
    }

    public static void main(String[] args) {
        new MainWindow();
    }
}
