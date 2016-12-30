module TimeConstraints
  ALL = {
    'all-time'   => 'All time',
    'today'      => 'Today',
    'this-week'  => 'This week',
    'this-month' => 'This month',
    'this-year'  => 'This year',
  }

  def self.all
    ALL
  end

  def self.label_for(key)
    ALL[key]
  end

  def self.known_key?(key)
    ALL.has_key?(key)
  end

  # These date objects have to be computed per call, they can't be associated
  # to the keys.
  def self.since_for(key)
    case key
    when 'all-time'
      nil
    when 'today'
      Date.current
    when 'this-week'
      Date.current.beginning_of_week
    when 'this-month'
      Date.current.beginning_of_month
    when 'this-year'
      Date.current.beginning_of_year
    else
      nil
    end
  end
end
