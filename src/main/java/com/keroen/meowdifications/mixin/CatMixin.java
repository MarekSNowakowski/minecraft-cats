package com.keroen.meowdifications.mixin;

import com.keroen.meowdifications.entity.ai.UseCatBedGoal;
import com.keroen.meowdifications.entity.ai.UseLitterBoxGoal;
import com.keroen.meowdifications.entity.ai.UseScratcherGoal;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.feline.Cat;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Cat.class)
public abstract class CatMixin extends TamableAnimal {
	protected CatMixin(EntityType<? extends TamableAnimal> entityType, Level level) {
		super(entityType, level);
	}

	@Inject(method = "registerGoals", at = @At("TAIL"))
	private void meowdifications$injectCustomCatGoals(CallbackInfo ci) {
		Cat cat = (Cat) (Object) this;
		this.goalSelector.addGoal(5, new com.keroen.meowdifications.entity.ai.PlayWithToyGoal(cat));
		this.goalSelector.addGoal(6, new com.keroen.meowdifications.entity.ai.CatAffectionGoal(cat));
		this.goalSelector.addGoal(6, new UseCatBedGoal(cat, 1.1, 16, 4));
		this.goalSelector.addGoal(7, new UseScratcherGoal(cat, 1.0, 12, 3));
		this.goalSelector.addGoal(7, new UseLitterBoxGoal(cat, 1.0, 16, 3));
		this.goalSelector.addGoal(8, new com.keroen.meowdifications.entity.ai.CatKneadGoal(cat));
	}
}
