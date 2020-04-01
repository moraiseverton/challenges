class CreateWriters < ActiveRecord::Migration[5.1]
  def change
    create_table :writers do |t|
      t.string :name
      t.string :website
      t.string :website_shortened
      t.text :website_headings

      t.timestamps
    end
  end
end
