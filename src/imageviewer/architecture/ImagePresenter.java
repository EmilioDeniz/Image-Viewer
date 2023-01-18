package imageviewer.architecture;

public class ImagePresenter {
    private final ImageDisplay display;
    private Image image;

    public static ImagePresenter with(ImageDisplay display) {
        return new ImagePresenter(display);
    }
    
    private ImagePresenter(ImageDisplay display) {
        this.display = display;
        this.display.onDragged(this::onDragged);
        this.display.onReleased(this::onReleased);
    }
    
    private void onDragged(int offset) {
        this.display.clear();
        this.display.paintImage(image.data(), imageWindowOf(image).offset(offset));
        if (offset == 0) return;
        if (offset < 0) {
            Image next = image.next();
            this.display.paintImage(next.data(), imageWindowOf(next).offset(offset+display.width()));
        }
        else {
            Image prev = image.prev();
            this.display.paintImage(prev.data(), imageWindowOf(prev).offset(offset-display.width()));
        }
    }
        
    private void onReleased(int offset) {
        if (Math.abs(offset) > display.width()/2) 
            this.image = offset < 0 ? image.next() : image.prev();
        this.refresh();
    }
    
    private void onResized(){
        this.refresh();
    }
    
    public ImagePresenter show(Image image) {
        this.image = image;
        this.refresh();
        return this;
    }
    
    public Image current() {
        return image;
    }

    private ImageDisplay.ImageWindow imageWindowOf(Image image) {
        ImageDisplay.ImageWindow window = new ImageDisplay.ImageWindow(image.width(), image.height());
        window.adjustTo(display.width(), display.height());
        return window;
    }

    private void refresh() {
        this.display.clear();
        this.display.paintImage(image.data(), imageWindowOf(image));
    }  
}
