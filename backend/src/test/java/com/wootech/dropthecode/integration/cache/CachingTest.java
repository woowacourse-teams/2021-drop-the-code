package com.wootech.dropthecode.integration.cache;

import javax.persistence.PersistenceUnit;

import com.wootech.dropthecode.IntegrationTest;
import com.wootech.dropthecode.util.DatabaseCleanup;

import org.springframework.beans.factory.annotation.Autowired;

import org.junit.jupiter.api.BeforeEach;

import org.hibernate.SessionFactory;

abstract class CachingTest extends IntegrationTest {

    @Autowired
    private DatabaseCleanup databaseCleanup;

    @PersistenceUnit
    protected SessionFactory sessionFactory;

    @BeforeEach
    void setUp() {
        databaseCleanup.execute();

        sessionFactory.getCache().evictAll();

        sessionFactory.getStatistics().clear();
    }
}
