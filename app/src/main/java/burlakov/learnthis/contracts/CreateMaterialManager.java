package burlakov.learnthis.contracts;

import android.net.Uri;

import java.util.List;

import burlakov.learnthis.models.CourseElement;
import burlakov.learnthis.models.Material;

/**
 * Контракт для работы с Материалами в рециклере
 */
public interface CreateMaterialManager {
    interface View extends IView {
        /**
         * Устанавливает в адаптер список материалов
         *
         * @param materials Список материалов
         */
        void setIntoAdapter(List<Material> materials);

        /**
         * Устанавливает название темы
         *
         * @param theme Название темы
         */
        void setTheme(String theme);
    }

    interface Presenter {

        /**
         * Сохраняет элемент в бд
         *
         * @param element   элемент
         * @param elementId Его айди(если нет можно null)
         */
        void saveCourseElement(CourseElement element, String elementId);

        /**
         * Загружает данные с бд
         *
         * @param courseId Айди курса
         * @param elementId Айди элемента
         */
        void loadData(String courseId, String elementId);
    }
}
