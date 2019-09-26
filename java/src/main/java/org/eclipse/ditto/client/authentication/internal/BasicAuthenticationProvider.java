/*
 * Copyright (c) 2019 Contributors to the Eclipse Foundation
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.ditto.client.authentication.internal;

import static org.eclipse.ditto.model.base.common.ConditionChecker.checkNotNull;

import org.eclipse.ditto.client.authentication.AuthenticationProvider;
import org.eclipse.ditto.client.configuration.AuthenticationConfiguration;
import org.eclipse.ditto.client.configuration.internal.BasicAuthenticationConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.neovisionaries.ws.client.WebSocket;

/**
 * Implements basic authentication for a {@link com.neovisionaries.ws.client.WebSocket} channel.
 *
 * @since 1.0.0
 */
public final class BasicAuthenticationProvider implements AuthenticationProvider<WebSocket> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BasicAuthenticationProvider.class);

    private final BasicAuthenticationConfiguration configuration;

    public BasicAuthenticationProvider(final BasicAuthenticationConfiguration configuration) {
        this.configuration = checkNotNull(configuration, "configuration");
    }

    @Override
    public AuthenticationConfiguration getConfiguration() {
        return configuration;
    }

    @Override
    public WebSocket prepareAuthentication(final WebSocket channel) {
        final String username = configuration.getUsername();
        configuration.getAdditionalHeaders().forEach(channel::addHeader);
        LOGGER.info("Using Basic Auth. Authenticating user <{}>", username);
        return channel.setUserInfo(username, configuration.getPassword());
    }

}
