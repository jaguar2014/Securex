package byAJ.Securex.configs;

import byAJ.Securex.models.AppRole;
import byAJ.Securex.models.AppUser;
import byAJ.Securex.repositories.AppUserRepository;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class SSUDS implements UserDetailsService {

    private AppUserRepository userRepo;


    public SSUDS(AppUserRepository userRepository) {
        this.userRepo = userRepository;
    }

  /*
   The loadbyUsername method attempts to create an authentication string to be used to authenticate the user who logs in.
   See the code below for the format of the user that is returned to Spring security, and used by the authentiation manager builder
   in the security configuration file.
*/
// =========================================================================================================================================
// UNCOMMENT THE FOLLOWING CODE TO SEE HOW IT WORKS

    /*   @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            // Lines 34 and 37 are put in a separate method for readability. The roles assigned to each user are converted into roles that
            //can be read by Spring Security.
            Set <GrantedAuthority> userAuthorities = new HashSet<>();
            userAuthorities.add(new SimpleGrantedAuthority("ADMIN"));
            return new User("afua","password",userAuthorities);
        }*/
//  ==========================================================================================================================================
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Set<GrantedAuthority> userAuthorities = new HashSet<>();
        AppUser thisUser = userRepo.findAppUserByUsername(username);
        return new User(thisUser.getUsername(),thisUser.getPassword(),grantedAuthorities(thisUser));
    }

    public Set <GrantedAuthority> grantedAuthorities(AppUser user)
    {
        Set <GrantedAuthority> userAuthorities = new HashSet<>();
        for(AppRole eachRole: user.getRoles())
        {
            userAuthorities.add(new SimpleGrantedAuthority(eachRole.getRoleName()));
        }
        return userAuthorities;
    }

}
