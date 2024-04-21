package net.wilux.util.dirty;

import net.wilux.util.Result;

import java.util.function.Function;

public class DirtyAble<TNullable> {
    private boolean dirty;
    private TNullable value;
    public DirtyAble(TNullable t) {
        this.dirty = true;
        this.value = t;
    }
    public void set(TNullable t) {
        this.dirty = true;
        this.value = t;
    }
    public <R> R mutate(Function<TNullable,R> func) {
        this.dirty = true;
        return func.apply(this.value);
    }

    public Result<TNullable, Exception> takeIfDirty() {
        if (this.dirty) {
            this.dirty = false;
            return new Result.Ok<>(this.value);
        } else {
            return Result.defaultFail();
        }
    }
}
