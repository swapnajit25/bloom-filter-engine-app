package org.brd.bloom.exceptions;

public class BloomFilterCreationException extends RuntimeException
{
	public BloomFilterCreationException(String errorMsg, Throwable err)
	{
		super(errorMsg, err);
	}
}
