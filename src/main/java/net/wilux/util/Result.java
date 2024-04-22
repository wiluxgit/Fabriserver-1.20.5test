package net.wilux.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Result<TValue, TException extends Exception> {
    final class Ok<TValue, TException extends Exception> implements Result<TValue, TException> {
        public final @Nullable TValue value;

        public Ok(@Nullable TValue value) { this.value = value; }
    }
    final class Fail<TValue, TException extends Exception> implements Result<TValue, TException> {
        public final @NotNull TException exception;

        public Fail(@NotNull TException exception) { this.exception = exception; }
    }

    static <T> Fail<T, Exception> defaultFail() {
        return new Fail<>(new Exception());
    }
}