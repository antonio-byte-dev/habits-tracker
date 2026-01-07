package com.antoniodev.habit_tracker.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.antoniodev.habit_tracker.model.Habit;
import com.antoniodev.habit_tracker.service.HabitService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/")
public class HabitController {

   private final HabitService habitService;

    public HabitController(HabitService habitService) {
        this.habitService = habitService;
    }

    @GetMapping
    public List<Habit> getAllHabits(@RequestHeader("Authorization") String token) {
        String uid = "";
        try{
        String idToken = token.substring(7); // remove 'Bearer '
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
        uid = decodedToken.getUid(); // or use decodedToken.getEmail()

        }catch(Exception e){
            e.printStackTrace();
        }
       
        return habitService.getHabitsByUser(uid);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Habit> getHabit(@PathVariable String id) {
        return habitService.getHabitById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Habit createHabit( @RequestHeader("Authorization") String token,@RequestBody Habit habit) {
        try {
        String idtoken = token.substring(7);
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idtoken);

        // Override username
        habit.setUserName(decodedToken.getUid());
        System.out.println(decodedToken.getUid());
        System.out.println(habit.getUserName());
        Habit saved = habitService.createHabit(habit);
        return saved;

    } catch (Exception e) {
          e.printStackTrace();
          return null;
    }
        
    }

    @PutMapping("/{id}")
    public ResponseEntity<Habit> updateHabit(@PathVariable String id, @RequestBody Habit habitDetails) {
        try {
            Habit updatedHabit = habitService.updateHabit(id, habitDetails);
            return ResponseEntity.ok(updatedHabit);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHabit(@PathVariable String id) {
        habitService.deleteHabit(id);
        return ResponseEntity.noContent().build();
    }

    /* 
    @PostMapping("/{id}/done")
    public ResponseEntity<Habit> markHabitDone(@PathVariable String id) {
        try {
            Habit habit = habitService.markHabitDone(id);
            return ResponseEntity.ok(habit);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }*/
    
}
