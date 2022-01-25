require 'uri'
require 'net/http'
require_relative '../spec/spec_helper'

module SetUp

  def secrets
    YAML.load_file('./config/secrets.yml')
  end
end
