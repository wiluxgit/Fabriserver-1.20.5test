package net.wilux.util;

import org.jetbrains.annotations.Nullable;

public interface Result<TValue, TException extends Exception> {
    final class Ok<TValue, TException extends Exception> implements Result<TValue, TException> {
        public final TValue value;

        public Ok(TValue value) {
            this.value = value;
        }
    }
    final class Fail<TValue, TException extends Exception> implements Result<TValue, TException> {
        public final TException exception;

        public Fail(TException exception) {
            this.exception = exception;
        }
    }

    static <T> Fail<T, Exception> defaultFail() {
        return new Fail<>(new Exception());
    }
}