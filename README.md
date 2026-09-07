# Movie-Streaming-Management-System

Movie Streaming Management System (Netflix Clone)
A console-based Java application designed to simulate a movie streaming platform's core functionalities. This project is built using the Model-View-Controller (MVC) architecture and relies entirely on custom-built data structures and file-based data storage.  

Key Features
    Authentication & Authorization
    Role-based access control supporting Admin and User accounts.  
    User registration and secure login handling.  

Admin Control Panel
    Category Management: Full CRUD (Create, Read, Update, Delete) operations with automatic ID generation and constraint checks (prevents deleting categories currently in use).  
    Movie Management: Full CRUD operations for movies, complete with input validation and category linking.  
    Viewing Reports: Export detailed system-wide or user-specific viewing reports detailing completion rates, total watch time, and most-watched categories.  

User Dashboard
    Search & Sort: Search movies by title, director, or actor, and sort by rating, release year, title, or popularity (views).  
    Smart Watchlist (Playlist): Add movies to a personal queue. Features a "Watch Next" function (Dequeue) and a unique "Undo Last Watch" function (Stack Pop) to restore accidental removals.  
    Community Favorites: Upvote movies and browse the platform's most liked content.  
    Viewing History & Stats: Resume unfinished movies, view recently watched history (Top 3), and access a personal viewing statistics dashboard.  
    Top 10 Ranking: An automated leaderboard that scores movies based on a weighted formula: Ratings (40%), Views (40%), and Favorites (20%).  

Architecture & Custom Data Structures
The system strictly follows the MVC architecture (Models, Views, Controllers) to separate business logic from the user interface and data access layers.  
To fulfill rigorous academic requirements, built-in Java collections were avoided. The system relies on manually implemented data structures to solve specific operational problems:
    MyLinkedList: Used globally for managing dynamic lists of movies, categories, accounts, and history logs.  
    MyQueue: Implemented for the User Watchlist to process movies in a First-In-First-Out (FIFO) sequence.  
    MyStack: Implemented to provide a Last-In-First-Out (LIFO) "Undo" mechanism for the Watchlist.  

📂 Project StructurePlaintextsrc/
```text
src/
├── app/
│   └── Main.java                 # Application entry point
├── controllers/                  # Business logic and data flow
│   ├── AuthController.java
│   ├── CategoryController.java
│   ├── HistoryController.java
│   ├── MovieController.java
│   └── WatchlistController.java
├── data/
│   └── FileHandler.java          # I/O operations for flat-file database
├── models/
│   ├── datastructures/           # Custom data structures (LinkedList, Queue, Stack)
│   └── entities/                 # Object models (Account, Category, Movie, WatchRecord)
├── utils/
│   └── ValidationUtil.java       # Robust input validation and error handling
└── views/
    └── MainView.java             # Console-based UI and menu navigation
```

Data Storage
All system data is persistently stored in flat .txt files located in the data/ directory, acting as a lightweight database.  
    accounts.txt  
    categories.txt  
    movies.txt  
    history.txt  
    watchlist.txt  
    favorites.txt  
    viewing_report.txt (Generated via Admin Panel)  

Installation & Usage
Compile the Project:
Ensure you have Java JDK (1.8 or higher) installed. Compile all .java files within the src directory.

Run the Application:
Execute the Main.java class located in the app package.  

Default Admin Account:
Upon first launch, the system automatically generates a default administrator account.  
    Username: admin  
    Password: admin123
