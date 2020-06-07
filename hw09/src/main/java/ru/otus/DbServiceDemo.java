package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.model.Account;
import ru.otus.core.model.User;
import ru.otus.h2.DataSourceH2;
import ru.otus.jdbc.mapper.JdbcMapperImpl;
import ru.otus.jdbc.mapper.interfaces.JdbcMapper;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author sergey
 * created on 03.02.19.
 */
public class DbServiceDemo {
    private static final Logger logger = LoggerFactory.getLogger(DbServiceDemo.class);

    public static void main(String[] args) throws Exception {
        var dataSource = new DataSourceH2();
        var demo = new DbServiceDemo();

        demo.createTable(dataSource);

        var sessionManager = new SessionManagerJdbc(dataSource);
        testUserModel(sessionManager);
        System.out.println();
        testAccountModel(sessionManager);

    }

    private static void testAccountModel(SessionManagerJdbc sessionManager) {
        JdbcMapper<Account> jdbcMapper = new JdbcMapperImpl<>(sessionManager);
        logger.info("Creating new account by mapper");
        Account account = new Account(0, "basic", 72);
        jdbcMapper.insert(account);

        logger.info("Searching new account");
        Account findAccount = jdbcMapper.findById(1, Account.class);
        logger.info(findAccount.toString());

        logger.info("Updating account");
        account.setNo(1);
        account.setType("advanced");
        jdbcMapper.update(account);
        findAccount = jdbcMapper.findById(1, Account.class);
        logger.info(findAccount.toString());

        logger.info("Insert or update. Insert");
        account.setNo(2);
        account.setRest(99999);
        jdbcMapper.insertOrUpdate(account);
        findAccount = jdbcMapper.findById(2, Account.class);
        logger.info(findAccount.toString());
    }

    private static void testUserModel(SessionManagerJdbc sessionManager) {
        JdbcMapper<User> jdbcMapper = new JdbcMapperImpl<>(sessionManager);
        logger.info("Creating new user by mapper");
        User newUser = new User(0, "testUser", 18);
        jdbcMapper.insert(newUser);

        logger.info("Searching new user:");
        User findUser = jdbcMapper.findById(1, User.class);
        logger.info(findUser.toString());

        logger.info("Updating new user");
        newUser.setId(1);
        newUser.setAge(58);
        jdbcMapper.update(newUser);

        findUser= jdbcMapper.findById(1, User.class);
        logger.info(findUser.toString());

        logger.info("Insert or update. Insert");
        newUser.setAge(100);
        newUser.setId(2);
        jdbcMapper.insertOrUpdate(newUser);
        findUser = jdbcMapper.findById(2, User.class);
        logger.info(findUser.toString());
    }

    private void createTable(DataSource dataSource) throws SQLException {
        try (var connection = dataSource.getConnection();
             var pst = connection.prepareStatement("create table user(id long auto_increment, name varchar(50), age int(3))")) {
            pst.executeUpdate();
        }
        try (var connection = dataSource.getConnection();
             var pst = connection.prepareStatement("create table account(no long auto_increment, type varchar(255), rest int)")) {
            pst.executeUpdate();
        }
        System.out.println("table created");
    }
}
