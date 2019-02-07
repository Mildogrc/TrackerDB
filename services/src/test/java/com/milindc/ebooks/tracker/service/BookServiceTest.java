package com.milindc.ebooks.tracker.service;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.milindc.ebooks.tracker.db.model.Book;

public class BookServiceTest {

	BookService bs = new BookService();
	
	@Test
	public void testgetRemote() {
		System.out.println(bs.lookupRemote("038549081X"));
	}

	@Test
	public void testParseBook() throws IOException, URISyntaxException {
		String filename = "thhgttg.json";
		URL fs = this.getClass().getClassLoader().getResource(filename);
		File f = new File(fs.toURI());
		String s = FileUtils.readFileToString(f, "UTF-8");
		Book b = bs.parseBook(s);
		System.out.println(b);
	}
	
}
