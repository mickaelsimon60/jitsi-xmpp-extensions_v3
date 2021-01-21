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
import org.jivesoftware.smack.provider.IQProvider;
import org.jivesoftware.smack.provider.ProviderManager;
import org.jxmpp.jid.Jid;
import org.jxmpp.jid.impl.JidCreate;
import org.xmlpull.v1.XmlPullParser;

/**
 * The parser of {@link VeazzyVisitorIq}.
 *
 * @author Pawel Domas
 */
public class VeazzyVisitorIqProvider
        extends IQProvider<VeazzyVisitorIq> {

    /**
     * The classLogger instance used by this class.
     */
    private final static Logger classLogger
            = Logger.getLogger(VeazzyVisitorIqProvider.class);

    /**
     * The logger for this instance. Uses the logging level either the one of
     * {@link #classLogger} or the one passed to the constructor, whichever is
     * higher.
     */
    private final Logger logger = Logger.getLogger(classLogger, null);

    /**
     * Registers this IQ provider into given <tt>ProviderManager</tt>.
     */
    public static void registerVeazzyVisitorIqProvider() {
        ProviderManager.addIQProvider(VeazzyVisitorIq.ELEMENT_NAME,
                VeazzyVisitorIq.NAMESPACE,
                new VeazzyVisitorIqProvider());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VeazzyVisitorIq parse(XmlPullParser parser, int initialDepth)
            throws Exception {
        String namespace = parser.getNamespace();

        // Check the namespace
        if (!VeazzyVisitorIq.NAMESPACE.equals(namespace)) {
            return null;
        }

        String rootElement = parser.getName();

        VeazzyVisitorIq iq;

        if (VeazzyVisitorIq.ELEMENT_NAME.equals(rootElement)) {
            
            iq = new VeazzyVisitorIq();
            
            String jidStr = parser.getAttributeValue("", VeazzyVisitorIq.JID_ATTR_NAME);
            if (jidStr != null) {
                Jid jid = JidCreate.from(jidStr);
                iq.setJid(jid);
            }

            String actorStr
                    = parser.getAttributeValue("", VeazzyVisitorIq.ACTOR_ATTR_NAME);
            if (actorStr != null) {
                Jid actor = JidCreate.from(actorStr);
                iq.setActor(actor);
            }
            
            String participantToHideStr
                    = parser.getAttributeValue("", VeazzyVisitorIq.PARTICIPANT_TO_HIDE_NAME);
            iq.setParticipantToHide(participantToHideStr);
            
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
                        int hiddenStatus = VeazzyVisitorIq.VISITOR_HIDDEN_STATUS_VISIBLE;
                        try {
                            hiddenStatus = Integer.parseInt(parser.getText());
                        }
                        catch(NumberFormatException e) {
                                
                        }
                        iq.setParticipantHiddenStatus(hiddenStatus);
                    } else {
                        logger.warn("Getting VisitorData request without value");
                    }
                    break;
                }
            }
        }

        return iq;
    }
}
