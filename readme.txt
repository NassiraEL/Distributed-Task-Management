Here is the professional README in English, structured to highlight the technical sophistication of your project to your professor.

🚀 Distributed Task Execution System (Version 2)
High-Performance Parallel & Multi-User Architecture
1️⃣ Project Overview
This project is a distributed computing platform designed to handle heavy computational tasks (like image filtering or matrix operations) by splitting them across multiple worker nodes (Slaves).

Key Enhancements in V2:

Multi-User Support: Handles multiple client requests concurrently using dedicated threads.

Distributed Parallelism: Tasks are split and distributed across all available Slaves simultaneously.

Advanced Design Patterns: Implemented to ensure code scalability and maintainability.

2️⃣ Technical Architecture & Design Patterns
The system is built on four core design principles:

Singleton Pattern (Master): Ensures a single point of orchestration, managing the pool of connected Slaves and routing client requests.

Strategy Pattern (Slave Execution): Allows the Slave to switch processing logic (e.g., GrayFilterStrategy vs MatrixProductStrategy) dynamically based on the user's task options without changing the core code.

Factory Pattern (StrategyFactory): Centralizes the creation of task strategies, making it easy to plug in new JAR processing types in the future.

Observer/Registry Pattern: The Master maintains a dynamic list of SlaveHandlers, allowing Slaves to join or leave the network at any time.

3️⃣ Project Structure
Plaintext
src/
├── shared/             # Common TaskMessage DTO for network communication
├── strategy/           # Strategy interface and concrete processing implementations
├── master/             # Server logic (Master, ClientHandler, SlaveHandler, Executor)
├── slave/              # Worker logic (Slave, JarExecutor)
├── user/               # Client-side application
└── test_jars/          # Pre-compiled test files
    ├── SplitProcessMerge.jar (Master-side splitter/merger)
    ├── filter.jar            (Slave-side processing unit)
    └── image.jpg             (Sample input)
4️⃣ Setup & Compilation
Navigate to the src/ directory and compile all packages using the Java compiler:

Bash
cd src
javac shared/*.java strategy/*.java master/*.java slave/*.java user/*.java
5️⃣ Execution Guide (The "Stress Test" Scenario)
To demonstrate the full power of the system (1 Master, 3 Slaves, 2 Concurrent Users), open 6 terminal windows:

Step A: Start the Infrastructure
Terminal 1 (Master): Initialize the central server.

Bash
java master.Master 5000 6000
Terminals 2, 3, & 4 (The Slaves): Connect the three workers to the Master.

Bash
java slave.Slave localhost 6000
Step B: Simultaneous Client Requests
Open Terminals 5 & 6 and run the User Client in both to simulate high traffic:

Bash
java user.UserClient localhost 5000
Input Data for Testing:

Master Jar path: test_jars/SplitProcessMerge.jar
Master Option: 2
Slave Jar path: test_jars/filter.jar
Slave Option: gray
File path: test_jars/picpic.jpg


6️⃣ Why This System is Robust
Thread Isolation: Every user is handled in a separate ClientHandler thread. Each task creates a unique timestamped directory (masterTask_1700...) to prevent data collisions.

Load Balancing: The Master uses a Round-Robin algorithm (i % slaves.size()) to ensure tasks are distributed evenly across the Slave pool.

Fault Tolerance: The SlaveHandler checks for connection health before sending tasks, ensuring that if a Slave disconnects, the system remains stable.

Synchronization: Uses Future and Callable to synchronize parallel results, ensuring the Master only begins the Merge phase once all Slaves have successfully returned their results.