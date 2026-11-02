# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added

- CHANGELOG.md with the history of the platform since version 0.1.0 (#11).

- LICENSE.md with the QualiTrack Proprietary Software License; `pom.xml` and the README refer to it (#11).

## [1.0.1] - 2026-10-08

### Changed

- CORS and front-end URL of the production profile now point to the Firebase Hosting site `qualitrack-iotech.web.app` (#8).

### Added

- README.md describing the platform, its bounded contexts, configuration, deployment and branching (#10).

## [1.0.0] - 2026-10-07

### Added

- CI: every pull request to `develop` and `main` runs the Maven tests (#2).

- CD: every push to `main` runs the tests, builds the Docker image on the runner, pushes it to Azure Container Registry and updates the Azure Container App (#2, #4, #6).

### Fixed

- Tests compute today's date in Lima time, so they no longer depend on the time zone of the machine (#1).

## [0.13.2] - 2026-10-05

### Fixed

- Product Batch (batch): the first raw material consumption moves a pending batch to in progress, so it counts as a batch in production.

## [0.13.0] - 2026-10-04

### Added

- Payments & Subscriptions (subscription): the subscription plans (Standard Lab and Enterprise, monthly and yearly) are created at startup when the database has none, without overwriting existing plans.

## [0.12.0] - 2026-10-04

### Added

- Profile (profile): new bounded context with the personal data and photo of each user; quality managers can see the profile of their staff.

- Compliance & Alerting (ca): in-app notifications of alerts and batch decisions, and e-mail of critical alerts according to the preferences of each user.

- IAM (iam): users can change the username and e-mail of their account.

### Fixed

- Compliance & Alerting (ca): critical alert e-mails show the detection time in Peru time.

## [0.11.0] - 2026-10-04

### Added

- IAM (iam): account e-mail and password recovery with a six-digit code sent by e-mail; sign-up requires a unique e-mail and the password policy.

- Shared (shared): transactional e-mail through Resend, with SMTP as fallback.

- Payments & Subscriptions (subscription): cancelling the renewal keeps access until the current period ends.

## [0.10.0] - 2026-10-04

### Added

- Reporting & Audit (ra): environmental indicators per period (time in range weighted by the time between readings) and the inventory report of a laboratory or an environment.

- Anti-corruption layers that expose environment readings, alerts, inventory and traceability to Reporting & Audit.

### Removed

- Reporting & Audit (ra): stored KPI dashboards and deviation trends; indicators are calculated on request.

## [0.9.0] - 2026-10-04

### Added

- Compliance & Alerting (ca): one alert per incident of an environment or container; new deviations of the same device and variable join the open alert, and its severity only rises.

- Tracking & Telemetry (tracking): returns to normal are reported and device actions are exposed.

- Reporting & Audit (ra): the escalation of a deviation alert is audited.

## [0.8.0] - 2026-10-04

### Added

- Inventory (inventory) and Product Batch (batch): raw material lots and product batches are stored in monitored containers.

- Reporting & Audit (ra): the container where a product batch is stored is audited.

## [0.7.0] - 2026-10-03

### Added

- Tracking & Telemetry (tracking): environmental profiles with normal and critical ranges per environment and container monitor, readings and actuation events.

### Changed

- REST API reviewed in every bounded context: actions and sub-resources use POST, created resources answer 201 with their location, and resources that depend on a laboratory are nested under it.

### Fixed

- Shared (shared): unknown routes answer 404 and unsupported methods 405.

## [0.6.0] - 2026-10-03

### Added

- Laboratory (laboratory) and IAM (iam): quality managers register operators and auditors with their own sign-in account; credentials are e-mailed or shown once, and auditors only consult.

## [0.5.0] - 2026-10-03

### Added

- Equipment (equipment): equipment and IoT devices per laboratory and environment, status changes and maintenance records.

- Tracking & Telemetry (tracking): whether an IoT device is communicating (TS41).

## [0.4.0] - 2026-10-02

### Added

- Product Batch (batch): pharmaceutical products per environment, batches under their product, raw material usages, equipment and staff taking part in a batch, and its traceability.

## [0.3.0] - 2026-10-02

### Added

- Laboratory (laboratory): environments of a laboratory with their usage, exposed to the other contexts.

- Inventory (inventory): raw materials and lots per environment.

- Shared (shared): created resources return their Location header.

## [0.2.0] - 2026-09-13

### Added

- Inventory (inventory): catalog, reviewed receipts and atomic stock consumption.

## [0.1.0] - 2026-09-12

### Added

- First version of the platform, organized by bounded context with domain, application, infrastructure and interfaces layers: IAM, Payments & Subscriptions, Laboratory, Equipment, Product Batch, Tracking & Telemetry, Compliance & Alerting and Reporting & Audit, plus the shared kernel.

- JWT authentication, onboarding through a Stripe subscription, PDF reports and architecture diagrams.
