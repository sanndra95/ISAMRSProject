package rs.team15.repositoryTests;

import static org.junit.Assert.assertNotNull;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import rs.team15.model.Friendship;
import rs.team15.model.User;
import rs.team15.repository.FriendshipRepository;
import rs.team15.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FriendshipRepositoryTest {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	FriendshipRepository friendshipRepository;
	
	@Test
	public void findRequestsTest(){
		Collection<User> users = friendshipRepository.findRequests("pending", Long.parseLong("9"));
		assertNotNull(users);
	}
	
	@Test
	public void findSendersTest(){
		Collection<User> users = friendshipRepository.findSenders("accept", Long.parseLong("7"));
		assertNotNull(users);
	}
	
	@Test
	public void findReceiversTest(){
		Collection<User> users = friendshipRepository.findReceivers("pending", Long.parseLong("8"));
		assertNotNull(users);
	}
	
	@Test
	public void getFriendshipTest(){
		Friendship f = friendshipRepository.getFriendship("accept",Long.parseLong("7"), Long.parseLong("8"));
		assertNotNull(f);
	}
	@Test
	public void createFriendshipRepository(){
		/*User u = new User(Long.parseLong("1"),"milana.becejac@gmail.com","Milana","Becejac","pass");
		userRepository.save(u);
		User u2 = new User(Long.parseLong("2"),"rik.becejac@gmail.com","Rik","Becejac","pass");
		userRepository.save(u2);
		Friendship f = new Friendship(u,u2,"pending");
		friendshipRepository.save(f);*/
		Collection<User> users = friendshipRepository.findRequests("pending", Long.parseLong("9"));
		assertNotNull(users);
	}
	
	
}
