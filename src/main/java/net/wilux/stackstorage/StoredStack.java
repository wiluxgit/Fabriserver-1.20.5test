package net.wilux.stackstorage;

import net.minecraft.item.ItemStack;
import net.wilux.PolyWorks;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Logger;

import static java.lang.Math.max;
import static java.lang.Math.min;

// Over-engineered, should probably fix?
public class StoredStack {
    private final ItemStack itemStack; // Should always have count 1
    private int actualCount;
    private int outAbleCount;
    private int inAbleCount;
    public final int maxCount;

    public StoredStack(ItemStack itemStack, int count, int maxCount) {
        this.itemStack = itemStack.copyWithCount(1);
        this.actualCount = count;
        this.outAbleCount = count;
        this.inAbleCount = maxCount-count;
        this.maxCount = maxCount;
    }

    public ItemStack stackCopy() {
        return this.itemStack.copy();
    }
    public int amount() {
        return actualCount;
    }

    private void modifyActualCount(int delta) {
        this.actualCount += delta;
        assert this.actualCount <= this.maxCount;
        assert this.actualCount >= 0;
    }
    private void changed(String why) {
        PolyWorks.LOGGER.info("modified stored stack because: \""+why+"\" is now: ["+
                "actual=" + this.actualCount + ", " +
                "inAble=" + this.inAbleCount + ", " +
                "outAble=" + this.outAbleCount + "]"
        );
    }

    public @Nullable StackOutTransfer takeLargest() {
        var getSize = min(this.outAbleCount, this.itemStack.getMaxCount());
        this.changed("takeLargest");
        return this.take(getSize);
    }
    public @Nullable StackOutTransfer take(int count) {
        if (count > this.outAbleCount) return null;
        this.outAbleCount -= count;
        this.changed("take");
        return new StackOutTransfer(this, this.itemStack.copyWithCount(count));
    }
    public @Nullable StackInTransfer insert(ItemStack inStack) {
        if (!ItemStack.canCombine(inStack, this.itemStack)) return null;
        int nToInsert = inStack.getCount();
        var delta = min(nToInsert, this.inAbleCount);
        this.inAbleCount -= delta;
        this.changed("insert");
        return new StackInTransfer(this, this.itemStack.copyWithCount(delta), delta);
    }

    protected int approveOutTransfer(int nItemsToRemoveFromMe) {
        modifyActualCount(-nItemsToRemoveFromMe);
        this.inAbleCount += nItemsToRemoveFromMe;
        this.changed("approveOutTransfer");
        return this.actualCount;
    }
    protected int rejectOutTransfer(int nItemsToRemoveFromMe) {
        this.outAbleCount += nItemsToRemoveFromMe;
        assert 0 <= this.outAbleCount && this.outAbleCount <= this.maxCount;
        this.changed("rejectOutTransfer");
        return this.actualCount;
    }
    protected int approveInTransfer(int nItemsToInsert) {
        modifyActualCount(nItemsToInsert);
        this.outAbleCount += nItemsToInsert;
        this.changed("approveInTransfer");
        return this.actualCount;
    }
    protected int rejectInTransfer(int nItemsToInsert) {
        this.inAbleCount += nItemsToInsert;
        assert 0 <= this.inAbleCount && this.inAbleCount <= this.maxCount;
        this.changed("rejectInTransfer");
        return this.actualCount;
    }

    public static class StackInTransfer {
        private final StoredStack backref;
        private boolean resolved;
        private final int nItemsToInsert;
        private final int nItemsThatWillRemainInInput;

        public final ItemStack itemStackToBeInserted;

        protected StackInTransfer(StoredStack backref, ItemStack itemStack, int nItemsThatWillRemainInInput) {
            this.resolved = false;
            this.backref = backref;
            this.nItemsThatWillRemainInInput = nItemsThatWillRemainInInput;
            this.nItemsToInsert = itemStack.getCount();
            this.itemStackToBeInserted = itemStack;
        }

        /**
         *
         * @param approved weather to approve or reject the transfer
         * @param stackToTakeItemsFrom /!\ MUST BE THE SAME STACK THAT WAS INSERTED /!\
         * @return
         */
        public int resolveWith(boolean approved, ItemStack stackToTakeItemsFrom) {
            if (this.resolved) throw new RuntimeException("Transfer has already resolved! how?");
            int remainingItems;
            if (approved) {
                remainingItems = this.backref.approveInTransfer(nItemsToInsert);
                stackToTakeItemsFrom.setCount(this.nItemsThatWillRemainInInput);
            } else {
                remainingItems = this.backref.rejectInTransfer(nItemsToInsert);
            }

            this.resolved = true;
            return remainingItems;
        }
    }

    public static class StackOutTransfer {
        private final StoredStack backref;
        private boolean resolved;
        private final int nItemsToTake;

        public final ItemStack itemStackToExtract;

        protected StackOutTransfer(StoredStack backref, ItemStack itemStack) {
            this.resolved = false;
            this.backref = backref;
            this.nItemsToTake = itemStack.getCount();
            this.itemStackToExtract = itemStack;
        }

        /**
         *
         * @param approved weather to approve or reject the transfer
         * @return the number of items remaining in the container after this transfer has resolved
         */
        public int resolveWith(boolean approved) {
            if (this.resolved) throw new RuntimeException("Transfer has already resolved! how?");
            int remainingItems;
            if (approved) {
                remainingItems = this.backref.approveOutTransfer(nItemsToTake);
            } else {
                remainingItems = this.backref.rejectOutTransfer(nItemsToTake);
            }
            this.resolved = true;
            return remainingItems;
        }
    }
}
