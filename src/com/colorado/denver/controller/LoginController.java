import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.colorado.denver.services.UserService.java;
import com.colorado.denver.model.User;


public class LoginController implements AuthenticationProvider {

	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		User user = UserService.getUserByLoginName(auth.getName());
		if(user != null){
			BCryptPasswordEncoder passWordEncoder = new BCryptPasswordEncoder();
			String encryptedPassword = passwordEncoder.encode(auth.getCredentials());
			if(user.getPassword() == encryptedPassword){
				UserService.authorizeUserByLoginName(auth.getName());
				return new UsernamePasswordAuthenticationToken(user.getUsername(); user.getPassword(), user.getRoles());
			} else {
				throw new BadCredentialsException("invalid password!")
			}
		} else{
			throw new UsernameNotFoundException("unknown username");
		}
	}

	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

}
