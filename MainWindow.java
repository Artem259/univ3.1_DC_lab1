import javax.swing.*;

public class MainWindow extends JDialog {
    private JPanel contentPane;
    private JSpinner spinner1;
    private JSpinner spinner2;
    private JSlider slider;

    public MainWindow() {
        setContentPane(contentPane);
        setModal(true);
        pack();
        setVisible(true);
        System.exit(0);
    }

    private void createUIComponents() {
        spinner1 = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        spinner2 = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
    }
}
