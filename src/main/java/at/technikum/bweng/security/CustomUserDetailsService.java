package at.technikum.bweng.security;

import at.technikum.bweng.entity.User;
import at.technikum.bweng.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // instance a new user container
        User user = this.loadByName(username);
        if (user == null) {
            user = this.loadByEmail(username);
        }
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        if (!user.getActive()) {
            throw new UsernameNotFoundException(username);
        }
        return new UserPrincipal(user.getId(), user.getUsername(), user.getPassword(), user.getRole());

    }

    private User loadByName(String username){
        try {
            return userService.findByUsername(username);
        } catch (EntityNotFoundException exception){
            return null;
        }
    }

    private User loadByEmail(String email){
        try {
            return userService.findByEmail(email);
        } catch (EntityNotFoundException exception){
            return null;
        }
    }


    /*
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            var user = userService.findByUsername(username);
            return new UserPrincipal(user.getId(), user.getUsername(), user.getPassword(), user.getRole());
        } catch (EntityNotFoundException exception) {
            try{
                var user = userService.findByEmail(username);
                return new UserPrincipal(user.getId(), user.getUsername(), user.getPassword(), user.getRole());
            }catch (EntityNotFoundException exception2)
            {
                throw new UsernameNotFoundException(username);
            }
        }
    }

     */
}
