package net.jack.ms.block;

import net.jack.ms.ModifiedSurvival;
import net.jack.ms.block.custom.FusionRail;
import net.jack.ms.block.custom.RadiationTableBlock;
import net.jack.ms.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;
import java.util.function.ToIntFunction;

public class ModBlocks {
    public static ToIntFunction<BlockState> reverbLightLevel = BlockState -> 8;
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, ModifiedSurvival.MOD_ID);

    public static final RegistryObject<Block> TITANIUM_BLOCK = registerBlock("titanium_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(72f).requiresCorrectToolForDrops()), CreativeModeTab.TAB_MISC);

    public static final RegistryObject<Block> TITANIUM_REMNANT = registerBlock("titanium_remnant",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(32f).requiresCorrectToolForDrops()), CreativeModeTab.TAB_MISC);


    public static final RegistryObject<Block> REVERB_BLOCK = registerBlock("reverb_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(8f).requiresCorrectToolForDrops().lightLevel(reverbLightLevel)), CreativeModeTab.TAB_MISC);

    public static final RegistryObject<Block> FUSION_RAIL = registerBlock("fusion_rail",
            () -> new FusionRail(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(2f).lightLevel(reverbLightLevel)), CreativeModeTab.TAB_MISC);

    public static final RegistryObject<Block> RADIATION_TABLE = registerBlock("radiation_table",
            () -> new RadiationTableBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)), CreativeModeTab.TAB_MISC);





    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block,
                                                                            CreativeModeTab tab) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().tab(tab)));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}