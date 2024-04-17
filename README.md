**MVVM App with Api Data Loading**

This app utilizes the MVVM (Model-View-ViewModel) architecture pattern to fetch data from an API and display it in a RecyclerView list. The separation of concerns in MVVM allows for better organization and maintenance of code.

**Features:**

•	API Integration: The app connects to a specified API endpoint to fetch data.

•	MVVM Architecture: Utilizes the MVVM pattern for clean and modular code structure.

•	Data Binding: Implements data binding to efficiently bind data between the View and ViewModel.

•	RecyclerView: Displays the fetched data in a RecyclerView list for easy navigation and scrolling.

•	Asynchronous Loading: Uses asynchronous tasks to fetch data from the API without blocking the main UI thread.

•	Error Handling: Implements error handling mechanisms to gracefully handle network errors and API failures.

**Setup Instructions:**

•	Clone the repository to your local machine.

•	Open the project in Android Studio.

•	Make sure you have an internet connection for API data fetching.

•	Run the app on an emulator or physical device.

**Usage Instructions:**

•	Upon launching the app, it will automatically fetch data from the API.

•	The fetched data will be displayed in a RecyclerView list.

•	Scroll through the list to view all the items.

•	If there are any errors in fetching the data, appropriate error messages will be displayed.

**Dependencies:**

•	Retrofit: For making API requests.

•	Gson: For JSON parsing.

•	LiveData: For observing data changes in the ViewModel.

•	RecyclerView: For displaying the list of items.

**Directory Structure:**

•	/data: Contains classes related to data handling, such as API service, repository, and data models.

•	/ui: Contains classes related to the user interface, including activities and adapters.

•	/viewmodel: Contains ViewModel classes responsible for managing UI-related data.

•	/utils: Contains Image Loader classes responsible for Image Loading.

**Image Loader Class:**
This is a Kotlin implementation of an image loader that loads images from a URL and caches them in memory and on disk. The ImageLoader class has a loadImage method that takes a URL and an ImageView as parameters. It first checks if the image is already in the memory cache and displays it if it is. If not, it adds the image to a queue to be loaded in a background thread. While the image is loading, it displays a placeholder image in the ImageView.

The ImageLoader class uses a MemoryCache and a FileCache to store the images. The MemoryCache stores the images in memory using a SoftReference to allow the garbage collector to reclaim memory if needed. The FileCache stores the images on disk in the app's cache directory.

The ImageLoader class also includes a PhotosLoader class that is used to load the image in a background thread and a BitmapDisplayer class that is used to display the loaded image in the ImageView on the UI thread.

The FileCache class provides methods to get a file for a given URL and to clear the cache. The MemoryCache class provides methods to get and put images in the cache and to clear the cache.

Thank you and Happy coding :)
