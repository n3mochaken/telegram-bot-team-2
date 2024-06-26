package pro.sky.telegrambot.service.entities;


import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import pro.sky.telegrambot.entity.Report;

import pro.sky.telegrambot.exception.ReportNotFoundException;
import pro.sky.telegrambot.listener.TelegramBotUpdatesListener;

import pro.sky.telegrambot.repository.ReportRepository;

import java.util.List;

@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final TelegramBot bot;
    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    public ReportService(ReportRepository reportRepository, TelegramBot bot) {
        this.reportRepository = reportRepository;
        this.bot = bot;

    }

    /**
     * Добавляет заданную сущность
     * Используется метод репозитория {@link JpaRepository#save(Object)}
     *
     * @param report сохраняемая сущность
     * @return сохраняет сущность
     */
    public Report create(Report report) {
        report.setId(null);
        logger.info("Отчет создан");
        return reportRepository.save(report);
    }

    /**
     * Обновляет сущность по передоваемыми параметрам
     * Используется метод репозитория {@link ReportRepository#findById(Object)}
     *
     * @param id     индификатор сущности
     * @param report сущность
     * @return измененную сущность
     */
    public Report update(long id, Report report) {
        return reportRepository.findById(id)
                .map(oldReport -> {
                    oldReport.setPhoto(report.getPhoto());
                    oldReport.setFood(report.getFood());
                    oldReport.setHealth(report.getHealth());
                    oldReport.setChanges(report.getChanges());
                    oldReport.setOwner(report.getOwner());
                    logger.info("Изменены данные отчета");
                    return reportRepository.save(oldReport);
                })
                //добавить логер logger.info("Отчет не найден");
                .orElseThrow(() -> new ReportNotFoundException(id));
    }

    /**
     * Удаляет сущность по идентификатору
     * Используются методы репозитория {@link ReportRepository#findById(Object)}
     * и {@link ReportRepository#delete(Object)}
     *
     * @param id индентификатор удаляемой сущности
     * @return удаленная сущность
     */
    public Report delete(long id) {
        return reportRepository.findById(id)
                .map(report -> {
                    reportRepository.delete(report);
                    logger.info("Отчет удален");
                    return report;
                })
                //добавить логер logger.info("Отчет не найден");
                .orElseThrow(() -> new ReportNotFoundException(id));
    }

    /**
     * Возвращает сущность по её идентификатору.
     * Используется метод репозитория {@link JpaRepository#findById(Object)}
     *
     * @param id идентификатор сущности
     * @return возвращаемая сущность
     */
    public Report get(long id) {
        //добавить логер logger.info("Отчет найден");
        return reportRepository.findById(id)
                //добавить логер logger.info("Отчет не найден");
                .orElseThrow(() -> new ReportNotFoundException(id));
    }

    /**
     * Возвращает список всех созданных отчетов
     *
     * @return список сущностей
     */
    public List<Report> findAll() {
        logger.info("Вызван метод нахождения всех отчетов");
        return reportRepository.findAll();
    }

    /**
     * Возвращает список отчеитов усыновителя
     *
     * @param id
     * @return
     */
    public List<Report> getReports(long id) {
        return reportRepository.findAllReportById(id);
    }

    public void sendNotification(Update update) {
        bot.execute(new SendMessage(update.message().chat().id(), "Тебя позвал чел @" + update.callbackQuery().from().username() + "\n" +
                "Срочно напиши ему, а то тебя уволят!"));
    }

    public void sendBadNotification(long id) {
        bot.execute(new SendMessage(id, "Дорогой усыновитель, мы заметили, что ты заполняешь отчет не так подробно, как необходимо.\n" +
                "Пожалуйста, подойди ответственнее к этому занятию.\n" +
                "В противном случае волонтеры приюта будут обязаны самолично проверять условия содержания животного"));
    }
}


