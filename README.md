Custom Google OAuth Login SSO
[📄 View Complete OAuth2 Flow](./complete%20flow%20custom%20oauth.txt)

This project demonstrates a custom implementation of **Google OAuth 2.0 Login (SSO)** using Spring Boot without relying on Spring Security's built-in OAuth2 login flow.


The complete OAuth2 Authorization Code Flow is implemented manually, 
including:

- Redirecting the user to Google's Authorization Server
- Handling the authorization code callback
- Exchanging the authorization code for an access token
- Calling Google's UserInfo API
- Retrieving user details such as email, name, and profile picture
- Creating and managing the user session
- Redirecting the authenticated user to the profile page

The main purpose of this project is to understand how **OAuth2 SSO works internally** and how frameworks such as Spring Security handle these steps behind the scenes.
