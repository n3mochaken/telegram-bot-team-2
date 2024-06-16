package pro.sky.telegrambot.constants;


import liquibase.pro.packaged.S;

public class Constants {

    // Command constants
    public static final String START_COMMAND = "/start";
    public static final String START_TEXT = " Привет\uD83D\uDE48!\n" +
            "\nЯ бот приютитель, могу помочь тебе приютить животное." +
            "\nУ меня есть вся подробная информация о процессе взаимодейсвия с приютами.";

    public static final String MAIN_MENU_TEXT = "Какая помощь тебе потребуется?";

    public static final String INFO_TEXT = "Вот информация о приютах!";

    public static final String CALL_BACK_GET_FILE_GENERAL = "/getFileGeneral";

    public static final String CALL_BACK_FOR_INFO = "info";

    public static final String CALL_BACK_FOR_VOLUNTEER = "volunteer";
    public static final String VOLUNTEER_TEXT = "Привет я педик!";

    public static final String CALL_BACK_FOR_MAIN_MENU = "mainMenu";

    public static final String CALL_BACK_FOR_START_MENU = "startMenu";
    public static final String CALL_BACK_FOR_GENERAL_INFO_FILE = "generalInfo.pdf";
    public static final String CALL_BACK_FOR_ADDRESS = "sendAddress";
    public static final String CALL_BACK_FOR_SAFETY_RULES = "safetyRules.pdf";
    public static final String CALL_BACK_FOR_CONTACTS = "getContacts";
    public static final String CALL_BACK_FOR_TIMING = "getTiming";


    public static final String CONSULTATION_TEXT = "Что вам проконсультировать?";
    public static final String CALL_BACK_FOR_CONSULTATION = "consultation";
    public static final String CALL_BACK_FOR_LOOK_ANIMAL = "lookAnimal";
    public static final String CALL_BACK_FOR_RULES_AND_SHELTER = "lookAnimal";
    public static final String CALL_BACK_FOR_LIST_DOCUMENTS = "listDocuments";
    public static final String CALL_BACK_FOR_REASONS_FOR_REFUSAL = "reasonsForRefusal";
    public static final String CALL_BACK_FOR_RECORD_CONTACTS = "recordСontacts";
    public static final String RECORD_CONTACTS = "Напишите ваши контакты!";

    public static final String RECOMMENDATIONS = "Рекомендации по:";

    //вынести все текстовки в проперти
    public static final String CALL_BACK_FOR_RECOMMENDATIONS = "recommendations";
    public static final String CALL_BACK_FOR_TRANSPORTATION_RECOMMENDATIONS = "transportation";
    public static final String CALL_BACK_FOR_HOME_IMPROVEMENT_RECOMMENDATIONS_PUPPY = "homeImprovementPuppy";
    public static final String CALL_BACK_FOR_HOME_IMPROVEMENT_RECOMMENDATIONS_ADULT_PUPPY = "recommendationsAdultPuppy";
    public static final String CALL_BACK_FOR_HOME_IMPROVEMENT_RECOMMENDATIONS_DISABLED = "recommendationsDisabled";
    public static final String CALL_BACK_FOR_ADVICE_DOG_HANDLER = "adviceВogHandler";
    public static final String CALL_BACK_FOR_PROVEN_DOG_HANDLERS = "provenDogHandlers";

    public static final String CALL_BACK_FOR_TO_SUBMIT_THE_REPORT = "submitTheReport";
}
