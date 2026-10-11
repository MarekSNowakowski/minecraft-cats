package com.keroen.meowdifications.item;

import com.keroen.meowdifications.registry.ModSoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CatnipMouseItem extends Item {
	public CatnipMouseItem(Item.Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult use(Level level, Player player, InteractionHand hand) {
		level.playSound(
			null,
			player.getX(),
			player.getY(),
			player.getZ(),
			ModSoundEvents.TOY_SQUEAK,
			SoundSource.PLAYERS,
			0.9f,
			1.2f + level.random.nextFloat() * 0.3f
		);
		player.getCooldowns().addCooldown(this, 10);
		return InteractionResult.SUCCESS;
	}

	@Override
	public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
		if (target instanceof Cat cat) {
			Level level = cat.level();
			level.playSound(
				null,
				cat.getX(),
				cat.getY(),
				cat.getZ(),
				ModSoundEvents.TOY_SQUEAK,
				SoundSource.NEUTRAL,
				1.0f,
				1.4f
			);
			if (!level.isClientSide()) {
				cat.setDeltaMovement(cat.getDeltaMovement().add(0, 0.3, 0));
			}
			return InteractionResult.SUCCESS;
		}
		return super.interactLivingEntity(stack, player, target, hand);
	}
}
