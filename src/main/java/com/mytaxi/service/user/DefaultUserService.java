package com.mytaxi.service.user;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.mytaxi.dataaccessobject.UserRepository;
import com.mytaxi.domainobject.UserDO;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;

/**
 * Service to encapsulate the link between DAO and controller and to have
 * business logic for some user specific things.
 * <p/>
 */
@Service
public class DefaultUserService implements UserService {

	private static final Logger LOG = LoggerFactory.getLogger(DefaultUserService.class);

	private final UserRepository userRepository;

	public DefaultUserService(final UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 * Selects a user by id.
	 *
	 * @param userId
	 * @return found user
	 * @throws EntityNotFoundException
	 *             if no user with the given id was found.
	 */
	@Override
	public UserDO find(Long userId) throws EntityNotFoundException {
		return findUserChecked(userId);
	}

	/**
	 * Creates a new user.
	 *
	 * @param userDO
	 * @return
	 * @throws ConstraintsViolationException
	 *             if a user already exists with the given username, ... .
	 */
	@Override
	public UserDO create(UserDO userDO) throws ConstraintsViolationException {
		UserDO user;
		String s = "abcdabcd";
		char[] chrs = new char[s.length()];
		StringBuilder sb = new StringBuilder();
		for (int i =0;i<chrs.length;i++) {
			boolean temp = sb.toString().indexOf(s.charAt(i)) != -1 ? true : false;
            if(sb.toString().indexOf(s.charAt(i)) != -1 ? true : false)
                sb.append(s.charAt(i));
		}
		try {
			user = userRepository.save(userDO);
		} catch (DataIntegrityViolationException e) {
			LOG.warn("ConstraintsViolationException while creating a user: {}", userDO, e);
			throw new ConstraintsViolationException(e.getMessage());
		}
		return user;
	}


	@Override
	public UserDO findUserByName(String username) throws EntityNotFoundException {
		return userRepository.findByUsername(username);
	}

	@Override
	public List<UserDO> getUsers() throws EntityNotFoundException {
		return userRepository.findAll();
	}

	@Override
	public List<UserDO> findUsersByRole(String role) throws EntityNotFoundException {
		return userRepository.findByRole(role);
	}
	
	private UserDO findUserChecked(Long userId) throws EntityNotFoundException {
		return userRepository.findById(userId)
				.orElseThrow(() -> new EntityNotFoundException("Could not find entity with rating: " + userId));
	}

}
