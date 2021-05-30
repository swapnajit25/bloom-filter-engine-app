package org.brd.bloom.exceptions;

public class FailedReadingFromStore extends RuntimeException
{
	public FailedReadingFromStore(String errorMsg, Throwable err)
	{
		super(errorMsg, err);
	}
}
