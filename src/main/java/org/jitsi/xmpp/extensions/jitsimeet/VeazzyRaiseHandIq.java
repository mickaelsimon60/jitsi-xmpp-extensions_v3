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
public class VeazzyRaiseHandIq
        extends IQ {

    /**
     * The classLogger instance used by this class.
     */
    private final static Logger classLogger
            = Logger.getLogger(VeazzyRaiseHandIq.class);

    /**
     * The logger for this instance. Uses the logging level either the one of
     * {@link #classLogger} or the one passed to the constructor, whichever is
     * higher.
     */
    private final Logger logger = Logger.getLogger(classLogger, null);

    /**
     * Name space of moderatorId packet extension.
     */
    public static final String NAMESPACE = "http://jitsi.org/jitmeet/raisehand";

    public static final int RAISE_HAND_STATUS_HAND_DOWNED = 0;
    public static final int RAISE_HAND_STATUS_HAND_RAISED = 1;
    
    /**
     * XML element name of moderatorId packet extension.
     */
    public static final String ELEMENT_NAME = "raiseHand";

    /**
     * Attribute name of "jid".
     */
    public static final String JID_ATTR_NAME = "jid";

    /**
     * Attribute name of "actor".
     */
    public static final String ACTOR_ATTR_NAME = "actor";

    /**
     * Attribute name of "actor".
     */
    public static final String PARTICIPANT_RAISE_HAND_NAME = "participantToRaiseHand";
    
    /**
     * Muted peer MUC jid.
     */
    private Jid jid;

    /**
     * The jid of the peer tha initiated the moderatorId, optional.
     */
    private Jid actor;

    /**
     * The participantToRaiseHand.
     */
    private String participantToRaiseHand;
    /**
     * The Raise Hand Status.
     */
    private int raiseHandStatus;

    
    /**
     * Creates a new instance of this class.
     */
    public VeazzyRaiseHandIq() {
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

        if (participantToRaiseHand != null) {
            xml.attribute(PARTICIPANT_RAISE_HAND_NAME, participantToRaiseHand);
        }
        
        xml.rightAngleBracket()
                .append(String.valueOf(raiseHandStatus));

        logger.warn("Building xml VeazzyRaiseHand " + xml.toString());

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

    public void setParticipantToRaiseHand(String participantToRaiseHand) {
        this.participantToRaiseHand = participantToRaiseHand;
    }
    public String getParticipantToRaiseHand() {
        return participantToRaiseHand;
    }
    
    
    /**
     * The action contained in the text part of 'raiseHandStatus' XML element body.
     *
     * @param raiseHandStatus
     */
    public void setRaiseHandStatus(int raiseHandStatus) {
        this.raiseHandStatus = raiseHandStatus;
    }

    /**
     * Returns raiseHandStatus or <tt>null</tt> if the action has not been
     * specified(which is invalid).
     */
    public int getRaiseHandStatus() {
        return raiseHandStatus;
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
}
