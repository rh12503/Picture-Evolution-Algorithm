package pea.util;

import pea.ga.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class PreviewWindow implements World.Observer {
    private final static int TIME_PER_FRAME = 200;
    private JFrame frame;
    private Panel panel;
    private World world;
    private long lastRenderedTime;

    public PreviewWindow(World world) {
        world.registerObserver(this);
        this.world = world;
        panel = new Panel(world);
        frame = new JFrame();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setTitle("Preview");
        Dimension size = new Dimension(world.getImage().getWidth(), world.getImage().getHeight());
        panel.setPreferredSize(size);
        frame.pack();
        panel.setBackground(Color.BLACK);
        panel.setVisible(true);

        frame.setContentPane(panel);
        frame.setVisible(true);

        frame.pack();
        frame.setResizable(false);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                close();
            }
        });
        lastRenderedTime = System.currentTimeMillis();
    }

    public void close() {
        System.out.println("Closing preview window...");
        world.removeObserver(this);
        frame.dispose();
        System.exit(1);
    }

    @Override
    public void update(World world) {
        if (System.currentTimeMillis() - lastRenderedTime < TIME_PER_FRAME) {
            return;
        }
        panel.repaint();

        lastRenderedTime = System.currentTimeMillis();
    }

    private static class Panel extends JPanel {
        private static final long serialVersionUID = 8844154337627751478L;
        World world;

        private Panel(World world) {
            this.world = world;
            setDoubleBuffered(true);
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            g2d.drawImage(world.getImage(), 0, 0, this);
        }

    }
}