# PracticTask
A program that administrates a database of jobs, tasks, and a file that will make the link between them.

The program contains the following things:

-Login Window: A user can login in the program using his username and password. After a successful login a new window showing the main progam will open. In the same window, new users can register themselves, every new user having only a READING permission at first. The ADMINISTRATOR of the program can give different permission to the users, ranging from READ,WRITE,and ALL (this means he can do whatever he wants with the database)(usually reserved only for the admin users)

-Main Window: On this window, a user that has the ALL permission can see a paginated table that refreshes depending on what option is selected in a combo box (Jobs, Tasks or JobFile), text fields that let the user insert new elements in the database, he/she can delete an element from the database, or he/she can filter them so the table shows only relevant informations.

-PDF generation: Clicking a button, the user can generate a pdf containing the elements from the File(Fisa, in the code) section. He can choose how many elements the pdf contains. If the number is lower than the number of the total elements in the File, a sorting alghoritm will take the ones than contains only the most commonly used Tasks, in descending order.

The program was made in Java, it uses OOP principles, it has a caching mechansim (made using the Spring Framework), a XML configuration file (using bean's), and the elements are taken from xml files.


![The winow that will be shown after a succesful login, for a user that has the ALL permission or both of the READ and WRITE ones](https://i.imgur.com/smZmxFP.png)
