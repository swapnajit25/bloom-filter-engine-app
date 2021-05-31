package org.brd.bloom.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.spi.FileSystemProvider;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.brd.bloom.exceptions.FailedReadingFromStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

@Component
public class Reader<T>
{
	private static final Logger LOG = LoggerFactory.getLogger(Reader.class);
	private final String FILE_NAME = "data-storage-4000.txt";
	
	public List<T> read() throws Exception
	{
		List<T> list = null;
		Resource resource = null;
		Stream<String> stream = null;
		FileSystem fs = null;
		
		try
		{
			resource = new ClassPathResource(FILE_NAME);
			URI uri = resource.getURI();
			Path path = null;
			
			if("jar".equals(uri.getScheme()))
			{
				LOG.info("INSIDE JAR.....");
				final Map<String, String> env = new HashMap<>();
				final String[] array = uri.toString().split("!");
				LOG.info("ARRAY: " + Arrays.toString(array));
				fs = FileSystems.newFileSystem(URI.create(array[0]), env);
				path = fs.getPath(array[1] + array[2]);
				LOG.info("PATH: " + path.toString());
			}
			
			LOG.info("OUT SIDE IF");
			stream = Files.lines(path);
			list = (List<T>) stream.collect(Collectors.toList());
		}
		catch(Exception ex)
		{
			LOG.error("ERROR: Failed reading", ex);
			throw new FailedReadingFromStore("Failed Reading From Store..", ex);
		}
		finally 
		{
			if(stream != null)
				stream.close();
			try 
			{
				if(fs != null)
					fs.close();
			}
			catch (Exception e) 
			{
			}
			
		}
		return list;
	}
	
	private FileSystem initFileSystem(URI uri) throws IOException 
	{
	    try 
	    {
	        return FileSystems.newFileSystem(uri, Collections.emptyMap());
	    }
	    catch(IllegalArgumentException e) 
	    {
	        return FileSystems.getDefault();
	    }
	}
}
