package twa.siedelwood.s5.mapmaker.view;

public class PresentationException extends Exception {
    public PresentationException() {
    }

    public PresentationException(String s) {
        super(s);
    }

    public PresentationException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public PresentationException(Throwable throwable) {
        super(throwable);
    }

    public PresentationException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
