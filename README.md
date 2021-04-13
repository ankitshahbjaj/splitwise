# splitwise
Clone repo.
Add database url and username password in application.properties file.
run command 
mvn clean install -U

run command

java -noverify -jar target/splitwise-1.0-SNAPSHOT.jar 


then try to create a user by calling create user api
try create a group
try creating a expense in a group.
try adding new transaction.


Note: I havn't added any status anywhere as we discussed yesterday.

API is provided for all the operations we discussed.


1. Create a User
2. Create a Group
3. Add Users to group
//4. Accept/Decline the invite
5. Add Expense & Split with Some Users
6. Current Balance
7. GroupWise User Balance
8. Settle Balances
//9. Simplified Debts


BaseEntity
	-> createTime
	-> lastUpdatedTime
	-> createdBy
	-> lastUpdatedBy
	-> version
	-> naturalId


User
 -> id
 -> name
 -> phoneNumber
 -> status[ONBOARDING, ACTIVE, ONHOLD, INACTIVE]

 Group
  -> id
  -> name
  -> status[ACTIVE, INACTIVE]

 GroupPartcipants
 	-> id
 	-> groupId*
 	-> userId*
 	-> role [ADMIN, PARTICIPANT]
 	-> status [INVITED, DECLINED, ACTIVE, INACTIVE]


 Expense
 	-> id
 	-> groupId*
 	-> amount

 Transaction
 	-> id
 	-> expenseId*
 	-> payeeId*
 	-> recieverId*
 	-> amount





