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
import org.jivesoftware.smack.packet.*;
import org.jxmpp.jid.*;

/**
 * IQ used for the signaling of moderator ID in Jitsi Meet conferences.
 *
 * @author Pawel Domas
 */
public class VeazzyQuizQuestionIq
        extends IQ {

    /**
     * The classLogger instance used by this class.
     */
    private final static Logger classLogger
            = Logger.getLogger(VeazzyQuizQuestionIq.class);

    /**
     * The logger for this instance. Uses the logging level either the one of
     * {@link #classLogger} or the one passed to the constructor, whichever is
     * higher.
     */
    private final Logger logger = Logger.getLogger(classLogger, null);

    /**
     * Name space of moderatorId packet extension.
     */
    public static final String NAMESPACE = "http://jitsi.org/jitmeet/quizquestion";
    /**
     * XML element name of moderatorId packet extension.
     */
    public static final String ELEMENT_NAME = "quizQuestion";

    /**
     * Attribute name of "jid".
     */
    public static final String JID_ATTR_NAME = "jid";

    /**
     * Attribute name of "actor".
     */
    public static final String ACTOR_ATTR_NAME = "actor";

    public static final String ANSWER_A_ATTR_NAME = "answerA";
    public static final String ANSWER_B_ATTR_NAME = "answerB";
    public static final String ANSWER_C_ATTR_NAME = "answerC";
    public static final String ANSWER_D_ATTR_NAME = "answerD";
    public static final String STATUS_A_ATTR_NAME = "statusA";
    public static final String STATUS_B_ATTR_NAME = "statusB";
    public static final String STATUS_C_ATTR_NAME = "statusC";
    public static final String STATUS_D_ATTR_NAME = "statusD";
    
    /**
     * Muted peer MUC jid.
     */
    private Jid jid;

    /**
     * The jid of the peer tha initiated the moderatorId, optional.
     */
    private Jid actor;

    /**
     * The Manager Id.
     */
    private String answerA;
    private String answerB;
    private String answerC;
    private String answerD;
    
    private Boolean statusA;
    private Boolean statusB;
    private Boolean statusC;
    private Boolean statusD;
    
    private String question;
    
    /**
     * Creates a new instance of this class.
     */
    public VeazzyQuizQuestionIq() {
        super(ELEMENT_NAME, NAMESPACE);
    }

    @Override
    protected IQChildElementXmlStringBuilder getIQChildElementBuilder(
            IQChildElementXmlStringBuilder xml) {

        if (jid != null) {
            xml.attribute(JID_ATTR_NAME, jid);
        }

        if (actor != null) {
            xml.attribute(ACTOR_ATTR_NAME, actor);
        }
        
        if (answerA != null) {
            xml.attribute(ANSWER_A_ATTR_NAME, answerA);
        }
        if (answerB != null) {
            xml.attribute(ANSWER_B_ATTR_NAME, answerB);
        }
        if (answerC != null) {
            xml.attribute(ANSWER_C_ATTR_NAME, answerC);
        }
        if (answerD != null) {
            xml.attribute(ANSWER_D_ATTR_NAME, answerD);
        }
        if (statusA != null) {
            xml.attribute(STATUS_A_ATTR_NAME, statusA);
        }
        if (statusB != null) {
            xml.attribute(STATUS_B_ATTR_NAME, statusB);
        }
        if (statusC != null) {
            xml.attribute(STATUS_C_ATTR_NAME, statusC);
        }
        if (statusD != null) {
            xml.attribute(STATUS_D_ATTR_NAME, statusD);
        }

        xml.rightAngleBracket()
                .append(question);

        logger.warn("Building xml QuizId " + xml.toString());

        return xml;
    }

    /**
     * Sets the MUC jid of the user.
     *
     * @param jid muc jid in the form of room_name@muc.server.net/nickname.
     */
    public void setJid(Jid jid) {
        this.jid = jid;
    }

    /**
     * Returns MUC jid of the participant in the form of
     * "room_name@muc.server.net/nickname".
     */
    public Jid getJid() {
        return jid;
    }

    /**
     * The action contained in the text part of 'question' XML element body.
     *
     * @param question
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * Returns question or <tt>null</tt> if the action has not been
     * specified(which is invalid).
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Returns the peer jid that initiated the moderatorId, if any.
     *
     * @return the peer jid that initiated the moderatorId.
     */
    public Jid getActor() {
        return actor;
    }

    /**
     * Sets jid for the peer that initiated the moderatorId.
     *
     * @param actor the jid of the peer doing the moderatorId.
     */
    public void setActor(Jid actor) {
        this.actor = actor;
    }

    /**
     * @return the answerA
     */
    public String getAnswerA() {
        return answerA;
    }

    /**
     * @param answerA the answerA to set
     */
    public void setAnswerA(String answerA) {
        this.answerA = answerA;
    }

    /**
     * @return the answerB
     */
    public String getAnswerB() {
        return answerB;
    }

    /**
     * @param answerB the answerB to set
     */
    public void setAnswerB(String answerB) {
        this.answerB = answerB;
    }

    /**
     * @return the answerC
     */
    public String getAnswerC() {
        return answerC;
    }

    /**
     * @param answerC the answerC to set
     */
    public void setAnswerC(String answerC) {
        this.answerC = answerC;
    }

    /**
     * @return the answerD
     */
    public String getAnswerD() {
        return answerD;
    }

    /**
     * @param answerD the answerD to set
     */
    public void setAnswerD(String answerD) {
        this.answerD = answerD;
    }

    /**
     * @return the statusA
     */
    public Boolean getStatusA() {
        return statusA;
    }

    /**
     * @param statusA the statusA to set
     */
    public void setStatusA(Boolean statusA) {
        this.statusA = statusA;
    }

    /**
     * @return the statusB
     */
    public Boolean getStatusB() {
        return statusB;
    }

    /**
     * @param statusB the statusB to set
     */
    public void setStatusB(Boolean statusB) {
        this.statusB = statusB;
    }

    /**
     * @return the statusC
     */
    public Boolean getStatusC() {
        return statusC;
    }

    /**
     * @param statusC the statusC to set
     */
    public void setStatusC(Boolean statusC) {
        this.statusC = statusC;
    }

    /**
     * @return the statusD
     */
    public Boolean getStatusD() {
        return statusD;
    }

    /**
     * @param statusD the statusD to set
     */
    public void setStatusD(Boolean statusD) {
        this.statusD = statusD;
    }
}
