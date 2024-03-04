# Github User Repo fetcher *Assignment*
### An Android app which communicates with the public Github API in	order to display information about a specific user.

##	Design Requirements
I followed the design requirements from the video as best as I could, as well as the animation.
**NOTE** the app in the video uses Material2, an older version of Google's official design system, the current version is Material3, I took the liberty to use Material3 with MaterialYou theme for the app colours, this means the UI theme will change according to the user's phone theme (to test the feature, try changing phone backgrond)

##	Architectural Patterns
For this project I used MVVM with Clean-Architecture.
I like using clean architecture when starting a new project for various reasons:
Advantages of Using Clean Architecture
- Code is even more easily testable than with plain MVVM.
- The package structure is even easier to navigate.
- Features can be implemented even more quickly.
- The project is easier to maintain.
- But the main advantage is that the code is further decoupled and layered into:
    - Data layer, this part is completely separated from the rest of the app, in fact it could even go into a separate module or flavour.
    - Domain layer, contains all the interfaces used by the presentation layer, which are implemented in the data layer. The 2 are "glued" together using Dependency Injection.
    - Presentation layer, the UI of the app. This layer only interacts with the Domain and never with the data layer.

**Models**
Models are separated into data transfer objects (dto), the raw data received from our API calls, and app domain models, the models usable by the app. The conversion is done using a `.toModel()` kotlin extension (ie. see `fun UserDto.toGithubUser()` inside UserDto data class).

The clean separation of the data layer (including the separation of DTO and domain-models) allows us to plug any backend to the app, we could very easily re-implemente the data layer to work with GitLab for example.

I did not implement use-cases, seemed a bit overkill for this type of app, but I can easily implement it live in the follow up interview.  I'm collecting results from flows directly in the view model, like regular non clean-architecture MVVM.

##	Functional	Requirements
All the requested tasks have been completed.
- [x] The app should accept	a github user's id as input and display the specified user's avatar and name.
- [x] For each public repository owned by the user, the name and description	are	shown in a scrollable list.
- [x] When	a	repository	is	selected, user should go to a detail screen	which should display the details regarding that specific repo.
- [x] Display the total number of forks across all the user's repos on detail screen. If the total number of forks exceed count 5000 then we want to have a star badge (which can be annotated by simple red/gold color on the text).
- [x] completion	of	tasks
- [x] architecture
- [x] functional/design	requirements
- [x] testablilty
- [x] coding	standard	&	best	practices
- [x] Nice	to	have	documentation	