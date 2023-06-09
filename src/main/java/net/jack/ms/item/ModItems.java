package net.jack.ms.item;

import net.jack.ms.ModifiedSurvival;
import net.jack.ms.sound.ModSounds;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, ModifiedSurvival.MOD_ID);

    public static final RegistryObject<Item> PLASTIC = ITEMS.register("plastic",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
            public static final RegistryObject<Item> BANANA = ITEMS.register("banana",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(ModFoods.BANANA).stacksTo(16)));
    public static final RegistryObject<Item> REVERB_FRAGMENT = ITEMS.register("reverb_fragment", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));

    public static final RegistryObject<Item> TITANIUM_FRAGMENT = ITEMS.register("titanium_fragment", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));

    public static final RegistryObject<Item> TITANIUM = ITEMS.register("titanium", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));

    public static final RegistryObject<Item> GANGNAM_STYLE_RECORD = ITEMS.register("gangnam_style_music_disc", () -> new RecordItem(4, () -> ModSounds.gangnamstyle.get(), new Item.Properties().tab(CreativeModeTab.TAB_MISC).stacksTo(1)));

    public static final RegistryObject<SwordItem> TITANIUM_SWORD = ITEMS.register("titanium_sword", () -> new SwordItem(ModToolTiers.TITANIUM, 5, -2.5F, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    public static final RegistryObject<PickaxeItem> TITANIUM_PICKAXE = ITEMS.register("titanium_pickaxe", () -> new PickaxeItem(ModToolTiers.TITANIUM, 2, -2.8F, new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));

    public static final RegistryObject<AxeItem> TITANIUM_AXE = ITEMS.register("titanium_axe", () -> new AxeItem(ModToolTiers.TITANIUM, 10.0F, -2.8F, new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));

    public static final RegistryObject<ShovelItem> TITANIUM_SHOVEL = ITEMS.register("titanium_shovel", () -> new ShovelItem(ModToolTiers.TITANIUM, 3.0F, -3.0F, new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));

    public static final RegistryObject<HoeItem> TITANIUM_HOE = ITEMS.register("titanium_hoe", () -> new HoeItem(ModToolTiers.TITANIUM, 0, 0.0F, new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));

    public static final RegistryObject<Item> TITANIUM_HELMET = ITEMS.register("titanium_helmet", () -> new ArmorItem(ModArmorMaterials.TITANIUM, EquipmentSlot.HEAD, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    public static final RegistryObject<Item> TITANIUM_CHESTPLATE = ITEMS.register("titanium_chestplate", () -> new ArmorItem(ModArmorMaterials.TITANIUM, EquipmentSlot.CHEST, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    public static final RegistryObject<Item> TITANIUM_LEGGINGS = ITEMS.register("titanium_leggings", () -> new ArmorItem(ModArmorMaterials.TITANIUM, EquipmentSlot.LEGS, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    public static final RegistryObject<Item> TITANIUM_BOOTS = ITEMS.register("titanium_boots", () -> new ArmorItem(ModArmorMaterials.TITANIUM, EquipmentSlot.FEET, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
