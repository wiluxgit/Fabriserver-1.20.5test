package net.wilux.stackstorage;

import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import static java.lang.Math.min;

public class StoredStack {
    public static class StackTransfer {
        private final StoredStack backref;
        private boolean resolved;
        private final int nItemsToTake;

        public final ItemStack itemStack;

        protected StackTransfer(StoredStack backref, ItemStack itemStack) {
            this.resolved = false;
            this.backref = backref;
            this.nItemsToTake = itemStack.getCount();
            this.itemStack = itemStack;
        }

        /**
         *
         * @param approved weather to approve or reject the transfer
         * @return the number of items remaining in the container after this transfer has resolved
         */
        public int resolveWith(boolean approved) {
            if (this.resolved) throw new RuntimeException("Transfer has already resolved!");
            int remainingItems;
            if (approved) {
                remainingItems = this.backref.approveTransfer(nItemsToTake);
            } else {
                remainingItems = this.backref.rejectTransfer(nItemsToTake);
            }
            this.resolved = true;
            return remainingItems;
        }
    }

    private final ItemStack itemStack; // Should always have count 1
    private int actualCount;
    private int transferableCount;
    public StoredStack(ItemStack itemStack, int count) {
        this.itemStack = itemStack.copyWithCount(1);
        this.actualCount = count;
        this.transferableCount = count;
    }
    public ItemStack stackCopy() {
        return this.itemStack.copy();
    }
    public int amount() {
        return actualCount;
    }

    // should have mutex
    public @Nullable StackTransfer takeLargest() {
        var getSize = min(this.transferableCount, this.itemStack.getMaxCount());
        return this.take(getSize);
    }
    public @Nullable StackTransfer take(int count) {
        if (count > this.transferableCount) return null;
        this.transferableCount -= count;
        return new StackTransfer(this, this.itemStack.copyWithCount(count));
    }
    public int insert(int count) {
        assert count > 0;
        this.transferableCount += count;
        this.actualCount += count;
        return this.actualCount;
    }

    protected int approveTransfer(int nItemsToRemoveFromMe) {
        this.actualCount -= nItemsToRemoveFromMe;
        assert (this.actualCount >= 0);
        return this.actualCount;
    }
    protected int rejectTransfer(int nItemsToRemoveFromMe) {
        this.transferableCount += nItemsToRemoveFromMe;
        return this.actualCount;
    }
}
