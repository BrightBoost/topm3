# Starter files — Resource ownership

The runnable application is the prepared [DevPro Authenticator](../../../devpro-authenticator-main/README.md). Its filter verifies JWTs and exposes the verified subject to the protected endpoint, so the starter is limited to the new resource and its HTTP tests.

Copy `ProfilesResource.java` to:

`devpro-authenticator-main/authenticator-app/src/main/java/nl/topicus/devpro/authenticator/app/`

Copy `ProfilesResourceTest.java` to:

`devpro-authenticator-main/authenticator-app/src/test/java/nl/topicus/devpro/authenticator/app/`

Complete the ownership check and run `mvn -pl authenticator-app test` from the authenticator project's root.
