class CreateActiveAdminEvents < ActiveRecord::Migration[5.0]
  def change
    create_table :active_admin_events do |t|
      t.string :title,            null: false, default: ""
      t.text   :description,      null: false, default: ""
      t.text   :category,         null: false, default: "unknown"
      t.timestamps null: false
    end
    add_index :active_admin_events, :title
  end
end
