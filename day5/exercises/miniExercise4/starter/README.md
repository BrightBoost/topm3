# Starter files — Safe bearer-token errors

The runnable application is the prepared [DevPro Authenticator](../../../devpro-authenticator-main/README.md); it already contains the filter, verifier, endpoints, and integration tests.

Copy `ProtectedEndpointHardeningTest.java` to:

`devpro-authenticator-main/authenticator-app/src/test/java/nl/topicus/devpro/authenticator/app/`

Run the focused test first to observe the current behavior. Then update `BearerTokenFilter` in `authenticator-core` and run `mvn test` from the authenticator project's root.
