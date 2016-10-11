<h1>Software requirements specification</h1>

<h2>1. Introduction</h2>
**Purpose**
The purpose of this SRS is to describe the FlatFindr online platform and specify the reuqirements of the project. For now, the SRS only describes the system-as-is and is thus only of interest to the developer team. Once the requirements of the customer are added, this document serves as a kind of contract between the customer and the developers and acts as a common ground for the stakeholders, where either one of them can check whether their understanding and vision of the product to be built is the same as the one of the other party.
To fulfill its purpose, the SRS is always to be kept up-to-date by the developer team.

**Stakeholders**
The customer defines the requirements.
The users uses the product.
The developer team “ESE 2016 Team 7” collect the requirement and build the product.

**Scope**
The software product produced for the customer is an online platform named FlatFindr.
FlatFindr allows users to advertise rooms and studios to be rent. On the other hand, users also can find rooms and studios advertised by other users and contact the advertising user when they are interested in renting the room/studio. FlatFindr includes other functionalities, which support the basic purpose of the system to advertise/rent rooms and studios, which are described in the requirements section.
FlatFindr does not, however, run online. It can only be run locally.

**Definitions**
SRS: Software Requirements Specification, i.e. this document.
Product: The FlatFindr online platform.

**System Overview**
The SRS contains descriptions of the product in general terms and from user perspective in chapter 2. It is described what the system has to be able to do for the user, how the user can interact with the system. The customer can read this to determine whether all the interactions he wants to be possible between the user and the system are appropriately included in this document.
A list of the requirements, functional and non-functional alike, can be found in chapter 3. There the functionalities the system needs in order to fulfill the descripitons in chapter 2 are written down. Developers can read this to determine how to best design the system and what they have to implement.

**References**
System_as_is.md: Found on Github on ese2016-team7 under documentation.

<h2>2. Overall description</h2>
**Product Perspective**
The FlatFindr platform is a self-contained, independent product.
It can be compared to other online platforms which allow the user to search for rooms to rent or advertise rooms to be rent, like the platform the SUB mantains. However, FlatFindr has some unique features that no other comparable platform we know of has, like the enquiry system.

**Use cases**

User: A Person signed in to the website
Guest: A Person not signed in to the website
Manager: User that wants to advertise a flat

Use cases with no explanation are seen to be self explanatory and a description would be redundant.


| Name        | Actor           | Description  | Precondition | Normal Flow |
| ------------- |:-------------:| ------| ------------| ------------|
| Sign up      | guest     |  Create an account.  | 1) the user visited the home page of flatfindr 2) The user has no active account | button login -> "sign up" button -> user fills the form |
| View schedule | user      | The accepted enquiries for visiting flats are shown. | User has already logged in | makes waterfall menu drop from his profile picture -> clicks on "Schedule" -> all planned visits and their details are accordingly displayed |
| View alerts | user      |     | The user had a succesful log in | User drops waterfall menu from his profile picture -> clicks on "Alerts" -> list of alerts is displayed |
| Create alert | user      | Create an alert by specifying the criteria for matching new ads. If a new ad matches the search criteria, an alert will be send to the user | User is logged in | 
| Delete alert | user      |     | User is already logged in | User (real estate manager) drops waterfall menu from his profile picture -> clicks on "Schedule"  -> user clicks on "Visit" -> Ad opens -> user clicks "Delete" button |
| Log in      | guest |  | User has to own an active account | user clicks the "login" button on the homepage -> login form is displayed -> user enters email and password -> user clicks login -> home page is displayed |
| Edit profile | user      |     | user has to be logged in | user drops waterfall menu from his profile picture ->user clicks on "Public profile" -> profile info are displayed -> user click "edit" button -> user changes what he needs to change -> user clicks on "update" -> success feedback from the application |
| Logout | user      |     | user has to be logged in | user drops waterfall menu from profile pic -> user clicks the button "Logout" -> user gets logged out and home page is displayed |
| Search ads | user      |     | user (which doesn't necessarily have to own an account) is on the home page | user clicks button "search" -> search form is displayed -> users fills form according to his needs -> user clicks on "search" -> the application shows a list of apartments that fullfil the user's needs |
| Filter search results | user      |     | user has already performed a search | user enters his criteria in the form -> users clicks on the button "Filter" -> filtered results are displayed |
| Sort search results | user      |     | User is on a page the shows search results | user choses criteria -> users clicks on button "Sort" -> results are accordingly displayed |
| View ad | user      |     | user is on a page that already shows a specific ad | user clicks the button "Visit profile" on the section about the user that published the ad -> application shows advertiser's infos |
| Send message | user      |     | user is already logged in | user drops waterfall menu from his profile picture -> user clicks on the button "Messages" -> user's messages are displayed -> user clicks on the button "New" -> user specifies the receiver, message object and text -> user clicks on the button "send" |
| Send enquiry | user      |     | user is already logged in | user clicks on one Ad -> possible viiting times are displayed -> user clicks on "Send enquiry to advertiser" |
| View ad location | user      |     | The user is already viewing an Ad | user clicks on the Adress on the Ad ->  the browser direcly opens GoogleMaps pointing the exact location of the object described on the Ad |
| Create ad | manager      |  | The user is already logged in | user drops waterfall menu from his profile picture -> user clicks on the button "Place an ad" -> The form to place an Ad is displayed -> User fills the form accordingly to his object -> users specifies Roommates -> users specifies his preferences -> user inserts pics and visiting times -> users clicks on the button "Submit"
| Accept enquiry | manager      | A manager accepts an enquiry that a user sent to him.| the user (advertiser) is already logged in | the advertiser drops the waterfall menu from his profile pic -> clicks on the button "Enquiries" -> enquiries' list is shown -> the advertiser clicks on the button "Accept" |
| Decline enquiry | manager      |  A manager declines an enquiry that a user sent to him.  | the user (advertiser) is already logged in | the advertiser drops the waterfall menu from his profile pic -> clicks on the button "Enquiries" -> enquiries' list is shown -> the advertiser clicks on the button "Decline" |
| View enquiries | manager      |     | the user (advertiser) is already logged in | the advertiser drops the waterfall menu from his profile pic -> clicks on the button "Enquiries" -> enquiries' list is shown
| View visitors | manager      |   Shows when the users visit the managers flat. | The user (real estate manager) is already logged in an already created an ad | user drops waterfall menu from his profile picture -> clicks on the button "Schedule" -> a list containing all planned visits appears -> the user clicks on the button "see visitors" for a specific enquiry -> a list containing all visitors and their arrival time is displayed, each visitor has also a rating |




**Actor characteristics**
The intended users of the product are people who want to rent a room or studio and people who want to advertise their room/studio to find a person willing to rent it.
We expect the users who rent rooms/studios to be primarily students on college level, maybe some adult singles who want to rent a studio. Both students or singles wanting to rent a studio or room tend to be rather young, e.g. between 18 – 35, because older people tend to not be single anymore and want to have more to live in than just a room or studio (and can afford so). As they want to rent a room/studio in Switzerland, we assume most of them lived in Switzerland for some time. Thus these users are young adult people who have lived in Switzerland for some time, so they do have quite some experience with navigating through web pages and using browsers.
The users who did not live in Switzerland for some time probably are mostly exchange students who probably also have the same level of experience since they are likely come from a Europian country via an exchange program or come from a rather wealthy family, seeing that an exchange semester in Switzerland is relatively expensive.
The users who advertise a room/studio are thought to be adult people living in Switzerland too, since they want to rent their room/studio in Switzerland. We assume they tend to be older compared to the first group of users, as they own one or multiple rooms or studios they want to rent. These group includes people who own a lot of rooms/studios, who tend to be rather wealthy. Therefore, we expect a big part of these users to be between 30 – 65 year old people who have lived in Switzerland for some time. Generally, we expect them to have some experience with navigating through web pages and using web browsers as most Swiss people do. However, for the older part of these users this might not apply. Some older people might have little experience with web pages and browsers. Yet it is unlikely that a user has no experience because the user had to somehow find the web page FlatFindr.
However, there also might be some students in this category, who want to rent a room of their ‘WG’ or who want to find a successor to their room/studio. These users would share the characteristics of the ones in the first paragraph.
We might conclude that, as most users atelre quite familiar with web pages and browsers, although they are no experts, it would be best for the website to be designed in a way that feels consistent with other typical websites, so that navigating through and using the product would feel intuitive. The UI should probably look fresh and appealing to the young portion of the users as well as professional enough to encourage people who own rooms/studios to advertise them here.


<h2>3. Specific requirements</h2>
**Functional requirements**

***User***

Placing an ad

A User must specify the general infos of the flat ad:
He must add a title, the address (containing a Street and a City).
Further he has to add the date of the move-in and (optional) can add a move-out-date.
He adds the prize, the type of the flat (where he can choose between Room and Studio) and the area of the flat in square meters.
For the flat he must add a description in the room and can tick the following options:
Animals allowed, garden, Cellar or Attic, Cable TV, WiFi, Smoking inside allowed, balcony or patio, furnished, garage
If the roommates have Flatfinder accounts, then the user can add them by mail and can add a description of them. This is optional.
Also optional is a field for the preferences
He can add pictures (optional) by uploading them. If he has uploaded pictures, he sees the name, the size and a delete option in a table.
The user can add visiting times by choosing them in a calendar and setting the time.

My rooms

In an overview the user sees all the ads he has posted and all the ads he has bookmarked.

Messages
In the messages he can choose between the following three options:
Inbox: In a table, messages received are shown in a table, ordered by subject, sender, recipient and date sent. If a message is unread, its blue, otherwise white. If clicked on it,  the message is shown below.
New: a window pops up in which the user must enter the mail address of the recipient (title “To:”), the subject of the message (title: “Subject”) and (optional) a message (title: “message”) if he wants to send a message. He then has a Send and a Cancel button
Sent: the mails sent by the user, displayed the same way as in the inbox

  Enquiries

The Enquiries are displayed in a table, sorted by sender, ad, date of the visit, date sent, and an Action. The user can either accept or decline an enquiry and if done so it is displayed.

Schedule

The schedule contains of two lists: The users presentations and the users visits.

In both, he sees the address, date, time of the visit/presentation and can enter the ad of the flat visited/presented and if he's the presenter he can enter a list of the users who visit the property, sorted by name, username, profile which he can enter and the rating of the visiting user.

Alerts

The user can create a new alert for either a romm or a studio (can be ticked) by entering a City/zip code,a radius and a max price. Then he can subscribe or cancel.
Further he has a table of his active alerts, sorted by type, city, radius, max Price and an option to delete the alert.

 User Profile

His picture, username, name and description are displayed with the options to message and edit profile if its the users.

Edit Profile
He can change the username, first name, last name and password and change the description. Its applied by clicking on update.

***Home***

The user sees the newest ads. An ad contains the picture, title, prize, type, address and move-in-date. He has the profile options and the search.

***Search***

He can tick the type and must enter a city code, radius and a price and can then search or cancel.

Display search

He sees the result and can sort them by the sortoptions and has a box in which he can filter by a title, the address (containing a Street and a City),the date of the move-in and (optional) can add a move-out-date, the prize, the type of the flat (where he can choose between Room and Studio) and the area of the flat in square meters.

Display Ad
The users sees the title, the details (tpye, address, avaiable from, move-out-date, rent, area and ad creation date), a room description, a roommate description, filters and preferences. It has possible visiting times where a user can send an enquiry and the profile of the advertiser, which a user can enter.

**Non-functional requirements (external, performance, etc.)**

This website should work flawlessly on all browsers.
An ad/user should be unique.



