package com.example.helloword.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.helloword.model.Train;
import com.example.helloword.repository.TrainRepository;

@Controller
public class TrainController {

    private final TrainRepository trainRepository;

    public TrainController(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }

    @GetMapping("/addTrain")
    public String addTrain() {
        return "addTrain";
    }

    @PostMapping("/adminaddtrain")
    public String adminAddTrain(@RequestParam String trainno,
            @RequestParam String trainname,
            @RequestParam String fromstation,
            @RequestParam String tostation,
            @RequestParam String available,
            @RequestParam String fare,
            Model model) {
        Integer trainNumberInt = null;
        boolean hasErrors = false;

        try {
            trainNumberInt = Integer.parseInt(trainno);
            if (trainNumberInt <= 0) {
                model.addAttribute("trainnoError", "Train number must be a positive integer");
                hasErrors = true;
            }
        } catch (NumberFormatException e) {
            model.addAttribute("trainnoError", "Train number must be a valid number");
            hasErrors = true;
        }

        if (hasErrors) {
            model.addAttribute("trainno", trainno);
            model.addAttribute("trainname", trainname);
            model.addAttribute("fromstation", fromstation);
            model.addAttribute("tostation", tostation);
            model.addAttribute("available", available);
            model.addAttribute("fare", fare);
            return "addTrain";
        }
        Train train = new Train();
        train.setTr_no(trainNumberInt);
        train.setTr_name(trainname);
        train.setFrom_stn(fromstation);
        train.setTo_stn(tostation);
        train.setSeats(available);
        train.setFare(fare);

        trainRepository.save(train);
        return "redirect:/addTrain";
    }

    @GetMapping("/adminviewtrain")
    public String showTrainData(Model model) {
        Iterable<Train> trains = trainRepository.findAll();
        model.addAttribute("trains", trains);
        return "showTrains";
    }

    @GetMapping("/userviewtrain")
    public String userviewtrain(Model model) {
        Iterable<Train> trains = trainRepository.findAll();
        model.addAttribute("trains", trains);
        return "showTrains";
    }

    @GetMapping("/editTrain/{id}")
    public String editTrain(@PathVariable("id") Integer id, Model model) {
        Optional<Train> train = trainRepository.findById(id);
        if (train.isPresent()) {
            model.addAttribute("train", train.get());
            return "editTrain";
        } else {
            return "redirect:/addTrain";
        }
    }

    @PostMapping("/updateTrain")
    public String updateTrain(@ModelAttribute("train") Train train) {
        Optional<Train> optionalTrain = trainRepository.findById(train.getId());
        if (optionalTrain.isPresent()) {
            Train existingTrain = optionalTrain.get();
            existingTrain.setTr_no(train.getTr_no());
            existingTrain.setTr_name(train.getTr_name());
            existingTrain.setFrom_stn(train.getFrom_stn());
            existingTrain.setTo_stn(train.getTo_stn());
            existingTrain.setSeats(train.getSeats());
            existingTrain.setFare(train.getFare());

            trainRepository.save(existingTrain);
        }
        return "redirect:/adminviewtrain";
    }

    @GetMapping("/adminsearchtrain")
    public String searchTrain(@RequestParam(value = "trainNo", required = false) Integer trainNo, Model model) {
        if (trainNo != null) {
            Optional<Train> train = trainRepository.findByTrno(trainNo);
            if (train.isPresent()) {
                model.addAttribute("train", train.get());
                return "showSingleTrain";
            } else {
                model.addAttribute("error", "Train not found");
            }
        }
        return "adminsearchtrain";
    }

    @GetMapping("/updatetrain")
    public String searchTraintoUpdate(@RequestParam(value = "trainNo", required = false) Integer trainNo, Model model) {
        if (trainNo != null) {
            Optional<Train> train = trainRepository.findByTrno(trainNo);
            if (train.isPresent()) {
                model.addAttribute("train", train.get());
                return "editTrain";
            } else {
                model.addAttribute("error", "Train not found");
            }
        }
        return "updateByTrainNo";
    }

    @GetMapping("/trainbwstn")
    public String trainbwstn() {
        return "trainBetweenTrain";
    }

  @GetMapping("/searchbetweentrain")
public String getTrainBetween(
        @RequestParam(value = "fromtrain", required = false) String fromtrain,
        @RequestParam(value = "totrain", required = false) String totrain,
        Model model) {

    if (fromtrain != null && totrain != null) {
    List<Train> trains = trainRepository.findByFromstnAndTostn(fromtrain, totrain);
        if (!trains.isEmpty()) {
            model.addAttribute("trains", trains);
        } else {
            model.addAttribute("error", "No trains found between " + fromtrain + " and " + totrain);
        }
    }
    return "trainBetweenTrain";
}
@GetMapping("/deleteTrain/{id}")
public String deleteTrain(@PathVariable("id") Integer id, RedirectAttributes redirAttrs) {
    trainRepository.deleteById(id); // Delete the train
    redirAttrs.addFlashAttribute("success", "Train deleted successfully.");
    return "redirect:/adminviewtrain";
}

}
