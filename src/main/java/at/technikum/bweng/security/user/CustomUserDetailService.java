package at.technikum.bweng.security.user;

import at.technikum.bweng.entity.User;
import at.technikum.bweng.service.UserService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


// I guess this is a wrapper for the UserService


@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    /* Wrapper for UserService that attaches all the security stuff */

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userService.findByUsername(username);
            return new UserPrincipal(user.getId(), user.getUsername(), user.getPassword(), user.getRole());

        }catch (EntityNotFoundException e){
            throw new UsernameNotFoundException(username);
        }



    }


}
