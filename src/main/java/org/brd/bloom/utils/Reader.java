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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

@Component
public class Reader<T>
{
	private static final Logger LOG = Logger.getLogger(Reader.class.getName());
	private final String FILE_NAME = "data-storage-4000.txt";
	
	public List<T> read() throws Exception
	{
		List<T> list = null;
		Resource resource = null;
		Stream<String> stream = null;
		
		try
		{
			resource = new ClassPathResource(FILE_NAME);
			stream = Files.lines(Paths.get(resource.getURI()));
			
			list = (List<T>) stream.collect(Collectors.toList());
		}
		catch(Exception ex)
		{
			LOG.log(Level.SEVERE, "Failed reading", ex);
			throw new FailedReadingFromStore("Failed Reading From Store..", ex);
		}
		finally 
		{
			if(stream != null)
				stream.close();
		}
		return list;
	}
}
