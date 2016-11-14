<h1>Software requirements specification</h1>

<h2>1. Introduction</h2>
**Purpose**
The purpose of this SRS is to describe the FlatFindr online platform and specify the reuqirements of the project. This document serves as a kind of contract between the customer and the developers and acts as a common ground for the stakeholders, where either one of them can check whether their understanding and vision of the product to be built is the same as the one of the other party.
To fulfill its purpose, the SRS is always to be kept up-to-date by the developer team.

**Stakeholders**
The customer defines the requirements.
The users uses the product.
The developer team “ESE 2016 Team 7” collect the requirements and build the product.

**Scope**
The software product produced for the customer is an online platform named FlatFindr.
FlatFindr allows users to advertise houses, apartments and studios to be bought. On the other hand, users can also find houses, apartments and studios advertised by other users and contact the advertising user when they are interested in buying the house, studio or apartment. FlatFindr includes other functionalities, which support the basic purpose of the system to sell or buy houses, apartments and studios, which are described in the requirements section.

**Definitions**
SRS: Software Requirements Specification, i.e. this document.
Product: The FlatFindr platform.

**System Overview**
The SRS contains descriptions of the product in general terms and from user perspective in chapter 2. It is described what the system has to be able to do for the user, how the user can interact with the system. The customer can read this to determine whether all the interactions he wants to be possible between the user and the system are appropriately included in this document.
A list of the requirements, functional and non-functional alike, can be found in chapter 3. There the functionalities the system needs in order to fulfill the descripitons in chapter 2 are written down. Developers can read this to determine how to best design the system and what they have to implement.

**References**
System_as_is.md: Found on Github on ese2016-team7 under documentation.

<h2>2. Overall description</h2>
**Product Perspective**
The FlatFindr platform is a self-contained, independent product.
It can be compared to other online platforms which allow the user to buy and sell real estate.
FlatFindr is based on auctions. You can buy a house by bidding on it. When the Auction ends the highest bidder wins.
There is also an instant buy price if you want to bypass the auction system and buy it right away.
FlatFindr also has some other unique features, like the enquiry system.

**Use cases**

User: A Person signed in to the website
Guest: A Person not signed in to the website
Manager: User that wants to advertise a flat

Use cases with no explanation are seen to be self explanatory and a description would be redundant.


| Name        | Actor           | Description  | Precondition | Normal Flow |
| ------------- |:-------------:| ------| ------------| ------------|
| Sign up      | guest     |  Create an account.  | the user visited the home page of flatfindr and The user has no active account | button login -> "sign up" button -> user fills the form |
| View schedule | user      | The accepted enquiries for visiting flats are shown. | User has already logged in | makes waterfall menu drop from his profile picture -> clicks on "Schedule" -> all planned visits and their details are accordingly displayed |
| View alerts | user      |     | The user had a succesful log in | User drops waterfall menu from his profile picture -> clicks on "Alerts" -> list of alerts is displayed | 
| Create alert | user      | Create an alert by specifying the criteria for matching new ads. If a new ad matches the search criteria, an alert will be send to the user | User is logged in | user drops the waterfall menu from his profile pic -> user clicks on the button "Alerts" -> user clicks on "Subscribe" 
alernative path:
user clicks button "search" -> search form is displayed -> users fills form according to his needs -> user clicks on subscribe
|
| Delete alert | user      |     | User is already logged in | User (real estate manager) drops waterfall menu from his profile picture -> clicks on "Schedule"  -> user clicks on "Visit" -> Ad opens -> user clicks "Delete" button |
| Log in      | guest |  | User has to own an active account | user clicks the "login" button on the homepage -> login form is displayed -> user enters email and password -> user clicks login -> home page is displayed |
| Edit profile | user      |     | user has to be logged in | user drops waterfall menu from his profile picture ->user clicks on "Public profile" -> profile info are displayed -> user click "edit" button -> user changes what he needs to change -> user clicks on "update" -> success feedback from the application |
| Logout | user      |     | user has to be logged in | user drops waterfall menu from profile pic -> user clicks the button "Logout" -> user gets logged out and home page is displayed |
| Search ads | user      |     | user (which doesn't necessarily have to own an account) is on the home page | user clicks button "search" -> search form is displayed -> users fills form according to his needs -> user clicks on "search" -> the application shows a list of apartments that fullfil the user's needs |
| Sort search results | user      |     | User is on a page the shows search results | user choses criteria -> users clicks on button "Sort" -> results are accordingly displayed |
| View ad | user      |     | user is on a page that already shows a specific ad | user clicks the button "Visit profile" on the section about the user that published the ad -> application shows advertiser's infos |
| Bid | user      |     | user is already logged in and is already viewing an Ad| user enters his bid -> user clicks on the "Make Bid" button |
| Instant Buy | user      |     | user is already logged in and is already viewing an Ad| user clicks the "instant buy" button -> user clicks on the "confirm" button |
| Send message | user      |     | user is already logged in | user drops waterfall menu from his profile picture -> user clicks on the button "Messages" -> user's messages are displayed -> user clicks on the button "New" -> user specifies the receiver, message object and text -> user clicks on the button "send" |
| Send enquiry | user      |     | user is already logged in | user clicks on one Ad -> possible viiting times are displayed -> user clicks on "Send enquiry to advertiser" |
| View ad location | user      |     | The user is already viewing an Ad | user clicks on the Adress on the Ad ->  the browser direcly opens GoogleMaps pointing the exact location of the object described on the Ad |
| Create ad | manager      |  | The user is already logged in | user drops waterfall menu from his profile picture -> user clicks on the button "Place an ad" -> The form to place an Ad is displayed -> User fills the form accordingly to his object -> user inserts pics and visiting times -> users clicks on the button "Submit"
| Create premium ad |manager| Only possible if the manager has added a credit card and the maximal amount of premium ads isn't reached | The manager is already logged in| user drops waterfall menu from his profile picture -> user clicks on the button "Place an ad" -> The form to place an Ad is displayed -> User fills the form accordingly to his object  -> user inserts pics and visiting times -> users clicks on the button "Submit" |
| Accept enquiry | manager      | A manager accepts an enquiry that a user sent to him.| the user (advertiser) is already logged in | the advertiser drops the waterfall menu from his profile pic -> clicks on the button "Enquiries" -> enquiries' list is shown -> the advertiser clicks on the button "Accept" |
| Decline enquiry | manager      |  A manager declines an enquiry that a user sent to him.  | the user (advertiser) is already logged in | the advertiser drops the waterfall menu from his profile pic -> clicks on the button "Enquiries" -> enquiries' list is shown -> the advertiser clicks on the button "Decline" |
| View enquiries | manager      |     | the user (advertiser) is already logged in | the advertiser drops the waterfall menu from his profile pic -> clicks on the button "Enquiries" -> enquiries' list is shown
| View visitors | manager      |   Shows when the users visit the managers flat. | The user (real estate manager) is already logged in an already created an ad | user drops waterfall menu from his profile picture -> clicks on the button "Schedule" -> a list containing all planned visits appears -> the user clicks on the button "see visitors" for a specific enquiry -> a list containing all visitors and their arrival time is displayed, each visitor has also a rating |
| See Insights | admin user | see Insights of the page | Administrator User is logged in | user opens waterfall menu on the top right corner of the page -> selects Insights from the Menu |



**Actor characteristics**
The intended users of the product are people who want to buy houses, apartments and studios and people who want to sell their houses, apartments and studios to a person willing to buy it.

We expect the buyers to be primarily real-estate companies, individuals over 30 or married couples. The first two would then rent the Object in order to sustain a lucrative business. As they want to buy an apartment, house or studio in Switzerland, we assume they have a reasonable amount of money at their disposal and they've gathered a significant amount of expierence trading in the swiss market and therefore they their expertise of browsing the net should already be advanced. Married couples instead could be very inexperienced, thus they need of a simple, minimalistc and intuitive interface. 

We expect the sellers to be families of deceased people or individuals within any range of age, background or employment, so basically people who want to get rid of the Object or earn some money for whatever reason. 


<h2>3. Specific requirements</h2>
<h3>Functional requirements</h3>

<h4>User</h4>

<h5>Placing an ad</h5>
- A Manager must specify the general infos of the flat ad. He must add a title, the address (containing a Street and a City).
- Further he has to add the date of the move-in. (no move-out date: _done_)
- He adds the price, the type of the flat (where he can choose between Apartment, House and Studio) and the area of the flat in square meters.
- For the flat he must add a description of the property, including the amount of rooms (_done_) and can tick the following options: Animals allowed, garden, Cellar or Attic, Smoking inside allowed, balcony or patio, furnished, garage
- He can add pictures (optional) by uploading them. If he has uploaded pictures, he sees the name, the size and a delete option in a table.
- The manager can add visiting times by choosing them in a calendar and setting the time.
- An Ad can be marked as a Premium Ad where such will appear on top in the search. This is only possible if he has added a credit card, if he hasn't he is asked to do so. (_To be done_)
- Manager should be able to add ads for selling houses, apartments and studios via auctions.(_done_).
- If he wants so he can make an instant buy option for a fixed price (_To be done_)

<h5>Auctions</h5> (_Done_)
- There will be no possibility for a direct buy. If a user gets overbid, he will be messaged. (_done_)
- If a user is overbid he is messaged and told on which ad and by how much (_Done_)
- If an auction ends, the manager is messaged and told on who won and for how much, including a link to the ad. (_done_)
- If an auction ends unsuccessfully, the manager is messaged with the possibility to restart the auction with (optional) adjusted parameters.(_done_)
- When a user doesn't enter a bid high enough, he is told so by a nice looking message (_done_)
- There is an instant buy option (_Done_)

<h5>Messages</h5>
- In the messages he can choose between the following three options:
- Inbox: In a table, messages received are shown in a table, ordered by subject, sender, recipient and date sent. If a message is unread, its blue, otherwise white. If clicked on it,  the message is shown below.
- New: a window pops up in which the user must enter the mail address of the recipient (title “To:”), the subject of the message (title: “Subject”) and (optional) a message (title: “message”) if he wants to send a message. He then has a Send and a Cancel button
- Sent: the mails sent by the user, displayed the same way as in the inbox

<h5>Enquiries</h5>
- The Enquiries are displayed in a table, sorted by sender, ad, date of the visit, date sent, and an Action. The user can either accept or decline an enquiry and if done so it is displayed.

<h5>Schedule</h5>
- The schedule contains of two lists: The users presentations and the users visits.
- In both, he sees the address, date, time of the visit/presentation and can enter the ad of the flat visited/presented and if he's the presenter he can enter a list of the users who visit the property, sorted by name, username, profile which he can enter and the rating of the visiting user.

<h5>Alerts</h5>
- The user can create a new alert for either a house, apartment or studio (can be ticked) by entering a City/zip code,a radius and a max price. Then he can subscribe or cancel. If new ad(s), which would satisfy his subscribed search criteria, are added, he gets an alert/a message (to be clarified) of the new add(s) fitting his search criteria.   
- The relevant search criteria should be the price, the time and the location of the add. The others are not that relevant and either don't need to fit or only need to be 'somewhat close' to the search criteria (_To be done_).
- Further he has a table of his active alerts, sorted by type, city, radius, max price and an option to delete the alert.  
- He can easily search by just clicking a button beside the alert (_done_)

<h5>User Profile</h5>
- His picture, username, name, credit card and description are displayed with the options to message and edit profile if its the users. (_To be done_)

<h5>Edit Profile</h5>
- He can change the username, first name, last name and password and change the description. Its applied by clicking on update.
- The user can add a credit card to his profile. (_done_)

<h4>Home</h4>
- The user sees the newest ads. An ad contains the picture, title, price, type, address and move-in-date. He has the profile options and the search.

<h4>Search</h4>
- He can tick the type and must enter a city code, radius and a price and can then search or cancel.

<h4>Display search</h4>
- He sees the result and can sort them by the sort options and has a box in which he can filter by a title, the address (containing a Street and a City),the date of the move-in and (optional) can add a move-out-date, the price, the type of the flat (where he can choose between house, apartment or studio) and the area of the flat in square meters.
- A Premium Ad is marked as such. Such a premium ad only shows up when the criterias match (_Done_)
- The user has a search filter where he can adjust the filter and redo the search (_Done_)
- In the ad preview the user sees also the number of rooms and the square meters of the property.

<h4>Display Ad</h4>
- The users sees the title, the details (tpye, address, avaiable from, area and ad creation date), an object description, and filters. It has possible visiting times where a user can send an enquiry and the profile of the advertiser, which a user can enter.
- The filter should be removed and the filter criteria added to the search criteria (_Done_).
- The user should be able to have an 'advanced search' option with which he can hide and show additional search criteria (_Done_).
- The user sees a log of the bids, the remaining time and has the option to enter his bid (_Done_)
- The time left should be prominent and clear to see (_Done_)


<h4>Admin</h4>(_Done_)
- If an administrator is logged in, he can see statistics (besides the normal ads) (_Done_)
- The administrator should see how many users are subscribed, ads active, ads expired, ads in total, money spent on Flatfindr,
- how many properties of each type are advertised, what options are chosen for the properties. (_Done_)
- He sees how much money Flatfindr has earned with provisions and premium ads. (_Done_)
   

<h3>Non-functional requirements (external, performance, etc.)</h3>
- This website should work flawlessly on all browsers.
- An ad/user should be unique.
- The website should be designed elegantly and modern.
- There mustn't be more than a fixed amount of premium ads. (_To be done_)
- The Homepage should be personalized for each user (_To be done_)


