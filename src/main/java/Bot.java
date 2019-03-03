import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;

import static org.apache.http.protocol.HTTP.USER_AGENT;

public class Bot extends TelegramLongPollingBot {

    private static Boolean isAuth = false;
    private static Boolean isPollNow = false;
    public static Map<String, String> phones = new HashMap<>();
    public static Map<String, String> questions = new LinkedHashMap<>();

//    GetUpdates updates = new GetUpdates().setOffset(0).setLimit(10).setTimeout(5);
//    List<String> updatesvalues = new ArrayList<>();

//    private void sendGet(String url) throws Exception {
//
//        URL obj = new URL(url);
//        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//
//        // optional default is GET
//        con.setRequestMethod("GET");
//
//        //add request header
//        con.setRequestProperty("User-Agent", USER_AGENT);
//
//        int responseCode = con.getResponseCode();
//        System.out.println("\nSending 'GET' request to URL : " + url);
//        System.out.println("Response Code : " + responseCode);
//
//        BufferedReader in = new BufferedReader(
//                new InputStreamReader(con.getInputStream()));
//        String inputLine;
//        StringBuffer response = new StringBuffer();
//
//        while ((inputLine = in.readLine()) != null) {
//            response.append(inputLine);
//        }
//        in.close();
//
//        //print result
//        System.out.println(response.toString());
//
//    }

    /**
     * Метод для приема сообщений.
     * @param update Содержит сообщение от пользователя.
     */
    public void onUpdateReceived(Update update) {
//        updatesvalues.add("ы бля");
//        System.out.println(updatesvalues);
//        System.out.println(updates.);

//        try {
//            System.out.println(updatesvalues);
//        } catch (Exception e) {
//            System.out.println("все плохо");
//        }
        Message message = update.getMessage();
        //SendMsg(message," "+message);
        if (isPollNow == true) {
            DoPoll(message);
        } else if (message != null && message.hasText()) {
            String text_message = message.getText();
            switch (text_message) {
                case "/start":
                    SendMsgKey(message, SetOfKeyboards.start(), "Хоба-оба, работяга. Чтобы общаться с тобой мне нужено узнать твой номерок, который должен был указать всесильный HR" +
                            "\n Позволишь?");
                    break;
                case "/faq": case "FAQ":
                    try {
                        execute(SetOfKeyboards.HelpKeyboard(message.getChatId()));
                    } catch (TelegramApiException e1) {
                        e1.printStackTrace();
                    }
                    break;
                case "Проверить Kakido":
                    SendMsg(message, "Ку-ка-ре-ку. Пока нам с тобой не о чем разговаривать. Последний опрос был N день назад");
                    break;
            }
        } else if(update.hasCallbackQuery()) {
                SendCallback(update);
            } else if (isAuth == false && update.getMessage().getContact() != null) {
                GetData.getPhones(phones);
                String phone = update.getMessage().getContact().getPhoneNumber();
                //SendMsg(message, phone);
                if (phones.containsKey(phone)) {
                    isAuth = true;
                    phones.replace(phone, message.getChatId().toString());
                    SendMsgKey(message, SetOfKeyboards.main(),"Я вспомнил твой номер, тебя записывал один хороший человек. Кажется его звали Энч..Энчар-р-р" +
                            "\n Ты теперь в папужьей стае какаду. Давай я задам тебе несколько вопросов?");
                    startPoll(update.getMessage().getContact().getPhoneNumber(),"df");
                } else {
                    SendMsg(message, "Kakido не шарит из чьего ты племени, братишка. Совсем не помню такого номера" +
                            "\n Но не отчаивайся, обратись к HR и вскоре я вспомню твой номер телефона, уж будь уверен. Чик-чирик");
                    SendSticker sticker = new SendSticker();
                    sticker.setChatId(message.getChatId().toString());
                    sticker.setSticker("CAADAgADEAADnZ3dGP2KM0HHWIdoAg");
                    try {
                        execute(sticker);
                    }
                    catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    SendMsg(message, "Чуть не забыл, оцени, как стиканы вышли?" +
                            "\n https://t.me/addstickers/Kakido");
                }
            }  else {
            SendSticker sticker = new SendSticker();
            sticker.setChatId(message.getChatId().toString());
            sticker.setSticker("CAADAgADEAADnZ3dGP2KM0HHWIdoAg");
            try {
                execute(sticker);
            }
            catch (TelegramApiException e) {
                e.printStackTrace();
            }
            SendMsg(message, "Какаду не поняль тебя");
            }

//        try {
//            sendGet("https://api.telegram.org/bot740462693:AAEnO_ZOeutDTMfaR_fhNle7tzfdC-KE9Ic/sendMessage?chat_id=425495314&text=Hi+Everyone");
//        } catch (Exception e) {
//            System.out.println("ых2");
//        }
//
//        try {
//            sendGet("https://api.telegram.org/bot740462693:AAEnO_ZOeutDTMfaR_fhNle7tzfdC-KE9Ic/getUpdates");
//        } catch (Exception e) {
//            System.out.println("ых");
//        }




        }


        private void DoPoll(Message message) {
            if (questions.size() != 0) {
                List<String> keys = new ArrayList(questions.keySet());
                for (int i = 0; i < 1; i++)
                {
                    String key = keys.get(i);
                    String value = questions.get(key);
                    SendMsg(message, key);
                    if (ValidatePollAnswer(message, value) != "no result") {
                        questions.remove(key);
                        //послать Гоше запрос
                    } else {
                        SendMsg(message, "Наше папужье племя понимает лишь определенный формат! Попробуй ответить мне нужным стикером");
                        SendMsg(message, key);
                    }
                }
            } else {
                SendMsg(message, "Опрос закончен, спасибо тебе от лица всех папугов. Удачного дня!");
            }
        }


        private String ValidatePollAnswer(Message message, String key) {
            switch (key) {
                case "тип 1":
                    if (message.hasSticker() == true) {
                        switch (message.getSticker().getFileId()) {
                            case "CAADAgADCwADnZ3dGHyPTq_F22T6Ag":
                                return "1";
                            case "CAADAgADDAADnZ3dGDK0ozRoRwrkAg":
                                return "2";
                            case "CAADAgADDQADnZ3dGHKoIZfGENd8Ag":
                                return "3";
                            case "CAADAgADDgADnZ3dGIY6CqyHEOnrAg":
                                return "4";
                            case "CAADAgADDwADnZ3dGAKbLg8aYs2PAg":
                                return "5";
                            default:
                                return "no result";
                        }
                    } else {
                        return "no result";
                    }
                case "тип 2":
                    if (message.hasSticker() == true) {
                        switch (message.getSticker().getFileId()) {
                            case "CAADAgADEAADnZ3dGP2KM0HHWIdoAg":
                                return "yes";
                            case "CAADAgADEQADnZ3dGEVatZXDL8-jAg":
                                return "no";
                            default:
                                return "no result";
                        }
                    } else {
                        return "no result";
                    }
                case "тип 3":
                    if (message != null && message.hasText()) {
                        return message.getText();
                    } else {
                        return "no result";
                    }
            }
            return "no result";
        }


    private void startPoll(String number, String id) {
        isPollNow = true;
        GetData.getQuestions();
        long chat_id = Long.parseLong(phones.get(number));
        Message msg_start = new Message();
        SendMsgInPoll(chat_id, "Давай начнем опрос, набери любые символы");
     }

    private synchronized void SendCallback(Update update) {
        Message message = update.getMessage();
        long chat_id = update.getCallbackQuery().getMessage().getChatId();
        String callback = update.getCallbackQuery().getData();
        switch (callback) {
            case "about_service":
                try {
                    execute(SetOfKeyboards.HelpKeyboardAfterAnswer("Kakido - сервис фидбэка о новом рабочем месте от сотрудника на испытательном сроке", chat_id));
                } catch (TelegramApiException e1) {
                    e1.printStackTrace();
                }
                break;
            case "how_auth":
                try {
                    execute(SetOfKeyboards.HelpKeyboardAfterAnswer("Чтобы авторизироваться, номер телефона должен быть забит в базе сервиса вашим HR", chat_id));
                } catch (TelegramApiException e1) {
                    e1.printStackTrace();
                }
                break;
            case "about_private":
                try {
                    execute(SetOfKeyboards.HelpKeyboardAfterAnswer("Ваш непосредственный руководитель не будет иметь доступа к вашим данным напрямую. С данными взаимодействует только HR", chat_id));
                } catch (TelegramApiException e1) {
                    e1.printStackTrace();
                }
                break;
            case "about_poll":
                try {
                    execute(SetOfKeyboards.HelpKeyboardAfterAnswer("Я не могу распознать ответ, попробуй еще раз" +
                            "\n Для однозначный ответов используй стикеры Да/Нет" +
                            "\n Для рейтинговой оценки используй папугов 1-5" +
                            "\n Для простого ответа используй чистый текст" +
                            "\n Курлык-курлык, жду твоих ответов", chat_id));
                } catch (TelegramApiException e1) {
                    e1.printStackTrace();
                }
                break;
            case "back":
                if (isAuth == false) {
                    SendMsgKey(message, SetOfKeyboards.start(), "Разрешите боту узнать Ваш номер телефона для авторизации в системе");
                } else {
                    SendMsg(message, "Ку-ка-ре-ку. Пока нам с тобой не о чем разговаривать. Последний опрос был N день назад");
                }
                break;
        }
    }

    private void SendMsg(Message message, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(s);
        try {
            execute(sendMessage);
        }
        catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void SendMsgInPoll(long chat_id, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chat_id);
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
        return "LINE_BOT_CHANNEL_SECRET";
    }

    /**
     * Метод возвращает token бота для связи с сервером Telegram
     * @return token для бота
     */
    public String getBotToken() {
        return "LINE_BOT_CHANNEL_SECRET";

    }


}

