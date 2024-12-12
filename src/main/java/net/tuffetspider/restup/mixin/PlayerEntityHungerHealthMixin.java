package net.tuffetspider.restup.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.tuffetspider.restup.effect.ModEffects;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityHungerHealthMixin{
	@Shadow protected HungerManager hungerManager;

	@Shadow @Final private PlayerInventory inventory;

	@Inject(at=@At("TAIL"),method = "wakeUp(ZZ)V")
		private void consumeHungerSetHealth(boolean skipSleepTimer, boolean updateSleepingPlayers, CallbackInfo ci){
		if(this.inventory.player instanceof ServerPlayerEntity player&&player.getWorld().getTimeOfDay()==24000) {
			player.clearStatusEffects();
			player.addStatusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 72000, Math.max(Math.min((int) (Random.create().nextGaussian() * ((double) this.hungerManager.getFoodLevel() / 7) + 3),6),1),false,false));
			player.getHungerManager().setSaturationLevel(0);
			player.getHungerManager().setFoodLevel(6);
		}
		}



	@ModifyReturnValue(at=@At("RETURN"),method = "createPlayerAttributes")
    private static DefaultAttributeContainer.Builder setHealthBase(DefaultAttributeContainer.Builder original){
		return original.add(EntityAttributes.GENERIC_MAX_HEALTH,12);
	}
	@Inject(at=@At("TAIL"),method = "eatFood")
	private void satiateUntilTime(World world, ItemStack stack, CallbackInfoReturnable<ItemStack> cir){
            PlayerEntity player = this.inventory.player;
			if (world.getTimeOfDay()>1000&&world.getTimeOfDay()<6000){
				player.addStatusEffect(new StatusEffectInstance(ModEffects.SATIATE.get(), (int) (6000-world.getTimeOfDay()),0,true,true));
		}
			if (world.getTimeOfDay()>6000&&world.getTimeOfDay()<13000){
			player.addStatusEffect(new StatusEffectInstance(ModEffects.SATIATE.get(), (int) (13000-world.getTimeOfDay()),0,true,true));
		}
			if (world.getTimeOfDay()>13000&&world.getTimeOfDay()<18000){
			player.addStatusEffect(new StatusEffectInstance(ModEffects.SATIATE.get(), (int) (18000-world.getTimeOfDay()),0,true,true));
		}
			if (world.getTimeOfDay()>18000){
			player.addStatusEffect(new StatusEffectInstance(ModEffects.SATIATE.get(), (int) (25000-world.getTimeOfDay()),0,true,true));
		}
			if (world.getTimeOfDay()<1000){
			player.addStatusEffect(new StatusEffectInstance(ModEffects.SATIATE.get(), (int) (1000-world.getTimeOfDay()),0,true,true));
		}

	}
	@Inject(at=@At("TAIL"),method = "tick")
	private void cancelHunger(CallbackInfo ci){
		if (this.inventory.player.hasStatusEffect(ModEffects.SATIATE.get())){
			this.hungerManager.setFoodLevel(20);
			this.hungerManager.setSaturationLevel(6);
			this.hungerManager.update(this.inventory.player);
		}

	}



}

