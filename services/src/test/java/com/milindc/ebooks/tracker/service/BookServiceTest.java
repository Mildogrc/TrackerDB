package com.milindc.ebooks.tracker.service;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.milindc.ebooks.tracker.db.model.Book;
import com.milindc.ebooks.tracker.service.assembler.BookAssembler;
import com.milindc.ebooks.tracker.service.model.BookView;

public class BookServiceTest {

	BookService bs = new BookService();
	
//	@Test
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
		BookAssembler ba = new BookAssembler();
		BookView bv = ba.to(b);
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(bv);
		System.out.println(json);
	}
	
}
