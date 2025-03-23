# CoreShield Project

This project processes JSON files and provides various analytical services based on the data. The main functionality of the project is to provide analytics, such as calculating the average rating, finding the location with the highest reviews, and more.

## Features

1. **Count Valid Points by Type**  
   Calculate the number of valid points grouped by type.

2. **Calculate Average Rating by Type**  
   Calculate the average rating of locations, grouped by their type.

3. **Find Location with Highest Reviews**  
   Identify the location with the highest number of reviews.

4. **Calculate Location with Incomplete Data**  
   Find locations with incomplete data fields.

## Prerequisites

To run this project, you need to have the following installed:

- **Java 21** or higher.
- **Maven**.

## Installation

### 1. Clone the repository:
```bash
git clone https://github.com/akhilr007/coreshield.git
cd coreshield
```

### 2. Install dependencies and build the project:
Run the following Maven command to install the dependencies and compile the project:
```bash
mvn clean install
```
### 3. Running the Application
To run the project, use the following Maven command:
```bash
mvn exec:java
```

### Usage
Once the program starts, it will prompt you with the following menu:
```plaintext
Processing JSON files...
XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
Get Analytics Data...

Enter a command number:
1. countValidPointsExistPerType
2. calculateAverageRatingPerType
3. calculateLocationWithHighestReviews
4. calculateLocationWithIncompleteData
```
- Enter 1, 2, 3, or 4 to perform the corresponding action.

- After each operation, you will be asked if you want to perform more analytics. Type y to continue or n to exit the program.

Example Interaction:
```plaintext
Enter a command number:
1. countValidPointsExistPerType
2. calculateAverageRatingPerType
3. calculateLocationWithHighestReviews
4. calculateLocationWithIncompleteData
1
TYPE : COUNT
cafe : 2
restaurant : 3
hotel : 3
Do you want to perform more analytics? (y/n)
y

Enter a command number:
1. countValidPointsExistPerType
2. calculateAverageRatingPerType
3. calculateLocationWithHighestReviews
4. calculateLocationWithIncompleteData

2
TYPE : AVERAGE RATING
cafe : 4.60
restaurant : 4.10
hotel : 3.40
Do you want to perform more analytics? (y/n)
n
Exiting the program...

Process finished with exit code 0
```
