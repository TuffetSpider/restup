package net.tuffetspider.restup.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class SatiateEffect extends StatusEffect {
    protected SatiateEffect(StatusEffectCategory category, int color) {
        super(StatusEffectCategory.BENEFICIAL, color);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return super.canApplyUpdateEffect(duration, amplifier);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {

        super.applyUpdateEffect(entity, amplifier);
    }
}
