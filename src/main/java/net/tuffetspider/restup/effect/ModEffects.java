package net.tuffetspider.restup.effect;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.tuffetspider.restup.RestUp;


public enum ModEffects {
    SATIATE("satiate", new SatiateEffect(StatusEffectCategory.BENEFICIAL,0));

    private final String pathName;
    private final StatusEffect effect;

    ModEffects(String pathName, StatusEffect effect) {
        this.pathName = pathName;
        this.effect = effect;
    }

    public static void registerAll() {
        for (ModEffects value : values()) {
            Registry.register(Registries.STATUS_EFFECT, new Identifier(RestUp.MOD_ID, value.pathName), value.effect);
        }
    }

    public StatusEffect get() {
        return effect;
    }
}
