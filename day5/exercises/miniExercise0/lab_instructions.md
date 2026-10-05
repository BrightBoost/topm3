# Choose the Best Authentication Mechanism

## Overview

- Review several use cases
- Match each scenario to the best auth model
- Discuss the trade-offs and risks

---

# Lab: Choose the Best Authentication Mechanism

## Scenario / Context

Your architecture team is reviewing authentication choices for several upcoming API integrations. The team needs to recommend a fitting approach for each case and explain the operational and security consequences. A poor fit could expose user credentials, give services excessive access, or add identity infrastructure that the use case does not need.

## Learning Goals

- Match API use cases to suitable authentication models.
- Distinguish JWT format from token-based protocols.
- Compare user and service identity requirements.
- Evaluate security and operational trade-offs.
- Justify a mechanism while identifying its risks.

## Prerequisites

- Basic understanding of HTTP APIs and authentication.
- Familiarity with Basic Auth, API keys, bearer tokens, JWT, OAuth/OIDC, and TLS.
- No coding, software installation, or starter project is required.

# Lab Parts

The lab contains **2 parts**.

## Part 1: Match the use cases

### What you will do

In small groups, read the case cards and choose a primary authentication approach for each. Record your recommendation and one sentence of reasoning. You may identify a reasonable alternative, but state what would make it preferable.

**Case cards**

1. **Internal service call:** A scheduled inventory service calls a pricing API. There is no end user, and both services are operated by the same organization.
2. **Customer-facing app:** A web application lets customers sign in using the organization's external identity provider. The API needs a user identity and limited access.
3. **Workshop demo:** A local demo has a tiny in-memory user directory and teaches the difference between login and permissions.
4. **Partner integration:** A small number of registered partners call a public API as their own systems. Each partner can be disabled independently.
5. **Fine-grained user access:** A mobile app lets users access different API capabilities depending on the access granted during sign-in.

For each case, consider Basic Auth, API keys, bearer access tokens, OAuth 2.0/OIDC, and mutual TLS where appropriate. JWT is a token format that may carry claims; it is not by itself a complete login or authorization protocol.

### Success criteria

- Every case has a recommended primary approach.
- Each recommendation is tied to its caller and trust boundary.
- The group distinguishes authentication from authorization.
- JWT is described as a format, not as a protocol replacement.

### Hints

<details>
<summary>Hint 1</summary>

Start by deciding whether the caller is a person, an application, or both.

</details>

<details>
<summary>Hint 2</summary>

Ask whether the API needs user identity, service identity, or delegated access.

</details>

<details>
<summary>Hint 3</summary>

Consider OAuth/OIDC when a user signs in through a provider. For service identities, compare API keys with stronger options such as mutual TLS.

</details>

<details>
<summary>Hint 4</summary>

For each case, complete: “We recommend **_ because _**. The main risk or cost is \_\_\_.” Then name one alternative and the condition that would justify it.

</details>

## Part 2: Defend the trade-offs

### What you will do

Compare recommendations with another group. Challenge one choice by changing a constraint: for example, add a second service operator, require per-user audit records, or increase the number of external partners. Decide whether the mechanism should change and name additional controls the API still needs.

### Success criteria

- The group can explain at least one alternative and its trade-off.
- At least one changed constraint leads to a reasoned decision.
- The discussion covers credential/token protection and lifecycle.
- The group identifies authorization checks as a separate API responsibility.

### Hints

<details>
<summary>Hint 1</summary>

A suitable protocol does not automatically make the API secure.

</details>

<details>
<summary>Hint 2</summary>

Include TLS, least privilege, revocation or rotation, and monitoring in the comparison where relevant.

</details>

<details>
<summary>Hint 3</summary>

When the requirement changes, revisit who issues credentials, who validates them, and how access is limited.

</details>

# Bonus Challenge (Optional)

Create a “decision reversal” for one case: change a single business or security constraint that would make your second-choice mechanism a better fit. Explain why that constraint changes the trust model.

# Reflection Questions

1. Which case made the choice between an API key and mutual TLS most difficult, and which trust assumption decided it?
2. What did the customer-facing application need from OAuth/OIDC that a shared API key could not provide?
3. Which extra controls would your team require before using bearer tokens in production?
4. Which part of the discussion was hardest to resolve: caller identity, access scope, or credential lifecycle?
5. How might partner offboarding change the way API keys or client credentials are issued and rotated?
6. If the internal service later needed to act on behalf of a user, what would need to change in its authentication model?
7. Why should authorization checks remain in the API even when a trusted identity provider issues the token?
