module Config

  @secrets = YAML.load_file File.dirname( File.dirname(__FILE__) + ".." ) + "/config/secrets.yml"
  @ENV = ENV['env']
  @client_id = @secrets['environments'][@ENV]['client_id']
  @client_secret = @secrets['environments'][@ENV]['client_access']

  def base_url
    raise ConfigurationException, "Could not find base url in secrets.yml" unless @secrets['environments'][@ENV]['base_url']
    @secrets['environments'][@ENV]['base_url']
  end

  def credential_with_base64
    base64_encoded_auth = Base64.strict_encode64"#{@client_id}:#{@client_secret}"
    "Basic #{base64_encoded_auth}"
  end

  def program_short_code
    raise ConfigurationException, "Could not find base url in secrets.yml" unless @secrets['environments'][@ENV]['base_url']
    @secrets['environments'][@ENV]['program_shortcode']
  end
end
