class AddTokenCreatedAtToUsers < ActiveRecord::Migration[5.0]
  def change
    add_column :users, :token_created_at, :datetime
    remove_index :users, :authentication_token
    add_index :users, [:authentication_token, :token_created_at]
  end
end
