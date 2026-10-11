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
			ItemStack tossedStack = stack.copyWithCount(1);
			if (!player.getAbilities().instabuild) {
				stack.shrink(1);
			}

			Vec3 look = player.getLookAngle();
			ItemEntity itemEntity = new ItemEntity(
				level,
				player.getX(),
				player.getEyeY() - 0.2,
				player.getZ(),
				tossedStack
			);
			itemEntity.setDeltaMovement(look.x * 0.7, look.y * 0.5 + 0.15, look.z * 0.7);
			itemEntity.setPickUpDelay(20);
			level.addFreshEntity(itemEntity);
		}

		player.getCooldowns().addCooldown(this, 10);
		return InteractionResult.SUCCESS;
	}
}
