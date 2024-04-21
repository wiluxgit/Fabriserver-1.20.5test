package net.wilux.util;

import java.util.function.Function;

public class Dirty<T> {
    private boolean dirty;
    private T value;

    public Dirty(T t) {
        this.dirty = true;
        this.value = t;
    }

    public void set(T t) {
        this.dirty = true;
        this.value = t;
    }

    public <R> R mutate(Function<T,R> func) {
        this.dirty = true;
        return func.apply(this.value);
    }

    public T get() {
        return this.value;
    }

    public Result<T, Exception> consume() {
        if (this.dirty) {
            this.dirty = false;
            return new Result.Ok<>(this.value);
        } else {
            return Result.defaultFail();
        }
    }
}
