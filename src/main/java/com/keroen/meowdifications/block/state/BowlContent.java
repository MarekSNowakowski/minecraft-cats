package com.keroen.meowdifications.block.state;

import net.minecraft.util.StringRepresentable;

public enum BowlContent implements StringRepresentable {
	EMPTY("empty"),
	WATER("water"),
	MILK("milk"),
	FISH("fish"),
	MEAT("meat");

	private final String name;

	BowlContent(String name) {
		this.name = name;
	}

	@Override
	public String getSerializedName() {
		return this.name;
	}

	public boolean isFood() {
		return this == FISH || this == MEAT;
	}

	public boolean isDrink() {
		return this == WATER || this == MILK;
	}
}
