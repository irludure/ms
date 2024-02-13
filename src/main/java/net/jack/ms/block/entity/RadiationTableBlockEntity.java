package net.jack.ms.block.entity;

import net.jack.ms.bentityrecipes.BEntityRecipes;
import net.jack.ms.bentityrecipes.RadiationRecipe;
import net.jack.ms.screen.RadiationTableMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public class RadiationTableBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(4) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 72;
    private int fuelTime = 0;
    private int maxFuelTime = 0;

    public RadiationTableBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModBlockEntities.COBALT_BLASTER.get(), pWorldPosition, pBlockState);
        this.data = new ContainerData() {
            public int get(int index) {
                switch (index) {
                    case 0: return RadiationTableBlockEntity.this.progress;
                    case 1: return RadiationTableBlockEntity.this.maxProgress;
                    case 2: return RadiationTableBlockEntity.this.fuelTime;
                    case 3: return RadiationTableBlockEntity.this.maxFuelTime;
                    default: return 0;
                }
            }

            public void set(int index, int value) {
                switch(index) {
                    case 0: RadiationTableBlockEntity.this.progress = value; break;
                    case 1: RadiationTableBlockEntity.this.maxProgress = value; break;
                    case 2: RadiationTableBlockEntity.this.fuelTime = value; break;
                    case 3: RadiationTableBlockEntity.this.maxFuelTime = value; break;
                }
            }

            public int getCount() {
                return 4;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return new TextComponent("Cobalt Blaster");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        return new RadiationTableMenu(pContainerId, pInventory, this, this.data);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps()  {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag.put("inventory", itemHandler.serializeNBT());
        tag.putInt("blaster.progress", progress);
        tag.putInt("blaster.fuelTime", fuelTime);
        tag.putInt("blaster.maxFuelTime", maxFuelTime);
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        progress = nbt.getInt("blaster.progress");
        fuelTime = nbt.getInt("blaster.fuelTime");
        maxFuelTime = nbt.getInt("blaster.maxFuelTime");
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    private void consumeFuel() {
        if(!itemHandler.getStackInSlot(0).isEmpty()) {
            this.fuelTime = ForgeHooks.getBurnTime(this.itemHandler.extractItem(0, 1, false),
                    RecipeType.SMELTING);
            this.maxFuelTime = this.fuelTime;
        }
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, RadiationTableBlockEntity pBlockEntity) {
        if(isConsumingFuel(pBlockEntity)) {
            pBlockEntity.fuelTime--;
        }

        if(hasRecipe(pBlockEntity)) {
            if(hasFuelInFuelSlot(pBlockEntity) && !isConsumingFuel(pBlockEntity)) {
                pBlockEntity.consumeFuel();
                setChanged(pLevel, pPos, pState);
            }
            if(isConsumingFuel(pBlockEntity)) {
                pBlockEntity.progress++;
                setChanged(pLevel, pPos, pState);
                if(pBlockEntity.progress > pBlockEntity.maxProgress) {
                    craftItem(pBlockEntity);
                }
            }
        } else {
            pBlockEntity.resetProgress();
            setChanged(pLevel, pPos, pState);
        }
    }

    private static boolean hasFuelInFuelSlot(RadiationTableBlockEntity entity) {
        return !entity.itemHandler.getStackInSlot(0).isEmpty();
    }

    private static boolean isConsumingFuel(RadiationTableBlockEntity entity) {
        return entity.fuelTime > 0;
    }

    private static boolean hasRecipe(RadiationTableBlockEntity entity) {
        if (entity.itemHandler.getSlots() < 3) {
            return false; // Not enough slots
        }

        Item fuelItem = entity.itemHandler.getStackInSlot(0).getItem();
        Item topItem = entity.itemHandler.getStackInSlot(1).getItem();
        Item bottomItem = entity.itemHandler.getStackInSlot(2).getItem();
        Object[] ret = new Object[2];

        for (RadiationRecipe recipe : BEntityRecipes.RADIATION_TABLE) {
            if (recipe.fuel.equals(fuelItem) && recipe.top.equals(topItem) && recipe.bottom.equals(bottomItem)) {
                return true;
            }
        }
        return false; // No matching recipe found
    }

    private static Object[] hasRecipe_dualret(RadiationTableBlockEntity entity) {
        Object[] ret = new Object[2];
        if (entity.itemHandler.getSlots() < 3) {
            ret[0] = false;
            ret[1] = null;
            return ret; // Not enough slots
        }

        Item fuelItem = entity.itemHandler.getStackInSlot(0).getItem();
        Item topItem = entity.itemHandler.getStackInSlot(1).getItem();
        Item bottomItem = entity.itemHandler.getStackInSlot(2).getItem();

        for (RadiationRecipe recipe : BEntityRecipes.RADIATION_TABLE) {
            if (recipe.fuel.equals(fuelItem) && recipe.top.equals(topItem) && recipe.bottom.equals(bottomItem)) {
                ret[0] = true;
                ret[1] = recipe;
                return ret;
            }
        }
        ret[0] = false;
        ret[1] = null;
        return ret; // No matching recipe found
    }

    private static void craftItem(RadiationTableBlockEntity entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Object[] retArray = hasRecipe_dualret(entity);
        boolean found = (boolean) retArray[0];
        Object radiation = retArray[1];


        if (found) {
            if (radiation instanceof RadiationRecipe radiationRecipe) {
                entity.itemHandler.extractItem(1,1, false);
                entity.itemHandler.extractItem(2,1, false);

                entity.itemHandler.setStackInSlot(3, new ItemStack(radiationRecipe.result,
                        entity.itemHandler.getStackInSlot(3).getCount() + 1));

                entity.resetProgress();
            }
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack output) {
        return inventory.getItem(3).getItem() == output.getItem() || inventory.getItem(3).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory) {
        return inventory.getItem(3).getMaxStackSize() > inventory.getItem(3).getCount();
    }
}
