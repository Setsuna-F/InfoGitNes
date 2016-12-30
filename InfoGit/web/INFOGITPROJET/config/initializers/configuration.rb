config_data = File.read(Rails.root + "config" + "config.yml")
$app_config = YAML.load(config_data)[Rails.env].with_indifferent_access
