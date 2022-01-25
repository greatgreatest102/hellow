## Tests addition requests, and issues to be fixed
#### Users Endpoint
- Run users tests only
  - Hint - Run the path to users or by className only.
- Fix failing tests if any
- Add assertions to the suspended user
  - Ruby: /spec/users_spec
- Create user and assert user’s 
  - Password
  - Passport details
  - Drivers license details
#### Cards 
- Run cards tests only
  - Hint - Run the path to cards or by className only.
Fix failing tests
Create cards
Provide ONLY Card Product token to candidate
Write one negative tests
{
   card_product_token: "card_product_token",
   user_token: "user_token"
}

Transaction
Add assertions to the transaction tests.
DRY the test above. 
Write a negative test here. 
Write a test to create card with an empty user request payload
