/*    */ package net.jack.ms.sound;
/*    */ 
/*    */

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/*    */
/*    */ 
/*    */ 
/*    */ public class ModSounds
/*    */ {
/* 14 */   public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, "ms");
/*    */   
/* 16 */   public static final RegistryObject<SoundEvent> ageofthedesert = registerSoundEvent("ageofthedesert");
/* 17 */   public static final RegistryObject<SoundEvent> alternate = registerSoundEvent("alternate");
/* 18 */   public static final RegistryObject<SoundEvent> ancientwell = registerSoundEvent("ancientwell");
/* 19 */   public static final RegistryObject<SoundEvent> channeledanger = registerSoundEvent("channeledanger");
/* 20 */   public static final RegistryObject<SoundEvent> dustycavern = registerSoundEvent("dustycavern");
/* 21 */   public static final RegistryObject<SoundEvent> fallingfeathers = registerSoundEvent("fallingfeathers");
/* 22 */   public static final RegistryObject<SoundEvent> firedome = registerSoundEvent("firedome");
/* 23 */   public static final RegistryObject<SoundEvent> frozenriver = registerSoundEvent("frozenriver");
/* 24 */   public static final RegistryObject<SoundEvent> gardenhangout = registerSoundEvent("gardenhangout");
/* 25 */   public static final RegistryObject<SoundEvent> impendingdoom = registerSoundEvent("impendingdoom");
/* 26 */   public static final RegistryObject<SoundEvent> leafyplanting = registerSoundEvent("leafyplanting");
/* 27 */   public static final RegistryObject<SoundEvent> messagefromhell = registerSoundEvent("messagefromhell");
/* 28 */   public static final RegistryObject<SoundEvent> peacefultrack = registerSoundEvent("peacefultrack");
/* 29 */   public static final RegistryObject<SoundEvent> quickarcade = registerSoundEvent("quickarcade");
/* 30 */   public static final RegistryObject<SoundEvent> savetheworld = registerSoundEvent("savetheworld");
/* 31 */   public static final RegistryObject<SoundEvent> toweringheights = registerSoundEvent("toweringheights");
/* 32 */   public static final RegistryObject<SoundEvent> gangnamstyle = registerSoundEvent("gangnamstyle");
           public static final RegistryObject<SoundEvent> coolwhip_eleven = registerSoundEvent("coolwhip_eleven");
        public static final RegistryObject<SoundEvent> bro_get_on_modified = registerSoundEvent("get_on_modified");


/* 33 */   public static final RegistryObject<SoundEvent> teleport = registerSoundEvent("teleport");
    public static final RegistryObject<SoundEvent> elephant_ambient = registerSoundEvent("elephant_ambient");
/*    */   
/*    */   private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
/* 36 */     ResourceLocation id = new ResourceLocation("ms", name);
/* 37 */     return SOUND_EVENTS.register(name, () -> new SoundEvent(id));
/*    */   }
/*    */   
/*    */   public static void register(IEventBus eventBus) {
/* 41 */     SOUND_EVENTS.register(eventBus);
/*    */   }
/*    */ }


/* Location:              C:\Users\theja\Downloads\ms-2.0.jar!\net\jack\ms\sound\ModSounds.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */