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
        keyboardButton.setText("Поделиться телефоном").setRequestContact(true);
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
        keyboardButton.setText("Помощь");
        keyboardFirstRow.add(keyboardButton);

        KeyboardButton keyboardButton2 = new KeyboardButton();
        keyboardButton2.setText("Проверить Kakado");
        keyboardFirstRow.add(keyboardButton2);
        keyboard1.add(keyboardFirstRow);

        keyboardMarkup.setKeyboard(keyboard1);
        return keyboardMarkup;
    }
}
