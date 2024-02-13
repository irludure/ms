package net.jack.ms.screen.slot;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ModRadiationFuelSlot extends SlotItemHandler {
    public ModRadiationFuelSlot(IItemHandler itemHandler, int index, int x, int y) {
        super(itemHandler, index, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return true; // return AbstractFurnaceBlockEntity.isFuel(stack) || ModRadiationFuelSlot.isBucket(stack);
    }

//    @Override
//    public int getMaxStackSize(ItemStack pStack) {
//        return ModRadiationFuelSlot.isBucket(pStack) ? 1 : super.getMaxStackSize(pStack);
//    }

//    public static boolean isBucket(ItemStack stack) {
//        return stack.is(Items.BUCKET);
//    }
}
