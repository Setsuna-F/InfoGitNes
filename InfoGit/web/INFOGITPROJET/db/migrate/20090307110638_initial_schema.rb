class InitialSchema < ActiveRecord::Migration
  def self.up
    create_table :contributors do |t|
      t.string  :name
      t.string  :url_id, :null => false
      t.integer :rank
    end
    add_index :contributors, :name
    add_index :contributors, :url_id, :unique => true

    create_table :commits do |t|
      t.string    :sha1, :null => false
      t.string    :author
      t.timestamp :authored_timestamp
      t.string    :committer
      t.timestamp :committed_timestamp
      t.text      :message
      t.boolean   :imported_from_svn
      t.text      :git_show
    end
    add_index :commits, :sha1, :unique => true

    create_table :contributions do |t|
      t.references :contributor, :null => false
      t.references :commit, :null => false
    end
    add_index :contributions, :contributor_id
    add_index :contributions, :commit_id

    create_table :repo_updates do |t|
      t.integer   :ncommits
      t.timestamp :started_at
      t.timestamp :pulled_at
      t.timestamp :ended_at
    end
  end

  def self.down
    drop_table :contributors
    drop_table :commits
    drop_table :contributions
    drop_table :repo_updates
  end
end
