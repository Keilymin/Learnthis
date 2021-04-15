package burlakov.learnthis.models;

import android.net.Uri;

/**
 * Класс материал
 */
public class Material {
    /**
     * Название файла
     */
    String fileName;
    /**
     * Путь к файлу в сторейже
     */
    String filePath;
    /**
     * URI файла
     */
    Uri fileUri;
    /**
     * ссылка на данные
     */
    String fileStorageUri;

    public Material(String fileName, String filePath, String fileStorageUri) {
        if (fileName != null) {
            if (fileName.length() > 0) {
                this.fileName = fileName;
            } else {
                throw new RuntimeException("Имя материала не может быть пустым");
            }
        } else throw new RuntimeException("Имя материала не может быть null");
        if (filePath != null) {
            if (filePath.length() > 0) {
                this.filePath = filePath;
            } else {
                throw new RuntimeException("Путь материала не может быть пустым");
            }
        } else throw new RuntimeException("Путь материала не может быть null");
        this.fileStorageUri = fileStorageUri;
    }

    public Material(Uri fileUri) {
        if (fileUri != null) {
            this.fileUri = fileUri;
            fileName = fileUri.getPath();
            int cut = fileName.lastIndexOf('/');
            if (cut != -1) {
                this.fileName = fileName.substring(cut + 1);
            }
            this.filePath = fileUri.getPath();
        } else throw new RuntimeException("Uri файла не может быть null");

    }

    public Material() {
    }


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        if (fileName != null) {
            if (fileName.length() > 0) {
                this.fileName = fileName;
            } else {
                throw new RuntimeException("Имя материала не может быть пустым");
            }
        } else throw new RuntimeException("Имя материала не может быть null");
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        if (filePath != null) {
            if (filePath.length() > 0) {
                this.filePath = filePath;
            } else {
                throw new RuntimeException("Путь материала не может быть пустым");
            }
        } else throw new RuntimeException("Путь материала не может быть null");
    }

    public Uri getFileUri() {
        return fileUri;
    }

    public void setFileUri(Uri fileUri) {
        if (fileUri != null) {
            this.fileUri = fileUri;
        } else throw new RuntimeException("Uri файла не может быть null");
    }

    public String getFileStorageUri() {
        return fileStorageUri;
    }

    public void setFileStorageUri(String fileStorageUri) {
        this.fileStorageUri = fileStorageUri;
    }
}
