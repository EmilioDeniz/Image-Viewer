package imageviewer.architecture;

public interface Image {
    public byte[] data();
    public int width();
    public int height();
    public Image prev();
    public Image next();

    public String location();
    
}
