package com.ndy.worker.util;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class InventoryUtility {

    public static boolean isFull(Inventory inventory) {
        int invSize = inventory.getSize();
        int count = 0;

        for(int i = 0; i < inventory.getSize(); i++) {
            ItemStack itemStack = inventory.getItem(i);

            if(itemStack != null && itemStack.getType() != Material.AIR) count++;
        }

        return invSize == count;
    }

    public static int getEmptySpace(Inventory inventory) {
        int space = 0;

        for(int i = 0; i < inventory.getSize(); i++) {
            ItemStack itemStack = inventory.getItem(i);

            if(itemStack == null || itemStack.getType() == Material.AIR) space++;
        }

        return space;
    }

    public static ItemStack[] getItemAndDelete(Inventory inventory, int count) {
        ItemStack[] itemStacks = new ItemStack[count];

        for(int i = 0; i < inventory.getSize(); i++) {
            if(i == count) break;

            ItemStack item = inventory.getItem(i);

            if(item == null) item = new ItemStack(Material.AIR);

            itemStacks[i] = item;

            inventory.setItem(i, new ItemStack(Material.AIR));
        }

        return itemStacks;
    }

    public static void addItems(Inventory inventory, ItemStack[] itemStacks) {
        for(ItemStack itemStack : itemStacks) {
            if(itemStack == null) continue;

            inventory.addItem(itemStack);
        }
    }

    public static ItemStack getItem(Inventory inventory, Material material, int amount) {
        for(int i = 0; i < inventory.getSize(); i++) {
            ItemStack itemStack = inventory.getItem(i);

            if(itemStack == null || itemStack.getType() == Material.AIR) continue;

            if(itemStack.getType() == material) {
                ItemStack clone = itemStack.clone();
                int itemAmount = itemStack.getAmount();

                if(itemAmount >= amount) itemStack.setType(Material.AIR);
                else {
                    itemStack.setAmount(itemAmount - amount);
                    clone.setAmount(amount);
                }

                return clone;
            }
        }

        return null;
    }

    /**
     * @param zeroSizeMaterial 만약 뽑을 아이템이 없다면 해당 Material ItemStack을 반환
     * */
    public static ItemStack getRandomItem(Inventory inventory, Material zeroSizeMaterial) {
        List<ItemStack> list = new ArrayList<>();

        for(int i = 0; i < inventory.getSize(); i++) {
            ItemStack itemStack = inventory.getItem(i);

            if(itemStack == null || itemStack.getType() == Material.AIR) continue;

            list.add(itemStack);
        }

        return list.size() > 0 ? list.get((int) (Math.random() * list.size())) : new ItemStack(zeroSizeMaterial);
    }

    public static boolean isEmpty(Inventory inventory) {
        int count = 0;

        for(int i = 0; i < inventory.getSize(); i++) {
            ItemStack itemStack = inventory.getItem(i);

            if(itemStack == null || itemStack.getType() == Material.AIR) continue;

            count++;
        }

        return count == 0;
    }

}
