import javax.imageio.plugins.jpeg.JPEGHuffmanTable;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class PinBall extends JFrame implements ActionListener{
    JButton start = new JButton("开始");
    JButton end = new JButton("停止");
    JButton xSpeedUp = new JButton("增加横向速度");
    JButton xSpeedDown = new JButton("减少横向速度");
    JButton ySpeedUp = new JButton("增加纵向速度");
    JButton ySpeedDown = new JButton("减少纵向速度");

    JPanel p1 = new JPanel();
    Ball ball = new Ball();
    ballPanel bp = new ballPanel(ball);

    public PinBall(){
        setTitle("ball game yyz");
        Container con = this.getContentPane();
        con.setLayout(new BorderLayout());
        con.add(BorderLayout.SOUTH, p1);
        con.add(BorderLayout.CENTER, bp);
        p1.setLayout(new GridLayout(1,6, 10, 10));
        p1.add(start);
        p1.add(end);
        p1.add(xSpeedUp);
        p1.add(xSpeedDown);
        p1.add(ySpeedUp);
        p1.add(ySpeedDown);

        start.addActionListener(this);
        end.addActionListener(this);
        xSpeedUp.addActionListener(this);
        xSpeedDown.addActionListener(this);
        ySpeedUp.addActionListener(this);
        ySpeedDown.addActionListener(this);

        Runnable r = new ballRunnable(ball, bp);
        Thread thread = new Thread(r);
        thread.start();
        setVisible(true);
        setBounds(200,200,1000,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent ae){
        Object object = ae.getSource();
        if (object==start){
            ball.flag=true;
        }
        if (object==end){
            ball.flag=false;
        }
        if (object==xSpeedUp){
            if (ball.x_Speed>=0)
                ball.x_Speed++;
            if (ball.x_Speed<0)
                ball.x_Speed--;
            System.out.println(ball.x_Speed + " " + ball.y_Speed);

        }
        if (object==xSpeedDown){
            if (ball.x_Speed>=0)
                ball.x_Speed--;
            if (ball.x_Speed<0)
                ball.x_Speed++;
            System.out.println(ball.x_Speed + " " + ball.y_Speed);

        }
        if (object==ySpeedUp){
            if (ball.y_Speed>=0)
                ball.y_Speed++;
            if (ball.y_Speed<0)
                ball.y_Speed--;
            System.out.println(ball.x_Speed + " " + ball.y_Speed);

        }
        if (object==ySpeedDown){
            if (ball.y_Speed>=0)
                ball.y_Speed--;
            if (ball.y_Speed<0)
                ball.y_Speed++;
            System.out.println(ball.x_Speed + " " + ball.y_Speed);

        }

    }

    public static void main(String[] args){
        new Exp7();
    }
}

class Ball{
    private double x = 0;//position
    private double y = 0;//position
    public double x_Speed = 1;
    public double y_Speed = 1;
    private int XSIZE = 15;
    private int YSIZE = 15;
    public boolean flag;
    public Ball(){

    }

    public void move(Rectangle2D bounds){
        if (flag==true){
            x = x + x_Speed;
            y = y + y_Speed;
            if (x < bounds.getMinX()){
                x = bounds.getMinX();
                x_Speed = -x_Speed;
            }
            if (x + XSIZE/2 >= bounds.getMaxX()){
                x = bounds.getMaxX() - XSIZE/2;
                x_Speed = -x_Speed;
            }
            if (y < bounds.getMinY()){
                y = bounds.getMinY();
                y_Speed = -y_Speed;
            }
            if (y + YSIZE/2 >= bounds.getMaxY()){
                y = bounds.getMaxY() - YSIZE/2;
                y_Speed = -y_Speed;
            }
        }
        if (flag==false){
        }
    }

    public Ellipse2D getShape(){
        return new Ellipse2D.Double(x, y, XSIZE, YSIZE);
    }
}

class ballPanel extends JPanel{

    public Ball ball = new Ball();
    public ballPanel(Ball b){
        ball = b;
    }
    public void paint(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.fill(ball.getShape());
    }
}

class ballRunnable implements Runnable{
    public Ball ball;
    private int DELAY = 10;
    public Component c;

    public ballRunnable(Ball b, Component com){
        ball = b;
        c = com;
    }
    public void run(){
        try {
            while (true) {
                ball.move(c.getBounds());
                c.repaint();
                Thread.sleep(DELAY);
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}

