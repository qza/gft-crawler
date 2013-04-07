package org.qza.gft.crawler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class DuplicateEntriesTest extends BaseConfigTest {

	final List<String> links = new ArrayList<String>();

	@Test
	public void test() {
		int duplicates = 0;
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader("src/main/resources/results-50000.txt");
			br = new BufferedReader(fr);
			String s = null;
			while ((s = br.readLine()) != null) {
				String link = s.trim();
				if (links.contains(link)) {
					System.out.println("DUPLICATE ::: " + link);
					duplicates++;
				} else {
					links.add(link);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (fr != null) {
					fr.close();
				}
			} catch (Exception ex1) {
				// ignore
			}
			System.out.println("DUPLICATES: " + duplicates);
		}
	}

}
