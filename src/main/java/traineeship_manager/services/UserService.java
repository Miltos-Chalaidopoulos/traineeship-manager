package traineeship_manager.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import traineeship_manager.domainmodel.User;

@Service
public interface UserService {
	public void saveUser(User user);
    public boolean isUserPresent(User user);
}
