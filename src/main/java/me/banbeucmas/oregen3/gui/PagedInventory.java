package me.banbeucmas.oregen3.gui;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public abstract class PagedInventory<E> implements InventoryHandler {
    private ItemStack[] slots;
    private SlotAction[] slotActions;

    @Getter
    private int size;
    @Getter
    private String title;
    @Getter
    private int page;
    private int index;
    @Getter
    private List<E> contents;
    private boolean hideBottomBar;

    public PagedInventory(List<E> contents, String title) {
        this(contents, title, false, 1);
    }

    public PagedInventory(List<E> contents, String title, int page) {
        this(contents, title, false, page);
    }

    public PagedInventory(List<E> contents, String title, boolean hideBottomBar) {
        this(contents, title, hideBottomBar, 1);
    }

    public PagedInventory(List<E> contents, String title, boolean hideBottomBar, int page) {
        this.contents = contents;
        this.hideBottomBar = hideBottomBar;
        this.title = title;
        setPage(page);
    }

    public abstract ItemStack getContentIcon(E content);

    public abstract void getContentAction(E content, InventoryClickEvent event);

    public abstract void addOtherIcons();

    public void setPage(int page) {
        this.page = page;
        this.index = (page - 1) * 45;

        int size = 9;
        if (contents.size() > index) {
            size *= Math.min(((int) Math.ceil((double) (contents.size() - index) / size)) + 1, 6);
        }
        if (hideBottomBar && contents.size() <= 45) size -= 9;
        this.size = size;

        //Overriding our values
        slotActions = new SlotAction[size];
        slots = new ItemStack[size];
    }

    public void setItemWithAction(int slot, ItemStack item, SlotAction action) {
        slots[slot] = item;
        slotActions[slot] = action;
    }

    @Override
    public Inventory getInventory() {
        addOtherIcons();
        for (int i = index; i < Math.min(index + 45, contents.size()); i++) {
            int idx = i;
            setItemWithAction(i - index, getContentIcon(contents.get(i)), (event) -> getContentAction(contents.get(idx), event));
        }

        if (!(hideBottomBar && contents.size() <= 45)) {
            int size = getSize();
            if (contents.size() > index + 45) {
                setItemWithAction(size - 2, GUICommons.NEXT_PAGE_ICON,
                        (event) -> {
                            setPage(page + 1);
                            event.getWhoClicked().openInventory(this.getInventory());
                });
            }
            if (page > 1) {
                setItemWithAction(size - 3, GUICommons.PREVIOUS_PAGE_ICON,
                        (event) -> {
                            setPage(page - 1);
                            event.getWhoClicked().openInventory(this.getInventory());
                });
            }
            for (int i = size - 9; i < size; i++) {
                if (slots[i] == null)
                    slots[i] = GUICommons.BORDER_ICON;
            }
        }
        Inventory inventory = Bukkit.createInventory(this, size, title.replaceAll("%page%", String.valueOf(page)));
        for (int i = 0; i < size; i++) {
            if (slots[i] != null) {
                inventory.setItem(i, slots[i]);
            }
        }
        return inventory;
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        SlotAction slotAction = slotActions[event.getSlot()];
        if (slotAction != null) slotAction.accept(event);
        else event.setCancelled(true);
    }

    @FunctionalInterface
    public interface SlotAction {
        void accept(InventoryClickEvent slot);
    }
}
