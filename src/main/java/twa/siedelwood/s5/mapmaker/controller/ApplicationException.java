package twa.siedelwood.s5.mapmaker.controller;

public class ApplicationException extends Exception {
    public ApplicationException() {
    }

    public ApplicationException(String s) {
        super(s);
    }

    public ApplicationException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public ApplicationException(Throwable throwable) {
        super(throwable);
    }

    public ApplicationException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
