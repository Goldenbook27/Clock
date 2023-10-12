private void drawClockMarkings(Graphics2D g2) {
    g2.setColor(Color.white);
    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 16));

    int hourMarkingRadius = HORIZONTAL_SIZE / 2 - 40;
    int minuteMarkingRadius = HORIZONTAL_SIZE / 2 - 20;

    // Draw hour markings
    for (int i = 1; i <= 12; i++) {
        if (i % 3 == 0) { // Draw every 3rd hour
            Point start = minToLocation(i * 5, hourMarkingRadius);
            Point end = minToLocation(i * 5, hourMarkingRadius - 20); // Adjust the length of the line
            g2.drawLine(start.x, start.y, end.x, end.y);
            g2.drawString(Integer.toString(i / 3), end.x - 5, end.y + 5);
        }
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
