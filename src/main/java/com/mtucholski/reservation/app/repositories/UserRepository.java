package com.mtucholski.reservation.app.repositories;

import com.mtucholski.reservation.app.model.User;

public interface UserRepository {

    void save(User user);
    void deleteUser(int id);
}
