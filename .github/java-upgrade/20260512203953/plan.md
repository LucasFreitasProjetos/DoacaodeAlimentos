# Upgrade Plan: doacaoalimentos (20260512203953)

- **Generated**: 2026-05-12 20:39:53
- **HEAD Branch**: N/A
- **HEAD Commit ID**: N/A

## Available Tools

**JDKs**
- JDK 1.8.0_482: C:\Users\zezao\.jdk\jdk-8\bin
- JDK 11.0.28: C:\Users\zezao\.jdk\jdk-11.0.28\bin
- JDK 17.0.19: C:\Users\zezao\AppData\Roaming\Code\User\globalStorage\pleiades.java-extension-pack-jdk\java\17\bin
- JDK 21.0.8: C:\Program Files\Java\jdk-21\bin (target runtime)
- JDK 25.0.3: C:\Users\zezao\AppData\Roaming\Code\User\globalStorage\pleiades.java-extension-pack-jdk\java\25\bin
- JDK 26: C:\Users\zezao\AppData\Roaming\Code\User\globalStorage\pleiades.java-extension-pack-jdk\java\latest\bin
- Base project JDK 7: not available (baseline will be skipped)

**Build Tools**
- Maven 3.9.14: C:\mavem\apache-maven-3.9.14\bin
- Maven Wrapper: not present

## Guidelines

> Note: You can add any specific guidelines or constraints for the upgrade process here if needed, bullet points are preferred.

## Options

- Working branch: appmod/java-upgrade-20260512203953
- Run tests before and after the upgrade: true

## Upgrade Goals

- Upgrade runtime/source compatibility to Java 21 (latest LTS)

## Technology Stack

| Technology/Dependency           | Current      | Min Compatible | Why Incompatible |
| ------------------------------ | ------------ | -------------- | ---------------- |
| Java                           | 1.7          | 21             | User requested latest LTS; project source/target is outdated |
| Maven                          | 3.9.14       | 3.9.0          | Maven 3.9+ supports Java 21 |
| maven-compiler-plugin          | 3.8.0        | 3.11.0         | Older plugin versions cannot compile Java 21 bytecode |
| maven-surefire-plugin          | 2.22.1       | 3.1.2          | Older Surefire may fail on Java 21 test execution |
| JUnit                          | 4.11         | 4.13.2         | Very old runtime version; update improves Java 21 compatibility |
| `javax.*` / `sun.*` sources    | none found   | N/A            | No direct incompatible imports found in source scan |

## Derived Upgrades

- Update project source and target levels from Java 7 to Java 21.
- Upgrade `maven-compiler-plugin` to 3.11.0 to support Java 21 compilation.
- Upgrade `maven-surefire-plugin` to 3.1.2 to support test execution on Java 21.
- Upgrade JUnit from 4.11 to 4.13.2 for better compatibility with the target JDK.
- Use local Maven 3.9.14 directly since no Maven wrapper is present.

## Upgrade Steps

- Step 1: Setup Environment
  - **Rationale**: Verify the target JDK and build tool are available before changing the project.
  - **Changes to Make**:
    - Confirm JDK 21 installation at `C:\Program Files\Java\jdk-21\bin`.
    - Confirm Maven 3.9.14 is available at `C:\mavem\apache-maven-3.9.14\bin`.
    - Document that no Maven wrapper exists and the build will use the installed Maven.
  - **Verification**: `"C:\Program Files\Java\jdk-21\bin\java" -version && mvn -v` on Windows; expected JDK 21 and Maven 3.9.x output.

- Step 2: Setup Baseline
  - **Rationale**: Establish a baseline only if the original Java 7 JDK is available.
  - **Changes to Make**:
    - Skip baseline because JDK 7 is not installed on the system.
  - **Verification**: skipped due to missing base JDK.

- Step 3: Upgrade Maven build settings for Java 21
  - **Rationale**: The project must compile with Java 21 and use plugins compatible with the target JDK.
  - **Changes to Make**:
    - Update `<maven.compiler.source>` and `<maven.compiler.target>` from `1.7` to `21`.
    - Upgrade `maven-compiler-plugin` to `3.11.0`.
    - Upgrade `maven-surefire-plugin` to `3.1.2`.
    - Upgrade JUnit dependency to `4.13.2`.
  - **Verification**: `mvn clean test-compile -q` with JDK 21; expected compile success.

- Step 4: Final Validation
  - **Rationale**: Ensure the upgraded project builds and tests pass under the target Java 21 runtime.
  - **Changes to Make**:
    - Run the full test suite under Java 21.
    - Fix any compilation or test failures arising from Java 21 or dependency version changes.
  - **Verification**: `mvn clean test -q` with JDK 21; expected 100% pass.

## Key Challenges

- **Java 7 → Java 21 jump**
  - **Challenge**: Direct source level upgrade requires modern compiler and test plugins, and may expose hidden compatibility issues.
  - **Strategy**: Upgrade build plugins first, then compile and test with JDK 21.

- **No Base JDK 7 available for baseline**
  - **Challenge**: Cannot perform an exact pre-upgrade baseline on the original Java version installed on the system.
  - **Strategy**: Mark baseline as skipped and rely on current source metadata plus tests on Java 21.

- **No Git repository detected**
  - **Challenge**: Changes cannot be tracked via git in this workspace.
  - **Strategy**: Document all file changes carefully and keep the generated plan/progress files in `.github/java-upgrade/20260512203953`.
