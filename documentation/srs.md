<h1>Software requirements specification</h1>

<h2>1. Introduction</h2>
**Purpose**   
The purpose of this SRS is to describe the FlatFindr online platform and specify the requirements of the project. This document serves as a kind of contract between the customer and the developers and acts as a common ground for the stakeholders, where either one of them can check whether their understanding and vision of the product to be built is the same as the one of the other party.
To fulfill its purpose, the SRS is always to be kept up-to-date by the developer team.

**Stakeholders**   
The **customer** defines the requirements.    
The **users** use the product.   
The **developer team** “ESE 2016 Team 7” collect the requirements and build the product.   

**Scope**   
The software product produced for the customer is an online platform named FlatFindr.
FlatFindr allows users to advertise houses, apartments and studios to be bought in an auction or instantly for a specified price. Users can also find houses, apartments and studios advertised by other users and contact the advertiser when they are interested in buying the house, studio or apartment. FlatFindr includes other functionalities which support the basic purpose of the product, which are described in the requirements section.

**Definitions**   
SRS: Software Requirements Specification, i.e. this document.
Product: The FlatFindr platform.

**System Overview**
The SRS contains descriptions of the product in general terms and from user perspective in chapter 2. It is described what the system has to be able to do for the user, how the user can interact with the system. The customer can read this to determine whether all the interactions he wants to be possible between the user and the system are appropriately included in this document.
A list of the requirements, functional and non-functional alike, can be found in chapter 3. There the functionalities the system needs in order to fulfill the descriptions in chapter 2 are written down. Developers can read this to determine how to best design the system and what they have to implement.

**References**   
System_as_is.md: Found on Github on ese2016-team7 under documentation.

<h2>2. Overall description</h2>
**Product Perspective**   
The FlatFindr platform is a self-contained, independent product.
It can be compared to other online platforms which allow the user to buy and sell real estate.
FlatFindr is based on auctions. You can buy real estate by bidding on it. When the Auction ends the highest bidder wins.
There is also an instant buy price if you want to bypass the auction system and buy it right away.
FlatFindr also has some other unique features, like the enquiry system.

**Use cases**   
**Guest**: A Person not signed in to the website Flatfindr.   
**User**: A Person signed in to the website Flatfindr.  
**Manager**: A User who wants to advertise a flat.   
**Admin**: A User who is administrator.   

Use cases with no explanation are seen to be self explanatory and a description would be redundant.


| Name        | Actor           | Description  | Precondition | Normal Flow |
| ------------- |:-------------:| ------| ------------| ------------|
| Sign up      | guest     |  Create an account.  | the user visited the home page of flatfindr and The user has no active account | button login -> "sign up" button -> user fills the form |
| View schedule | user      | The accepted enquiries for visiting flats are shown. | User has already logged in | makes waterfall menu drop from his profile picture -> clicks on "Schedule" -> all planned visits and their details are accordingly displayed |
| View alerts | user      |     | The user had a succesful log in | User drops waterfall menu from his profile picture -> clicks on "Alerts" -> list of alerts is displayed | 
| Create alert | user      | Create an alert by specifying the criteria for matching new ads. If a new ad matches the search criteria, an alert will be send to the user | User is logged in | user drops the waterfall menu from his profile pic -> user clicks on the button "Alerts" -> user clicks on "Subscribe"<br>alernative path: user clicks button "search" -> search form is displayed -> users fills form according to his needs -> user clicks on subscribe|
| Delete alert | user      |     | User is already logged in | User (real estate manager) drops waterfall menu from his profile picture -> clicks on "Schedule"  -> user clicks on "Visit" -> Ad opens -> user clicks "Delete" button |
| Log in      | guest |  | User has to own an active account | user clicks the "login" button on the homepage -> login form is displayed -> user enters email and password -> user clicks login -> home page is displayed |
| Google login      | guest |  | User has to own a google account | user clicks the "login" button on the homepage -> login form is displayed -> user chooses the google login-> entering the google email and password is handled by google -> home page is displayed |
| Edit profile | user      |     | user has to be logged in | user drops waterfall menu from his profile picture ->user clicks on "Public profile" -> profile info are displayed -> user click "edit" button -> user changes what he needs to change -> user clicks on "update" -> success feedback from the application |
| Logout | user      |     | user has to be logged in | user drops waterfall menu from profile pic -> user clicks the button "Logout" -> user gets logged out and home page is displayed |
| Search ads | user      |     | user (which doesn't necessarily have to own an account) is on the home page | user clicks button "search" -> search form is displayed -> users fills form according to his needs -> user clicks on "search" -> the application shows a list of apartments that fullfil the user's needs |
| Sort search results | user      |     | User is on the page that shows search results | user chooses criteria -> users clicks on button "Sort" -> results are accordingly displayed |
| Refine search | user      | Refine search parameters and search anew. | User is on the search results page | User clicks button "show search" -> search form for these results is displayed -> user adjusts search parameters in the search form -> user clicks on "search" -> search results for the refined search are displayed |
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
| See Insights | admin | see Insights of the page | Administrator User is logged in | user opens waterfall menu on the top right corner of the page -> selects Insights from the Menu |



**Actor characteristics**   
The target audience of the product are people who want to buy houses, apartments and studios and people who want to sell their houses, apartments and studios to a person willing to buy it.

We expect the buyers to be primarily real-estate companies, individuals over 30 or married couples. We assume that they have a reasonable amount of money at their disposal because they want to buy an apartment, house or studio in Switzerland. We also assume that they have gathered a significant amount of experience trading in the Swiss market and therefore their expertise of browsing the internet should already be advanced. Married couples instead could be very inexperienced, thus they need a simple, minimalistic and intuitive interface.
 
We expect the sellers to be mainly families of deceased people or individuals within any range of age, background or employment. They want to sell their real estate and earn some money. 


<h2>3. Specific requirements</h2>
<h3>Functional requirements</h3>

<h4>User</h4>
<h5>Placing an ad</h5>
- A Manager must specify the general information of the ad for the flat:
- He must add a title, the address (containing a Street and a City).
- Further he has to add the date of the move-in. 
- He adds the price, the type of the flat (He can choose between Apartment, House and Studio) and the area of the flat in square meters.
- For the flat he must add a description of the property, including the amount of rooms and can tick the following options: Animals allowed, garden, Cellar or Attic, Smoking inside allowed, balcony or patio, furnished, garage, dishwasher, washing machine.
- He can add pictures (optional) by uploading them. If he has uploaded pictures, he sees the name, the size and a delete option in a table.
- The manager can add visiting times by choosing them in a calendar and setting the time.
- An Ad can be marked as a Premium Ad that will appear more prominently. (on top in the search and first on the front page). This is only possible if he has added a credit card, if he has not he is asked to do so.
- Manager should be able to add ads for selling houses, apartments and studios via auctions.
- The manager can add an instant buy option so that other users can buy the flat for a fixed price, bypassing the auction system.

<h5>Auctions</h5>
- If a user is overbid the user gets a message telling him/her on which ad he got overbid by how much.
- If an auction ends the manager gets a message telling him/her who won and by how much.
- If an auction ends unsuccessfully the manager gets a message with the possibility to restart the auction with the same parameters.
- When a user does not enter a bid that is higher than the highest bid on the ad, he is prompted to make a higher bid.
- There is an instant buy option.
- If a user buys a property with the instant buy option, the highest bidder will be informed with a message. 
- If a user buys a property with the instant buy option, the manager will be informed with a message on who bought the property and for how much.

<h5>Messages</h5>
- In the messages the user can choose between the following three options:
- Inbox: In a table, messages received are shown in a table, ordered by subject, sender, recipient and date sent. If a message is unread, its blue, otherwise white. If the user clicks on it, the message is shown below.
- New: A window pops up where the user must enter the mail address of the recipient (title “To:”), the subject of the message (title: “Subject”) and (optional) a message (title: “message”) if he wants to send a message. He then has a send and a cancel button.
- Sent: The mails sent by the user are displayed the same way as in the inbox.

<h5>Enquiries</h5>
- The Enquiries are displayed in a table, sorted by sender, ad, date of the visit, date sent, and an action. The user can either accept or decline an enquiry and if done so it is displayed.

<h5>Schedule</h5>
- The schedule contains two lists: The users presentations and the users visits.
- In both, he sees the address, date, time of the visit/presentation. The user can visit the ad page of the flat and if he's the presenter he can view a list of the users who signed up to visit the property, sorted by name, username, profile. Here he can set his rating of the visiting user.

<h5>Alerts</h5>
- The user can create a new alert by clicking subscribe when he searches for real estate. If new ad(s) which would satisfy his subscribed search criteria are added he gets an alert for the new ad(s) fitting his search criteria.   
- On the alerts page the user has a table of his active alerts, sorted by type, city, radius, max price and an option to delete the alert.  
- He can easily search for all ads matching the alert criteria by just clicking a button beside the alert.

<h5>User Profile</h5>
- The users profile picture, username, name and a description are displayed with the options to message the user.
- If the user is viewing his own profile there is a button that leads to the edit profile page.

<h5>Edit Profile</h5>
- The user can change the username, first name, last name and password and change the description.
- The user can add a credit card to his profile.
- The user can edit his credit card.

<h4>Home Page</h4>
- The homepage is designed to capture the users attention by displaying a rather large banner with our product statement. He can then search for real estate very quickly by just entering the location he wants to search for. All the other search parameters are then set to the default value. 
- The user can see the premium ads and the newest ads. An ad contains a picture, title, address, type, price, move-in-date and it is shown if the ad has an instant buy option and if it is a premium ad.
- If the user is logged in he can see the ads he bookmarked on the home page.

<h4>Search</h4>
- He can tick the type of property he wants to search for and must enter a location, radius and a price and can then search or cancel.
- There is also an advanced search section where he can specify additional parameters. (Instant-Buy-Price lower than, Earliest move-in date, latest move-in-date, smoking allowed, garden, cellar or attic, garage, washing machine, animals inside allowed, balcony or patio, furnished, dishwasher)

<h4>Display search</h4>
- The results are displayed and he can sort them by the sort options.
- A Premium Ad is marked as such. Such a premium ad only shows up when the criteria match.
- There is a 'Show Search' button where the user can adjust the search criteria and redo the search.
- An ad contains a picture, title, address, type, price, move-in-date and it is shown if the ad has an instant buy option and if it is a premium ad. Additionally the user can also see how much time is left until the auction expires.
- The user can display his search on a map by clicking the "Show Map" button.

<h4>Display Ad</h4>
- The user sees the title, the details (type, address, available from, price, number of rooms and square meters), a description of the property and images of the property.
- Additionally it is displayed if smoking inside is allowed, if animals are allowed, if the room is furnished, if it has a garage, a cellar, a balcony, a garden, a dishwasher or a washing machine.
- The user can see a list of all the bids by clicking the "Show All Bids" button.
- The remaining time until the auction ends is displayed and the user and has the option to make a bid for this property if it is not his own.
- The user can view possible visiting times and send enquiries to the advertiser for visiting. 
- The user can view the advertiser or message him by clicking the "Contact Advertiser" button.
- If the displayed ad is advertised by the current logged-in user, he can click the "Edit Ad" button to get to the page for editing his ad.
- The user can bookmark an ad so that it will show up in his bookmarks in the "my rooms" page and on the front page.

<h4>My rooms</h4>
- The user can see all of his properties that he advertised on this page with a small preview and a link to visit the ad.
- The user can see all of his bookmarked properties on this page with a small preview and a link to visit the bookmarked ad.

<h4>Balance</h4>
- The user can see how much money he earned on Flatfindr. (By selling real estate)
- The user can also see how much money he spent on Flatfindr. (By winning an auction or using the instant buy option to buy property or putting up a premium ad)

<h4>Insights</h4>
- If an administrator is logged in, he can see statistics about Flatfindr on the Insights page.
- The administrator can see how many users are subscribed, ads active, ads expired, ads in total and total money spent on Flatfindr.
- He can also see how many properties of each type are advertised and what options are chosen for the properties.
- He sees how much money Flatfindr has earned with provisions and premium ads.
   
<h4>Header</h4>
- The header is displayed on the top of each page of the website.
- It contains a name of the website with a link to the home page as well as a link to the about section.
- There is a search button in the header for convenience, so that the user can get to the search page no matter where he is on the website.
- If the user is not logged in there is a link to login on the top right.
- If the user is logged in the users name is displayed next to a small profile picture. He can click it to get a dropdown menu with all of the relevant user pages. (Place an ad, My rooms, Balance, Messages, Enquiries, Schedule, Alerts, Insights, Public Profile, Logout)


<h3>Non-functional requirements (external, performance, etc.)</h3>
- This website should work flawlessly on all browsers.
- An ad/user should be unique.
- The website should be designed elegantly and modern.
- The Homepage should be personalized for each user.


