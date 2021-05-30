package org.brd.bloom.exceptions;

public class BloomFilterSearchException extends RuntimeException
{
	public BloomFilterSearchException(String errorMsg)
	{
		super(errorMsg);
	}
}
