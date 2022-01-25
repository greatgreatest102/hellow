## Marqeta Test Automation

This repository consist of a test automation framework to test some of Marqeta's API

## Pre-Requisites:
* Python 3.7+
* Pip3
* Install all dependencies - `pip3 install -r requirements.txt` or `pip install -r requirements.txt`
* add a .env file with the following environment variables or just add them to your environment variables directly:
    * application_token=<marqeta_username_token>
    * admin_access_token=<marqeta_pw_token>
## Steps to run Tests:
1. Clone this project and load onto your IDE
2. In your terminal, Go to the python root directory of the project
3. Confirm and ensure that you have the pre-requisites set up
4. Run all test using this command - `pytest`
## Complete Task
1. Start from 
  - The Users test file.
  - Cards test file.
  - Transactions test file.