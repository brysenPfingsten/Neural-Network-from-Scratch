import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class NumberDrawer extends JFrame {
    private final int SIZE = 28;
    private final int SCALE = 10;
    private final int WIDTH = SIZE * SCALE;
    private final int HEIGHT = SIZE * SCALE;

    private BufferedImage image;
    private Graphics2D g2d;

    public NumberDrawer() {
        setTitle("Number Drawer");
        setSize(WIDTH, HEIGHT + 50); // Increased height for the "Predict" button
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g2d = image.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, WIDTH, HEIGHT);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, null);
            }
        };
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / SCALE;
                int y = e.getY() / SCALE;
                g2d.setColor(Color.BLACK);
                g2d.fillRect(x * SCALE, y * SCALE, SCALE, SCALE);
                // Fill the surrounding eight pixels with grey color
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        if (dx != 0 || dy != 0) { // Skip the current pixel
                            int newX = x + dx;
                            int newY = y + dy;
                            if (newX >= 0 && newX < SIZE && newY >= 0 && newY < SIZE) {
                                if ((image.getRGB(newX * SCALE, newY * SCALE) & 0xFFFFFF) != 0x000000) {
                                    g2d.setColor(Color.GRAY);
                                    g2d.fillRect(newX * SCALE, newY * SCALE, SCALE, SCALE);
                                }
                            }
                        }
                    }
                }
        

                repaint();
            }
        });
        panel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int x = e.getX() / SCALE;
                int y = e.getY() / SCALE;
                g2d.setColor(Color.BLACK);
                g2d.fillRect(x * SCALE, y * SCALE, SCALE, SCALE);
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        if (dx != 0 || dy != 0) { // Skip the current pixel
                            int newX = x + dx;
                            int newY = y + dy;
                            if (newX >= 0 && newX < SIZE && newY >= 0 && newY < SIZE) {
                                if ((image.getRGB(newX * SCALE, newY * SCALE) & 0xFFFFFF) != 0x000000) {
                                    g2d.setColor(Color.GRAY);
                                    g2d.fillRect(newX * SCALE, newY * SCALE, SCALE, SCALE);
                                }
                            }
                        }
                    }
                }
        
                repaint();
            }
        });

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> clearImage());
        add(clearButton, BorderLayout.SOUTH);

        JButton predictButton = new JButton("Predict");
        predictButton.addActionListener(e -> predictNumber());
        add(predictButton, BorderLayout.NORTH);
    }

    private void clearImage() {
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, WIDTH, HEIGHT);
        repaint();
    }

    private void predictNumber() {
        double[] imageData = new double[SIZE * SIZE];
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                int pixelValue = image.getRGB(x * SCALE, y * SCALE) & 0xFF; // Extracting the grayscale value
                double normalizedValue = (double) 255 - pixelValue; // Normalizing to range [0, 1]
                imageData[y * SIZE + x] = normalizedValue;
            }
        }
        int predictedNumber = predict(imageData); // Call your prediction function here
        clearImage();
        JOptionPane.showMessageDialog(this, "Predicted Number: " + predictedNumber);
    }

    private int predict(double[] imageData) {
       return App.Predict(imageData);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            NumberDrawer drawer = new NumberDrawer();
            drawer.setVisible(true);
        });
    }
}



