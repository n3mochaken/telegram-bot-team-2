package pro.sky.telegrambot.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.telegrambot.service.VolunteerService;

import java.util.List;

@RestController
@RequestMapping("/volunteer")
public class VolunteerController {
    private final VolunteerService volunteerService;
    public VolunteerController(VolunteerService volunteerService){
        this.volunteerService = volunteerService;
    }

//    @GetMapping("/reports")
//    public ResponseEntity getAllReports(){
//       return ResponseEntity.ok();
//    }
}
