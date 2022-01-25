## Candidate Test Suite
### Set up
- Clone this repository
- Authorization and configuration are kept in the `config/secrets.yml`
- Run `bundle install` on your terminal
### How to Run Tests
- **To run all tests** - `rake all env=qa`
- **To run specific test** - `env=qa rspec spec/<test>_spec`
### Test Directory
- The `config/` directory contains environment variables in a yml file(s)
- The `lib/` directory is where you'll find module to return yml data. The module will be called by spec files
- The `setup/` directory is where you'll find test harness and setup scripts. When you execute `rake all`, these should always be defined to run. This assumes the API is idempotent. 
- The `spec/` directory is where you'll find spec files AKA tests.
- The `.rspec`, `.ruby-version`, `.gitignore` files should be complete as-is
- The `.ruby-gemset` needs to be defined, and the naming convention is application-name-test-suite

Notes: 
- RMV is a requirement http://rvm.io/
- If you need to use any MySQL gems, you will need to `$ brew install mysql`
- Install gems (and thus populate `Gemfile.lock`) but make sure you're doing so in the namespace you defined in `.ruby-gemset`:
```
$ gem install bundler
$ bundle install
```
## Sandbox Endpoints
- Users
- Cards
- Transactions
- Balances
## Addition/Issues/Bugs
- You can find test addition requests, and issues to be fixed [here]()


