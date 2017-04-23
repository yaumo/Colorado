package com.colorado.denver.services.user;

import com.colorado.denver.model.User;

public interface IUserService {

	User registerNewUserAccount(User user);

	void saveRegisteredUser(User user);

	void deleteUser(User user);

	User findUserByUserName(String userName);

	User getUserByID(long id);

	void changeUserPassword(User user, String password);

	boolean checkIfValidOldPassword(User user, String password);

}