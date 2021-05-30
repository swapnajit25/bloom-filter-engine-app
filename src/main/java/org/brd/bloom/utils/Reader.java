package org.brd.bloom.utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.brd.bloom.exceptions.FailedReadingFromStore;
import org.springframework.stereotype.Component;

@Component
public class Reader<T>
{
	private static final Logger LOG = Logger.getLogger(Reader.class.getName());
	private final String fileName = "data-storage-4000.txt";
	
	public List<T> read() throws Exception
	{
		List<T> list = null;
		try(Stream<String> stream = Files.lines(Paths.get(Reader.class.getClassLoader().getResource(fileName).toURI())))
		{
			list = (List<T>) stream.collect(Collectors.toList());
		}
		catch(Exception ex)
		{
			LOG.log(Level.SEVERE, "Failed reading", ex);
			throw new FailedReadingFromStore("Failed Reading From Store..", ex);
		}
		return list;
	}
}
