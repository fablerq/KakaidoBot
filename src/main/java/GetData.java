import java.util.ArrayList;
import java.util.Map;

public class GetData {
    public static Map<String, String> getPhones(Map<String, String> phones) {
        phones.put("11", "q");
        phones.put("222", "w");
        phones.put("333", "r");
        phones.put("phone", "");
        return phones;
    }

    public static void getQuestions() {
        Bot.questions.put("Зацени свое рабочее место от 1 до 5 попугаев.", "тип 1");
        Bot.questions.put("Познакомилиь ли вы с большей части команды?", "тип 2");
        Bot.questions.put("Вас ознакомили с вашими обязанностями?", "тип 2");
        Bot.questions.put("Как дела?", "тип 3");
        Bot.questions.put("Есть ли у вас какие-либо предложения?", "тип 3");
    }
}
