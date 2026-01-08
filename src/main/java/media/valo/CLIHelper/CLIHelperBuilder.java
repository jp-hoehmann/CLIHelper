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
 * CLIHelperBuilder.java
 * CLIHelper
 */

import java.util.regex.Pattern;

/**
 * Builder for the CLIHelper class.
 *
 * @author      Jean-Pierre Hoehmann <jeanpierre.hoehmann@gmail.de>
 * @version     1.0, 2015-01-05
 */
public class CLIHelperBuilder {

    /**
     * Character used for ASCII-art frames when the content is informative.
     */
    private char infoFrame = '#';

    /**
     * Character used for ASCII-art frames when the content is an error.
     */
    private char errFrame = '@';

    /**
     * Character used for ASCII-art frames, when the content is a warning.
     */
    private char warnFrame = '%';

    /**
     * Default heading used when printing an informational message.
     */
    private String infoHead = "INFO";

    /**
     * Default heading used when printing an error.
     */
    private String errHead = "ERROR";

    /**
     * Default heading used when printing a warning.
     */
    private String warnHead = "WARN";

    /**
     * Text output if the users input was not understood.
     */
    private String notUnderstood = "I did not understand that, please try again.";

    /**
     * Pattern used to find out if an answer is positive.
     */
    private Pattern positive = Pattern.compile("(true)|y|(yes)", Pattern.CASE_INSENSITIVE);

    /**
     * Pattern used to find out if an answer is negative.
     */
    private Pattern negative = Pattern.compile("(false)|n|(no)", Pattern.CASE_INSENSITIVE);

    /**
     * Constructor.
     */
    public CLIHelperBuilder() {

    }

    /**
     * Builds the CLIHelper.
     *
     * @return  The CLIHelper.
     */
    public CLIHelper build() {

        return new CLIHelper(infoFrame, errFrame, warnFrame, infoHead, errHead, warnHead,
                notUnderstood, positive, negative);

    }

    /**
     * Sets the character used for ASCII-Art frames when the content is informative.
     *
     * @param   infoFrame  The character to use.
     *
     * @return  The CLIHelperBuilder it was invoked on, for method chaining purposes.
     */
    public CLIHelperBuilder infoFrame(char infoFrame) {

        this.infoFrame = infoFrame;
        return this;

    }

    /**
     * Sets the character used for ASCII-Art frames when the content is an error.
     *
     * @param   errFrame   The character to use.
     *
     * @return  The CLIHelperBuilder it was invoked on, for method chaining purposes.
     */
    public CLIHelperBuilder errFrame(char errFrame) {

        this.errFrame = errFrame;
        return this;

    }

    /**
     * Sets the character used for ASCII-Art frames, when the content a warning.
     *
     * @param   warnFrame  The character to use.
     *
     * @return  The CLIHelperBuilder it was invoked on, for method chaining purposes.
     */
    public CLIHelperBuilder warnFrame(char warnFrame) {

        this.warnFrame = warnFrame;
        return this;

    }

    /**
     * Sets the default text to print out if the users answer was not understood.
     *
     * @param   notUnderstood  The text to print.
     *
     * @return  The CLIHelperBuilder it was invoked on, for method chaining purposes.
     */
    public CLIHelperBuilder notUnderstood(String notUnderstood) {

        this.notUnderstood = notUnderstood;
        return this;

    }

    /**
     * Sets the Pattern used to determine whether an answer is positive.
     *
     * Using an existing Pattern.
     *
     * @param   positive   The Pattern to use.
     *
     * @return  The CLIHelperBuilder it was invoked on, for method chaining purposes.
     */
    public CLIHelperBuilder positive(Pattern positive) {

        this.positive = positive;
        return this;

    }

    /**
     * Sets the Pattern used to determine whether an answer is positive.
     *
     * Using a regex string.
     *
     *  <p>
     *      <b>
     *          Important note: <br/>
     *      </b>
     *      This method sets the regex i-flag to make the Pattern case-insensitive, if this is not wanted, you have to
     *      use this with an existing pattern.
     *  </p>
     *
     * @param   positive   The regex for the Pattern to use. The i-flag will be set!
     *
     * @return  The CLIHelperBuilder it was invoked on, for method chaining purposes.
     */
    public CLIHelperBuilder positive(String positive) {

        return positive(Pattern.compile(positive, Pattern.CASE_INSENSITIVE));

    }

    /**
     * Sets the Pattern used to determine whether an answer is negative.
     *
     * Using an existing Pattern.
     *
     * @param   negative   The Pattern to use.
     *
     * @return  The CLIHelperBuilder it was invoked on, for method chaining purposes.
     */
    public CLIHelperBuilder negative(Pattern negative) {

        this.negative = negative;
        return this;

    }

    /**
     * Sets the Pattern used to determine whether an answer is negative.
     *
     * Using a regex string.
     *
     *  <p>
     *      <b>
     *          Important note: <br/>
     *      </b>
     *      This method sets the regex i-flag to make the Pattern case-insensitive, if this is not wanted, you have to
     *      use this with an existing pattern.
     *  </p>
     *
     * @param   negative   The regex for the Pattern to use. The i-flag will be set!
     *
     * @return  The CLIHelperBuilder it was invoked on, for method chaining purposes.
     */
    public CLIHelperBuilder negative(String negative) {

        return negative(Pattern.compile(negative, Pattern.CASE_INSENSITIVE));

    }

    /**
     * Sets the default heading used when an informational message is printed out.
     *
     * @param   infoHead    The heading to use.
     *
     * @return  The CLIHelperBuilder it was invoked on, for method chaining purposes.
     */
    public CLIHelperBuilder infoHead(String infoHead) {

        this.infoHead = infoHead;
        return this;

    }

    /**
     * Sets the default heading used when an error is printed out.
     *
     * @param   errHead    The heading to use.
     *
     * @return  The CLIHelperBuilder it was invoked on, for method chaining purposes.
     */
    public CLIHelperBuilder errHead(String errHead) {

        this.errHead = errHead;
        return this;

    }

    /**
     * Sets the default heading used when a warning is printed out.
     *
     * @param   warnHead    The heading to use.
     *
     * @return  The CLIHelperBuilder it was invoked on, for method chaining purposes.
     */
    public CLIHelperBuilder warnHead(String warnHead) {

        this.warnHead = warnHead;
        return this;

    }

}
