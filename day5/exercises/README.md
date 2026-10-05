# Module 3 — Day 5 Security Labs

All labs use the supplied [DevPro Authenticator](../devpro-authenticator-main/README.md) as their runnable starter application. It provides the Maven setup, OAuth/OIDC flow, JWT issuance and verification, protected API, and HTTP test helpers. Each lab's `starter/` folder contains only the focused files learners add or extend, so they can spend lab time on security behavior rather than scaffolding.

## Labs

0. [Choose the Best Authentication Mechanism](miniExercise0/lab_instructions.md) — no-code scenario matching and trade-off discussion.
1. [Trust the Token, Not Its Payload](miniExercise1/lab_instructions.md) — JWT tampering and verifier regression test.
2. [Keep Access Tokens and ID Tokens in Their Lanes](miniExercise2/lab_instructions.md) — OAuth/OIDC flow and HTTP integration test.
3. [Close an IDOR in a Protected API](miniExercise3/lab_instructions.md) — resource ownership and access-control tests.
4. [Harden Bearer-Token Error Responses](miniExercise4/lab_instructions.md) — safe error contract and regression test.

Open each code lab's `starter/README.md` for the target paths of its prepared files. Mini Exercise 0 requires no code or starter project. The existing authenticator project remains unchanged until learners copy the lab-specific starter files into it.
