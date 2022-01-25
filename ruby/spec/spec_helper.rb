
## Require all files here
require 'yaml'
require 'json'
require 'rest_client'
require 'base64'
require 'faker'
require 'rspec'
require 'mysql2'
require_relative '../lib/config'
require_relative '../setup/test_harness'
require_relative '../lib/test-data/user_data'
require_relative '../lib/test-data/transaction_data'
require_relative '../lib/test-data/card_data'


# include all Modules here
include Config
include SetUp
include UserData
include TransactionData
include CardData
