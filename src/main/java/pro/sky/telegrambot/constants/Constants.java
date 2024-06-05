package pro.sky.telegrambot.constants;


import liquibase.pro.packaged.S;

public class Constants {

    // Command constants
    public static final String START_COMMAND = "/start";
    public static final String START_TEXT = " Привет!\n" +
            "\nЯ бот приютитель, могу помочь тебе приютить животное." +
            "\nУ меня есть вся подробная информация о процессе взаимодейсвия с приютами.";

    public static final String MAIN_MENU_TEXT = "Какая помощь тебе потребуется?";

    public static final String HELP_COMMAND = "/help";

    public static final String INFO_COMMAND = "/info";
    public static final String INFO_TEXT = "Вот информация о приютах!";

    public static final String VOLUNTEER_COMMAND = "/volunteer";

    public static final String VOLUNTEER_TEXT = "Привет я педик!";

    public static final String CALL_BACK_GET_FILE_GENERAL = "/getFileGeneral";

    public static final String CALL_BACK_FOR_INFO = "info";
    public static final String CALL_BACK_FOR_VOLUNTEER = "volunteer";
    public static final String CALL_BACK_FOR_MAIN_MENU = "mainMenu";
    public static final String CALL_BACK_FOR_START_MENU = "mainMenu";
    public static final String CALL_BACK_FOR_GENERAL_INFO_FILE = "generalInfo.pdf";

}
