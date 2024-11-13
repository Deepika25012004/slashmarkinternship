import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class snakeGame extends JPanel implements ActionListener {
    private static final int TILE_SIZE = 25;
    private static final int GRID_SIZE = 20;
    private static final int WINDOW_SIZE = TILE_SIZE * GRID_SIZE;
    private static final int DELAY = 150; // Speed of the snake

    private final ArrayList<Point> snake;
    private Point food;
    private char direction = 'R'; // R=Right, L=Left, U=Up, D=Down
    private boolean running = true;
   final Timer timer;

    public snakeGame() {
        setPreferredSize(new Dimension(WINDOW_SIZE, WINDOW_SIZE));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        if (direction != 'R') direction = 'L';
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (direction != 'L') direction = 'R';
                        break;
                    case KeyEvent.VK_UP:
                        if (direction != 'D') direction = 'U';
                        break;
                    case KeyEvent.VK_DOWN:
                        if (direction != 'U') direction = 'D';
                        break;
                }
            }
        });

        snake = new ArrayList<>();
        snake.add(new Point(GRID_SIZE / 2, GRID_SIZE / 2));

        placeFood();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkCollision();
            checkFood();
        }
        repaint();
    }

    private void move() {
        Point head = new Point(snake.get(0));
        switch (direction) {
            case 'U' -> head.translate(0, -1);
            case 'D' -> head.translate(0, 1);
            case 'L' -> head.translate(-1, 0);
            case 'R' -> head.translate(1, 0);
        }
        snake.add(0, head);
        snake.remove(snake.size() - 1);
    }

    private void checkCollision() {
        Point head = snake.get(0);

        // Check for wall collision
        if (head.x < 0 || head.x >= GRID_SIZE || head.y < 0 || head.y >= GRID_SIZE) {
            running = false;
            timer.stop();
        }

        // Check for self-collision
        for (int i = 1; i < snake.size(); i++) {
            if (head.equals(snake.get(i))) {
                running = false;
                timer.stop();
                break;
            }
        }
    }

    private void checkFood() {
        if (snake.get(0).equals(food)) {
            snake.add(new Point(food)); // Grow snake
            placeFood(); // Place new food
        }
    }

    private void placeFood() {
        Random random = new Random();
        food = new Point(random.nextInt(GRID_SIZE), random.nextInt(GRID_SIZE));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (running) {
            // Draw food
            g.setColor(Color.RED);
            g.fillOval(food.x * TILE_SIZE, food.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);

            // Draw snake
            for (int i = 0; i < snake.size(); i++) {
                if (i == 0) g.setColor(Color.GREEN); // Head
                else g.setColor(Color.YELLOW); // Body
                Point p = snake.get(i);
                g.fillRect(p.x * TILE_SIZE, p.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        } else {
            showGameOver(g);
        }
    }

    private void showGameOver(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        FontMetrics metrics = getFontMetrics(g.getFont());
        String message = "Game Over! Score: " + (snake.size() - 1);
        g.drawString(message, (WINDOW_SIZE - metrics.stringWidth(message)) / 2, WINDOW_SIZE / 2);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake Game");
        snakeGame game = new snakeGame();
        frame.add(game);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

