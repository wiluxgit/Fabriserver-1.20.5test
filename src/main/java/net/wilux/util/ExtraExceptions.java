package net.wilux.util;

public final class ExtraExceptions {
    public static class ImpossibleException extends RuntimeException{
        public ImpossibleException(String s) { super(s); }
        public ImpossibleException() { super(); }
    } // Bold claim I know

    public static class ProbablyImpossibleException extends RuntimeException{
        public ProbablyImpossibleException(String s) { super(s); }
        public ProbablyImpossibleException() { super(); }
    }

    public static class DoNotCallException extends RuntimeException {}

    public static void debugCrash(String why) {
        throw new RuntimeException(why);
    }
}
