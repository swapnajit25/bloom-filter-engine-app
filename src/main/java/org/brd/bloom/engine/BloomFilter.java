package org.brd.bloom.engine;

import java.util.BitSet;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.brd.bloom.common.BloomFilterEnum;
import org.brd.bloom.exceptions.BloomFilterCreationException;
import org.brd.bloom.exceptions.BloomFilterSearchException;
import org.brd.bloom.hash.HashFunction;
import org.brd.bloom.utils.Reader;
import org.springframework.stereotype.Component;

@Component
public class BloomFilter
{
	private static final Logger LOG = Logger.getLogger(BloomFilter.class.getName());
	
	private final static HashFunction H1 = new HashFunction(11, 9);
	private final static HashFunction H2 = new HashFunction(17, 15);
	private final static HashFunction H3 = new HashFunction(31, 29);
	private final static HashFunction H4 = new HashFunction(61, 59);

	BitSet bitSet = null;

	public BloomFilter()
	{
		// Create the Bloom Filter during instantiation 
		try
		{
			createBloomFilter();
		} 
		catch (Exception e)
		{
			// de-initialize the bit set
			LOG.log(Level.SEVERE, "Exception Creating Bloom Filter", e);
			bitSet = null;
		}
	}

	public boolean fastSearch(final String value) throws Exception
	{
		// Stop searching when Bloom Filter Failed initialization
		if(bitSet == null)
		{
			LOG.log(Level.INFO, "Bloom Filter is Not Raady, can't perform search through it");
			throw new BloomFilterSearchException("Bloom Filter is Not Raady, can't perform search through it");
		}
		
		String convValue = value.toLowerCase();
		return bitSet.get(H1.getHashValue(convValue))
				&& bitSet.get(H2.getHashValue(convValue))
				&& bitSet.get(H3.getHashValue(convValue))
				&& bitSet.get(H4.getHashValue(convValue));
	}

	public void createBloomFilter() throws Exception
	{
		try
		{
			// Creating Bit Array
			bitSet = new BitSet(BloomFilterEnum.NUMBER_OF_BITS.getValue());

			// Read the input store
			Reader<String> reader = new Reader<>();
			List<String> store = reader.read();

			Iterator<String> itr = store.iterator();
			while (itr.hasNext())
			{
				String val = itr.next().toLowerCase();
				bitSet.set(H1.getHashValue(val));
				bitSet.set(H2.getHashValue(val));
				bitSet.set(H3.getHashValue(val));
				bitSet.set(H4.getHashValue(val));
			}
			
			// Empty the store no longer required
			store.clear();
		} 
		catch (Exception ex)
		{
			throw new BloomFilterCreationException("Failed Creating Bloom Filter", ex);
		}
	}
	
	public void addNewWord()
	{
		
	}
}
