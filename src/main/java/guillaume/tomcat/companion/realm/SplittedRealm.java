/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package guillaume.tomcat.companion.realm;

import java.security.Principal;
import java.security.cert.X509Certificate;

import org.apache.catalina.Realm;
import org.apache.catalina.realm.CombinedRealm;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

/**
 * Realm implementation that contains one or more realms. Authentication is
 * attempted for each realm in the order they were configured. If any realm
 * authenticates the user then the authentication succeeds. When combining
 * realms usernames should be unique across all combined realms.
 */
public class SplittedRealm extends CombinedRealm {

	private static final Log log = LogFactory.getLog(SplittedRealm.class);

	/**
	 * Descriptive information about this Realm implementation.
	 */
	protected static final String name = "splittedRealm";

	/**
	 * Realm used to authenticate, can not be empty.
	 */
	private Realm authenticator;

	/**
	 * Realm used to determine roles, can not be empty end can not need
	 * authentication.
	 */
	private Realm authorizer;

	
	@Override
	public void addRealm(Realm theRealm) {
		
		if (theRealm instanceof org.apache.catalina.realm.JNDIRealm) {
			this.authenticator = theRealm;
		}
		
		if (theRealm instanceof org.apache.catalina.realm.DataSourceRealm) {
			this.authorizer = theRealm;
		}

		super.addRealm(theRealm);
	}

	/**
	 * Return the Principal associated with the specified username, which
	 * matches the digest calculated using the given parameters using the method
	 * described in RFC 2069; otherwise return <code>null</code>.
	 * 
	 * @param username
	 *            Username of the Principal to look up
	 * @param clientDigest
	 *            Digest which has been submitted by the client
	 * @param nonce
	 *            Unique (or supposedly unique) token which has been used for
	 *            this request
	 * @param realmName
	 *            Realm name
	 * @param md5a2
	 *            Second MD5 digest used to calculate the digest : MD5(Method +
	 *            ":" + uri)
	 */
	@Override
	public Principal authenticate(String username, String clientDigest,
			String nonce, String nc, String cnonce, String qop,
			String realmName, String md5a2) {

		// This method should never be called
		// Stack trace will show where this was called from
		UnsupportedOperationException uoe = new UnsupportedOperationException(
				sm.getString("splittedRealm.authenticate"));
		log.error(sm.getString("splittedRealm.unexpectedMethod"), uoe);
		throw uoe;
		
		
/*
		Principal authenticatedUser = null;

		for (Realm realm : realms) {
			if (log.isDebugEnabled()) {
				log.debug(sm.getString("splittedRealm.authStart", username,
						realm.getInfo()));
			}

			authenticatedUser = realm.authenticate(username, clientDigest,
					nonce, nc, cnonce, qop, realmName, md5a2);

			if (authenticatedUser == null) {
				if (log.isDebugEnabled()) {
					log.debug(sm.getString("splittedRealm.authFail", username,
							realm.getInfo()));
				}
			} else {
				if (log.isDebugEnabled()) {
					log.debug(sm.getString("splittedRealm.authSucess",
							username, realm.getInfo()));
				}
				break;
			}
		}
		return authenticatedUser;
*/		
	}

	/**
	 * Return the Principal associated with the specified username and
	 * credentials, if there is one; otherwise return <code>null</code>.
	 * 
	 * @param username
	 *            Username of the Principal to look up
	 * @param credentials
	 *            Password or other credentials to use in authenticating this
	 *            username
	 */
	@Override
	public Principal authenticate(String username, String credentials) {

		Principal authenticatedUser = null;

		if (log.isDebugEnabled()) {
			log.debug(sm.getString("splittedRealm.authStart", username,	authenticator.getInfo()));
		}

		authenticatedUser = authenticator.authenticate(username, credentials);
		
		if (authenticatedUser == null) {
			if (log.isDebugEnabled()) {
				log.debug(sm.getString("splittedRealm.authFail", username, authenticator.getInfo()));
			}
		} else {

			if (log.isDebugEnabled()) {
				log.debug(sm.getString("splittedRealm.authSucess", username,	authenticator.getInfo()));
				log.debug(sm.getString("splittedRealm.2ndAuthStart", username,	authorizer.getInfo()));
			}

			authenticatedUser = null ;
			credentials = "no-password";
			authenticatedUser = authorizer.authenticate(username, credentials);
			
			if (authenticatedUser == null) {
				if (log.isDebugEnabled()) {
					log.debug(sm.getString("splittedRealm.2authFail", username, authorizer.getInfo()));
				}
			} else {
				if (log.isDebugEnabled()) {
					log.debug(sm.getString("splittedRealm.2authSucess", username,	authorizer.getInfo()));
				}
			}
			
		}

		return authenticatedUser;
	}

	/**
	 * Return the Principal associated with the specified chain of X509 client
	 * certificates. If there is none, return <code>null</code>.
	 * 
	 * @param certs
	 *            Array of client certificates, with the first one in the array
	 *            being the certificate of the client itself.
	 */
	@Override
	public Principal authenticate(X509Certificate[] certs) {
		// This method should never be called
		// Stack trace will show where this was called from
		UnsupportedOperationException uoe = new UnsupportedOperationException(
				sm.getString("splittedRealm.authenticate"));
		log.error(sm.getString("splittedRealm.unexpectedMethod"), uoe);
		throw uoe;
	}

}