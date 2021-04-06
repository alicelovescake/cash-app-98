# Cash App '98  💸

### What if I gave you a million pieces of paper? 💰

Think it's worthless? What if I told you that Elon Musk will let you to buy *a Tesla* with these pieces of paper? 

**Money**, in and of it itself, has no actual value. Yet when it moves from one hand to another, it starts to function 
as a medium of exchange and a storehouse of wealth. 

### How can we make it easier to send money, spend money and save money? 🤯

Mobile payment and virtual currency has revolutionized money transfers to make it faster and more convenient than ever 
before. Imagine businesses and personal users being able to send and receive money with anyone within a matter of 
minutes... *with zero fees*. 

### What if Cash App was invented in the 90s? 💡

Introducing, **Cash App '98**, a state of the art (for its time) payment app that aims to redefine the value of money 
by making it universally accessible, at your fingertips. Connect your credit card and send money 
in an instant rather than enduring long bank wait times and expensive fees. Start capitalizing on the power of digital 
wallets to exceed the limits of what money transfer could do in the 90s. 

Now it is possible with an easy-to-use, desktop app that allows you to navigate your transactions with ease and joy! 

### Who is Cash App '98 for? 🌐
It's for you! 

During the 90s, a time when tools like 
[SWIFT](https://en.wikipedia.org/wiki/Society_for_Worldwide_Interbank_Financial_Telecommunication) 
were used largely for businesses and banking-related transfers, 
the lifestyle needs of the individual was not being satisfied. 
With **Cash App '98**, everyone with a bank account can take part in defining *an inclusive economy*. 

### Why build this? 🚀
Equitable access and freedom with money is the key to *an inclusive economy*.
Cash App '98 is inspired by modern, 21st century tools like [Venmo](https://venmo.com/) and 
[Cash App](https://cash.app/), both part of the fastest growing financial brands in the world, 
with a combined user base of over 100 million monthly 
active users. Cash App '98 aims to empower people during the rise of the internet in the 90s, 
so that everyone can have equal access to opportunity. 

There is only one question for you to consider:

> Are you ready to re-invent the future with us?

---

### User Stories 💁

- As a user, I want to be able to create a personal or business account and use username as my *$cashtag*.
- As a user, I want to be able to view/add/remove my credit card to my list of credit cards.
- As a user, I want to be able to deposit and withdraw funds from my account.
- As a user, I want to be able to send and receive money to other users.
- As a user, I want to be able to request money from another user.
- As a user, I want to be able to view my current balance at any time.
- As a user, I want to be able to view my history of pending, failed, and completed transactions.
- As a user, I want to be rewarded for referring my friends to the app.
- As a user, I want to save all my account changes and transactions to file.
- As a user, I want to load all my previous account changes and transactions from a file.
- As a user, I can create, view and edit my account through a graphical user interface.

---
### Design Constructs ✅
- Type hierarchy is implemented in the UI package. Page is the interface with method `createPage()` that is implemented 
and overridden in every subclass that end in `*page` (i.e. DepositPage).

---
### Future Improvements ✅
Great apps take iteration and thoughtful design to achieve readability and maintainability. 

Here are some things I'd like to do in Cash App 2.0:
- *Improve cohesion by ensuring each class has a single responsibility*
  - **Problem:** Currently the account class has 18 different methods performing different functions. This results in 
      different clusters of methods that represent different responsibilities. 
      
  - **Solution:** It would be best to refactor each cluster into its own class. So splitting all the transaction 
    related methods into a class, the credit card management into another class, and boost management into a separate 
    class.
    
- *Reducing coupling so modules are easy to test, reuse, and avoid compiling errors*
  - **Problem:** Currently the pages in the UI class are tightly coupled. All the pages share implementation details 
    with each other such as the `addComponentListener` and the `createPage` method.
    
  - **Solution:** I've already added a type hierarchy to refactor all the pages to implement a page interface. I 
    could also add an Abstract Class that includes the shared implementation details so that changes happen 
    at a single place. 
    
- *Complete robustness so every class has exception handling*
  - **Problem:** Not all classes have exception handling. For instance, if a user enters a string input instead of 
    integer as their credit card number, the program will crash.
    
  - **Solution:** Add additional exceptions in all methods with REQUIRE claus and include them in tests to ensure that 
    all inputs are handled to make the program more robust.
    
- *Future Feature Improvements:*
  - Add multiple users to app and include messaging notifications that can go to different people
  - Messaging and notifications would be a great feature to utilize the observer design principle to ensure all users 
    are subscribed to and respond appropriately to changes in state. 



