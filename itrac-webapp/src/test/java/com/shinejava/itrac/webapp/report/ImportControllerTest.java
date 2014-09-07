package com.shinejava.itrac.webapp.report;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class ImportControllerTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testParseWrikeRss() {
		
		StringBuilder xml = new StringBuilder();
		xml.append("<feed xmlns='http://www.w3.org/2005/Atom' xmlns:rdf='http://www.w3.org/1999/02/22-rdf-syntax-ns#' xmlns:sy='http://purl.org/rss/1.0/modules/syndication/' xmlns:dc='http://purl.org/dc/elements/1.1/' xmlns:taxo='http://purl.org/rss/1.0/modules/taxonomy/'>");
		xml.append("<entry>");
		xml.append("<title>");
		xml.append("Work out an easy way to let client have the ability to custome the chart color");
		xml.append("</title> ");
		xml.append("<link rel='alternate' href='https://www.wrike.com/open.htm?id=14276775'/> ");
		xml.append("<author> ");
		xml.append("<name>Qin baoxian</name> ");
		xml.append("</author> ");
		xml.append("<updated>2013-08-22T14:55:09Z</updated> ");
		xml.append("<published>2013-08-21T14:55:09Z</published> ");
		xml.append("<summary type='HTML'>abc def");
		xml.append("</summary> ");
		xml.append("<dc:creator>Qin baoxian</dc:creator> ");
		xml.append("<dc:date>2013-08-22T14:55:09Z</dc:date> ");
		xml.append("</entry> ");
		xml.append("</feed> ");
		
		ImportController importController = new ImportController();
		List<Map<String, String>> list =  (List<Map<String, String>>)importController.parseWrikeRss(xml.toString());
		
		assertNotNull(list);
		
		Map<String, String> map = list.get(0);
		assertNotNull(map);
		
		assertEquals(map.get("title"), "Work out an easy way to let client have the ability to custome the chart color");
		assertEquals(map.get("link"), "https://www.wrike.com/open.htm?id=14276775");
		assertEquals(map.get("author"), "Qin baoxian");
		assertEquals(map.get("updated"), "2013-08-22T14:55:09Z");
	}

}
