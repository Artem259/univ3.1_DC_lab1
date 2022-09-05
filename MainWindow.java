import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JDialog {
    private Thread thread1, thread2;
    private volatile int counter;
    private JPanel contentPane;
    private JSpinner spinner1;
    private JSpinner spinner2;
    private JSlider slider;
    private JButton startButton;

    public MainWindow() {
        JFrame win = new JFrame();
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setSize(500,300);
        setModal(true);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startButton.setEnabled(false);

                thread1 = new Thread(
                        () -> {
                            while (true) {
                                synchronized (this) {
                                    if(counter>10)
                                        counter--;
                                    slider.setValue(counter);
                                    try {
                                        wait(5);
                                    } catch (InterruptedException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                }
                            }
                        }
                );

                thread2 = new Thread(
                        () -> {
                            while (true) {
                                synchronized (this) {
                                    if(counter<90)
                                        counter++;
                                    slider.setValue(counter);
                                    try {
                                        wait(5);
                                    } catch (InterruptedException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                }
                            }
                        }
                );

                thread1.start();
                thread2.start();
                thread1.setPriority((Integer)(spinner1.getValue()));
                thread2.setPriority((Integer)(spinner2.getValue()));
            }
        });

        spinner1.addChangeListener(e -> {
            if(thread1 != null) {
                thread1.setPriority((Integer)(spinner1.getValue()));
            }
        });
        spinner2.addChangeListener(e -> {
            if(thread2 != null) {
                thread2.setPriority((Integer)(spinner2.getValue()));
            }
        });

        counter = slider.getValue();

        pack();
        win.setContentPane(contentPane);
        win.setVisible(true);
    }

    private void createUIComponents() {
        spinner1 = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        spinner2 = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
    }

    public static void main(String[] args) {
        new MainWindow();
    }
}
