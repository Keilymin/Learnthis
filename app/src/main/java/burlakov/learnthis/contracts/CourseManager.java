package burlakov.learnthis.contracts;

import java.util.ArrayList;

import burlakov.learnthis.models.CourseElement;

public interface CourseManager {
    interface View extends IView {
        /**
         * Устанавливает список в адаптер
         *
         * @param elements Список елеметов
         */
        void setIntoAdapter(ArrayList<CourseElement> elements);
    }

    interface Presenter {
        /**
         * Читает бд на наличие елеметов курса
         */
        void startDBCourseElementListen();
    }
}
