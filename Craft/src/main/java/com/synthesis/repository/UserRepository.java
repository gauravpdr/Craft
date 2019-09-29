package com.synthesis.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.synthesis.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>{

	User findByUserIdInAndPaswd(String userName,String password);

	

	
}
