import java.awt.*;
import java.util.Calendar;
import javax.swing.*;

class Clock extends JFrame {

    private static final Color BACKGROUND_COLOR = new Color(0, 0, 0);

    public Clock() {
        ClockPanel container = new ClockPanel();
        add(container, BorderLayout.CENTER);
        setBackground(BACKGROUND_COLOR);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        new Clock();
    }
}

class ClockPanel extends JPanel implements Runnable {

    private static final long serialVersionUID = 1L;
    Thread t = new Thread(this);

    int xHandSec, yHandSec, xHandMin, yHandMin, xHandHour, yHandHour;

    private final int HORIZONTAL_SIZE = 500;
    private final int VERTICAL_SIZE = 500;

    private final int secondHandLength = HORIZONTAL_SIZE / 2 - 50;
    private final int minuteHandLength = HORIZONTAL_SIZE / 2 - 90;
    private final int hourHandLength = HORIZONTAL_SIZE / 2 - 150;

    private final static Color GREY_COLOR = new Color(215, 50, 105);

    public ClockPanel() {
        setMinimumSize(new Dimension(HORIZONTAL_SIZE, VERTICAL_SIZE));
        setMaximumSize(new Dimension(HORIZONTAL_SIZE, VERTICAL_SIZE));
        setPreferredSize(new Dimension(HORIZONTAL_SIZE, VERTICAL_SIZE));
        setLayout(null);
        t.start();
    }

    public void run() {
        while (true) {
            try {
                int currentSecond = Calendar.getInstance().get(Calendar.SECOND);
                int currentMinute = Calendar.getInstance().get(Calendar.MINUTE);
                int currentHour = Calendar.getInstance().get(Calendar.HOUR);

                xHandSec = minToLocation(currentSecond, secondHandLength).x;
                yHandSec = minToLocation(currentSecond, secondHandLength).y;
                xHandMin = minToLocation(currentMinute, minuteHandLength).x;
                yHandMin = minToLocation(currentMinute, minuteHandLength).y;
                xHandHour = minToLocation(currentHour * 5 + getRelativeHour(currentMinute), hourHandLength).x;
                yHandHour = minToLocation(currentHour * 5 + getRelativeHour(currentMinute), hourHandLength).y;
                repaint();
                Thread.sleep(500);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }

    private int getRelativeHour(int min) {
        return min / 12;
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.clearRect(0, 0, getWidth(), getHeight());

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int radius = Math.min(centerX, centerY) - 10;

        // Draw clock circle
        g2.drawOval(centerX - radius, centerY - radius, 2 * radius, 2 * radius);

        // Draw markings for hours and minutes
        drawClockMarkings(g2);

        // Draw clock hands
        g2.setColor(Color.red);
        g2.drawLine(HORIZONTAL_SIZE / 2, VERTICAL_SIZE / 2, xHandSec, yHandSec);
        g2.drawLine(HORIZONTAL_SIZE / 2, VERTICAL_SIZE / 2, xHandMin, yHandMin);
        g2.drawLine(HORIZONTAL_SIZE / 2, VERTICAL_SIZE / 2, xHandHour, yHandHour);
    }

    private void drawClockMarkings(Graphics2D g2) {
    g2.setColor(Color.red);
    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 16));

    int hourMarkingRadius = HORIZONTAL_SIZE / 2 - 40;
    int minuteMarkingRadius = HORIZONTAL_SIZE / 2 - 20;

    // Draw hour markings
    for (int i = 1; i <= 12; i++) {
        
            Point start = minToLocation(i * 5, hourMarkingRadius);
            Point end = minToLocation(i * 5, hourMarkingRadius - 20); // Adjust the length of the line
            g2.drawLine(start.x, start.y, end.x, end.y);
            g2.drawString(Integer.toString(i), end.x - 5, end.y + 5);
        
    }

    // Draw minute markings
    for (int i = 1; i <= 60; i++) {
        Point start = minToLocation(i, minuteMarkingRadius);
        Point end;
        if (i % 5 == 0) {
            end = minToLocation(i, minuteMarkingRadius - 10); // Longer line for hours
        } else {
            end = minToLocation(i, minuteMarkingRadius - 5); // Shorter line for minutes
        }
        g2.drawLine(start.x, start.y, end.x, end.y);
    }
}


    private Point minToLocation(int timeStep, int radius) {
        double t = 2 * Math.PI * (timeStep - 15) / 60;
        int x = (int) (HORIZONTAL_SIZE / 2 + radius * Math.cos(t));
        int y = (int) (VERTICAL_SIZE / 2 + radius * Math.sin(t));
        return new Point(x, y);
    }
}


















// if (i % 5 == 0) {
      //   // big dot
      //   g2.fillOval(dotCoordinates.x - (DIAMETER_BIG_DOT / 2),
      //         dotCoordinates.y - (DIAMETER_BIG_DOT / 2),
      //         DIAMETER_BIG_DOT,
      //         DIAMETER_BIG_DOT);
      // } else {
      //   // small dot
      //   g2.fillOval(dotCoordinates.x - (DIAMETER_SMALL_DOT / 2),
      //         dotCoordinates.y - (DIAMETER_SMALL_DOT / 2),
      //         DIAMETER_SMALL_DOT,
      //         DIAMETER_SMALL_DOT);
      // }