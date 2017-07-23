class FixColumnName < ActiveRecord::Migration[5.0]
  def change
    def self.up
      rename_column :users, :token, :auth_token
    end
  end
end
