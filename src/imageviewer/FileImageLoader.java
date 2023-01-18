package imageviewer;

import imageviewer.architecture.Image;
import imageviewer.architecture.ImageLoader;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import javax.imageio.ImageIO;

public class FileImageLoader implements ImageLoader {
    private final static String[] ImageExtension = new String[] {"png","jpg","jpeg"};
    private final File[] files;

    public FileImageLoader(File file) {
        this.files = file.listFiles(imageFilter());
    }

    @Override
    public Image load() {
        return imageOf(0);
    }

    private Image imageOf(int index) {
        return new Image() {
            private byte[] data;
            private int width;
            private int height;
            
            {
                try {
                    this.data = load(files[index]);
                    BufferedImage image = ImageIO.read(new ByteArrayInputStream(data));
                    this.width = image.getWidth();
                    this.height = image.getHeight();                    
                }
                catch (IOException e) {
                    this.data = new byte[0];
                    this.width = 0;
                    this.height = 0;
                }
            }
            @Override
            public Image prev() {
                return imageOf(index == 0 ? files.length - 1 : index -1);
            }

            @Override
            public Image next() {
                return imageOf(index == files.length - 1 ? 0 : index+1);
            }

            @Override
            public byte[] data() {
                return data;
            }
            
            private byte[] load(File file) throws IOException {
                return Files.readAllBytes(file.toPath());
            }

            @Override
            public int width() {
                return width;
            }

            @Override
            public int height() {
                return height;
            }

            @Override
            public String location() {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        };
    }

    private FilenameFilter imageFilter() {
        return new FilenameFilter() {
            @Override
            public boolean accept(File file, String filename) {
                return isImage(extensionOf(filename));
            }

            private boolean isImage(String extension) {
                for (String option : ImageExtension) 
                    if (option.equals(extension)) return true;
                return false;
            }

            private String extensionOf(String filename) {
                return filename.substring(filename.lastIndexOf(".")+1);
            }
        };
    }
    
    
    
    
}
