import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class SetOfKeyboards {
    public static ReplyKeyboardMarkup start() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setSelective(true);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboard1 = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardButton keyboardButton = new KeyboardButton();
        keyboardButton.setText("Поделиться номерком").setRequestContact(true);
        keyboardFirstRow.add(keyboardButton);
        keyboard1.add(keyboardFirstRow);

        keyboardMarkup.setKeyboard(keyboard1);
        return keyboardMarkup;
    }

    public static ReplyKeyboardMarkup main() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setSelective(true);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard1 = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardButton keyboardButton = new KeyboardButton();
        keyboardButton.setText("FAQ");
        keyboardFirstRow.add(keyboardButton);

        KeyboardButton keyboardButton2 = new KeyboardButton();
        keyboardButton2.setText("Проверить Kakido");
        keyboardFirstRow.add(keyboardButton2);
        keyboard1.add(keyboardFirstRow);

        keyboardMarkup.setKeyboard(keyboard1);
        return keyboardMarkup;
    }

    public static SendMessage HelpKeyboard(long chat_id) {
        SendMessage message = InlineKeyboardBuilder.create(chat_id)
                .setText("Что ты хочешь узнать от Какаду?")
                .row()
                .button("О Kakido", "about_service")
                .button("Обратная связь", "about_private")
                .endRow()
                .row()
                .button("Как авторизироваться", "how_auth")
                .button("Как в опросе", "about_poll")
                .endRow()
                .row()
                .button("Назад", "back")
                .endRow()
                .build();
        return message;
    }

    public static SendMessage HelpKeyboardAfterAnswer(String answer, long chat_id) {
        SendMessage message = InlineKeyboardBuilder.create(chat_id)
                .setText(answer + "\n \n У Какаду есть еще много секретов. Что тебя интересует?")
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
        return message;
    }
}
