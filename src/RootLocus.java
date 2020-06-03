import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RootLocus {
    public static void main(String[] args){
        //GUI
        JFrame frame = new JFrame("Root Locus");
        frame.setSize(800, 800);
        Canvas canvas = new MyCanvas();
        frame.setResizable(false);
        frame.add(canvas);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    static class MyCanvas extends Canvas {
        public MyCanvas() {
            setBackground (Color.WHITE);
            setSize(800, 800);
        }
        @Override
        public void paint(Graphics g){
            Graphics2D g2d = (Graphics2D) g;
            initialize(g2d);
            drawPoles(g2d);
            drawAsymptoticLines(g2d);
            drawBreakAwayPoints(g2d);
            drawIntersectionWithImaginaryAxis(g2d);
            drawDepartureAngle(g2d);
            try {
                drawCurves(g2d);
            } catch (IOException e) {
                e.printStackTrace();
            }
            g2d.dispose();
        }
        private void initialize(Graphics2D g2d){
            g2d.setColor(Color.RED);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawLine(0, 400, 800, 400);
            g2d.drawLine(400, 0, 400, 800);
            g2d.setColor(Color.GRAY);
            g2d.setStroke(new BasicStroke(1));
            for(int i = 0; i <= 800; i+=100){
                if(i==400)
                    continue;
                g2d.drawLine(0, i, 800, i);
                g2d.drawString("("+(200-i/2)+")", 400, i);
            }
            for(int i = 0; i <= 800; i+=100){
                if(i==400)
                    continue;
                g2d.drawLine(i, 0, i, 800);
                g2d.drawString("("+(i/2-200)+")", i, 400);
            }
        }
        private void drawPoles(Graphics2D g2d){
            g2d.setColor(Color.CYAN);
            Font font = g2d.getFont();
            g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
            g2d.drawString("X", 395, 405);
            g2d.drawString("X", 345, 405);
            g2d.drawString("X", 295, 405-20);
            g2d.drawString("X", 295, 405+20);
            g2d.setColor(Color.GRAY);
            g2d.setFont(font);
        }
        private void drawAsymptoticLines(Graphics2D g2d){
            g2d.setColor(Color.RED);
            Font font = g2d.getFont();
            g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
            g2d.drawString("O", (float)(395.0-62.5), 405);
            g2d.setColor(Color.GRAY);
            g2d.setFont(font);
            Stroke stroke = g2d.getStroke();
            g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0));
            g2d.drawLine(800, -62, 0, 800-62);
            g2d.drawLine(800, 862, 0, 62);
            g2d.setStroke(stroke);
        }
        public void drawBreakAwayPoints(Graphics2D g2d){
            g2d.setColor(Color.ORANGE);
            Font font = g2d.getFont();
            g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
            g2d.drawString("O", (float) (395-(9.15039014)*2), 405);
            g2d.setColor(Color.GRAY);
            g2d.setFont(font);
        }
        public void drawIntersectionWithImaginaryAxis(Graphics2D g2d){
            g2d.setColor(Color.BLUE);
            Font font = g2d.getFont();
            g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
            g2d.drawString("O", 395, (float) (405-(22.8035085)*2));
            g2d.drawString("O", 395,(float) (405+(22.8035085)*2));
            g2d.setColor(Color.GRAY);
            g2d.setFont(font);
        }
        public void drawDepartureAngle(Graphics2D g2d){
            double departureAngle = 123.1113419603;
            g2d.setColor(Color.BLACK);
            Font font = g2d.getFont();
            g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
            g2d.drawString("123.11", 300, 375);
            g2d.setColor(Color.MAGENTA);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawLine(300, 400-20, 300+(int)(100*Math.cos(departureAngle*Math.PI/180)), 380-(int)(100*Math.sin(departureAngle*Math.PI/180)));
            g2d.drawLine(300, 380, 375, 380);
            g2d.setColor(Color.GRAY);
            g2d.setFont(font);
        }
        public void drawCurves(Graphics2D g) throws IOException {
            g.setStroke(new BasicStroke(3));
            g.drawLine(400, 400, 400, 400);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/a.txt")));
            String line, pattern="([-+]?[0-9.]+)([-+][0-9.]+i)?";
            Pattern r = Pattern.compile(pattern);
            while((line = bufferedReader.readLine())!=null){
                String[] row = line.split(",");
                Color[] colors = new Color[]{Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE};
                for(int i = 0; i < 4; ++i){
                    g.setColor(colors[i]);
                    Matcher matcher = r.matcher(row[i]);
                    matcher.find();
                    double x = Double.parseDouble(matcher.group(1)), y = 0;
                    if(matcher.group(2) != null){
                        String t = matcher.group(2);
                        if(t.charAt(t.length()-1)=='i')
                            t = t.substring(0, t.length()-1);
                        y = Double.parseDouble(t);
                    }
                    g.drawLine(400+(int)x*2, 400+(int)y*2, 400+(int)x*2, 400+(int)y*2);
                }
            }
        }
    }
}
