package org.brd.bloom.controllers;

import org.brd.bloom.engine.BloomFilter;
import org.brd.bloom.exceptions.BloomFilterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class BloomFilterOperationController
{
	@Autowired
	private BloomFilter bloomFilter;

	@GetMapping("/search")
	public boolean search(@RequestParam String input)
	{
		boolean isPresent = false;
		try
		{
			System.out.println(input);
			isPresent = bloomFilter.fastSearch(input);

			if (isPresent)
				return true;
			else
				return false;

		} 
		catch (Exception e)
		{
			e.printStackTrace();
			throw new BloomFilterException("Problem Occured While Searching In Bloom Filter...", e);
		}

	}

}
