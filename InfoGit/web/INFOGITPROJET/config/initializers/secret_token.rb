# Be sure to restart your server when you modify this file.

# Your secret key is used for verifying the integrity of signed cookies.
# If you change this key, all old signed cookies will become invalid!

# Make sure the secret is at least 30 characters and all random,
# no regular words or you'll be exposed to dictionary attacks.
# You can use `rake secret` to generate a secure secret key.

# Make sure your secret_key_base is kept private
# if you're sharing your code publicly.
RailsContributors::Application.config.secret_token = 'fc5a8f4c56bdffaa4b9792a6049f2cd43989ff4128ae79c527e66874219855f45329a193a133fc14bfd2e983fb3029ec1b7272ceaec60c53a60018e741f5582a'
RailsContributors::Application.config.secret_key_base = 'this application is read-only'
