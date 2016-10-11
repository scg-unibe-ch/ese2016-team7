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


| Name        | Actor           | Description  |
| ------------- |:-------------:| ------|
| Sign up      | guest     |  Create an account.  |
| View schedule | user      | The accepted enquiries for visiting flats are shown. |
| View alerts | user      |     |
| Create alert | user      | Create an alert by specifying the criteria for matching new ads. If a new ad matches the search criteria, an alert will be send to the user. 
| Delete alert | user      |     |
| Log in      | guest |  |
| Edit profile | user      |     |
| Logout | user      |     |
| Search ads | user      |     |
| Filter search results | user      |     |
| Sort search results | user      |     |
| View ad | user      |     |
| Send message | user      |     |
| Send enquiry | user      |     |
| View ad location | user      |     |
| Create ad | manager      |  |
| Accept enquiry | manager      | A manager accepts an enquiry that a user sent to him.|
| Decline enquiry | manager      |  A manager declines an enquiry that a user sent to him.  |
| View enquiries | manager      |     |
| View visitors | manager      |   Shows when the users visit the managers flat. |




**Actor characteristics**
The intended users of the product are people who want to rent a room or studio and people who want to advertise their room/studio to find a person willing to rent it.
We expect the users who rent rooms/studios to be primarily students on college level, maybe some adult singles who want to rent a studio. Both students or singles wanting to rent a studio or room tend to be rather young, e.g. between 18 – 35, because older people tend to not be single anymore and want to have more to live in than just a room or studio (and can afford so). As they want to rent a room/studio in Switzerland, we assume most of them lived in Switzerland for some time. Thus these users are young adult people who have lived in Switzerland for some time, so they do have quite some experience with navigating through web pages and using browsers.
The users who did not live in Switzerland for some time probably are mostly exchange students who probably also have the same level of experience since they are likely come from a Europian country via an exchange program or come from a rather wealthy family, seeing that an exchange semester in Switzerland is relatively expensive.
The users who advertise a room/studio are thought to be adult people living in Switzerland too, since they want to rent their room/studio in Switzerland. We assume they tend to be older compared to the first group of users, as they own one or multiple rooms or studios they want to rent. These group includes people who own a lot of rooms/studios, who tend to be rather wealthy. Therefore, we expect a big part of these users to be between 30 – 65 year old people who have lived in Switzerland for some time. Generally, we expect them to have some experience with navigating through web pages and using web browsers as most Swiss people do. However, for the older part of these users this might not apply. Some older people might have little experience with web pages and browsers. Yet it is unlikely that a user has no experience because the user had to somehow find the web page FlatFindr.
However, there also might be some students in this category, who want to rent a room of their ‘WG’ or who want to find a successor to their room/studio. These users would share the characteristics of the ones in the first paragraph.
We might conclude that, as most users atelre quite familiar with web pages and browsers, although they are no experts, it would be best for the website to be designed in a way that feels consistent with other typical websites, so that navigating through and using the product would feel intuitive. The UI should probably look fresh and appealing to the young portion of the users as well as professional enough to encourage people who own rooms/studios to advertise them here.


<h2>3. Specific requirements</h2>
**Functional requirements**
**Non-functional requirements (external, performance, etc.)**




