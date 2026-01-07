package com.antoniodev.habit_tracker.service;

import com.antoniodev.habit_tracker.model.Habit;
import com.antoniodev.habit_tracker.repository.HabitRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class HabitService {
    private final HabitRepository habitRepository;

    public HabitService(HabitRepository habitRepository) {
        this.habitRepository = habitRepository;
    }

    public List<Habit> getHabitsByUser(String user){
        return habitRepository.findByUserName(user);
    }
    public List<Habit> getAllHabits() {
        return habitRepository.findAll();
    }

    public Optional<Habit> getHabitById(String id) {
        return habitRepository.findById(id);
    }

    public Habit createHabit(Habit habit) {
        return habitRepository.save(habit);
    }

    public Habit updateHabit(String id, Habit habitDetails) {
        Habit habit = habitRepository.findById(id).orElseThrow(() -> new RuntimeException("Habit not found"));
        habit.setName(habitDetails.getName());
        habit.setDescription(habitDetails.getDescription());
        habit.setFrequency(habitDetails.getFrequency());
        return habitRepository.save(habit);
    }

    public void deleteHabit(String id) {
        habitRepository.deleteById(id);
    }

    
}
