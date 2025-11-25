package dev.tobynguyen27.codebebelib.inventory;

import dev.tobynguyen27.codebebelib.utils.ArrayUtils;
import java.util.Arrays;
import java.util.Objects;
import lombok.NonNull;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class InventorySimple implements Container {

    public ItemStack[] items;
    public int limit;
    public String name;

    public InventorySimple(ItemStack[] items, int limit, String name) {
        this.items = items;
        ArrayUtils.fillArray(items, ItemStack.EMPTY, (Objects::isNull));
        this.limit = limit;
        this.name = name;
    }

    public InventorySimple(ItemStack[] items, String name) {
        this(items, 64, name);
    }

    public InventorySimple(ItemStack[] items, int limit) {
        this(items, limit, "inv");
    }

    public InventorySimple(ItemStack[] items) {
        this(items, 64, "inv");
    }

    public InventorySimple(int size, int limit, String name) {
        this(new ItemStack[size], limit, name);
    }

    public InventorySimple(int size, int limit) {
        this(size, limit, "inv");
    }

    public InventorySimple(int size, String name) {
        this(size, 64, name);
    }

    public InventorySimple(int size) {
        this(size, 64, "inv");
    }

    @Override
    public int getContainerSize() {
        return items.length;
    }

    @Override
    public boolean isEmpty() {
        return ArrayUtils.count(items, (stack -> !stack.isEmpty())) <= 0;
    }

    @Override
    @NonNull
    public ItemStack getItem(int slot) {
        return items[slot];
    }

    @Override
    @NonNull
    public ItemStack removeItem(int slot, int amount) {
        return InventoryUtils.decrStackSize(this, slot, amount);
    }

    @Override
    @NonNull
    public ItemStack removeItemNoUpdate(int slot) {
        return InventoryUtils.removeStackFromSlot(this, slot);
    }

    @Override
    public void setItem(int slot, @NonNull ItemStack stack) {
        items[slot] = stack;
        setChanged();
    }

    @Override
    public int getMaxStackSize() {
        return limit;
    }

    @Override
    public boolean stillValid(@NonNull Player player) {
        return true;
    }

    @Override
    public boolean canPlaceItem(int index, @NonNull ItemStack stack) {
        return true;
    }

    @Override
    public void setChanged() {}

    @Override
    public void startOpen(@NonNull Player player) {}

    @Override
    public void stopOpen(@NonNull Player player) {}

    @Override
    public void clearContent() {
        Arrays.fill(items, ItemStack.EMPTY);
    }
}
