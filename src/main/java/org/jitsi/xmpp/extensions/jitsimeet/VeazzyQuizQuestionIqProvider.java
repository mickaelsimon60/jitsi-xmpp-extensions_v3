/*
 * Copyright @ 2018 - present 8x8, Inc.
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
package org.jitsi.xmpp.extensions.jitsimeet;

import org.jitsi.utils.logging.Logger;
import org.jivesoftware.smack.provider.*;

import org.jxmpp.jid.*;
import org.jxmpp.jid.impl.*;
import org.xmlpull.v1.*;

/**
 * The parser of {@link VeazzyQuizQuestionIq}.
 *
 * @author Pawel Domas
 */
public class VeazzyQuizQuestionIqProvider
        extends IQProvider<VeazzyQuizQuestionIq> {

    /**
     * The classLogger instance used by this class.
     */
    private final static Logger classLogger
            = Logger.getLogger(VeazzyQuizQuestionIqProvider.class);

    /**
     * The logger for this instance. Uses the logging level either the one of
     * {@link #classLogger} or the one passed to the constructor, whichever is
     * higher.
     */
    private final Logger logger = Logger.getLogger(classLogger, null);

    /**
     * Registers this IQ provider into given <tt>ProviderManager</tt>.
     */
    public static void registerVeazzyQuizQuestionIqProvider() {
        ProviderManager.addIQProvider(VeazzyQuizQuestionIq.ELEMENT_NAME,
                VeazzyQuizQuestionIq.NAMESPACE,
                new VeazzyQuizQuestionIqProvider());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VeazzyQuizQuestionIq parse(XmlPullParser parser, int initialDepth)
            throws Exception {
        String namespace = parser.getNamespace();

        // Check the namespace
        if (!VeazzyQuizQuestionIq.NAMESPACE.equals(namespace)) {
            return null;
        }

        String rootElement = parser.getName();

        VeazzyQuizQuestionIq iq;

        if (VeazzyQuizQuestionIq.ELEMENT_NAME.equals(rootElement)) {
            iq = new VeazzyQuizQuestionIq();
            String jidStr = parser.getAttributeValue("", VeazzyQuizQuestionIq.JID_ATTR_NAME);
            if (jidStr != null) {
                Jid jid = JidCreate.from(jidStr);
                iq.setJid(jid);
            }

            String actorStr
                    = parser.getAttributeValue("", VeazzyQuizQuestionIq.ACTOR_ATTR_NAME);
            if (actorStr != null) {
                Jid actor = JidCreate.from(actorStr);
                iq.setActor(actor);
            }
            
            String answerAStr
                    = parser.getAttributeValue("", VeazzyQuizQuestionIq.ANSWER_A_ATTR_NAME);
            if (answerAStr != null) {
                iq.setAnswerA(answerAStr);
            }
            String answerBStr
                    = parser.getAttributeValue("", VeazzyQuizQuestionIq.ANSWER_B_ATTR_NAME);
            if (answerBStr != null) {
                iq.setAnswerB(answerBStr);
            }
            String answerCStr
                    = parser.getAttributeValue("", VeazzyQuizQuestionIq.ANSWER_C_ATTR_NAME);
            if (answerCStr != null) {
                iq.setAnswerC(answerCStr);
            }
            String answerDStr
                    = parser.getAttributeValue("", VeazzyQuizQuestionIq.ANSWER_D_ATTR_NAME);
            if (answerDStr != null) {
                iq.setAnswerD(answerDStr);
            }
            
            String statusAStr
                    = parser.getAttributeValue("", VeazzyQuizQuestionIq.STATUS_A_ATTR_NAME);
            if (statusAStr != null) {
                iq.setStatusA(Boolean.valueOf(statusAStr));
            }
            String statusBStr
                    = parser.getAttributeValue("", VeazzyQuizQuestionIq.STATUS_B_ATTR_NAME);
            if (statusBStr != null) {
                iq.setStatusB(Boolean.valueOf(statusBStr));
            }
            String statusCStr
                    = parser.getAttributeValue("", VeazzyQuizQuestionIq.STATUS_C_ATTR_NAME);
            if (statusCStr != null) {
                iq.setStatusC(Boolean.valueOf(statusCStr));
            }
            String statusDStr
                    = parser.getAttributeValue("", VeazzyQuizQuestionIq.STATUS_D_ATTR_NAME);
            if (statusDStr != null) {
                iq.setStatusD(Boolean.valueOf(statusDStr));
            }
        } else {
            return null;
        }

        boolean done = false;

        while (!done) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG: {
                    String name = parser.getName();

                    if (rootElement.equals(name)) {
                        done = true;
                    }
                    break;
                }

                case XmlPullParser.TEXT: {
                    if (parser.getText() != null && parser.getText().length() > 0) {
                        String question = parser.getText();
                        iq.setQuestion(question);
                    } else {
                        logger.warn("Getting quizQuestion request without value");
                    }
                    break;
                }
            }
        }

        return iq;
    }
}
