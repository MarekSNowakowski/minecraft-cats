package com.keroen.meowdifications.block.state;

import net.minecraft.util.StringRepresentable;

public enum LitterFillType implements StringRepresentable {
	EMPTY("empty"),
	SAND("sand"),
	LITTER("litter");

	private final String name;

	LitterFillType(String name) {
		this.name = name;
	}

	@Override
	public String getSerializedName() {
		return this.name;
	}
}
