
package imageviewer.architecture;

public class ResizeCommand implements Command{
    private ImagePresenter presenter;

    public ResizeCommand(ImagePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void execute() {
        presenter.show(presenter.current());
    }
}
