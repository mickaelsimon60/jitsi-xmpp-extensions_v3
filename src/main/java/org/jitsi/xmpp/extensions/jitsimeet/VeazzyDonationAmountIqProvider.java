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
 * The parser of {@link VeazzyDonationAmountIq}.
 *
 * @author Pawel Domas
 */
public class VeazzyDonationAmountIqProvider
        extends IQProvider<VeazzyDonationAmountIq> {

    /**
     * The classLogger instance used by this class.
     */
    private final static Logger classLogger
            = Logger.getLogger(VeazzyDonationAmountIqProvider.class);

    /**
     * The logger for this instance. Uses the logging level either the one of
     * {@link #classLogger} or the one passed to the constructor, whichever is
     * higher.
     */
    private final Logger logger = Logger.getLogger(classLogger, null);

    /**
     * Registers this IQ provider into given <tt>ProviderManager</tt>.
     */
    public static void registerVeazzyDonationAmountIqProvider() {
        ProviderManager.addIQProvider(VeazzyDonationAmountIq.ELEMENT_NAME,
                VeazzyDonationAmountIq.NAMESPACE,
                new VeazzyDonationAmountIqProvider());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VeazzyDonationAmountIq parse(XmlPullParser parser, int initialDepth)
            throws Exception {
        String namespace = parser.getNamespace();

        // Check the namespace
        if (!VeazzyDonationAmountIq.NAMESPACE.equals(namespace)) {
            return null;
        }

        String rootElement = parser.getName();

        VeazzyDonationAmountIq iq;

        if (VeazzyDonationAmountIq.ELEMENT_NAME.equals(rootElement)) {
            
            iq = new VeazzyDonationAmountIq();
            
            String jidStr = parser.getAttributeValue("", VeazzyDonationAmountIq.JID_ATTR_NAME);
            if (jidStr != null) {
                Jid jid = JidCreate.from(jidStr);
                iq.setJid(jid);
            }

            String actorStr
                    = parser.getAttributeValue("", VeazzyDonationAmountIq.ACTOR_ATTR_NAME);
            if (actorStr != null) {
                Jid actor = JidCreate.from(actorStr);
                iq.setActor(actor);
            }
            
            String avatarStr
                    = parser.getAttributeValue("", VeazzyDonationAmountIq.AVATAR_ATTR_NAME);
            iq.setAvatar(avatarStr);
            
            String currencyStr
                    = parser.getAttributeValue("", VeazzyDonationAmountIq.CURRENCY_ATTR_NAME);
            iq.setCurrency(currencyStr);
            
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
                        iq.setDonationAmount(parser.getText());
                    } else {
                        logger.warn("Getting donationAmount request without value");
                    }
                    break;
                }
            }
        }

        return iq;
    }
}
