package imageviewer;

import imageviewer.architecture.Command;
import imageviewer.architecture.ImageDisplay;
import imageviewer.architecture.ImageDisplay.ResizeEvent;
import imageviewer.architecture.ImageWindow;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame {
    private final Map<String,Command> commands = new HashMap<>();
    private ImagePanel imageDisplay;
    private ResizeEvent onResized = ResizeEvent::Null;
    
    public static MainFrame create() throws IOException {
        return new MainFrame();
    }

    private MainFrame() throws IOException {
        this.setTitle("Image Viewer");
        this.setSize(800,600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.add(imageDisplay = imagePanel());
        JButton prev = (JButton) prevButton();
        JButton next = (JButton) nextButton();
        Component toolbar = toolbar(prev,next);
        this.add(toolbar,BorderLayout.SOUTH);
        
        toolbar.addMouseMotionListener(new MouseAdapter(){
            public void mouseMoved(MouseEvent e){
                prev.setVisible(e.getY() <= 30);
                next.setVisible(e.getY() <= 30);
            }
        });
        
        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                commands.get("resize").execute();
            }
        });;
    }
    
   
    void start() {
        this.setVisible(true);
    }
    
    public void add(String name, Command command) {
        commands.put(name, command);
    }

    private Component toolbar(JButton prev, JButton next) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel.add(prev);
        panel.add(next);
        return panel;
    }

    
    private ImagePanel imagePanel()  {
        return new ImagePanel();
    }

    private Component nextButton() {
        final JButton button = new JButton(">");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                commands.get("next").execute();
            }
        });
        return button;
    }

    private Component prevButton() {
        final JButton button = new JButton("<");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                commands.get("prev").execute();
            }
        });
        return button;
    }

    ImageDisplay imageDisplay() {
        return imageDisplay;
    }
    
}
