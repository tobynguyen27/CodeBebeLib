package dev.tobynguyen27.codebebelib.inventory;

import com.google.common.base.Objects;
import dev.tobynguyen27.codebebelib.utils.ItemUtils;
import lombok.NonNull;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NumericTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class InventoryUtils {

    /**
     * Static default implementation for IInventory method
     */
    @NonNull
    public static ItemStack decrStackSize(Container inv, int slot, int size) {
        ItemStack item = inv.getItem(slot);

        if (!item.isEmpty()) {
            if (item.getCount() <= size) {
                inv.setItem(slot, ItemStack.EMPTY);
                inv.setChanged();
                return item;
            }
            ItemStack itemstack1 = item.split(size);
            if (item.getCount() == 0) {
                inv.setItem(slot, ItemStack.EMPTY);
            } else {
                inv.setItem(slot, item);
            }

            inv.setChanged();
            return itemstack1;
        }
        return ItemStack.EMPTY;
    }

    /**
     * Static default implementation for IInventory method
     */
    public static ItemStack removeStackFromSlot(Container inv, int slot) {
        ItemStack stack = inv.getItem(slot);
        inv.setItem(slot, ItemStack.EMPTY);
        return stack;
    }

    /**
     * @return The quantity of items from addition that can be added to base
     */
    public static int incrStackSize(@NonNull ItemStack base, @NonNull ItemStack addition) {
        if (canStack(base, addition)) {
            return incrStackSize(base, addition.getCount());
        }

        return 0;
    }

    /**
     * @return The quantity of items from addition that can be added to base
     */
    public static int incrStackSize(@NonNull ItemStack base, int addition) {
        int totalSize = base.getCount() + addition;

        if (totalSize <= base.getMaxStackSize()) {
            return addition;
        } else if (base.getCount() < base.getMaxStackSize()) {
            return base.getMaxStackSize() - base.getCount();
        }

        return 0;
    }

    /**
     * NBT item saving function
     */
    public static ListTag writeItemStacksToTag(ItemStack[] items) {
        return writeItemStacksToTag(items, 64);
    }

    /**
     * NBT item saving function with support for stack sizes > 32K
     */
    public static ListTag writeItemStacksToTag(ItemStack[] items, int maxQuantity) {
        ListTag tagList = new ListTag();
        for (int i = 0; i < items.length; i++) {
            CompoundTag tag = new CompoundTag();
            tag.putShort("Slot", (short) i);
            items[i].save(tag);

            if (maxQuantity > Short.MAX_VALUE) {
                tag.putInt("Quantity", items[i].getCount());
            } else if (maxQuantity > Byte.MAX_VALUE) {
                tag.putShort("Quantity", (short) items[i].getCount());
            }

            tagList.add(tag);
        }
        return tagList;
    }

    /**
     * NBT item loading function with support for stack sizes > 32K
     */
    public static void readItemStacksFromTag(ItemStack[] items, ListTag tagList) {
        for (int i = 0; i < tagList.size(); i++) {
            CompoundTag tag = tagList.getCompound(i);
            int b = tag.getShort("Slot");
            items[b] = ItemStack.of(tag);
            Tag quantTag = tag.get("Quantity");
            if (quantTag instanceof NumericTag quant) {
                items[b].setCount(quant.getAsInt());
            }
        }
    }

    /**
     * Gets the maximum quantity of an item that can be inserted into inv
     */
    public static int getInsertibleQuantity(InventoryRange inv, @NonNull ItemStack stack) {
        int quantity = 0;
        stack = ItemUtils.copyStack(stack, Integer.MAX_VALUE);
        for (int slot : inv.slots) {
            quantity += fitStackInSlot(inv, slot, stack);
        }

        return quantity;
    }

    public static int getInsertibleQuantity(Container inv, @NonNull ItemStack stack) {
        return getInsertibleQuantity(new InventoryRange(inv), stack);
    }

    public static int fitStackInSlot(InventoryRange inv, int slot, ItemStack stack) {
        ItemStack base = inv.inv.getItem(slot);
        if (!canStack(base, stack) || !inv.canInsertItem(slot, stack)) {
            return 0;
        }

        int fit = !base.isEmpty()
                ? incrStackSize(base, inv.inv.getMaxStackSize() - base.getCount())
                : inv.inv.getMaxStackSize();
        return Math.min(fit, stack.getCount());
    }

    public static int fitStackInSlot(Container inv, int slot, @NonNull ItemStack stack) {
        return fitStackInSlot(new InventoryRange(inv), slot, stack);
    }

    /**
     * @param simulate If set to true, no items will actually be inserted
     * @return The number of items unable to be inserted
     */
    public static int insertItem(InventoryRange inv, @NonNull ItemStack stack, boolean simulate) {
        stack = stack.copy();
        for (int pass = 0; pass < 2; pass++) {
            for (int slot : inv.slots) {
                ItemStack base = inv.inv.getItem(slot);
                if ((pass == 0) == (base.isEmpty())) {
                    continue;
                }
                int fit = fitStackInSlot(inv, slot, stack);
                if (fit == 0) {
                    continue;
                }

                if (!base.isEmpty()) {
                    stack.shrink(fit);
                    if (!simulate) {
                        base.grow(fit);
                        inv.inv.setItem(slot, base);
                    }
                } else {
                    if (!simulate) {
                        inv.inv.setItem(slot, ItemUtils.copyStack(stack, fit));
                    }
                    stack.shrink(fit);
                }
                if (stack.getCount() == 0) {
                    return 0;
                }
            }
        }
        return stack.getCount();
    }

    public static int insertItem(Container inv, @NonNull ItemStack stack, boolean simulate) {
        return insertItem(new InventoryRange(inv), stack, simulate);
    }

    /**
     * Gets the stack in slot if it can be extracted
     */
    public static ItemStack getExtractableStack(InventoryRange inv, int slot) {
        ItemStack stack = inv.inv.getItem(slot);
        if (stack.isEmpty() || !inv.canExtractItem(slot, stack)) {
            return ItemStack.EMPTY;
        }

        return stack;
    }

    public static ItemStack getExtractableStack(Container inv, int slot) {
        return getExtractableStack(new InventoryRange(inv), slot);
    }

    public static boolean areStacksIdentical(@NonNull ItemStack stack1, @NonNull ItemStack stack2) {
        if (stack1.isEmpty() || stack2.isEmpty()) {
            return stack1 == stack2;
        }

        return stack1.getItem() == stack2.getItem()
                && stack1.getDamageValue() == stack2.getDamageValue()
                && stack1.getCount() == stack2.getCount()
                && Objects.equal(stack1.getTag(), stack2.getTag());
    }

    public static boolean canStack(@NonNull ItemStack stack1, @NonNull ItemStack stack2) {
        return stack1.isEmpty()
                || stack2.isEmpty()
                || (stack1.getItem() == stack2.getItem()
                                && (stack2.getDamageValue() == stack1.getDamageValue())
                                && ItemStack.tagMatches(stack2, stack1))
                        && stack1.isStackable();
    }

    /**
     * Consumes one item from slot in inv with support for containers.
     */
    public static void consumeItem(Container inv, int slot) {
        ItemStack stack = inv.getItem(slot);
        Item item = stack.getItem();

        ItemStack remainStack = item.getRecipeRemainder(stack);

        if (remainStack != null) {
            inv.setItem(slot, remainStack);
        } else {
            inv.removeItem(slot, 1);
        }
    }

    /**
     * Gets the size of the stack in a slot. Returns 0 on empty stacks
     */
    public static int stackSize(Container inv, int slot) {
        ItemStack stack = inv.getItem(slot);
        return stack.isEmpty() ? 0 : stack.getCount();
    }

    /**
     * Drops all items from inv using removeStackFromSlot
     */
    public static void dropOnClose(Player player, Container inv) {
        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.removeItemNoUpdate(i);
            if (!stack.isEmpty()) {
                player.drop(stack, false);
            }
        }
    }
}
