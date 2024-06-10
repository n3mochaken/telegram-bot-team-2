package pro.sky.telegrambot.service.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.entity.Report;
import pro.sky.telegrambot.repository.PersonRepository;
import pro.sky.telegrambot.repository.ReportRepository;

import java.util.Collection;

@Service
public class VolunteerService {
    @Autowired
    private final ReportRepository reportRepository;
    Logger logger = LoggerFactory.getLogger(PersonRepository.class);

    public VolunteerService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public  Collection<Report> getAllReports() {
        logger.info("getAllReports вызван");
     return  reportRepository.findAll();
    }
}
