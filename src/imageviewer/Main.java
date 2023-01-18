package imageviewer;

import imageviewer.architecture.Image;
import imageviewer.architecture.ImagePresenter;
import imageviewer.architecture.NextCommand;
import imageviewer.architecture.PrevCommand;
import imageviewer.architecture.ResizeCommand;
import java.awt.event.ComponentAdapter;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        MainFrame frame = MainFrame.create();
        Image image = new FileImageLoader(new File("images")).load(); //carga la carpeta que contiene las imagenes
        ImagePresenter presenter = ImagePresenter.with(frame.imageDisplay());
        
        frame.add("next", new NextCommand(presenter));
        frame.add("prev", new PrevCommand(presenter));
        frame.add("resize",new ResizeCommand(presenter));
        frame.start();
        presenter.show(image);
    }
    
}
