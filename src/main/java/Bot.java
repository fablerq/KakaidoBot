import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Bot extends TelegramLongPollingBot {

    public static ArrayList<String> phones = new ArrayList<>();
    public static ArrayList<String> questions = new ArrayList<>();

    /**
     * Метод для приема сообщений.
     * @param update Содержит сообщение от пользователя.
     */
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        //SendMsg(message," "+message);

        if (message != null && message.hasText()) {
            String text_message = message.getText();
            switch (text_message) {
                case "/start":
                    SendMsgKey(message, SetOfKeyboards.start(), "Разрешите боту узнать Ваш номер телефона для авторизации в системе");
                    break;
                case "/help": case "Помощь":
                    SendMessage message1 = InlineKeyboardBuilder.create(message.getChatId())
                        .setText("Какаду может многое. О чем тебе расказать?")
                        .row()
                        .button("О Kakido", "about_service")
                        .button("Обратная связь", "about_private")
                        .endRow()
                        .row()
                        .button("Как авторизироваться", "how_auth")
                        .button("О команде", "about_team")
                        .endRow()
                        .row()
                        .button("Назад", "back")
                        .endRow()
                        .build();
                    try {
                        sendApiMethod(message1);
                    } catch (TelegramApiException e1) {
                        e1.printStackTrace();
                    }
                    break;
                default:
                    if(update.hasCallbackQuery()) {
                        SendCallback(update);
                    } else {
                        SendSticker sticker = new SendSticker();
                        sticker.setChatId(message.getChatId().toString());
                        sticker.setSticker("CAADAgADCAADnZ3dGPnAJ0f8V2RTAg");
                        try {
                            execute(sticker);
                        }
                        catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                        SendMsg(message, "Какаду не поняль тебя");
                    }
                    break;
            }
        }

        if (update.getMessage().getContact() != null) {
            GetData.getPhones();
            SendMsg(message, "" + update.getMessage().getContact().getPhoneNumber());
            if (phones.contains(update.getMessage().getContact().getPhoneNumber())) {
                SendMsgKey(message, SetOfKeyboards.main(),"Ура! Ты теперь в стае какаду");
            } else {
                SendMsg(message, "Kakado не знает человека с таким телефоном" +
                        "\n Чтобы Kakado считал вас своим другом, попросите HR или руководителя добавить вас ко мне в стаю");
            }
        }
    }

    //private static void startPoll(String number, ) {
     //   GetData.getQuestions();
//
  //      for (int i = 0; i < questions.size(); i++) {
//
  //      }
    //
    // }

    private synchronized  void SendCallback(Update update) {

    }

    private void SendMsg(Message message, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(s);
        try {
            execute(sendMessage);
        }
        catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void SendMsgKey(Message message, ReplyKeyboardMarkup k, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(s);
        sendMessage.setReplyMarkup(k);
        try {
            execute(sendMessage);
        }
        catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    /**
     * Метод возвращает имя бота, указанное при регистрации.
     * @return имя бота
     */
    public String getBotUsername() {
        return "";
    }



    /**
     * Метод возвращает token бота для связи с сервером Telegram
     * @return token для бота
     */
    public String getBotToken() {
        return "";
    }


    private String getWelcomeMessage() {
        Date myDate = new Date();
        int hrs = myDate.getHours();

        if (hrs < 12) {
            return "Доброе утро";
        } else if (hrs >= 12 && hrs <= 17) {
            return "Добрый день";
        } else if (hrs > 17 && hrs <= 24) {
            return "Добрый вечер";
        } else {
            return "Доброй ночи";
        }
    }
}

