# Upgrade Plan: doacaoalimentos (20260508023724)

- **Generated**: 7 de maio de 2026, 02:37 UTC
- **HEAD Branch**: N/A (git not available)
- **HEAD Commit ID**: N/A (git not available)

## Available Tools

**JDKs**
- JDK 1.8.0: C:\Users\zezao\.jdk\jdk-8\bin (not available for baseline - baseline will be skipped)
- JDK 21.0.8: C:\Program Files\Java\jdk-21\bin (target JDK, used by steps 3-6)

**Build Tools**
- Maven 3.9.14: C:\mavem\apache-maven-3.9.14\bin (compatible with Java 21)

## Guidelines

> Note: You can add any specific guidelines or constraints for the upgrade process here if needed, bullet points are preferred.

- Upgrade to latest LTS Java version (Java 21)
- Minimal dependency changes to ensure compatibility
- Focus on core Java and build plugin compatibility

## Options

- Working branch: appmod/java-upgrade-20260508023724
- Run tests before and after the upgrade: true

## Upgrade Goals

- **Target Java Version**: Java 21 LTS

## Technology Stack

| Technology/Dependency    | Current | Min Compatible | Why Incompatible                               |
| ------------------------ | ------- | -------------- | ---------------------------------------------- |
| Java                     | 1.7     | 21             | User requested upgrade to latest LTS           |
| Maven (CLI)              | 3.9.14  | 3.9.0          | Compatible; no upgrade needed                 |
| maven-compiler-plugin    | 3.8.0   | 3.11.0         | 3.8.0 cannot compile Java 21 bytecode          |
| maven-surefire-plugin    | 2.22.1  | 3.0.0          | Older versions lack Java 21 support            |
| maven-war-plugin         | 3.2.2   | 3.3.1          | Compatible; optional update recommended       |
| JUnit                    | 4.11    | 4.13.2         | Compatible; update for Java 21 compatibility  |

## Derived Upgrades

- **maven-compiler-plugin 3.8.0 → 3.11.0**: Required for Java 21 compilation support. Version 3.11.0+ explicitly supports Java 21 source and target levels.
- **maven-surefire-plugin 2.22.1 → 3.0.0+**: Required for Java 17+ testing support. Earlier versions may encounter compatibility issues with module system and encapsulation.
- **JUnit 4.11 → 4.13.2**: Recommended update to latest stable 4.x release for improved Java 21 compatibility and bug fixes.

## Upgrade Steps

- **Step 1: Setup Environment**
  - **Rationale**: Verify Java 21 JDK is available and accessible. This is a precondition for all subsequent upgrade steps.
  - **Changes to Make**: Verify Java 21 JDK installation; verify Maven 3.9.14+ available
  - **Verification**: `java -version` and `mvn -version`; Expected: Java 21 and Maven 3.9.14 detected

- **Step 2: Setup Baseline**
  - **Rationale**: Document current project state with Java 1.7. If baseline JDK unavailable, skip this step. This establishes acceptance criteria for comparison post-upgrade.
  - **Changes to Make**: None (diagnostic step)
  - **Verification**: Attempt `mvn clean compile test-compile -q` with Java 1.7; document result. Expected: Success or known compile errors documented
  - **Status**: Will be skipped (base JDK 1.8 not available in proper location)

- **Step 3: Upgrade maven-compiler-plugin to 3.11.0**
  - **Rationale**: Current version 3.8.0 does not support Java 21 bytecode generation. Version 3.11.0+ is required for Java 21 source/target compilation.
  - **Changes to Make**: Update `maven-compiler-plugin` version from 3.8.0 to 3.11.0 in pom.xml
  - **Verification**: `mvn clean test-compile -q` with Java 21; Expected: Compilation succeeds (test classes compile successfully)

- **Step 4: Upgrade maven-surefire-plugin to 3.0.0**
  - **Rationale**: Version 2.22.1 predates Java 17 module system and lacks full Java 21 support. Version 3.0.0+ required for reliable test execution on Java 21.
  - **Changes to Make**: Update `maven-surefire-plugin` version from 2.22.1 to 3.0.0 in pom.xml
  - **Verification**: `mvn clean test-compile -q` with Java 21; Expected: Compilation succeeds

- **Step 5: Update Java Source/Target to 21**
  - **Rationale**: Update pom.xml compiler properties to target Java 21. This directs Maven to generate Java 21 bytecode and enables Java 21 language features.
  - **Changes to Make**: Update `maven.compiler.source` from 1.7 to 21; Update `maven.compiler.target` from 1.7 to 21
  - **Verification**: `mvn clean test-compile -q` with Java 21; Expected: Compilation succeeds with Java 21 bytecode generated

- **Step 6: Upgrade JUnit and Final Validation**
  - **Rationale**: Final step combines JUnit dependency upgrade (4.11 → 4.13.2) and full validation. This achieves the Upgrade Success Criteria: all targets met, compilation succeeds for both main and test code, and all tests pass (100% pass rate).
  - **Changes to Make**: Update JUnit version from 4.11 to 4.13.2; resolve any test failures through iterative fixes
  - **Verification**: `mvn clean test -q` with Java 21; Expected: All tests pass (100% pass rate); Compilation succeeds for both main and test source code

## Key Challenges

- **Java 1.7 → 21 Feature Gap**: Large version jump spans 14+ years of Java evolution. While this simple project has minimal legacy code, any reflection usage against encapsulated modules (JDK 9+) or deprecated APIs would cause failures. Mitigation: Simple codebase (single empty class) poses minimal risk; verify no reflection or internals access during testing.
