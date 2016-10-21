/*
 * Copyright (c) 2016 Memorial Sloan-Kettering Cancer Center.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY, WITHOUT EVEN THE IMPLIED WARRANTY OF MERCHANTABILITY OR FITNESS
 * FOR A PARTICULAR PURPOSE. The software and documentation provided hereunder
 * is on an "as is" basis, and Memorial Sloan-Kettering Cancer Center has no
 * obligations to provide maintenance, support, updates, enhancements or
 * modifications. In no event shall Memorial Sloan-Kettering Cancer Center be
 * liable to any party for direct, indirect, special, incidental or
 * consequential damages, including lost profits, arising out of the use of this
 * software and its documentation, even if Memorial Sloan-Kettering Cancer
 * Center has been advised of the possibility of such damage.
 */

/*
 * This file is part of cBioPortal.
 *
 * cBioPortal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.cbioportal.security.filters.oauth2.google;

import org.springframework.beans.factory.annotation.*;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;

import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;

import static org.springframework.security.oauth2.common.AuthenticationScheme.query;

import java.util.*;
import javax.servlet.Filter;

/**
 * Created by andre on 13.06.2016.
 */
public class GoogleFilter {

    // TDB - should the following properties be moved to GlobalProperties?
    private String clientID;
    @Value("${googleplus.consumer.key}")
    public void setClientID(String property) { this.clientID = property; }

    private String clientSecret;
    @Value("${googleplus.consumer.secret}")
    public void setClientSecret(String property) { this.clientSecret = property; }

    @Autowired
    OAuth2ClientContext oAuth2ClientContext;

    @Autowired
    @Qualifier("listOfFilters")
    ArrayList<Filter> filters;

    @Bean(name="googleFilter")
    public int addFilter() {
        filters.add(createFilter());
        return 1;
    }

    private OAuth2ClientAuthenticationProcessingFilter createFilter() {
        OAuth2ClientAuthenticationProcessingFilter googleFilter = new OAuth2ClientAuthenticationProcessingFilter("/login/google");
        OAuth2RestTemplate googleTemplate = new OAuth2RestTemplate(getClient(), oAuth2ClientContext);
        googleFilter.setRestTemplate(googleTemplate);
        googleFilter.setTokenServices(new GoogleCustomUserInfoTokenService(getProviderResource().getUserInfoUri(), getClient().getClientId()));
        googleFilter.setAllowSessionCreation(true);
        return googleFilter;
    }

    public ResourceServerProperties getProviderResource() {
        ResourceServerProperties resourceServerProperties = new ResourceServerProperties();
        resourceServerProperties.setUserInfoUri("https://www.googleapis.com/plus/v1/people/me");
        resourceServerProperties.setPreferTokenInfo(false);
        GoogleCredential googleCredential;
        return resourceServerProperties;
    }

    private OAuth2ProtectedResourceDetails getClient() {
        AuthorizationCodeResourceDetails authorizationCodeResourceDetails = new AuthorizationCodeResourceDetails();
        authorizationCodeResourceDetails.setClientId(clientID);
        authorizationCodeResourceDetails.setClientSecret(clientSecret);
        authorizationCodeResourceDetails.setAccessTokenUri("https://www.googleapis.com/oauth2/v3/token");
        authorizationCodeResourceDetails.setUserAuthorizationUri("https://accounts.google.com/o/oauth2/auth");
        authorizationCodeResourceDetails.setTokenName("oauth_token");
        authorizationCodeResourceDetails.setClientAuthenticationScheme(query);
        authorizationCodeResourceDetails.setScope(createScopesList());
        return authorizationCodeResourceDetails;
    }

    private List<String> createScopesList() {
        List<String> scopes = new ArrayList<>();
        scopes.add("profile");
        scopes.add("email");
        return scopes;
    }
}
