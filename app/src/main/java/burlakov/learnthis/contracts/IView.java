package burlakov.learnthis.contracts;

/**
 * Базовый интерфейс для вью
 */
public interface IView {
    /**
     * Метод должен выводить сообщение на экран
     *
     * @param message    Сообщение ошибки
     * @param isPositive Позитивное ли сообщение
     */
    void showMessage(String message, Boolean isPositive);
}
