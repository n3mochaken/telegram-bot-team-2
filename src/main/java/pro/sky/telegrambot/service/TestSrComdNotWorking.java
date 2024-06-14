package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.model.Contact;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ForceReply;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.request.EditMessageReplyMarkup;
import com.pengrad.telegrambot.request.EditMessageText;
import com.pengrad.telegrambot.request.SendMessage;
import pro.sky.telegrambot.entity.Owner;

public class TestSrComdNotWorking {
//    public void savePhoneNumber(Update update) {
//        // НАД ЭТИМ НАДО ПОДУМАТЬ!
//
//        Message message = update.message();
//
//        if (message != null && message.contact() == null) {
//            long chatId = message.chat().id();
//            Integer messageId = message.messageId();
//
//            SendMessage sendMessage = new SendMessage(chatId, "Теперь напишите номер телефона в формате: +7 123 456-78-90");
//            sendMessage.replyMarkup(new ForceReply());
//
//            bot.execute(sendMessage);
//        } else if (message != null && message.contact() != null) {
//            long chatId = message.chat().id();
//            String textNumber = message.contact().phoneNumber();
//
//            // Проверка и очистка номера телефона
//            textNumber = textNumber.replaceAll("[^0-9]+", "");
//
//            if (textNumber.length() < 10 || textNumber.length() > 11 ||
//                    (textNumber.charAt(0) != '7' && textNumber.charAt(0) != '8')) {
//                throw new RuntimeException("Некорректный номер телефона");
//            }
//
//            Owner owner = new Owner();
//            owner.setChatId(chatId);
//            owner.setPhoneNumber(textNumber);
//
//            ownerRepository.save(owner);
//
//            bot.execute(new SendMessage(chatId, "Номер телефона успешно сохранен"));
//
//        } else {
//            logger.info("Не добавлено!");
//        }
//    }


//    public void savePhoneNumber(Update update) {
//        // НАД ЭТИМ НАДО ПОДУМАТЬ!
//
//        if (update.message() != null && update.message().contact() != null) {
//            long chatId = update.message().chat().id();
//            Integer messageId = update.message().messageId();
//
//            bot.execute(new SendMessage(chatId, "Напишите контакты!"));
//
//
//            if (update.message() != null && update.message().contact() != null) {
//                Contact contact = update.callbackQuery().message().contact();
//                String textNumber = contact.phoneNumber();
//
//                if (!textNumber.isEmpty()) {
//                    textNumber = textNumber.replace("+", "")
//                            .replace("-", "")
//                            .replace(" ", "");
//
//                    if (textNumber.length() < 10) {
//                        throw new RuntimeException("Номер телефона слишком короткий");
//                    } else if (textNumber.length() > 11) {
//                        throw new RuntimeException("Номер телефона слишком длинный");
//                    } else if (textNumber.charAt(0) != '7' && textNumber.charAt(0) != '8') {
//                        throw new RuntimeException("Номер телефона не начинается с '7' или '8'");
//                    }
//
//                    Owner owner = new Owner();
//                    owner.setChatId(chatId);
//                    owner.setPhoneNumber(textNumber);
//                    ownerRepository.save(owner);
//
//                    logger.info("Успешно добавлено!");
//                }
//
//            } else {
//                logger.info("Не добавлено!" + messageId);
//            }
//        }
//    }

//    public void savePhoneNumber(Update update) {
//
//        logger.info("Запущен метод savePhoneNumber");
//
//        long chatId = update.callbackQuery().message().chat().id();
//        Integer messageId = update.callbackQuery().message().messageId();
//
//
//        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
//        KeyboardButton button = new KeyboardButton("Отправить контакт");
//        button.requestContact(true);
//
//        bot.execute(new EditMessageText(chatId, messageId, "Оставить контакт?"));
//        bot.execute(new EditMessageReplyMarkup(chatId, messageId).replyMarkup(inlineKeyboardMarkup));
//    }
}
