import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Frame implements Runnable{

    public enum Position {UPPER_LEFT, UPPER_RIGHT};

    private Position position;
    private JFrame frame;
    private JLabel picLabel;
    private BufferedImage pic;
    private File file;

    public Frame(JFrame frame, BufferedImage pic, File file, Position position) {
        this.frame = frame;
        this.pic=pic;
        this.file = file;
        this.position = position;
    }

    public void run(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        picLabel = new JLabel(new ImageIcon(pic));
        try {
            pic = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        frame.add(picLabel);

        frame.pack();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
        int y = 0;
        int x = (int) (position == Position.UPPER_RIGHT ? rect.getMaxX() - frame.getWidth() : 0);

        frame.setLocation(x, y);
        frame.setVisible(true);
    }

    public void refresh(){

        SwingUtilities.invokeLater(() -> {
            try {
                pic = ImageIO.read(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            picLabel.setIcon(new ImageIcon(pic));
            frame.repaint();

            frame.pack();
        });
    }


}
