package com.antoniodev.habit_tracker.repository;


import com.antoniodev.habit_tracker.model.Habit;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface HabitRepository extends MongoRepository<Habit, String> {
    
    List<Habit> findByUserName(String userName);
}