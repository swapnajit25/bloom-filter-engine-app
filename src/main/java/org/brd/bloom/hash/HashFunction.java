package org.brd.bloom.hash;

import org.brd.bloom.common.BloomFilterEnum;
import org.springframework.stereotype.Component;

public class HashFunction
{
	private long prime;
    private long odd;

    public HashFunction(final long prime, final long odd) 
    {
        this.prime = prime;
        this.odd = odd;
    }

    public int getHashValue(final String word) 
    {
        int hash = word.hashCode();
        if (hash < 0) 
        {
            hash = Math.abs(hash);
        }
        return calculateHash(hash, prime, odd);
    }

    private int calculateHash(final int hash, final long prime, final long odd) 
    {
        return (int)((((hash % BloomFilterEnum.NUMBER_OF_BITS.getValue()) * prime) % BloomFilterEnum.NUMBER_OF_BITS.getValue()) * odd) % BloomFilterEnum.NUMBER_OF_BITS.getValue();
    }

}
