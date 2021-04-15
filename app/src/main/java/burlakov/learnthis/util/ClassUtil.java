package burlakov.learnthis.util;

/**
 * Вспомагательный класс с инструментами
 */
public class ClassUtil {
    /**
     * Получает название класса элемента
     *
     * @param e Элемент
     * @return Название класса
     */
    public static String getObjectClassName(Object e) {
        String name = e.getClass().getName();
        int cut = name.lastIndexOf('.');
        if (cut != -1) {
            name = name.substring(cut + 1);
        }
        return name;
    }
}
