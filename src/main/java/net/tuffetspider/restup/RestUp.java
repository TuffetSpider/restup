package net.tuffetspider.restup;

import net.fabricmc.api.ModInitializer;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.tuffetspider.restup.effect.ModEffects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestUp implements ModInitializer {
	public static final String MOD_ID = "rest-up";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModEffects.registerAll();
	}
}