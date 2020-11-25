package com.example.demoH2JPA.url;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class urlOutput {
	public static void main(String[] args) {
		String bodyFragment=
				"<div><href=\"documention\">Stack Overflow Documention</a></div>";
		
		Document doc = Jsoup.parseBodyFragment(bodyFragment);
		String link = doc.select("div > a").first().attr("href");
		
		System.out.println(link);
	}
}
