package org.brd.bloom.common;

public enum BloomFilterEnum
{
	NUMBER_OF_BITS(64000);

	private int value;

	private BloomFilterEnum(int value)
	{
		this.value = value;
	}

	public int getValue()
	{
		return value;
	}
}
