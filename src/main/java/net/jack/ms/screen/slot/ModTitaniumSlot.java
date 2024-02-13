package net.jack.ms.screen.slot;

import net.jack.ms.item.ModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ModTitaniumSlot extends SlotItemHandler {
    public ModTitaniumSlot(IItemHandler itemHandler, int index, int x, int y) {
        super(itemHandler, index, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return ModTitaniumSlot.isTitanium(stack);
    }

    @Override
    public int getMaxStackSize(ItemStack pStack) {
        return ModTitaniumSlot.isTitanium(pStack) ? 1 : super.getMaxStackSize(pStack);
    }

    public static boolean isTitanium(ItemStack stack) {
        return stack.is(ModItems.TITANIUM.get());
    }
}
