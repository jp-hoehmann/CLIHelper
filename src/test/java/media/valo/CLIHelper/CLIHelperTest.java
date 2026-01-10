/*
 * Copyright (c) 2026
 * valo.media GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package media.valo.CLIHelper;

/*
 * CLIHelperTest.java
 * CLIHelper
 */

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for CLIHelper class.
 */
class CLIHelperTest {

    private CLIHelper cliHelper;
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private final InputStream originalIn = System.in;
    private ByteArrayOutputStream outContent;
    private ByteArrayOutputStream errContent;

    @BeforeEach
    void setUp() {
        // Create CLIHelper with default settings
        Pattern positive = Pattern.compile("(?i)^(y|yes|true|1)$");
        Pattern negative = Pattern.compile("(?i)^(n|no|false|0)$");
        
        cliHelper = new CLIHelper(
            '#', '!', '?',
            "INFO", "ERROR", "WARNING",
            "I did not understand that.",
            positive, negative
        );

        // Redirect output streams for testing
        outContent = new ByteArrayOutputStream();
        errContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.setErr(originalErr);
        System.setIn(originalIn);
    }

    // Constructor tests
    @Test
    void testConstructor() {
        assertEquals('#', cliHelper.infoFrame);
        assertEquals('!', cliHelper.errFrame);
        assertEquals('?', cliHelper.warnFrame);
        assertEquals("INFO", cliHelper.infoHead);
        assertEquals("ERROR", cliHelper.errHead);
        assertEquals("WARNING", cliHelper.warnHead);
        assertEquals("I did not understand that.", cliHelper.notUnderstood);
        assertNotNull(cliHelper.positive);
        assertNotNull(cliHelper.negative);
        assertNotNull(cliHelper.scanner);
    }

    // Frame tests
    @Test
    void testFrameSingleLine() {
        String result = CLIHelper.frame("Hello", '*');
        assertTrue(result.contains("*********"));
        assertTrue(result.contains("* Hello *"));
    }

    @Test
    void testFrameMultiLine() {
        String result = CLIHelper.frame("Hello\nWorld", '*');
        assertTrue(result.contains("* Hello *"));
        assertTrue(result.contains("* World *"));
    }

    @Test
    void testFrameWithDifferentLengths() {
        String result = CLIHelper.frame("Hi\nLonger line", '#');
        // The frame should be sized to the longest line
        assertTrue(result.contains("# Hi          #"));
        assertTrue(result.contains("# Longer line #"));
    }

    @Test
    void testFrameEmpty() {
        String result = CLIHelper.frame("", '*');
        assertTrue(result.contains("****"));
        assertTrue(result.contains("*  *"));
    }

    // Print methods tests
    @Test
    void testPrint() {
        cliHelper.print("Test message");
        String output = outContent.toString();
        assertTrue(output.contains("Test message"));
        assertTrue(output.contains("###"));
    }

    @Test
    void testInfoWithCustomHeading() {
        cliHelper.info("Test info", "CUSTOM");
        String output = outContent.toString();
        assertTrue(output.contains("CUSTOM: Test info"));
        assertTrue(output.contains("###"));
    }

    @Test
    void testInfoWithDefaultHeading() {
        cliHelper.info("Test info");
        String output = outContent.toString();
        assertTrue(output.contains("INFO: Test info"));
    }

    @Test
    void testErrWithDefaultHeading() {
        cliHelper.err("Test error");
        String output = errContent.toString();
        assertTrue(output.contains("ERROR: Test error"));
        assertTrue(output.contains("!!!"));
    }

    @Test
    void testErrWithCustomHeading() {
        cliHelper.err("Test error", "CRITICAL");
        String output = errContent.toString();
        assertTrue(output.contains("CRITICAL: Test error"));
        assertTrue(output.contains("!!!"));
    }

    @Test
    void testWarnWithDefaultHeading() {
        cliHelper.warn("Test warning");
        String output = errContent.toString();
        assertTrue(output.contains("WARNING: Test warning"));
        assertTrue(output.contains("???"));
    }

    @Test
    void testWarnWithCustomHeading() {
        cliHelper.warn("Test warning", "ALERT");
        String output = errContent.toString();
        assertTrue(output.contains("ALERT: Test warning"));
        assertTrue(output.contains("???"));
    }

    // askString tests
    @Test
    void testAskString() {
        String input = "test input" + System.lineSeparator();
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        CLIHelper helper = new CLIHelper('#', '!', '?', "INFO", "ERROR", "WARNING",
                "Not understood", Pattern.compile("yes"), Pattern.compile("no"));
        
        String result = helper.askString("Enter text:");
        assertEquals("test input", result);
    }

    // askInt tests
    @Test
    void testAskIntValid() {
        String input = "42" + System.lineSeparator();
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        CLIHelper helper = new CLIHelper('#', '!', '?', "INFO", "ERROR", "WARNING",
                "Not understood", Pattern.compile("yes"), Pattern.compile("no"));
        
        int result = helper.askInt("Enter number:");
        assertEquals(42, result);
    }

    @Test
    void testAskIntWithRetry() {
        String input = "invalid" + System.lineSeparator() + "42" + System.lineSeparator();
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        CLIHelper helper = new CLIHelper('#', '!', '?', "INFO", "ERROR", "WARNING",
                "Not understood", Pattern.compile("yes"), Pattern.compile("no"));
        
        int result = helper.askInt("Enter number:", "Try again");
        assertEquals(42, result);
    }

    // askBool tests
    @Test
    void testAskBoolYes() {
        String input = "yes" + System.lineSeparator();
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        CLIHelper helper = new CLIHelper('#', '!', '?', "INFO", "ERROR", "WARNING",
                "Not understood", Pattern.compile("(?i)^(y|yes|true|1)$"), 
                Pattern.compile("(?i)^(n|no|false|0)$"));
        
        boolean result = helper.askBool("Confirm:");
        assertTrue(result);
    }

    @Test
    void testAskBoolNo() {
        String input = "no" + System.lineSeparator();
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        CLIHelper helper = new CLIHelper('#', '!', '?', "INFO", "ERROR", "WARNING",
                "Not understood", Pattern.compile("(?i)^(y|yes|true|1)$"), 
                Pattern.compile("(?i)^(n|no|false|0)$"));
        
        boolean result = helper.askBool("Confirm:");
        assertFalse(result);
    }

    @Test
    void testAskBoolCaseInsensitive() {
        String input = "Y" + System.lineSeparator();
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        CLIHelper helper = new CLIHelper('#', '!', '?', "INFO", "ERROR", "WARNING",
                "Not understood", Pattern.compile("(?i)^(y|yes|true|1)$"),
                Pattern.compile("(?i)^(n|no|false|0)$"));

        boolean result = helper.askBool("Confirm:");
        assertTrue(result);
    }

    // askByte tests
    @Test
    void testAskByteValid() {
        String input = "127" + System.lineSeparator();
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        CLIHelper helper = new CLIHelper('#', '!', '?', "INFO", "ERROR", "WARNING",
                "Not understood", Pattern.compile("yes"), Pattern.compile("no"));
        
        byte result = helper.askByte("Enter byte:");
        assertEquals(127, result);
    }

    // askShort tests
    @Test
    void testAskShortValid() {
        String input = "32767" + System.lineSeparator();
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        CLIHelper helper = new CLIHelper('#', '!', '?', "INFO", "ERROR", "WARNING",
                "Not understood", Pattern.compile("yes"), Pattern.compile("no"));
        
        short result = helper.askShort("Enter short:");
        assertEquals(32767, result);
    }

    // askLong tests
    @Test
    void testAskLongValid() {
        String input = "9223372036854775807" + System.lineSeparator();
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        CLIHelper helper = new CLIHelper('#', '!', '?', "INFO", "ERROR", "WARNING",
                "Not understood", Pattern.compile("yes"), Pattern.compile("no"));
        
        long result = helper.askLong("Enter long:");
        assertEquals(9223372036854775807L, result);
    }

    // askFloat tests
    @Test
    void testAskFloatValid() {
        String input = "3.14" + System.lineSeparator();
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        CLIHelper helper = new CLIHelper('#', '!', '?', "INFO", "ERROR", "WARNING",
                "Not understood", Pattern.compile("yes"), Pattern.compile("no"));
        
        float result = helper.askFloat("Enter float:");
        assertEquals(3.14f, result, 0.001);
    }

    // askDouble tests
    @Test
    void testAskDoubleValid() {
        String input = "3.141592653589793" + System.lineSeparator();
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        CLIHelper helper = new CLIHelper('#', '!', '?', "INFO", "ERROR", "WARNING",
                "Not understood", Pattern.compile("yes"), Pattern.compile("no"));
        
        double result = helper.askDouble("Enter double:");
        assertEquals(3.141592653589793, result, 0.0000000000001);
    }

    // askBigInteger tests
    @Test
    void testAskBigIntegerValid() {
        String input = "123456789012345678901234567890" + System.lineSeparator();
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        CLIHelper helper = new CLIHelper('#', '!', '?', "INFO", "ERROR", "WARNING",
                "Not understood", Pattern.compile("yes"), Pattern.compile("no"));
        
        BigInteger result = helper.askBigInteger("Enter big integer:");
        assertEquals(new BigInteger("123456789012345678901234567890"), result);
    }

    // askBigDecimal tests
    @Test
    void testAskBigDecimalValid() {
        String input = "123456.789" + System.lineSeparator();
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        CLIHelper helper = new CLIHelper('#', '!', '?', "INFO", "ERROR", "WARNING",
                "Not understood", Pattern.compile("yes"), Pattern.compile("no"));
        
        BigDecimal result = helper.askBigDecimal("Enter big decimal:");
        assertEquals(new BigDecimal("123456.789"), result);
    }

    // Edge case tests
    @Test
    void testMultilineInfoFormatting() {
        cliHelper.info("Line 1\nLine 2\nLine 3");
        String output = outContent.toString();
        assertTrue(output.contains("INFO: Line 1"));
        assertTrue(output.contains("      Line 2"));
        assertTrue(output.contains("      Line 3"));
    }

    @Test
    void testMultilineErrorFormatting() {
        cliHelper.err("Error line 1\nError line 2");
        String output = errContent.toString();
        assertTrue(output.contains("ERROR: Error line 1"));
        assertTrue(output.contains("       Error line 2"));
    }

    @Test
    void testPatternMatching() {
        assertTrue(cliHelper.positive.matcher("yes").matches());
        assertTrue(cliHelper.positive.matcher("YES").matches());
        assertTrue(cliHelper.positive.matcher("y").matches());
        assertTrue(cliHelper.positive.matcher("true").matches());
        assertTrue(cliHelper.positive.matcher("1").matches());
        
        assertTrue(cliHelper.negative.matcher("no").matches());
        assertTrue(cliHelper.negative.matcher("NO").matches());
        assertTrue(cliHelper.negative.matcher("n").matches());
        assertTrue(cliHelper.negative.matcher("false").matches());
        assertTrue(cliHelper.negative.matcher("0").matches());
        
        assertFalse(cliHelper.positive.matcher("maybe").matches());
        assertFalse(cliHelper.negative.matcher("maybe").matches());
    }
}
