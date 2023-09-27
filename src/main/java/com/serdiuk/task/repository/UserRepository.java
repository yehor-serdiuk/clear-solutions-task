package com.serdiuk.task.repository;

import com.serdiuk.task.model.User;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {
    List<User> findAllByBirthDateBetween(Date from, Date to);
}
