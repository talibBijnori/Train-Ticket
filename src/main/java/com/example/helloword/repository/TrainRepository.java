package com.example.helloword.repository;

import com.example.helloword.model.Train;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface TrainRepository extends JpaRepository<Train, Integer> {
       Optional<Train> findByTrno(Integer trno);
List<Train> findByFromstnAndTostn(String fromstn, String tostn);

}
