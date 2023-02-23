package tankrotationexample.menus;

import tankrotationexample.Launcher;
import tankrotationexample.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class EndGamePanel extends JPanel {

    private BufferedImage menuBackground;
    private JButton start;
    private JButton exit;
    private Launcher lf;

    public EndGamePanel(Launcher lf) {
        this.lf = lf;

        menuBackground = Resources.getSprite("menu");

        this.setBackground(Color.BLACK);
        this.setLayout(null);

        start = new JButton("Restart");
        start.setFont(new Font("Courier New", Font.BOLD, 24));
        start.setBounds(550, 300, 150, 50);
        start.addActionListener((actionEvent -> {
            this.lf.setFrame("game");
        }));

        exit = new JButton("Exit");
        exit.setFont(new Font("Courier New", Font.BOLD, 24));

        exit.setBounds(550, 400, 150, 50);
        exit.addActionListener((actionEvent -> {
            this.lf.closeGame();
        }));

        this.add(start);
        this.add(exit);

    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.menuBackground, 0, 0, null);
    }
}
