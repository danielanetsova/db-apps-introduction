# Introduction to DB Apps

## Tasks:
- DataRetrievalApplication class retrieves information about the users, their games and their duration. The database used is called diablo_database and is located in. For all other classes minions_db database is used.

- Add Minion class reads information about a minion and its villain and adds it to the database. In case the town of the minion is not in the database, inserts it as well. In case the villain is not present in the database, add him too with the default evilness factor of “evil”. Finally, set the new minion to be a servant of the villain. Print appropriate messages after each operation.

- ChangeTownNamesCasing class changes all town names to uppercase for a given country. Prints the number of towns that were changed. On the next line prints the names that were changed, separated by a coma and a space.

- GetMinionNames class writes a program that prints on the console all minion names and their age for a given villain id.

- GetVillainsNames prints on the console all villains’ names and their number of minions. Gets only the villains who have more than 15 minions. Orders them by a number of minions in descending order.

- IncreaseAgeStoredProcedure. A stored procedure usp_get_older that receives a minion_id and increases the minion’s years by 1 was created. The class uses that stored procedure to increase the age of a minion, whose id will be given as input from the console. After that prints the name and the age of that minion.

- IncreaseMinionsAge reads from the console minion IDs, separated by space. Increment the age of those minions by 1 and make their names titles lower case. Finally, prints the names and the ages of all minions that are in the database.

- PrintAllMinionNames prints all minion names from the minion’s table in order first record, last record, first + 1, last – 1, first + 2, last – 2… first + n, last – n.

- RemoveVillain receives an ID of a villain, deletes him from the database and releases his minions from serving him. As an output prints the name of the villain and the number of minions released. If some operation does not go as planned, makes no changes to the database.
