package com.keroen.meowdifications.item;

import com.keroen.meowdifications.registry.ModItems;
import com.keroen.meowdifications.registry.ModSoundEvents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.feline.Cat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class CatBrushItem extends Item {
	public CatBrushItem(Item.Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity interactionTarget, InteractionHand hand) {
		if (interactionTarget instanceof Cat cat) {
			if (!player.level().isClientSide()) {
				ServerLevel serverLevel = (ServerLevel) player.level();

				// Brushing sound + purr
				serverLevel.playSound(null, cat.blockPosition(), SoundEvents.SHEEP_SHEAR, SoundSource.PLAYERS, 0.7f, 1.4f);
				serverLevel.playSound(null, cat.blockPosition(), ModSoundEvents.PURR_CONTENT, SoundSource.PLAYERS, 0.8f, 1.0f);

				// Hearts + fur particles
				serverLevel.sendParticles(ParticleTypes.HEART, cat.getX(), cat.getY() + 0.3, cat.getZ(), 2, 0.15, 0.1, 0.15, 0.02);

				// Drop cat fur tuft
				cat.spawnAtLocation(serverLevel, new ItemStack(ModItems.CAT_FUR_TUFT));

				// Damage brush
				EquipmentSlot slot = (hand == InteractionHand.MAIN_HAND) ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
				stack.hurtAndBreak(1, player, slot);

				// Cooldown
				player.getCooldowns().addCooldown(stack, 40);
			}

			return InteractionResult.SUCCESS;
		}

		return InteractionResult.PASS;
	}
}
