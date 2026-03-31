package com.pomello;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintStream;

import io.qameta.allure.Step;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AppTest {
    @Test
    public void alwaysSucceeds() {
        assert(true);
    }
}
