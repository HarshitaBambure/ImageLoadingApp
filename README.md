**MVVM App with Api Data Loading**

This app utilizes the MVVM (Model-View-ViewModel) architecture pattern to fetch data from an API and display it in a RecyclerView list. The separation of concerns in MVVM allows for better organization and maintenance of code.

Features:

API Integration: The app connects to a specified API endpoint to fetch data.
MVVM Architecture: Utilizes the MVVM pattern for clean and modular code structure.
Data Binding: Implements data binding to efficiently bind data between the View and ViewModel.
RecyclerView: Displays the fetched data in a RecyclerView list for easy navigation and scrolling.
Asynchronous Loading: Uses asynchronous tasks to fetch data from the API without blocking the main UI thread.
Error Handling: Implements error handling mechanisms to gracefully handle network errors and API failures.

Setup Instructions:

Clone the repository to your local machine.
Open the project in Android Studio.
Make sure you have an internet connection for API data fetching.
Run the app on an emulator or physical device.

Usage Instructions:

Upon launching the app, it will automatically fetch data from the API.
The fetched data will be displayed in a RecyclerView list.
Scroll through the list to view all the items.
If there are any errors in fetching the data, appropriate error messages will be displayed.

Dependencies:

Retrofit: For making API requests.
Gson: For JSON parsing.
LiveData: For observing data changes in the ViewModel.
RecyclerView: For displaying the list of items.

Directory Structure:

/data: Contains classes related to data handling, such as API service, repository, and data models.
/ui: Contains classes related to the user interface, including activities and adapters.
/viewmodel: Contains ViewModel classes responsible for managing UI-related data.
/utils: Contains Image Loader classes responsible for Image Loading. 
