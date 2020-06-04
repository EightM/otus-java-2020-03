package ru.otus.core.sessionmanager;

import ru.otus.core.sessionmanager.DatabaseSession;

public interface SessionManager extends AutoCloseable {
    void beginSession();

    void commitSession();

    void rollbackSession();

    void close();

    DatabaseSession getCurrentSession();
}
