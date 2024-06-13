package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambot.entity.Report;

import java.util.List;


@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    List<Report> findAllReportById(long id);
}
