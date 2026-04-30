# RestAssured-Gate Project Documentation

This documentation provides a comprehensive technical overview of the **RestAssured-Gate** project, a robust API automation framework built using **Java**, **Rest Assured**, and **TestNG**.

---

## Master Technical Reference: Deep-Dive Chaining Logic Analysis

### The "Life of a Request": `createUserWithPOJO` Flow

This section traces the mechanical linkage and "Passing of the Torch" between layers for a typical test case.

#### 1. The Test Layer (`tests` package)
**Flow**: `UserTests` -> `UserPayload` -> `UserApi`
- **Action**: The test method `createUserWithPOJO` triggers the process.
- **Internal Logic**: 
    1. It calls `UserPayload.createUserPOJO()`. Before this call, the data exists only as instructions in the `FakerUtils`. After the call, a `UserPOJO` object is instantiated in memory, populated with random strings (e.g., `firstName="John"`, `lastName="Doe"`, `age=30`).
    2. It then passes this **POJO instance** as an argument to the static method `UserApi.createUser(payload)`.
- **Technical Justification**: Using **Static Methods** here allows for a "stateless" handoff. We don't need to maintain the state of the `UserApi` class; we only care about the data being passed through it.

#### 2. The Application Layer (`api.application` package)
**Flow**: `UserApi` -> `RestResource`
- **Action**: `UserApi.createUser(Object payload)` receives the POJO.
- **Internal Logic**: 
    1. This layer acts as a **Mediator**. It doesn't know *how* to send the request, only *where* it should go (`CREATE_USER` endpoint).
    2. It "hands off" the payload and the endpoint string to `RestResource.post()`.
- **Technical Justification**: This **Intermediate Layer** hides the URI constants. If the endpoint changes from `/users/add` to `/api/v2/register`, we only update `Routes.java`, and the tests remain untouched. This is the **Abstraction Principle** in action.

#### 3. The Engine Layer (`api.rest` package)
**Flow**: `RestResource` -> Rest Assured Engine
- **Action**: Execution of the method chain: `given().spec(getRequestSpec()).body(payload).when().post(path).then().spec(getResponseSpec()).extract().response()`.
- **Chaining Breakdown**:
    - `given()`: Initializes the **FilterableRequestSpecification**. This is the "blank canvas" of the HTTP request.
    - `.spec(getRequestSpec())`: Injects the "DNA" of the request (Base URI, Content-Type=JSON) provided by the **Configuration Layer**.
    - `.body(payload)`: **Critical Serialization Point**. The Rest Assured engine sees that the Content-Type is JSON and the payload is a Java Object. It invokes the **Jackson ObjectMapper** to transform the POJO into a JSON string byte-stream.
    - `.when()`: Syntactic sugar that transitions the specification from "Setup" to "Action".
    - `.post(path)`: The trigger. It combines the Base URI from the spec and the `path` from the Application Layer, then transmits the physical HTTP POST packet over the network.
    - `.then()`: Transitions to the **Response Validation** phase.
    - `.spec(getResponseSpec())`: Applies global validation rules (e.g., "Response must be JSON" and "Response must be fast").
- **Technical Justification**: **Method Chaining** avoids "spaghetti code" by creating a readable, fluent DSL (Domain Specific Language). Each link in the chain modifies the internal state of the request object in a controlled, sequential manner.

#### 4. The Configuration Layer (`api.spec` & `utils` packages)
**Flow**: `ConfigLoader` -> `SpecBuilder` -> `RestResource`
- **Action**: `SpecBuilder` provides the `RequestSpecification` used in the Engine Layer.
- **Internal Logic**: 
    1. Before any test runs, the `ConfigLoader` static block executes, reading `config.properties` into a `Properties` object. This ensures the `Base URI` is residing in memory.
    2. When `getRequestSpec()` is called, it "injects" these settings into a `RequestSpecBuilder`.
- **Technical Justification**: **Static Imports** (e.g., `import static api.spec.SpecBuilder.*`) allow the Engine Layer to pull in these configurations seamlessly, making the code look like a natural extension of the Rest Assured library.

#### 5. The Data Layer (`api.complexPojo` & `payloads` packages)
**Flow**: POJO -> JSON (Serialization)
- **Action**: Conversion of Java memory structures to network-ready strings.
- **Logic**: During the `.body(payload)` call in `RestResource`, the `Jackson` library inspects the `RootUser` or `UserPOJO` class. It uses reflection to read the fields and creates a JSON key-value pair for each field.
- **Technical Justification**: This is vastly superior to "spaghetti" string concatenation (e.g., `"{ \"name\": \"" + name + "\" }"`) because it is type-safe, handles nulls gracefully, and supports complex nesting (like `Address` inside `User`) automatically.

---

## Package Analysis

### Package: `api.application`

#### [UserApi.java](./src/main/java/api/application/UserApi.java)

**File Purpose**: 
This class serves as an **Abstraction Layer** (Business Logic Layer) that wraps the low-level REST calls into high-level, readable methods. It separates the API endpoint logic from the test cases, following the **Page Object Model** (POM) pattern applied to API testing.

**Line-by-Line Breakdown**:
- `1`: Declares the package `api.application`.
- `3-5`: Imports `Routes`, `RestResource`, and Rest Assured `Response`.
- `9-10`: Static imports of `RestResource.UploadFile` and all constants from `Routes`.
- `12`: Class definition for `UserApi`.
- `14-16`: `getUsers()` method. Calls `RestResource.get()` passing `GET_USERS` endpoint. Returns the `Response`.
- `18-20`: `getUser(String id)` method. Calls `RestResource.get()` with a path parameter `id`.
- `22-24`: `createUser(Object payload)` method. Sends a POST request using `RestResource.post()` with the provided payload.
- `26-28`: `updateUser(String id, Object payload)` method. Sends a PUT request.
- `30-32`: `deleteUser(String id)` method. Sends a DELETE request.
- `33-35`: `echoPost(Object name)` method. Handles form-url-encoded data using `encodedTest`.
- `36-38`: `upload(File file)` method. Handles file uploads using the `UploadFile` utility.

**Technical Justification**:
- **Abstraction**: Hides `RestResource` calls, keeping tests clean.
- **Static Methods**: Provides easy access across the framework without instantiation.

---

## Package: `api.complexPojo`

### [RootUser.java](./src/main/java/api/complexPojo/RootUser.java)

**File Purpose**: 
The main **Data Model** for representing a User object in the system. It handles complex, nested data structures for JSON serialization/deserialization.

**Line-by-Line Breakdown**:
- `6`: `@JsonIgnoreProperties(ignoreUnknown = true)` - Jackson annotation to skip unknown JSON fields during deserialization.
- `8`: Default constructor for Jackson.
- `10-36`: Public fields representing user attributes (e.g., `firstName`, `address`, `crypto`).
- `38-66`: Parameterized constructor for manual object creation.
- `68-282`: Getters and Setters for all fields.

**Technical Justification**:
- **Type Safety**: Ensures data integrity by mapping JSON fields to Java types.
- **IgnoreUnknown**: Prevents crashes when the API returns extra fields not mapped in the POJO.

### [Address.java](./src/main/java/api/complexPojo/Address.java)
- **Purpose**: Nested POJO for user address details.
- **Approach**: Uses a dedicated `Coordinates` object for geographic data, demonstrating deep nesting support.

### [Bank.java](./src/main/java/api/complexPojo/Bank.java)
- **Purpose**: Nested POJO for banking information.
- **Approach**: Maps sensitive-like data fields for realistic API testing scenarios.

### [Company.java](./src/main/java/api/complexPojo/Company.java)
- **Purpose**: Nested POJO for employment data.
- **Approach**: Demonstrates cross-referencing between POJOs (references `Address`).

### [Coordinates.java](./src/main/java/api/complexPojo/Coordinates.java)
- **Purpose**: Utility POJO for latitude/longitude.
- **Approach**: Uses `double` types for precise coordinate handling.

### [Crypto.java](./src/main/java/api/complexPojo/Crypto.java)
- **Purpose**: Nested POJO for cryptocurrency details.

### [Hair.java](./src/main/java/api/complexPojo/Hair.java)
- **Purpose**: Nested POJO for physical characteristics.

---

## Package: `api.rest`

### [RestResource.java](./src/main/java/api/rest/RestResource.java)

**File Purpose**: 
A **Reusable Utility Class** that centralizes Rest Assured `given-when-then` logic.

**Line-by-Line Breakdown**:
- `14-17`: `get(path)` - Basic GET request with spec validation.
- `20-24`: `get(path, id)` - **Method Overloading** for path parameters.
- `26-32`: `post(path, payload)` - POST request with object payload.
- `35-40`: `put(...)` - PUT request with path parameters and payload.
- `47-55`: `UploadFile(...)` - `multiPart` request for binary data.
- `56-64`: `encodedTest(...)` - POST using `formParam` for URL-encoded data.

**Technical Justification**:
- **Centralized Specs**: Uses `SpecBuilder` to ensure every request follows the same standards.
- **Flexible Payloads**: Accepts `Object` to support various data types (Map, POJO, String).

---

## Package: `api.routes`

### [Routes.java](./src/main/java/api/routes/Routes.java)
- **Purpose**: Centralized storage for API endpoints.
- **Justification**: Prevents hardcoding and simplifies path updates.

---

## Package: `api.spec`

### [SpecBuilder.java](./src/main/java/api/spec/SpecBuilder.java)

**File Purpose**: 
Constructs reusable **Request** and **Response Specifications**.

**Line-by-Line Breakdown**:
- `18-22`: Builds request spec with Base URI, JSON content type, and full logging.
- `26-30`: Builds response spec with JSON content type expectation and response time validation.

**Technical Justification**:
- **Code Reuse**: Reduces boilerplate code in every test method.
- **Standardization**: Ensures consistent headers and logging across the project.

---

## Package: `constants`

### [StatusCode.java](./src/main/java/constants/StatusCode.java)
- **Purpose**: Enum for HTTP Status Codes.
- **Justification**: Enhances readability (e.g., `CODE_201` vs `201`).

---

## Package: `filters`

### [CustomFilter.java](./src/main/java/filters/CustomFilter.java)
- **Purpose**: Request/Response logging interceptor.
- **Justification**: Separates logging logic from the core framework implementation.

---

## Package: `payloads`

### [ComplextPayload.java](./src/main/java/payloads/ComplextPayload.java)
- **Purpose**: Factory for creating complex, deeply nested `RootUser` objects.
- **Justification**: Centralizes the creation of large test data sets.

### [UserPayload.java](./src/main/java/payloads/UserPayload.java)
- **Purpose**: Utility for generating simple user data in different formats (Map, JSON, POJO).
- **Justification**: Demonstrates multiple ways to interact with Rest Assured's body method.

### [UserPOJO.java](./src/main/java/payloads/UserPOJO.java)
- **Purpose**: Simplified POJO for basic user tests.

---

## Package: `utils`

### [ConfigLoader.java](./src/main/java/utils/ConfigLoader.java)
- **Purpose**: Loads configuration from `config.properties`.
- **Justification**: Facilitates environment-driven testing (Dev/Staging/Prod).

### [FakerUtils.java](./src/main/java/utils/FakerUtils.java)
- **Purpose**: Generates random test data using the Java Faker library.
- **Justification**: Ensures test independence and prevents hardcoded data collisions.

---

## Package: `base`

### [BaseTest.java](./src/test/java/base/BaseTest.java)
- **Purpose**: Common setup for all test classes.
- **Approach**: Uses `@BeforeClass` to enable global logging configurations.

---

## Package: `tests`

### [UserTests.java](./src/test/java/tests/UserTests.java)

**File Purpose**: 
The main test execution class for User-related API scenarios.

**Line-by-Line Breakdown**:
- `19-23`: `getUsersTest()` - Validates fetching all users.
- `25-29`: `createUserWithHashMap()` - Tests POST using a Map.
- `31-35`: `createUserWithJson()` - Tests POST using a JSON String.
- `38-41`: `createUserWithPOJO()` - Tests POST using a Java Object.
- `43-46`: `schemaValidationTest()` - Uses `JsonSchemaValidator` to match response against a `.json` schema file.
- `60-65`: `fileUploadTest()` - Validates file upload functionality.
- `73-81`: `complexPojoTest()` - Demonstrates full Serialization/Deserialization workflow with assertions.

**Technical Justification**:
- **Inheritance**: Extends `BaseTest` for centralized configuration.
- **Fluent Assertions**: Uses Rest Assured's Gherkin-style syntax (`then().statusCode()`) for readable validations.
- **Object Mapping**: Uses `.as(RootUser.class)` to deserialize responses, enabling powerful Java-based assertions.

---

## Alignment with Practical Examination Requirements

This project is meticulously structured to fulfill all criteria of the **Rest Assured Practical Examination**. Below is a mapping of the examination tasks to the framework's implementation.

### Task 1: Request and Response Setup
- **Implementation**: Handled in [SpecBuilder.java](./src/main/java/api/spec/SpecBuilder.java).
- **Details**: It defines `getRequestSpec()` with Base URI, Content-Type (JSON), and `log(LogDetail.ALL)`. It defines `getResponseSpec()` with response time validation (`lessThan(300000L)`) and status code checks are handled in the test layer using [StatusCode.java](./src/main/java/constants/StatusCode.java).

### Task 2: Routes and Configuration Management
- **Implementation**: Handled in [Routes.java](./src/main/java/api/routes/Routes.java) and [ConfigLoader.java](./src/main/java/utils/ConfigLoader.java).
- **Details**: `Routes` centralizes all endpoint paths. `ConfigLoader` dynamically loads the `base.url` and other environment variables from `src/main/resources/config.properties`.

### Task 3: CRUD Operations
- **Implementation**: Located in [UserTests.java](./src/test/java/tests/UserTests.java).
- **Details**: 
    - **Read**: `getUsersTest()` and `schemaValidationTest()`.
    - **Create**: `createUserWithPOJO()`.
    - **Update**: `updateUserTest()`.
    - **Delete**: `deleteUserTest()`.

### Task 4: Sending Request Payloads in Multiple Ways
- **Implementation**: Demonstrated across [UserTests.java](./src/test/java/tests/UserTests.java) using methods from [UserPayload.java](./src/main/java/payloads/UserPayload.java).
- **Details**:
    - **HashMap**: `createUserWithHashMap()` using `createUserMap()`.
    - **Raw JSON**: `createUserWithJson()` using `createUserJson()`.
    - **Java POJO**: `createUserWithPOJO()` using `createUserPOJO()`.

### Task 5: Complex POJO and Serialization
- **Implementation**: [api.complexPojo](./src/main/java/api/complexPojo/) package and `complexPojoTest()` in [UserTests.java](./src/test/java/tests/UserTests.java).
- **Details**: Uses `RootUser` as a parent POJO containing nested objects like `Address`, `Bank`, and `Company`. Serialization is handled by Jackson during the `.body()` call, and deserialization is achieved via `.as(RootUser.class)`.

### Task 6: JSON Schema Validation
- **Implementation**: `schemaValidationTest()` in [UserTests.java](./src/test/java/tests/UserTests.java).
- **Details**: Uses Rest Assured's `JsonSchemaValidator` to validate the response against a schema file located in the resources directory (referenced via `ConfigLoader.getSchema()`).

### Task 7: Filters and Logging
- **Implementation**: [CustomFilter.java](./src/main/java/filters/CustomFilter.java) and `SpecBuilder.java`.
- **Details**: `SpecBuilder` applies global logging, while `CustomFilter` demonstrates a custom implementation of the `Filter` interface to log specific request/response details.

### Task 8: File Upload
- **Implementation**: `upload()` in [UserApi.java](./src/main/java/api/application/UserApi.java) and `fileUploadTest()` in [UserTests.java](./src/test/java/tests/UserTests.java).
- **Details**: Uses `multiPart("file", file)` to send a binary file to `postman-echo.com/post`.

### Task 9: Form URL Encoding
- **Implementation**: `echoPost()` in [UserApi.java](./src/main/java/api/application/UserApi.java) and `formUrlEncodedTest()` in [UserTests.java](./src/test/java/tests/UserTests.java).
- **Details**: Uses `formParam()` and `application/x-www-form-urlencoded` content type (defined in `encodedTest` within `RestResource`) to verify form data submission.

