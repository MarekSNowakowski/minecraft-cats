package com.keroen.meowdifications.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class YarnBallItem extends Item {
	public YarnBallItem(Item.Properties properties) {
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
			SoundEvents.WOOL_PLACE,
			SoundSource.PLAYERS,
			0.7f,
			1.2f
		);

		if (!level.isClientSide()) {
			if (!player.getAbilities().instabuild) {
				stack.shrink(1);
			}

			Vec3 look = player.getLookAngle();
			com.keroen.meowdifications.entity.YarnBallEntity yarnBall = new com.keroen.meowdifications.entity.YarnBallEntity(
				level,
				player.getX(),
				player.getEyeY() - 0.2,
				player.getZ()
			);
			yarnBall.setDeltaMovement(look.x * 0.75, look.y * 0.4 + 0.15, look.z * 0.75);
			level.addFreshEntity(yarnBall);
		}

		player.getCooldowns().addCooldown(stack, 10);
		return InteractionResult.SUCCESS;
	}
}
