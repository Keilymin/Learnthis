package burlakov.learnthis.contracts;

import java.util.ArrayList;

import burlakov.learnthis.models.Course;

/**
 * Контракт для вкладки Курсы для учителя
 */
public interface TeacherCourses {

    interface View extends IView {
        /**
         * Устанавливает список в адаптер
         *
         * @param courses Список курсов
         */
        void setIntoAdapter(ArrayList<Course> courses);

        /**
         * Вызывает диалог для смены названия курса
         *
         * @param course Список курсов
         */
        void editDialog(Course course);
    }

    interface Presenter {
        /**
         * Читает бд на наличие курсов
         */
        void startDBCourseListen();
    }

    /**
     * Интерфейс для диалога создания курса
     */
    interface CreateDialog {
        /**
         * Создает курс
         */
        void createCourse();
    }
}
