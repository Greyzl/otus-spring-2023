package ru.otus.service;

import java.util.List;

public interface OutputService {

    <T> void output(List<T> questions);
}
