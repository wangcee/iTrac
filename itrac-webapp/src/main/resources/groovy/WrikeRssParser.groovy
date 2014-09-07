def feed = new XmlParser().parseText(xmlContent);

List list = [];
feed.entry.each{
	Map map = [:]
	map["title"] = it.title.text();
	map["author"] = it.author.name.text();
	map["updated"] = it.updated.text();
	map["link"] = it.link[0].attribute("href");
	
  	list.add(map);
}
return list;