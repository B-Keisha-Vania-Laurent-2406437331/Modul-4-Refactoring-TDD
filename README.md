[![Deploy to Koyeb](https://www.koyeb.com/static/images/deploy/button.svg)](https://app.koyeb.com/deploy?name=eshop-kurrorro&type=git&repository=B-Keisha-Vania-Laurent-2406437331%2FModul-2-CICD-DevOps&branch=main&builder=dockerfile&instance_type=free&regions=was&instances_min=0&autoscaling_sleep_idle_delay=3900&ports=8080%3Bhttp%3B%2F&hc_protocol%5B8080%5D=tcp&hc_grace_period%5B8080%5D=5&hc_interval%5B8080%5D=30&hc_restart_limit%5B8080%5D=3&hc_timeout%5B8080%5D=5&hc_path%5B8080%5D=%2F&hc_method%5B8080%5D=get)

## Deployment Link
https://bewildered-essa-adpro-tutorial-kurrorro-a7136900.koyeb.app

<details>
<summary><b>Module 1 - Coding Standards</b></summary>

# Reflection 1

## 1. Coding Principles
In implementing this project, I have applied several Clean Code and Secure Coding practices to ensure the software is maintainable, readable, and secure:

* **Meaningful Names (Penamaan yang Bermakna):** I used clear and descriptive names for variables, methods, and classes (e.g., `ProductRepository`, `create`, `delete`). This practice makes the code **self-documenting**, allowing other developers to understand the logic easily without needing extensive comments.

* **Single Responsibility Principle (SRP):** I adhered to SRP by strictly separating application logic into distinct layers. The **Controller** handles HTTP requests, the **Service** contains business logic, and the **Repository** manages data access. This separation ensures that each class has only one reason to change.

* **Secure Coding (UUID Generation):** Instead of using sequential integers (1, 2, 3...), I utilized **UUIDs** (Universally Unique Identifiers) for generating unique product IDs. This approach significantly enhances security by preventing **ID Enumeration Attacks**, where an attacker could otherwise guess valid IDs by simply incrementing numbers.

## 2. Areas for Improvement
Despite the implementations above, I identified a few critical areas where the code quality and security can be improved:

* **Input Validation:** Previously, the application crashed with a `TypeMismatchException` when submitting empty forms because the primitive `int` type could not handle null values. Additionally, there was no validation preventing negative quantities.  
    * *The Improvement (BackEnd):* I refactored the model to use the `Integer` Wrapper Class instead of `int`, allowing safe handling of null values. I then implemented validation using annotations like `@NotNull` and `@Min(1)` and updated the Controller to use `@Valid` and `BindingResult`. This ensures that invalid input is caught and not causing a system crash (Whitelabel Error Page).
    * *The Improvement (FrontEnd):* I implemented **Client-Side** Validation directly in the HTML. I added the `required` attribute to ensure fields are not empty and the `min="1"` attribute to restrict negative input.
 
# Reflection 2

## 1. Unit Testing & Code Coverage
After writing the unit tests for this project, here are my key takeaways regarding code quality and testing metrics:

* **Confidence in Code Stability:**
    Writing unit tests has significantly increased my confidence in the application's stability. I can verify that core features (Create, Edit, Delete) function correctly. More importantly, these tests act as a safety net during **refactoring**, ensuring that future changes do not break existing functionality.

* **Number of Unit Tests:**
    There is no fixed number of unit tests required for a class. Instead of focusing on quantity, the focus should be on **Scenario Coverage**. A good test suite must cover:
    * **Positive Cases (Happy Path):** Ensures the code works as expected under normal conditions.
    * **Negative Cases (Error Handling):** Ensures the code handles invalid input or exceptions gracefully.
    * **Edge Cases (Boundary Values):** Ensures the code handles limits (e.g., empty lists, maximum values).

* **100% Code Coverage:**
    I learned that **100% Code Coverage does not guarantee bug-free code**. Coverage only measures the percentage of lines executed during tests, but it fails to detect:
    * **Missing Requirements:** Features that were never implemented cannot be caught by coverage tools.
    * **Uncaught Edge Cases:** If a developer forgets to write a test for a specific edge case, the coverage might still be 100% while the bug remains.
    Therefore, code coverage is a useful metric but must be complemented by thoughtful test design.

## 2. Functional Test Cleanliness & Code Quality
Regarding the creation of a new functional test suite to verify the number of items in the product list:

* **Code Quality Issue: Code Duplication**
    If I simply copy-paste the setup procedures (instance variables like `serverPort`, `testBaseUrl`, and `@BeforeEach` setup) from `CreateProductFunctionalTest.java` to a new test class, I would be violating the **DRY (Don't Repeat Yourself)** principle. This creates unnecessary **boilerplate code** in every test file.

* **Maintenance Nightmare:**
    Duplicating setup code drastically reduces maintainability. For example, if the testing configuration changes (e.g., changing how the `baseUrl` is constructed or switching from a random port to a fixed port), I would have to manually update **every single test file**. This increases the risk of inconsistencies and human error.

* **Readability & Focus:**
    A clean test class should focus solely on the **test logic** (verifying the product count), not on **infrastructure setup**. Cluttering the class with repetitive setup code makes it harder for other developers to read and understand what is actually being tested.

* **Proposed Solution: Inheritance (Base Test Class)**
    To improve code cleanliness, I should implement a **Base Functional Test Class** (e.g., `BaseFunctionalTest`).
    * **Encapsulation:** This class would hold the common setup logic (`@LocalServerPort`, `@Value`, `setUp()` method).
    * **Inheritance:** Specific test classes (like `CreateProductFunctionalTest` and the new `CountProductFunctionalTest`) would simply **extend** this base class.
    * **Benefit:** This approach eliminates duplication, centralizes configuration management, and keeps the test classes clean and focused on their specific scenarios.
</details>

<details>
<summary><b>Module 2 - CI/CD & DevOps</b></summary>

# Reflection 1

## 1. Code Quality Issue(s)

During this module, I utilized **SonarCloud** as a static analysis tool to identify and resolve various code quality issues. Since I am using the free tier of SonarCloud, I had to merge my changes to the `main` branch first to access the detailed analysis reports, as I couldn't identify the specific issues beforehand, this allowed me to finally see and resolve them properly :D.

The following issues were identified and resolved:

* **Security Hotspot:** I addressed a warning regarding unverified dependencies in the project. My strategy was to generate a `verification-metadata.xml` file to verify the integrity and authenticity of external libraries. This ensures that all dependencies are safe and have not been tampered with before they are used in the application build.
* **Field Injection:** I refactored instances of field injection using `@Autowired` into **Constructor Injection** to improve the maintainability and testability of the code.
* **Unused Imports:** I removed several unused import statements to keep the codebase clean and organized.
* **Redundant Exception Declarations:** I removed unnecessary `throws Exception` clauses in the test files as they were not required for the specific test logic.
* **Missing Assertions:** I added appropriate assertions to test classes, such as `EshopApplicationTests`, to ensure that the tests effectively validate the application's behavior.
* **Dependency Management:** I reorganized the dependencies in the `build.gradle.kts` file by grouping them logically for better readability.

Beyond source code issues, I also encountered several technical difficulties during the deployment phase such as:

* **Workflow Resolution:** I faced an error where the GitHub Action for Koyeb could not be found. To fix this, I implemented a manual installation of the Koyeb CLI using the official shell script provided in the documentation (Source: https://www.koyeb.com/docs/build-and-deploy/cli/installation).

## 2. Has the current implementation met the definition of Continuous Integration and Continuous Deployment?

I believe the current implementation successfully meets the definitions of Continuous Integration (CI) and Continuous Deployment (CD). The CI aspect is fulfilled by the automated workflow that triggers test suites and static analysis via SonarCloud upon every push, ensuring code integrity before it is merged. The CD aspect is achieved through the automated deployment to Koyeb, which ensures that any successful push to the `main` branch is immediately reflected in the live production environment. Additionally, the use of `paths-ignore` for documentation files demonstrates an efficient pipeline that avoids unnecessary deployment cycles for non-code changes.

</details>

<details>
<summary><b>Module 3 - OO Principles</b></summary>

# Reflection 1

## 1. SOLID Principles
In this project, I evaluated the existing codebase and applied several **SOLID** principles to improve its maintainability and readability:

* **Single Responsibility Principle (SRP):**
  * I separated the `CarController` class, which was initially placed inside the `ProductController.java` file, into its own dedicated `CarController.java` file. 
  * I moved the business logic for generating UUIDs for new cars from the `CarRepository` to the `CarServiceImpl`. This ensures the repository is strictly responsible for data storage operations, while the service handles business logic.
* **Open-Closed Principle (OCP) & Liskov Substitution Principle (LSP):**
  * I removed the `extends ProductController` inheritance from `CarController`. Conceptually, a `CarController` is not a true substitute for a `ProductController` (violating LSP). By removing this inheritance, both controllers are now closed to unintended modifications caused by changes in the other class, while remaining open for their respective extensions (satisfying OCP).
* **Interface Segregation Principle (ISP):**
  * This principle was already satisfied in the initial design. The interfaces (`CarService` and `ProductService`) are small, specific, and focused solely on their respective entities. The implementation classes are not forced to depend on or implement methods they do not use.
* **Dependency Inversion Principle (DIP):**
  * In `CarController`, I changed the injected dependency from the concrete class `CarServiceImpl` to its abstraction, the `CarService` interface.

## 2. Advantages of applying SOLID principles
Applying SOLID principles makes the codebase significantly easier to maintain, scale, and test. It reduces coupling between components and increases cohesion. 

**Example:** By applying SRP, I separated the UUID generation logic into `CarServiceImpl` and left `CarRepository` purely for data storage. Because of this, if the business later decides to change how IDs are generated (for instance, using a sequential database ID or a custom string format instead of a UUID), I only need to modify the Service layer. The Repository remains completely untouched, making the system easier to maintain. Furthermore, by applying DIP (`CarController` depending on the `CarService` interface rather than a concrete class), the application becomes highly extensible. If we ever need to implement a new type of car service (e.g., a `PremiumCarServiceImpl` that applies different business rules), we can easily swap the implementation being injected without modifying a single line of code inside the `CarController`.

## 3. Disadvantages of not applying SOLID principles
Not applying SOLID principles leads to "spaghetti code" that is tightly coupled, fragile, and prone to cascading errors where a change in one place unexpectedly breaks another unrelated part of the system.

**Example:** A major disadvantage of violating LSP and OCP was seen when `CarController` originally extended `ProductController`. If left unfixed, any future modifications to `ProductController`, such as adding a mandatory validation, a new security check, or a product-specific `@ModelAttribute` would be automatically and incorrectly inherited by `CarController`. This means adding a new feature for "Products" could unexpectedly crash or alter the behavior of the "Cars" feature. Additionally, violating SRP by keeping ID generation inside the `CarRepository`'s `create` method would cause severe logical bugs. For instance, if we later added a feature to "Import" existing cars from an external file where the cars *already have* their own IDs, the repository would forcefully overwrite their original IDs with newly generated UUIDs because it improperly mixed business logic with data storage.

</details>

<details>
<summary><b>Module 4 - TDD & Refactoring</b></summary>

# Reflection

## 1. TDD Flow Usefulness

The TDD flow was useful for me in this module. Writing tests before the actual code made me think about what the code should do before writing it. This helped me avoid confusion later, especially when implementing the Payment feature where there were many conditions to check, like voucher code validation and how payment status affects order status.

The RED-GREEN-REFACTOR cycle also helped me stay focused. When a test failed, I only wrote the minimum code needed to make it pass, which kept the code clean. Without TDD, I probably would have over-engineered things or missed some edge cases.

One thing I would improve next time is to make my tests less dependent on implementation details. Some of my tests broke when I refactored the internal logic, even though the behavior did not change. I should focus more on testing the expected output rather than how the code works internally.

## 2. F.I.R.S.T. Principle

My unit tests mostly follow the F.I.R.S.T. principle:

- **Fast:** The tests run quickly because I used Mockito to mock dependencies like repositories, so no real database or I/O is involved.

- **Independent:** Each test uses `@BeforeEach` to set up fresh data, so tests do not affect each other.

- **Repeatable:** Since everything is mocked, the tests give the same result every time regardless of the environment.

- **Self-Validating:** All tests use clear assertions like `assertEquals` and `assertThrows`, so they automatically show pass or fail.

- **Timely:** Because I followed TDD, the tests were written before the production code, which is the right time to write them.

# Bonus 2 Reflection

## 1. What do you think about your partner's code?

The overall structure of the code is clean and easy to follow. The separation between layers (model, repository, service) is properly done. However, there are several areas that need improvement, mainly around the use of hardcoded strings, missing update logic in the repository, incorrect payment status for COD, and the use of field injection instead of constructor injection.

## 2. What did you do to contribute to your partner's code?

I reviewed the pull request and left comments on the code smells I found. I then created a new branch `refactor/[NPM]` based on the `order` branch and fixed the identified issues. Finally, I opened a new pull request from the refactor branch to the `order` branch.

## 3. What code smells did you find?

- **Magic strings:** `PaymentServiceImpl` used hardcoded strings like `"VOUCHER"`, `"COD"`, `"BANK_TRANSFER"`, `"SUCCESS"`, `"REJECTED"`, and `"WAITING"` directly instead of using enums, making the code fragile and hard to maintain.
- **Missing update logic in PaymentRepository:** `save()` always added a new entry without checking if a payment with the same ID already existed, which could cause duplicate data.
- **Exposed internal state:** `findAll()` returned the internal list directly, allowing external code to accidentally modify the repository's data.
- **Field injection:** Both `PaymentServiceImpl` and `OrderServiceImpl` used `@Autowired` field injection instead of constructor injection, which makes the dependencies less explicit and harder to test.
- **Incorrect COD status:** The COD validation set payment status to `"SUCCESS"` instead of `"WAITING"`, which does not match the expected payment flow.
- **Silent failure in createOrder:** `OrderServiceImpl.createOrder()` returned `null` when a duplicate order was found instead of throwing an exception, making bugs harder to detect.

## 4. What refactoring steps did you suggest and execute?

- Added `PaymentMethod` and `PaymentStatus` enums to replace all magic strings in `PaymentServiceImpl`.
- Fixed `PaymentRepository.save()` to check for existing IDs before adding, enabling proper update behavior.
- Fixed `PaymentRepository.findAll()` to return a new `ArrayList` copy instead of the internal list.
- Fixed COD validation to correctly set status to `"WAITING"` instead of `"SUCCESS"`.
- Replaced `@Autowired` field injection with constructor injection in both `PaymentServiceImpl` and `OrderServiceImpl`.
- Updated `OrderServiceImpl.createOrder()` to throw `IllegalArgumentException` instead of returning `null` for duplicate orders.

</details>
