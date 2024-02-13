package net.jack.ms.screen.slot;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ModLavaFuelSlot extends SlotItemHandler {
    public ModLavaFuelSlot(IItemHandler itemHandler, int index, int x, int y) {
        super(itemHandler, index, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return ModLavaFuelSlot.isLavaFuel(stack);
    }

    @Override
    public int getMaxStackSize(ItemStack pStack) {
        return ModLavaFuelSlot.isLavaFuel(pStack) ? 1 : super.getMaxStackSize(pStack);
    }

    public static boolean isLavaFuel(ItemStack stack) {
        return stack.is(Items.LAVA_BUCKET);
    }
}
