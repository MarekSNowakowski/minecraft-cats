package com.keroen.meowdifications.item;

import com.keroen.meowdifications.registry.ModSoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.animal.feline.Cat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class FeatherWandItem extends Item {
	public FeatherWandItem(Item.Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		level.playSound(
			null,
			player.getX(),
			player.getY(),
			player.getZ(),
			ModSoundEvents.FEATHER_SWISH,
			SoundSource.PLAYERS,
			0.8f,
			1.0f + level.getRandom().nextFloat() * 0.3f
		);

		player.getCooldowns().addCooldown(stack, 15);

		if (!level.isClientSide()) {
			AABB searchBox = player.getBoundingBox().inflate(8.0);
			List<Cat> nearbyCats = level.getEntitiesOfClass(Cat.class, searchBox);
			for (Cat cat : nearbyCats) {
				cat.getLookControl().setLookAt(player, 30.0f, 30.0f);
				if (level.getRandom().nextFloat() < 0.4f && cat.onGround()) {
					cat.setDeltaMovement(cat.getDeltaMovement().add(0, 0.25, 0));
				}
			}
		}

		return InteractionResult.SUCCESS;
	}
}
