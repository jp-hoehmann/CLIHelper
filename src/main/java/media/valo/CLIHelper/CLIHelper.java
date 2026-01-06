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
 * CLIHelper.java
 * CLIHelper
 */

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Helper for using the command line interface.
 *
 * @author      Jean-Pierre Hoehmann <jeanpierre.hoehmann@gmail.com>
 * @version     1.2, 2015-01-05
 */
public class CLIHelper {

    /**
     * The scanner used to read the users input.
     */
    public final Scanner scanner = new Scanner(System.in);

    /**
     * Character used for ASCII-art frames when the content is informative.
     */
    public final char infoFrame;

    /**
     * Character used for ASCII-art frames when the content is an error.
     */
    public final char errFrame;

    /**
     * Character used for ASCII-art frames, when the content is a warning.
     */
    public final char warnFrame;

    /**
     * Default heading used when printing an information.
     */
    public final String infoHead;

    /**
     * Default heading used when printing an error.
     */
    public final String errHead;

    /**
     * Default heading used when printing a warning.
     */
    public final String warnHead;

    /**
     * Text output if the users input was not understood.
     */
    public final String notUnderstood;

    /**
     * Pattern used to find out if an answer is positive.
     */
    public final Pattern positive;

    /**
     * Pattern used to find out if an answer is negative.
     */
    public final Pattern negative;

    /**
     * Constructor.
     *
     * @param   infoFrame       Character to use for ASCII-art frames with informative content.
     * @param   errFrame        Character to use for ASCII-art frames with error content.
     * @param   warnFrame       Character to use for ASCII-art frames with warning content.
     * @param   infoHead        Default heading used when printing an information.
     * @param   errHead         Default heading used when printing an error.
     * @param   warnHead        Default heading used when printing a warning.
     * @param   notUnderstood   Default message output if the users input could not be parsed.
     * @param   positive        Pattern used to determine whether an answer is positive.
     * @param   negative        Pattern used to determine whether an answer is negative.
     */
    public CLIHelper(char infoFrame, char errFrame, char warnFrame, String infoHead,
                     String errHead, String warnHead, String notUnderstood,
                     Pattern positive, Pattern negative) {

        this.infoFrame = infoFrame;
        this.errFrame = errFrame;
        this.warnFrame = warnFrame;
        this.infoHead = infoHead;
        this.errHead = errHead;
        this.warnHead = warnHead;
        this.notUnderstood = notUnderstood;
        this.positive = positive;
        this.negative = negative;
        this.scanner.useDelimiter(Pattern.compile(System.lineSeparator()));

    }

    /**
     * Helper method for easily drawing an ASCII-Art frame around a String.<br/>
     * Multiline Strings are allowed.
     *
     * @param   input   The text to print out in a frame.
     * @param   frame   The Character used for the frame.
     * @return  The new String.
     */
    public static String frame(String input, char frame) {

        int width = 0;
        String[] text = stringToArray(input);
        List<String> output = new ArrayList<>();
        // Iterate over all lines to find the longest one, this one dictates the width of the frame.
        for (String s : text) {
            width = Math.max(width, s.length());
        }
        // Newline before the frame for better optics
        output.add("");
        // First line is the framechracter repeated as width times
        // plus four extra times for spacing to the sides.
        output.add(Character.toString(frame).repeat(width + 4));
        // Every line gets writen from text to output with framecharacters pre- and appended
        // if the line is shorter than the width extra spacing to the frame gets added.
        for (String s : text) {
            output.add(frame + " " + s + " " + " ".repeat(width - s.length()) + frame);
        }
        // Last line: same as the first.
        output.add(output.get(1));
        // Finally convert the List back to a String and return it.
        return arrayToString(output.toArray(new String[0]));

    }

    /**
     * Helper method for easily printing a String multiple times into a single line.
     *
     * @param   input   String to be printed.
     * @param   n       Times the String is to be printed.
     * @return  The input String repeated n times.
     */
    public static String printNTimes(String input, int n) {

        // Print the input String n times into output.
        return String.valueOf(input).repeat(Math.max(0, n));

    }

    /**
     * Helper method used to convert a multiline String into an Array of singleline Strings.
     *
     * @param   input   Multiline String to be turned into an Array.
     * @return  An Array of Strings with each String representating a single line.
     */
    public static String[] stringToArray(String input) {

        // Simply split the input String at all the newlines.
        // Two Backslashes here because of Regex.
        // This version of split automatically removes the trailing empty String.
        return input.split("\\n");

    }

    /**
     * Helper method used to convert an Array of Strings into one multiline String.
     *
     * @param   input   The Array of Strings to be converted.
     * @return  The converted String.
     */
    public static String arrayToString(String[] input) {

        String output = "";
        // Iterate over the array and append each line including a line feed to the output.
        // This will produce a String with a trailing newline.
        // Restoration of the original String is not possible,
        // because stringToArray simply strips trailing newlines for easier formatting.
        for (String s : input) {
            output += s;
            output += "\n";
        }
        return output;

    }

    /**
     * Helper method used to indent text.
     *
     * @param   input   The text to be indented.
     * @param   depth   Depth of the indentation.
     * @param   start   Number of lines after which the indentation should start.
     * @return  The text with indentation.
     */
    public static String indent(String input, int depth, int start) {

        String[] text = stringToArray(input);
        // Simply iterate from start to end and append the given number of spaces
        for (int i = start; i < text.length; i++) {
            text[i] = " ".repeat(depth) + text[i];
        }
        return arrayToString(text);

    }

    /**
     * Helper method used to quickformat a text to a given keyword.<br/>
     * I do not know how to describe this exact behaviour,
     * if you do not know what this does just try it :)
     *
     * @param   input   The text.
     * @param   keyword The keyword, should not contain line breaks.
     * @return  The formatted text.
     */
    public static String format(String input, String keyword) {

        String[] text = stringToArray(input);
        // First line is the keyword folowed by colon and space.
        text[0] = keyword + ": " + text[0];
        // From the second line on it is just an indentation.
        return indent(arrayToString(text), keyword.length() + 2, 1);

    }

    /**
     * Used for framed outputting of genaral information.
     * Uses no heading.
     *
     * @param   info    Text to output.
     */
    public void print(String info) {

        System.out.println(frame(info, infoFrame));

    }

    /**
     * Used for framed outputting of general information.
     * Uses a given heading.
     *
     * @param   info    Text to output.
     * @param   head    Heading to use.
     */
    public void info(String info, String head) {

        System.out.println(frame(format(info, head), infoFrame));

    }

    /**
     * Used for framed outputting of general information.
     * Uses the default heading.
     *
     * @param   info    Text to output.
     */
    public void info(String info) {

        info(info, infoHead);

    }

    /**
     * Print out an error.
     *
     * @param   error   Error to print out to error out.
     */
    public void err(String error) {

        err(error, errHead);

    }

    /**
     * Print out an error.
     * With a given heading.
     *
     * @param   error   Error to print out to error out.
     * @param   head    Heading to use.
     */
    public void err(String error, String head) {

        System.err.println(frame(format(error, head), errFrame));

    }

    /**
     * Print out a warning.
     *
     * @param   warning Warning to issue, can have multiple lines.
     */
    public void warn(String warning) {

        warn(warning, warnHead);

    }

    /**
     * Print out a warning.
     * With a given heading.
     *
     * @param   warning Warning to issue, can have multiple lines.
     * @param   head    Heading to use.
     */
    public void warn(String warning, String head) {

        System.err.println(frame(format(warning, head), warnFrame));

    }

    /**
     * Reads a string from stdin.
     *
     * @param   text    Question the user is asked.
     *
     * @return  The users response.
     */
    public String askString(String text) {

        System.out.println(text);
        return scanner.next();

    }

    /**
     * Reads a BigDecimal from stdin.
     * Outputs a specified text if the input could not be parsed.
     *
     * @param   text            Question the user is asked.
     * @param   notUnderstood   Text which is output if the input could not be parsed.
     *
     * @return  The users response as a BigDecimal.
     */
    public BigDecimal askBigDecimal(String text, String notUnderstood) {

        System.out.println(text);
        while (!(scanner.hasNextBigDecimal())) {
            System.out.println(notUnderstood);
            System.out.println(text);
            scanner.next();
        }
        return scanner.nextBigDecimal();

    }

    /**
     * Reads a BigDecimal from stdin.
     * Outputs the default message if the input could not be parsed.
     *
     * @param   text    Question the user is asked.
     *
     * @return  The users response as a BigDecimal.
     */
    public BigDecimal askBigDecimal(String text) {

        return askBigDecimal(text, notUnderstood);

    }

    /**
     * Reads a BigInteger from stdin.
     * Outputs a specified text if the input could not be parsed.
     *
     * @param   text            Question the user is asked.
     * @param   notUnderstood   Text which is output if the input could not be parsed.
     *
     * @return  The users response as a BigInteger.
     */
    public BigInteger askBigInteger(String text, String notUnderstood) {

        System.out.println(text);
        while (!(scanner.hasNextBigInteger())) {
            System.out.println(notUnderstood);
            System.out.println(text);
            scanner.next();
        }
        return scanner.nextBigInteger();

    }

    /**
     * Reads a BigInteger from stdin.
     * Outputs the default message if the input could not be parsed.
     *
     * @param   text    Question the user is asked.
     *
     * @return  The users response as a BigInteger.
     */
    public BigInteger askBigInteger(String text) {

        return askBigInteger(text, notUnderstood);

    }

    /**
     * Reads a boolean from stdin.
     * Outputs a specified text if the input could not be parsed.
     *
     * @param   text            Question the user is asked.
     * @param   notUnderstood   Text which is output if the input could not be parsed.
     *
     * @return  The users response as a boolean.
     */
    public boolean askBool(String text, String notUnderstood) {

        System.out.println(text);
        while (!(scanner.hasNext(positive) || scanner.hasNext(negative))) {
            System.out.println(notUnderstood);
            System.out.println(text);
            scanner.next();
        }
        boolean answer = scanner.hasNext(positive);
        scanner.next();
        return answer;

    }

    /**
     * Reads a boolean from stdin.
     * Outputs the default message if the input could not be parsed.
     *
     * @param   text    Question the user is asked.
     *
     * @return  The users response as a boolean.
     */
    public boolean askBool(String text) {

        return askBool(text, notUnderstood);

    }

    /**
     * Reads a byte from stdin.
     * Outputs a specified text if the input could not be parsed.
     *
     * @param   text            Question the user is asked.
     * @param   notUnderstood   Text which is output if the input could not be parsed.
     *
     * @return  The users response as a byte.
     */
    public byte askByte(String text, String notUnderstood) {

        System.out.println(text);
        while (!(scanner.hasNextByte())) {
            System.out.println(notUnderstood);
            System.out.println(text);
            scanner.next();
        }
        return scanner.nextByte();

    }

    /**
     * Reads a byte from stdin.
     * Outputs the default message if the input could not be parsed.
     *
     * @param   text    Question the user is asked.
     *
     * @return  The users response as a byte.
     */
    public byte askByte(String text) {

        return askByte(text, notUnderstood);

    }

    /**
     * Reads a double from stdin.
     * Outputs a specified text if the input could not be parsed.
     *
     * @param   text            Question the user is asked.
     * @param   notUnderstood   Text which is output if the input could not be parsed.
     *
     * @return  The users response as a double.
     */
    public double askDouble(String text, String notUnderstood) {

        System.out.println(text);
        while (!(scanner.hasNextDouble())) {
            System.out.println(notUnderstood);
            System.out.println(text);
            scanner.next();
        }
        return scanner.nextDouble();

    }

    /**
     * Reads a double from stdin.
     * Outputs the default message if the input could not be parsed.
     *
     * @param   text    Question the user is asked.
     *
     * @return  The users response as a double.
     */
    public double askDouble(String text) {

        return askDouble(text, notUnderstood);

    }

    /**
     * Reads a float from stdin.
     * Outputs a specified text if the input could not be parsed.
     *
     * @param   text            Question the user is asked.
     * @param   notUnderstood   Text which is output if the input could not be parsed.
     *
     * @return  The users response as a float.
     */
    public float askFloat(String text, String notUnderstood) {

        System.out.println(text);
        while (!(scanner.hasNextFloat())) {
            System.out.println(notUnderstood);
            System.out.println(text);
            scanner.next();
        }
        return scanner.nextFloat();

    }

    /**
     * Reads a float from stdin.
     * Outputs the default message if the input could not be parsed.
     *
     * @param   text    Question the user is asked.
     *
     * @return  The users response as a float.
     */
    public float askFloat(String text) {

        return askFloat(text, notUnderstood);

    }

    /**
     * Reads an int from stdin.
     * Outputs a specified text if the input could not be parsed.
     *
     * @param   text            Question the user is asked.
     * @param   notUnderstood   Text which is output if the input could not be parsed.
     *
     * @return  The users response as an int.
     */
    public int askInt(String text, String notUnderstood) {

        System.out.println(text);
        while (!(scanner.hasNextInt())) {
            System.out.println(notUnderstood);
            System.out.println(text);
            scanner.next();
        }
        return scanner.nextInt();

    }

    /**
     * Reads an int from stdin.
     * Outputs the default message if the input could not be parsed.
     *
     * @param   text    Question the user is asked.
     *
     * @return  The users response as an int.
     */
    public int askInt(String text) {

        return askInt(text, notUnderstood);

    }

    /**
     * Reads a long from stdin.
     * Outputs a specified text if the input could not be parsed.
     *
     * @param   text            Question the user is asked.
     * @param   notUnderstood   Text which is output if the input could not be parsed.
     *
     * @return  The users response as a long.
     */
    public long askLong(String text, String notUnderstood) {

        System.out.println(text);
        while (!(scanner.hasNextLong())) {
            System.out.println(notUnderstood);
            System.out.println(text);
            scanner.next();
        }
        return scanner.nextLong();

    }

    /**
     * Reads a long from stdin.
     * Outputs the default message if the input could not be parsed.
     *
     * @param   text    Question the user is asked.
     *
     * @return  The users response as a long.
     */
    public long askLong(String text) {

        return askLong(text, notUnderstood);

    }

    /**
     * Reads a short from stdin.
     * Outputs a specified text if the input could not be parsed.
     *
     * @param   text            Question the user is asked.
     * @param   notUnderstood   Text which is output if the input could not be parsed.
     *
     * @return  The users response as a short.
     */
    public short askShort(String text, String notUnderstood) {

        System.out.println(text);
        while (!(scanner.hasNextShort())) {
            System.out.println(notUnderstood);
            System.out.println(text);
            scanner.next();
        }
        return scanner.nextShort();

    }

    /**
     * Reads a short from stdin.
     * Outputs the default message if the input could not be parsed.
     *
     * @param   text    Question the user is asked.
     *
     * @return  The users response as a short.
     */
    public short askShort(String text) {

        return askShort(text, notUnderstood);

    }

}
