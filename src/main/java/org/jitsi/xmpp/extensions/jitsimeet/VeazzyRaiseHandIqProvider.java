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
 * The parser of {@link VeazzyRaiseHandIq}.
 *
 * @author Pawel Domas
 */
public class VeazzyRaiseHandIqProvider
        extends IQProvider<VeazzyRaiseHandIq> {

    /**
     * The classLogger instance used by this class.
     */
    private final static Logger classLogger
            = Logger.getLogger(VeazzyRaiseHandIqProvider.class);

    /**
     * The logger for this instance. Uses the logging level either the one of
     * {@link #classLogger} or the one passed to the constructor, whichever is
     * higher.
     */
    private final Logger logger = Logger.getLogger(classLogger, null);

    /**
     * Registers this IQ provider into given <tt>ProviderManager</tt>.
     */
    public static void registerVeazzyRaiseHandIqProvider() {
        ProviderManager.addIQProvider(VeazzyRaiseHandIq.ELEMENT_NAME,
                VeazzyRaiseHandIq.NAMESPACE,
                new VeazzyRaiseHandIqProvider());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VeazzyRaiseHandIq parse(XmlPullParser parser, int initialDepth)
            throws Exception {
        String namespace = parser.getNamespace();

        // Check the namespace
        if (!VeazzyRaiseHandIq.NAMESPACE.equals(namespace)) {
            return null;
        }

        String rootElement = parser.getName();

        VeazzyRaiseHandIq iq;

        if (VeazzyRaiseHandIq.ELEMENT_NAME.equals(rootElement)) {
            
            iq = new VeazzyRaiseHandIq();
            
            String jidStr = parser.getAttributeValue("", VeazzyRaiseHandIq.JID_ATTR_NAME);
            if (jidStr != null) {
                Jid jid = JidCreate.from(jidStr);
                iq.setJid(jid);
            }

            String actorStr
                    = parser.getAttributeValue("", VeazzyRaiseHandIq.ACTOR_ATTR_NAME);
            if (actorStr != null) {
                Jid actor = JidCreate.from(actorStr);
                iq.setActor(actor);
            }
            
            String participantToRaiseHandStr
                    = parser.getAttributeValue("", VeazzyRaiseHandIq.PARTICIPANT_RAISE_HAND_NAME);
            iq.setParticipantToRaiseHand(participantToRaiseHandStr);
            
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
                        int raiseHandStatus = VeazzyRaiseHandIq.RAISE_HAND_STATUS_HAND_DOWNED;
                        try {
                            raiseHandStatus = Integer.parseInt(parser.getText());
                        }
                        catch(NumberFormatException e) {
                                
                        }
                        iq.setRaiseHandStatus(raiseHandStatus);
                    } else {
                        logger.warn("Getting raiseHandData request without value");
                    }
                    break;
                }
            }
        }

        return iq;
    }
}
