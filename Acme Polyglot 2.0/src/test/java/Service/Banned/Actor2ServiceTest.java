package Service.Banned;


import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;


import services.ActorService;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
					"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class Actor2ServiceTest  extends AbstractTest {
	
	// Service under test ------------------------

	
	// Supporting services -----------------------
	@Autowired
	private ActorService actorService;


	// A user who is authenticated as an administrator
		// must be banned and disbanned a actor
	

	
	// POSITIVE TEST CASE: An administrator can banned and disbanned a actor
	@Transactional
	@Test
	public void testBannedNormal() {
		
		authenticate("agent2");
		int actorId = actorService.findByPrincipal().getId();
		unauthenticate();	
	
		
		
		authenticate("admin");
		
		
		
		actorService.disable(actorId);
		actorService.enable(actorId);
		unauthenticate();	

	}
	

	
	

}
