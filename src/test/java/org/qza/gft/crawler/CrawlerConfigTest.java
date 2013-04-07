/**
 * 
 */
package org.qza.gft.crawler;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author qza
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class CrawlerConfigTest extends BaseConfigTest {

	@Test
	public void testConfiguration() {
		assertTrue("Configuration loaded", true);
	}

}
