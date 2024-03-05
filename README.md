# Github User Repo fetcher *Assignment*
### An Android app which communicates with the public Github API in	order to display information about a specific user.

##	Design Requirements
I followed the design requirements and replicated the animations from the video as best as I could.
**NOTE** the app demonstrated in the video uses Material2, an older version of Google's official design system, the current version is Material3. I took the liberty of using Material3 with MaterialYou theme for the app colours, this means the UI theme will change according to the user's phone theme (to test the feature, try changing phone backgrond). Other small differences include the default elevation for cards, the corder radius, the coloured top bar, and the button shape and background.

##	Architectural Patterns
For this project I used MVVM with Clean-Architecture.
I like using Clean-Architecture when starting a new project for various reasons.
Advantages of Using Clean Architecture
- The package structure is even easier to navigate.
- Features can be implemented even more quickly.
- Code is even more easily testable than with plain MVVM.
- The project is easier to maintain.
- But for me the main advantage is that the code is further decoupled and layered into:
    - Data layer, this part is completely separated from the rest of the app, in fact it could even go into a separate module or flavour.
    - Domain layer, contains all the interfaces used by the presentation layer, which are implemented in the data layer.
      The 2 are "glued" together thanks to Dependency Injection. The same will be used by unit testing and instrumentation testing together with an eventual mocked data layer.
    - Presentation layer, the UI of the app. This layer only interacts with the Domain and never with the data layer. View models will call Use-Cases to fetch the appropriate data.

##	Coding standard & best practices
The code follows SOLID principles and MVVM Clean-Architecture design-patterns rules.

**Models**
Models are separated into data transfer objects (dto), the raw data received from our API calls, and app domain models, the models usable by the app. The conversion is done using a `.toModel()` kotlin extension (ie. see `fun UserDto.toGithubUser()` inside UserDto data class).
<br>
The clean separation of the data layer (including the separation of DTO and domain-models) allows us to plug any backend to the app, we could very easily re-implement the data layer to work with GitLab for example.
<br>
I used Use-Cases to follow the design pattern by the book. They are a bit overkill for such a small app, we can very easily remove this layer and collect results from flows directly in the view model, like regular non clean-architecture MVVM.

## Testing
I tried to cover the 3 Major types of testing
- unit testing
	- tested business logic of 2 of the 3 use cases and a repository
- integration (instrumented) testing
	- tested the main page UI and interaction
- end to end testing
	- tested full user behaviour. Set of actions that would lead to expected results. ie. fetch, navigate, verify data validity.

##	Functional	Requirements
All the requested tasks have been completed.
- [x] The app should accept	a github user's id as input and display the specified user's avatar and name.
- [x] For each public repository owned by the user, the name and description are shown in a scrollable list.
- [x] When a repository is selected, user should go to a detail screen which should display the details regarding that specific repo.
- [x] Display the total number of forks across all the user's repos on detail screen. If the total number of forks exceed count 5000 then we want to have a star badge (which can be annotated by simple red/gold color on the text).
- [x] completion of tasks
- [x] architecture
- [x] functional/design	requirements
- [x] testablilty
- [x] coding standard & best practices
- [x] Nice to have documentation

# DEMO
https://github.com/icefields/Github-Fetch-Assignment/assets/149625124/867e42a2-7bb4-4a20-ab8b-a857faa98e81

# DEMO Dark, with state retention over configuration changes
https://github.com/icefields/Github-Fetch-Assignment/assets/149625124/22aef4b9-4998-4022-bc98-40872558c802

