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

import java.util.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import static org.springframework.web.context.request.RequestContextHolder.currentRequestAttributes;

/**
 * Created by andre on 13.06.2016.
 */
public class GoogleCustomUserInfoTokenService extends UserInfoTokenServices {

    public GoogleCustomUserInfoTokenService(String userInfoEndpointUrl, String clientId) {
        super(userInfoEndpointUrl, clientId);
    }

    public OAuth2Authentication loadAuthentication(String accessToken) throws AuthenticationException {
        OAuth2Authentication auth = super.loadAuthentication(accessToken);
        if (userIsKnown(getUserDetails(auth))) {
            getUserDetails(auth).put("userId", getUserId(getUserDetails(auth)));
            getUserDetails(auth).put("userEmail", getUserEmail(getUserDetails(auth)));
            getUserDetails(auth).put("userName", getUserName(getUserDetails(auth)));
            getUserDetails(auth).put("token", accessToken);
            getUserDetails(auth).put("provider", "Google");
            getUserDetails(auth).put("JSessionId", getJSessionId());
        }
        else {
            throw new UsernameNotFoundException("Unknown user: " + getUserEmail(getUserDetails(auth)));
        }

        return auth;
    }

    private boolean userIsKnown(Map<String, Object> userDetails) {
        String email = getUserEmail(userDetails);
        if (email.equals("unknown")) return false;
        // look in db here
        return true;
    }

    private String getUserName(Map<String, Object> userDetails) {
        if(userDetails.containsKey("displayName")){
            String userName = (String) userDetails.get("displayName");
            return userName;
        }
        return "unknown";
    }

    private String getUserEmail(Map<String, Object> userDetails) {
        if (userDetails.containsKey("emails")) {
            List<Map<String, Object>> emails =
                    (List<Map<String, Object>>) userDetails.get("emails");
            for (Map<String, Object> emailAccount : emails) {
                if (emailAccount.containsKey("value")) {
                    String email = (String) emailAccount.get("value");
                    return email;
                }
            }
        }
        return "unknown";
    }

    private String getUserId(Map<String, Object> userDetails) {
        if(userDetails.containsKey("id"))
        {
            return (String)userDetails.get("id");
        }
        return "unknown";
    }

    private Map<String, Object> getUserDetails(OAuth2Authentication auth) {
        return (Map<String, Object>) auth.getUserAuthentication().getDetails();
    }
}
