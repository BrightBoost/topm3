# Starter files — Access token versus ID token

The runnable application is the prepared [DevPro Authenticator](../../../devpro-authenticator-main/README.md). It already runs the authorization-code flow and contains the HTTP test harness.

Copy `TokenPurposeExerciseTest.java` to:

`devpro-authenticator-main/authenticator-app/src/test/java/nl/topicus/devpro/authenticator/app/`

The test is intentionally focused on the token-substitution boundary. Extend it with the requested response-header assertions, then run `mvn -pl authenticator-app test` from the authenticator project's root.
