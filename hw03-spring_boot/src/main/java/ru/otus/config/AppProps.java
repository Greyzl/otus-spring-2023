package ru.otus.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import java.util.Locale;

@ConfigurationProperties("application")
public class AppProps implements QuestionCsvResourceFileNameHolder, LocaleHolder {
    private Locale locale;

    private TestFile testFile;

    public static class TestFile{
        private String path;

        private String extension;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getExtension() {
            return extension;
        }

        public void setExtension(String extension) {
            this.extension = extension;
        }
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public TestFile getTestFile() {
        return testFile;
    }

    public void setTestFile(TestFile testFile) {
        this.testFile = testFile;
    }

    @Override
    public String getResourceFileName() {
        return testFile.getPath() + "_" + locale + "." + testFile.getExtension();
    }
}
